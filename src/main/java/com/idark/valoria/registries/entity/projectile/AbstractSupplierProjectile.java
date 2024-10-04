package com.idark.valoria.registries.entity.projectile;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.advancements.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
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

public abstract class AbstractSupplierProjectile extends AbstractValoriaArrow implements ItemSupplier{
    public static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(AbstractSupplierProjectile.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(AbstractSupplierProjectile.class, EntityDataSerializers.ITEM_STACK);
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    public int returningTicks;
    public boolean returnToPlayer;
    public boolean wasInGround;
    public boolean velocityBased;
    public AbstractSupplierProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public AbstractSupplierProjectile(EntityType<? extends AbstractArrow> pEntityType, Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(pEntityType, worldIn, thrower, thrownStackIn, 0);
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOYALTY_LEVEL, (byte) 0);
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void addEffect(MobEffectInstance pEffectInstance) {
        this.effects.add(pEffectInstance);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (!this.effects.isEmpty()) {
            ListTag listtag = new ListTag();
            for (MobEffectInstance mobeffectinstance : this.effects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            compound.put("CustomPotionEffects", listtag);
        }

        compound.putBoolean("DealtDamage", this.returnToPlayer);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.save(new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        for (MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pCompound)) {
            this.addEffect(mobeffectinstance);
        }

        this.returnToPlayer = pCompound.getBoolean("DealtDamage");
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(this.getItem()));
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
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

    public ItemStack getPickupItem() {
        return this.getItem().copy();
    }

    public SoundEvent getReturnSound() {
        return null;
    }

    public void setVelocityBasedDamage(double baseDamage) {
        this.baseDamage = baseDamage;
        this.velocityBased = true;
    }

    public boolean isVelocityBased() {
        return velocityBased;
    }

    public void spawnParticlesTrail() {
        if (this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround && !wasInGround) {
            Vec3 vector3d = this.getDeltaMovement();
            double a3 = vector3d.x;
            double a4 = vector3d.y;
            double a0 = vector3d.z;
            this.level().addParticle(ParticleTypes.CRIT, this.getX() + a3 / 4.0D, this.getY() + a4 / 4.0D, this.getZ() + a0 / 4.0D, -a3, 0.1, -a0);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        super.tick();
        if (this.inGroundTime > 4) {
            this.returnToPlayer = true;
        }

        Entity owner = this.getOwner();
        if ((this.returnToPlayer || this.isNoPhysics()) && owner != null) {
            int i = this.entityData.get(LOYALTY_LEVEL);
            if (i > 0 && !this.shouldReturnToThrower()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else if (i > 0) {
                this.setNoPhysics(true);
                Vec3 vector3d = new Vec3(owner.getX() - this.getX(), owner.getEyeY() - this.getY(), owner.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vector3d.y * 0.015D * (double) i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double) i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(getReturnSound(), 10.0F, 0.6F);
                }

                ++this.returningTicks;
            }
        }
    }

    protected void onBelowWorld() {
        if (this.getOwner() instanceof Player player) {
            if (!player.getAbilities().instabuild) {
                player.spawnAtLocation(this.getItem());
            }
        }

        super.onBelowWorld();
    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        Entity entity = this.getEffectSource();
        if (!this.effects.isEmpty()) {
            for (MobEffectInstance effect : this.effects) {
                pLiving.addEffect(effect, entity);
            }
        }
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.wasInGround = true;
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        if (this.getPierceLevel() > 0) {
            if (this.piercingIgnoreEntityIds == null) {
                this.piercingIgnoreEntityIds = new IntOpenHashSet(5);
            }

            if (this.piercedAndKilledEntities == null) {
                this.piercedAndKilledEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercingIgnoreEntityIds.size() >= this.getPierceLevel() + 1) {
                this.discard();
                return;
            }

            this.piercingIgnoreEntityIds.add(entity.getId());
        }

        DamageSource damagesource;
        if(shooter == null){
            damagesource = this.damageSources().arrow(this, this);
        }else{
            damagesource = this.damageSources().arrow(this, shooter);
            if(shooter instanceof LivingEntity){
                ((LivingEntity)shooter).setLastHurtMob(entity);
            }
        }

        if(shooter instanceof Player player && !this.returnToPlayer){
            boolean flag = entity.getType() == EntityType.ENDERMAN;
            if (this.isOnFire() && !flag) {
                entity.setSecondsOnFire(5);
            }

            if(isVelocityBased()){
                double velocity = this.getDeltaMovement().length();
                int damage = Mth.ceil(Mth.clamp(velocity * this.baseDamage, 0, Integer.MAX_VALUE));
                if (this.isCritArrow()) {
                    long j = this.random.nextInt(damage / 2 + 2);
                    damage = (int) Math.min(j + (long) damage, 2147483647L);
                }

                hurt(player, entity, damagesource, damage);
            }else{
                int e = (int)EnchantmentHelper.getDamageBonus(this.getItem(), MobType.UNDEFINED);
                float f = (float)(player.getAttributes().getValue(AttributeRegistry.PROJECTILE_DAMAGE.get()) + Math.max(0, e - 2));
                if(entity instanceof LivingEntity livingentity){
                    f += EnchantmentHelper.getDamageBonus(this.getItem(), livingentity.getMobType());
                }

                hurt(player, entity, damagesource, f);
            }
        }
    }

    public void hurt(Player player, Entity entity, DamageSource source, float damage) {
        int k = entity.getRemainingFireTicks();
        if(entity.hurt(source, damage)){
            boolean flag = entity.getType() == EntityType.ENDERMAN;
            if(flag){
                return;
            }

            if(entity instanceof LivingEntity living){
                EnchantmentHelper.doPostHurtEffects(living, player);
                EnchantmentHelper.doPostDamageEffects(player, living);
                this.doPostHurtEffects(living);
            }

            if(entity instanceof LivingEntity livingentity){
                if(this.knockback > 0){
                    double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
                    if(vec3.lengthSqr() > 0.0D){
                        livingentity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if (livingentity != player && livingentity instanceof Player && player instanceof ServerPlayer serv && !this.isSilent()) {
                    serv.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
                    this.piercedAndKilledEntities.add(livingentity);
                }

                if (!this.level().isClientSide && player instanceof ServerPlayer serverplayer) {
                    if (this.piercedAndKilledEntities != null && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, this.piercedAndKilledEntities);
                    } else if (!entity.isAlive() && this.shotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayer, List.of(entity));
                    }
                }
            }
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            }
        }

        if(EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, this.getItem()) == 0){
            this.returnToPlayer = true;
        }
    }

    public void setHurt() {
        this.returnToPlayer = true;
    }

    public void setEffectsFromList(ImmutableList<MobEffectInstance> effects) {
        for (MobEffectInstance mobeffectinstance : effects) {
            this.effects.add(new MobEffectInstance(mobeffectinstance));
        }
    }

    protected Item getDefaultItem() {
        return ItemsRegistry.stoneSpear.get();
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public @NotNull ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }
    }
}