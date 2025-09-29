package com.idark.valoria.client.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.client.gfx.particle.type.*;

public class ParticleRegistry{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Valoria.ID);

    public static RegistryObject<GenericParticleType> SMOKE = PARTICLES.register("smoke", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> SPHERE = PARTICLES.register("sphere", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> GLITTER = PARTICLES.register("glitter", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> FLESH = PARTICLES.register("flesh", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> VALORIA_FOG = PARTICLES.register("valoria_fog", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> ACID_SPIT = PARTICLES.register("acid_spit", GenericParticleType::new);

    public static RegistryObject<GenericParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", GenericParticleType::new);
    public static RegistryObject<LeavesParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shadewood_leaf", LeavesParticleType::new);

    public static RegistryObject<SimpleParticleType> HEAL = PARTICLES.register("heal", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FIREFLY = PARTICLES.register("firefly", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleRegistry.SMOKE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SPHERE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.TRANSFORM_PARTICLE.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE.get(), LeavesParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.CHOMP.get(), ChompParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.FIREFLY.get(), FireflyParticle.Factory::new);
        event.registerSpriteSet(ParticleRegistry.HEAL.get(), EndRodParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.VOID_GLITTER.get(), EndRodParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.FLESH.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.GLITTER.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.VALORIA_FOG.get(), GenericParticleType.Factory::new);
        event.registerSpriteSet(ParticleRegistry.ACID_SPIT.get(), GenericParticleType.Factory::new);
    }

    public static void register(IEventBus eventBus){
        PARTICLES.register(eventBus);
    }
}