package com.idark.valoria.registries.level.tree;

import com.idark.valoria.registries.level.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.levelgen.feature.*;
import org.jetbrains.annotations.*;

public class DreadwoodTree extends AbstractMegaTreeGrower{

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers){
        return LevelGen.DREADWOOD_TREE;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom){
        return LevelGen.FANCY_DREADWOOD_TREE;
    }
}