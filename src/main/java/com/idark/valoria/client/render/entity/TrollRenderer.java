package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.client.render.layers.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class TrollRenderer extends MobRenderer<Troll, TrollModel<Troll>> {
    protected ResourceLocation texture;

    public TrollRenderer(EntityRendererProvider.Context context, boolean corrupted) {
        super(context, new TrollModel<>(TrollModel.createBodyLayer().bakeRoot()), 0.8F);
        this.texture = new ResourceLocation(Valoria.ID, corrupted ? "textures/entity/corrupted_troll.png" : "textures/entity/troll.png");
        this.addLayer(new LuminescentLayer.Builder<>(this)
        .setTexture(new ResourceLocation(Valoria.ID, corrupted ? "textures/entity/corrupted_troll_eyes.png" : "textures/entity/troll_eyes_layer.png"))
        .build());
    }

    @Override
    public ResourceLocation getTextureLocation(Troll pEntity) {
        return texture;
    }
}