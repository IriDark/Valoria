package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.*;

public class KunaiEntity extends AbstractKunai implements ItemSupplier{
    public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(KunaiEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(KunaiEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(KunaiEntity.class, EntityDataSerializers.ITEM_STACK);
    public float rotationVelocity = 0;
    public int returningTicks;

    public KunaiEntity(EntityType<? extends KunaiEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public KunaiEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.KUNAI.get(), thrower, worldIn);
        this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(thrownStackIn));
        this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
    }

    public void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte)0);
        this.entityData.define(PIERCE_LEVEL, (byte)0);
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void tick(){
        if(this.inGroundTime > 4){
            this.returnToPlayer = true;
        }

        Entity entity = this.getOwner();
        if((this.returnToPlayer || this.isNoPhysics()) && entity != null){
            int i = this.entityData.get(LOYALTY_LEVEL);
            if(i > 0 && !this.shouldReturnToThrower()){
                if(!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED){
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            }else if(i > 0){
                this.setNoPhysics(true);
                Vec3 vector3d = new Vec3(entity.getX() - this.getX(), entity.getEyeY() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * (double)i, this.getZ());
                if(this.level().isClientSide){
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double)i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if(this.returningTicks == 0){
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            for(int a = 0; a < 3; ++a){
                this.level().addParticle(ParticleTypes.WHITE_ASH, this.getX() + a3 * (double)a / 4.0D, this.getY() + a4 * (double)a / 4.0D, this.getZ() + a0 * (double)a / 4.0D, -a3, -a4 + 0.2D, -a0);
            }
        }

        super.tick();
    }

    public ItemStack getPickupItem(){
        return this.getItem().copy();
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        DamageSource damagesource = level().damageSources().trident(this, shooter == null ? this : shooter);
        if(shooter instanceof Player player){
            int e = (int)EnchantmentHelper.getDamageBonus(this.getItem(), MobType.UNDEFINED);
            float f = (float)(player.getAttributes().getValue(AttributeRegistry.PROJECTILE_DAMAGE.get()) + Math.max(0, e - 2));
            if(entity instanceof LivingEntity livingentity){
                f += EnchantmentHelper.getDamageBonus(this.getItem(), livingentity.getMobType());
            }

            if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.getItem()) == 0){
                this.returnToPlayer = true;
            }

            if(entity.hurt(damagesource, f)){
                if(entity.getType() == EntityType.ENDERMAN){
                    return;
                }

                if(entity instanceof LivingEntity living){
                    if(this.getItem().is(ItemsRegistry.SAMURAI_POISONED_KUNAI.get())){
                        living.addEffect(new MobEffectInstance(MobEffects.POISON, 170, 0));
                    }

                    EnchantmentHelper.doPostHurtEffects(living, player);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)shooter, player);
                    this.doPostHurtEffects(living);
                }
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag compound){
        super.addAdditionalSaveData(compound);
        compound.putBoolean("DealtDamage", this.returnToPlayer);
        compound.putByte("PierceLevel", this.getPierceLevel());
        ItemStack itemstack = this.getItemRaw();
        if(!itemstack.isEmpty()){
            compound.put("Item", itemstack.save(new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(CompoundTag compound){
        super.readAdditionalSaveData(compound);
        this.returnToPlayer = compound.getBoolean("DealtDamage");
        this.setPierceLevel(compound.getByte("PierceLevel"));
        this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(this.getItem()));
        this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.getItem()));
        ItemStack itemstack = ItemStack.of(compound.getCompound("Item"));
        this.setItem(itemstack);
    }

    public void tickDespawn(){
        int i = this.entityData.get(LOYALTY_LEVEL);
        if(this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0){
            super.tickDespawn();
        }
    }

    protected Item getDefaultItem(){
        return ItemsRegistry.SAMURAI_KUNAI.get();
    }

    protected ItemStack getItemRaw(){
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public @NotNull ItemStack getItem(){
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    public void setItem(ItemStack pStack){
        if(!pStack.is(this.getDefaultItem()) || pStack.hasTag()){
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }
    }
}