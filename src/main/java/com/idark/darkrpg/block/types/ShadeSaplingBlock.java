package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ShadeSaplingBlock extends SaplingBlock {
	public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
	private final Tree tree;

	public ShadeSaplingBlock(Tree treeIn, AbstractBlock.Properties properties) {
		super(treeIn, properties);
		this.tree = treeIn;
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
	}
	
	protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == ModBlocks.VOID_STONE.get() || block == ModBlocks.VOID_GRASS.get();
	}
}	