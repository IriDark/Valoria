package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.model.blockentity.TickableBlockEntity;
import com.idark.valoria.core.network.PacketHandler;
import com.idark.valoria.core.network.packets.CubeShapedParticlePacket;
import com.idark.valoria.core.network.packets.ManipulatorCraftParticlePacket;
import com.idark.valoria.core.network.packets.ManipulatorEmptyParticlePacket;
import com.idark.valoria.registries.BlockEntitiesRegistry;
import com.idark.valoria.registries.menus.ManipulatorMenu;
import com.idark.valoria.registries.recipe.ManipulatorRecipe;
import com.idark.valoria.util.ValoriaUtils;
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
        this(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), pos, state);
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
                    for (int i = 0; i < 5; i++) {
                        double xOffset, yOffset, zOffset;
                        double motionX, motionY, motionZ;
                        float R, G, B;
                        switch (i) {
                            case 0:
                                xOffset = this.getBlockPos().getX() + 0.85f;
                                yOffset = this.getBlockPos().getY() + 1.10f;
                                zOffset = this.getBlockPos().getZ() + 0.85f;
                                motionX = this.getBlockPos().getCenter().x + 0.5f;
                                motionY = this.getBlockPos().getCenter().y + 0.5f;
                                motionZ = this.getBlockPos().getCenter().z + 0.5f;
                                R = 231;
                                G = 76;
                                B = 60;
                                break;
                            case 1:
                                xOffset = this.getBlockPos().getX() + 0.15f;
                                yOffset = this.getBlockPos().getY() + 1.10f;
                                zOffset = this.getBlockPos().getZ() + 0.15f;
                                motionX = this.getBlockPos().getCenter().x - 0.5f;
                                motionY = this.getBlockPos().getCenter().y + 0.5f;
                                motionZ = this.getBlockPos().getCenter().z - 0.5f;
                                R = 46;
                                G = 204;
                                B = 113;
                                break;
                            case 2:
                                xOffset = this.getBlockPos().getX() + 0.85f;
                                yOffset = this.getBlockPos().getY() + 1.10f;
                                zOffset = this.getBlockPos().getZ() + 0.15f;
                                motionX = this.getBlockPos().getCenter().x + 0.5f;
                                motionY = this.getBlockPos().getCenter().y + 0.5f;
                                motionZ = this.getBlockPos().getCenter().z - 0.5f;
                                R = 17;
                                G = 195;
                                B = 214;
                                break;
                            case 3:
                                xOffset = this.getBlockPos().getX() + 0.15;
                                yOffset = this.getBlockPos().getY() + 1.10f;
                                zOffset = this.getBlockPos().getZ() + 0.85f;
                                motionX = this.getBlockPos().getCenter().x - 0.5f;
                                motionY = this.getBlockPos().getCenter().y + 0.5f;
                                motionZ = this.getBlockPos().getCenter().z + 0.5f;
                                R = 52;
                                G = 73;
                                B = 94;
                                break;
                            case 4:
                                xOffset = this.getBlockPos().getX() + 0.5f;
                                yOffset = this.getBlockPos().getY() + 1.45f;
                                zOffset = this.getBlockPos().getZ() + 0.5f;
                                motionX = this.getBlockPos().getX() + 0.5f;
                                motionY = this.getBlockPos().getY() + 1.75f;
                                motionZ = this.getBlockPos().getZ() + 0.5f;
                                R = 255;
                                G = 255;
                                B = 255;
                                break;
                            default:
                                xOffset = 0;
                                yOffset = 0;
                                zOffset = 0;
                                motionX = this.getBlockPos().getCenter().x + 0.05f;
                                motionY = this.getBlockPos().getCenter().y + 0.5f;
                                motionZ = this.getBlockPos().getCenter().z + 0.05f;
                                R = 0;
                                G = 0;
                                B = 0;
                        }

                        PacketHandler.sendToTracking(this.level, this.getBlockPos(), new ManipulatorCraftParticlePacket((float) xOffset, (float) yOffset, (float) zOffset, (float) motionX, (float) motionY, (float) motionZ, R, G, B));
                    }

                    if (hasProgressFinished()) {
                        craftItem();
                        resetProgress();
                    }

                    ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
                } else if (recipe.get().getCore().equals("empty") && itemOutputHandler.getStackInSlot(0).getCount() < itemOutputHandler.getStackInSlot(0).getMaxStackSize()) {
                    increaseCraftingProgress();
                    startCraft = true;
                    setMaxProgress();
                    setChanged(level, getBlockPos(), getBlockState());
                    if (hasProgressFinished()) {
                        craftItem();
                        resetProgress();
                    }

                    for (int i = 0; i < 360; i += 25) {
                        PacketHandler.sendToTracking(this.level, this.getBlockPos(), new ManipulatorEmptyParticlePacket((float) this.getBlockPos().getX() + 0.5f, (float) this.getBlockPos().getY() + 0.75f, (float) this.getBlockPos().getZ() + 0.5f, i, (float) this.getBlockPos().getX() + 0.5f, (float) this.getBlockPos().getY() + 0.65f, ((float) this.getBlockPos().getZ() + 0.5f), 255, 255, 255));
                    }

                    ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
                }
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

        if (!recipe.get().getCore().equals("empty")) {
            decreaseCharge(recipe.get().getCore(), recipe.get().getCoresNeeded());
        }

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
        PacketHandler.sendToTracking(this.level, this.getBlockPos(), new CubeShapedParticlePacket((float) this.getBlockPos().getCenter().x, (float) this.getBlockPos().getCenter().y - 0.25f, (float) this.getBlockPos().getCenter().z, 6, (float) this.getBlockPos().getCenter().x, (float) this.getBlockPos().getCenter().y - 0.45f, (float) this.getBlockPos().getCenter().z, 255, 255, 255));
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

    public void setCharge(String name, int charge) {
        CompoundTag nbt = this.serializeNBT();
        if (nbt == null) {
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        nbt.putInt(name, charge);
        this.deserializeNBT(nbt);
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

    public int getCoreNBT(String name) {
        CompoundTag nbt = this.serializeNBT();
        if (nbt != null) {
            this.deserializeNBT(nbt);
            return nbt.getInt(name);
        } else {
            throw new IllegalArgumentException("Unknown core");
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
    public void load(@NotNull CompoundTag pTag) {
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
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("menu.valoria.manipulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ManipulatorMenu(pContainerId, this.level, this.getBlockPos(), pPlayerInventory, pPlayer);
    }
}

