package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

@OnlyIn(Dist.CLIENT)
public class NecromancerFangsRenderer extends EntityRenderer<NecromancerFangs>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necromancer_fangs.png");
    private final EvokerFangsModel<NecromancerFangs> model;

    public NecromancerFangsRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new EvokerFangsModel<>(pContext.bakeLayer(ModelLayers.EVOKER_FANGS));
    }

    public void render(NecromancerFangs pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
        float f = pEntity.getAnimationProgress(pPartialTicks);
        if(f != 0.0F){
            float f1 = 2.0F;
            if(f > 0.9F){
                f1 *= (1.0F - f) / 0.1F;
            }

            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F - pEntity.getYRot()));
            pMatrixStack.scale(-f1, -f1, f1);
            pMatrixStack.translate(0.0D, -0.626D, 0.0D);
            pMatrixStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setupAnim(pEntity, f, 0.0F, 0.0F, pEntity.getYRot(), pEntity.getXRot());
            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
            this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull NecromancerFangs pEntity){
        return TEXTURE;
    }
}