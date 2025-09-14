package com.idark.valoria.client.ui.menus;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.*;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.client.render.gui.screen.*;

import java.util.*;

public class HeavyWorkbenchMenu extends ContainerMenuBase{
    private final Player player;
    private final Level level;
    private final ContainerLevelAccess access;
    public final List<WorkbenchRecipe> allRecipes;

    public HeavyWorkbenchMenu(int windowId, Inventory playerInventory) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }

    public HeavyWorkbenchMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, ContainerLevelAccess.NULL);
    }

    public HeavyWorkbenchMenu(int windowId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuRegistry.HEAVY_WORKBENCH.get(), windowId);
        this.player = playerInventory.player;
        this.level = playerInventory.player.level();
        this.access = access;
        this.allRecipes = this.level.getRecipeManager().getAllRecipesFor(WorkbenchRecipe.Type.INSTANCE);
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 114 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 172));
        }
    }

    public Player getPlayer(){
        return player;
    }

    public List<WorkbenchRecipe> getAllRecipes() {
        return allRecipes;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.heavyWorkbench.get());
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    /**
     * Checks if the player has the required ingredients for a given recipe.
     * This can be called on both client and server.
     */
    public boolean checkAndSetAvailability(WorkbenchRecipe recipe) {
        Inventory inv = this.player.getInventory();
        boolean isEnough = true;
        for (var entry : recipe.getInputs()) {
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            int currentCount = 0;

            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (ingredient.test(stack)) {
                    currentCount += stack.getCount();
                }
            }

            entry.getSecond().setCurrent(currentCount);
            entry.getSecond().setEnough(currentCount >= requiredCount);
            if (!entry.getSecond().isEnough) {
                isEnough = false;
            }
        }

        return isEnough;
    }

    /**
     * Consumes the required materials from the player's inventory.
     * This should ONLY be called on the server.
     */
    private void consumeMaterials(WorkbenchRecipe recipe) {
        Inventory inv = this.player.getInventory();
        for (var entry : recipe.getInputs()) {
            Ingredient ingredient = entry.getFirst();
            int requiredCount = entry.getSecond().count;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                if (requiredCount <= 0) break;
                ItemStack stack = inv.getItem(i);
                if (ingredient.test(stack)) {
                    int consumeAmount = Math.min(requiredCount, stack.getCount());
                    stack.shrink(consumeAmount);
                    requiredCount -= consumeAmount;
                }
            }
        }
    }

    public boolean tryCraftRecipe(ServerPlayer player, ResourceLocation recipeId) {
        Optional<WorkbenchRecipe> recipeHolder = level.getRecipeManager().byKey(recipeId)
        .filter(r -> r instanceof WorkbenchRecipe)
        .map(r -> (WorkbenchRecipe) r);

        if (recipeHolder.isPresent()) {
            WorkbenchRecipe recipe = recipeHolder.get();
            if (checkAndSetAvailability(recipe)) {
                consumeMaterials(recipe);
                ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY).copy();
                player.getInventory().placeItemBackInInventory(result);
                this.broadcastChanges();
                return true;
            }
        }
        return false;
    }
}