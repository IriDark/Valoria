package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
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