package com.idark.darkrpg.world;
import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> COBALT_ORE = registerKey("cobalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER_ORE = registerKey("amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMETHYST_ORE = registerKey("amethyst_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE = registerKey("ruby_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPHIRE_ORE = registerKey("sapphire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GEODITE_STONE = registerKey("geodite_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GEODITE_DIRT = registerKey("geodite_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE = registerKey("limestone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest dirtReplaceables = new BlockMatchTest(Blocks.DIRT);

        List<OreConfiguration.TargetBlockState> CobaltOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.COBALT_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_COBALT_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> AmberOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.AMBER_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> AmethystOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.AMETHYST_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_AMETHYST_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> RubyOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.RUBY_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_RUBY_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> SapphireOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.SAPPHIRE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables,
                ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> GeoditeStone = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.GEODITE_STONE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> GeoditeDirt = List.of(OreConfiguration.target(dirtReplaceables,
                ModBlocks.GEODITE_DIRT.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> Limestone = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.LIMESTONE.get().defaultBlockState()));

        register(context, COBALT_ORE, Feature.ORE, new OreConfiguration(CobaltOres, 11));
        register(context, AMBER_ORE, Feature.ORE, new OreConfiguration(AmberOres, 9));
        register(context, AMETHYST_ORE, Feature.ORE, new OreConfiguration(AmethystOres, 8));
        register(context, RUBY_ORE, Feature.ORE, new OreConfiguration(RubyOres, 4));
        register(context, SAPPHIRE_ORE, Feature.ORE, new OreConfiguration(SapphireOres, 7));
        register(context, GEODITE_STONE, Feature.ORE, new OreConfiguration(GeoditeStone, 11));
        register(context, GEODITE_DIRT, Feature.ORE, new OreConfiguration(GeoditeDirt, 11));
        register(context, LIMESTONE, Feature.ORE, new OreConfiguration(Limestone, 19));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DarkRPG.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}