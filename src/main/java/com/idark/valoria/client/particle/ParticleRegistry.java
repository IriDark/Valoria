package com.idark.valoria.client.particle;

import com.idark.valoria.*;
import net.minecraft.core.particles.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.particle.world.type.*;

public class ParticleRegistry{

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Valoria.ID);

    public static RegistryObject<LodestoneWorldParticleType> SMOKE = PARTICLES.register("smoke", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SPHERE = PARTICLES.register("sphere", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SKULL = PARTICLES.register("skull", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> GLITTER = PARTICLES.register("glitter", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> GEODE_PARTICLE = PARTICLES.register("geode", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shadewood_leaf", LodestoneWorldParticleType::new);

    public static RegistryObject<SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLES.register(eventBus);
    }
}