package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class ThrowableBomb extends ThrowableItemProjectile{
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_RADIUS_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.ITEM_STACK);

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel){
        super(pEntityType, pX, pY, pZ, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel){
        super(pEntityType, pShooter, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(Level pLevel, LivingEntity pShooter){
        super(EntityTypeRegistry.THROWABLE_BOMB.get(), pShooter, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    @NotNull
    @Override
    protected Item getDefaultItem(){
        return ItemsRegistry.THROWABLE_BOMB.get();
    }

    @Override
    public void tick(){
        super.tick();
        if(!this.isNoGravity()){
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if(this.onGround()){
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if(i <= 0){
            this.discard();
            if(!this.level().isClientSide){
                this.explode();
            }
        }else{
            this.updateInWaterStateAndDoFluidPushing();
            if(this.level().isClientSide){
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

        // something really rare but happens
        if(this.isInWall()){
            this.discard();
        }

        this.checkInsideBlocks();
    }

    public void onHit(HitResult pResult){
        if(!this.level().isClientSide){
            this.level().playSound(this, this.getOnPos(), SoundEvents.CREEPER_PRIMED, SoundSource.AMBIENT, 0.4f, 1f);
            this.level().broadcastEntityEvent(this, (byte)3);

            if(!this.onGround()){
                // preventing stuck
                this.setDeltaMovement(-0.07283160655324188, 0.05140070277125347, 0.0049501345270485525);
            }
        }

        super.onHit(pResult);
    }

    public void setItem(ItemStack pStack){
        if(!pStack.is(this.getDefaultItem()) || pStack.hasTag()){
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }
    }

    protected ItemStack getItemRaw(){
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public void addAdditionalSaveData(CompoundTag pCompound){
        ItemStack itemstack = this.getItemRaw();
        if(!itemstack.isEmpty()){
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

        pCompound.putShort("Fuse", (short)this.getFuse());
        pCompound.putShort("Radius", (short)this.getRadius());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setFuse(pCompound.getShort("Fuse"));
        this.setItem(itemstack);
    }

    protected void explode(){
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), getRadius(), Level.ExplosionInteraction.TNT);
    }

    public void setFuse(int pLife){
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse(){
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setRadius(float pRadius){
        this.entityData.set(DATA_RADIUS_ID, pRadius);
    }

    /**
     * Gets the radius from the data manager
     */
    public float getRadius(){
        return this.entityData.get(DATA_RADIUS_ID);
    }

    protected void defineSynchedData(){
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
        this.getEntityData().define(DATA_FUSE_ID, 80);
        this.getEntityData().define(DATA_RADIUS_ID, 1.25f);
    }
}
