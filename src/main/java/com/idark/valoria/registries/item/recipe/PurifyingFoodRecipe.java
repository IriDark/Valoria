package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.registries.*;

public class PurifyingFoodRecipe extends CustomRecipe{
    public PurifyingFoodRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        boolean foundRot = false;
        boolean foundVIal = false;
        for (int i = 0; i < inv.getContainerSize(); i++) {
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
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack rot = ItemStack.EMPTY;
        int index = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemsRegistry.rot.get())) {
                index = i;
                rot = stack.copy();
            }
        }

        if (!rot.isEmpty()) {
            var itemOpt = ForgeRegistries.ITEMS.getDelegate(ResourceLocation.tryParse(rot.getOrCreateTag().getString("OriginalItem")));
            if(itemOpt.isPresent()){
                return new ItemStack(itemOpt.get());
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