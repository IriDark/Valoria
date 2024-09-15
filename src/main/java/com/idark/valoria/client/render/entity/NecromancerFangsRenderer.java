package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.entity.projectile.NecromancerFangs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NecromancerFangsRenderer extends EntityRenderer<NecromancerFangs> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/necromancer_fangs.png");
    private final EvokerFangsModel<NecromancerFangs> model;

    public NecromancerFangsRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new EvokerFangsModel<>(pContext.bakeLayer(ModelLayers.EVOKER_FANGS));
    }

    public void render(NecromancerFangs pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        float f = pEntity.getAnimationProgress(pPartialTicks);
        if (f != 0.0F) {
            float f1 = 2.0F;
            if (f > 0.9F) {
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull NecromancerFangs pEntity) {
        return TEXTURE;
    }
}