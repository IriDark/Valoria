package com.idark.valoria.registries.item.recipe;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.core.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

/**
 * PORT NOTE: CustomRecipe lost its ResourceLocation id and crafting recipes take {@link CraftingInput} plus a
 * {@link HolderLookup.Provider} in 1.21. The poison state is the {@code valoria:poison_hits} component.
 */
public class PoisonWeaponRecipe extends CustomRecipe{
    public PoisonWeaponRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput inv, Level world) {
        boolean foundWeapon = false;
        boolean foundPoison = false;

        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ItemTags.SWORDS) && !PoisonItem.isPoisoned(stack)) {
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
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider registryAccess) {
        ItemStack weapon = ItemStack.EMPTY;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.is(ItemTags.SWORDS)) {
                weapon = stack.copy();
            }
        }

        if (!weapon.isEmpty()) {
            PoisonItem.setPoisonHits(weapon, 10);
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
