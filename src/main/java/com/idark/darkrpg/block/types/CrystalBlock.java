package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.*;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class CrystalBlock extends DirectionalBlock implements IWaterLoggable {
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.FACE;
	private static final VoxelShape upAabb = Block.makeCuboidShape((double)3, 0.0D, (double)3, (double)(16 - 3), (double)5, (double)(16 - 3));
	private static final VoxelShape downAabb = Block.makeCuboidShape((double)3, (double)(16 - 5), (double)3, (double)(16 - 3), 16.0D, (double)(16 - 3));
    private static final VoxelShape northAabb = Block.makeCuboidShape((double)3, (double)3, (double)(16 - 5), (double)(16 - 3), (double)(16 - 3), 16.0D);
    private static final VoxelShape southAabb = Block.makeCuboidShape((double)3, (double)3, 0.0D, (double)(16 - 3), (double)(16 - 3), (double)5);
    private static final VoxelShape eastAabb = Block.makeCuboidShape(0.0D, (double)3, (double)3, (double)5, (double)(16 - 3), (double)(16 - 3));
    private static final VoxelShape westAabb = Block.makeCuboidShape((double)(16 - 5), (double)3, (double)3, 16.0D, (double)(16 - 3), (double)(16 - 3));
	
    public CrystalBlock(AbstractBlock.Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP).with(FACE, AttachFace.FLOOR));
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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(FACE);
        builder.add(WATERLOGGED);
    }

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return isSideSolidForDirection(worldIn, pos, getFacing(state).getOpposite());
	}

	protected static Direction getFacing(BlockState state) {
		Direction direction = (state.get(FACING));
		switch((AttachFace)state.get(FACE)) {
		case CEILING:
			return Direction.DOWN;
		case FLOOR:
		  default:
			return Direction.UP;
		case WALL:
		switch(direction) {
			case NORTH:	
				return Direction.NORTH;
			case SOUTH:
				return Direction.SOUTH;
			case EAST:
				return Direction.EAST;
			case WEST:
				return Direction.WEST;
			}
		}
		return Direction.UP;
	}


	public static boolean isSideSolidForDirection(IWorldReader reader, BlockPos pos, Direction direction) {
		BlockPos blockpos = pos.offset(direction);
		return reader.getBlockState(blockpos).isSolidSide(reader, blockpos, direction.getOpposite());
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
		for(Direction direction : context.getNearestLookingDirections()) {
			BlockState blockstate;
		    if (direction.getAxis() == Direction.Axis.Y) {
				blockstate = this.getDefaultState().with(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).with(FACING, direction.getOpposite());
			} else {
				blockstate = this.getDefaultState().with(FACE, AttachFace.WALL).with(FACING, direction.getOpposite());
			}
	
			if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
				return blockstate;
			}
		}

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
		return getFacing(stateIn).getOpposite() == side && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, side, facingState, worldIn, currentPos, facingPos);
    }
			
    public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
    }
}