package com.idark.valoria.client.render.entity;

import com.idark.valoria.*;
import com.idark.valoria.client.model.entity.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;


@OnlyIn(Dist.CLIENT)
public class AcornRenderer extends EntityRenderer<AcornProjectile>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/projectile/acorn.png");
    private final AcornModel<AcornProjectile> model;

    public AcornRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new AcornModel<>(AcornModel.createBodyLayer().bakeRoot());
    }

    protected void scale(AcornProjectile pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = pLivingEntity.getSwelling(pPartialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        pPoseStack.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(AcornProjectile pLivingEntity, float pPartialTicks) {
        float f = pLivingEntity.getSwelling(pPartialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    public static int getOverlayCoords(float pU) {
        return OverlayTexture.pack(OverlayTexture.u(pU), OverlayTexture.v(false));
    }

    public void render(AcornProjectile pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
        pMatrixStack.pushPose();
        if(!Minecraft.getInstance().isPaused() && !(pEntity.inGround || pEntity.onGround())){
            pEntity.rotationVelocity = Mth.lerp(pPartialTicks, pEntity.rotationVelocity, pEntity.rotationVelocity + 10);
        }

        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) + 90.0F));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO - 90 + pEntity.rotationVelocity, pEntity.getXRot() - 90 + pEntity.rotationVelocity)));
        this.scale(pEntity, pMatrixStack, pPartialTicks);
        pMatrixStack.translate(0.0D, -1.5, 0.0D);
        this.model.prepareMobModel(pEntity, pEntity.getYRot(), 0.0F, pPartialTicks);

        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        int i = getOverlayCoords(this.getWhiteOverlayProgress(pEntity, pPartialTicks));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull AcornProjectile pEntity){
        return TEXTURE;
    }
}