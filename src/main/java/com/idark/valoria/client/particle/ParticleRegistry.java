package com.idark.valoria.client.particle;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.types.*;
import net.minecraft.client.*;
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
    public static RegistryObject<GenericParticleType> SKULL = PARTICLES.register("skull", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> GLITTER = PARTICLES.register("glitter", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", GenericParticleType::new);
    public static RegistryObject<GenericParticleType> GEODE_PARTICLE = PARTICLES.register("geode", GenericParticleType::new);
    public static RegistryObject<LeavesParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shadewood_leaf", LeavesParticleType::new);

    public static RegistryObject<SimpleParticleType> HEAL = PARTICLES.register("heal", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FIREFLY = PARTICLES.register("firefly", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SMOKE.get(), GenericParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SPHERE.get(), GenericParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.TRANSFORM_PARTICLE.get(), GenericParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.GEODE_PARTICLE.get(), GenericParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE.get(), LeavesParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.CHOMP.get(), ChompParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.FIREFLY.get(), FireflyParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.HEAL.get(), EndRodParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.VOID_GLITTER.get(), EndRodParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.GLITTER.get(), GenericParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SKULL.get(), GenericParticleType.Factory::new);
    }

    public static void register(IEventBus eventBus){
        PARTICLES.register(eventBus);
    }
}