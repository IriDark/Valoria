package com.idark.valoria.client.compat.jei;

import com.idark.valoria.recipe.JewelryRecipe;
import com.idark.valoria.recipe.KegRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class ModJeiRecipes {
    private final RecipeManager recipeManager;

    public ModJeiRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public List<KegRecipe> getBreweryRecipes() {
        return recipeManager.getAllRecipesFor(KegRecipe.Type.INSTANCE).stream().toList();
    }

    public List<JewelryRecipe> getJewelryRecipes() {
        return recipeManager.getAllRecipesFor(JewelryRecipe.Type.INSTANCE).stream().toList();
    }
}