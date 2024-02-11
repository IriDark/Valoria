package com.idark.valoria.block.types;

import com.idark.valoria.block.blockentity.ManipulatorBlockEntity;
import com.idark.valoria.client.menu.ManipulatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManipulatorBlock extends Block implements EntityBlock {

    public ManipulatorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, Level pLevel, BlockPos pos, RandomSource rand) {
        float chance = 0.35f;
        if (chance < rand.nextFloat()) {
            pLevel.addParticle(ParticleTypes.PORTAL, pos.getX() + rand.nextDouble(),
                pos.getY() + 0.5D, pos.getZ() + rand.nextDouble(),
                0d, 0.05d, 0d);
        }

        super.animateTick(stateIn, pLevel, pos, rand);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.triggerEvent(id, param);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof ManipulatorBlockEntity table) {
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
                return Component.translatable("menu.valoria.manipulator");
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new ManipulatorMenu(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ManipulatorBlockEntity(pPos, pState);
    }
}