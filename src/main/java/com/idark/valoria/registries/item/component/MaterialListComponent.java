package com.idark.valoria.registries.item.component;

import com.idark.valoria.registries.item.recipe.*;
import com.mojang.datafixers.util.*;
import net.minecraft.world.inventory.tooltip.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public record MaterialListComponent(List<Pair<Ingredient, RecipeData>> list) implements TooltipComponent{
}