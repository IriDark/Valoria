package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.item.recipe.*;
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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.*;
import org.jetbrains.annotations.Nullable;
import pro.komaru.tridot.common.registry.block.entity.*;
import pro.komaru.tridot.util.*;

import javax.annotation.*;
import java.util.*;

public class KegBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity{
    public int progress = 0;
    public int progressMax = 0;
    public int ambientSoundTime;
    public boolean startCraft = false;
    public final ItemStackHandler itemHandler = createHandler(2);
    public final ItemStackHandler itemOutputHandler = createHandler(1);

    public KegBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
    }

    public KegBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.KEG_BLOCK_ENTITY.get(), pos, state);
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

    @Override
    public Component getDisplayName(){
        return Component.translatable("menu.valoria.keg");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer){
        return new KegMenu(pContainerId, this.level, this.getBlockPos(), pPlayer, pPlayerInventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries){
        super.saveAdditional(tag, registries);
        tag.put("inv", itemHandler.serializeNBT(registries));
        tag.put("output", itemOutputHandler.serializeNBT(registries));
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("progress", progress);
        tag.putInt("progressMax", progressMax);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries){
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(registries, tag.getCompound("output"));
        startCraft = tag.getBoolean("startCraft");
        progress = tag.getInt("progress");
        progressMax = tag.getInt("progressMax");
    }

    private void resetProgress(){
        progress = 0;
        startCraft = false;
        KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), false);
    }

    public int getAmbientSoundInterval(){
        return 60;
    }

    private void resetAmbientSoundTime(){
        this.ambientSoundTime = -this.getAmbientSoundInterval();
    }

    public void playBrewSound(){
        SoundEvent soundevent = this.getAmbientSound();
        if(soundevent != null){
            this.level.playSound(null, this.getBlockPos(), getBrewSound(), SoundSource.AMBIENT, 1, 1);
        }
    }

    public void playAmbientSound(){
        SoundEvent soundevent = this.getAmbientSound();
        if(soundevent != null){
            this.level.playSound(null, this.getBlockPos(), getAmbientSound(), SoundSource.AMBIENT, 1, Tmp.rnd.nextFloat(1));
        }
    }

    @Nullable
    protected SoundEvent getBrewSound(){
        return SoundsRegistry.KEG_BREW.get();
    }

    @Nullable
    protected SoundEvent getAmbientSound(){
        return SoundsRegistry.KEG_AMBIENT.get();
    }

    @Override
    public void tick(){
        if(!level.isClientSide){
            Optional<KegRecipe> recipe = getCurrentRecipe();
            ItemStack output = this.itemOutputHandler.getStackInSlot(0);
            boolean outputFree = output.isEmpty() || (output.isStackable() && output.getCount() < output.getMaxStackSize() && this.itemOutputHandler.isItemValid(0, output));
            if(recipe.isPresent() && outputFree){
                increaseCraftingProgress();
                startCraft = true;
                setMaxProgress();
                setChanged(level, getBlockPos(), getBlockState());
                KegBlock.setBrewing(this.getLevel(), this.getBlockPos(), this.getBlockState(), true);
                if(level.random.nextInt(1000) < this.ambientSoundTime++){
                    this.resetAmbientSoundTime();
                    this.playAmbientSound();
                }

                if(hasProgressFinished()){
                    craftItem();
                    resetProgress();
                }

                ValoriaUtils.SUpdateTileEntityPacket(this);
            }else{
                resetProgress();
            }
        }
    }

    private void craftItem(){
        Optional<KegRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(level.registryAccess());
        this.itemHandler.extractItem(0, 1, false);
        this.itemHandler.extractItem(1, 1, false);
        this.itemOutputHandler.insertItem(0, result, false);
        this.playBrewSound();
    }

    private Optional<KegRecipe> getCurrentRecipe(){
        return this.level.getRecipeManager().getRecipeFor(KegRecipe.Type.INSTANCE, ContainerRecipeInput.of(itemHandler), this.level).map(RecipeHolder::value);
    }

    private boolean hasProgressFinished(){
        Optional<KegRecipe> recipe = getCurrentRecipe();
        return progress >= recipe.get().getTime();
    }

    private void increaseCraftingProgress(){
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if(progress < recipe.get().getTime()){
            progress++;
        }
    }

    private void setMaxProgress(){
        Optional<KegRecipe> recipe = getCurrentRecipe();
        if(progressMax <= 0){
            progressMax = recipe.map(KegRecipe::getTime).orElse(200);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries){
        super.onDataPacket(net, pkt, registries);
        handleUpdateTag(pkt.getTag(), registries);
    }

    public float getBlockRotate(){
        BlockState state = this.getBlockState();
        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
            Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return switch(direction){
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
    public final CompoundTag getUpdateTag(HolderLookup.Provider registries){
        var tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void setChanged(){
        super.setChanged();
        if(level != null && !level.isClientSide){
            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }
}
