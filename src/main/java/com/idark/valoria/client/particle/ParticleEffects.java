package com.idark.valoria.client.particle;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.core.particles.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

import static net.minecraft.util.Mth.nextFloat;

public class ParticleEffects{

    public static void spawnItemParticles(Level level, ItemEntity entity, ParticleType<?> particle, ColorParticleData color){
        RandomSource rand = level.getRandom();
        Vec3 pos = new Vec3(entity.getX() + (rand.nextDouble() - 0.5f) / 6, entity.getY() + 0.4F, entity.getZ());
        if(particle != null && color != null){
            if(!entity.isInWater()){
                Color particleColor = new Color(color.r1, color.g1, color.b1);
                Color particleColorTo = new Color(color.r2, color.g2, color.b2);

                ParticleEffects.itemParticles(level, pos, ColorParticleData.create(particleColor, particleColorTo).build()).spawnParticles();
            }

            if(entity.getItem().is(TagsRegistry.SMOKE_PARTICLE)){
                ParticleEffects.itemParticles(level, pos, ColorParticleData.create(Color.black, Pal.smoke).build()).getBuilder().setLifetime(16).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).spawn(level, pos.x, pos.y, pos.z);
            }
        }
    }

    public static WorldParticleBuilder particleBloom(Level level, WorldParticleBuilder builder, int lifetime){
        var rand = level.random;
        return builder
        .setTransparencyData(GenericParticleData.create(0.35f, 0f).build())
        .setScaleData(GenericParticleData.create(0.15f, RandomHelper.randomBetween(rand, 0.08f, 0.2f), 0).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build())
        .setLifetime(lifetime)
        .enableNoClip();
    }

    public static ParticleEffectSpawner trailMotionSparks(Level level, Vec3 pos, ColorParticleData colorData){
        return trailMotionSparks(level, pos, colorData, new WorldParticleOptions(LodestoneParticleRegistry.SMOKE_PARTICLE));
    }

    public static ParticleEffectSpawner trailMotionSparks(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return trailMotionSparks(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner trailMotionSparks(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var lengthData = GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build();
        var builder = builderSupplier.apply(options.setBehaviorIfDefault(new SparkBehaviorComponent(lengthData)));
        var bloomBuilder = builderSupplier.apply(new WorldParticleOptions(LodestoneParticleRegistry.SMOKE_PARTICLE));
        return trailMotionSparks(level, pos, builder, bloomBuilder);
    }

    public static ParticleEffectSpawner trailMotionSparks(Level level, Vec3 pos, WorldParticleBuilder builder, WorldParticleBuilder bloomBuilder){
        RandomSource rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final WorldParticleBuilder sparkParticleBuilder = builder
        .setTransparencyData(GenericParticleData.create(1f, 0f).build())
        .setScaleData(GenericParticleData.create(0.15f, RandomHelper.randomBetween(rand, 0.1f, 0.2f), 0).build())
        .setLifetime(9)
        .setMotion((rand.nextDouble() / 32), 0.02f, (rand.nextDouble() / 32))
        .setSpinData(SpinParticleData.create((0.5f * (float)((rand.nextDouble() - 0.5D) * 2))).build())
        .enableNoClip();
        final WorldParticleBuilder bloomParticleBuilder = particleBloom(level, bloomBuilder, 25).setSpinData(spinData);
        return new ParticleEffectSpawner(level, pos, sparkParticleBuilder, bloomParticleBuilder);
    }

    public static ParticleEffectSpawner leafParticle(Level level, Vec3 pos, ColorParticleData colorData){
        return leafParticle(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE));
    }

    public static ParticleEffectSpawner leafParticle(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return leafParticle(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner leafParticle(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var lengthData = GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build();
        var builder = builderSupplier.apply(options.setBehaviorIfDefault(new SparkBehaviorComponent(lengthData)));
        return leafParticle(level, pos, builder);
    }

    public static ParticleEffectSpawner leafParticle(Level level, Vec3 pos, WorldParticleBuilder builder){
        RandomSource rand = level.getRandom();
        final WorldParticleBuilder transformParticleBuilder = builder
        .setMotion(((rand.nextDouble() - 0.5D) / 6), ((rand.nextDouble() - 1.25D) / 3), ((rand.nextDouble() - 0.5D) / 6))
        .setTransparencyData(GenericParticleData.create(0.75f, 0f).build())
        .setScaleData(GenericParticleData.create(0.15f, RandomHelper.randomBetween(rand, 0.08f, 0.14f), 0).setEasing(Easing.EXPO_IN, Easing.EXPO_IN_OUT).build())
        .setLifetime(21)
        .setSpinData(SpinParticleData.create(((float)Math.toRadians(rand.nextBoolean() ? -2 : 4))).build())
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, transformParticleBuilder);
    }


    public static ParticleEffectSpawner transformParticle(Level level, Vec3 pos, ColorParticleData colorData){
        return transformParticle(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.TRANSFORM_PARTICLE));
    }

    public static ParticleEffectSpawner transformParticle(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return transformParticle(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner transformParticle(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return transformParticle(level, pos, builder);
    }

    public static ParticleEffectSpawner transformParticle(Level level, Vec3 pos, WorldParticleBuilder builder){
        RandomSource rand = level.getRandom();
        final WorldParticleBuilder transformParticleBuilder = builder
        .setRandomMotion(0.025f)
        .setTransparencyData(GenericParticleData.create(0.45f, 0f).build())
        .setScaleData(GenericParticleData.create(0.35f, 0).setEasing(Easing.EXPO_IN, Easing.EXPO_IN_OUT).build())
        .setLifetime(16)
        .setSpinData(SpinParticleData.create(0.5f * (float)((rand.nextDouble() - 0.5D) * 2)).build())
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, transformParticleBuilder);
    }

    public static ParticleEffectSpawner particles(Level level, Vec3 pos, ColorParticleData colorData){
        return particles(level, pos, colorData, new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE));
    }

    public static ParticleEffectSpawner particles(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return particles(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner particles(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return particles(level, pos, builder);
    }

    public static ParticleEffectSpawner particles(Level level, Vec3 pos, WorldParticleBuilder builder){
        final WorldParticleBuilder particleBuilder = builder
        .setTransparencyData(GenericParticleData.create(1.25f, 0f).build())
        .setScaleData(GenericParticleData.create(0.2f, 0.1f, 0).build())
        .setLifetime(6)
        .setMotion(0, 0.08f, 0)
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, particleBuilder);
    }

    public static ParticleEffectSpawner fireParticles(Level level, Vec3 pos, ColorParticleData colorData){
        return fireParticles(level, pos, colorData, new WorldParticleOptions(LodestoneParticleRegistry.WISP_PARTICLE));
    }

    public static ParticleEffectSpawner fireParticles(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return fireParticles(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner fireParticles(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return fireParticles(level, pos, builder);
    }

    public static ParticleEffectSpawner fireParticles(Level level, Vec3 pos, WorldParticleBuilder builder){
        RandomSource random = level.getRandom();
        final WorldParticleBuilder particleBuilder = builder
        .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
        .setScaleData(GenericParticleData.create(0.32f + RandomUtil.randomValueUpTo(0.2f), RandomUtil.randomValueUpTo(0.2f)).build())
        .setLifetime(21)
        .setSpinData(SpinParticleData.create(0.5f, 0).build())
        .setMotion((random.nextDouble() - 0.2D) / 30, (random.nextDouble() + 0.2D) / 6, (random.nextDouble() - 0.2D) / 30)
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, particleBuilder);
    }

    public static ParticleEffectSpawner smokeParticles(Level level, Vec3 pos, ColorParticleData colorData){
        return smokeParticles(level, pos, colorData, new WorldParticleOptions(LodestoneParticleRegistry.SMOKE_PARTICLE));
    }

    public static ParticleEffectSpawner smokeParticles(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return smokeParticles(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner smokeParticles(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return smokeParticles(level, pos, builder);
    }

    public static ParticleEffectSpawner smokeParticles(Level level, Vec3 pos, WorldParticleBuilder builder){
        RandomSource random = level.getRandom();
        final WorldParticleBuilder particleBuilder = builder
        .setTransparencyData(GenericParticleData.create(0.2f, 0f).build())
        .setScaleData(GenericParticleData.create(0.25f + RandomUtil.randomValueUpTo(0.25f), RandomUtil.randomValueUpTo(0.2f)).build())
        .setLifetime(32)
        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
        .setSpinData(SpinParticleData.create(0.75f, 0).build())
        .setMotion((random.nextDouble() - 0.2D) / 30, (random.nextDouble() + 0.2D) / 4, (random.nextDouble() - 0.2D) / 30)
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, particleBuilder);
    }

    public static ParticleEffectSpawner packetSmokeParticles(Level level, Vec3 pos, ColorParticleData colorData){
        return packetSmokeParticles(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.SMOKE));
    }

    public static ParticleEffectSpawner packetSmokeParticles(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return packetSmokeParticles(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner packetSmokeParticles(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return packetSmokeParticles(level, pos, builder);
    }

    public static ParticleEffectSpawner packetSmokeParticles(Level level, Vec3 pos, WorldParticleBuilder builder){
        Random random = new Random();
        final WorldParticleBuilder particleBuilder = builder
        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT.withDepthFade())
        .setTransparencyData(GenericParticleData.create(random.nextFloat(0, 0.6f), 0f).build())
        .setScaleData(GenericParticleData.create(0.92f, 0f).build())
        .setLifetime(95 + random.nextInt(100))
        .setRandomMotion(0.125f, 0, 0.125)
        .setRandomOffset(0.025f);

        return new ParticleEffectSpawner(level, pos, particleBuilder);
    }

    public static ParticleEffectSpawner itemParticles(Level level, Vec3 pos, ColorParticleData colorData){
        return itemParticles(level, pos, colorData, new WorldParticleOptions(LodestoneParticleRegistry.SPARK_PARTICLE));
    }

    public static ParticleEffectSpawner itemParticles(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options){
        return itemParticles(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner itemParticles(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier){
        var builder = builderSupplier.apply(options);
        return itemParticles(level, pos, builder);
    }

    public static ParticleEffectSpawner itemParticles(Level level, Vec3 pos, WorldParticleBuilder builder){
        RandomSource random = level.getRandom();
        final WorldParticleBuilder particleBuilder = builder
        .setTransparencyData(GenericParticleData.create(0.25f, 0f).build())
        .setScaleData(GenericParticleData.create(0.05f + RandomUtil.randomValueUpTo(0.25f), RandomUtil.randomValueUpTo(0.2f)).build())
        .setLifetime(6)
        .setSpinData(SpinParticleData.create(0.5f * (float)((random.nextDouble() - 0.5D) * 2), 0).build())
        .setMotion((random.nextDouble() / 30), 0.05f, (random.nextDouble() / 30))
        .disableNoClip();
        return new ParticleEffectSpawner(level, pos, particleBuilder);
    }

}
