package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.render.tile.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
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
import org.jetbrains.annotations.*;

import javax.annotation.*;
import java.util.*;

public class ManipulatorBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity{

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

    public ManipulatorBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState){
        super(pType, pPos, pBlockState);
    }

    public ManipulatorBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.MANIPULATOR_BLOCK_ENTITY.get(), pos, state);
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
    public void tick(){
        if(!level.isClientSide){
            Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
            if(recipe.isPresent()){
                if(getCharge(recipe.get().getCore()) > 0 && itemOutputHandler.getStackInSlot(0).getCount() < itemOutputHandler.getStackInSlot(0).getMaxStackSize()){
                    increaseCraftingProgress();
                    startCraft = true;
                    setMaxProgress();
                    setChanged(level, getBlockPos(), getBlockState());
                    PacketHandler.sendToTracking(this.level, this.getBlockPos(), new ManipulatorCraftParticlePacket(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), -0.2f, 0.2f, -0.2f, 255, 255, 255));
                    if(hasProgressFinished()){
                        craftItem();
                        resetProgress();
                    }

                    ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
                }else if(recipe.get().getCore().equals("empty") && itemOutputHandler.getStackInSlot(0).getCount() < itemOutputHandler.getStackInSlot(0).getMaxStackSize()){
                    increaseCraftingProgress();
                    startCraft = true;
                    setMaxProgress();
                    setChanged(level, getBlockPos(), getBlockState());
                    if(hasProgressFinished()){
                        craftItem();
                        resetProgress();
                    }

                    PacketHandler.sendToTracking(this.level, this.getBlockPos(), new ManipulatorEmptyParticlePacket((float)this.getBlockPos().getX() + 0.5f, (float)this.getBlockPos().getY() + 0.75f, (float)this.getBlockPos().getZ() + 0.5f, (float)this.getBlockPos().getX() + 0.5f, (float)this.getBlockPos().getY() + 0.65f, ((float)this.getBlockPos().getZ() + 0.5f), 255, 255, 255));
                    ValoriaUtils.tileEntity.SUpdateTileEntityPacket(this);
                }
            }
        }
    }

    private Optional<ManipulatorRecipe> getCurrentRecipe(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(ManipulatorRecipe.Type.INSTANCE, inventory, level);
    }

    private void craftItem(){
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(RegistryAccess.EMPTY);
        if(!recipe.get().getCore().equals("empty")){
            decreaseCharge(recipe.get().getCore(), recipe.get().getCoresNeeded());
        }

        itemHandler.extractItem(0, 1, false);
        itemHandler.extractItem(1, 1, false);
        itemOutputHandler.insertItem(0, new ItemStack(result.getItem(), result.getCount()), false);
    }

    private boolean hasProgressFinished(){
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress(){
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        if(progress < recipe.get().getTime()){
            progress++;
        }
    }

    private void setMaxProgress(){
        Optional<ManipulatorRecipe> recipe = getCurrentRecipe();
        if(progressMax <= 0){
            progressMax = recipe.map(ManipulatorRecipe::getTime).orElse(200);
        }
    }

    private void resetProgress(){
        PacketHandler.sendToTracking(this.level, this.getBlockPos(), new CubeShapedParticlePacket((float)this.getBlockPos().getCenter().x, (float)this.getBlockPos().getCenter().y - 0.25f, (float)this.getBlockPos().getCenter().z, 0.62f, 0.15f, 255, 255, 255));
        progress = 0;
        startCraft = false;
    }

    public int getCharge(String name){
        CompoundTag nbt = this.serializeNBT();
        if(nbt == null){
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        return nbt.getInt(name);
    }

    public void setCharge(String name, int charge){
        CompoundTag nbt = this.serializeNBT();
        if(nbt == null){
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        nbt.putInt(name, charge);
        this.deserializeNBT(nbt);
    }

    public void decreaseCharge(String name, int charge){
        CompoundTag nbt = this.serializeNBT();
        if(nbt == null){
            nbt = new CompoundTag();
            this.deserializeNBT(nbt);
        }

        nbt.putInt(name, nbt.getInt(name) - charge);
        this.deserializeNBT(nbt);
    }

    public int getCoreNBT(String name){
        CompoundTag nbt = this.serializeNBT();
        if(nbt != null){
            this.deserializeNBT(nbt);
            return nbt.getInt(name);
        }else{
            throw new IllegalArgumentException("Unknown core");
        }
    }

    @Override
    public void saveAdditional(CompoundTag pTag){
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
    public void load(@NotNull CompoundTag pTag){
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
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
    public @NotNull Component getDisplayName(){
        return Component.translatable("menu.valoria.manipulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer){
        return new ManipulatorMenu(pContainerId, this.level, this.getBlockPos(), pPlayerInventory, pPlayer);
    }
}

