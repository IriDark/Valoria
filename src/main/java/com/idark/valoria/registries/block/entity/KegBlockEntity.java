package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.registries.menus.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.*;

import javax.annotation.*;
import java.util.*;

public class KegBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity {
    public int progress = 0;
    public int progressMax = 0;
    public int ambientSoundTime;
    public boolean startCraft = false;
    public final ItemStackHandler itemHandler = createHandler(1);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    public KegBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public KegBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), pos, state);
    }

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

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.valoria.keg");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new KegMenu(pContainerId, this.level, this.getBlockPos(), pPlayer, pPlayerInventory);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("output", itemOutputHandler.serializeNBT());
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("progress", progress);
        tag.putInt("progressMax", progressMax);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(tag.getCompound("output"));
        startCraft = tag.getBoolean("startCraft");
        progress = tag.getInt("progress");
        progressMax = tag.getInt("progressMax");
    }

    private void resetProgress() {
        progress = 0;
        startCraft = false;
        KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
    }

    public int getAmbientSoundInterval() {
        return 60;
    }

    private void resetAmbientSoundTime() {
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    public void playBrewSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.level.playSound(null, this.getBlockPos(), getBrewSound(), SoundSource.AMBIENT, 1, 1);
        }
    }

    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.level.playSound(null, this.getBlockPos(), getAmbientSound(), SoundSource.AMBIENT, 1, new ArcRandom().nextFloat(1));
        }
    }

    @Nullable
    protected SoundEvent getBrewSound() {
        return SoundsRegistry.KEG_BREW.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundsRegistry.KEG_AMBIENT.get();
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
    public void tick() {
        if (!level.isClientSide) {
            Optional<KegRecipe> recipe = getCurrentRecipe();
            if (recipe.isPresent() && this.itemOutputHandler.getStackInSlot(0).isEmpty()) {
                increaseCraftingProgress();
                startCraft = true;
                setMaxProgress();
                setChanged(level, getBlockPos(), getBlockState());
                KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), true);
                if (level.random.nextInt(1000) < this.ambientSoundTime++) {
                    this.resetAmbientSoundTime();
                    this.playAmbientSound();
                }

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }

                ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
            } else {
                resetProgress();
            }
        }
    }

    private void craftItem() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY);
        if (this.itemOutputHandler.getStackInSlot(0).isEmpty()) {
            this.itemHandler.extractItem(0, 1, false);
            this.itemOutputHandler.setStackInSlot(0, result);
        }

        this.playBrewSound();
    }

    private Optional<KegRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(3);
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        return this.level.getRecipeManager().getRecipeFor(KegRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasProgressFinished() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if (progress < recipe.get().getTime()) {
            progress++;
        }
    }

    private void setMaxProgress() {
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if (progressMax <= 0) {
            progressMax = recipe.map(KegRecipe::getTime).orElse(200);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    public float getBlockRotate() {
        BlockState state = this.getBlockState();
        if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return switch (direction) {
                case EAST -> 90F;
                case SOUTH -> 360F;
                case WEST -> -90F;
                default -> 180F;
            };
        }

        return 0F;
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
}