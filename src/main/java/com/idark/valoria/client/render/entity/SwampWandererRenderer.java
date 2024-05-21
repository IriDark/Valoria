package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.model.entity.SwampWandererModel;
import com.idark.valoria.registries.entity.living.SwampWandererEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SwampWandererRenderer extends HumanoidMobRenderer<SwampWandererEntity, SwampWandererModel<SwampWandererEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/swamp_wanderer.png");

    public SwampWandererRenderer(EntityRendererProvider.Context context){
        super(context, new SwampWandererModel<>(SwampWandererModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public void render(SwampWandererEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.6f, 0.6f, 0.6f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SwampWandererEntity entity){
        return TEXTURE;
    }
}