package com.idark.valoria.core.compat.jei;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.recipe.*;
import mezz.jei.api.recipe.*;

public class ModRecipeTypes{
    public static final RecipeType<KegRecipe> BREWERY = RecipeType.create(Valoria.ID, "brewery", KegRecipe.class);
    public static final RecipeType<JewelryRecipe> JEWELRY = RecipeType.create(Valoria.ID, "jewelry", JewelryRecipe.class);
    public static final RecipeType<CrusherRecipe> CRUSHER = RecipeType.create(Valoria.ID, "crusher", CrusherRecipe.class);
    public static final RecipeType<ManipulatorRecipe> MANIPULATOR = RecipeType.create(Valoria.ID, "manipulator", ManipulatorRecipe.class);
    public static final RecipeType<TinkeringRecipe> TINKERING = RecipeType.create(Valoria.ID, "tinkering", TinkeringRecipe.class);
    public static final RecipeType<KilnRecipe> KILN = RecipeType.create(Valoria.ID, "kiln", KilnRecipe.class);
}