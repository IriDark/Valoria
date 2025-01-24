package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.EntityTypeRegistry;
import com.idark.valoria.registries.SoundsRegistry;
import com.idark.valoria.util.Pal;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.TrailParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeHandler;
import mod.maxbogomol.fluffy_fur.client.screenshake.ScreenshakeInstance;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ThrownSpearEntity extends AbstractSupplierProjectile{
    private float explosive_radius;
    private boolean shouldExplode;
    private boolean isExploded;
    private Level.ExplosionInteraction interaction;

    public ThrownSpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn){
        super(EntityTypeRegistry.SPEAR.get(), worldIn, thrower, thrownStackIn);
        this.setItem(thrownStackIn);
    }

    public ThrownSpearEntity(EntityType<? extends ThrownSpearEntity> type, Level worldIn){
        super(type, worldIn);
    }

    @Override
    public void spawnParticlesTrail(){
        if(!this.inGround){
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

            ParticleBuilder.create(FluffyFurParticles.TRAIL)
                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                    .setBehavior(TrailParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(Pal.darkerGray.brighter()).build())
                    .setTransparencyData(GenericParticleData.create(0.65f, 0).setEasing(Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.5f).setEasing(Easing.EXPO_IN).build())
                    .addTickActor(target)
                    .setGravity(0)
                    .setLifetime(20)
                    .repeat(this.level(), pos.x, pos.y, pos.z, 5);
        }
    }


    public @NotNull SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public @NotNull SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.SPEAR_GROUND_IMPACT.get();
    }

    @Override
    public SoundEvent getReturnSound(){
        return SoundsRegistry.SPEAR_RETURN.get();
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
                ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(3).setIntensity(0.75f));
            }

            this.isExploded = true;
        }
    }
}