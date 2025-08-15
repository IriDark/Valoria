package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.goals.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.api.entity.*;
import pro.komaru.tridot.common.registry.entity.*;

import javax.annotation.Nullable;
import java.util.*;

public class Ent extends MultiAttackMob implements NeutralMob, Enemy{
    public final AnimationState idleAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();

    @Nullable
    public UUID persistentAngerTarget;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(80, 250);

    public Ent(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.xpReward = 15;
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

    @Override
    public void performMeleeAttack(){
        this.navigation.stop();
        super.performMeleeAttack();
    }

    public void aiStep(){
        this.updateSwingTime();
        if(!this.level().isClientSide && level() instanceof ServerLevel serverLevel){
            this.updatePersistentAnger(serverLevel, true);
        }

        super.aiStep();
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > (double)2.5000003E-7F && this.random.nextInt(5) == 0;
    }

    public int getRemainingPersistentAngerTime(){
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pTime){
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    public void startPersistentAngerTimer(){
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Nullable
    public UUID getPersistentAngerTarget(){
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID pTarget){
        this.persistentAngerTarget = pTarget;
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
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

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity){
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;

        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f1);
        if (flag) {
            double d2;
            if (pEntity instanceof LivingEntity livingentity) {
                d2 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d2 = 0.0D;
            }

            double d0 = d2;
            double d1 = Math.max(0.0D, 1.0D - d0);
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(0.0D, (double)0.2F * d1, 0.0D));
            this.doEnchantDamageEffects(this, pEntity);
        }

        return flag;
    }

    public boolean canDisableShield() {
        return true;
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

    public SoundSource getSoundSource(){
        return SoundSource.HOSTILE;
    }

    protected void doPush(@NotNull Entity pEntity){
    }

    @Override
    public void knockback(double strength, double x, double z){
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
        this.goalSelector.addGoal(1, new DashAttackGoal(this, 0.65f));
        this.goalSelector.addGoal(1, new EntAttackGoal(this, 1));


        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public class EntAttackGoal extends TridotMeleeAttackGoal{
        public EntAttackGoal(MultiAttackMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        @Override
        public int attackAnimationTick(){
            return 40;
        }

        @Override
        public void onPrepare() {
            super.onPrepare();
            Ent.this.level().broadcastEntityEvent(Ent.this, (byte)4);
        }

        @Override
        public int getPreparingTime() {
            return 40;
        }

        @Override
        public int getAttackInterval() {
            return 40;
        }

        @Override
        public SoundEvent getPrepareSound() {
            return SoundEvents.IRON_GOLEM_ATTACK;
        }

        @Override
        public SoundEvent getAttackSound() {
            return SoundEvents.PLAYER_ATTACK_STRONG;
        }

        @Override
        public AttackRegistry getAttack() {
            return EntityStatsRegistry.MELEE;
        }
    }
}
