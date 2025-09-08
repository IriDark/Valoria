package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

public class SamuraiArmorRenderer extends GeoArmorRenderer<SamuraiArmorItem>{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "armor/samurai");

    public SamuraiArmorRenderer() {
        super(new DefaultedItemGeoModel<>(GEO));
    }
}