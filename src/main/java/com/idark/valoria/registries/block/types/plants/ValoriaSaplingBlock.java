package com.idark.valoria.registries.block.types.plants;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class ValoriaSaplingBlock extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    public ValoriaSaplingBlock(AbstractTreeGrower treeIn, BlockBehaviour.Properties properties) {
        super(treeIn, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == BlockRegistry.VOID_TAINT.get() || block == BlockRegistry.VOID_GRASS.get();
    }
}	