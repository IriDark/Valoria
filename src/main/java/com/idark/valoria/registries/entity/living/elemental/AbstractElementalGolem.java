package com.idark.valoria.registries.entity.living.elemental;

import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.registries.*;
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
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.gfx.particle.options.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.common.registry.entity.system.*;

import java.util.*;

public class AbstractElementalGolem extends PathfinderMob implements NeutralMob, Enemy, AttackSystemMob, IEffectiveWeaponEntity{
    public final AttackSelector selector = new AttackSelector();
    private AttackInstance currentAttack;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackSlapAnimationState = new AnimationState();
    public final AnimationState groundPunchAnimationState = new AnimationState();
    public final AnimationState stompAttackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Nullable
    public UUID persistentAngerTarget;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(AbstractElementalGolem.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 60);

    public AbstractElementalGolem(EntityType<? extends PathfinderMob> type, Level pLevel){
        super(type, pLevel);
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    @Override
    public TagKey<Item> getEffective(){
        return ItemTags.PICKAXES;
    }

    public void aiStep(){
        this.tickCooldowns();
        this.updateSwingTime();
        if(!this.level().isClientSide && level() instanceof ServerLevel serverLevel){
            this.updatePersistentAnger(serverLevel, true);
        }

        super.aiStep();
    }

    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistanceSqr() > (double)2.5000003E-7F && this.random.nextInt(5) == 0;
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        this.playSound(SoundsRegistry.ELEMENTAL_GOLEM_STEP.get(), 1, 1);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundsRegistry.ELEMENTAL_GOLEM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound(){
        return SoundsRegistry.ELEMENTAL_GOLEM_DEATH.get();
    }

    @Override
    public void spawnHitParticles(Level level, BlockPos blockPos){
        BlockState state = Blocks.STONE.defaultBlockState();
        var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), state);
        ParticleBuilder.create(opt)
        .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
        .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
        .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
        .setLifetime(30)
        .randomVelocity(0.35, 0.65, 0.35)
        .randomOffset(0.125, 0.125)
        .setGravity(0.75f)
        .repeat(level, blockPos.getX() + 0.5f, blockPos.getY() + 1, blockPos.getZ() + 0.5f, 12);
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 3) {
            var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), Blocks.STONE.defaultBlockState());

            ParticleBuilder.create(opt)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
            .setScaleData(GenericParticleData.create(0.45f, 0.02f, 0).build())
            .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
            .setLifetime(30)
            .randomVelocity(0.45, 0.75, 0.45)
            .randomOffset(0.065, 0.065)
            .setGravity(0.75f)
            .repeat(this.level(), this.getX(), this.getY(), this.getZ(), 32);
        } else if(pId == 4){
            this.attackAnimationState.start(this.tickCount);
        } else if(pId == 61){
            this.stompAttackAnimationState.start(this.tickCount);

            if(this.level().getBlockState(this.blockPosition().below()).isAir()) return;
            var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), this.level().getBlockState(this.blockPosition().below()));

            ParticleBuilder.create(opt)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
            .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
            .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
            .setLifetime(30)
            .randomVelocity(0.45, 0.75, 0.45)
            .randomOffset(0.125, 0.125)
            .setGravity(0.75f)
            .repeat(this.level(), this.getX(), this.getY(), this.getZ(), 64);
        }else if(pId == 62){
            this.attackSlapAnimationState.start(this.tickCount);
        }else if(pId == 64){
            this.groundPunchAnimationState.start(this.tickCount);

            if(this.level().getBlockState(this.blockPosition().below()).isAir()) return;
            var opt = new BlockParticleOptions(TridotParticles.BLOCK.get(), this.level().getBlockState(this.blockPosition().below()));

            ParticleBuilder.create(opt)
            .setRenderType(TridotRenderTypes.TRANSLUCENT_BLOCK_PARTICLE)
            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
            .setScaleData(GenericParticleData.create(0.15f, 0.02f, 0).build())
            .setSpriteData(SpriteParticleData.CRUMBS_RANDOM)
            .setLifetime(30)
            .randomVelocity(0.35, 0.65, 0.35)
            .randomOffset(0.125, 0.125)
            .setGravity(0.75f)
            .repeat(this.level(), this.getX(), this.getY(), this.getZ(), 128);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ExecuteAttackGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
        this.writeAttackInfo(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
        this.readAttackInfo(pCompound);
    }

    @Override
    public AttackSelector getAttackSelector(){
        return selector;
    }

    @Override
    public AttackInstance getActiveAttack(){
        return currentAttack;
    }

    @Override
    public void setActiveAttack(AttackInstance attackInstance){
        currentAttack = attackInstance;
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
}