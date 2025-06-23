package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.block.entity.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.block.entity.*;

import javax.annotation.Nullable;
import javax.annotation.*;

public class JewelerBlock extends Block implements EntityBlock{

    public JewelerBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity tile = world.getBlockEntity(pos);
            if(tile instanceof JewelryBlockEntity){
                JewelryBlockEntity table = (JewelryBlockEntity)tile;
                SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
                for(int i = 0; i < table.itemHandler.getSlots(); i++){
                    inv.setItem(i, table.itemHandler.getStackInSlot(i));
                }

                inv.setItem(table.itemHandler.getSlots(), table.itemOutputHandler.getStackInSlot(0));
                Containers.dropContents(world, pos, inv);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type){
        return TickableBlockEntity.getTickerHelper();
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState){
        return new JewelryBlockEntity(pPos, pState);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        if(pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }else{
            if (pPlayer instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, getMenuProvider(pLevel, pPos), buf -> buf.writeBlockPos(pPos));
            }

            return InteractionResult.CONSUME;
        }
    }

    public MenuProvider getMenuProvider(Level pLevel, BlockPos pPos){
        return new SimpleMenuProvider((p_57074_, p_57075_, p_57076_) -> new JewelryMenu(p_57074_, pLevel, pPos, p_57075_, p_57076_), Component.translatable("menu.valoria.jewelry"));
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param){
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }
}