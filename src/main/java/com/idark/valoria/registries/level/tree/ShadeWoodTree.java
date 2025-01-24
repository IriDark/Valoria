package com.idark.valoria.registries.level.tree;

import com.idark.valoria.registries.level.LevelGen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ShadeWoodTree extends AbstractMegaTreeGrower{

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers){
        return LevelGen.SHADEWOOD_TREE;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom){
        return LevelGen.FANCY_SHADEWOOD_TREE;
    }
}