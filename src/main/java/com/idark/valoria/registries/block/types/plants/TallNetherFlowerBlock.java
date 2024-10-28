package com.idark.valoria.registries.block.types.plants;

import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class TallNetherFlowerBlock extends DoublePlantBlock {
    public TallNetherFlowerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(BlockTags.NYLIUM) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, worldIn, pos);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }
}