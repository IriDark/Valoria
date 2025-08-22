package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class PoisonWeaponRecipe extends CustomRecipe{
    public PoisonWeaponRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        boolean foundWeapon = false;
        boolean foundPoison = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ItemTags.SWORDS) && (stack.getTag() != null && !stack.getTag().contains("poison_hits"))) {
                    if (foundWeapon) return false;
                    foundWeapon = true;
                } else if (stack.getItem() == ItemsRegistry.toxinsBottle.get()) {
                    if (foundPoison) return false;
                    foundPoison = true;
                } else {
                    return false;
                }
            }
        }
        return foundWeapon && foundPoison;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack weapon = ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemTags.SWORDS)) {
                weapon = stack.copy();
            }
        }

        if (!weapon.isEmpty()) {
            weapon.getOrCreateTag().putInt("poison_hits", 10);
        }

        return weapon;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.POISON_RECIPE.get();
    }
}