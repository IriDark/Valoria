package com.idark.valoria.client.model.animations;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import mod.maxbogomol.fluffy_fur.client.animation.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public class SpinAttackAnimation extends ItemAnimation{

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.rightArm.zRot = 5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.zRot = -5;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        int i = flag ? 1 : -1;
        float swingAmount = Mth.sin(swingProgress * (float) Math.PI);

        poseStack.translate(i * 0.25F, -0.32F + 1 * -0.6F, -0.52F);
        poseStack.translate(0, 0.68f, 0);
        poseStack.translate(0, -0.125F + 0.7F - ((float) Math.PI / 5F), 0);
        poseStack.mulPose(Axis.XN.rotationDegrees(90f));
        poseStack.mulPose(Axis.YP.rotationDegrees(flag ? 90f : -90f));
        poseStack.mulPose(Axis.XN.rotationDegrees(56 * swingAmount));
        poseStack.mulPose(Axis.XP.rotationDegrees((-80 + player.getUseItemRemainingTicks() * 14 - partialTicks) * 2f));
    }
}