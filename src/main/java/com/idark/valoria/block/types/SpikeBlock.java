package com.idark.valoria.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SpikeBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final VoxelShape upAabb = Block.box(0, 0, 0, 16, 3, 16);
    private static final VoxelShape downAabb = Block.box(0, 13, 0, 16, 16, 16);
    private static final VoxelShape northAabb = Block.box(0, 0, 13, 16, 16, 16);
    private static final VoxelShape southAabb = Block.box(0, 0, 0, 16, 16, 3);
    private static final VoxelShape eastAabb = Block.box(0, 0, 0, 3, 16, 16);
    private static final VoxelShape westAabb = Block.box(13, 0, 0, 16, 16, 16);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

	public SpikeBlock(BlockBehaviour.Properties properties) {
		super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP).setValue(AGE, 0));
	}

	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(entityIn.damageSources().generic(), 1.5F);
	}
	
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    Direction direction = (state.getValue(FACING));
        return switch (direction) {
            case NORTH -> northAabb;
            case SOUTH -> southAabb;
            case EAST -> eastAabb;
            case WEST -> westAabb;
            case DOWN -> downAabb;
            default -> upAabb;
        };
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction.getOpposite());
        return pLevel.getBlockState(blockpos).isFaceSturdy(pLevel, blockpos, direction);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pDirection == pState.getValue(FACING).getOpposite() && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(FACING, pContext.getClickedFace()).setValue(AGE, 0);
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED, FACING, AGE);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pState.getValue(AGE);
        if (i < 3) {
            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(i + 1)), 2);
            pLevel.scheduleTick(pPos, this, Mth.nextInt(pRandom, 10, 20));
        } else {
            pLevel.removeBlock(pPos, false);
        }
    }
}