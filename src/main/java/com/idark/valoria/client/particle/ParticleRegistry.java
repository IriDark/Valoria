package com.idark.valoria.client.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.registries.*;
import pro.komaru.tridot.client.gfx.particle.type.*;

public class ParticleRegistry{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, Valoria.ID);

    public static DeferredHolder<ParticleType<?>, GenericParticleType> SMOKE = PARTICLES.register("smoke", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> SPHERE = PARTICLES.register("sphere", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> GLITTER = PARTICLES.register("glitter", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> FLESH = PARTICLES.register("flesh", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> VALORIA_FOG = PARTICLES.register("valoria_fog", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> ACID_SPIT = PARTICLES.register("acid_spit", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> NIHILITY_FLAME = PARTICLES.register("nihility_flame", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> DASH = PARTICLES.register("dash", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> DUST = PARTICLES.register("dust", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> SMASH_CIRCLE = PARTICLES.register("smash_circle", FlatGenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> INWARD_CIRCLE = PARTICLES.register("inward_circle", FlatGenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, GenericParticleType> CRUSHED_GROUND = PARTICLES.register("crushed_ground", FlatGenericParticleType::new);

    public static DeferredHolder<ParticleType<?>, GenericParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", GenericParticleType::new);
    public static DeferredHolder<ParticleType<?>, LeavesParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shade_leaf", LeavesParticleType::new);

    public static DeferredHolder<ParticleType<?>, SimpleParticleType> HEAL = PARTICLES.register("heal", () -> new SimpleParticleType(false));
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> FIREFLY = PARTICLES.register("firefly", () -> new SimpleParticleType(false));
    public static DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleRegistry.DASH.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.DUST.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SMOKE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SPHERE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.TRANSFORM_PARTICLE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE.get(), LeavesParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.CHOMP.get(), ChompParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SMASH_CIRCLE.get(), FlatGenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.INWARD_CIRCLE.get(), FlatGenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.CRUSHED_GROUND.get(), FlatGenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.FIREFLY.get(), FireflyParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.HEAL.get(), EndRodParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.VOID_GLITTER.get(), EndRodParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.FLESH.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.GLITTER.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.VALORIA_FOG.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.ACID_SPIT.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.NIHILITY_FLAME.get(), GenericParticleType.Factory::new);
    }

    public static void register(IEventBus eventBus){
        PARTICLES.register(eventBus);
    }
}