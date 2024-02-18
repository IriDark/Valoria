package com.idark.valoria.block.types;

import com.idark.valoria.block.blockentity.BlockSimpleInventory;
import com.idark.valoria.block.blockentity.PedestalBlockEntity;
import com.idark.valoria.util.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PedestalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
	
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);

	public PedestalBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
	}

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BlockSimpleInventory) {
                Containers.dropContents(world, pos, ((BlockSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        PedestalBlockEntity tile = (PedestalBlockEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                if (!player.isCreative()) {
                    player.getItemInHand(hand).setCount(stack.getCount() - 1);
                }

                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                PacketUtils.SUpdateTileEntityPacket(tile);
                return InteractionResult.SUCCESS;
            } else {
                tile.getItemHandler().setItem(0, stack);
                if (!player.isCreative()) {
                    player.getInventory().removeItem(player.getItemInHand(hand));
                }

                PacketUtils.SUpdateTileEntityPacket(tile);
                return InteractionResult.SUCCESS;
            }
        }

        if (!tile.getItemHandler().getItem(0).isEmpty()) {
            if (!player.isCreative()) {
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }

            tile.getItemHandler().removeItemNoUpdate(0);
            PacketUtils.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }
}