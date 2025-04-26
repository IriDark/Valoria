package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class SpellProjectile extends AbstractProjectile{
    private static final EntityDataAccessor<Integer> TYPE = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    public Col color;
    private boolean ignite;
    private int fireSeconds;
    public SpellProjectile(EntityType<? extends AbstractArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        discardOnHit = true;
    }

    public void setColor(Col variant) {
        this.color = variant;
        this.entityData.set(TYPE, variant.rgba8888());
    }

    public Col getColor() {
        return new Col(this.entityData.get(TYPE));
    }

    @Override
    public SoundEvent getHitGroundSoundEvent(){
        return SoundsRegistry.MAGIC_HIT.get();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent(){
        return SoundsRegistry.MAGIC_HIT.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, Col.white.rgba8888());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound){
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Color", color.rgba8888());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound){
        super.readAdditionalSaveData(pCompound);
        this.color = new Col(pCompound.getInt("Color"));
    }

    public SpellProjectile(Level pLevel, LivingEntity thrower, int damage){
        super(EntityTypeRegistry.SPELL.get(), pLevel, thrower, damage);
        discardOnHit = true;
    }

    public void igniteOnHit(int seconds) {
        this.ignite = true;
        this.fireSeconds = seconds;
    }

    @Override
    public void onHitEntity(EntityHitResult result){
        if(this.ignite){
            Entity entity = result.getEntity();
            entity.setSecondsOnFire(this.fireSeconds);
        }

        super.onHitEntity(result);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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

            ParticleBuilder.create(TridotParticles.TRAIL)
            .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
            .setBehavior(TrailParticleBehavior.create().build())
            .setColorData(ColorParticleData.create(getColor()).build())
            .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.sineOut).build())
            .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.exp5In).build())
            .addTickActor(target)
            .setGravity(0)
            .setLifetime(20)
            .spawn(this.level(), pos.x, pos.y, pos.z);
        }
    }
}