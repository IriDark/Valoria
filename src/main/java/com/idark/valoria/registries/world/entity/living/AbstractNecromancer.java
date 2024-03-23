package com.idark.valoria.registries.world.entity.living;

import com.idark.valoria.util.ColorUtils;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.IntFunction;

public abstract class AbstractNecromancer extends Monster {

    private static final EntityDataAccessor<Byte> DATA_SPELL_CASTING_ID = SynchedEntityData.defineId(AbstractNecromancer.class, EntityDataSerializers.BYTE);
    protected int spellCastingTickCount;
    private AbstractNecromancer.necromancerSpell currentSpell = AbstractNecromancer.necromancerSpell.NONE;

    protected AbstractNecromancer(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SPELL_CASTING_ID, (byte) 0);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.spellCastingTickCount = pCompound.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("SpellTicks", this.spellCastingTickCount);
    }

    public boolean isCastingSpell() {
        if (this.level().isClientSide) {
            return this.entityData.get(DATA_SPELL_CASTING_ID) > 0;
        } else {
            return this.spellCastingTickCount > 0;
        }
    }

    public void setIsCastingSpell(AbstractNecromancer.necromancerSpell pCurrentSpell) {
        this.currentSpell = pCurrentSpell;
        this.entityData.set(DATA_SPELL_CASTING_ID, (byte) pCurrentSpell.id);
    }

    protected AbstractNecromancer.necromancerSpell getCurrentSpell() {
        return !this.level().isClientSide ? this.currentSpell : AbstractNecromancer.necromancerSpell.byId(this.entityData.get(DATA_SPELL_CASTING_ID));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellCastingTickCount > 0) {
            --this.spellCastingTickCount;
        }

    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isCastingSpell()) {
            AbstractNecromancer.necromancerSpell spell = this.getCurrentSpell();
            int d0 = spell.spellColor[0];
            int d1 = spell.spellColor[1];
            int d2 = spell.spellColor[2];
            Vector3f PARTICLE_COLOR = Vec3.fromRGB24(ColorUtils.packColor(155, d0, d1, d2)).toVector3f();
            Vector3f TO_PARTICLE_COLOR = Vec3.fromRGB24(ColorUtils.packColor(0, d0/2, d1/2, d2/2)).toVector3f();

            float f = this.yBodyRot * ((float) Math.PI / 180F) + Mth.cos((float) this.tickCount * 0.6662F) * 0.35F;
            float f1 = Mth.cos(f);
            float f2 = Mth.sin(f);
            this.level().addParticle(new DustColorTransitionOptions(PARTICLE_COLOR, TO_PARTICLE_COLOR, 1.0F), this.getX() + (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() + (double) f2 * 0.6D, 0, 0.15, 0);
            this.level().addParticle(new DustColorTransitionOptions(PARTICLE_COLOR, TO_PARTICLE_COLOR, 1.0F), this.getX() - (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() - (double) f2 * 0.6D, 0, 0.15, 0);
        }

    }

    protected int getSpellCastingTime() {
        return this.spellCastingTickCount;
    }

    public SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    public enum necromancerSpell {
        NONE(0, 0, 0, 0),
        SUMMON_MOBS(1, 255, 255, 255),
        FANGS(2, 255, 255, 255),
        WOLOLO(3, 65, 57, 74),
        HEAL(4, 164, 202, 65);

        private static final IntFunction<AbstractNecromancer.necromancerSpell> BY_ID = ByIdMap.continuous((p_263091_) -> p_263091_.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        final int id;
        final int[] spellColor;

        necromancerSpell(int pId, int pRed, int pGreen, int pBlue) {
            this.id = pId;
            this.spellColor = new int[]{pRed, pGreen, pBlue};
        }

        public static AbstractNecromancer.necromancerSpell byId(int pId) {
            return BY_ID.apply(pId);
        }
    }

    protected class SpellcasterCastingSpellGoal extends Goal {
        public SpellcasterCastingSpellGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return AbstractNecromancer.this.getSpellCastingTime() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            AbstractNecromancer.this.navigation.stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            AbstractNecromancer.this.setIsCastingSpell(AbstractNecromancer.necromancerSpell.NONE);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (AbstractNecromancer.this.getTarget() != null) {
                AbstractNecromancer.this.getLookControl().setLookAt(AbstractNecromancer.this.getTarget(), (float) AbstractNecromancer.this.getMaxHeadYRot(), (float) AbstractNecromancer.this.getMaxHeadXRot());
            }

        }
    }

    protected abstract class SpellcasterUseSpellGoal extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = AbstractNecromancer.this.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                if (AbstractNecromancer.this.isCastingSpell()) {
                    return false;
                } else {
                    return AbstractNecromancer.this.tickCount >= this.nextAttackTickCount;
                }
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = AbstractNecromancer.this.getTarget();
            return livingentity != null && livingentity.isAlive() && this.attackWarmupDelay > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
            AbstractNecromancer.this.spellCastingTickCount = this.getCastingTime();
            this.nextAttackTickCount = AbstractNecromancer.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            if (soundevent != null) {
                AbstractNecromancer.this.playSound(soundevent, 1.0F, 1.0F);
            }

            AbstractNecromancer.this.setIsCastingSpell(this.getSpell());
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performSpellCasting();
                AbstractNecromancer.this.playSound(AbstractNecromancer.this.getCastingSoundEvent(), 1.0F, 1.0F);
            }
        }

        protected abstract void performSpellCasting();

        protected int getCastWarmupTime() {
            return 20;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();

        protected abstract AbstractNecromancer.necromancerSpell getSpell();
    }
}
