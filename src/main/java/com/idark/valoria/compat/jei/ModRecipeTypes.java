package com.idark.valoria.compat.jei;

import com.idark.valoria.*;
import com.idark.valoria.registries.recipe.*;
import mezz.jei.api.recipe.*;

public class ModRecipeTypes{
    public static final RecipeType<KegRecipe> BREWERY = RecipeType.create(Valoria.ID, "brewery", KegRecipe.class);
    public static final RecipeType<JewelryRecipe> JEWELRY = RecipeType.create(Valoria.ID, "jewelry", JewelryRecipe.class);
    public static final RecipeType<CrusherRecipe> CRUSHER = RecipeType.create(Valoria.ID, "crusher", CrusherRecipe.class);
    public static final RecipeType<ManipulatorRecipe> MANIPULATOR = RecipeType.create(Valoria.ID, "manipulator", ManipulatorRecipe.class);

}