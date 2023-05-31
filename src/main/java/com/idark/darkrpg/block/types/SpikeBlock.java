package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.state.properties.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class SpikeBlock extends DirectionalBlock implements IWaterLoggable {
	private static final VoxelShape upAabb = Block.makeCuboidShape((double)3, 0.0D, (double)3, (double)(16 - 3), (double)5, (double)(16 - 3));
	private static final VoxelShape downAabb = Block.makeCuboidShape((double)3, (double)(16 - 1), (double)3, (double)(16 - 3), 16.0D, (double)(16 - 3));
    private static final VoxelShape northAabb = Block.makeCuboidShape((double)3, (double)3, (double)(16 - 1), (double)(16 - 3), (double)(16 - 3), 16.0D);
    private static final VoxelShape southAabb = Block.makeCuboidShape((double)3, (double)3, 0.0D, (double)(16 - 3), (double)(16 - 3), (double)5);
    private static final VoxelShape eastAabb = Block.makeCuboidShape(0.0D, (double)3, (double)3, (double)5, (double)(16 - 3), (double)(16 - 3));
    private static final VoxelShape westAabb = Block.makeCuboidShape((double)(16 - 1), (double)3, (double)3, 16.0D, (double)(16 - 3), (double)(16 - 3));
	public SpikeBlock(AbstractBlock.Properties properties) {
		super(properties);
		setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.GENERIC, 1.5F);
	}
	
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    Direction direction = (state.get(FACING));
	switch(direction) {
        case NORTH:
            return northAabb;
		case SOUTH:
			return southAabb;
		case EAST:
            return eastAabb;
		case WEST:
            return westAabb;
        case DOWN:
            return this.downAabb;
        case UP:
         default:
            return this.upAabb;
        }
    }

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return type == PathType.AIR && !this.canCollide ? true : super.allowsMovement(state, worldIn, pos, type);
    }
		
	@Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
	    builder.add(FACING);
    }
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite()).with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));
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