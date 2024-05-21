package com.idark.valoria.registries.levelgen;

import com.idark.valoria.*;
import com.idark.valoria.registries.levelgen.configurations.*;
import com.idark.valoria.registries.levelgen.feature.*;
import com.idark.valoria.registries.levelgen.modifier.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class LevelGen{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Valoria.ID);
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Valoria.ID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.key(), Valoria.ID);
    public static final RegistryObject<PlacementModifierType<BiomeTagFilter>> BIOME_TAG = PLACEMENT_MODIFIERS.register("biome_tag", () -> typeConvert(BiomeTagFilter.CODEC));
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = LevelGen.registerKey("shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_SHADEWOOD_TREE = LevelGen.registerKey("fancy_shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> ELDRITCH_TREE = LevelGen.registerKey("eldritch_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_ELDRITCH_TREE = LevelGen.registerKey("fancy_eldritch_tree");

    public static final RegistryObject<Feature<BlockStateConfiguration>> FALLEN_TREE = FEATURES.register("fallen_tree", () -> new FallenTreeFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>> CATTAIL = FEATURES.register("cattail", () -> new CattailFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> ABYSSAL_GLOWFERN = FEATURES.register("abyssal_glowfern", () -> new AbyssalGlowfernFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> VIOLET_SPROUT = FEATURES.register("violet_sprout", () -> new VioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> GLOW_VIOLET_SPROUT = FEATURES.register("glow_violet_sprout", () -> new GlowVioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TaintedRootsConfig>> TAINTED_ROOTS = FEATURES.register("tainted_roots", () -> new TaintedRootsFeature(TaintedRootsConfig.CODEC));

    public static final ResourceKey<LevelStem> VALORIA = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(Valoria.ID, "the_valoria"));
    public static final ResourceKey<Level> VALORIA_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Valoria.ID, "the_valoria"));
    public static final ResourceKey<Biome> SHADE_FOREST = ResourceKey.create(Registries.BIOME, new ResourceLocation(Valoria.ID, "shade_forest"));
    public static final ResourceKey<Biome> ECOTONE = ResourceKey.create(Registries.BIOME, new ResourceLocation(Valoria.ID, "ecotone"));
    public static final ResourceKey<Biome> VOID_BARREN = ResourceKey.create(Registries.BIOME, new ResourceLocation(Valoria.ID, "void_barren"));

    public static RegistryObject<Codec<AddFeaturesByFilterBiomeModifier>> ADD_FEATURES_BY_FILTER = BIOME_MODIFIER_SERIALIZERS.register("add_features_by_filter", () ->
    RecordCodecBuilder.create(builder -> builder.group(
    Biome.LIST_CODEC.fieldOf("allowed_biomes").forGetter(AddFeaturesByFilterBiomeModifier::allowedBiomes),
    Biome.LIST_CODEC.optionalFieldOf("denied_biomes").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::deniedBiomes),
    Codec.FLOAT.optionalFieldOf("min_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::minimumTemperature),
    Codec.FLOAT.optionalFieldOf("max_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::maximumTemperature),
    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesByFilterBiomeModifier::features),
    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesByFilterBiomeModifier::step)
    ).apply(builder, AddFeaturesByFilterBiomeModifier::new)));

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Valoria.ID, name));
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(Codec<P> codec){
        return () -> codec;
    }
}