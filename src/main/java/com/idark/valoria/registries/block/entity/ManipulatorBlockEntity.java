package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.sounds.*;
import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.client.sounds.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.*;
import org.jetbrains.annotations.Nullable;
import pro.komaru.tridot.common.registry.block.entity.*;

import javax.annotation.*;

public class ManipulatorBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity{
    @OnlyIn(Dist.CLIENT) private ElementalManipulatorSoundInstance MANIPULATOR_LOOP;
    private ManipulatorRecipe cachedRecipe = null;

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
                updateRecipeCache();
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
        if(level == null) return;
        if(level.isClientSide){
            playSound();
        }

        if(!level.isClientSide){
            if(this.cachedRecipe != null){
                boolean canOutput = itemOutputHandler.getStackInSlot(0).getCount() < itemOutputHandler.getStackInSlot(0).getMaxStackSize();
                if(getCharge(cachedRecipe.getCore()) >= cachedRecipe.getCoresNeeded() && canOutput){
                    updateCraft(level);
                    PacketHandler.sendToTracking(level, this.getBlockPos(), new ManipulatorCraftParticlePacket(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), -0.2f, 0.2f, -0.2f, 255, 255, 255));
                }else if(cachedRecipe.getCore().equals("empty") && canOutput){
                    updateCraft(level);
                    PacketHandler.sendToTracking(level, this.getBlockPos(), new ManipulatorEmptyParticlePacket((float)this.getBlockPos().getX() + 0.5f, (float)this.getBlockPos().getY() + 0.75f, (float)this.getBlockPos().getZ() + 0.5f, (float)this.getBlockPos().getX() + 0.5f, (float)this.getBlockPos().getY() + 0.65f, ((float)this.getBlockPos().getZ() + 0.5f), 255, 255, 255));
                }
            }else{
                resetProgress();
            }
        }
    }

    private void updateCraft(Level level){
        increaseCraftingProgress();
        setMaxProgress();
        if(hasProgressFinished()){
            craftItem();
            resetProgress();
        }

        setChanged(level, getBlockPos(), getBlockState());
        ValoriaUtils.SUpdateTileEntityPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void playSound(){
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        if(cachedRecipe != null && getCharge(cachedRecipe.getCore()) >= cachedRecipe.getCoresNeeded() && MANIPULATOR_LOOP != null && soundManager.isActive(MANIPULATOR_LOOP)){
            return;
        }

        if(cachedRecipe != null && progress > 0){
            MANIPULATOR_LOOP = ElementalManipulatorSoundInstance.getSound(this);
            soundManager.play(MANIPULATOR_LOOP);
            if(!soundManager.isActive(MANIPULATOR_LOOP)){
                MANIPULATOR_LOOP = null;
            }
        }else{
            if(MANIPULATOR_LOOP != null && soundManager.isActive(MANIPULATOR_LOOP)){
                MANIPULATOR_LOOP.stopSound();
            }

            MANIPULATOR_LOOP = null;
        }
    }

    private void updateRecipeCache() {
        if (this.level == null) return;
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        this.cachedRecipe = this.level.getRecipeManager().getRecipeFor(ManipulatorRecipe.Type.INSTANCE, inventory, this.level).orElse(null);
    }

    private void craftItem(){
        if(this.cachedRecipe != null){
            PacketHandler.sendToTracking(this.level, this.getBlockPos(), new CubeShapedParticlePacket((float)this.getBlockPos().getCenter().x, (float)this.getBlockPos().getCenter().y - 0.25f, (float)this.getBlockPos().getCenter().z, 0.62f, 0.15f, 255, 255, 255));
            ItemStack result = cachedRecipe.assemble(itemHandler);
            if(!cachedRecipe.getCore().equals("empty")){
                decreaseCharge(cachedRecipe.getCore(), cachedRecipe.getCoresNeeded());
            }

            itemHandler.extractItem(0, 1, false);
            itemHandler.extractItem(1, 1, false);
            itemOutputHandler.insertItem(0, result, false);
        }
    }

    private boolean hasProgressFinished(){
        return this.cachedRecipe != null && progress >= cachedRecipe.getTime();
    }

    private void increaseCraftingProgress(){
        startCraft = true;
        if(this.cachedRecipe != null && progress < cachedRecipe.getTime()){
            progress++;
        }
    }

    private void setMaxProgress(){
        if(this.cachedRecipe != null && progressMax <= 0){
            progressMax = cachedRecipe.getTime();
        }
    }

    private void resetProgress(){
        progress = 0;
        progressMax = 0;
        startCraft = false;
    }

    public int getCharge(String name){
        return switch(name){
            case "nature_core" -> nature_core;
            case "infernal_core" -> infernal_core;
            case "aquarius_core" -> aquarius_core;
            case "void_core" -> void_core;
            case "empty" -> 999;
            default -> 0;
        };
    }

    public void addCharge(String name, int charge){
        switch (name) {
            case "nature_core": nature_core += Math.min(charge, ManipulatorBlock.maxCores - nature_core); break;
            case "infernal_core": infernal_core += Math.min(charge, ManipulatorBlock.maxCores - infernal_core); break;
            case "aquarius_core": aquarius_core += Math.min(charge, ManipulatorBlock.maxCores - aquarius_core); break;
            case "void_core": void_core += Math.min(charge, ManipulatorBlock.maxCores - void_core); break;
        }

        this.setChanged();
    }

    public void setCharge(String name, int charge){
        switch (name) {
            case "nature_core": nature_core = charge; break;
            case "infernal_core": infernal_core = charge; break;
            case "aquarius_core": aquarius_core = charge; break;
            case "void_core": void_core = charge; break;
        }

        this.setChanged();
    }

    public void decreaseCharge(String name, int charge){
        switch (name) {
            case "nature_core": nature_core -= charge; break;
            case "infernal_core": infernal_core -= charge; break;
            case "aquarius_core": aquarius_core -= charge; break;
            case "void_core": void_core -= charge; break;
        }

        this.setChanged();
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
    public void onLoad(){
        super.onLoad();
        updateRecipeCache();
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
            ValoriaUtils.SUpdateTileEntityPacket(this);
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

