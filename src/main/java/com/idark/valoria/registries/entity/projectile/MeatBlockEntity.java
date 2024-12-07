package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;

public class MeatBlockEntity extends AbstractArrow {
    public boolean dealtDamage;
    public ItemStack thrownStack = new ItemStack(BlockRegistry.meatBlock.get());
    public float rotationVelocity = -8;
    RandomSource rand = RandomSource.create();

    public MeatBlockEntity(EntityType<? extends MeatBlockEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public MeatBlockEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
        super(EntityTypeRegistry.MEAT.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        BlockState state = BlockRegistry.meatBlock.get().defaultBlockState();
        for (int a = 0; a < 2; ++a) {
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + Mth.nextFloat(rand, 0.0F, 0.2F), this.getY() + 0.7D, this.getZ() + Mth.nextFloat(rand, 0.0F, 0.2F), 0d, 0.02d, 0d);
        }

        if (isInWater()) {
            if (!this.level().isClientSide()) {
                this.removeAfterChangingDimensions();
            } else {
                this.level().playSound(this, this.getOnPos(), SoundsRegistry.DISAPPEAR.get(), SoundSource.AMBIENT, 0.4f, 1f);
                for (int a = 0; a < 6; ++a) {
                    double d0 = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.POOF, xo, yo, zo, d0, d1, d2);
                }
            }
        }

        super.tick();
    }

    public void onHit(HitResult pResult) {
        if (pResult.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult) pResult).getEntity())) {
            if (!this.level().isClientSide) {
                BlockState state = BlockRegistry.cattail.get().defaultBlockState();
                for (int a = 0; a < 10; ++a) {
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), xo, yo + 4, zo, 0.2d, 0.04d, 0.2d);
                }

                this.level().playSound(this, this.getOnPos(), SoundEvents.FROGSPAWN_BREAK, SoundSource.AMBIENT, 0.4f, 1f);
                this.removeAfterChangingDimensions();
            }
        }

        super.onHit(pResult);
    }

    public ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Nullable
    public EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        Entity shooter = this.getOwner();
        if (shooter instanceof Player player){
            float totalDamage = (float)(player.getAttributes().getValue(AttributeRegistry.PROJECTILE_DAMAGE.get()));
            if (entity instanceof LivingEntity livingentity) {
                totalDamage += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
            }

            DamageSource damagesource = new DamageSource(DamageSourceRegistry.source(level(), DamageSourceRegistry.BLEEDING).typeHolder(), this, shooter);
            player.heal(totalDamage * 0.75f);
            this.dealtDamage = true;
            if(entity.hurt(damagesource, totalDamage)){
                if(entity.getType() == EntityType.ENDERMAN){
                    return;
                }

                if(entity instanceof LivingEntity living){
                    EnchantmentHelper.doPostHurtEffects(living, shooter);
                    EnchantmentHelper.doPostDamageEffects(player, living);
                    this.doPostHurtEffects(living);
                }
            }
        }
    }

    public SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.FROGSPAWN_BREAK;
    }

    @Override
    public SoundEvent getHitGroundSoundEvent() {
        return SoundEvents.FROGSPAWN_BREAK;
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("thrown", 10)) {
            this.thrownStack = ItemStack.of(compound.getCompound("thrown"));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("thrown", this.thrownStack.save(new CompoundTag()));
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.DISALLOWED) {
            super.tickDespawn();
        }
    }
}