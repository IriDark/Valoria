package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.model.entity.DraugrModel;
import com.idark.valoria.registries.world.entity.living.GoblinEntity;
import com.idark.valoria.client.render.model.entity.GoblinModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class GoblinRenderer extends HumanoidMobRenderer<GoblinEntity, GoblinModel<GoblinEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinModel<>(GoblinModel.createBodyLayer().bakeRoot()),0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(GoblinEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.7f,0.7f,0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    public GoblinRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation p_174383_, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new GoblinModel<>(pContext.bakeLayer(p_174383_)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new GoblinModel(pContext.bakeLayer(pInnerModelLayer)), new GoblinModel(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
    }
}