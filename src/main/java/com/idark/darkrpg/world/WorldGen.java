package com.idark.darkrpg.world;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WorldGen {
    public static ResourceKey<ConfiguredFeature<?, ?>> SHADEWOOD_TREE = WorldGen.registerKey("shadewood_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(DarkRPG.MOD_ID, name));
    }

    /*static List<ConfiguredFeature<?, ?>> ORES = new ArrayList<>();
    static ConfiguredFeature<?, ?> COBALT_ORE_GEN, AMBER_ORE_GEN, AMETHYST_ORE_GEN, RUBY_ORE_GEN, SAPPHIRE_ORE_GEN, GEODITE_DIRT_GEN, GEODITE_STONE_GEN, LIMESTONE_GEN, QUICKSAND_GEN;
    static RuleTest IN_STONE = new TagMatchRuleTest(Tags.Blocks.STONE);
    static RuleTest IN_DIRT = new TagMatchRuleTest(Tags.Blocks.DIRT);
    static RuleTest IN_SAND = new TagMatchRuleTest(Tags.Blocks.SAND);

    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SHADEWOOD_TREE;
	
    static IStructurePieceType register(IStructurePieceType type, String name) {
        net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.STRUCTURE_PIECE, new ResourceLocation(DarkRPG.MOD_ID, name), type);
        return type;
    }

    static <C extends IFeatureConfig, F extends Feature<C>> ConfiguredFeature<C, F> register(ConfiguredFeature<C, F> feature, String name) {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(DarkRPG.MOD_ID, name), feature);
        return feature;
    }

    static <C extends IFeatureConfig, S extends Structure<C>> StructureFeature<C, S> register(StructureFeature<C, S> feature, String name) {
        WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(DarkRPG.MOD_ID, name), feature);
        return feature;
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static void init() {
        COBALT_ORE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.COBALT_ORE.get().defaultBlockState(), Config.COBALT_VEIN_SIZE.get()))
		.squared()
        .count(Config.COBALT_VEIN_COUNT.get())
        .range(Config.COBALT_MAX_Y.get()), "cobalt_ore");
        if (Config.COBALT_ENABLED.get()) ORES.add(COBALT_ORE_GEN);
	
        AMBER_ORE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.AMBER_ORE.get().defaultBlockState(), Config.AMBER_VEIN_SIZE.get()))
		.squared()
        .count(Config.AMBER_VEIN_COUNT.get())
        .range(Config.AMBER_MAX_Y.get()), "amber_ore");
        if (Config.AMBER_ENABLED.get()) ORES.add(AMBER_ORE_GEN);
	
        AMETHYST_ORE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.AMETHYST_ORE.get().defaultBlockState(), Config.AMETHYST_VEIN_SIZE.get()))
		.squared()
        .count(Config.AMETHYST_VEIN_COUNT.get())
        .range(Config.AMETHYST_MAX_Y.get()), "amethyst_ore");
        if (Config.AMETHYST_ENABLED.get()) ORES.add(AMETHYST_ORE_GEN);
	
        RUBY_ORE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.RUBY_ORE.get().defaultBlockState(), Config.RUBY_VEIN_SIZE.get()))
		.squared()
        .count(Config.RUBY_VEIN_COUNT.get())
        .range(Config.RUBY_MAX_Y.get()), "ruby_ore");
        if (Config.RUBY_ENABLED.get()) ORES.add(RUBY_ORE_GEN);
	
        SAPPHIRE_ORE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.SAPPHIRE_ORE.get().defaultBlockState(), Config.SAPPHIRE_VEIN_SIZE.get()))
		.squared()
        .count(Config.SAPPHIRE_VEIN_COUNT.get())
        .range(Config.SAPPHIRE_MAX_Y.get()), "sapphire_ore");
        if (Config.SAPPHIRE_ENABLED.get()) ORES.add(SAPPHIRE_ORE_GEN);
		
        GEODITE_DIRT_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_DIRT, ModBlocks.GEODITE_DIRT.get().defaultBlockState(), Config.GEODITE_DIRT_VEIN_COUNT.get()))
		.squared()
        .count(Config.GEODITE_DIRT_VEIN_COUNT.get())
        .range(Config.GEODITE_DIRT_MAX_Y.get()), "geodite_dirt");
        if (Config.GEODITE_DIRT_ENABLED.get()) ORES.add(GEODITE_DIRT_GEN);
 
		GEODITE_STONE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.GEODITE_STONE.get().defaultBlockState(), Config.GEODITE_VEIN_COUNT.get()))
		.squared()
        .count(Config.GEODITE_VEIN_COUNT.get())
        .range(Config.GEODITE_MAX_Y.get()), "geodite_stone");
        if (Config.GEODITE_ENABLED.get()) ORES.add(GEODITE_STONE_GEN);

		LIMESTONE_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_STONE, ModBlocks.LIMESTONE.get().defaultBlockState(), Config.LIMESTONE_VEIN_COUNT.get()))
		.squared()
        .count(Config.LIMESTONE_VEIN_COUNT.get())
        .range(Config.LIMESTONE_MAX_Y.get()), "limestone");
        if (Config.LIMESTONE_ENABLED.get()) ORES.add(LIMESTONE_GEN);

		QUICKSAND_GEN = register(Feature.ORE.configured(new OreFeatureConfig(IN_SAND, ModBlocks.QUICKSAND.get().defaultBlockState(), Config.QUICKSAND_VEIN_COUNT.get()))
		.squared()
        .count(Config.QUICKSAND_VEIN_COUNT.get())
        .range(Config.QUICKSAND_MAX_Y.get()), "quicksand");
        if (Config.QUICKSAND_ENABLED.get()) ORES.add(QUICKSAND_GEN);
	
        SHADEWOOD_TREE = register("shadewood", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.SHADELOG.get().defaultBlockState()),
			new SimpleBlockStateProvider(ModBlocks.SHADEWOOD_LEAVES.get().defaultBlockState()),
			new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4),
			new FancyTrunkPlacer(6, 9, 1),
			new TwoLayerFeature(2, 0, 2))).maxWaterDepth(Integer.MAX_VALUE).heightmap(Heightmap.Type.MOTION_BLOCKING).ignoreVines().build()));;	
    }
	
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        for (ConfiguredFeature<?, ?> feature : ORES) {
            event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
        }
    }*/
}