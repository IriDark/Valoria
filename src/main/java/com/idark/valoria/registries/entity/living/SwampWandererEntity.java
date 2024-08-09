package com.idark.valoria.registries.entity.living;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;

public class SwampWandererEntity extends Zombie{

    boolean searchingForLand;
    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;

    public SwampWandererEntity(EntityType<? extends SwampWandererEntity> type, Level pLevel){
        super(type, pLevel);
        this.setMaxUpStep(1F);
        this.moveControl = new SwampWandererEntity.DrownedMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.waterNavigation = new WaterBoundPathNavigation(this, pLevel);
        this.groundNavigation = new GroundPathNavigation(this, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 32.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.25D)
        .add(Attributes.ATTACK_DAMAGE, 6.0D)
        .add(Attributes.FOLLOW_RANGE, 25.0D)
        .add(Attributes.KNOCKBACK_RESISTANCE, new Random().nextDouble() * (double)0.05F)
        .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, new Random().nextDouble() * 0.25D + 0.5D);
    }

    public static boolean checkDrownedSpawnRules(EntityType<SwampWandererEntity> SwampWandererEntity, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom){
        if(!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)){
            return false;
        }else{
            boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL && (pMobSpawnType == MobSpawnType.SPAWNER || pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
            return pRandom.nextInt(40) == 0 && flag;
        }
    }

    protected void addBehaviourGoals(){
        this.goalSelector.addGoal(2, new SwampWandererEntity.DrownedGoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new SwampWandererEntity.DrownedAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new SwampWandererEntity.DrownedGoToBeachGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new SwampWandererEntity.DrownedSwimUpGoal(this, 1.2D, this.level().getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Drowned.class)).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::okTarget));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Axolotl.class, true, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    protected SoundEvent getAmbientSound(){
        return SoundEvents.DROWNED_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound(){
        return SoundEvents.DROWNED_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn){
        return SoundEvents.DROWNED_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn){
        this.playSound(SoundEvents.DROWNED_STEP, 0.20F, 0.5F);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty){
        if((double)pRandom.nextFloat() > 0.9D){
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public float getScale(){
        return this.isBaby() ? 0.8f : 1.45F;
    }

    protected boolean convertsInWater(){
        return false;
    }

    public boolean checkSpawnObstruction(LevelReader pLevel){
        return pLevel.isUnobstructed(this);
    }

    public boolean okTarget(@Nullable LivingEntity p_32396_){
        if(p_32396_ != null){
            return p_32396_.isAlive();
        }else{
            return false;
        }
    }

    public boolean isPushedByFluid(){
        return !this.isSwimming();
    }

    boolean wantsToSwim(){
        if(this.searchingForLand){
            return true;
        }else{
            LivingEntity livingentity = this.getTarget();
            return livingentity != null && livingentity.isInWater();
        }
    }


    @Override
    public boolean doHurtTarget(Entity entityIn){
        if(!super.doHurtTarget(entityIn)){
            return false;
        }else{
            if(entityIn instanceof LivingEntity){
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0));
            }
            return true;
        }
    }

    public void travel(Vec3 pTravelVector){
        if(this.isControlledByLocalInstance() && this.isInWater() && this.wantsToSwim()){
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        }else{
            super.travel(pTravelVector);
        }

    }

    public void updateSwimming(){
        if(!this.level().isClientSide){
            if(this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()){
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            }else{
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }

    }

    public boolean isVisuallySwimming(){
        return this.isSwimming();
    }

    protected boolean closeToNextPos(){
        Path path = this.getNavigation().getPath();
        if(path != null){
            BlockPos blockpos = path.getTarget();
            double d0 = this.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            return d0 < 4.0D;
        }

        return false;
    }

    public void setSearchingForLand(boolean pSearchingForLand){
        this.searchingForLand = pSearchingForLand;
    }

    static class DrownedAttackGoal extends ZombieAttackGoal{
        private final SwampWandererEntity drowned;

        public DrownedAttackGoal(SwampWandererEntity pDrowned, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen){
            super(pDrowned, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.drowned = pDrowned;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return super.canUse() && this.drowned.okTarget(this.drowned.getTarget());
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return super.canContinueToUse() && this.drowned.okTarget(this.drowned.getTarget());
        }
    }

    static class DrownedGoToBeachGoal extends MoveToBlockGoal{
        private final SwampWandererEntity drowned;

        public DrownedGoToBeachGoal(SwampWandererEntity pDrowned, double pSpeedModifier){
            super(pDrowned, pSpeedModifier, 8, 2);
            this.drowned = pDrowned;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return super.canUse() && !this.drowned.level().isDay() && this.drowned.isInWater() && this.drowned.getY() >= (double)(this.drowned.level().getSeaLevel() - 3);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return super.canContinueToUse();
        }

        /**
         * Return {@code true} to set given position as destination
         */
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos){
            BlockPos blockpos = pPos.above();
            return pLevel.isEmptyBlock(blockpos) && pLevel.isEmptyBlock(blockpos.above()) && pLevel.getBlockState(pPos).entityCanStandOn(pLevel, pPos, this.drowned);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            this.drowned.setSearchingForLand(false);
            this.drowned.navigation = this.drowned.groundNavigation;
            super.start();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            super.stop();
        }
    }

    static class DrownedGoToWaterGoal extends Goal{
        private final PathfinderMob mob;
        private double wantedX;
        private double wantedY;
        private double wantedZ;
        private final double speedModifier;
        private final Level level;

        public DrownedGoToWaterGoal(PathfinderMob pMob, double pSpeedModifier){
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.level = pMob.level();
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            if(this.mob.isInWater()){
                if(this.mob.getTarget() != null){
                    LivingEntity target = this.mob.getTarget();
                    BlockPos targetBlockPos = target.blockPosition();
                    if(this.level.getBlockState(targetBlockPos).is(Blocks.LILY_PAD)){
                        double jumpHeight = 15.5;
                        double jumpSpeed = 0.6;
                        Vec3 jumpVec = new Vec3(target.getX() - this.mob.getX(), jumpHeight, target.getZ() - this.mob.getZ()).normalize().scale(jumpSpeed);
                        if(target.distanceToSqr(this.mob) < 5){
                            this.level.destroyBlock(targetBlockPos, true);
                            this.mob.setDeltaMovement(jumpVec);
                            this.mob.hasImpulse = true;
                            return true;
                        }
                    }
                }

                return false;
            }else{
                Vec3 vec3 = this.getWaterPos();
                if(vec3 == null){
                    return false;
                }else{
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                    return true;
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return !this.mob.getNavigation().isDone();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }

        @Nullable
        private Vec3 getWaterPos(){
            RandomSource randomsource = this.mob.getRandom();
            BlockPos blockpos = this.mob.blockPosition();

            for(int i = 0; i < 10; ++i){
                BlockPos blockpos1 = blockpos.offset(randomsource.nextInt(20) - 10, 2 - randomsource.nextInt(8), randomsource.nextInt(20) - 10);
                if(this.level.getBlockState(blockpos1).is(Blocks.WATER)){
                    return Vec3.atBottomCenterOf(blockpos1);
                }
            }

            return null;
        }
    }

    static class DrownedMoveControl extends MoveControl{
        private final SwampWandererEntity drowned;

        public DrownedMoveControl(SwampWandererEntity pDrowned){
            super(pDrowned);
            this.drowned = pDrowned;
        }

        public void tick(){
            LivingEntity livingentity = this.drowned.getTarget();
            if(this.drowned.wantsToSwim() && this.drowned.isInWater()){
                if(livingentity != null && livingentity.getY() > this.drowned.getY() || this.drowned.searchingForLand){
                    this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
                }

                if(this.operation != MoveControl.Operation.MOVE_TO || this.drowned.getNavigation().isDone()){
                    this.drowned.setSpeed(0.0F);
                    return;
                }

                double d0 = this.wantedX - this.drowned.getX();
                double d1 = this.wantedY - this.drowned.getY();
                double d2 = this.wantedZ - this.drowned.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.drowned.setYRot(this.rotlerp(this.drowned.getYRot(), f, 90.0F));
                this.drowned.yBodyRot = this.drowned.getYRot();
                float f1 = (float)(this.speedModifier * this.drowned.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f2 = Mth.lerp(0.125F, this.drowned.getSpeed(), f1);
                this.drowned.setSpeed(f2);
                this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add((double)f2 * d0 * 0.005D, (double)f2 * d1 * 0.1D, (double)f2 * d2 * 0.005D));
            }else{
                if(!this.drowned.onGround()){
                    this.drowned.setDeltaMovement(this.drowned.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
                }

                super.tick();
            }
        }
    }

    static class DrownedSwimUpGoal extends Goal{
        private final SwampWandererEntity drowned;
        private final double speedModifier;
        private final int seaLevel;
        private boolean stuck;

        public DrownedSwimUpGoal(SwampWandererEntity pDrowned, double pSpeedModifier, int pSeaLevel){
            this.drowned = pDrowned;
            this.speedModifier = pSpeedModifier;
            this.seaLevel = pSeaLevel;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return this.drowned.isInWater() && this.drowned.getY() < (double)(this.seaLevel - 2);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return this.canUse() && !this.stuck;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            if(this.drowned.getY() < (double)(this.seaLevel - 1) && (this.drowned.getNavigation().isDone() || this.drowned.closeToNextPos())){
                Vec3 vec3 = DefaultRandomPos.getPosTowards(this.drowned, 4, 12, new Vec3(this.drowned.getX(), this.seaLevel - 1, this.drowned.getZ()), (float)Math.PI / 2F);
                if(vec3 == null){
                    this.stuck = true;
                    return;
                }

                this.drowned.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            this.drowned.setSearchingForLand(true);
            this.stuck = false;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            this.drowned.setSearchingForLand(false);
        }
    }
}