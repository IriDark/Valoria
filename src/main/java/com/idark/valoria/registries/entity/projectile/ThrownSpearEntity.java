package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.client.particle.ModParticles;
import com.idark.valoria.client.particle.types.Particles;
import com.idark.valoria.registries.EnchantmentsRegistry;
import com.idark.valoria.registries.EntityTypeRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class ThrownSpearEntity extends AbstractValoriaArrow implements ItemSupplier {
    public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.ITEM_STACK);
    public int returningTicks;
    public boolean returnToPlayer;
    public float rotationVelocity = 50;

    public ThrownSpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int minDamage, int baseDamage) {
        super(EntityTypeRegistry.SPEAR.get(), worldIn, thrower, thrownStackIn, minDamage, baseDamage);
        this.minDamage = minDamage;
        this.baseDamage = baseDamage;

        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
        this.entityData.set(PIERCE_LEVEL, (byte) EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
        this.setOwner(thrower);
        if (thrower instanceof Player) {
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte) 0);
        this.entityData.define(PIERCE_LEVEL, (byte) 0);
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Spear", 10)) {
            this.arrowItem = ItemStack.of(compound.getCompound("Spear"));
        }

        this.returnToPlayer = compound.getBoolean("DealtDamage");
        this.setPierceLevel(compound.getByte("PierceLevel"));
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(this.getItem()));
        this.entityData.set(PIERCE_LEVEL, (byte) EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.getItem()));
        ItemStack itemstack = ItemStack.of(compound.getCompound("Item"));
        this.setItem(itemstack);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Spear", this.getItem().save(new CompoundTag()));
        compound.putBoolean("DealtDamage", this.returnToPlayer);
        compound.putByte("PierceLevel", this.getPierceLevel());
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.save(new CompoundTag()));
        }
    }

    public void tickDespawn() {
        int i = this.entityData.get(LOYALTY_LEVEL);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }
    }

    public boolean shouldReturnToThrower() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec) {
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public void playerTouch(Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.returnToPlayer = true;
        }

        Entity entity = this.getOwner();
        if ((this.returnToPlayer || this.isNoPhysics()) && entity != null) {
            int i = this.entityData.get(LOYALTY_LEVEL);
            if (i > 0 && !this.shouldReturnToThrower()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else if (i > 0) {
                this.setNoPhysics(true);
                Vec3 vector3d = new Vec3(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * (double) i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double) i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        if (this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for (int a = 0; a < 1; ++a) {
                this.level().addParticle(ParticleTypes.POOF, this.getX() + a3 * (double) a / 4.0D, this.getY() + a4 * (double) a / 4.0D, this.getZ() + a0 * (double) a / 4.0D, -a3, 0.1, -a0);
            }
        }

        super.tick();
    }

    @Override
    public void onRemovedFromWorld() {
        if(this.getOwner() instanceof Player player) {
            if(!player.getAbilities().instabuild) {
                player.spawnAtLocation(this.getItem());
            }
        }

        super.onRemovedFromWorld();
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        DamageSource damagesource = level().damageSources().trident(this, shooter == null ? this : shooter);
        int e = (int) EnchantmentHelper.getDamageBonus(this.getItem(), MobType.UNDEFINED);
        float f = 7f + (float) Math.max(0, e - 2);
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.getItem(), livingentity.getMobType());
        }

        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.getItem()) == 0) {
            this.returnToPlayer = true;
        }

        if(EnchantmentHelper.getTagEnchantmentLevel(EnchantmentsRegistry.BLEEDING.get(), this.getItem()) > 0) {
            for (int a = 0; a < 12; a++) {
                Particles.create(ModParticles.SPHERE)
                        .randomOffset(0.7f, 0f, 0.7f)
                        .randomVelocity(0.5f, 0, 0.5f)
                        .enableGravity()
                        .setAlpha(1f, 0)
                        .setScale(0.1f, 0)
                        .setColor(145, 0, 20, 255, 0, 0)
                        .setLifetime(6)
                        .spawn(entity.level(), entity.getX() + (new Random().nextDouble() - 0.5f) / 2, entity.getY() + (new Random().nextDouble() + 1f) / 2, entity.getZ());
            }
        }

        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity living) {
                if (shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(living, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, living);
                }

                this.doPostHurtEffects(living);
            }
        }
    }

    protected Item getDefaultItem() {
        return Items.AIR;
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public @NotNull ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
}
