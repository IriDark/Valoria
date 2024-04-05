package com.idark.valoria.registries.block.types;

import com.idark.valoria.client.gui.menu.ManipulatorMenu;
import com.idark.valoria.client.render.model.blockentity.TickableBlockEntity;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.ManipulatorParticlePacket;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.block.entity.types.ManipulatorBlockEntity;
import com.idark.valoria.util.PacketUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        }

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (!(tileEntity instanceof ManipulatorBlockEntity coreBlock)) {
            return InteractionResult.FAIL;
        }

        ItemStack core = player.getItemInHand(hand);
        Map<Supplier<Item>, Pair<Consumer<ManipulatorBlockEntity>, int[]>> coreActions = new HashMap<>();
        coreActions.put(ItemsRegistry.NATURE_CORE, Pair.of(block -> block.nature_core = 8, new int[]{46, 204, 113}));
        coreActions.put(ItemsRegistry.AQUARIUS_CORE, Pair.of(block -> block.aquarius_core = 8, new int[]{17, 195, 214}));
        coreActions.put(ItemsRegistry.INFERNAL_CORE, Pair.of(block -> block.infernal_core = 8, new int[]{231, 76, 60}));
        coreActions.put(ItemsRegistry.VOID_CORE, Pair.of(block -> block.void_core = 8, new int[]{52, 73, 94}));
        boolean coreUpdated = false;
        for (Map.Entry<Supplier<Item>, Pair<Consumer<ManipulatorBlockEntity>, int[]>> entry : coreActions.entrySet()) {
            if (core.is(entry.getKey().get()) && coreBlock.getCore(entry.getKey().get()) != 8) {
                entry.getValue().getFirst().accept(coreBlock);
                if (!player.getAbilities().instabuild) {
                    core.shrink(1);
                }

                for (int i = 0; i < 360; i += 10) {
                    int[] color = entry.getValue().getSecond();
                    int r = color[0];
                    int g = color[1];
                    int b = color[2];
                    PacketHandler.sendToTracking(world, pos, new ManipulatorParticlePacket(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, player.getRotationVector().y + i, pos.getX() + 0.5f, pos.getY() - 0.25F, pos.getZ() + 0.5f, r, g, b));
                }

                coreUpdated = true;
                break;
            }
        }

        if (coreUpdated) {
            PacketUtils.SUpdateTileEntityPacket(coreBlock);
        } else {
            MenuProvider containerProvider = createContainerProvider(world, pos);
            NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, tileEntity.getBlockPos());
        }

        return InteractionResult.CONSUME;
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }
}