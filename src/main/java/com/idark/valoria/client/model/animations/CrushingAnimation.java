package com.idark.valoria.client.model.animations;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.client.*;

public class CrushingAnimation extends ItemAnimation{

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        model.rightArm.xRot = -0.9F;
        model.rightArm.zRot = -0.9F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        model.leftArm.xRot = -0.9F;
        model.leftArm.zRot = 0.9F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight){
        poseStack.translate(0, -0.15f, 0);
        poseStack.translate(0, -0.125F + 0.7F - ((float)Math.PI / 5F), 0);
        poseStack.mulPose(Axis.XP.rotationDegrees((float)(-45f * Math.sin((ClientTick.ticksInGame + Minecraft.getInstance().getPartialTick()) / 1.2) - 65)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight){
        boolean flag = hand == InteractionHand.MAIN_HAND;
        int i = flag ? 1 : -1;
        poseStack.translate((float)i * 0.56F, -0.52F + 1 * -0.6F, -0.72F);

        poseStack.translate(0, 0.68f, 0);
        poseStack.translate(0, -0.125F + 0.7F - ((float)Math.PI / 5F), 0);

        poseStack.mulPose(Axis.YP.rotationDegrees(flag ? 20f : -20f));
        poseStack.mulPose(Axis.XP.rotationDegrees((float)(-45f * Math.sin((ClientTick.ticksInGame + partialTicks) / 1.2) - 65)));
    }
}