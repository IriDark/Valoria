package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.SorcererModel;
import com.idark.valoria.registries.entity.living.SorcererEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SorcererRenderer extends MobRenderer<SorcererEntity, SorcererModel<SorcererEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/sorcerer.png");

    public SorcererRenderer(EntityRendererProvider.Context context){
        super(context, new SorcererModel<>(SorcererModel.createBodyLayer().bakeRoot()), 0.4F);
    }

    @Override
    public void render(SorcererEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SorcererEntity pEntity){
        return TEXTURE;
    }
}