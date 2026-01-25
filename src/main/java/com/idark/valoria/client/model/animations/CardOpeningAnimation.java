package com.idark.valoria.client.model.animations;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.render.animation.*;

public class CardOpeningAnimation extends ItemAnimation{

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.rightArm.xRot = -1.35F;
        model.rightArm.yRot = -0.6F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.xRot = -1.35F;
        model.leftArm.yRot = 0.6F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        float time = player.tickCount + partialTicks;

        poseStack.translate(-0.25F, -0.45, -0.75F);

        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-25.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(25.0F));

        float breathe = Mth.sin(time * 0.1F) * 0.05F;
        poseStack.translate(0, breathe, 0);

        float shakeIntensity = 0.015F;
        float shakeX = (Mth.sin(time * 0.8F) * shakeIntensity);
        float shakeY = (Mth.cos(time * 1.2F) * shakeIntensity);
        
        poseStack.translate(shakeX, shakeY, 0);
        poseStack.scale(1.5f, 1.5f, 1.5f);
    }
}