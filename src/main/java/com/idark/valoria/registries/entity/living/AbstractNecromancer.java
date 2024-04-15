package com.idark.valoria.registries.entity.living;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import net.minecraft.core.BlockPos;
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
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
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
            int r = spell.spellColor[0];
            int g = spell.spellColor[1];
            int b = spell.spellColor[2];

            float f = this.yBodyRot * ((float) Math.PI / 180F) + Mth.cos(this.tickCount * 0.6662F) * 0.25F;
            float f1 = Mth.cos(f);
            float f2 = Mth.sin(f);
            for (int i = 0; i < 1f; i++) {
                Particles.create(ModParticles.GLOWING_SPHERE)
                        .addVelocity(((new Random().nextDouble() - 0.5D) / 30), (new Random().nextDouble() + 0.5D) / 6, (new Random().nextDouble() - 0.5D) / 30)
                        .setAlpha(0.65f, 0)
                        .setScale(0.2f, 0)
                        .setColor(r, g, b, 0, 0, 0)
                        .setLifetime(8)
                        .setSpin((0.5f * (float) ((new Random().nextDouble() - 0.5D) * 2)))
                        .spawn(this.level(), this.getX() + 0.2 + (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() + 0.2 + (double) f2 * 0.6D)
                        .spawn(this.level(), this.getX() - 0.2 - (double) f1 * 0.6D, this.getY() + 1.8D, this.getZ() - 0.2 - (double) f2 * 0.6D);
            }

            if (spell.id == necromancerSpell.SUMMON_MOBS.id || spell.id == necromancerSpell.HEAL.id) {
                BlockPos blockpos = AbstractNecromancer.this.blockPosition().offset(-2 + AbstractNecromancer.this.random.nextInt(5), 0, -2 + AbstractNecromancer.this.random.nextInt(5));
                Vector3d direction = new Vector3d(AbstractNecromancer.this.getX() - blockpos.getX(), AbstractNecromancer.this.getY() + blockpos.getY(), AbstractNecromancer.this.getZ() - blockpos.getZ()).normalize();
                double speed = 0.3;
                double motionX = direction.x * speed;
                double motionY = direction.y * speed;
                double motionZ = direction.z * speed;
                for (int i = 0; i < 0.2f; i++) {
                    double startX = blockpos.getX() + 0.5;
                    double startY = blockpos.getY() - 0.2;
                    double startZ = blockpos.getZ() + 0.5;
                    Particles.create(ModParticles.GLOWING_SPHERE)
                            .addVelocity(motionX, motionY, motionZ)
                            .setAlpha(0.65f, 0)
                            .setScale(0.2f, 0)
                            .setColor(r, g, b, 0, 0, 0)
                            .setLifetime(8)
                            .setSpin((0.5f * (float) ((new Random().nextDouble() - 0.5D) * 2)))
                            .spawn(this.level(), startX, startY, startZ);
                }
            }
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
        SUMMON_MOBS(1, 30, 35, 75),
        FANGS(2, 160, 164, 164),
        WOLOLO(3, 46, 51, 60),
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
