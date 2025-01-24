package com.idark.valoria.registries.block.types;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class PotBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock{
    private final boolean potLong;

    public PotBlock(boolean pLong, BlockBehaviour.Properties properties){
        super(properties);
        potLong = pLong;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public PotBlock(BlockBehaviour.Properties properties){
        super(properties);
        potLong = false;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public VoxelShape getPotLong(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.5, 0.25, 0.75, 0.5, 0.625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.75, 0.375, 0.625, 0.875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.84375, 0.3125, 0.6875, 0.96875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.25, 0.0625, 0.5, 0.625, 0.25), BooleanOp.OR);

        return shape;
    }

    public VoxelShape getPotSmall(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.375, 0.5, 0.375, 0.625, 0.625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.59375, 0.3125, 0.6875, 0.71875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.5, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.0625, 0.5, 0.5, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.125, 0.75, 0.5, 0.5, 0.9375), BooleanOp.OR);

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return potLong ? getPotLong() : getPotSmall();
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
}