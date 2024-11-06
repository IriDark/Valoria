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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class ThrowableBomb extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_RADIUS_ID = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrowableBomb.class, EntityDataSerializers.ITEM_STACK);

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    public ThrowableBomb(Level pLevel, LivingEntity pShooter) {
        super(EntityTypeRegistry.THROWABLE_BOMB.get(), pShooter, pLevel);
        this.setFuse(80);
        this.setRadius(1.25f);
    }

    @NotNull
    @Override
    protected Item getDefaultItem() {
        return ItemsRegistry.throwableBomb.get();
    }

    @Override
    public void tick(){
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double)this.getGravity(), vec31.z);
        }

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = this.level().getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level().getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level(), blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.wasInPowderSnow = this.isInPowderSnow;
        this.isInPowderSnow = false;
        this.updateInWaterStateAndDoFluidPushing();
        this.updateSwimming();
        if (this.level().isClientSide) {
            this.clearFire();
        } else if (this.getRemainingFireTicks() > 0) {
            if (this.fireImmune()) {
                this.setRemainingFireTicks(this.getRemainingFireTicks() - 4);
                if (this.getRemainingFireTicks() < 0) {
                    this.clearFire();
                }
            } else {
                if (this.getRemainingFireTicks() % 20 == 0 && !this.isInLava()) {
                    this.hurt(this.damageSources().onFire(), 1.0F);
                }

                this.setRemainingFireTicks(this.getRemainingFireTicks() - 1);
            }

            if (this.getTicksFrozen() > 0) {
                this.setTicksFrozen(0);
                this.level().levelEvent(null, 1009, this.blockPosition(), 1);
            }
        }

        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, this.getX() - vec3.x * 0.25D, this.getY() - vec3.y * 0.25D, this.getZ() - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            this.setDeltaMovement(vec3.scale(0.8f));
        }

        if (this.isInLava()) {
            this.lavaHurt();
            this.fallDistance *= this.getFluidFallDistanceModifier(net.minecraftforge.common.ForgeMod.LAVA_TYPE.get());
        }

        if (this.verticalCollision){
            Vec3 normal = vec3.normalize();
            vec3 = vec3.subtract(normal.scale(2 * vec3.dot(normal)));
            vec3 = new Vec3(vec3.x, vec3.y * 0.5, vec3.z);
        }

        if (this.onGround()) this.setDeltaMovement(vec3.scale(0.5D));
        this.checkBelowWorld();
        if (!this.level().isClientSide) {
            this.setSharedFlagOnFire(this.getRemainingFireTicks() > 0);
        }

        this.move(MoverType.SELF, vec3.scale(1.5f));
        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);
        if (fuse <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                float yaw = this.getYRot();
                float pitch = this.getXRot();
                double offsetX = -Math.sin(Math.toRadians(yaw)) * 0.25;
                double offsetZ = Math.cos(Math.toRadians(yaw)) * 0.25;
                double offsetY = -Math.sin(Math.toRadians(pitch)) * 0.3;
                this.level().addParticle(ParticleTypes.SMOKE, this.getX() + offsetX, this.getY() + 0.3D + offsetY, this.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

        pCompound.putShort("Fuse", (short) this.getFuse());
        pCompound.putShort("Radius", (short) this.getRadius());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setFuse(pCompound.getShort("Fuse"));
        this.setItem(itemstack);
    }

    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), getRadius(), Level.ExplosionInteraction.TNT);
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setRadius(float pRadius) {
        this.entityData.set(DATA_RADIUS_ID, pRadius);
    }

    /**
     * Gets the radius from the data manager
     */
    public float getRadius() {
        return this.entityData.get(DATA_RADIUS_ID);
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
        this.getEntityData().define(DATA_FUSE_ID, 80);
        this.getEntityData().define(DATA_RADIUS_ID, 1.25f);
    }
}
