package com.idark.valoria.core.compat.kubejs;

import com.idark.valoria.*;
import com.idark.valoria.core.compat.kubejs.schemas.*;
import dev.latvian.mods.kubejs.*;
import dev.latvian.mods.kubejs.recipe.schema.*;
import dev.latvian.mods.kubejs.recipe.schema.minecraft.*;

public class ValoriaKJSPlugin extends KubeJSPlugin{
    @Override
    public void init() {
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace(Valoria.ID)
        .register("kiln", CookingRecipeSchema.SCHEMA)
        .register("crusher", CrusherRecipeSchema.SCHEMA)
        .register("jewelry", JewelryRecipeSchema.SCHEMA)
        .register("keg_brewery", KegRecipeSchema.SCHEMA)
        .register("heavy_workbench", HeavyWorkbenchRecipeSchema.SCHEMA)
        .register("manipulator", ManipulatorRecipeSchema.SCHEMA)
        ;
    }
}