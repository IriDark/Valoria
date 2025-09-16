package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.item.*;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public interface HeavyWorkbenchRecipeSchema{
    RecipeKey<InputItem[]> INGREDIENTS = ItemComponents.INPUT_ARRAY.key("ingredients");
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT_ID_WITH_COUNT.key("result");
    RecipeKey<String> GROUP = StringComponent.ANY.key("group");

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENTS, RESULT, GROUP).uniqueOutputId(RESULT);
}