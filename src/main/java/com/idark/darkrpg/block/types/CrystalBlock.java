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
	public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
	private static final VoxelShape upAabb = Block.box((double)3, 0.0D, (double)3, (double)(16 - 3), (double)5, (double)(16 - 3));
	private static final VoxelShape downAabb = Block.box((double)3, (double)(16 - 5), (double)3, (double)(16 - 3), 16.0D, (double)(16 - 3));
    private static final VoxelShape northAabb = Block.box((double)3, (double)3, (double)(16 - 5), (double)(16 - 3), (double)(16 - 3), 16.0D);
    private static final VoxelShape southAabb = Block.box((double)3, (double)3, 0.0D, (double)(16 - 3), (double)(16 - 3), (double)5);
    private static final VoxelShape eastAabb = Block.box(0.0D, (double)3, (double)3, (double)5, (double)(16 - 3), (double)(16 - 3));
    private static final VoxelShape westAabb = Block.box((double)(16 - 5), (double)3, (double)3, 16.0D, (double)(16 - 3), (double)(16 - 3));
	
    public CrystalBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.UP).setValue(FACE, AttachFace.FLOOR));
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

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(FACE);
        builder.add(WATERLOGGED);
    }

	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return isSideSolidForDirection(worldIn, pos, getFacing(state).getOpposite());
	}

	protected static Direction getFacing(BlockState state) {
		Direction direction = (state.getValue(FACING));
		switch((AttachFace)state.getValue(FACE)) {
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
		BlockPos blockpos = pos.relative(direction);
		return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, direction.getOpposite());
	}

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
		for(Direction direction : context.getNearestLookingDirections()) {
			BlockState blockstate;
		    if (direction.getAxis() == Direction.Axis.Y) {
				blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, direction.getOpposite());
			} else {
				blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
			}
	
			if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
				return blockstate;
			}
		}

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
		return getFacing(stateIn).getOpposite() == side && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, side, facingState, worldIn, currentPos, facingPos);
    }
			
    public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }
}