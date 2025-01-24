package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.entity.projectile.SpectralBladeEntity;
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

public class SpectralBladeRenderer<T extends SpectralBladeEntity> extends EntityRenderer<T>{
    public SpectralBladeRenderer(EntityRendererProvider.Context context){
        super(context);
    }

    public void render(SpectralBladeEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light){
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.mulPose(Axis.ZP.rotationDegrees(45F));
        ItemStack stack = new ItemStack(ItemsRegistry.spectralBladeThrown.get());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, ms, buffers, entityIn.level(), 0);
        ms.popPose();
    }

    public ResourceLocation getTextureLocation(SpectralBladeEntity entity){
        return null;
    }
}