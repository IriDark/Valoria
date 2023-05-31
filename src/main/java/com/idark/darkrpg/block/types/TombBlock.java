package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class TombBlock extends HorizontalBlock implements IWaterLoggable {
	
	private static final VoxelShape shape = Block.makeCuboidShape(3, 0, 3, 13, 16, 13);
	public TombBlock(AbstractBlock.Properties properties) {
		super(properties);
		setDefaultState(getDefaultState().with(WATERLOGGED, false));
	}

	@Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
        ISelectionContext ctx) {
        return shape;
    }
	
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return type == PathType.AIR && !this.canCollide ? true : super.allowsMovement(state, worldIn, pos, type);
    }
	
	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
	    builder.add(HORIZONTAL_FACING);
    }
		
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
	
	@Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return stateIn;
    }
}