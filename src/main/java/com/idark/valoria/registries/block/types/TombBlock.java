package com.idark.valoria.registries.block.types;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.*;

import javax.annotation.*;

public class TombBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    private final boolean isGrave;
    public TombBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.isGrave = false;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public TombBlock(boolean isGrave, BlockBehaviour.Properties properties){
        super(properties);
        this.isGrave = isGrave;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public VoxelShape makeGraveShape(BlockState state){
        VoxelShape shape = Shapes.empty();
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        switch(direction){
            case SOUTH, NORTH -> {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.375, 0.8125, 0.875, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.875, 0.375, 0.75, 0.9375, 0.625), BooleanOp.OR);
            }

            case WEST, EAST -> {
                shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.1875, 0.625, 0.875, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.875, 0.25, 0.625, 0.9375, 0.75), BooleanOp.OR);
            }
        }

        return shape;
    }

    public VoxelShape makeTombShape(BlockState state){
        VoxelShape shape = Shapes.empty();
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.1875, 0.375, 0.625, 0.5, 0.625), BooleanOp.OR);
        switch(direction) {
            case SOUTH, NORTH -> {
                shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 1, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.25, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.125, 0.5, 0.375, 0.875, 0.75, 0.625), BooleanOp.OR);
            }

            case WEST, EAST -> {
                shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 1, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.25, 0.8125), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.5, 0.125, 0.625, 0.75, 0.875), BooleanOp.OR);
            }
        }

        return shape;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx){
        return this.isGrave ? makeGraveShape(state): makeTombShape(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
        if(pState.getValue(BlockStateProperties.WATERLOGGED)){
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pDirection == pState.getValue(FACING).getOpposite() && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }
}