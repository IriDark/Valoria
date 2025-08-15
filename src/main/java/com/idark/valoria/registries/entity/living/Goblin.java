package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.goals.RemoveBlockGoal;
import com.idark.valoria.registries.entity.ai.goals.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;

public class Goblin extends AbstractGoblin{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public ArcRandom arcRandom = Tmp.rnd;

    public Goblin(EntityType<? extends PathfinderMob> type, Level worldIn){
        super(type, worldIn);
        this.xpReward = 5;
    }

    public boolean doHurtTarget(Entity pEntity){
        this.level().broadcastEntityEvent(this, (byte)4);
        return super.doHurtTarget(pEntity);
    }

    protected float getSoundVolume(){
        return 0.45F;
    }

    public float getVoicePitch(){
        return this.isBaby() ? (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F : (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.25F;
    }

    protected SoundEvent getAmbientSound(){
        return SoundsRegistry.GOBLIN_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundsRegistry.GOBLIN_HURT.get();
    }

    protected SoundEvent getDeathSound(){
        return SoundsRegistry.GOBLIN_DEATH.get();
    }

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()){
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty){
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if(arcRandom.chance(0.3f)){
            this.setItemSlot(EquipmentSlot.MAINHAND, goblinCanSpawnWith.get(pRandom.nextInt(0, goblinCanSpawnWith.size())).getDefaultInstance());
        }
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos){
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    public static boolean checkGoblinSpawnRules(EntityType<Goblin> pGoblin, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
    }

    @Override
    public void handleEntityEvent(byte pId){
        if(pId == 4){
            this.attackAnimationState.start(this.tickCount);
        }else{
            super.handleEntityEvent(pId);
        }
    }

    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, true, (p_28604_) -> p_28604_ instanceof Chicken || p_28604_ instanceof Rabbit || p_28604_ instanceof Pig));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractFish.class, 20, true, true, (p_28600_) -> p_28600_ instanceof AbstractSchoolingFish));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, true));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));

        this.goalSelector.addGoal(0, new AvoidStrongEntityGoal<>(this, Player.class, 16, 1.2, 1.6));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Wolf.class, 8, 1.6, 1.4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Creeper.class, 12, 1.8, 1.4));
        this.goalSelector.addGoal(0, new SearchForItemsGoal(this, ALLOWED_ITEMS));
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new ReasonableAvoidEntityGoal<>(this, Player.class, 15, 1.25, 2, isLowHP()));
        this.goalSelector.addGoal(0, new ReasonablePanicGoal(this, 2f, isLowHP()));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.2));
        this.goalSelector.addGoal(3, new CollectBerriesGoal(this, 1.2, 12, 4));
        this.goalSelector.addGoal(6, new RemoveBlockGoal(Blocks.FARMLAND, this, 1.4, 10));
        this.goalSelector.addGoal(6, new RemoveCropsGoal(this, 1.2, 10));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.2, 6, 10));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
}