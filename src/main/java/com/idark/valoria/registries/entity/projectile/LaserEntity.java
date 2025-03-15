package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.registries.*;
import net.minecraft.network.syncher.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class LaserEntity extends Projectile{
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(LaserEntity.class, EntityDataSerializers.FLOAT);

    public LaserEntity(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    public LaserEntity(EntityType type, double x, double y, double z, Level worldIn) {
        this(type, worldIn);
        this.setPos(x, y, z);
    }

    public LaserEntity(Level worldIn, LivingEntity shooter) {
        this(EntityTypeRegistry.LASER.get(), shooter.getX(), shooter.getEyeY(), shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    public void spawnParticlesTrail(){
        Vec3 delta = this.getDeltaMovement().normalize();
        Vec3 pos = new Vec3(this.getX() + delta.x() * 0.00015, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.00015);
        final Vec3[] cachePos = {new Vec3(pos.x, pos.y  + 0.5f, pos.z)};
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
        .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE_TEXTURE)
        .setBehavior(TrailParticleBehavior.create().build())
        .setColorData(ColorParticleData.create(Col.fromHex("b8202d"), Col.fromHex("c4352b")).build())
        .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.sineOut).build())
        .setScaleData(GenericParticleData.create(0.85f).setEasing(Interp.exp5In).build())
        .addTickActor(target)
        .setGravity(0)
        .setLifetime(12)
        .repeat(this.level(), pos.x, pos.y, pos.z, 1);
    }

    protected void defineSynchedData() {
        this.entityData.define(DAMAGE,0f);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            LivingEntity owner = (LivingEntity) this.getOwner();
            entity.hurt(this.level().damageSources().mobProjectile(this, owner), getDamage());
            if(owner != null) this.doEnchantDamageEffects(owner, entity);
        }
    }

    @Override
    protected void onHit(HitResult p_37628_) {
        super.onHit(p_37628_);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    public void tick() {
        if(this.level().isClientSide()){
            this.spawnParticlesTrail();
        }

        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                double d0 = vec3.horizontalDistance();
                this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
                this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
                this.yRotO = this.getYRot();
                this.xRotO = this.getXRot();
            }
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();

            this.setYRot((float) (Mth.atan2(d5, d1) * (double) (180F / (float) Math.PI)));
            this.setXRot((float) (Mth.atan2(d6, d4) * (double) (180F / (float) Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f = 0.99F;
            float sqrt = (float)this.getDeltaMovement().length();
            if (sqrt < 0.1F) {
                this.discard();
            }
            this.setDeltaMovement(vec3.scale(f));

            this.setPos(d7, d2, d3);
        } else {
            this.discard();
        }
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public boolean hurt(DamageSource damageSource, float damage) {
        return false;
    }

}