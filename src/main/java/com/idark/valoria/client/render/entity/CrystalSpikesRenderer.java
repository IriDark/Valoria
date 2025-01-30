package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

@OnlyIn(Dist.CLIENT)
public class CrystalSpikesRenderer extends EntityRenderer<CrystalSpikes>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/crystal_spikes.png");
    private final CrystalSpikesModel<CrystalSpikes> model;

    public CrystalSpikesRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new CrystalSpikesModel<>(CrystalSpikesModel.createBodyLayer().bakeRoot());
    }

    public void render(CrystalSpikes pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
        float f = pEntity.getAnimationProgress(pPartialTicks);
        if(f != 0.0F){
            pMatrixStack.pushPose();
            pMatrixStack.mulPose(Axis.YP.rotationDegrees(pEntity.getYRot()));
            pMatrixStack.scale(-1, -1, 1);
            pMatrixStack.translate(0.0D, -1.5, 0.0D);
            this.model.prepareMobModel(pEntity, f, 0.0F, pPartialTicks);
            this.model.setupAnim(pEntity, f, f, pEntity.tickCount + f, pEntity.getYRot(), pEntity.getXRot());

            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
            this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            pMatrixStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull CrystalSpikes pEntity){
        return TEXTURE;
    }
}