package com.idark.valoria.client.particle;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.particle.types.ShadewoodLeafParticleType;
import com.idark.valoria.client.particle.types.SparkleParticleType;
import com.idark.valoria.client.particle.types.SphereParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Valoria.ID);

    public static RegistryObject<SphereParticleType> SPHERE = PARTICLES.register("sphere", SphereParticleType::new);

    public static RegistryObject<SparkleParticleType> GLOWING_SPHERE = PARTICLES.register("glowing_sphere", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> SKULL = PARTICLES.register("skull", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> GLITTER = PARTICLES.register("glitter", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> TRANSFORM_PARTICLE = PARTICLES.register("transform", SparkleParticleType::new);
    public static RegistryObject<SparkleParticleType> GEODE_PARTICLE = PARTICLES.register("geode", SparkleParticleType::new);
    public static RegistryObject<ShadewoodLeafParticleType> SHADEWOOD_LEAF_PARTICLE = PARTICLES.register("shadewood_leaf", ShadewoodLeafParticleType::new);

    public static RegistryObject<SimpleParticleType> VOID_GLITTER = PARTICLES.register("void_glitter",  () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CHOMP = PARTICLES.register("chomp", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}