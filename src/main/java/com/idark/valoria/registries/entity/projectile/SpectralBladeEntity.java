package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;

import javax.annotation.*;

public class SpectralBladeEntity extends AbstractArrow{
    public boolean dealtDamage;
    public ItemStack thrownStack = new ItemStack(ItemsRegistry.SPECTRAL_BLADE.get());
    RandomSource rand = RandomSource.create();

    public SpectralBladeEntity(EntityType<? extends SpectralBladeEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public SpectralBladeEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.SPECTRAL_BLADE.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
    }

    public void tick(){
        if(this.inGroundTime > 4){
            this.dealtDamage = true;
        }

        Vec3 vector3d = this.getDeltaMovement();
        double a3 = vector3d.x;
        double a4 = vector3d.y;
        double a0 = vector3d.z;
        this.setDeltaMovement(a3, 0, a0);

        this.level().addParticle(ParticleTypes.REVERSE_PORTAL, this.getX() + a3 / 4.0D, this.getY() + a4 / 4.0D, this.getZ() + a0 / 2.0D, -a3, -a4 + 0.2D, -a0);
        if(isInWater()){
            if(!this.level().isClientSide()){
                this.removeAfterChangingDimensions();
            }else{
                this.level().playSound(this, this.getOnPos(), SoundsRegistry.DISAPPEAR.get(), SoundSource.AMBIENT, 0.4f, 1f);
                for(int a = 0; a < 6; ++a){
                    double d0 = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY() + 1.5D, this.getRandomZ(1.0D), d0, d1, d2);
                }
            }
        }

        super.tick();
    }

    public void onHit(HitResult pResult){
        super.onHit(pResult);
        if(!this.level().isClientSide){
            this.discard();
        }

    }

    public ItemStack getPickupItem(){
        return null;
    }

    @Nullable
    public EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec){
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    public void onHitBlock(BlockHitResult pResult){
        this.level().playSound(this, this.getOnPos(), SoundsRegistry.DISAPPEAR.get(), SoundSource.AMBIENT, 0.4f, 1f);
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        if(shooter instanceof Player player) {
            int e = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SHARPNESS, this.thrownStack);
            float f = (float) (player.getAttributes().getValue(AttributeRegistry.PROJECTILE_DAMAGE.get()) + Math.max(0, e - 2));
            if (entity instanceof LivingEntity livingentity) {
                f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
            }

            DamageSource damagesource = level().damageSources().trident(this, shooter);
            this.dealtDamage = true;
            this.level().playSound(this, this.getOnPos(), SoundsRegistry.DISAPPEAR.get(), SoundSource.AMBIENT, 0.4f, 1f);
            if (entity.hurt(damagesource, f)) {
                if (entity.getType() == EntityType.ENDERMAN) {
                    return;
                }

                if (entity instanceof LivingEntity living) {
                    EnchantmentHelper.doPostHurtEffects(living, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, living);
                    this.doPostHurtEffects(living);
                }
            }
        }

    }

    public SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
    }

    @Override
    public SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
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

    public void readAdditionalSaveData(CompoundTag compound){
        super.readAdditionalSaveData(compound);
        if(compound.contains("spectral_blade", 10)){
            this.thrownStack = ItemStack.of(compound.getCompound("spectral_blade"));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag compound){
        super.addAdditionalSaveData(compound);
        compound.put("spectral_blade", this.thrownStack.save(new CompoundTag()));
    }

    public void tickDespawn(){
        if(this.pickup != Pickup.DISALLOWED){
            super.tickDespawn();
        }
    }

    public float getWaterInertia(){
        return 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z){
        return true;
    }
}
