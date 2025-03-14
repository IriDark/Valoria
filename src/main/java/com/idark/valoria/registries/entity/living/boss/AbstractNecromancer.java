package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.particle.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import org.joml.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;
import java.lang.Math;
import java.util.Random;
import java.util.*;
import java.util.function.*;

public abstract class AbstractNecromancer extends Monster{
    private static final EntityDataAccessor<Byte> DATA_SPELL_CASTING_ID = SynchedEntityData.defineId(AbstractNecromancer.class, EntityDataSerializers.BYTE);
    protected int spellCastingTickCount;
    private NecromancerSpells currentSpell = NecromancerSpells.NONE;

    protected AbstractNecromancer(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_SPELL_CASTING_ID, (byte)0);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.spellCastingTickCount = pCompound.getInt("SpellTicks");
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("SpellTicks", this.spellCastingTickCount);
    }

    public boolean hasTarget(){
        return AbstractNecromancer.this.getTarget() != null;
    }

    public boolean isCastingSpell(){
        if(this.level().isClientSide){
            return this.entityData.get(DATA_SPELL_CASTING_ID) > 0;
        }else{
            return this.spellCastingTickCount > 0;
        }
    }

    public void setIsCastingSpell(NecromancerSpells pCurrentSpell){
        this.currentSpell = pCurrentSpell;
        this.entityData.set(DATA_SPELL_CASTING_ID, (byte)pCurrentSpell.id);
    }

    public NecromancerSpells getCurrentSpell(){
        return !this.level().isClientSide ? this.currentSpell : NecromancerSpells.byId(this.entityData.get(DATA_SPELL_CASTING_ID));
    }

    protected void customServerAiStep(){
        super.customServerAiStep();
        if(this.spellCastingTickCount > 0){
            --this.spellCastingTickCount;
        }
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide && this.isCastingSpell()){
            NecromancerSpells spell = this.getCurrentSpell();
            int r = spell.spellColor[0];
            int g = spell.spellColor[1];
            int b = spell.spellColor[2];

            float f = this.yBodyRot * ((float)Math.PI / 180F) + Mth.cos(this.tickCount * 0.6662F) * 0.25F;
            float f1 = Mth.cos(f);
            float f2 = Mth.sin(f);
            Col colorTo = new Col(r, g, b);
            for(int i = 0; i < 1f; i++){
                ParticleBuilder.create(ParticleRegistry.SPHERE)
                        .setLifetime(8)
                        .setTransparencyData(GenericParticleData.create(0.65f, 0).build())
                        .setColorData(ColorParticleData.create(colorTo, Col.white).build())
                        .addVelocity(((this.random.nextDouble() - 0.5D) / 6), ((this.random.nextDouble() - 1.25D) / 8), ((this.random.nextDouble() - 0.5D) / 6))
                        .setSpinData(SpinParticleData.create((0.5f * (float)((new Random().nextDouble() - 0.5D) * 2))).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .spawn(this.level(), this.getX() + 0.2 + (double)f1 * 0.6D, this.getY() + 1.8D, this.getZ() + 0.2 + (double)f2 * 0.6D)
                        .spawn(this.level(), this.getX() - 0.2 - (double)f1 * 0.6D, this.getY() + 1.8D, this.getZ() - 0.2 - (double)f2 * 0.6D);
            }

            if(spell.id == NecromancerSpells.SUMMON_MOBS.id || spell.id == NecromancerSpells.HEAL.id){
                BlockPos blockpos = AbstractNecromancer.this.blockPosition().offset(-2 + AbstractNecromancer.this.random.nextInt(5), 0, -2 + AbstractNecromancer.this.random.nextInt(5));
                Vector3d direction = new Vector3d(AbstractNecromancer.this.getX() - blockpos.getX(), AbstractNecromancer.this.getY() + blockpos.getY(), AbstractNecromancer.this.getZ() - blockpos.getZ()).normalize();
                double speed = 0.3;
                double motionX = direction.x * speed;
                double motionY = direction.y * speed;
                double motionZ = direction.z * speed;
                for(int i = 0; i < 0.2f; i++){
                    double startX = blockpos.getX() + 0.5;
                    double startY = blockpos.getY() - 0.2;
                    double startZ = blockpos.getZ() + 0.5;
                    ParticleBuilder.create(ParticleRegistry.SPHERE)
                            .setLifetime(8)
                            .addVelocity(motionX, motionY, motionZ)
                            .setTransparencyData(GenericParticleData.create(0.65f, 0).build())
                            .setColorData(ColorParticleData.create(colorTo, Col.white).build())
                            .addVelocity(((this.random.nextDouble() - 0.5D) / 6), ((this.random.nextDouble() - 1.25D) / 8), ((this.random.nextDouble() - 0.5D) / 6))
                            .setSpinData(SpinParticleData.create((0.5f * (float)((new Random().nextDouble() - 0.5D) * 2))).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .spawn(this.level(), startX, startY, startZ);
                }
            }

        }
    }

    public int getSpellCastingTime(){
        return this.spellCastingTickCount;
    }

    public SoundEvent getCastingSoundEvent(){
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    public enum NecromancerSpells{
        NONE(0, 0, 0, 0),
        SUMMON_MOBS(1, 30, 35, 75),
        FANGS(2, 160, 164, 164),
        WOLOLO(3, 46, 51, 60),
        HEAL(4, 164, 202, 65, true),
        KNOCKBACK(5, 185, 201, 203, true),
        EFFECT(6, 190, 105, 25);

        private static final IntFunction<NecromancerSpells> BY_ID = ByIdMap.continuous((p_263091_) -> p_263091_.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public final int id;
        public final boolean hasAura;
        public final int[] spellColor;

        NecromancerSpells(int pId, int pRed, int pGreen, int pBlue, boolean pAura){
            this.id = pId;
            this.hasAura = pAura;
            this.spellColor = new int[]{pRed, pGreen, pBlue};
        }

        NecromancerSpells(int pId, int pRed, int pGreen, int pBlue){
            this.id = pId;
            this.hasAura = false;
            this.spellColor = new int[]{pRed, pGreen, pBlue};
        }

        public static NecromancerSpells byId(int pId){
            return BY_ID.apply(pId);
        }

        public static boolean hasAura(int pId){
            return BY_ID.apply(pId).hasAura;
        }
    }

    public class SpellcasterCastingSpellGoal extends Goal{
        public SpellcasterCastingSpellGoal(){
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return AbstractNecromancer.this.getSpellCastingTime() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            super.start();
            AbstractNecromancer.this.navigation.stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            super.stop();
            AbstractNecromancer.this.setIsCastingSpell(NecromancerSpells.NONE);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            if(AbstractNecromancer.this.getTarget() != null){
                AbstractNecromancer.this.getLookControl().setLookAt(AbstractNecromancer.this.getTarget(), (float)AbstractNecromancer.this.getMaxHeadYRot(), (float)AbstractNecromancer.this.getMaxHeadXRot());
            }
        }
    }

    public abstract class SpellcasterUseSpellGoal extends Goal{
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            LivingEntity livingentity = AbstractNecromancer.this.getTarget();
            if(AbstractNecromancer.this.hasTarget() && livingentity.isAlive()){
                if(AbstractNecromancer.this.isCastingSpell()){
                    return false;
                }else{
                    return AbstractNecromancer.this.tickCount >= this.nextAttackTickCount;
                }
            }else{
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            LivingEntity livingentity = AbstractNecromancer.this.getTarget();
            return livingentity != null && livingentity.isAlive() && this.attackWarmupDelay > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            AbstractNecromancer.this.setIsCastingSpell(this.getSpell());
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastingTime());
            AbstractNecromancer.this.spellCastingTickCount = this.getCastingTime();
            this.nextAttackTickCount = AbstractNecromancer.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            if(soundevent != null){
                AbstractNecromancer.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            --this.attackWarmupDelay;
            if(this.attackWarmupDelay == 0){
                this.performSpellCasting();
                AbstractNecromancer.this.playSound(AbstractNecromancer.this.getCastingSoundEvent(), 1.0F, 1.0F);
            }
        }

        protected abstract void performSpellCasting();

        public abstract int getCastingTime();

        public abstract int getCastingInterval();

        @Nullable
        public abstract SoundEvent getSpellPrepareSound();

        public abstract NecromancerSpells getSpell();
    }
}
