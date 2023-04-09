package com.idark.darkrpg.block.types;

import com.idark.darkrpg.block.ModBlocks;
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
	private static final VoxelShape upAabb = Block.makeCuboidShape(16, 3, 3, 5, 13, 13);
	private static final VoxelShape downAabb = Block.makeCuboidShape(3, 16, 3, 13, 13, 5);
    private static final VoxelShape northAabb = Block.makeCuboidShape(3, 3, 16, 13, 13, 5);
    private static final VoxelShape southAabb = Block.makeCuboidShape(0, 3, 3, 13, 13, 5);
    private static final VoxelShape eastAabb = Block.makeCuboidShape(3, 3, 0, 13, 13, 5);
    private static final VoxelShape westAabb = Block.makeCuboidShape(0, 3, 3, 5, 13, 13);

    public CrystalBlock(AbstractBlock.Properties properties) {
        super(properties);
        setDefaultState(getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
    }
	
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.matchesBlock(Blocks.STONE) || state.matchesBlock(Blocks.GRANITE) || state.matchesBlock(Blocks.DIORITE) || state.matchesBlock(Blocks.ANDESITE) || state.matchesBlock(Blocks.INFESTED_STONE) || state.matchesBlock(Blocks.INFESTED_COBBLESTONE) || state.matchesBlock(Blocks.COBBLESTONE) || state.matchesBlock(Blocks.IRON_ORE)  || state.matchesBlock(Blocks.COAL_ORE)  || state.matchesBlock(ModBlocks.AMETHYST_ORE.get())  || state.matchesBlock(ModBlocks.AMBER_ORE.get()) || state.matchesBlock(ModBlocks.RUBY_ORE.get()) || state.matchesBlock(ModBlocks.SAPPHIRE_ORE.get()) || state.matchesBlock(ModBlocks.VOID_STONE.get())|| state.matchesBlock(ModBlocks.WICKED_AMETHYST_ORE.get());
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