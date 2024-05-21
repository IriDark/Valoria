package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.render.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;

public class GoblinRenderer extends HumanoidMobRenderer<GoblinEntity, GoblinModel<GoblinEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context context){
        super(context, new GoblinModel<>(GoblinModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    public GoblinRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation p_174383_, ModelLayerLocation pInnerModelLayer, ModelLayerLocation pOuterModelLayer){
        super(pContext, new GoblinModel<>(pContext.bakeLayer(p_174383_)), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, new GoblinModel(pContext.bakeLayer(pInnerModelLayer)), new GoblinModel(pContext.bakeLayer(pOuterModelLayer)), pContext.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinEntity entity){
        return TEXTURE;
    }

    @Override
    public void render(GoblinEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}