package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.minions.FleshSentinel;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class FleshSentinelRenderer extends MobRenderer<FleshSentinel, FleshSentinelModel<FleshSentinel>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/flesh_sentinel.png");
    public FleshSentinelRenderer(EntityRendererProvider.Context context) {
        super(context, new FleshSentinelModel<>(FleshSentinelModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(FleshSentinel entity) {
        return TEXTURE;
    }

    @Override
    public void render(FleshSentinel pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
