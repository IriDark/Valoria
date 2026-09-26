package com.idark.valoria.core.compat.kubejs;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.recipe.*;
import com.mojang.datafixers.util.*;
import dev.latvian.mods.kubejs.recipe.component.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

public final class ValoriaRecipeComponents{
    private ValoriaRecipeComponents(){}

    public static final RecipeComponentType<ItemStack> ITEM_STACK = RecipeComponentType.unit(Valoria.loc("item_stack"),
        type -> new ItemStackComponent(type, RecipeCodecs.ITEM_STACK, false, Ingredient.EMPTY));

    public static final RecipeComponentType<Pair<Ingredient, RecipeData>> COUNTED_INGREDIENT = RecipeComponentType.unit(Valoria.loc("counted_ingredient"),
        CountedIngredientComponent::new);
}
