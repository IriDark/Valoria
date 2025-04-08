package com.idark.valoria.registries.block.types.plants;

import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class TallRootsBlock extends DoublePlantBlock implements BonemealableBlock{
    public TallRootsBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos){
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block == Blocks.DIRT || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG;
    }

    public boolean isValidBonemealTarget(LevelReader p_256234_, BlockPos p_57304_, BlockState p_57305_, boolean p_57306_) {
        return true;
    }

    public boolean isBonemealSuccess(Level p_222573_, RandomSource p_222574_, BlockPos p_222575_, BlockState p_222576_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_222568_, RandomSource p_222569_, BlockPos p_222570_, BlockState p_222571_) {
        popResource(p_222568_, p_222570_, new ItemStack(this));
    }
}