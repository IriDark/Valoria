package com.idark.valoria.client.render.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RadiusAttackAnim extends ItemAnims {

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
        poseStack.mulPose(Axis.XP.rotationDegrees((-60 + player.getUseItemRemainingTicks() * 16 - partialTicks)));
    }
}