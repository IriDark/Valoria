package com.idark.valoria.client.compat.jei;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.recipe.CrusherRecipe;
import com.idark.valoria.registries.recipe.JewelryRecipe;
import com.idark.valoria.registries.recipe.KegRecipe;
import mezz.jei.api.recipe.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<KegRecipe> BREWERY = RecipeType.create(Valoria.MOD_ID, "brewery", KegRecipe.class);
    public static final RecipeType<JewelryRecipe> JEWELRY = RecipeType.create(Valoria.MOD_ID, "jewelry", JewelryRecipe.class);
    public static final RecipeType<CrusherRecipe> CRUSHER = RecipeType.create(Valoria.MOD_ID, "crusher", CrusherRecipe.class);

}