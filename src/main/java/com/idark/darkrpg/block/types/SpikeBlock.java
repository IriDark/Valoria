package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class SpikeBlock extends DirectionalBlock implements IWaterLoggable {
	private static final VoxelShape upAabb = Block.box((double)3, 0.0D, (double)3, (double)(16 - 3), (double)5, (double)(16 - 3));
	private static final VoxelShape downAabb = Block.box((double)3, (double)(16 - 1), (double)3, (double)(16 - 3), 16.0D, (double)(16 - 3));
    private static final VoxelShape northAabb = Block.box((double)3, (double)3, (double)(16 - 1), (double)(16 - 3), (double)(16 - 3), 16.0D);
    private static final VoxelShape southAabb = Block.box((double)3, (double)3, 0.0D, (double)(16 - 3), (double)(16 - 3), (double)5);
    private static final VoxelShape eastAabb = Block.box(0.0D, (double)3, (double)3, (double)5, (double)(16 - 3), (double)(16 - 3));
    private static final VoxelShape westAabb = Block.box((double)(16 - 1), (double)3, (double)3, 16.0D, (double)(16 - 3), (double)(16 - 3));
	public SpikeBlock(AbstractBlock.Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP));
	}

	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(DamageSource.GENERIC, 1.5F);
	}
	
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    Direction direction = (state.getValue(FACING));
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

	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return type == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(state, worldIn, pos, type);
    }
		
	@Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
	    builder.add(FACING);
    }
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
    }
		
	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return stateIn;
    }
}