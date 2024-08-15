package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.ai.goals.RemoveBlockGoal;
import com.idark.valoria.registries.entity.ai.goals.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class GoblinEntity extends PathfinderMob implements NeutralMob, Enemy{
    public static List<Item> goblinCanSpawnWith = new ArrayList<>();
    private final SimpleContainer inventory = new SimpleContainer(8);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(GoblinEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(GoblinEntity.class, EntityDataSerializers.BOOLEAN);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false){
        public void stop(){
            super.stop();
            GoblinEntity.this.setAggressive(false);
        }

        public void start(){
            super.start();
            GoblinEntity.this.setAggressive(true);
        }
    };

    public GoblinEntity(EntityType<? extends PathfinderMob> type, Level worldIn){
        super(type, worldIn);
        this.xpReward = 3;
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public static void spawnable(Item... T){
        Collections.addAll(goblinCanSpawnWith, T);
    }

    @Nullable
    private UUID persistentAngerTarget;
    private int ticksSinceEaten;
    static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_289438_) -> !p_289438_.hasPickUpDelay() && p_289438_.isAlive() || p_289438_.getItem().isEdible() || p_289438_.getItem() == Items.GOLD_INGOT.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_BLOCK.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_NUGGET.getDefaultInstance() || p_289438_.getItem() == ItemsRegistry.SAMURAI_KUNAI.get().getDefaultInstance() || p_289438_.getItem() == ItemsRegistry.SAMURAI_POISONED_KUNAI.get().getDefaultInstance() || p_289438_.getItem().getItem() instanceof SwordItem;

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter pLevel, BlockPos pPos){
        return pLevel.getRawBrightness(pPos, 0) > 8;
    }

    public static boolean checkGoblinSpawnRules(EntityType<GoblinEntity> pGoblin, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0D)
        .add(Attributes.MOVEMENT_SPEED, 0.18D)
        .add(Attributes.ATTACK_DAMAGE, 2.0D)
        .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    public int getExperienceReward(){
        if(this.isBaby()){
            this.xpReward = (int)((double)this.xpReward * 2.5D);
        }

        return super.getExperienceReward();
    }

    @VisibleForDebug
    public SimpleContainer getInventory(){
        return this.inventory;
    }

    public void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit){
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        this.inventory.removeAllItems().forEach(this::spawnAtLocation);
    }

    public boolean shouldDespawnInPeaceful(){
        return false;
    }

    private boolean canEat(ItemStack pStack){
        return pStack.getItem().isEdible() && this.getTarget() == null && this.onGround() && !this.isSleeping();
    }

    public boolean wantsToPickUp(ItemStack pStack){
        Item item = pStack.getItem();
        return item.isEdible() || item == ItemsRegistry.SAMURAI_KUNAI.get() || item == ItemsRegistry.SAMURAI_POISONED_KUNAI.get() || item == Items.GOLD_INGOT || item == Items.GOLD_BLOCK || item == Items.GOLD_NUGGET || item instanceof SwordItem && this.getInventory().canAddItem(pStack);
    }

    public void aiStep(){
        if(!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()){
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if(this.canEat(itemstack)){
                if(this.ticksSinceEaten > 600){
                    ItemStack foodStack = itemstack.finishUsingItem(this.level(), this);
                    if(!foodStack.isEmpty()){
                        this.setItemSlot(EquipmentSlot.MAINHAND, foodStack);
                    }

                    this.ticksSinceEaten = 0;
                }else if(this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F){
                    this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                    this.heal(1.25F);
                    this.level().broadcastEntityEvent(this, (byte)45);
                }
            }
        }

        if(!this.level().isClientSide){
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }

        if(this.getHealth() < 12){
            this.stopBeingAngry();
        }

        super.aiStep();
    }

    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty){
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        if(RandomUtil.percentChance(0.3f)){
            this.setItemSlot(EquipmentSlot.MAINHAND, goblinCanSpawnWith.get(pRandom.nextInt(0, goblinCanSpawnWith.size())).getDefaultInstance());
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag){
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public void handleEntityEvent(byte pId){
        if(pId == 45){
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if(!itemstack.isEmpty()){
                for(int i = 0; i < 8; ++i){
                    Vec3 vec3 = (new Vec3(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).xRot(-this.getXRot() * ((float)Math.PI / 180F)).yRot(-this.getYRot() * ((float)Math.PI / 180F));
                    this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemstack), this.getX() + this.getLookAngle().x / 2.0D, this.getY(), this.getZ() + this.getLookAngle().z / 2.0D, vec3.x, vec3.y + 0.05D, vec3.z);
                }
            }
        }else{
            super.handleEntityEvent(pId);
        }
    }

    public boolean canHoldItem(ItemStack pStack){
        return super.canHoldItem(pStack);
    }

    // maybe todo brain?
    @Override
    protected void registerGoals(){
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Animal.class, 10, true, true, (p_28604_) -> p_28604_ instanceof Chicken || p_28604_ instanceof Rabbit || p_28604_ instanceof Pig));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFish.class, 20, true, true, (p_28600_) -> p_28600_ instanceof AbstractSchoolingFish));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true, true));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(3, new AvoidStrongEntityGoal<>(this, Player.class, 16, 1.6, 1.8));

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.3));
        this.goalSelector.addGoal(0, new AdvancedPanicGoal(this, 1.85, this.getHealth() < 12));
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.3));
        this.goalSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, true));
        this.goalSelector.addGoal(3, new CollectBerriesGoal(this, 1.3, 12, 4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Wolf.class, 8, 1.6, 1.4));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Creeper.class, 12, 1.8, 1.4));
        this.goalSelector.addGoal(0, new SearchForItemsGoal(this, ALLOWED_ITEMS));
        this.goalSelector.addGoal(6, new RemoveBlockGoal(Blocks.FARMLAND, this, 1.4, 10));
        this.goalSelector.addGoal(6, new RemoveCropsGoal(this, 1.4, 10));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.4, 6, 10));
        this.goalSelector.addGoal(0, new SeekShelterGoal(this, 1.8));
    }

    public void reassessWeaponGoal(){
        this.level();
        if(!this.level().isClientSide){
            this.goalSelector.removeGoal(this.meleeGoal);
        }
    }

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack){
        super.setItemSlot(pSlot, pStack);
        if(!this.level().isClientSide){
            this.reassessWeaponGoal();
        }
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
        this.getEntityData().define(DATA_BABY_ID, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public float getScale(){
        return this.isBaby() ? 1f : 1.45F;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey){
        if(DATA_BABY_ID.equals(pKey)){
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level(), pCompound);
    }

    public boolean isBaby(){
        return this.getEntityData().get(DATA_BABY_ID);
    }

    public void setBaby(boolean Baby){
        this.getEntityData().set(DATA_BABY_ID, Baby);
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        Entity entity = source.getEntity();
        if(entity instanceof Player player){
            if(player.getAttributeValue(Attributes.ATTACK_DAMAGE) > 10f){
                this.goalSelector.addGoal(1, new AdvancedPanicGoal(this, 1.5, this.getHealth() < 12));
                this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 15, 1.2, 1.8));
            }else if(this.getHealth() < 12){
                this.goalSelector.addGoal(1, new AdvancedPanicGoal(this, 1.5, this.getHealth() < 12));
                this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 15, 1.2, 1.8));
            }else{
                if(!player.getAbilities().instabuild)
                    this.setTarget((Player)entity);
            }
        }

        return super.hurt(source, amount);
    }
}