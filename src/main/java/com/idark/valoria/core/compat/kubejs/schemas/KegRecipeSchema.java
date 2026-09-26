package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public interface KegRecipeSchema{
    RecipeKey<ItemStack> OUTPUT = com.idark.valoria.core.compat.kubejs.ValoriaRecipeComponents.ITEM_STACK.instance().outputKey("output"); // PORT NOTE: lenient {"item"|"id","count"} codec
    RecipeKey<Integer> TIME = NumberComponent.INT.otherKey("time");
    RecipeKey<List<Ingredient>> INGREDIENTS = IngredientComponent.INGREDIENT.instance().asList().inputKey("ingredients");

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, INGREDIENTS, TIME).uniqueId(OUTPUT);
}
