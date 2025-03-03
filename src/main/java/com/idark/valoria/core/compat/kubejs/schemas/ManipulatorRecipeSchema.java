package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.item.*;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public interface ManipulatorRecipeSchema {
    RecipeKey<String> CORE = StringComponent.ID.key("core");
    RecipeKey<Integer> CORES = NumberComponent.INT.key("cores");
    RecipeKey<Integer> TIME = NumberComponent.INT.key("time");

    RecipeKey<InputItem[][]> INGREDIENTS = ItemComponents.INPUT_ARRAY.asArray().key("ingredients");
    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("output");

    RecipeSchema SCHEMA = new RecipeSchema(CORE, CORES, TIME, INGREDIENTS).uniqueOutputId(OUTPUT);
}