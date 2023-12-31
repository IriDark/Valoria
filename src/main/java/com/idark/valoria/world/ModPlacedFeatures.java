package com.idark.valoria.world;

import com.idark.valoria.Valoria;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> COBALT_ORE_PLACED = registerKey("cobalt_ore_placed");
    public static final ResourceKey<PlacedFeature> AMBER_ORE_PLACED = registerKey("amber_ore_placed");
    public static final ResourceKey<PlacedFeature> AMETHYST_ORE_PLACED = registerKey("amethyst_ore_placed");
    public static final ResourceKey<PlacedFeature> RUBY_ORE_PLACED = registerKey("ruby_ore_placed");
    public static final ResourceKey<PlacedFeature> SAPPHIRE_ORE_PLACED = registerKey("sapphire_ore_placed");
    public static final ResourceKey<PlacedFeature> GEODITE_STONE_PLACED = registerKey("geodite_stone_placed");
    public static final ResourceKey<PlacedFeature> GEODITE_DIRT_PLACED = registerKey("geodite_dirt_placed");
    public static final ResourceKey<PlacedFeature> LIMESTONE_PLACED = registerKey("limestone_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, COBALT_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.COBALT_ORE),
                ModOrePlacement.commonOrePlacement(12,
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(40))));

        register(context, AMBER_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.AMBER_ORE),
                ModOrePlacement.commonOrePlacement(10,
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));

        register(context, AMETHYST_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.AMETHYST_ORE),
                ModOrePlacement.commonOrePlacement(7,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16))));

        register(context, RUBY_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.RUBY_ORE),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(8))));

        register(context, SAPPHIRE_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.SAPPHIRE_ORE),
                ModOrePlacement.commonOrePlacement(9,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16))));

        register(context, GEODITE_STONE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.GEODITE_STONE),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, GEODITE_DIRT_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.GEODITE_DIRT),
                ModOrePlacement.commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, LIMESTONE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.LIMESTONE),
                ModOrePlacement.commonOrePlacement(26,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Valoria.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}