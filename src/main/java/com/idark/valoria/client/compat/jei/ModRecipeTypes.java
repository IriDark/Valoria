package com.idark.valoria.client.compat.jei;

import com.idark.valoria.Valoria;
import com.idark.valoria.recipe.KegRecipe;
import mezz.jei.api.recipe.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<KegRecipe> BREWERY = RecipeType.create(Valoria.MOD_ID, "brewery", KegRecipe.class);
}
