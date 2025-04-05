package com.idark.valoria.registries.entity.living.minions;

import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.entity.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;
import java.util.*;

public class PixieEntity extends AbstractMinionEntity{
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(Guardian.class, EntityDataSerializers.INT);
    private LivingEntity clientSideCachedAttackTarget;
    private int clientSideHealTime;

    public PixieEntity(EntityType<? extends PixieEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this,20, true);
        this.xpReward = 1;
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
                this.moveRelative(this.getSpeed(), pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.91F));
            }
        }

        this.calculateEntityAnimation(false);
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

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
    }

    public float getHealAnimationScale(float pPartialTick) {
        return ((float)this.clientSideHealTime + pPartialTick) / (float)this.getHealDuration();
    }

    public float getClientSideHealTime() {
        return (float)this.clientSideHealTime;
    }

    void setActiveAttackTarget(int pActiveAttackTargetId) {
        this.entityData.set(DATA_ID_ATTACK_TARGET, pActiveAttackTargetId);
    }

    public boolean hasActiveOwner() {
        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
    }

    @Nullable
    public LivingEntity getActiveAttackTarget() {
        if (!this.hasActiveOwner()) {
            return null;
        } else if (this.level().isClientSide) {
            if (this.clientSideCachedAttackTarget != null) {
                return this.clientSideCachedAttackTarget;
            } else {
                Entity entity = this.level().getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
                if (entity instanceof LivingEntity) {
                    this.clientSideCachedAttackTarget = (LivingEntity)entity;
                    return this.clientSideCachedAttackTarget;
                } else {
                    return null;
                }
            }
        } else {
            return this.getTarget();
        }
    }

    @Override
    public void aiStep(){
        super.aiStep();
        if(this.isAlive()){
            if(this.level().isClientSide){
                if(this.hasActiveOwner()){
                    if(this.clientSideHealTime < this.getHealDuration()){
                        ++this.clientSideHealTime;
                    }

                    LivingEntity livingentity = this.getActiveAttackTarget();
                    if(livingentity != null){
                        this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
                        this.getLookControl().tick();
                        double d5 = (double)this.getHealAnimationScale(0.0F);
                        double d0 = livingentity.getX() - this.getX();
                        double d1 = livingentity.getY(0.5D) - this.getEyeY();
                        double d2 = livingentity.getZ() - this.getZ();
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        double d4 = this.random.nextDouble();

                        while(d4 < d3 && Tmp.rnd.chance(0.45)){
                            d4 += 1.8D - d5 + this.random.nextDouble() * (1.7D - d5);
                            ParticleBuilder.create(TridotParticles.TINY_WISP)
                            .setColorData(ColorParticleData.create(Pal.nature, Pal.oceanic).build())
                            .setScaleData(GenericParticleData.create(0.1f).build())
                            .spawn(this.level(), getX() + d0 * d4, this.getEyeY() + d1 * d4, this.getZ() + d2 * d4);
                        }
                    }
                }

                if (this.hasActiveOwner()) {
                    this.setYRot(this.yHeadRot);
                }
            }
        }
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (DATA_ID_ATTACK_TARGET.equals(pKey)) {
            this.clientSideHealTime = 0;
            this.clientSideCachedAttackTarget = null;
        }

    }

    public void spawnParticlesTrail(){
        if(Tmp.rnd.chance(0.15f)){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for(int a = 0; a < 0.05f; ++a){
                ParticleBuilder.create(TridotParticles.WISP)
                .setColorData(ColorParticleData.create(Pal.nature, Pal.oceanic).build())
                .randomVelocity(0.15f)
                .setScaleData(GenericParticleData.create(0.1f).build())
                .spawn(this.level(), this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D);
            }
        }
    }

    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(0, new HealOwnerGoal());
        this.goalSelector.addGoal(0, new RandomMoveGoal());
    }

    protected SoundEvent getAmbientSound(){
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.ALLAY_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.ALLAY_HURT;
    }

    public float getLightLevelDependentMagicValue(){
        return 1.0F;
    }

    protected PathNavigation createNavigation(Level pLevel){
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos){
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

    public int getHealDuration() {
        return 80;
    }

    class HealOwnerGoal extends Goal {
        private int healTime;

        @Override
        public boolean canUse(){
            return owner != null && owner.isAlive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (owner != null && PixieEntity.this.distanceToSqr(owner) > 9.0D);
        }

        public void start() {
            this.healTime = -10;
            PixieEntity.this.getNavigation().stop();
            LivingEntity livingentity = owner;
            if (livingentity != null) {
                PixieEntity.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
            }

            PixieEntity.this.hasImpulse = true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }


        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            PixieEntity.this.setActiveAttackTarget(0);
        }
        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = owner;
            if (livingentity != null) {
                PixieEntity.this.getNavigation().stop();
                PixieEntity.this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
                if(PixieEntity.this.hasLineOfSight(livingentity)) {
                    ++this.healTime;
                    if (this.healTime == 0){
                        PixieEntity.this.setActiveAttackTarget(livingentity.getId());
                    } else if (this.healTime >= PixieEntity.this.getHealDuration()) {
                        if (this.healTime % 20 == 0) {
                            float f = 1f;
                            if (PixieEntity.this.level().getDifficulty() == Difficulty.HARD) {
                                f += 5f;
                            }
                            livingentity.heal(f);
                        }
                    }

                    super.tick();
                }
            }
        }
    }

    class RandomMoveGoal extends Goal{
        public RandomMoveGoal(){
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse(){
            return PixieEntity.this.getTarget() == null && !PixieEntity.this.getMoveControl().hasWanted() && PixieEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
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
            BlockPos blockpos = PixieEntity.this.getBoundOrigin();
            if(blockpos == null){
                blockpos = PixieEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i){
                BlockPos blockpos1 = blockpos.offset(PixieEntity.this.random.nextInt(15) - 7, PixieEntity.this.random.nextInt(7) - 5, PixieEntity.this.random.nextInt(15) - 7);
                if(PixieEntity.this.level().isEmptyBlock(blockpos1)){
                    PixieEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    break;
                }
            }
        }
    }
}