package com.idark.valoria.client.render.layers;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.TrollModel;
import com.idark.valoria.registries.entity.living.Troll;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrollEyeLayer<T extends Troll, M extends TrollModel<T>> extends EyesLayer<T, M> {
    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(Valoria.ID, "textures/entity/troll_eyes_layer.png"));

    public TrollEyeLayer(RenderLayerParent<T, M> p_117507_) {
        super(p_117507_);
    }

    public RenderType renderType() {
        return EYES;
    }
}