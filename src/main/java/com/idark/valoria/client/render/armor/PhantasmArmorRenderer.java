package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import mod.azure.azurelib.rewrite.render.armor.*;
import net.minecraft.resources.*;

public class PhantasmArmorRenderer extends AzArmorRenderer{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "geo/phantasm.geo.json");
    private static final ResourceLocation TEX = new ResourceLocation(Valoria.ID, "textures/models/armor/phantasm_armor.png");

    public PhantasmArmorRenderer() {
        super(AzArmorRendererConfig.builder(GEO, TEX).build());
    }
}