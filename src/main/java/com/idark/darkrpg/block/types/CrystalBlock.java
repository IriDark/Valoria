package com.idark.darkrpg.block.types;

import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.*;
import net.minecraft.state.properties.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.Mirror;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class CrystalBlock extends DirectionalBlock implements IWaterLoggable {

    private static final VoxelShape shape = Block.makeCuboidShape(0, 0, 0, 15, 16, 15);

    public CrystalBlock(AbstractBlock.Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
        return shape;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        Direction direction = context.getFace();
        BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction.getOpposite()));
        return blockstate.matchesBlock(this) && blockstate.get(FACING) == direction ? this.getDefaultState().with(FACING, direction.getOpposite()) : this.getDefaultState().with(FACING, direction);
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
	
    public BlockState rotate(BlockState state, Rotation rot) {
       return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
       return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
    }
}