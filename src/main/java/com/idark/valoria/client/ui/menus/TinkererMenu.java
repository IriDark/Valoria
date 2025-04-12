package com.idark.valoria.client.ui.menus;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import com.idark.valoria.registries.item.recipe.TinkeringRecipe.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.render.gui.screen.*;

import java.util.*;

public class TinkererMenu extends ContainerMenuBase{
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final ContainerLevelAccess access;
    private final Level level;
    private TinkeringRecipe currentRecipe;
    private List<TinkeringRecipe> recipes = Lists.newArrayList();
    private ItemStack input = ItemStack.EMPTY;
    long lastSoundTime;
    private final Slot inputSlot;
    private final Slot resultSlot;
    private Runnable slotUpdateListener = () -> {
    };
    public final Container container = new SimpleContainer(1){
        public void setChanged(){
            super.setChanged();
            TinkererMenu.this.slotsChanged(this);
            TinkererMenu.this.slotUpdateListener.run();
        }
    };

    final ResultContainer resultContainer = new ResultContainer();

    public TinkererMenu(int windowId, Inventory playerInventory){
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }

    public TinkererMenu(int windowId, Inventory playerInventory, final ContainerLevelAccess pAccess){
        super(MenuRegistry.TINKERING_MENU.get(), windowId);
        this.level = playerInventory.player.level();
        this.access = pAccess;
        this.inputSlot = this.addSlot(new Slot(this.container, 0, 20, 33));
        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33){
            public boolean mayPlace(ItemStack p_40362_){
                return false;
            }

            public void onTake(Player p_150672_, ItemStack p_150673_){
                p_150673_.onCraftedBy(p_150672_.level(), p_150672_, p_150673_.getCount());
                TinkererMenu.this.resultContainer.awardUsedRecipes(p_150672_, this.getRelevantItems());
                ItemStack itemstack = TinkererMenu.this.inputSlot.remove(currentRecipe.getIngredientCount());
                if(!itemstack.isEmpty()){
                    TinkererMenu.this.setupResultSlot();
                }

                pAccess.execute((p_40364_, p_40365_) -> {
                    long l = p_40364_.getGameTime();
                    if(TinkererMenu.this.lastSoundTime != l){
                        p_40364_.playSound(null, p_40365_, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        TinkererMenu.this.lastSoundTime = l;
                    }

                });
                super.onTake(p_150672_, p_150673_);
            }

            private List<ItemStack> getRelevantItems(){
                return List.of(TinkererMenu.this.inputSlot.getItem());
            }
        });

        this.playerInventory = new InvWrapper(playerInventory);
        this.layoutPlayerInventorySlots(8, 84);
        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex(){
        return this.selectedRecipeIndex.get();
    }

    @Nullable
    public TinkeringRecipe getCurrentRecipe(){
        return this.currentRecipe;
    }

    public void slotsChanged(Container pInventory){
        ItemStack itemstack = this.inputSlot.getItem();
        if(!itemstack.is(this.input.getItem())){
            this.input = itemstack.copy();
            this.setupRecipeList(pInventory, itemstack);
        }

        this.setupResultSlot();
        this.broadcastChanges();
    }

    private void setupRecipeList(Container pContainer, ItemStack pStack){
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        if(!pStack.isEmpty()){
            this.recipes = this.level.getRecipeManager().getRecipesFor(Type.INSTANCE, pContainer, this.level);
        }
    }

    public boolean clickMenuButton(Player pPlayer, int pId){
        if(this.isValidRecipeIndex(pId)){
            this.selectedRecipeIndex.set(pId);
            this.setupResultSlot();
        }

        return true;
    }

    private boolean isValidRecipeIndex(int pRecipeIndex){
        return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
    }

    void setupResultSlot(){
        if(!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())){
            currentRecipe = this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = currentRecipe.assemble(container, this.level.registryAccess());
            if(itemstack.isItemEnabled(this.level.enabledFeatures())){
                this.resultContainer.setRecipeUsed(currentRecipe);
                this.resultSlot.set(itemstack);
            }else{
                this.resultSlot.set(ItemStack.EMPTY);
            }
        }else{
            this.resultSlot.set(ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    public void registerUpdateListener(Runnable pListener){
        this.slotUpdateListener = pListener;
    }

    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot){
        return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex){
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if(slot != null && slot.hasItem()){
            ItemStack itemstack1 = slot.getItem();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if(pIndex == 1){
                item.onCraftedBy(itemstack1, pPlayer.level(), pPlayer);
                if(!this.moveItemStackTo(itemstack1, 2, 38, true)){
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            }else if(pIndex == 0){
                if(!this.moveItemStackTo(itemstack1, 2, 38, false)){
                    return ItemStack.EMPTY;
                }
            }else if(this.level.getRecipeManager().getRecipeFor(Type.INSTANCE, new SimpleContainer(itemstack1), this.level).isPresent()){
                if(!this.moveItemStackTo(itemstack1, 0, 1, false)){
                    return ItemStack.EMPTY;
                }
            }else if(pIndex >= 2 && pIndex < 29){
                if(!this.moveItemStackTo(itemstack1, 29, 38, false)){
                    return ItemStack.EMPTY;
                }
            }else if(pIndex >= 29 && pIndex < 38 && !this.moveItemStackTo(itemstack1, 2, 29, false)){
                return ItemStack.EMPTY;
            }

            if(itemstack1.isEmpty()){
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if(itemstack1.getCount() == itemstack.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            this.broadcastChanges();
        }

        return itemstack;
    }

    public void removed(Player pPlayer){
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(1);
        this.access.execute((p_40313_, p_40314_) -> {
            this.clearContainer(pPlayer, this.container);
        });
    }

    public List<TinkeringRecipe> getRecipes(){
        return this.recipes;
    }

    public int getNumRecipes(){
        return this.recipes.size();
    }

    public boolean hasInputItem(){
        return this.inputSlot.hasItem() && !this.recipes.isEmpty();
    }

    @Override
    public boolean stillValid(Player playerIn){
        return stillValid(this.access, playerIn, BlockRegistry.tinkererWorkbench.get());
    }
}