package com.idark.valoria.core.datagen.worldgen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.util.random.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.*;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> amberOre = registerKey("amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> cobaltOre = registerKey("cobalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> sapphireOre = registerKey("sapphire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> rubyOre = registerKey("ruby_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> jadeOre = registerKey("jade_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> pyratiteOre = registerKey("pyratite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> wickedAmethystOre = registerKey("wicked_amethyst_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> pearliumOre = registerKey("pearlium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LONG_POT = registerKey("long_pot_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_POT = registerKey("small_pot_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_POT = registerKey("desert_pot_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_POT = registerKey("swamp_pot_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE = registerKey("limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_POT_SMALL = registerKey("cave_pot_small_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_POT_LONG = registerKey("cave_pot_long_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POT_SMALL = registerKey("deepslate_pot_small_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_POT_LONG = registerKey("deepslate_pot_long_feature");
    public static final ResourceKey<ConfiguredFeature<?, ?>> dormantCrystals = registerKey("dormant_crystals");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest voidStoneReplaceables = new BlockMatchTest(BlockRegistry.voidStone.get());
        RuleTest picriteReplaceables = new BlockMatchTest(BlockRegistry.picrite.get());
        RuleTest crystalStoneReplaceables = new BlockMatchTest(BlockRegistry.crystalStone.get());

        registerOre(context, amberOre, List.of(OreConfiguration.target(stoneReplaceables(), BlockRegistry.amberOre.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables(), BlockRegistry.deepslateAmberOre.get().defaultBlockState())), 4);
        registerOre(context, cobaltOre, List.of(OreConfiguration.target(stoneReplaceables(), BlockRegistry.cobaltOre.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables(), BlockRegistry.deepslateCobaltOre.get().defaultBlockState())), 4);
        registerOre(context, sapphireOre, List.of(OreConfiguration.target(stoneReplaceables(), BlockRegistry.sapphireOre.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables(), BlockRegistry.deepslateSapphireOre.get().defaultBlockState())), 4);
        registerOre(context, rubyOre, List.of(OreConfiguration.target(stoneReplaceables(), BlockRegistry.rubyOre.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables(), BlockRegistry.deepslateRubyOre.get().defaultBlockState())), 4);
        registerOre(context, jadeOre, List.of(OreConfiguration.target(voidStoneReplaceables, BlockRegistry.jadeOre.get().defaultBlockState()), OreConfiguration.target(picriteReplaceables, BlockRegistry.picriteJadeOre.get().defaultBlockState())), 3);
        registerOre(context, pyratiteOre, List.of(OreConfiguration.target(picriteReplaceables, BlockRegistry.pyratiteOre.get().defaultBlockState())), 5);
        registerOre(context, wickedAmethystOre, List.of(OreConfiguration.target(voidStoneReplaceables, BlockRegistry.wickedAmethystOre.get().defaultBlockState())), 14);
        registerOre(context, pearliumOre, List.of(OreConfiguration.target(crystalStoneReplaceables, BlockRegistry.pearliumOre.get().defaultBlockState())), 14);

        RuleTest limestoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        context.register(LIMESTONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(limestoneReplaceables, BlockRegistry.limestone.get().defaultBlockState())),
                64, 0.0f
        )));

        context.register(LONG_POT, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potLong.get().defaultBlockState(), 1)
                .add(BlockRegistry.potLongHandles.get().defaultBlockState(), 1)
                .add(BlockRegistry.potLongMossy.get().defaultBlockState(), 2)
                .add(BlockRegistry.potLongMossyHandles.get().defaultBlockState(), 2)
                .build())
        )));

        context.register(DESERT_POT, new ConfiguredFeature<>(LevelGen.POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potDesert.get().defaultBlockState(), 1)
                .add(BlockRegistry.potDesertHandles.get().defaultBlockState(), 1)
                .build())
        )));

        context.register(SWAMP_POT, new ConfiguredFeature<>(LevelGen.POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potLongMossy.get().defaultBlockState(), 4)
                .add(BlockRegistry.potLongMossyHandles.get().defaultBlockState(), 4)
                .add(BlockRegistry.potLong.get().defaultBlockState(), 1)
                .add(BlockRegistry.potLongHandles.get().defaultBlockState(), 1)
                .build())
        )));

        context.register(SMALL_POT, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potSmall.get().defaultBlockState(), 1)
                .add(BlockRegistry.potSmallHandles.get().defaultBlockState(), 1)
                .build())
        )));

        context.register(CAVE_POT_SMALL, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potCaveSmall.get().defaultBlockState(), 1)
                .add(BlockRegistry.potCaveSmallHandles.get().defaultBlockState(), 1)
                .build())
        )));
        
        context.register(CAVE_POT_LONG, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potCaveLong.get().defaultBlockState(), 1)
                .add(BlockRegistry.potCaveLongHandles.get().defaultBlockState(), 1)
                .build())
        )));
        
        context.register(DEEPSLATE_POT_SMALL, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potDeepslateSmall.get().defaultBlockState(), 1)
                .add(BlockRegistry.potDeepslateSmallHandles.get().defaultBlockState(), 1)
                .build())
        )));
        
        context.register(DEEPSLATE_POT_LONG, new ConfiguredFeature<>(LevelGen.CAVE_POT.get(), new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(BlockRegistry.potDeepslateLong.get().defaultBlockState(), 1)
                .add(BlockRegistry.potDeepslateLongHandles.get().defaultBlockState(), 1)
                .build())
        )));

        registerOre(context, dormantCrystals, List.of(OreConfiguration.target(voidStoneReplaceables, BlockRegistry.dormantCrystals.get().defaultBlockState())), 8);
    }

    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, List<OreConfiguration.TargetBlockState> targetStates, int size) {
        register(context, key, Feature.ORE, new OreConfiguration(targetStates, size));
    }

    private static RuleTest stoneReplaceables() {
        return new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    }

    private static RuleTest deepslateReplaceables() {
        return new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Valoria.loc(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
