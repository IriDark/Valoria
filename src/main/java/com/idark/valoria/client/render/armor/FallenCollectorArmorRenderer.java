package com.idark.valoria.client.render.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.armor.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.renderer.*;

public class FallenCollectorArmorRenderer extends GeoArmorRenderer<FallenCollectorArmorItem>{
    private static final ResourceLocation GEO = new ResourceLocation(Valoria.ID, "armor/collector");

    @Override
    public RenderType getRenderType(FallenCollectorArmorItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick){
        return RenderType.entityTranslucent(texture);
    }

    public FallenCollectorArmorRenderer() {
        super(new DefaultedItemGeoModel<>(GEO));
    }
}