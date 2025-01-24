package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.model.entity.DevourerModel;
import com.idark.valoria.registries.entity.projectile.Devourer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DevourerRenderer extends EntityRenderer<Devourer>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/devourer.png");
    private final DevourerModel<Devourer> model;

    public DevourerRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new DevourerModel<>(DevourerModel.createBodyLayer().bakeRoot());
    }

    public void render(Devourer pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull Devourer pEntity){
        return TEXTURE;
    }
}