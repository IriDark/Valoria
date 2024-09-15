package com.idark.valoria.compat.jei;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.item.recipe.CrusherRecipe;
import com.idark.valoria.registries.item.recipe.JewelryRecipe;
import com.idark.valoria.registries.item.recipe.KegRecipe;
import com.idark.valoria.registries.item.recipe.ManipulatorRecipe;
import mezz.jei.api.recipe.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<KegRecipe> BREWERY = RecipeType.create(Valoria.ID, "brewery", KegRecipe.class);
    public static final RecipeType<JewelryRecipe> JEWELRY = RecipeType.create(Valoria.ID, "jewelry", JewelryRecipe.class);
    public static final RecipeType<CrusherRecipe> CRUSHER = RecipeType.create(Valoria.ID, "crusher", CrusherRecipe.class);
    public static final RecipeType<ManipulatorRecipe> MANIPULATOR = RecipeType.create(Valoria.ID, "manipulator", ManipulatorRecipe.class);

}