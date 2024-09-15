package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.TrollModel;
import com.idark.valoria.client.render.layers.TrollEyeLayer;
import com.idark.valoria.registries.entity.living.Troll;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TrollRenderer extends MobRenderer<Troll, TrollModel<Troll>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/troll.png");

    public TrollRenderer(EntityRendererProvider.Context context) {
        super(context, new TrollModel<>(TrollModel.createBodyLayer().bakeRoot()), 0.8F);
        this.addLayer(new TrollEyeLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Troll pEntity) {
        return TEXTURE;
    }
}