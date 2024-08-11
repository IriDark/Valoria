package com.idark.valoria.registries.levelgen.feature;

import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.registries.levelgen.configurations.*;
import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.Heightmap.*;
import net.minecraft.world.level.levelgen.feature.*;

public class SuspiciousStateFeature extends Feature<SuspiciousStateConfiguration>{
    public SuspiciousStateFeature(Codec<SuspiciousStateConfiguration> pCodec){
        super(pCodec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<SuspiciousStateConfiguration> pContext){
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();
        SuspiciousStateConfiguration config = pContext.config();

        int i = 0;
        BlockPos pos = worldgenlevel.getHeightmapPos(Types.WORLD_SURFACE_WG, blockpos);
        for(int j = 0; j < config.tries; ++j){
            for(SuspiciousStateConfiguration.TargetBlockState target : config.targetStates){
                if(canPlace(target.state, randomsource, target)){
                    worldgenlevel.setBlock(pos.below(), target.state, 2);
                    CrushableBlockEntity.setLootTable(worldgenlevel, randomsource, pos.below(), config.loot);
                    ++i;
                    break;
                }
            }
        }

        return i > 0;
    }

    public static boolean canPlace(BlockState pState,  RandomSource pRandom, SuspiciousStateConfiguration.TargetBlockState pTargetState){
        return !pTargetState.target.test(pState, pRandom);
    }
}
