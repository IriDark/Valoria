package com.idark.darkrpg.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SpikeBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
	private static final VoxelShape upAabb = Block.box((double)3, 0.0D, (double)3, (double)(16 - 3), (double)5, (double)(16 - 3));
	private static final VoxelShape downAabb = Block.box((double)3, (double)(16 - 1), (double)3, (double)(16 - 3), 16.0D, (double)(16 - 3));
    private static final VoxelShape northAabb = Block.box((double)3, (double)3, (double)(16 - 1), (double)(16 - 3), (double)(16 - 3), 16.0D);
    private static final VoxelShape southAabb = Block.box((double)3, (double)3, 0.0D, (double)(16 - 3), (double)(16 - 3), (double)5);
    private static final VoxelShape eastAabb = Block.box(0.0D, (double)3, (double)3, (double)5, (double)(16 - 3), (double)(16 - 3));
    private static final VoxelShape westAabb = Block.box((double)(16 - 1), (double)3, (double)3, 16.0D, (double)(16 - 3), (double)(16 - 3));
	public SpikeBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(FACING, Direction.UP));
	}

	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(entityIn.damageSources().generic(), 1.5F);
	}
	
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
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

	//public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
    //  return type == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(state, worldIn, pos, type);
    //}
		
	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
	    builder.add(FACING);
    }
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(fluidState.getType() == Fluids.WATER));
    }
		
	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    /*@Override
    public BlockState updateShape(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return stateIn;
    }*/
}