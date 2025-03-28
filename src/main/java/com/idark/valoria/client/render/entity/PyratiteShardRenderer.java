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
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.jetbrains.annotations.*;

@OnlyIn(Dist.CLIENT)
public class PyratiteShardRenderer extends EntityRenderer<PyratiteShard>{
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/entity/pyratite_shard.png");
    private final CrystalShardModel<PyratiteShard> model;

    public PyratiteShardRenderer(EntityRendererProvider.Context pContext){
        super(pContext);
        this.model = new CrystalShardModel<>(CrystalShardModel.createBodyLayer().bakeRoot());
    }

    public void render(PyratiteShard pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight){
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
        pMatrixStack.translate(0.0F, -1.0F, 0.0F);
        float f9 = (float)pEntity.shakeTime - pPartialTicks;
        if (f9 > 0.0F) {
            float f10 = -Mth.sin(f9 * 3.0F) * f9;
            pMatrixStack.mulPose(Axis.ZP.rotationDegrees(f10));
        }

        this.model.prepareMobModel(pEntity, pEntity.getYRot(), 0.0F, pPartialTicks);
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull PyratiteShard pEntity){
        return TEXTURE;
    }
}