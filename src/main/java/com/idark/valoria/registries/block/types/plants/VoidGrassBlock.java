package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class VoidGrassBlock extends Block {
    public VoidGrassBlock(Properties properties) {
        super(properties);
    }

    private static boolean canBeVein(LevelReader pLevelReader, BlockPos pPos) {
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pLevelReader.getBlockState(blockpos);
        return blockstate.isSolid() && !pLevelReader.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (canBeVein(pLevel, pPos)) {
            if (pLevel.isAreaLoaded(pPos, 1))
                pLevel.setBlockAndUpdate(pPos, BlockRegistry.voidTaint.get().defaultBlockState());
        }
    }
}