package com.idark.valoria.registries.entity.living;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public abstract class AbstractGoblin extends PathfinderMob implements NeutralMob, Enemy{
    public final SimpleContainer inventory = new SimpleContainer(8);
    public static final Predicate<ItemEntity> ALLOWED_ITEMS = (p_289438_) -> !p_289438_.hasPickUpDelay() && p_289438_.isAlive() || p_289438_.getItem().isEdible() || p_289438_.getItem() == Items.GOLD_INGOT.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_BLOCK.getDefaultInstance() || p_289438_.getItem() == Items.GOLD_NUGGET.getDefaultInstance() || p_289438_.getItem() == ItemsRegistry.samuraiKunai.get().getDefaultInstance() || p_289438_.getItem() == ItemsRegistry.samuraiPoisonedKunai.get().getDefaultInstance() || p_289438_.getItem().getItem() instanceof SwordItem;

    @Nullable
    public UUID persistentAngerTarget;
    public int ticksSinceEaten;
    public static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(AbstractGoblin.class, EntityDataSerializers.INT);
    public static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    public static final EntityDataAccessor<Boolean> DATA_BABY_ID = SynchedEntityData.defineId(AbstractGoblin.class, EntityDataSerializers.BOOLEAN);

    public AbstractGoblin(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
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

    public boolean canEat(ItemStack pStack){
        return pStack.getItem().isEdible() && this.getTarget() == null && this.onGround() && !this.isSleeping();
    }

    public boolean wantsToPickUp(ItemStack pStack){
        Item item = pStack.getItem();
        return item.isEdible() || item == ItemsRegistry.samuraiKunai.get() || item == ItemsRegistry.samuraiPoisonedKunai.get() || item == Items.GOLD_INGOT || item == Items.GOLD_BLOCK || item == Items.GOLD_NUGGET || item instanceof SwordItem && this.getInventory().canAddItem(pStack);
    }

    public int getExperienceReward(){
        if(this.isBaby()){
            this.xpReward = (int)((double)this.xpReward * 2.5D);
        }

        return super.getExperienceReward();
    }

    public void aiStep(){
        boolean flag = this.getHealth() < 12;
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

        if(!this.level().isClientSide && level() instanceof ServerLevel serverLevel){
            if(flag){
                this.setAggressive(false);
                this.forgetCurrentTargetAndRefreshUniversalAnger();
                this.updatePersistentAnger(serverLevel, true);
            }else{
                this.updatePersistentAnger(serverLevel, true);
            }
        }

        super.aiStep();
    }

    // panic reason
    public final boolean isLowHP(){
        return this.getHealth() < 12;
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

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack){
        super.setItemSlot(pSlot, pStack);
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

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey){
        if(DATA_BABY_ID.equals(pKey)){
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    protected void defineSynchedData(){
        super.defineSynchedData();
        this.getEntityData().define(DATA_BABY_ID, false);
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

    @Override
    public float getScale(){
        return this.isBaby() ? 1f : 1.45F;
    }

    public boolean isBaby(){
        return this.getEntityData().get(DATA_BABY_ID);
    }

    public void setBaby(boolean Baby){
        this.getEntityData().set(DATA_BABY_ID, Baby);
    }
}
