package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.model.blockentity.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.menus.*;
import com.idark.valoria.registries.recipe.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.Nullable;

import javax.annotation.*;
import java.util.*;

public class JewelryBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity{
    public final ItemStackHandler itemHandler = createHandler(2);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);
    public int progress = 0;
    public int progressMax = 0;

    public JewelryBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(BlockEntitiesRegistry.JEWELRY_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    private ItemStackHandler createHandler(int size){
        return new ItemStackHandler(size){
            @Override
            protected void onContentsChanged(int slot){
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack){
                return true;
            }

            @Override
            public int getSlotLimit(int slot){
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
                if(!isItemValid(slot, stack)){
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            if(side == null){
                CombinedInvWrapper item = new CombinedInvWrapper(itemHandler, itemOutputHandler);
                return LazyOptional.of(() -> item).cast();
            }

            if(side == Direction.DOWN){
                return outputHandler.cast();
            }else{
                return handler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public final CompoundTag getUpdateTag(){
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged(){
        super.setChanged();
        if(level != null && !level.isClientSide){
            ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    public Component getDisplayName(){
        return Component.translatable("menu.valoria.jewelry");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer){
        return new JewelryMenu(pContainerId, this.level, this.getBlockPos(), pPlayerInventory, pPlayer);
    }

    @Override
    public void saveAdditional(CompoundTag pTag){
        pTag.put("inv", itemHandler.serializeNBT());
        pTag.put("output", itemOutputHandler.serializeNBT());
        pTag.putInt("progress", progress);
        pTag.putInt("progressMax", progressMax);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag){
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(pTag.getCompound("output"));
        progress = pTag.getInt("progress");
        progressMax = pTag.getInt("progressMax");
    }

    @Override
    public void tick(){
        if(!level.isClientSide){
            Optional<JewelryRecipe> recipe = getCurrentRecipe();
            if(recipe.isPresent()){
                increaseCraftingProgress();
                setMaxProgress();
                setChanged(level, getBlockPos(), getBlockState());
                if(hasProgressFinished()){
                    craftItem();
                    resetProgress();
                }

                ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
            }else{
                resetProgress();
            }
        }
    }

    private void resetProgress(){
        progress = 0;
    }

    private boolean hasProgressFinished(){
        Optional<JewelryRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress(){
        Optional<JewelryRecipe> recipe = getCurrentRecipe();
        if(this.itemOutputHandler.getStackInSlot(0).isEmpty()){
            if(progress < recipe.get().getTime()){
                progress++;
            }
        }
    }

    private void setMaxProgress(){
        Optional<JewelryRecipe> recipe = getCurrentRecipe();
        if(progressMax <= 0){
            progressMax = recipe.map(JewelryRecipe::getTime).orElse(200);
        }
    }

    private void craftItem(){
        Optional<JewelryRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY);
        if(this.itemOutputHandler.getStackInSlot(0).isEmpty()){
            this.itemHandler.extractItem(0, 1, false);
            this.itemHandler.extractItem(1, 1, false);
            this.itemOutputHandler.setStackInSlot(0, result);
        }
    }

    private Optional<JewelryRecipe> getCurrentRecipe(){
        SimpleContainer inv = new SimpleContainer(3);
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(JewelryRecipe.Type.INSTANCE, inv, level);
    }
}