package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.Level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class ThrowableBomb extends ThrowableItemProjectile{
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_RADIUS_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.ITEM_STACK);
    private static Level.ExplosionInteraction bombInteraction = ExplosionInteraction.TNT;

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
        return ItemsRegistry.throwableBomb.get();
    }

    @Override
    public void tick(){
        this.checkInsideBlocks();
        super.tick();
        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);
        if(fuse <= 0){
            this.discard();
            if(!this.level().isClientSide){
                this.explode();
            }
        }else{
            this.updateInWaterStateAndDoFluidPushing();
            if(this.level().isClientSide){
                float yaw = this.getYRot();
                float pitch = this.getXRot();
                double offsetX = -Math.sin(Math.toRadians(yaw)) * 0.25;
                double offsetZ = Math.cos(Math.toRadians(yaw)) * 0.25;
                double offsetY = -Math.sin(Math.toRadians(pitch)) * 0.3;
                this.level().addParticle(ParticleTypes.SMOKE, this.getX() + offsetX, this.getY() + 0.3D + offsetY, this.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult){
        super.onHitEntity(pResult);
        this.explode();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult){
        Level level = level();
        BlockPos pos = pResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);
        if(blockState.isSolid()){
            Direction d = pResult.getDirection();
            Vec3 mot = getDeltaMovement();
            double x = mot.x();
            double y = mot.y();
            double z = mot.z();

            if(d.getStepX() != 0){
                x *= -1D;
            }else if(d.getStepY() != 0){
                y *= -1D;
            }else if(d.getStepZ() != 0){
                z *= -1D;
            }

            Vec3 newMot = new Vec3(x, y, z).scale(0.325f);
            setPos(pResult.getLocation());
            setDeltaMovement(newMot);
        }

        super.onHitBlock(pResult);
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
        this.level().explode(this.getOwner(), this.getX(), this.getY(0.0625D), this.getZ(), getRadius(), bombInteraction);
    }

    public void setInteraction(Level.ExplosionInteraction intr){
        bombInteraction = intr;
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
