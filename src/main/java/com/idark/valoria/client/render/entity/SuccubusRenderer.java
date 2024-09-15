package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.SuccubusModel;
import com.idark.valoria.registries.entity.living.Succubus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SuccubusRenderer extends MobRenderer<Succubus, SuccubusModel<Succubus>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/succubus.png");

    public SuccubusRenderer(EntityRendererProvider.Context context) {
        super(context, new SuccubusModel<>(SuccubusModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public void render(Succubus pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Succubus pEntity) {
        return TEXTURE;
    }
}