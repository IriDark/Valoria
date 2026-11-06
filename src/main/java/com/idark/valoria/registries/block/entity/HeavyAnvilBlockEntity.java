package com.idark.valoria.registries.block.entity;

import com.idark.valoria.core.recipe.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.util.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.items.*;

import javax.annotation.*;
import java.util.*;

public class HeavyAnvilBlockEntity extends BlockEntity {
    public final ItemStackHandler itemHandler = createHandler(1);

    public int progress = 0;
    public int requiredHits = 0;
    public int instability = 0;
    public int maxInstability = 0;
    public boolean tierTooLow = false;

    public float cursorSpeed = 0.07f;
    public double sweetSpotMin = 0.40;
    public double sweetSpotMax = 0.60;
    public long lastHitTick = 0;

    public HeavyAnvilBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntitiesRegistry.ANVIL_BLOCK_ENTITY.get(), pPos, pBlockState);
        randomizeSweetSpot();
    }

    public HeavyAnvilTier getTier() {
        if (this.getBlockState().getBlock() instanceof HeavyAnvil anvil) {
            return anvil.tier;
        }

        return HeavyAnvilTier.BRONZE;
    }

    public double getLinePosition(Level level, float partialTick) {
        double time = level.getGameTime() + partialTick;
        double sine = Math.sin(time * cursorSpeed);
        double sharpness = 2;
        double distorted = Math.atan(sharpness * sine) / Math.atan(sharpness);
        return (distorted + 1.0) / 2.0;
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

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
                if(!isItemValid(slot, stack)){
                    return stack;
                }
                
                ItemStack result = super.insertItem(slot, stack, simulate);
                if(slot == 0 && !simulate && !stack.isEmpty()) {
                    getCurrentRecipe().ifPresent(recipe -> {
                        if (getTier().getLevel() < recipe.getMinTier().getLevel()) {
                            tierTooLow = true;
                            requiredHits = 0;
                            maxInstability = 0;
                        } else {
                            tierTooLow = false;
                            int bonus = recipe.acceptsTierBonus() ? getTier().getInstabilityBonus() : 0;
                            requiredHits = recipe.getRequiredHits();
                            maxInstability = recipe.getMaxInstability() + bonus;
                            cursorSpeed = recipe.getCursorSpeed();
                        }
                    });

                    randomizeSweetSpot();
                    sync();
                }

                return result;
            }
        };
    }

    public void randomizeSweetSpot(double bonus) {
        double baseRange = Math.max(0.15, 0.30 - (this.progress * 0.015));
        double range = Math.min(0.70, baseRange + bonus);
        this.sweetSpotMin = 0.1 + Math.random() * (0.8 - range);
        this.sweetSpotMax = this.sweetSpotMin + range;
        sync();
    }

    public void randomizeSweetSpot() {
        randomizeSweetSpot(0);
    }

    public void resetProgress(){
        progress = 0;
        requiredHits = 0;
        instability = 0;
        maxInstability = 0;
        cursorSpeed = 0.07f;
        tierTooLow = false;
        randomizeSweetSpot();
    }

    public Optional<HeavyAnvilRecipe> getCurrentRecipe(){
        if (level == null) return Optional.empty();
        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, itemHandler.getStackInSlot(0));
        return this.level.getRecipeManager().getRecipeFor(HeavyAnvilRecipe.Type.INSTANCE, ContainerRecipeInput.of(this.itemHandler), level).map(RecipeHolder::value);
    }

    public void sync() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, lookupProvider);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, lookupProvider);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            ValoriaUtils.SUpdateTileEntityPacket(this);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inv"));
        this.progress = pTag.getInt("Progress");
        this.instability = pTag.getInt("Instability");
        this.requiredHits = pTag.getInt("RequiredHits");
        this.maxInstability = pTag.getInt("MaxInstability");
        this.cursorSpeed = pTag.getFloat("CursorSpeed");
        this.tierTooLow = pTag.getBoolean("TierTooLow");
        if (pTag.contains("SweetMin")) {
            this.sweetSpotMin = pTag.getDouble("SweetMin");
            this.sweetSpotMax = pTag.getDouble("SweetMax");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inv", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("Progress", this.progress);
        pTag.putInt("Instability", this.instability);
        pTag.putInt("RequiredHits", this.requiredHits);
        pTag.putInt("MaxInstability", this.maxInstability);
        pTag.putFloat("CursorSpeed", this.cursorSpeed);
        pTag.putBoolean("TierTooLow", this.tierTooLow);
        pTag.putDouble("SweetMin", this.sweetSpotMin);
        pTag.putDouble("SweetMax", this.sweetSpotMax);
    }
}
