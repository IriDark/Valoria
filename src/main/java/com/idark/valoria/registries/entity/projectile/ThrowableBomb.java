package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
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
        this.checkInsideBlocks();
        Vec3 velocity = this.getDeltaMovement();
        this.move(MoverType.SELF, velocity);
        if (this.horizontalCollision) {
            this.setDeltaMovement(new Vec3(-velocity.x * 0.5D, velocity.y * -0.8D, velocity.z * 0.5D));
            velocity = this.getDeltaMovement();
        }

        if (this.verticalCollision) {
            velocity = this.getDeltaMovement();
            this.setDeltaMovement(new Vec3(velocity.x * 0.7D, (-velocity.y * 0.5f) / 2, velocity.z * 0.7D));
            if (Math.abs(velocity.x) < 0.1D && Math.abs(velocity.y) < 0.1D && Math.abs(velocity.z) < 0.1D) {
                this.setDeltaMovement(new Vec3(0, (-velocity.y * 0.5f) / 2, 0));
            }

        }

        if (!this.onGround()) {
            this.setDeltaMovement(velocity.scale(0.98D));
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
                float yaw = this.getYRot();
                float pitch = this.getXRot();

                float yawRadians = (float) Math.toRadians(yaw);
                float pitchRadians = (float) Math.toRadians(pitch);

                double offsetX = -Math.sin(yawRadians) * 0.25;
                double offsetZ = Math.cos(yawRadians) * 0.25;
                double offsetY = -Math.sin(pitchRadians) * 0.3;
                this.level().addParticle(ParticleTypes.SMOKE, this.getX() + offsetX, this.getY() + 0.3D + offsetY, this.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void onInsideBlock(BlockState pState) {
        if(!pState.isAir()) {
            if (this.verticalCollision){
                this.setOnGround(false);
            }  else {
                this.setDeltaMovement(new Vec3(-this.getDeltaMovement().x * 0.5D, this.getDeltaMovement().y * -0.8D, this.getDeltaMovement().z * 0.5D));
            }

            if (!pState.isAir() && isInsideBlocks()) {
                // Valoria.LOGGER.error("Entity discarded, stuck in blocks, position {}", this.getOnPos());
                // FIXME: 07.09.2024
                this.discard();
            }
        }
    }

    public boolean isInsideBlocks() {
        if (this.noPhysics) {
            return false;
        } else {
            float f = this.getDimensions(this.getPose()).width * 0.8F;
            AABB aabb = AABB.ofSize(this.getEyePosition(), f, 1.0E-6D, f);
            return BlockPos.betweenClosedStream(aabb).anyMatch((p_201942_) -> {
                BlockState blockstate = this.level().getBlockState(p_201942_);
                return !blockstate.isAir() && blockstate.isSolid() && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level(), p_201942_).move((double)p_201942_.getX(), (double)p_201942_.getY(), (double)p_201942_.getZ()), Shapes.create(aabb), BooleanOp.AND);
            });
        }
    }

    public void onHit(HitResult pResult){
        if(!this.level().isClientSide){
            this.level().playSound(this, this.getOnPos(), SoundEvents.CREEPER_PRIMED, SoundSource.AMBIENT, 0.4f, 1f);
            this.level().broadcastEntityEvent(this, (byte)3);
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
