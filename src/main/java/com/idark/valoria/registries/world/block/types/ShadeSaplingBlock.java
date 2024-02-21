package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.registries.world.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ShadeSaplingBlock extends SaplingBlock {
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    public ShadeSaplingBlock(AbstractTreeGrower treeIn, BlockBehaviour.Properties properties) {
		super(treeIn, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
	}
	
	protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == ModBlocks.VOID_TAINT.get() || block == ModBlocks.VOID_GRASS.get();
	}
}	