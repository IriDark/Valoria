package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.item.*;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public interface CrusherRecipeSchema{
    RecipeKey<String> LOOT_TABLE = StringComponent.ID.key("loot_table");
    RecipeKey<InputItem[][]> INGREDIENTS = ItemComponents.INPUT_ARRAY.asArray().key("ingredients");

    RecipeSchema SCHEMA = new RecipeSchema(LOOT_TABLE, INGREDIENTS);
}