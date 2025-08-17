package com.idark.valoria.registries.block.entity;

import com.idark.valoria.client.ui.menus.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.*;
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
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.block.entity.*;

import javax.annotation.*;
import java.util.*;

public class SoulInfuserBlockEntity extends BlockEntity implements MenuProvider, TickableBlockEntity{
    public final ItemStackHandler itemHandler = createHandler(2);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);
    public int progress = 0;
    public int progressMax = 0;
    public boolean startCraft = false;

    public SoulInfuserBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState){
        super(pType, pPos, pBlockState);
    }

    public SoulInfuserBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntitiesRegistry.SOUL_INFUSER_BLOCK_ENTITY.get(), pos, state);
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

            if (side == Direction.DOWN) return outputHandler.cast();
            else return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide) return;

        Optional<SoulInfuserRecipe> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) {
            this.resetProgress();
            return;
        }

        SoulInfuserRecipe recipe = recipeOpt.get();
        if (this.canCraft(recipe)) {
            ItemStack input = this.itemHandler.getStackInSlot(0);
            ItemStack soulCollector = this.itemHandler.getStackInSlot(1);

            if (this.getSouls(soulCollector) >= recipe.getSouls(input)) {
                this.increaseCraftingProgress();
                this.setMaxProgress();
                this.setChanged();

                if (this.hasProgressFinished()) {
                    this.craftItem(recipe);
                    this.resetProgress();
                }
            }

        } else resetProgress();
    }

    private boolean canCraft(SoulInfuserRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem(this.level.registryAccess());
        ItemStack outputSlot = this.itemOutputHandler.getStackInSlot(0);

        if (outputSlot.isEmpty()) return true;
        if (!ItemStack.isSameItem(outputSlot, recipeOutput)) return false;
        if (!ItemStack.isSameItemSameTags(outputSlot, recipeOutput)) return false;

        return outputSlot.getCount() + recipeOutput.getCount() <= outputSlot.getMaxStackSize();
    }

    //todo
    @OnlyIn(Dist.CLIENT)
    public void playSound(){
//        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
//        if(getCurrentRecipe().isPresent() && ValoriaClient.MANIPULATOR_LOOP != null && soundManager.isActive(ValoriaClient.MANIPULATOR_LOOP)){
//            return;
//        }
//
//        if(getCurrentRecipe().isPresent() && progress > 0){
//            ValoriaClient.MANIPULATOR_LOOP = ElementalManipulatorSoundInstance.getSound(this);
//            soundManager.play(ValoriaClient.MANIPULATOR_LOOP);
//            if(!soundManager.isActive(ValoriaClient.MANIPULATOR_LOOP)){
//                ValoriaClient.MANIPULATOR_LOOP = null;
//            }
//        }else{
//            if(soundManager.isActive(ValoriaClient.MANIPULATOR_LOOP) && ValoriaClient.MANIPULATOR_LOOP != null){
//                ValoriaClient.MANIPULATOR_LOOP.stopSound();
//            }
//
//            ValoriaClient.MANIPULATOR_LOOP = null;
//        }
    }

    public Optional<SoulInfuserRecipe> getCurrentRecipe(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(SoulInfuserRecipe.Type.INSTANCE, inventory, level);
    }

    private void craftItem(SoulInfuserRecipe recipe) {
        PacketHandler.sendToTracking(this.level, this.getBlockPos(), new CubeShapedParticlePacket((float)this.getBlockPos().getCenter().x, (float)this.getBlockPos().getCenter().y - 0.25f, (float)this.getBlockPos().getCenter().z, 0.62f, 0.15f, 255, 255, 255));

        ItemStack infusableItem = this.itemHandler.getStackInSlot(0);
        ItemStack soulCollector = this.itemHandler.getStackInSlot(1);

        int soulsToConsume = recipe.getSouls(infusableItem);

        this.consumeSouls(soulCollector, soulsToConsume);
        soulCollector.hurt(1, this.level.random, null);

        if (soulCollector.getDamageValue() >= soulCollector.getMaxDamage()) {
            this.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
            this.level.playSound(null, this.getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1, 1);
        } else this.itemHandler.setStackInSlot(1, soulCollector);

        ItemStack recipeResult = recipe.assemble(this.itemHandler, this.level.registryAccess());
        ItemStack outputSlot = this.itemOutputHandler.getStackInSlot(0);

        if (outputSlot.isEmpty()) this.itemOutputHandler.setStackInSlot(0, recipeResult);
        else outputSlot.grow(recipeResult.getCount());


        this.itemHandler.extractItem(0, 1, false);
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.progressMax;
    }

    private void increaseCraftingProgress(){
        startCraft = true;
        Optional<SoulInfuserRecipe> recipe = getCurrentRecipe();
        if(progress < recipe.get().getTime()){
            progress++;
        }
    }

    private void setMaxProgress(){
        Optional<SoulInfuserRecipe> recipe = getCurrentRecipe();
        if(progressMax <= 0){
            progressMax = recipe.map(SoulInfuserRecipe::getTime).orElse(200);
        }
    }

    private void resetProgress() {
        if (this.progress != 0 || this.startCraft) {
            this.progress = 0;
            this.progressMax = 0;
            this.startCraft = false;
            this.setChanged();
        }
    }

    public int getSouls(ItemStack stack){
        if(stack.getItem() instanceof ISoulItem soulItem){
            return soulItem.getCurrentSouls(stack);
        }

        return 0;
    }

    public void setSouls(ItemStack stack, int souls){
        if(stack.getItem() instanceof ISoulItem soulItem){
            soulItem.setSouls(souls, stack);
        }
    }

    public void consumeSouls(ItemStack stack, int souls){
        if(stack.getItem() instanceof ISoulItem soulItem){
            soulItem.consumeSouls(souls, stack);
        }
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
    public void load(@NotNull CompoundTag pTag){
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(pTag.getCompound("output"));
        progress = pTag.getInt("progress");
        progressMax = pTag.getInt("progressMax");
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
        return Component.translatable("menu.valoria.soul_infuser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer){
        return new SoulInfuserMenu(pContainerId, this.level, this.getBlockPos(), pPlayerInventory, pPlayer);
    }
}

