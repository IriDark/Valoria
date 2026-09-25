package com.idark.valoria.client.model.animations;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.model.*;
import net.minecraft.client.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.render.animation.*;
import pro.komaru.tridot.client.*;

public class HammerAnimation extends ItemAnimation {

    @Override
    public boolean isOnlyItemUse() {
        return false;
    }

    private boolean isSmashFalling(LivingEntity entity) {
        return entity != null && entity.hasEffect(EffectsRegistry.HAMMER_SMASH.get());
    }

    private boolean isCharging(LivingEntity entity) {
        return entity != null && entity.isUsingItem() && (entity.getUseItem().getItem() instanceof HammerItem);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof LivingEntity living)) return;

        if (isSmashFalling(living)) {
            model.rightArm.xRot = -2.85F;
            model.rightArm.yRot = -0.18F;
            model.rightArm.zRot = 0.12F;

            model.leftArm.xRot = -2.85F;
            model.leftArm.yRot = 0.18F;
            model.leftArm.zRot = -0.12F;
        } else if (isCharging(living)) {
            int useTicks = living.getTicksUsingItem();
            float progress = Math.min(1.0F, useTicks / 35.0F);
            float rightX = -1.1F - (0.35F * progress);
            float rightY = -0.45F;
            float rightZ = 0.1F;

            float leftX = -1.0F - (0.35F * progress);
            float leftY = 0.65F;
            float leftZ = -0.1F;
            if (useTicks >= 35) {
                float shake = (float) Math.sin(ageInTicks * 2.5F) * 0.025F;
                rightX += shake;
                leftX += shake;
            }

            model.rightArm.xRot = rightX;
            model.rightArm.yRot = rightY;
            model.rightArm.zRot = rightZ;

            model.leftArm.xRot = leftX;
            model.leftArm.yRot = leftY;
            model.leftArm.zRot = leftZ;

            model.body.yRot = -0.2F * progress;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof LivingEntity living)) return;

        if (isSmashFalling(living)) {
            model.leftArm.xRot = -2.85F;
            model.leftArm.yRot = 0.18F;
            model.leftArm.zRot = -0.12F;

            model.rightArm.xRot = -2.85F;
            model.rightArm.yRot = -0.18F;
            model.rightArm.zRot = 0.12F;
        } else if (isCharging(living)) {
            int useTicks = living.getTicksUsingItem();
            float progress = Math.min(1.0F, useTicks / 35.0F);
            float leftX = -1.1F - (0.35F * progress);
            float leftY = 0.45F;
            float leftZ = -0.1F;

            float rightX = -1.0F - (0.35F * progress);
            float rightY = -0.65F;
            float rightZ = 0.1F;
            if (useTicks >= 35) {
                float shake = (float) Math.sin(ageInTicks * 2.5F) * 0.025F;
                leftX += shake;
                rightX += shake;
            }

            model.leftArm.xRot = leftX;
            model.leftArm.yRot = leftY;
            model.leftArm.zRot = leftZ;

            model.rightArm.xRot = rightX;
            model.rightArm.yRot = rightY;
            model.rightArm.zRot = rightZ;

            model.body.yRot = 0.2F * progress;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void setupAnim(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof LivingEntity living)) return;

        if (isSmashFalling(living)) {
            model.head.xRot = Math.max(model.head.xRot, 0.35F);
            model.body.xRot = 0.15F;
        } else if (isCharging(living)) {
            int useTicks = living.getTicksUsingItem();
            float progress = Math.min(1.0F, useTicks / 35.0F);
            model.body.xRot = 0.08F * progress;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (isSmashFalling(livingEntity)) {
            poseStack.translate(0.0F, -0.15F, 0.0F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-20.0F));
        } else if (isCharging(livingEntity)) {
            boolean isRight = arm == HumanoidArm.RIGHT;
            poseStack.translate(0.0F, -0.05F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(isRight ? 15.0F : -15.0F));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        boolean isFalling = isSmashFalling(player);
        boolean isCharging = isCharging(player);
        if (!isFalling && !isCharging) {
            return;
        }

        boolean isMainHand = hand == InteractionHand.MAIN_HAND;
        int side = isMainHand ? 1 : -1;
        if (isFalling) {
            poseStack.translate(-side * 0.22F, 0.32F, 0.05F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(-side * 20.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(side * 15.0F));

            float sway = (float) Math.sin((ClientTick.ticksInGame + partialTicks) * 0.6F) * 2.0F;
            poseStack.mulPose(Axis.XP.rotationDegrees(sway));
            poseStack.translate(0.0F, (float) Math.sin((ClientTick.ticksInGame + partialTicks) * 0.6F) * 0.015F, 0.0F);
        } else {
            int useTicks = player.getTicksUsingItem();
            float progress = Math.min(1.0F, (useTicks + partialTicks) / 35.0F);

            poseStack.translate(-side * 0.15F * progress, -0.1F * progress, -0.35F * progress);
            poseStack.mulPose(Axis.XP.rotationDegrees(50.0F * progress));
            poseStack.mulPose(Axis.YP.rotationDegrees(-side * 25.0F * progress));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-side * 20.0F * progress));

            if (useTicks >= 35) {
                float shake = (float)Math.sin((ClientTick.ticksInGame + partialTicks) * 2.8F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(shake));
                poseStack.mulPose(Axis.XP.rotationDegrees(shake * 0.5F));
            }
        }
    }
}
