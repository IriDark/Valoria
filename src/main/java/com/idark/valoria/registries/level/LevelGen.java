package com.idark.valoria.registries.level;

import com.idark.valoria.*;
import com.idark.valoria.registries.level.configurations.*;
import com.idark.valoria.registries.level.feature.*;
import com.idark.valoria.registries.level.modifier.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraftforge.common.world.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class LevelGen {
    public static void init(IEventBus eventBus) {
        FEATURES.register(eventBus);
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        PLACEMENT_MODIFIERS.register(eventBus);
    }

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Valoria.ID);
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Valoria.ID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.key(), Valoria.ID);
    public static final RegistryObject<PlacementModifierType<BiomeTagFilter>> BIOME_TAG = PLACEMENT_MODIFIERS.register("biome_tag", () -> typeConvert(BiomeTagFilter.CODEC));

    public static final RegistryObject<Feature<BlockStateConfiguration>> FALLEN_TREE = FEATURES.register("fallen_tree", () -> new FallenTreeFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>> CATTAIL = FEATURES.register("cattail", () -> new CattailFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<SimpleBlockConfiguration>> POT = FEATURES.register("simple_pot", () -> new PotFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> ABYSSAL_GLOWFERN = FEATURES.register("abyssal_glowfern", () -> new AbyssalGlowfernFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> VIOLET_SPROUT = FEATURES.register("violet_sprout", () -> new VioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> glowVioletSprout = FEATURES.register("glow_violet_sprout", () -> new GlowVioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TaintedRootsConfig>> TAINTED_ROOTS = FEATURES.register("tainted_roots", () -> new TaintedRootsFeature(TaintedRootsConfig.CODEC));
    public static final RegistryObject<Feature<SuspiciousStateConfiguration>> SUSPICIOUS_STATE = FEATURES.register("suspicious_state", () -> new SuspiciousStateFeature(SuspiciousStateConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SUSPICIOUS_ICEBERG = FEATURES.register("suspicious_iceberg", () -> new SuspiciousIcebergFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<FleshConfiguration>> FLESH_FEATURE = FEATURES.register("flesh_corruption", () -> new FleshFeature(FleshConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> VALORIA_MONSTER_ROOM = FEATURES.register("valoria_monster_room", () -> new ValoriaMonsterRoomFeature(NoneFeatureConfiguration.CODEC));

    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = registerKey(Registries.CONFIGURED_FEATURE, "shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_SHADEWOOD_TREE = registerKey(Registries.CONFIGURED_FEATURE, "fancy_shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> ELDRITCH_TREE = registerKey(Registries.CONFIGURED_FEATURE, "eldritch_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_ELDRITCH_TREE = registerKey(Registries.CONFIGURED_FEATURE, "fancy_eldritch_tree");

    public static final ResourceKey<LevelStem> VALORIA = registerKey(Registries.LEVEL_STEM, "the_valoria");
    public static final ResourceKey<Level> VALORIA_KEY = registerKey(Registries.DIMENSION, "the_valoria");
    public static final ResourceKey<DimensionType> VALORIA_TYPE = registerKey(Registries.DIMENSION_TYPE, "the_valoria");
    public static final ResourceKey<Biome> VOID_BARREN = registerKey(Registries.BIOME, "void_barren");
    public static final ResourceKey<Biome> BARREN_ECOTONE = registerKey(Registries.BIOME, "barren_ecotone");
    public static final ResourceKey<Biome> ECOTONE = registerKey(Registries.BIOME, "ecotone");
    public static final ResourceKey<Biome> COAST = registerKey(Registries.BIOME, "coast");
    public static final ResourceKey<Biome> OCEAN_WASTES = registerKey(Registries.BIOME, "ocean_wastes");
    public static final ResourceKey<Biome> SHADED_BARRENS = registerKey(Registries.BIOME, "shaded_barrens");
    public static final ResourceKey<Biome> SHADED_ECOTONE = registerKey(Registries.BIOME, "shaded_ecotone");
    public static final ResourceKey<Biome> SHADE_FOREST = registerKey(Registries.BIOME, "shade_forest");
    public static final ResourceKey<Structure> NECROMANCER_CRYPT = registerKey(Registries.STRUCTURE, "necromancer_crypt");

    public static RegistryObject<Codec<AddFeaturesByFilterBiomeModifier>> ADD_FEATURES_BY_FILTER = BIOME_MODIFIER_SERIALIZERS.register("add_features_by_filter", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("allowed_biomes").forGetter(AddFeaturesByFilterBiomeModifier::allowedBiomes),
                    Biome.LIST_CODEC.optionalFieldOf("denied_biomes").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::deniedBiomes),
                    Codec.FLOAT.optionalFieldOf("min_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::minimumTemperature),
                    Codec.FLOAT.optionalFieldOf("max_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::maximumTemperature),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesByFilterBiomeModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesByFilterBiomeModifier::step)
            ).apply(builder, AddFeaturesByFilterBiomeModifier::new)));

    public static <T> ResourceKey<T> registerKey(ResourceKey<? extends Registry<T>> pRegistryKey, String name) {
        return ResourceKey.create(pRegistryKey, new ResourceLocation(Valoria.ID, name));
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(Codec<P> codec) {
        return () -> codec;
    }
}