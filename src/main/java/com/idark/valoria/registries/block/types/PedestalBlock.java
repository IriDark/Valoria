package com.idark.valoria.registries.block.types;

import com.idark.valoria.registries.block.entity.BlockSimpleInventory;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.networking.packets.*;
import pro.komaru.tridot.common.registry.block.entity.*;
import pro.komaru.tridot.common.registry.book.*;

import javax.annotation.Nullable;
import javax.annotation.*;

public class PedestalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock{
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);
    public PedestalBlock(BlockBehaviour.Properties properties){
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx){
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof BlockSimpleInventory){
                Containers.dropContents(world, pos, ((BlockSimpleInventory)tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
        PedestalBlockEntity tile = (PedestalBlockEntity)world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(handIn).copy();
        ItemStack tileStack = tile.getItemHandler().getItem(0);
        Book book = tile.getBook();
        if (book != null) {
            if (player.isShiftKeyDown() && !tile.getItemHandler().getItem(0).isEmpty()) {
                BlockSimpleInventory.addHandPlayerItem(world, player, handIn, stack, tile.getItemHandler().getItem(0));
                tile.getItemHandler().removeItem(0, 1);
                world.updateNeighbourForOutputSignal(pos, this);
                BlockEntityUpdate.packet(tile);
                return InteractionResult.SUCCESS;
            }

            if (world.isClientSide()) {
                book.openGui(world, pos.getCenter(), tile.getItemHandler().getItem(0));
            }

            return InteractionResult.SUCCESS;
        }

        if(tileStack.isEmpty()){
            tile.getItemHandler().setItem(0, stack);
            if(!player.isCreative()){
                player.getItemInHand(handIn).shrink(1);
            }

            ValoriaUtils.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        if(!tileStack.isEmpty()){
            BlockSimpleInventory.addHandPlayerItem(world, player, handIn, stack, tile.getItemHandler().getItem(0));
            tile.getItemHandler().removeItem(0, 1);
            ValoriaUtils.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos){
        if(pState.getValue(BlockStateProperties.WATERLOGGED)){
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new PedestalBlockEntity(pPos, pState);
    }
}