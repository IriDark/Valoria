package com.idark.valoria.registries.world.block.entity;

import com.idark.valoria.client.gui.menu.ManipulatorMenu;
import com.idark.valoria.client.render.model.blockentity.TickableBlockEntity;
import com.idark.valoria.registries.recipe.ManipulatorRecipe;
import com.idark.valoria.registries.world.item.ModItems;
import com.idark.valoria.util.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ManipulatorBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity {

    public final ItemStackHandler itemHandler = createHandler(2);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);
    public int progress = 0;
    public int progressMax = 0;
    public boolean startCraft = false;

    public int nature_core = 0;
    public int infernal_core = 0;
    public int aquarius_core = 0;
    public int void_core = 0;

    private ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    public ManipulatorBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ManipulatorBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.MANIPULATOR_BLOCK_ENTITY.get(), pos, state);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                CombinedInvWrapper item = new CombinedInvWrapper(itemHandler, itemOutputHandler);
                return LazyOptional.of(() -> item).cast();
            }

            if (side == Direction.DOWN) {
                return outputHandler.cast();
            } else {
                return handler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
            if (recipe.isPresent()) {
                if (getCharge(recipe.get().getCore()) > 0 && itemOutputHandler.getStackInSlot(0).getCount() < itemOutputHandler.getStackInSlot(0).getMaxStackSize()) {
                    increaseCraftingProgress();
                    startCraft = true;
                    setMaxProgress();
                    setChanged(level, getBlockPos(), getBlockState());
                    if (hasProgressFinished()) {
                        craftItem();
                        resetProgress();
                    }

                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            } else {
                resetProgress();
            }
        }
    }

    private Optional<ManipulatorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(ManipulatorRecipe.Type.INSTANCE, inventory, level);
    }

    private void craftItem() {
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY);

        decreaseCharge(recipe.get().getCore(), 1);
        itemHandler.extractItem(0, 1, false);
        itemHandler.extractItem(1, 1, false);
        itemOutputHandler.insertItem(0, new ItemStack(result.getItem(), result.getCount()), false);
    }

    private boolean hasProgressFinished() {
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress() {
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        if (progress < recipe.get().getTime()) {
            progress++;
        }
    }

    private void setMaxProgress() {
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        if (progressMax <= 0) {
            progressMax = recipe.map(ManipulatorRecipe::getTime).orElse(200);
        }
    }

    private void resetProgress() {
        progress = 0;
        startCraft = false;
    }

    public int getCharge(String name) {
        CompoundTag nbt = this.serializeNBT();
        if (nbt == null) {
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        return nbt.getInt(name);
    }

    public void decreaseCharge(String name, int charge) {
        CompoundTag nbt = this.serializeNBT();
        if (nbt == null) {
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        nbt.putInt(name, nbt.getInt(name) - charge);
        this.deserializeNBT(nbt);
    }

    public int getCore(Item coreItem) {
        if (coreItem.equals(ModItems.NATURE_CORE.get())) {
            return nature_core;
        } else if (coreItem.equals(ModItems.AQUARIUS_CORE.get())) {
            return aquarius_core;
        } else if (coreItem.equals(ModItems.INFERNAL_CORE.get())) {
            return infernal_core;
        } else if (coreItem.equals(ModItems.VOID_CORE.get())) {
            return void_core;
        } else {
            throw new IllegalArgumentException("Unknown core item: " + coreItem);
        }
    }

    @Override
    public void saveAdditional(CompoundTag pTag) {
        pTag.put("inv", itemHandler.serializeNBT());
        pTag.put("output", itemOutputHandler.serializeNBT());
        pTag.putInt("progress", progress);
        pTag.putInt("progressMax", progressMax);

        pTag.putInt("nature_core", nature_core);
        pTag.putInt("infernal_core", infernal_core);
        pTag.putInt("aquarius_core", aquarius_core);
        pTag.putInt("void_core", void_core);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(pTag.getCompound("output"));
        progress = pTag.getInt("progress");
        progressMax = pTag.getInt("progressMax");

        nature_core = pTag.getInt("nature_core");
        infernal_core = pTag.getInt("infernal_core");
        aquarius_core = pTag.getInt("aquarius_core");
        void_core = pTag.getInt("void_core");
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.valoria.manipulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ManipulatorMenu(pContainerId, this.level, this.getBlockPos(), pPlayerInventory, pPlayer);
    }
}

