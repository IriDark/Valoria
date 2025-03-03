package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.item.*;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public interface JewelryRecipeSchema{
    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("output");
    RecipeKey<Integer> TIME = NumberComponent.INT.key("time");
    RecipeKey<InputItem[][]> INGREDIENTS = ItemComponents.INPUT_ARRAY.asArray().key("ingredients");

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENTS, TIME).uniqueOutputId(OUTPUT);
}