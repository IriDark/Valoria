package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class VoidGrassBlock extends Block{
    public VoidGrassBlock(Properties properties){
        super(properties);
    }

    private static boolean canBeVein(LevelReader pLevelReader, BlockPos pPos){
        BlockPos blockpos = pPos.above();
        BlockState blockstate = pLevelReader.getBlockState(blockpos);
        return blockstate.isSolid() && !pLevelReader.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public boolean isRandomlyTicking(BlockState pState){
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom){
        if(canBeVein(pLevel, pPos)){
            if(pLevel.isAreaLoaded(pPos, 1))
                pLevel.setBlockAndUpdate(pPos, BlockRegistry.VOID_TAINT.get().defaultBlockState());
        }
    }
}