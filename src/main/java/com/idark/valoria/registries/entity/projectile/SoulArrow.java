package com.idark.valoria.registries.entity.projectile;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.api.interfaces.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.common.registry.entity.projectiles.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.function.*;

public class SoulArrow extends AbstractTridotArrow implements TexturedArrow{

    public SoulArrow(EntityType<? extends AbstractTridotArrow> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    public SoulArrow(Level pLevel, LivingEntity pShooter, ItemStack thrown){
        super(EntityTypeRegistry.SOUL_ARROW.get(), pLevel, pShooter, thrown, 1);
    }

    @Override
    public void tick(){
        super.tick();
        Utils.Physics.homingTo(0.05f, this, this.level(), this.getOwner(), new AABB(this.getX() - 4.5f, this.getY() - 4.5f, this.getZ() - 4.5f, this.getX() + 4.5f, this.getY() + 4.5f, this.getZ() + 4.5f));
    }

    @Override
    public void spawnParticlesTrail(){
        if(!this.inGround){
            Vec3 vec3 = Vec3.ZERO;
            Vec3 delta = this.getDeltaMovement().normalize();
            Vec3 pos = new Vec3(this.getX() + delta.x() * 0.05415, this.getY() + delta.y() * 0.00015, this.getZ() + delta.z() * 0.05415);
            final Consumer<GenericParticle> pT = p -> {
                double dX = vec3.x();
                double dY = vec3.y();
                double dZ = vec3.z();

                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                float speed = 0.1f + (p.getAge() * 0.001f);
                double x = Math.sin(pitch) * Math.cos(yaw) * speed;
                double y = Math.cos(pitch) * speed;
                double z = Math.sin(pitch) * Math.sin(yaw) * speed;

                p.setSpeed(p.getSpeed().add(-x, -y, -z));
            };

            ParticleBuilder.create(TridotParticles.TRAIL)
            .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
            .setBehavior(TrailParticleBehavior.create().build())
            .setColorData(ColorParticleData.create(Col.white, Col.fromHex("19419b")).build())
            .setTransparencyData(GenericParticleData.create(0.5f, 0).setEasing(Interp.sineOut).build())
            .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
            .addTickActor(pT)
            .setLifetime(10)
            .randomVelocity(0.25, 0f, 0.25)
            .randomOffset(0.15f)
            .setVelocity(vec3.x, 0, vec3.z)
            .setFriction(0.8f)
            .repeat(this.level(), pos.x, pos.y, pos.z, 10);

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


            ParticleEffects.smoothTrail(this.level(), target, pos, ColorParticleData.create(Col.fromHex("19419b"), Col.fromHex("0000af")).build());
        }
    }

    @Override
    public ResourceLocation getTexture(){
        return new ResourceLocation(Valoria.ID, "textures/entity/projectile/arrow/soul_arrow.png");
    }
}
