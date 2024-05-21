package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.DamageSourceRegistry;
import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MeatBlockEntity extends AbstractArrow{
    public boolean dealtDamage;
    public ItemStack thrownStack = new ItemStack(BlockRegistry.MEAT_BLOCK.get());
    public float rotationVelocity = -8;
    RandomSource rand = RandomSource.create();

    public MeatBlockEntity(EntityType<? extends MeatBlockEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public MeatBlockEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.MEAT.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
    }

    public void tick(){
        if(this.inGroundTime > 4){
            this.dealtDamage = true;
        }

        BlockState state = BlockRegistry.MEAT_BLOCK.get().defaultBlockState();
        for(int a = 0; a < 2; ++a){
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + Mth.nextFloat(rand, 0.0F, 0.2F), this.getY() + 0.7D, this.getZ() + Mth.nextFloat(rand, 0.0F, 0.2F), 0d, 0.02d, 0d);
        }

        if(isInWater()){
            if(!this.level().isClientSide()){
                this.removeAfterChangingDimensions();
            }else{
                this.level().playSound(this, this.getOnPos(), SoundsRegistry.DISAPPEAR.get(), SoundSource.AMBIENT, 0.4f, 1f);
                for(int a = 0; a < 6; ++a){
                    double d0 = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.POOF, xo, yo, zo, d0, d1, d2);
                }
            }
        }

        super.tick();
    }

    public void onHit(HitResult pResult){
        if(pResult.getType() != HitResult.Type.ENTITY || !this.ownedBy(((EntityHitResult)pResult).getEntity())){
            if(!this.level().isClientSide){
                BlockState state = BlockRegistry.CATTAIL.get().defaultBlockState();
                for(int a = 0; a < 10; ++a){
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), xo, yo + 4, zo, 0.2d, 0.04d, 0.2d);
                }

                this.level().playSound(this, this.getOnPos(), SoundEvents.FROGSPAWN_BREAK, SoundSource.AMBIENT, 0.4f, 1f);
                this.removeAfterChangingDimensions();
            }
        }

        super.onHit(pResult);
    }

    public ItemStack getPickupItem(){
        return ItemStack.EMPTY;
    }

    @Nullable
    public EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec){
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        int e = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SHARPNESS, this.thrownStack);
        float f = 7.5f + (((float)e) - 1.5f);
        if(entity instanceof LivingEntity livingentity){
            f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
        }

        Entity shooter = this.getOwner();
        DamageSource damagesource = new DamageSource(DamageSourceRegistry.source(level(), DamageSourceRegistry.BLEEDING).typeHolder(), this, (Entity)(shooter == null ? this : shooter));
        this.dealtDamage = true;
        if(entity.hurt(damagesource, f)){
            if(entity.getType() == EntityType.ENDERMAN){
                return;
            }

            if(entity instanceof LivingEntity living){
                if(shooter instanceof LivingEntity){
                    ((LivingEntity)shooter).heal(0.5f);
                    EnchantmentHelper.doPostHurtEffects(living, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)shooter, living);
                }

                this.doPostHurtEffects(living);
            }
        }
    }

    public SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.FROGSPAWN_BREAK;
    }

    @Override
    public SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.FROGSPAWN_BREAK;
    }

    public void readAdditionalSaveData(CompoundTag compound){
        super.readAdditionalSaveData(compound);
        if(compound.contains("meat", 10)){
            this.thrownStack = ItemStack.of(compound.getCompound("meat"));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag compound){
        super.addAdditionalSaveData(compound);
        compound.put("meat", this.thrownStack.save(new CompoundTag()));
    }

    public void tickDespawn(){
        if(this.pickup != Pickup.DISALLOWED){
            super.tickDespawn();
        }
    }
}