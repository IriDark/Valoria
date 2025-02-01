package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class Ent extends MultiAttackMob implements Enemy{
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();

    public Ent(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    public static boolean checkEntSpawnRules(EntityType<Ent> Ent, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
    }

    protected static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos) {
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    protected boolean canRide(Entity pVehicle) {
        return false;
    }

    public boolean canDisableShield() {
        return true;
    }

    public SoundSource getSoundSource(){
        return SoundSource.HOSTILE;
    }

    public void aiStep(){
        this.updateSwingTime();
        super.aiStep();
    }

    protected void doPush(@NotNull Entity pEntity){
    }

    @Override
    public void knockback(double strength, double x, double z){
    }

    protected boolean shouldDespawnInPeaceful(){
        return true;
    }

    protected SoundEvent getSwimSound(){
        return SoundEvents.HOSTILE_SWIM;
    }

    protected SoundEvent getSwimSplashSound(){
        return SoundEvents.HOSTILE_SPLASH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.HOSTILE_HURT;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.HOSTILE_DEATH;
    }

    public LivingEntity.Fallsounds getFallSounds(){
        return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, false));

        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers(Devil.class));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    /**
     * Actually a copy of vanilla melee attack goal with changes
     */
    public class MeleeAttackGoal extends AttackGoal {
        protected final PathfinderMob mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;

        public MeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse(){
            long i = this.mob.level().getGameTime();
            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }

                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return super.canUse() && canAttack();
                    }
                }
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if(livingentity == null || !livingentity.isAlive()) return false;
            if (this.mob.distanceToSqr(this.mob.getTarget()) > 6){
                return true;
            }

            if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else {
                return super.canContinueToUse() && canAttack();
            }
        }

        @Override
        public void start(){
            super.start();
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
        }

        public void stop() {
            super.stop();
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity)null);
            }

            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick(){
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    if (this.canPenalize) {
                        this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                        if (this.mob.getNavigation().getPath() != null) {
                            net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                            if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                                failedPathFindingPenalty = 0;
                            else
                                failedPathFindingPenalty += 10;
                        } else {
                            failedPathFindingPenalty += 10;
                        }
                    }
                    if (d0 > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                super.tick();
            }
        }

        @Override
        public void onPrepare(){
            this.mob.level().broadcastEntityEvent(mob, (byte)4);
        }

        @Override
        protected void performAttack(){
            if(this.mob.getTarget() == null) return;
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.mob.doHurtTarget(this.mob.getTarget());
        }

        @Override
        public int getPreparingTime(){
            return 40;
        }

        @Override
        public int getAttackInterval(){
            return 20;
        }

        @Override
        public @Nullable SoundEvent getPrepareSound(){
            return null;
        }

        public boolean canAttack() {
            boolean intersects = this.mob.getBoundingBox().inflate(1.5f).intersects(this.mob.getTarget().getBoundingBox());
            boolean isWithinRadius = this.getAttackReachSqr(this.mob.getTarget()) >= this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ());
            if (!this.mob.getSensing().hasLineOfSight(this.mob.getTarget())) {
                this.mob.getNavigation().stop();
                return false;
            }

            return intersects || isWithinRadius;
        }

        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
        }

        @Override
        public AttackRegistry getAttack(){
            return EntityStatsRegistry.THROW;
        }
    }
}
