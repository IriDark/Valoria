package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import net.minecraft.resources.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

public class PhantasmArmorRenderer extends GeoArmorRenderer<PhantasmArmor>{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "armor/phantasm");

    public PhantasmArmorRenderer() {
        super(new DefaultedItemGeoModel<>(GEO));
    }
}