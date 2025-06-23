package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.BlockSimpleInventory;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
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
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.block.entity.*;

import javax.annotation.Nullable;
import javax.annotation.*;

public class KegBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock{
    private static final BooleanProperty BREWING = BooleanProperty.create("brewing");
    private static final VoxelShape shape_west_east = Block.box(0, 0, 2, 16, 14, 14);
    private static final VoxelShape shape_north_south = Block.box(2, 0, 0, 14, 14, 16);

    public KegBlock(Properties properties){
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BREWING, false));
    }

    public static boolean isBrewing(BlockState pState){
        return pState.getValue(BREWING);
    }

    public static void setBrewing(Level level, BlockPos pos, BlockState state, boolean pBrew){
        level.setBlockAndUpdate(pos, state.setValue(BREWING, pBrew));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof BlockSimpleInventory && !((BlockSimpleInventory)tile).getItemHandler().getItem(0).is(TagsRegistry.CUP_DRINKS) && !((BlockSimpleInventory)tile).getItemHandler().getItem(0).is(TagsRegistry.BOTTLE_DRINKS)){
                Containers.dropContents(world, pos, ((BlockSimpleInventory)tile).getItemHandler());
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public MenuProvider getMenuProvider(Level pLevel, BlockPos pPos){
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new KegMenu(p_57074_, pLevel, pPos, p_57076_, p_57075_), Component.translatable("menu.valoria.keg"));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        KegBlockEntity tile = (KegBlockEntity)world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();
        boolean isHoldingCup = stack.getItem() == ItemsRegistry.woodenCup.get();
        boolean isHoldingBottle = stack.getItem() == ItemsRegistry.bottle.get();

        if(isHoldingCup || isHoldingBottle){
            if(!tile.itemOutputHandler.getStackInSlot(0).isEmpty() && isCupOrBottle(tile, player, hand)){
                BlockSimpleInventory.addHandPlayerItem(world, player, hand, stack, tile.itemOutputHandler.getStackInSlot(0).getItem().getDefaultInstance());
                if(!player.isCreative()) player.getItemInHand(hand).shrink(1);

                tile.itemOutputHandler.extractItem(0, 1, false);
                ValoriaUtils.SUpdateTileEntityPacket(tile);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }else{
            if(player instanceof ServerPlayer serv) NetworkHooks.openScreen(serv, getMenuProvider(world, pos), buf -> buf.writeBlockPos(pos));
        }

        return InteractionResult.SUCCESS;
    }

    public boolean isCupOrBottle(KegBlockEntity tile, Player player, InteractionHand hand){
        ItemStack itemStack = tile.itemOutputHandler.getStackInSlot(0);
        ItemStack playerHeldItem = player.getItemInHand(hand).copy();
        boolean isHoldingCup = playerHeldItem.getItem() == ItemsRegistry.woodenCup.get();
        boolean isHoldingBottle = playerHeldItem.getItem() == ItemsRegistry.bottle.get();
        return (isHoldingCup && itemStack.is(TagsRegistry.CUP_DRINKS)) || (isHoldingBottle && itemStack.is(TagsRegistry.BOTTLE_DRINKS));
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx){
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        switch(direction){
            case SOUTH, NORTH -> {
                return shape_north_south;
            }
            case WEST, EAST -> {
                return shape_west_east;
            }
        }

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(BREWING);
        builder.add(FACING);
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new KegBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(BREWING, false);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
}