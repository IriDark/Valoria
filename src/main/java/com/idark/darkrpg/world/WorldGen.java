package com.idark.darkrpg.world;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.config.Config;
import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldGen {

    static List<ConfiguredFeature<?, ?>> ORES = new ArrayList<>();
    static ConfiguredFeature<?, ?> COBALT_ORE_GEN, AMBER_ORE_GEN, AMETHYST_ORE_GEN, RUBY_ORE_GEN, SAPPHIRE_ORE_GEN;
    static RuleTest IN_STONE = new TagMatchRuleTest(Tags.Blocks.STONE);


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

    public static void init() {
        COBALT_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE, ModBlocks.COBALT_ORE.get().getDefaultState(), Config.COBALT_VEIN_SIZE.get()))
		.square()
        .count(Config.COBALT_VEIN_COUNT.get())
        .range(Config.COBALT_MAX_Y.get()), "cobalt_ore");
        if (Config.COBALT_ENABLED.get()) ORES.add(COBALT_ORE_GEN);
	
        AMBER_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE, ModBlocks.AMBER_ORE.get().getDefaultState(), Config.AMBER_VEIN_SIZE.get()))
		.square()
        .count(Config.AMBER_VEIN_COUNT.get())
        .range(Config.AMBER_MAX_Y.get()), "amber_ore");
        if (Config.AMBER_ENABLED.get()) ORES.add(AMBER_ORE_GEN);
	
        AMETHYST_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE, ModBlocks.AMETHYST_ORE.get().getDefaultState(), Config.AMETHYST_VEIN_SIZE.get()))
		.square()
        .count(Config.AMETHYST_VEIN_COUNT.get())
        .range(Config.AMETHYST_MAX_Y.get()), "amethyst_ore");
        if (Config.AMETHYST_ENABLED.get()) ORES.add(AMETHYST_ORE_GEN);
	
        RUBY_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE, ModBlocks.RUBY_ORE.get().getDefaultState(), Config.RUBY_VEIN_SIZE.get()))
		.square()
        .count(Config.RUBY_VEIN_COUNT.get())
        .range(Config.RUBY_MAX_Y.get()), "ruby_ore");
        if (Config.RUBY_ENABLED.get()) ORES.add(COBALT_ORE_GEN);
	
        SAPPHIRE_ORE_GEN = register(Feature.ORE.withConfiguration(new OreFeatureConfig(IN_STONE, ModBlocks.SAPPHIRE_ORE.get().getDefaultState(), Config.SAPPHIRE_VEIN_SIZE.get()))
		.square()
        .count(Config.SAPPHIRE_VEIN_COUNT.get())
        .range(Config.SAPPHIRE_MAX_Y.get()), "sapphire_ore");
        if (Config.SAPPHIRE_ENABLED.get()) ORES.add(SAPPHIRE_ORE_GEN);
    }
	
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        for (ConfiguredFeature<?, ?> feature : ORES) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
        }
    }
}