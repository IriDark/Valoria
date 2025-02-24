package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.core.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.raid.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.registry.entity.*;

import java.util.*;

public class FleshSentinel extends AbstractMinionEntity{
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(FleshSentinel.class, EntityDataSerializers.BYTE);
    public AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public boolean cystSpawned;

    public FleshSentinel(EntityType<? extends FleshSentinel> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.moveControl = new UndeadMoveControl(this);
        this.xpReward = 3;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions){
        return pDimensions.height - 0.28125F;
    }

    //todo meaty effect
    @Override
    public void spawnDisappearParticles(ServerLevel serverLevel){
        double posX = this.getOnPos().getCenter().x;
        double posY = this.getOnPos().above().getCenter().y;
        double posZ = this.getOnPos().getCenter().z;
        PacketHandler.sendToTracking(serverLevel, this.getOnPos(), new SmokeParticlePacket(3, posX, posY - 0.5f, posZ, 0.005f, 0.025f, 0.005f, 255, 255, 255));
    }

    public boolean isFlapping(){
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(0, new FleshSentinel.FleshSentinelOrbitAndShootGoal( 8, 0.5f));
        this.goalSelector.addGoal(0, new FleshSentinel.FleshSentinelRandomMoveGoal());
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    protected SoundEvent getAmbientSound(){
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.SKELETON_HURT;
    }

    public float getLightLevelDependentMagicValue(){
        return 1.0F;
    }

    @Override
    public void tick(){
        super.tick();
        if(cystSpawned && this.getBoundOrigin() != null){
            if(this.level().getBlockState(this.getBoundOrigin()).is(BlockRegistry.fleshCyst.get()) && !hasLimitedLife){
                this.setLimitedLife(1750);
            }
        }

        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    protected PathNavigation createNavigation(Level pLevel){
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos){
    }

    public void travel(Vec3 pTravelVector){
        if(this.isControlledByLocalInstance()){
            if(this.isInWater()){
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            }else if(this.isInLava()){
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            }else{
                BlockPos ground = getBlockPosBelowThatAffectsMyMovement();
                float f = 0.91F;
                if(this.onGround()){
                    f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if(this.onGround()){
                    f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
                }

                this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(f));
            }
        }

        this.calculateEntityAnimation(false);
    }

    /**
     * Returns {@code true} if this entity should move as if it were on a ladder (either because it's actually on a
     * ladder, or for AI reasons)
     */
    public boolean onClimbable(){
        return false;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getMyRidingOffset(){
        return 0.4D;
    }

    class FleshSentinelOrbitAndShootGoal extends Goal {
        private final double orbitRadius;
        private final double speed;
        private int attackCooldown;

        public FleshSentinelOrbitAndShootGoal(double orbitRadius, double speed) {
            this.orbitRadius = orbitRadius;
            this.speed = speed;
            this.attackCooldown = 0;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return FleshSentinel.this.getTarget() != null;
        }

        @Override
        public boolean canContinueToUse() {
            return FleshSentinel.this.getTarget() != null && FleshSentinel.this.getTarget().isAlive();
        }

        @Override
        public void tick() {
            LivingEntity target = FleshSentinel.this.getTarget();
            if (target == null) return;

            double dx = target.getX() - FleshSentinel.this.getX();
            double dz = target.getZ() - FleshSentinel.this.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            if (distance < orbitRadius * 0.8) {
                FleshSentinel.this.getMoveControl().setWantedPosition(FleshSentinel.this.getX() - dx / distance * orbitRadius, FleshSentinel.this.getY(), FleshSentinel.this.getZ() - dz / distance * orbitRadius, speed);
            } else if (distance > orbitRadius * 1.2) {
                FleshSentinel.this.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), speed);
            } else {
                double angle = Math.toRadians((FleshSentinel.this.tickCount % 360) * 4);
                double offsetX = Math.cos(angle) * orbitRadius;
                double offsetZ = Math.sin(angle) * orbitRadius;
                FleshSentinel.this.getMoveControl().setWantedPosition(target.getX() + offsetX, target.getY(), target.getZ() + offsetZ, speed);
            }

            FleshSentinel.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (attackCooldown > 0) {
                attackCooldown--;
            } else {
                shootAtTarget(target);
                attackCooldown = 40;
            }
        }

        private void shootAtTarget(LivingEntity target) {
            Vec3 direction = new Vec3(target.getX() - FleshSentinel.this.getX(), target.getEyeY() - FleshSentinel.this.getEyeY(), target.getZ() - FleshSentinel.this.getZ()).normalize();
            LaserEntity projectile = new LaserEntity(FleshSentinel.this.level(), FleshSentinel.this);
            projectile.setPos(FleshSentinel.this.getX(), FleshSentinel.this.getEyeY(), FleshSentinel.this.getZ());
            projectile.setDamage(4);
            projectile.shoot(direction.x, direction.y, direction.z, 1.5F, 1.0F);
            FleshSentinel.this.level().addFreshEntity(projectile);
        }
    }

    class FleshSentinelRandomMoveGoal extends Goal{
        public FleshSentinelRandomMoveGoal(){
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return FleshSentinel.this.getTarget() == null && !FleshSentinel.this.getMoveControl().hasWanted() && FleshSentinel.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            BlockPos blockpos = FleshSentinel.this.getBoundOrigin();
            if(blockpos == null){
                blockpos = FleshSentinel.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i){
                BlockPos blockpos1 = blockpos.offset(FleshSentinel.this.random.nextInt(15) - 7, FleshSentinel.this.random.nextInt(11) - 5, FleshSentinel.this.random.nextInt(15) - 7);
                if(FleshSentinel.this.level().isEmptyBlock(blockpos1)){
                    FleshSentinel.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    break;
                }
            }
        }
    }

    class UndeadMoveControl extends MoveControl{
        public UndeadMoveControl(FleshSentinel pFleshSentinel){
            super(pFleshSentinel);
        }

        public void tick(){
            if(this.operation == MoveControl.Operation.MOVE_TO){
                Vec3 vec3 = new Vec3(this.wantedX - FleshSentinel.this.getX(), this.wantedY - FleshSentinel.this.getY(), this.wantedZ - FleshSentinel.this.getZ());
                double d0 = vec3.length();
                if(d0 < FleshSentinel.this.getBoundingBox().getSize()){
                    this.operation = MoveControl.Operation.WAIT;
                    FleshSentinel.this.setDeltaMovement(FleshSentinel.this.getDeltaMovement().scale(0.5D));
                }else{
                    FleshSentinel.this.setDeltaMovement(FleshSentinel.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if(FleshSentinel.this.getTarget() == null){
                        Vec3 vec31 = FleshSentinel.this.getDeltaMovement();
                        FleshSentinel.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                    }else{
                        double d2 = FleshSentinel.this.getTarget().getX() - FleshSentinel.this.getX();
                        double d1 = FleshSentinel.this.getTarget().getZ() - FleshSentinel.this.getZ();
                        FleshSentinel.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                    }
                    FleshSentinel.this.yBodyRot = FleshSentinel.this.getYRot();
                }

            }
        }
    }
}
