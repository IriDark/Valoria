package com.idark.valoria.client.particle;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import mod.maxbogomol.fluffy_fur.client.particle.*;
import mod.maxbogomol.fluffy_fur.client.particle.data.*;
import mod.maxbogomol.fluffy_fur.common.easing.*;
import mod.maxbogomol.fluffy_fur.registry.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import javax.annotation.*;
import java.awt.*;

import static net.minecraft.util.Mth.randomBetween;

public class ParticleEffects{
    public static ArcRandom arcRandom = new ArcRandom();

    public static void particles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            ParticleBuilder.create(FluffyFurParticles.WISP)
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
            ParticleBuilder.create(FluffyFurParticles.WISP)
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
            .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
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
            .setScaleData(GenericParticleData.create(0.35f, 0).setEasing(Easing.EXPO_IN, Easing.EXPO_IN_OUT).build())
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
            .flatRandomOffset(0.75, 0, 0.75)
            .setColorData(data)
            .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void trailMotionSparks(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource rand = level.getRandom();
            ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
            .setTransparencyData(GenericParticleData.create(1f, 0f).build())
            .setScaleData(GenericParticleData.create(0.15f, randomBetween(rand, 0.1f, 0.2f), 0).build())
            .setLifetime(9)
            .setVelocity((rand.nextDouble() / 32), 0.02f, (rand.nextDouble() / 32))
            .setColorData(data)
            .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void itemParticles(Level level, Vec3 pos, ColorParticleData data){
        if(level.isClientSide()){
            RandomSource random = level.getRandom();
            ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
            .setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
            .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
            .setLifetime(6)
            .setColorData(data)
            .setSpinData(SpinParticleData.create(0.5f * (float)((random.nextDouble() - 0.5D) * 2), 0).build())
            .setVelocity((random.nextDouble() / 30), 0.05f, (random.nextDouble() / 30))
            .spawn(level, pos.x, pos.y, pos.z);
        }
    }

    public static void spawnItemParticles(Level level, ItemEntity entity, @Nullable ParticleType<?> particle, ColorParticleData color){
        if(level.isClientSide()){
            RandomSource rand = level.getRandom();
            Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
            if(color != null){
                if(!entity.isInWater()){
                    Color particleColor = new Color(color.r1, color.g1, color.b1);
                    Color particleColorTo = new Color(color.r2, color.g2, color.b2);
                    if(level.isClientSide()){
                        RandomSource random = level.getRandom();
                        ParticleBuilder.create(particle != null ? particle : FluffyFurParticles.TINY_WISP.get())
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
                    ParticleBuilder.create(FluffyFurParticles.TINY_WISP)
                    .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.05f + arcRandom.randomValueUpTo(0.25f), arcRandom.randomValueUpTo(0.2f)).build())
                    .setLifetime(16)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.black, Pal.smoke).build())
                    .setSpinData(SpinParticleData.create(0.5f * (float)((rand.nextDouble() - 0.5D) * 2), 0).build())
                    .setVelocity((rand.nextDouble() / 30), 0.05f, (rand.nextDouble() / 30))
                    .spawn(level, pos.x, pos.y, pos.z);
                }
            }
        }
    }
}