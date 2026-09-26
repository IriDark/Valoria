package com.idark.valoria.core.compat.kubejs;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.kubejs.schemas.*;
import dev.latvian.mods.kubejs.plugin.*;
import dev.latvian.mods.kubejs.recipe.schema.*;

public class ValoriaKJSPlugin implements KubeJSPlugin{
    @Override
    public void init() {
    }

    @Override
    public void registerRecipeComponents(dev.latvian.mods.kubejs.recipe.component.RecipeComponentTypeRegistry registry) {
        registry.register(ValoriaRecipeComponents.ITEM_STACK);
        registry.register(ValoriaRecipeComponents.COUNTED_INGREDIENT);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(Valoria.loc("kiln"), KilnRecipeSchema.SCHEMA);
        registry.register(Valoria.loc("crusher"), CrusherRecipeSchema.SCHEMA);
        registry.register(Valoria.loc("jewelry"), JewelryRecipeSchema.SCHEMA);
        registry.register(Valoria.loc("keg_brewery"), KegRecipeSchema.SCHEMA);
        registry.register(Valoria.loc("heavy_workbench"), HeavyWorkbenchRecipeSchema.SCHEMA);
        registry.register(Valoria.loc("manipulator"), ManipulatorRecipeSchema.SCHEMA);
    }
}
