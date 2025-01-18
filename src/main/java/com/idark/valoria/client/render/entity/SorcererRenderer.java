package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;

public class SorcererRenderer extends MobRenderer<SorcererEntity, SorcererModel<SorcererEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/sorcerer.png");

    public SorcererRenderer(EntityRendererProvider.Context context) {
        super(context, new SorcererModel<>(SorcererModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public void render(SorcererEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SorcererEntity pEntity) {
        return TEXTURE;
    }
}