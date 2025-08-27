package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import mod.azure.azurelib.rewrite.render.armor.*;
import net.minecraft.resources.*;

public class SamuraiArmorRenderer extends AzArmorRenderer{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "geo/samurai.geo.json");
    private static final ResourceLocation TEX = new ResourceLocation(Valoria.ID, "textures/models/armor/samurai_armor.png");
    private static final ResourceLocation ANIM = new ResourceLocation(Valoria.ID, "animations/item/samurai.animation.json");

    public SamuraiArmorRenderer() {
        super(AzArmorRendererConfig.builder(GEO, TEX).setAnimatorProvider(() -> new ArmorAnimator(ANIM)).build());
    }
}