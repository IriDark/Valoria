package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public interface CrusherRecipeSchema{
    RecipeKey<String> LOOT_TABLE = StringComponent.ID.otherKey("loot_table");
    RecipeKey<List<Ingredient>> INGREDIENTS = IngredientComponent.INGREDIENT.instance().asList().inputKey("ingredients");

    RecipeSchema SCHEMA = new RecipeSchema(LOOT_TABLE, INGREDIENTS);
}
