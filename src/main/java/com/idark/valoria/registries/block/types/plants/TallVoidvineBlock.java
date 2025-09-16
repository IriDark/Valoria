package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class TallVoidvineBlock extends DoublePlantBlock{
    public TallVoidvineBlock(Properties properties){
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos){
        Block block = state.getBlock();
        return block == BlockRegistry.voidGrass.get() || block == BlockRegistry.voidTaint.get() || block == BlockRegistry.voidStone.get() || block == BlockRegistry.voidSand.get() || block == BlockRegistry.smoothVoidSandstone.get() || super.mayPlaceOn(state, worldIn, pos);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext){
        return false;
    }
}