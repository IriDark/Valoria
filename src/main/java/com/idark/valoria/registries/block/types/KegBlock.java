package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.entity.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
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

import javax.annotation.Nullable;
import javax.annotation.*;

public class KegBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock{

    private static final BooleanProperty BREWING = BooleanProperty.create("brewing");
    private static final VoxelShape shape_west_east = Block.box(0, 0, 2, 16, 14, 14);
    private static final VoxelShape shape_north_south = Block.box(2, 0, 0, 14, 14, 16);

    public KegBlock(BlockBehaviour.Properties properties){
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

    public boolean isCupOrBottle(KegBlockEntity tile, Player player, InteractionHand hand){
        ItemStack itemStack = tile.getItemHandler().getItem(0);
        ItemStack playerHeldItem = player.getItemInHand(hand).copy();
        boolean isHoldingCup = playerHeldItem.getItem() == ItemsRegistry.WOODEN_CUP.get();
        boolean isHoldingBottle = playerHeldItem.getItem() == ItemsRegistry.BOTTLE.get();
        if(!player.isCreative()){
            if(isHoldingCup && itemStack.is(TagsRegistry.CUP_DRINKS) || isHoldingBottle && itemStack.is(TagsRegistry.BOTTLE_DRINKS)){
                player.getItemInHand(hand).shrink(1);
            }
        }

        return (isHoldingCup && itemStack.is(TagsRegistry.CUP_DRINKS)) || (isHoldingBottle && itemStack.is(TagsRegistry.BOTTLE_DRINKS));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        KegBlockEntity tile = (KegBlockEntity)world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();
        if(stack.is(Items.HONEY_BOTTLE)){
            if(!player.isCreative()){
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, new ItemStack(Items.GLASS_BOTTLE)));
            }
        }

        // Checking if not alcohol to prevent miss clicks and collecting with isCupOrBottle
        if((!stack.isEmpty()) && !stack.is(TagsRegistry.ALCOHOL) && (tile.getItemHandler().getItem(0).isEmpty())){
            if(stack.getCount() > 1){
                if(!player.isCreative()){
                    player.getItemInHand(hand).shrink(1);
                }

                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                world.setBlockAndUpdate(pos, state.setValue(BREWING, true));
            }else{
                tile.getItemHandler().setItem(0, stack);
                world.setBlockAndUpdate(pos, state.setValue(BREWING, true));
                if(!player.isCreative()){
                    player.getInventory().removeItem(player.getItemInHand(hand));
                }
            }

            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        if(!tile.getItemHandler().getItem(0).isEmpty() && isCupOrBottle(tile, player, hand)){
            if(!player.isCreative()){
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }

            tile.getItemHandler().removeItemNoUpdate(0);
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }else if(!tile.getItemHandler().getItem(0).isEmpty() && !tile.getItemHandler().getItem(0).is(TagsRegistry.CUP_DRINKS) && !tile.getItemHandler().getItem(0).is(TagsRegistry.BOTTLE_DRINKS)){
            if(!player.isCreative() && !tile.getItemHandler().getItem(0).is(Items.HONEY_BOTTLE)){
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }else if(!player.isCreative() && tile.getItemHandler().getItem(0).is(Items.HONEY_BOTTLE) && stack.is(Items.GLASS_BOTTLE)){
                player.getItemInHand(hand).setCount(stack.getCount() - 1);
                world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5F, player.getY() + 0.5F, player.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }

            tile.getItemHandler().removeItemNoUpdate(0);
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(tile);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
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