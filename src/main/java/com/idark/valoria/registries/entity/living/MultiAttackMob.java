package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;

import javax.annotation.*;
import java.util.*;

//todo delete
public abstract class MultiAttackMob extends PathfinderMob{
    private static final EntityDataAccessor<String> DATA_ID = SynchedEntityData.defineId(MultiAttackMob.class, EntityDataSerializers.STRING);
    protected int preparingTickCount;
    public AttackRegistry currentAttack = AttackRegistry.NONE;

    public MultiAttackMob(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID, AttackRegistry.NONE.toString());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.preparingTickCount = pCompound.getInt("PrepareTicks");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("PrepareTicks", this.preparingTickCount);
    }

    public boolean isPreparingAttack() {
        if (this.level().isClientSide) {
            return !this.entityData.get(DATA_ID).equals("none");
        } else {
            return this.preparingTickCount > 0;
        }
    }

    public boolean hasTarget() {
        return MultiAttackMob.this.getTarget() != null;
    }

    public int getPreparingTime() {
        return this.preparingTickCount;
    }

    public SoundEvent getPreparingSound() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    public AttackRegistry getCurrentAttack() {
        return !this.level().isClientSide ? this.currentAttack : AttackRegistry.byId(this.entityData.get(DATA_ID));
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.preparingTickCount > 0) {
            --this.preparingTickCount;
        }
    }

    public void setCurrentAttack(AttackRegistry pCurrentAttack) {
        this.currentAttack = pCurrentAttack;
        this.entityData.set(DATA_ID, pCurrentAttack.getId());
    }

    public boolean isFleeing(Mob mob, float dist) {
        return mob.getNavigation().getPath() != null && mob.getNavigation().getPath().getDistToTarget() > dist;
    }

    public boolean cantReachTarget(LivingEntity target) {
        Path path = navigation.createPath(target, 1);
        return path == null;
    }

    public class PrepareGoal extends Goal{
        public PrepareGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return getPreparingTime() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            MultiAttackMob.this.navigation.stop();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            MultiAttackMob.this.setCurrentAttack(AttackRegistry.NONE);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (MultiAttackMob.this.getTarget() != null) {
                MultiAttackMob.this.getLookControl().setLookAt(MultiAttackMob.this.getTarget(), (float) MultiAttackMob.this.getMaxHeadYRot(), (float) MultiAttackMob.this.getMaxHeadXRot());
            }
        }
    }

    public abstract class AttackGoal extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = MultiAttackMob.this.getTarget();
            if (MultiAttackMob.this.hasTarget() && livingentity.isAlive()) {
                if (MultiAttackMob.this.isPreparingAttack()) {
                    return false;
                } else {
                    return MultiAttackMob.this.tickCount >= this.nextAttackTickCount;
                }
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = MultiAttackMob.this.getTarget();
            return livingentity != null && livingentity.isAlive() && this.attackWarmupDelay > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            MultiAttackMob.this.setAggressive(true);
            MultiAttackMob.this.setCurrentAttack(this.getAttack());
            this.attackWarmupDelay = this.adjustedTickDelay(this.getPreparingTime());
            MultiAttackMob.this.preparingTickCount = this.getPreparingTime();
            this.nextAttackTickCount = MultiAttackMob.this.tickCount + this.getAttackInterval();
            SoundEvent soundevent = this.getPrepareSound();
            this.onPrepare();
            if (soundevent != null) {
                MultiAttackMob.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        @Override
        public void stop(){
            MultiAttackMob.this.setAggressive(false);
            super.stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performAttack();
                MultiAttackMob.this.playSound(MultiAttackMob.this.getPreparingSound(), 1.0F, 1.0F);
            }
        }

        public abstract void onPrepare();

        protected abstract void performAttack();

        /**
         * Time to charge the attack
         */
        public abstract int getPreparingTime();

        /**
         * Cooldown between attacks
         */
        public abstract int getAttackInterval();

        @Nullable
        public abstract SoundEvent getPrepareSound();

        /**
         * Used to indicate which attack is used to delay it
         */
        public abstract AttackRegistry getAttack();
    }
}
