package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.render.model.entity.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.resources.*;

public class GoblinRenderer extends MobRenderer<Goblin, GoblinModel<Goblin>>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context context){
        super(context, new GoblinModel<>(GoblinModel.createBodyLayer().bakeRoot()), 0.4F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));

//            @Override
//            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Goblin pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
//                boolean flag = pLivingEntity.getMainArm() == HumanoidArm.RIGHT;
//                ItemStack itemstack = flag ? pLivingEntity.getOffhandItem() : pLivingEntity.getMainHandItem();
//                ItemStack itemstack1 = flag ? pLivingEntity.getMainHandItem() : pLivingEntity.getOffhandItem();
//                if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
//                    pPoseStack.pushPose();
//                    if (this.getParentModel().young) {
//                        float f = 0.5F;
//                        pPoseStack.translate(0.0F, 0.75F, 0.0F);
//                        pPoseStack.scale(0.5F, 0.5F, 0.5F);
//                    }
//
////                    pPoseStack.scale(1, 1, 1);
////                    if(flag){
////                        pPoseStack.translate(0.05F, -0.350f, 0.025F);
////                    }else{
////                        pPoseStack.translate(-0.05F, -0.350f, -0.025F);
////                    }
//
//                    this.renderArmWithItem(pLivingEntity, itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, pPoseStack, pBuffer, pPackedLight);
//                    this.renderArmWithItem(pLivingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, pPoseStack, pBuffer, pPackedLight);
//                    pPoseStack.popPose();
//                }
//            }
//        });
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin entity){
        return TEXTURE;
    }

    @Override
    public void render(Goblin pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight){
        if(pEntity.isBaby()){
            pMatrixStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}