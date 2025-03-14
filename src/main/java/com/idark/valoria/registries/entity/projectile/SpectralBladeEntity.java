package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;
import java.util.function.*;

public class SpectralBladeEntity extends AbstractTridotArrow{
    public boolean dealtDamage;
    public ItemStack thrownStack = new ItemStack(ItemsRegistry.spectralBlade.get());
    RandomSource rand = RandomSource.create();

    public SpectralBladeEntity(EntityType<? extends SpectralBladeEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public SpectralBladeEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.SPECTRAL_BLADE.get(), worldIn, thrower, thrownStackIn, 2);
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
        spawnParticleTrail(level(), this, new Vec3(this.getX() + a3 / 4.0D, this.getY() + a4 / 4.0D, this.getZ() + a0 / 2.0D));
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

    public void spawnParticleTrail(Level level, Projectile projectile, Vec3 spawnPos){
        if(level.isClientSide()){
            final Consumer<GenericParticle> blockTarget = p -> {
                Vec3 pPos = p.getPosition().mcVec();
                double dX = projectile.getX() - pPos.x();
                double dY = projectile.getY() - pPos.y();
                double dZ = projectile.getZ() - pPos.z();
                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                float speed = 0.01f;
                float x = (float)(Math.sin(pitch) * Math.cos(yaw) * speed);
                float y = (float)(Math.cos(pitch) * speed);
                float z = (float)(Math.sin(pitch) * Math.sin(yaw) * speed);

                p.setSpeed(p.getSpeed().sub(x, y, z));
            };

            ParticleBuilder.create(TridotParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(Pal.cyan, Col.white).build())
                    .setTransparencyData(GenericParticleData.create(0.3f).setEasing(Interp.sineIn).build())
                    .setScaleData(GenericParticleData.create(0.06f, 0.15f, 0).setEasing(Interp.sineOut).build())
                    .addTickActor(blockTarget)
                    .setLifetime(16)
                    .randomVelocity(0.25f)
                    .disablePhysics()
                    .repeat(level, spawnPos.x(), spawnPos.y(), spawnPos.z(), 4);
        }
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

    public SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
    }

    @Override
    public SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
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
