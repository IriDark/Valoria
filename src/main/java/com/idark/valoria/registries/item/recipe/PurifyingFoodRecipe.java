package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.consumables.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class PurifyingFoodRecipe extends CustomRecipe{
    public PurifyingFoodRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput inv, Level world) {
        boolean foundRot = false;
        boolean foundVIal = false;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ItemsRegistry.rot.get())) {
                    if (foundRot) return false;
                    foundRot = true;
                } else if (stack.getItem() == ItemsRegistry.clarityVial.get()) {
                    if (foundVIal) return false;
                    foundVIal = true;
                } else {
                    return false;
                }
            }
        }

        return foundRot && foundVIal;
    }

    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registryAccess) {
        ItemStack rot = ItemStack.EMPTY;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemsRegistry.rot.get())) {
                rot = stack.copy();
            }
        }

        if (!rot.isEmpty()) {
            var original = RotItem.getOriginalItem(rot);
            if(original.isPresent()){
                var itemOpt = BuiltInRegistries.ITEM.getHolder(original.get());
                if(itemOpt.isPresent()){
                    return new ItemStack(itemOpt.get());
                }
            }
        }

        return rot;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.PURIFY_RECIPE.get();
    }
}
