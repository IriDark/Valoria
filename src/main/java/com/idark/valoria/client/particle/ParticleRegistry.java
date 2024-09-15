package com.idark.valoria.client.particle;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.types.ChompParticle;
import com.idark.valoria.client.particle.types.FireflyParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ParticleRegistry {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Valoria.ID);

    public static RegistryObject<LodestoneWorldParticleType> CUBE = PARTICLES.register("cube", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SMOKE = PARTICLES.register("smoke", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SPHERE = PARTICLES.register("sphere", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SKULL = PARTICLES.register("skull", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> GLITTER = PARTICLES.register("glitter", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> GEODE_PARTICLE = PARTICLES.register("geode", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shadewood_leaf", LodestoneWorldParticleType::new);

    public static RegistryObject<SimpleParticleType> HEAL = PARTICLES.register("heal", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FIREFLY = PARTICLES.register("firefly", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.CUBE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SMOKE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SPHERE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.TRANSFORM_PARTICLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.GEODE_PARTICLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SHADEWOOD_LEAF_PARTICLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.CHOMP.get(), ChompParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.FIREFLY.get(), FireflyParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.HEAL.get(), EndRodParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.VOID_GLITTER.get(), EndRodParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.GLITTER.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SKULL.get(), LodestoneWorldParticleType.Factory::new);
    }

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}