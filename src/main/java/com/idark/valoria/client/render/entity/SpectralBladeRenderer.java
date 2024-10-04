package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;

public class SpectralBladeRenderer<T extends SpectralBladeEntity> extends EntityRenderer<T> {
    public SpectralBladeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(SpectralBladeEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.mulPose(Axis.ZP.rotationDegrees(45F));
        ItemStack stack = new ItemStack(ItemsRegistry.spectralBladeThrown.get());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, ms, buffers, entityIn.level(), 0);
        ms.popPose();
    }

    public ResourceLocation getTextureLocation(SpectralBladeEntity entity) {
        return null;
    }
}