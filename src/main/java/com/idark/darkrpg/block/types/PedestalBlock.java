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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class PedestalBlock extends Block implements ITileEntityProvider, IWaterLoggable {
	
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);

	public PedestalBlock(AbstractBlock.Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader world) {
        return new PedestalTileEntity();
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                net.minecraft.inventory.InventoryHelper.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        PedestalTileEntity tile = (PedestalTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getMainHandItem().setCount(stack.getCount() - 1);
                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                return ActionResultType.SUCCESS;
            } else {
                tile.getItemHandler().setItem(0, stack);
                player.inventory.removeItem(player.getItemInHand(hand));
                return ActionResultType.SUCCESS;
            }
        }

        if (!tile.getItemHandler().getItem(0).isEmpty()) {
            player.inventory.add(tile.getItemHandler().getItem(0).copy());
            tile.getItemHandler().removeItemNoUpdate(0);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
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

    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}