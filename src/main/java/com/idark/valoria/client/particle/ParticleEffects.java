package com.idark.valoria.client.particle;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import pro.komaru.tridot.client.gfx.*;
import pro.komaru.tridot.client.gfx.particle.*;
import pro.komaru.tridot.client.gfx.particle.behavior.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import javax.annotation.*;
import java.util.function.*;

import static net.minecraft.util.Mth.randomBetween;

public class ParticleEffects{
    public static ArcRandom arcRandom = Tmp.rnd;

    public static void particles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            ParticleBuilder.create(TridotParticles.WISP)
                    .setColorData(data)
                    .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
                    .setLifetime(6)
                    .addVelocity(0, 0.08f, 0)
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void fireParticles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource random = level.getRandom();
            ParticleBuilder.create(TridotParticles.WISP)
                    .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.32f + arcRandom.randomValueUpTo(0.2f), arcRandom.randomValueUpTo(0.2f)).build())
                    .setLifetime(21)
                    .setColorData(data)
                    .setSpinData(SpinParticleData.create(0.5f, 0).build())
                    .setVelocity((random.nextDouble() - 0.2D) / 30, (random.nextDouble() + 0.2D) / 6, (random.nextDouble() - 0.2D) / 30)
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void smokeParticles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource random = level.getRandom();
            ParticleBuilder.create(ParticleRegistry.SPHERE)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.25f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
                    .setLifetime(32)
                    .setColorData(data)
                    .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                    .setSpinData(SpinParticleData.create(0.75f, 0).build())
                    .setVelocity((random.nextDouble() - 0.2D) / 30, (random.nextDouble() + 0.2D) / 4, (random.nextDouble() - 0.2D) / 30)
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void transformParticle(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource rand = level.getRandom();
            ParticleBuilder.create(ParticleRegistry.TRANSFORM_PARTICLE)
                    .randomVelocity(0.025f)
                    .setColorData(data)
                    .setTransparencyData(GenericParticleData.create(0.45f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0).setEasing(Interp.sineIn, Interp.sineOut).build())
                    .setLifetime(16)
                    .setSpinData(SpinParticleData.create(0.5f * (float)((rand.nextDouble() - 0.5D) * 2)).build())
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void leafParticle(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            ParticleBuilder.create(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE)
                    .setParticleRenderType(ParticleRenderType.PARTICLE_SHEET_OPAQUE)
                    .setLightData(LightParticleData.DEFAULT)
                    .flatRandomOffset(1, 0, 1)
                    .setColorData(data)
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void trailMotionSparks(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource rand = level.getRandom();
            ParticleBuilder.create(TridotParticles.TINY_WISP)
                    .setTransparencyData(GenericParticleData.create(1f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.125f, randomBetween(rand, 0.1f, 0.2f), 0).build())
                    .setLifetime(15)
                    .setVelocity((rand.nextDouble() / 32), 0.02f, (rand.nextDouble() / 32))
                    .setColorData(data)
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void fancyTrail(Level level, Vec3 vec3, Vec3 pos){
        final Consumer<GenericParticle> target = p -> {
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
                .setColorData(ColorParticleData.create(Pal.infernal, Pal.darkMagenta).build())
                .setTransparencyData(GenericParticleData.create(0.6f, 0).setEasing(Interp.sineOut).build())
                .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
                .addTickActor(target)
                .setLifetime(20)
                .randomVelocity(0.25, 0.02f, 0.25)
                .randomOffset(0.15f)
                .setVelocity(vec3.x, vec3.y, vec3.z)
                .setFriction(0.8f)
                .repeat(level, pos.x, pos.y, pos.z, 15);
    }

    public static void itemParticles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource random = level.getRandom();
            ParticleBuilder.create(TridotParticles.TINY_WISP)
                    .setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
                    .setLifetime(6)
                    .setColorData(data)
                    .setSpinData(SpinParticleData.create(0.5f * (float)((random.nextDouble() - 0.5D) * 2), 0).build())
                    .setVelocity((random.nextDouble() / 30), 0.05f, (random.nextDouble() / 30))
                    .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void swirlTrail(Level level, Vec3 pos, ColorParticleData data) {
        Vec3 vec3 = Vec3.ZERO;
        final Consumer<GenericParticle> fancyTarget = p -> {
            Vec3 particlePos = p.getPos();
            Vec3 toCenter = vec3.subtract(particlePos).normalize();

            double angle = (p.getAge() + p.hashCode() % 20) * 0.15;
            double radius = 0.05;

            double swirlX = Math.cos(angle) * radius;
            double swirlZ = Math.sin(angle) * radius;

            Vec3 swirlVec = new Vec3(swirlX, 0, swirlZ);
            Vec3 attraction = toCenter.scale(0.02);
            Vec3 finalMotion = swirlVec.add(attraction);

            p.setSpeed(p.getSpeed().add(finalMotion));
        };

        ParticleBuilder.create(TridotParticles.TRAIL)
        .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
        .setBehavior(TrailParticleBehavior.create().build())
        .setColorData(data)
        .setTransparencyData(GenericParticleData.create(1f, 0).setEasing(Interp.sineOut).build())
        .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
        .addTickActor(fancyTarget)
        .setLifetime(15)
        .randomVelocity(0.25f, 0.25f, 0.25f)
        .setVelocity(vec3.x, 0, vec3.z)
        .repeat(level, pos.x, pos.y, pos.z, 5);
    }

    public static void smoothTrail(Level level, Consumer<GenericParticle> target, Vec3 pos, ColorParticleData data){
        ParticleBuilder.create(TridotParticles.TRAIL)
                .setRenderType(TridotRenderTypes.ADDITIVE_PARTICLE_TEXTURE)
                .setBehavior(TrailParticleBehavior.create().build())
                .setColorData(data)
                .setTransparencyData(GenericParticleData.create(1, 0).setEasing(Interp.bounceOut).build())
                .setScaleData(GenericParticleData.create(0.5f).setEasing(Interp.sineIn).build())
                .addTickActor(target)
                .setGravity(0)
                .setLifetime(30)
                .repeat(level, pos.x, pos.y, pos.z, 5);
    }

    public static void spawnItemParticles(Level level, ItemEntity entity, @Nullable ParticleType<?> particle, ColorParticleData color){
        if(level.isClientSide()){
            RandomSource rand = level.getRandom();
            Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
            if(color != null){
                if(!entity.isInWater()){
                    Col particleColor = new Col(color.r1, color.g1, color.b1);
                    Col particleColorTo = new Col(color.r2, color.g2, color.b2);
                    if(level.isClientSide()){
                        RandomSource random = level.getRandom();
                        ParticleBuilder.create(particle != null ? particle : TridotParticles.TINY_WISP.get())
                                .setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
                                .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.15f), arcRandom.randomValueUpTo(0.2f)).build())
                                .setLifetime(6)
                                .setColorData(ColorParticleData.create(particleColor, particleColorTo).build())
                                .setSpinData(SpinParticleData.create(0.5f * (float)((random.nextDouble() - 0.5D) * 2), 0).build())
                                .setVelocity((random.nextDouble() / 30), 0.05f, (random.nextDouble() / 30))
                                .spawn(level, pos.x, pos.y, pos.z);
                    }
                }

                if(entity.getItem().is(TagsRegistry.SMOKE_PARTICLE)){
                    ParticleBuilder.create(TridotParticles.TINY_WISP)
                            .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
                            .setLifetime(16)
                            .setRenderType(TridotRenderTypes.TRANSLUCENT_PARTICLE)
                            .setColorData(ColorParticleData.create(Col.black, Pal.smoke).build())
                            .setSpinData(SpinParticleData.create(0.5f * (float)((rand.nextDouble() - 0.5D) * 2), 0).build())
                            .setVelocity((rand.nextDouble() / 30), 0.05f, (rand.nextDouble() / 30))
                            .spawn(level, pos.x, pos.y, pos.z);
                }
            }
        }
    }
}