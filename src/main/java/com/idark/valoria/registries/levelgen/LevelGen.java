package com.idark.valoria.registries.levelgen;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.levelgen.configurations.TaintedRootsConfig;
import com.idark.valoria.registries.levelgen.feature.*;
import com.idark.valoria.registries.levelgen.modifier.AddFeaturesByFilterBiomeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class LevelGen {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Valoria.MOD_ID);
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Valoria.MOD_ID);
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.key(), Valoria.MOD_ID);
    public static final RegistryObject<PlacementModifierType<BiomeTagFilter>> BIOME_TAG = PLACEMENT_MODIFIERS.register("biome_tag", () -> typeConvert(BiomeTagFilter.CODEC));
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = LevelGen.registerKey("shadewood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> FANCY_SHADEWOOD_TREE = LevelGen.registerKey("fancy_shadewood_tree");

    public static final RegistryObject<Feature<RandomPatchConfiguration>> CATTAIL = FEATURES.register("cattail", () -> new CattailFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> ABYSSAL_GLOWFERN = FEATURES.register("abyssal_glowfern", () -> new AbyssalGlowfernFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> VIOLET_SPROUT = FEATURES.register("violet_sprout", () -> new VioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TwistingVinesConfig>> GLOW_VIOLET_SPROUT = FEATURES.register("glow_violet_sprout", () -> new GlowVioletSproutFeature(TwistingVinesConfig.CODEC));
    public static final RegistryObject<Feature<TaintedRootsConfig>> TAINTED_ROOTS = FEATURES.register("tainted_roots", () -> new TaintedRootsFeature(TaintedRootsConfig.CODEC));

    public static final ResourceKey<LevelStem> VALORIA = ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(Valoria.MOD_ID, "the_valoria"));
    public static final ResourceKey<Level> VALORIA_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Valoria.MOD_ID, "the_valoria"));
    public static final ResourceKey<Biome> SHADE_FOREST = ResourceKey.create(Registries.BIOME, new ResourceLocation(Valoria.MOD_ID, "shade_forest"));
    public static final ResourceKey<Biome> VOID_BARREN = ResourceKey.create(Registries.BIOME, new ResourceLocation(Valoria.MOD_ID, "void_barren"));

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Valoria.MOD_ID, name));
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> typeConvert(Codec<P> codec) {
        return () -> codec;
    }

    public static RegistryObject<Codec<AddFeaturesByFilterBiomeModifier>> ADD_FEATURES_BY_FILTER = BIOME_MODIFIER_SERIALIZERS.register("add_features_by_filter", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("allowed_biomes").forGetter(AddFeaturesByFilterBiomeModifier::allowedBiomes),
                    Biome.LIST_CODEC.optionalFieldOf("denied_biomes").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::deniedBiomes),
                    Codec.FLOAT.optionalFieldOf("min_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::minimumTemperature),
                    Codec.FLOAT.optionalFieldOf("max_temperature").orElse(Optional.empty()).forGetter(AddFeaturesByFilterBiomeModifier::maximumTemperature),
                    PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesByFilterBiomeModifier::features),
                    GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesByFilterBiomeModifier::step)
            ).apply(builder, AddFeaturesByFilterBiomeModifier::new)));
}