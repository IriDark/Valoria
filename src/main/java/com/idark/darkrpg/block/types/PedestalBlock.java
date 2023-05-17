package com.idark.darkrpg.block.types;

import com.idark.darkrpg.tileentity.PedestalTileEntity;
import com.idark.darkrpg.tileentity.TileSimpleInventory;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class PedestalBlock extends Block implements ITileEntityProvider, IWaterLoggable {
	
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 15, 16);

	public PedestalBlock(AbstractBlock.Properties properties) {
		super(properties);
		setDefaultState(getDefaultState().with(WATERLOGGED, false));
	}

	@Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new PedestalTileEntity();
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        PedestalTileEntity tile = (PedestalTileEntity) world.getTileEntity(pos);
        ItemStack stack = player.getHeldItem(hand).copy();

        if ((!stack.isEmpty()) && (tile.getItemHandler().getStackInSlot(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getHeldItemMainhand().setCount(stack.getCount() - 1);
                stack.setCount(1);
                tile.getItemHandler().setInventorySlotContents(0, stack);
                return ActionResultType.SUCCESS;
            } else {
                tile.getItemHandler().setInventorySlotContents(0, stack);
                player.inventory.deleteStack(player.getHeldItem(hand));
                return ActionResultType.SUCCESS;
            }
        }

        if (!tile.getItemHandler().getStackInSlot(0).isEmpty()) {
            player.inventory.addItemStackToInventory(tile.getItemHandler().getStackInSlot(0).copy());
            tile.getItemHandler().removeStackFromSlot(0);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
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

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}