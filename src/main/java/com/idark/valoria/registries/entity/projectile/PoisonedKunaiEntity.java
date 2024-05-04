package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.Lists;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PoisonedKunaiEntity extends AbstractKunai {
    public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(PoisonedKunaiEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(PoisonedKunaiEntity.class, EntityDataSerializers.BYTE);
    public ItemStack thrownStack = new ItemStack(ItemsRegistry.SAMURAI_POISONED_KUNAI.get());
    public float rotationVelocity = 0;
    public int returningTicks;
    public List<Entity> hitEntities;

    public PoisonedKunaiEntity(EntityType<? extends PoisonedKunaiEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public PoisonedKunaiEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
        super(EntityTypeRegistry.POISONED_KUNAI.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
        this.entityData.set(PIERCE_LEVEL, (byte) EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte) 0);
        this.entityData.define(PIERCE_LEVEL, (byte) 0);
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
            for (int a = 0; a < 3; ++a) {
                this.level().addParticle(ParticleTypes.WHITE_ASH, this.getX() + a3 * (double) a / 4.0D, this.getY() + a4 * (double) a / 4.0D, this.getZ() + a0 * (double) a / 4.0D, -a3, -a4 + 0.2D, -a0);
            }
        }

        super.tick();
    }

    public ItemStack getPickupItem() {
        return this.thrownStack;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        DamageSource damagesource = level().damageSources().trident(this, shooter == null ? this : shooter);
        int e = (int) EnchantmentHelper.getDamageBonus(this.thrownStack, MobType.UNDEFINED);
        float f = 7f + (float) Math.max(0, e - 2);
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
        }

        if (this.hitEntities == null) {
            this.hitEntities = Lists.newArrayListWithCapacity(5);
        }

        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.thrownStack) == 0) {
            this.returnToPlayer = true;
        }

        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity) {
                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 170, 0));
                EnchantmentHelper.doPostHurtEffects(livingentity, entity);
                EnchantmentHelper.doPostDamageEffects((LivingEntity) entity, livingentity);

                this.doPostHurtEffects(livingentity);
            }
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Kunai", 10)) {
            this.thrownStack = ItemStack.of(compound.getCompound("Kunai"));
        }

        this.returnToPlayer = compound.getBoolean("DealtDamage");
        this.setPierceLevel(compound.getByte("PierceLevel"));
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(this.thrownStack));
        this.entityData.set(PIERCE_LEVEL, (byte) EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.thrownStack));
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Kunai", this.thrownStack.save(new CompoundTag()));
        compound.putBoolean("DealtDamage", this.returnToPlayer);
        compound.putByte("PierceLevel", this.getPierceLevel());
    }
}