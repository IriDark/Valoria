package com.idark.valoria.registries.world.block.types;

import com.idark.valoria.client.gui.menu.JewelryMenu;
import com.idark.valoria.client.render.model.blockentity.TickableBlockEntity;
import com.idark.valoria.registries.world.block.entity.types.JewelryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JewelerBlock extends Block implements EntityBlock {

    public JewelerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof JewelryBlockEntity) {
                JewelryBlockEntity table = (JewelryBlockEntity) tile;
                SimpleContainer inv = new SimpleContainer(table.itemHandler.getSlots() + 1);
                for (int i = 0; i < table.itemHandler.getSlots(); i++) {
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Nonnull
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JewelryBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileEntity = world.getBlockEntity(pos);

            MenuProvider containerProvider = createContainerProvider(world, pos);
            NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
            return InteractionResult.CONSUME;
        }
    }

    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos) {
        return new MenuProvider() {

            @Override
            public Component getDisplayName() {
                return Component.translatable("menu.valoria.jewelry");
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new JewelryMenu(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }
}