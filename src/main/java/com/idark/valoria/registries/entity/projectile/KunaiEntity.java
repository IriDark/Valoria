package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.graphics.particle.*;
import pro.komaru.tridot.client.graphics.particle.behavior.*;
import pro.komaru.tridot.client.graphics.particle.data.*;
import pro.komaru.tridot.core.math.*;

import javax.annotation.Nullable;
import java.util.function.*;

public class KunaiEntity extends AbstractSupplierProjectile{
    public float rotationVelocity = 0;
    public boolean returnToPlayer;

    public KunaiEntity(EntityType<? extends KunaiEntity> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public KunaiEntity(LivingEntity pShooter, Level pLevel, ItemStack thrownStackIn){
        super(EntityTypeRegistry.KUNAI.get(), pLevel, pShooter, thrownStackIn);
        this.setPos(pShooter.getX(), pShooter.getEyeY() - (double)0.1F, pShooter.getZ());
        this.setOwner(pShooter);
        if(pShooter instanceof Player){
            this.pickup = AbstractArrow.Pickup.ALLOWED;
        }
    }

    @Nullable
    public EntityHitResult findHitEntity(@NotNull Vec3 startVec, @NotNull Vec3 endVec){
        return this.returnToPlayer ? null : super.findHitEntity(startVec, endVec);
    }

    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public SoundEvent getReturnSound(){
        return SoundEvents.TRIDENT_RETURN;
    }

    public void spawnParticlesTrail(){
        if(this.shouldRender(this.getX(), this.getY(), this.getZ()) && !this.inGround){
            Vec3 delta = this.getDeltaMovement().normalize();
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
            final Vec3[] cachePos = {new Vec3(pos.x, pos.y, pos.z)};
            final Consumer<GenericParticle> target = p -> {
                Vec3 arrowPos = new Vec3(getX(), getY(), getZ());
                float lenBetweenArrowAndParticle = (float)(arrowPos.subtract(cachePos[0])).length();
                Vec3 vector = (arrowPos.subtract(cachePos[0]));
                if(lenBetweenArrowAndParticle > 0){
                    cachePos[0] = cachePos[0].add(vector);
                    p.setPosition(cachePos[0]);
                }
            };

            ParticleBuilder.create(TridotParticles.TRAIL)
                    .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(Pal.darkerGray.brighter()).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Interp.sineOut).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
                    .addTickActor(target)
                    .setGravity(0)
                    .setLifetime(20)
                    .repeat(this.level(), pos.x, pos.y, pos.z, 1);
        }
    }

    public void playerTouch(@NotNull Player pEntity){
        if(this.ownedBy(pEntity) || this.getOwner() == null){
            super.playerTouch(pEntity);
        }
    }

    public float getWaterInertia(){
        return 0.5F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z){
        return true;
    }
}