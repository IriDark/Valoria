package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.entity.living.NecromancerEntity;
import com.idark.valoria.client.render.model.entity.NecromancerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NecromancerRenderer extends HumanoidMobRenderer<NecromancerEntity, NecromancerModel<NecromancerEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.MOD_ID, "textures/entity/necromancer.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new NecromancerModel<>(NecromancerModel.createBodyLayer().bakeRoot()),0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancerEntity pEntity) {
        return TEXTURE;
    }

    public NecromancerRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation p_174383_, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer) {
        super(pContext, new NecromancerModel<>(pContext.bakeLayer(p_174383_)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new NecromancerModel(pContext.bakeLayer(pInnerModelLayer)), new NecromancerModel(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
    }

    public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
        return TEXTURE;
    }

    protected boolean isShaking(AbstractSkeleton pEntity) {
        return pEntity.isShaking();
    }
}