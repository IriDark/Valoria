package com.idark.valoria.core.compat.kubejs.schemas;

import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

public interface KilnRecipeSchema{
    RecipeKey<Ingredient> INGREDIENT = IngredientComponent.INGREDIENT.inputKey("ingredient");
    RecipeKey<ItemStack> RESULT = ItemStackComponent.ITEM_STACK.outputKey("result");
    RecipeKey<Float> EXPERIENCE = NumberComponent.FLOAT.otherKey("experience").optional(0F);
    RecipeKey<Integer> COOKING_TIME = NumberComponent.INT.otherKey("cookingtime").optional(120);

    RecipeSchema SCHEMA = new RecipeSchema(RESULT, INGREDIENT, EXPERIENCE, COOKING_TIME).uniqueId(RESULT);
}
