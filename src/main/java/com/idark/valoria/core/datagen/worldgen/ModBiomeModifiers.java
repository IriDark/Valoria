package com.idark.valoria.core.datagen.worldgen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraftforge.common.world.*;
import net.minecraftforge.registries.*;

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
    public static final ResourceKey<BiomeModifier> ADD_CAVE_POT_SMALL = registerKey("add_cave_pot_small");
    public static final ResourceKey<BiomeModifier> ADD_CAVE_POT_LONG = registerKey("add_cave_pot_long");
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_POT_SMALL = registerKey("add_deepslate_pot_small");
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_POT_LONG = registerKey("add_deepslate_pot_long");
    public static final ResourceKey<BiomeModifier> ADD_DESERT_POT = registerKey("add_desert_pot");
    public static final ResourceKey<BiomeModifier> ADD_SWAMP_POT = registerKey("add_swamp_pot");
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

        context.register(ADD_LONG_POT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.longPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_SMALL_POT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.smallPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        
        context.register(ADD_CAVE_POT_SMALL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.cavePotSmallPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_CAVE_POT_LONG, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.cavePotLongPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DEEPSLATE_POT_SMALL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.deepslatePotSmallPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DEEPSLATE_POT_LONG, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.deepslatePotLongPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DESERT_POT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.DESERT_POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.desertPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_SWAMP_POT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(TagsRegistry.MOSSY_POT_SPAWN_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.swampPotPlaced)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Valoria.ID, name));
    }
}
