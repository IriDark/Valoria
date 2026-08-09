package com.idark.valoria.core.datagen.worldgen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.modifier.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraftforge.common.world.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_AMBER_ORE = registerKey("add_amber_ore");
    public static final ResourceKey<BiomeModifier> ADD_COBALT_ORE = registerKey("add_cobalt_ore");
    public static final ResourceKey<BiomeModifier> ADD_SAPPHIRE_ORE = registerKey("add_sapphire_ore");
    public static final ResourceKey<BiomeModifier> ADD_RUBY_ORE = registerKey("add_ruby_ore");
    public static final ResourceKey<BiomeModifier> ADD_JADE_ORE = registerKey("add_jade_ore");
    public static final ResourceKey<BiomeModifier> ADD_PYRATITE_ORE = registerKey("add_pyratite_ore");
    public static final ResourceKey<BiomeModifier> ADD_WICKED_AMETHYST_ORE = registerKey("add_wicked_amethyst_ore");
    public static final ResourceKey<BiomeModifier> ADD_PEARLIUM_ORE = registerKey("add_pearlium_ore");
    public static final ResourceKey<BiomeModifier> ADD_DORMANT_CRYSTALS = registerKey("add_dormant_crystals");
    public static final ResourceKey<BiomeModifier> ADD_LONG_POT = registerKey("add_long_pot");
    public static final ResourceKey<BiomeModifier> ADD_SMALL_POT = registerKey("add_small_pot");
    public static final ResourceKey<BiomeModifier> ADD_LIMESTONE = registerKey("add_limestone_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        context.register(ADD_AMBER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.amberOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_COBALT_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.cobaltOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_SAPPHIRE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.sapphireOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_RUBY_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.rubyOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_JADE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.IS_VALORIA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.jadeOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_PYRATITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.IS_VALORIA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.pyratiteOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_WICKED_AMETHYST_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.IS_VALORIA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.wickedAmethystOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_PEARLIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.IS_VALORIA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.pearliumOrePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_DORMANT_CRYSTALS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.IS_VALORIA),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.dormantCrystalsPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_LIMESTONE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.limestonePlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_LONG_POT, new AddFeaturesByFilterBiomeModifier(
                biomes.getOrThrow(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft:is_overworld"))),
                Optional.of(biomes.getOrThrow(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft:spawns_cold_variant_frogs")))),
                Optional.empty(),
                Optional.empty(),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.longPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_SMALL_POT, new AddFeaturesByFilterBiomeModifier(
                biomes.getOrThrow(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft:is_overworld"))),
                Optional.of(biomes.getOrThrow(TagKey.create(Registries.BIOME, new ResourceLocation("minecraft:spawns_cold_variant_frogs")))),
                Optional.empty(),
                Optional.empty(),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.smallPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Valoria.ID, name));
    }
}
