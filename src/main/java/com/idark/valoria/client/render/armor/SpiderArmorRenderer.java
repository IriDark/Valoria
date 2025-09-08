package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

public class SpiderArmorRenderer extends GeoArmorRenderer<SamuraiArmorItem>{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "armor/spider");

    public SpiderArmorRenderer() {
        super(new DefaultedItemGeoModel<>(GEO));
    }
}