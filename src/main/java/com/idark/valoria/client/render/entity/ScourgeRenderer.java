package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.ScourgeModel;
import com.idark.valoria.registries.entity.living.ScourgeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScourgeRenderer extends MobRenderer<ScourgeEntity, ScourgeModel<ScourgeEntity>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/scourge.png");

    public ScourgeRenderer(EntityRendererProvider.Context context){
        super(context, new ScourgeModel<>(ScourgeModel.createBodyLayer().bakeRoot()), 0.75F);
    }

    @Override
    public void render(ScourgeEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.6f, 0.6f, 0.6f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ScourgeEntity entity){
        return TEXTURE;
    }
}