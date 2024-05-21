package com.idark.valoria.client.render.entity;

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

public class KunaiRenderer<T extends KunaiEntity> extends EntityRenderer<T>{
    public KunaiRenderer(EntityRendererProvider.Context context){
        super(context);
    }

    public void render(KunaiEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light){
        if(!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.onGround())){
            entityIn.rotationVelocity = Mth.lerp(partialTicks, entityIn.rotationVelocity, entityIn.rotationVelocity + 10);
        }

        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO - 90 + entityIn.rotationVelocity, entityIn.getXRot() - 90 + entityIn.rotationVelocity)));
        ItemStack stack = entityIn.getItem();
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, ms, buffers, entityIn.level(), 0);
        ms.popPose();
    }

    public ResourceLocation getTextureLocation(KunaiEntity entity){
        return null;
    }
}