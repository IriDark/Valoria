package com.idark.valoria.core.datagen.worldgen;

import com.idark.valoria.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.*;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> amberOrePlaced = registerKey("amber_ore_placed");
    public static final ResourceKey<PlacedFeature> cobaltOrePlaced = registerKey("cobalt_ore_placed");
    public static final ResourceKey<PlacedFeature> sapphireOrePlaced = registerKey("sapphire_ore_placed");
    public static final ResourceKey<PlacedFeature> rubyOrePlaced = registerKey("ruby_ore_placed");
    public static final ResourceKey<PlacedFeature> jadeOrePlaced = registerKey("jade_ore_placed");
    public static final ResourceKey<PlacedFeature> pyratiteOrePlaced = registerKey("pyratite_ore_placed");
    public static final ResourceKey<PlacedFeature> wickedAmethystOrePlaced = registerKey("wicked_amethyst_ore_placed");
    public static final ResourceKey<PlacedFeature> pearliumOrePlaced = registerKey("pearlium_ore_placed");
    public static final ResourceKey<PlacedFeature> dormantCrystalsPlaced = registerKey("dormant_crystals_placed");
    public static final ResourceKey<PlacedFeature> longPotPlaced = registerKey("long_pot");
    public static final ResourceKey<PlacedFeature> smallPotPlaced = registerKey("small_pot");
    public static final ResourceKey<PlacedFeature> desertPotPlaced = registerKey("desert_pot");
    public static final ResourceKey<PlacedFeature> swampPotPlaced = registerKey("swamp_pot");
    public static final ResourceKey<PlacedFeature> limestonePlaced = registerKey("limestone_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, amberOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.amberOre), ModOrePlacement.commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        register(context, cobaltOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.cobaltOre), ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(6))));
        register(context, sapphireOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.sapphireOre), ModOrePlacement.commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(16))));
        register(context, rubyOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.rubyOre), ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(8))));
        register(context, jadeOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.jadeOre), ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(6))));
        register(context, pyratiteOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.pyratiteOre), ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        register(context, wickedAmethystOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.wickedAmethystOre), ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(42))));
        register(context, pearliumOrePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.pearliumOre), ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(42))));
        register(context, dormantCrystalsPlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.dormantCrystals), ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(42))));

        register(context, limestonePlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.LIMESTONE), ModOrePlacement.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(120))));
        register(context, longPotPlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.LONG_POT), List.of(CountPlacement.of(1), InSquarePlacement.spread()));
        register(context, smallPotPlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.SMALL_POT), List.of(CountPlacement.of(1), InSquarePlacement.spread()));
        register(context, desertPotPlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.DESERT_POT), List.of(CountPlacement.of(1), InSquarePlacement.spread()));
        register(context, swampPotPlaced, configuredFeatures.getOrThrow(ModConfiguredFeatures.SWAMP_POT), List.of(CountPlacement.of(3), InSquarePlacement.spread()));

    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Valoria.loc(name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
