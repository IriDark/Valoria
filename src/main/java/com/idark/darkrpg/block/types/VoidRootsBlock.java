package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class VoidRootsBlock extends BushBlock {
	private static final VoxelShape shape = Block.box(3, 0, 3, 13, 8, 13);

	public VoidRootsBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
        ISelectionContext ctx) {
        return shape;
    }
	
	protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
		Block block = state.getBlock();
		return block == ModBlocks.VOID_STONE.get() || block == ModBlocks.VOID_GRASS.get();
	}
	
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
		return true;
	}
}