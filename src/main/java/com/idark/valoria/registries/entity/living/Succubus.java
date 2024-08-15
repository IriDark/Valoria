package com.idark.valoria.registries.entity.living;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.brains.*;
import com.idark.valoria.registries.entity.ai.sensing.*;
import com.mojang.serialization.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.ai.sensing.*;
import net.minecraft.world.entity.ai.util.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;

public class Succubus extends Monster{
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(Succubus.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public AnimationState fireballAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    protected static final ImmutableList<SensorType<? extends Sensor<? super Succubus>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorTypes.SUCCUBUS_SPECIFIC_SENSOR.get());
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.HOME);
    public Succubus(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 5;
        this.getNavigation().setCanFloat(false);
        applyOpenDoorsAbility();
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
    }

    private void applyOpenDoorsAbility() {
        if (GoalUtils.hasGroundPathNavigation(this)) {
            ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        }
    }

    public void setAttackTarget(LivingEntity pAttackTarget) {
        this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, pAttackTarget);
        this.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    public boolean doHurtTarget(Entity pEntity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        this.playSound(SoundEvents.WARDEN_ATTACK_IMPACT, 10.0F, this.getVoicePitch());
        if(pEntity instanceof LivingEntity){
            ((LivingEntity)pEntity).addEffect(new MobEffectInstance(EffectsRegistry.BLEEDING.get(), 200), this);
        }


        return super.doHurtTarget(pEntity);
    }

    public void handleEntityEvent(byte pId) {
//        if (pId == 4) {
//            this.attackAnimationState.start(this.tickCount);
//        if (pId == 62) {
//            this.fireballAnimationState.start(this.tickCount);
//        } else {
            super.handleEntityEvent(pId);
//        } todo
    }

    public boolean isAdult() {
        return !this.isBaby();
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
        .add(Attributes.MOVEMENT_SPEED, 0.25)
        .add(Attributes.MAX_HEALTH, 40.0D)
        .add(Attributes.ATTACK_DAMAGE, 6.0D)
        .add(Attributes.FOLLOW_RANGE, 4.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(17) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    protected void customServerAiStep() {
        this.level().getProfiler().push("succubusBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        SuccubusAI.maybePlayActivitySound(this);
        SuccubusAI.updateActivity(this);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        if (!this.level().isClientSide && !this.isNoAi()) {
            Entity entity = pSource.getEntity();
            if (flag && pSource.getEntity() instanceof LivingEntity) {
                SuccubusAI.wasHurtBy(this, (LivingEntity)pSource.getEntity());
            }

            if (this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty() && entity instanceof LivingEntity livingentity) {
                if (!pSource.isIndirect() || this.closerThan(livingentity, 5.0D)) {
                    this.setAttackTarget(livingentity);
                }
            }
        }

        return flag;
    }

    protected Brain.Provider<Succubus> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> pDynamic) {
        return SuccubusAI.makeBrain(this, this.brainProvider().makeBrain(pDynamic));
    }

    public Brain<Succubus> getBrain() {
        return (Brain<Succubus>) super.getBrain();
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    public MobType getMobType(){
        return MobType.UNDEAD;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_BABY_ID, false);
    }

    public boolean isBaby() {
        return this.getEntityData().get(DATA_BABY_ID);
    }

    public void setBaby(boolean child) {
        this.getEntityData().set(DATA_BABY_ID, child);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BABY_ID.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public float getScale(){
        return this.isBaby() ? 1f : 1.45F;
    }

    public int getExperienceReward() {
        if (this.isBaby()) {
            this.xpReward = this.xpReward / 2;
        }

        return super.getExperienceReward();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsBaby", this.isBaby());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setBaby(pCompound.getBoolean("IsBaby"));
    }

    @Contract("null->false")
    public boolean canTargetEntity(@Nullable Entity p_219386_) {
        if (p_219386_ instanceof LivingEntity livingentity) {
            return this.level() == p_219386_.level() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_219386_) && !this.isAlliedTo(p_219386_) && livingentity.getType() != EntityType.ARMOR_STAND && livingentity.getType() != EntityType.WARDEN && !livingentity.isInvulnerable() && !livingentity.isDeadOrDying() && this.level().getWorldBorder().isWithinBounds(livingentity.getBoundingBox());
        }

        return false;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        SuccubusAI.initMemories(this);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected SoundEvent getAmbientSound() {
        if (this.level().isClientSide) {
            return null;
        } else {
            return this.brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET) ? SoundEvents.ZOGLIN_ANGRY : SoundEvents.ZOGLIN_AMBIENT;
        }
    }

    public void playAngrySound() {
        this.playSound(SoundEvents.WARDEN_LISTENING_ANGRY, 1.0F, this.getVoicePitch());
    }

    protected boolean canRide(Entity pVehicle) {
        return false;
    }
}
