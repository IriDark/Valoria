package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;

import javax.annotation.Nullable;
import java.util.*;

public class ThrownSpearEntity extends AbstractValoriaArrow implements ItemSupplier{
    public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrownSpearEntity.class, EntityDataSerializers.ITEM_STACK);
    public int returningTicks;
    public boolean returnToPlayer;
    public float rotationVelocity = 50;
    private float explosive_radius;
    public boolean wasInGround;
    private boolean shouldExplode;
    private boolean isExploded;
    private Level.ExplosionInteraction interaction;
    private final Set<MobEffectInstance> effects = Sets.newHashSet();

    public ThrownSpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, int minDamage, int baseDamage){
        super(EntityTypeRegistry.SPEAR.get(), worldIn, thrower, thrownStackIn, minDamage, baseDamage);
        this.minDamage = minDamage;
        this.baseDamage = baseDamage;
        this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(thrownStackIn));
        this.entityData.set(PIERCE_LEVEL, (byte)EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, thrownStackIn));
        this.setOwner(thrower);
        if(thrower instanceof Player){
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    public void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte)0);
        this.entityData.define(PIERCE_LEVEL, (byte)0);
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void setExplode(Level.ExplosionInteraction pInteraction, float radius){
        this.shouldExplode = true;
        interaction = pInteraction;
        explosive_radius = radius;
    }

    protected void onHit(HitResult pResult){
        super.onHit(pResult);
        if(this.shouldExplode && !this.isExploded){
            if(!this.level().isClientSide){
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), explosive_radius, interaction);
            }

            this.isExploded = true;
        }
    }

    public void addEffect(MobEffectInstance pEffectInstance){
        this.effects.add(pEffectInstance);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound){
        super.addAdditionalSaveData(compound);
        if(!this.effects.isEmpty()){
            ListTag listtag = new ListTag();
            for(MobEffectInstance mobeffectinstance : this.effects){
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            compound.put("CustomPotionEffects", listtag);
        }

        compound.putBoolean("DealtDamage", this.returnToPlayer);
        compound.putByte("PierceLevel", this.getPierceLevel());
        ItemStack itemstack = this.getItemRaw();
        if(!itemstack.isEmpty()){
            compound.put("Item", itemstack.save(new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound){
        super.readAdditionalSaveData(compound);
        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(compound)){
            this.addEffect(mobeffectinstance);
        }

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

    public boolean shouldReturnToThrower(){
        Entity entity = this.getOwner();
        if(entity != null && entity.isAlive()){
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        }else{
            return false;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec){
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public void playerTouch(Player pEntity){
        if(this.ownedBy(pEntity) || this.getOwner() == null){
            super.playerTouch(pEntity);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
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
                    this.playSound(SoundsRegistry.SPEAR_RETURN.get(), 10.0F, 0.6F);
                }

                ++this.returningTicks;
            }
        }

        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround && !wasInGround){
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            this.level().addParticle(ParticleTypes.CRIT, this.getX() + a3 / 4.0D, this.getY() + a4 / 4.0D, this.getZ() + a0 / 4.0D, -a3, 0.1, -a0);
        }

        super.tick();
    }

    protected void onBelowWorld(){
        if(this.getOwner() instanceof Player player){
            if(!player.getAbilities().instabuild){
                player.spawnAtLocation(this.getItem());
            }
        }

        super.onBelowWorld();
    }

    protected void doPostHurtEffects(LivingEntity pLiving){
        super.doPostHurtEffects(pLiving);
        Entity entity = this.getEffectSource();
        if(!this.effects.isEmpty()){
            for(MobEffectInstance effect : this.effects){
                pLiving.addEffect(effect, entity);
            }
        }
    }

    protected void onHitBlock(BlockHitResult pResult){
        super.onHitBlock(pResult);
        this.wasInGround = true;
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
                boolean flag = entity.getType() == EntityType.ENDERMAN;
                if(flag){
                    return;
                }

                if(entity instanceof LivingEntity living){
                    EnchantmentHelper.doPostHurtEffects(living, player);
                    EnchantmentHelper.doPostDamageEffects(player, living);
                    this.doPostHurtEffects(living);
                }
            }
        }
    }

    public void setEffectsFromList(ImmutableList<MobEffectInstance> effects){
        for(MobEffectInstance mobeffectinstance : effects){
            this.effects.add(new MobEffectInstance(mobeffectinstance));
        }
    }

    protected Item getDefaultItem(){
        return ItemsRegistry.STONE_SPEAR.get();
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
