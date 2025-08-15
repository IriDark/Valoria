package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.*;

@OnlyIn(Dist.CLIENT)
public class WaterBubbleRenderer extends EntityRenderer<WaterBubble>{
   private static final ResourceLocation LOCATION = new ResourceLocation(Valoria.ID, "textures/entity/water_bubble.png");
   private final WaterBubbleModel<WaterBubble> model;

   public WaterBubbleRenderer(Context pContext) {
      super(pContext);
      this.model = new WaterBubbleModel<>(WaterBubbleModel.createBodyLayer().bakeRoot());
   }

   public void render(WaterBubble pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
       pPoseStack.pushPose();
       pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
       pPoseStack.scale(-1.0F, -1.0F, 1.0F);
       pPoseStack.translate(0.0F, -1.501F, 0.0F);

       double ticksUp = (ClientTick.ticksInGame + pPartialTicks) * 4;
       ticksUp = (ticksUp) % 360;
       pPoseStack.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.12125F), 0F);

       float f = pEntity.getAnimationProgress(pPartialTicks);
       VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
       this.model.prepareMobModel(pEntity, f, 0.0F, pPartialTicks);
       this.model.setupAnim(pEntity, f, f, pEntity.tickCount + f, pEntity.getYRot(), pEntity.getXRot());
       this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);


       pPoseStack.popPose();
       super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
   }

   /**
    * Returns the location of an entity's texture.
    */
   public ResourceLocation getTextureLocation(WaterBubble pEntity) {
      return LOCATION;
   }
}