package com.idark.valoria.registries.levelgen.feature;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

public class FallenTreeFeature extends Feature<BlockStateConfiguration>{

    public FallenTreeFeature(Codec<BlockStateConfiguration> pCodec){
        super(pCodec);
    }

    public static void placeAt(LevelAccessor pLevel, BlockState pLog, BlockPos pPos, int pFlags){
        BlockPos blockpos = pPos.relative(Direction.EAST, 1);
        if(pLevel.getBlockState(blockpos).canBeReplaced() && !pLevel.getBlockState(blockpos.below()).isAir()){
            pLevel.setBlock(blockpos, pLog.setValue(RotatedPillarBlock.AXIS, Direction.EAST.getAxis()), pFlags);
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> pContext){
        WorldGenLevel level = pContext.level();
        BlockPos origin = pContext.origin();
        BlockPos blockpos = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, origin);
        BlockState block = pContext.config().state;

        int i = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        if(level.getBlockState(mutablePos.set(blockpos).move(0, 0, 1)).canBeReplaced() && !level.getBlockState(mutablePos.set(blockpos).move(0, 0, 1).below()).isAir()){
            level.setBlock(mutablePos.set(blockpos).move(0, 0, 1), block.setValue(RotatedPillarBlock.AXIS, Direction.UP.getAxis()), 2);
            for(int j = 0; j < pContext.random().nextInt(3, 6); ++j){
                mutablePos.set(blockpos).move(1 + i, 0, 1);
                placeAt(level, block, mutablePos, 2);
                ++i;
            }
        }

        return i > 0;
    }
}