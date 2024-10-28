package com.idark.valoria.registries.block.types.plants;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class TallRootsBlock extends DoublePlantBlock {
    public TallRootsBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block == Blocks.DIRT || block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG || block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }
}