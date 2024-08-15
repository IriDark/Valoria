package com.idark.valoria.client.render.model.entity;

import com.idark.valoria.client.render.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class SuccubusModel<T extends Succubus> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public SuccubusModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftWing = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
        .texOffs(54, 11).addBox(-6.0F, -12.0F, 0.0F, 12.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftEar = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.0F, 0.0F));

        PartDefinition rightEar = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -4.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(34, 13).addBox(-8.0F, -12.0F, 1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(0, 60).addBox(-8.0F, -12.0F, 1.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 12.0F, -3.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(58, 53).addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(44, 57).addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 1.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 25).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(48, 41).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, 1.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 45).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(16, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(36, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition leftWing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -4.0F, -1.0F, 18.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition rightWing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 32).addBox(-18.0F, -4.0F, -1.0F, 18.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.0F, 0.0873F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateHeadLookTarget(pNetHeadYaw, pHeadPitch);
        this.animateWalk(pLimbSwing, pLimbSwingAmount);
        this.animateIdlePose(pAgeInTicks);
        this.animate(pEntity.idleAnimationState, SuccubusAnimation.IDLE, pAgeInTicks);
        this.animate(pEntity.fireballAnimationState, SuccubusAnimation.ATTACK_RANGE, pAgeInTicks);
    }

    private void animateHeadLookTarget(float pYaw, float pPitch) {
        this.head.xRot = pPitch * ((float)Math.PI / 180F);
        this.head.yRot = pYaw * ((float)Math.PI / 180F);
    }

    private void animateWalk(float pLimbSwing, float pLimbSwingAmount) {
        float f = Math.min(0.5F, 3.0F * pLimbSwingAmount);
        float f1 = pLimbSwing * 0.8662F;
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        this.leftLeg.xRot = 1.0F * f2 * f;
        this.rightLeg.xRot = 1.0F * Mth.cos(f1 + (float)Math.PI) * f;
        this.leftArm.xRot = -(0.6F * f2 * f);
        this.leftArm.zRot = 0.0F;
        this.rightArm.xRot = -(0.6F * f3 * f);
        this.rightArm.zRot = 0.0F;

        this.leftWing.yRot = -0.45f;
        this.rightWing.yRot = 0.45f;
        this.leftWing.yRot -= (0.65F * Mth.cos(f1 + (float)Math.PI) * f);
        this.rightWing.yRot += (0.65F * Mth.cos(f1 + (float)Math.PI) * f);
        this.resetArmPoses();
    }

    private void resetArmPoses() {
        this.leftArm.yRot = 0.0F;
        this.leftArm.z = 0;
        this.leftArm.x = 4;
        this.leftArm.y = 0;

        this.rightArm.yRot = 0.0F;
        this.rightArm.z = 0;
        this.rightArm.x = -4;
        this.rightArm.y = 0;
    }

    private void animateIdlePose(float pAgeInTicks) {
        float f = pAgeInTicks * 0.1F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f);
        this.head.zRot += 0.03F * f1;
        this.head.xRot += 0.03F * f2;
        this.body.zRot += 0.0025F * f2;
        this.body.xRot += 0.0025F * f1;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root(){
        return this.root;
    }
}
