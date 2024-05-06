package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.entity.projectile.KunaiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class KunaiRenderer<T extends KunaiEntity> extends EntityRenderer<T> {
    public KunaiRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(KunaiEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        if (!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.onGround())) {
            entityIn.rotationVelocity = Mth.lerp(partialTicks, entityIn.rotationVelocity, entityIn.rotationVelocity + 10);
        }

        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO - 90 + entityIn.rotationVelocity, entityIn.getXRot() - 90 + entityIn.rotationVelocity)));
        ItemStack stack = entityIn.getItem();
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, ms, buffers, entityIn.level(), 0);
        ms.popPose();
    }

    public ResourceLocation getTextureLocation(KunaiEntity entity) {
        return null;
    }
}