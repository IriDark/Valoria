package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;

public class DevilRenderer extends MobRenderer<Devil, DevilModel<Devil>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/devil.png");
    protected static final ResourceLocation TEXTURE_ANGER = new ResourceLocation(Valoria.ID, "textures/entity/devil_anger.png");

    public DevilRenderer(EntityRendererProvider.Context context) {
        super(context, new DevilModel<>(DevilModel.createBodyLayer().bakeRoot()), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public void render(Devil pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Devil pEntity) {
        return pEntity.isAggressive() ? TEXTURE_ANGER : TEXTURE;
    }
}