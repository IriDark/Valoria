package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.client.render.layers.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class TrollRenderer extends MobRenderer<Troll, TrollModel<Troll>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/troll.png");

    public TrollRenderer(EntityRendererProvider.Context context) {
        super(context, new TrollModel<>(TrollModel.createBodyLayer().bakeRoot()), 0.8F);
        this.addLayer(new LuminescentLayer<>(new ResourceLocation(Valoria.ID, "textures/entity/troll_eyes_layer.png"), this));
    }

    @Override
    public ResourceLocation getTextureLocation(Troll pEntity) {
        return TEXTURE;
    }
}