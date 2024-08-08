package com.idark.valoria.registries.entity.living;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;

public class ShadewoodSpider extends Monster{
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(ShadewoodSpider.class, EntityDataSerializers.BYTE);

    public ShadewoodSpider(EntityType<? extends ShadewoodSpider> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.MOVEMENT_SPEED, 0.4F);
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getPassengersRidingOffset(){
        return this.getBbHeight() * 0.5F;
    }

    protected PathNavigation createNavigation(Level pLevel){
        return new WallClimberNavigation(this, pLevel);
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick(){
        super.tick();
        if(!this.level().isClientSide){
            this.setClimbing(this.horizontalCollision);
        }
    }

    protected SoundEvent getAmbientSound(){
        return SoundEvents.SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.SPIDER_HURT;
    }

    protected SoundEvent getDeathSound(){
        return SoundEvents.SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pPos, BlockState pBlock){
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    /**
     * Returns {@code true} if this entity should move as if it were on a ladder (either because it's actually on a
     * ladder, or for AI reasons)
     */
    public boolean onClimbable(){
        return this.isClimbing();
    }

    public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier){
        if(!pState.is(Blocks.COBWEB)){
            super.makeStuckInBlock(pState, pMotionMultiplier);
        }
    }

    public MobType getMobType(){
        return MobType.ARTHROPOD;
    }

    public boolean canBeAffected(MobEffectInstance pPotioneffect){
        if(pPotioneffect.getEffect() == MobEffects.POISON){
            net.minecraftforge.event.entity.living.MobEffectEvent.Applicable event = new net.minecraftforge.event.entity.living.MobEffectEvent.Applicable(this, pPotioneffect);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.canBeAffected(pPotioneffect);
    }

    /**
     * Returns {@code true} if the WatchableObject (Byte) is 0x01 otherwise returns {@code false}. The WatchableObject is
     * updated using setBesideClimbableBlock.
     */
    public boolean isClimbing(){
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setClimbing(boolean pClimbing){
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if(pClimbing){
            b0 = (byte)(b0 | 1);
        }else{
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();
        if(randomsource.nextInt(100) == 0){
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if(skeleton != null){
                skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skeleton.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
                skeleton.startRiding(this);
            }
        }

        if(pSpawnData == null){
            pSpawnData = new Spider.SpiderEffectsGroupData();
            if(pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()){
                ((Spider.SpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
            }
        }

        if(pSpawnData instanceof Spider.SpiderEffectsGroupData spider$spidereffectsgroupdata){
            MobEffect mobeffect = spider$spidereffectsgroupdata.effect;
            if(mobeffect != null){
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return pSpawnData;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
        return 0.65F;
    }
}