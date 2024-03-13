package com.idark.valoria.client.render.model.item;

import com.idark.valoria.client.event.ClientTickHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PickAnim extends ItemAnims {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.rightArm.xRot = -0.9F;
        model.rightArm.zRot = -0.9F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.leftArm.xRot = -0.9F;
        model.leftArm.zRot = 0.9F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        HumanoidArm mainArm = livingEntity.getMainArm();
        boolean flag = arm == mainArm;
        poseStack.translate(0, -0.15f, 0);
        poseStack.translate(0, -0.125F + 0.7F - ((float) Math.PI / 5F), 0);
        poseStack.mulPose(Axis.XP.rotationDegrees((float) (-45f * Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) / 1.2) - 65)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean flag = hand == InteractionHand.MAIN_HAND;
        int i = flag ? 1 : -1;
        poseStack.translate((float) i * 0.56F, -0.52F + 1 * -0.6F, -0.72F);

        poseStack.translate(0, 0.68f, 0);
        poseStack.translate(0, -0.125F + 0.7F - ((float) Math.PI / 5F), 0);

        poseStack.mulPose(Axis.YP.rotationDegrees(flag ? 20f : -20f));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) (-45f * Math.sin((ClientTickHandler.ticksInGame + partialTicks) / 1.2) - 65)));
    }
}