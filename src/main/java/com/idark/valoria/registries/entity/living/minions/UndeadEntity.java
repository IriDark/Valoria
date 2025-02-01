package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.entity.living.boss.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.registry.entity.*;

import javax.annotation.*;
import java.util.*;

public class UndeadEntity extends AbstractMinionEntity{
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(UndeadEntity.class, EntityDataSerializers.BYTE);

    public UndeadEntity(EntityType<? extends UndeadEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.moveControl = new UndeadMoveControl(this);
        this.xpReward = 3;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions){
        return pDimensions.height - 0.28125F;
    }

    public boolean isFlapping(){
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    @Override
    public void spawnDisappearParticles(ServerLevel serverLevel){
        double posX = this.getOnPos().getCenter().x;
        double posY = this.getOnPos().above().getCenter().y;
        double posZ = this.getOnPos().getCenter().z;
        PacketHandler.sendToTracking(serverLevel, this.getOnPos(), new SmokeParticlePacket(3, posX, posY - 0.5f, posZ, 0.005f, 0.025f, 0.005f, 255, 255, 255));
    }

    public void spawnParticlesTrail(){
        if(this.isCharging()){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for(int a = 0; a < 0.05f; ++a){
                this.level().addParticle(ParticleTypes.SCRAPE, this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);
            }
        }
    }

    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(0, new CopyOwnerTargetGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(0, new UndeadEntityRandomMoveGoal());
        this.goalSelector.addGoal(1, new UndeadEntity.UndeadChargeAttackGoal());
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, NecromancerEntity.class)).setAlertOthers());
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    private boolean getUndeadFlag(int pMask){
        int i = this.entityData.get(DATA_FLAGS_ID);
        return (i & pMask) != 0;
    }

    private void setUndeadFlag(int pMask, boolean pValue){
        int i = this.entityData.get(DATA_FLAGS_ID);
        if(pValue){
            i |= pMask;
        }else{
            i &= ~pMask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
    }

    public boolean isCharging(){
        return this.getUndeadFlag(1);
    }

    public void setIsCharging(boolean pCharging){
        this.setUndeadFlag(1, pCharging);
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty){
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
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
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
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
                this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
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

    class UndeadEntityRandomMoveGoal extends Goal{
        public UndeadEntityRandomMoveGoal(){
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return UndeadEntity.this.getTarget() == null && !UndeadEntity.this.getMoveControl().hasWanted() && UndeadEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
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
            BlockPos blockpos = UndeadEntity.this.getBoundOrigin();
            if(blockpos == null){
                blockpos = UndeadEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i){
                BlockPos blockpos1 = blockpos.offset(UndeadEntity.this.random.nextInt(15) - 7, UndeadEntity.this.random.nextInt(11) - 5, UndeadEntity.this.random.nextInt(15) - 7);
                if(UndeadEntity.this.level().isEmptyBlock(blockpos1)){
                    UndeadEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    break;
                }
            }
        }
    }

    class UndeadMoveControl extends MoveControl{
        public UndeadMoveControl(UndeadEntity pUndeadEntity){
            super(pUndeadEntity);
        }

        public void tick(){
            if(this.operation == MoveControl.Operation.MOVE_TO){
                Vec3 vec3 = new Vec3(this.wantedX - UndeadEntity.this.getX(), this.wantedY - UndeadEntity.this.getY(), this.wantedZ - UndeadEntity.this.getZ());
                double d0 = vec3.length();
                if(d0 < UndeadEntity.this.getBoundingBox().getSize()){
                    this.operation = MoveControl.Operation.WAIT;
                    UndeadEntity.this.setDeltaMovement(UndeadEntity.this.getDeltaMovement().scale(0.5D));
                }else{
                    UndeadEntity.this.setDeltaMovement(UndeadEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if(UndeadEntity.this.getTarget() == null){
                        Vec3 vec31 = UndeadEntity.this.getDeltaMovement();
                        UndeadEntity.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                    }else{
                        double d2 = UndeadEntity.this.getTarget().getX() - UndeadEntity.this.getX();
                        double d1 = UndeadEntity.this.getTarget().getZ() - UndeadEntity.this.getZ();
                        UndeadEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                    }
                    UndeadEntity.this.yBodyRot = UndeadEntity.this.getYRot();
                }

            }
        }
    }

    class UndeadChargeAttackGoal extends MeleeAttackGoal{
        public UndeadChargeAttackGoal(){
            super(UndeadEntity.this, 1, true);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected int getAttackInterval(){
            return this.adjustedTickDelay(60);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse(){
            return UndeadEntity.this.getMoveControl().hasWanted() && UndeadEntity.this.getTarget() != null && UndeadEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start(){
            super.start();
            LivingEntity livingentity = UndeadEntity.this.getTarget();
            if(livingentity != null){
                Vec3 vec3 = livingentity.getEyePosition();
                UndeadEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            UndeadEntity.this.setIsCharging(true);
            UndeadEntity.this.playSound(SoundEvents.SKELETON_CONVERTED_TO_STRAY, 1.0F, 1.0F);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop(){
            super.stop();
            UndeadEntity.this.setIsCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick(){
            super.tick();
            LivingEntity livingentity = UndeadEntity.this.getTarget();
            if(livingentity != null){
                if(UndeadEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())){
                    UndeadEntity.this.doHurtTarget(livingentity);
                    UndeadEntity.this.setIsCharging(false);
                    UndeadEntity.this.setDeltaMovement((UndeadEntity.this.getX() - UndeadEntity.this.getTarget().getX()) * 3, (UndeadEntity.this.getY() - UndeadEntity.this.getTarget().getY()) * 0.5, (UndeadEntity.this.getTarget().getZ() - UndeadEntity.this.getZ()) * 3);
                }else{
                    double d0 = UndeadEntity.this.distanceToSqr(livingentity);
                    if(d0 < UndeadEntity.this.getAttributeValue(Attributes.FOLLOW_RANGE)){
                        Vec3 vec3 = livingentity.getEyePosition();
                        UndeadEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }
                }
            }
        }
    }
}