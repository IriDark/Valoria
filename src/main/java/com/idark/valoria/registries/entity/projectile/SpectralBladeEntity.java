package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class SpectralBladeEntity extends AbstractSupplierProjectile{
    public SpectralBladeEntity(EntityType<? extends SpectralBladeEntity> type, Level worldIn){
        super(type, worldIn);
    }

    public SpectralBladeEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.SPECTRAL_BLADE.get(), worldIn, thrower, thrownStackIn);
        this.setItem(thrownStackIn);
        this.setOwner(thrower);
    }

    public void tick(){
        tickDespawn();
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
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.POOF, this.getRandomX(1.0D), this.getRandomY() + 1.5D, this.getRandomZ(1.0D), d0, d1, d2);
                }
            }
        }

        super.tick();
    }

    public void spawnParticleTrail(Level level, Projectile projectile, Vec3 spawnPos){
        if(level.isClientSide()){
            final Consumer<GenericParticle> blockTarget = p -> {
                Vec3 pPos = p.getPosition();
                double dX = projectile.getX() - pPos.x();
                double dY = projectile.getY() - pPos.y();
                double dZ = projectile.getZ() - pPos.z();
                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                float speed = 0.01f;
                float x = (float)(Math.sin(pitch) * Math.cos(yaw) * speed);
                float y = (float)(Math.cos(pitch) * speed);
                float z = (float)(Math.sin(pitch) * Math.sin(yaw) * speed);

                p.setSpeed(p.getSpeed().subtract(x, y, z));
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

    public void tickDespawn(){
        ++this.life;
        if (this.life >= 60) {
            this.discard();
        }
    }

    public void onHit(HitResult pResult){
        super.onHit(pResult);
        this.discard();
    }

    public ItemStack getPickupItem(){
        return null;
    }

    public SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
    }

    @Override
    public SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.DISAPPEAR.get();
    }

    public float getWaterInertia(){
        return 0.0F;
    }
}
