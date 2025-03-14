package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.entity.ai.goals.*;
import com.mojang.logging.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.gameevent.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.api.entity.ai.goals.*;

public class ScourgeEntity extends SwampWandererEntity{
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public AnimationState diggingAnimationState = new AnimationState();
    public AnimationState deathAnimationState = new AnimationState();
    private int spawnTime = 0;
    public int deathTime = 0;
    public ScourgeEntity(EntityType<? extends ScourgeEntity> type, Level pLevel){
        super(type, pLevel);
    }

    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }

        if(this.spawnTime < 60){
            this.spawnTime++;
            if(this.onGround() && this.getPose() != Pose.DIGGING) {
                this.setPose(Pose.DIGGING);
                this.playSound(SoundEvents.WARDEN_DIG, 5.0F, 1.0F);
            }
        }

        if(this.getPose() == Pose.DIGGING) {
            this.clientDiggingParticles(this.diggingAnimationState);
        }
    }

    protected boolean isSunSensitive() {
        return false;
    }

    public boolean doHurtTarget(Entity pEntity) {
        return super.doHurtTarget(pEntity);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }
    }

    public void handleEntityEvent(byte pId){
        if(pId == 4){
            this.attackAnimationState.start(this.tickCount);
        }
        super.handleEntityEvent(pId);
    }

    protected void addBehaviourGoals(){
        this.goalSelector.addGoal(1, new DashAttackGoal(this, 1.0f));
        this.goalSelector.addGoal(1, new DrownedAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new SwampWandererEntity.DrownedGoToBeachGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ScourgeEntity.class));
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

    @Override
    protected void tickDeath(){
        ++this.deathTime;
        if (this.deathTime >= 60 && !this.level().isClientSide() && !this.isRemoved()) {
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    public boolean isInvulnerableTo(DamageSource pSource) {
        return spawnTime < 60 && !pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || super.isInvulnerableTo(pSource);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_POSE.equals(pKey)) {
            if(this.getPose() == Pose.DIGGING){
                this.diggingAnimationState.start(this.tickCount);
            }
        }

        super.onSyncedDataUpdated(pKey);
    }

    private void clientDiggingParticles(AnimationState pAnimationState) {
        if ((float)pAnimationState.getAccumulatedTime() < 4500.0F) {
            RandomSource randomsource = this.getRandom();
            BlockState blockstate = this.getBlockStateOn();
            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                for(int i = 0; i < 10; ++i) {
                    double d0 = this.getX() + (double)Mth.randomBetween(randomsource, -0.7F, 0.7F);
                    double d1 = this.getY();
                    double d2 = this.getZ() + (double)Mth.randomBetween(randomsource, -0.7F, 0.7F);
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void die(DamageSource pDamageSource){
        if(net.minecraftforge.common.ForgeHooks.onLivingDeath(this, pDamageSource)) return;
        if(!this.isRemoved() && !this.dead){
            Entity entity = pDamageSource.getEntity();
            LivingEntity livingentity = this.getKillCredit();
            if(this.deathScore >= 0 && livingentity != null){
                livingentity.awardKillScore(this, this.deathScore, pDamageSource);
            }

            if(this.isSleeping()){
                this.stopSleeping();
            }

            if(!this.level().isClientSide && this.hasCustomName()){
                LogUtils.getLogger().info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
            }

            this.dead = true;
            this.getCombatTracker().recheckStatus();
            Level level = this.level();
            if(level instanceof ServerLevel serverlevel){
                if(entity == null || entity.killedEntity(serverlevel, this)){
                    this.gameEvent(GameEvent.ENTITY_DIE);
                    this.dropAllDeathLoot(pDamageSource);
                }

                this.level().broadcastEntityEvent(this, (byte)3);
            }
        }

        this.deathAnimationState.start(tickCount);
        int maggotCount = 6 + random.nextInt(2);
        if(!this.level().isClientSide){
            for(int i = 0; i < maggotCount; i++){
                MaggotEntity maggot = new MaggotEntity(this.level());
                Vec3 mobPos = this.position();
                double offsetX = (level().random.nextDouble() - 0.5) * 2.0;
                double offsetY = level().random.nextDouble() * 0.5 + 0.35;
                double offsetZ = (level().random.nextDouble() - 0.5) * 2.0;

                maggot.moveTo(mobPos.x, mobPos.y + 0.5, mobPos.z, level().random.nextFloat() * 360F, 0.0F);
                maggot.setDeltaMovement(new Vec3(offsetX, offsetY, offsetZ));
                level().addFreshEntity(maggot);
            }
        }
    }

    private int attackAnimationTick;
    public void aiStep(){
        super.aiStep();
        if(this.attackAnimationTick > 0){
            --this.attackAnimationTick;
        }
    }

    static class DrownedAttackGoal extends DelayedMeleeAttackGoal{
        private final ScourgeEntity drowned;

        public DrownedAttackGoal(ScourgeEntity pDrowned, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen){
            super(pDrowned, pSpeedModifier, 60, pFollowingTargetEvenIfNotSeen);
            this.drowned = pDrowned;
        }

        @Override
        public void start(){
            this.drowned.level().broadcastEntityEvent(drowned, (byte)4);
            this.drowned.attackAnimationTick = 15;
            super.start();
        }

        public void tick() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.mob.getRandom().nextFloat() < 0.05F)) {
                    this.pathedTargetX = livingentity.getX();
                    this.pathedTargetY = livingentity.getY();
                    this.pathedTargetZ = livingentity.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                    if (d0 > 1024.0) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                if(this.drowned.attackAnimationTick < 3){
                    this.ticksUntilNextAttack = Math.max(this.getTicksUntilNextAttack() - 1, 0);
                    this.checkAndPerformAttack(livingentity, d0);
                }
            }
        }

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
}