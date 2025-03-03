package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.item.*;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public interface ArchaeologyRecipeSchema{
    RecipeKey<InputItem> INGREDIENT = ItemComponents.INPUT.key("ingredient");
    RecipeKey<Integer> INGREDIENT_COUNT = NumberComponent.INT.key("ingredient_count");
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
    RecipeKey<Integer> RESULT_COUNT = NumberComponent.INT.key("count");

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENT, INGREDIENT_COUNT, RESULT_COUNT).uniqueOutputId(RESULT);
}