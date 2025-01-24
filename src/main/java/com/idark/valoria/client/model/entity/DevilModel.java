package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.DevilAnimation;
import com.idark.valoria.registries.entity.living.Devil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class DevilModel<T extends Devil> extends HierarchicalModel<T> implements ArmedModel, HeadedModel{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public DevilModel(ModelPart root){
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

        PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.25F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(60, 27).addBox(-3.0F, 3.75F, -3.25F, 6.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.0F, -4.25F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(0, 43).addBox(-7.0F, -9.25F, 0.75F, 14.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.75F, -0.75F));

        PartDefinition Left_ear = partdefinition.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(16, 50).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, -4.5F, 0.0F));

        PartDefinition Right_ear = partdefinition.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(22, 50).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, -4.5F, 0.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 11).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 27).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition Left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 59).addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 59).addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 1.0F, 0.0F));

        PartDefinition Right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 59).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 59).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, 1.0F, 0.0F));

        PartDefinition Left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(28, 43).addBox(-2.0F, 0.0132F, 0.0521F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 43).addBox(-2.0F, -0.3618F, 0.0521F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, -2.0F));

        PartDefinition Right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(56, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition Left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 32).addBox(-9.0F, -5.5F, 0.0F, 18.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9658F, 4.5F, 2.7844F, 0.0F, -0.0873F, 0.0F));

        PartDefinition Right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(32, 0).addBox(-9.0275F, -5.5F, 0.0F, 18.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8786F, 4.5F, 2.7806F, 0.0F, 0.0873F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(pLimbSwing, pLimbSwingAmount);
        this.animateWalk(DevilAnimation.WALK, pLimbSwing, pLimbSwingAmount, pEntity.getSpeed(), pAgeInTicks);
        this.animateIdlePose(pAgeInTicks);
        this.animate(pEntity.idleAnimationState, DevilAnimation.IDLE, pAgeInTicks);
        this.animate(pEntity.throwAnimationState, DevilAnimation.ATTACK_RANGE, pAgeInTicks);
        this.animate(pEntity.magicAnimationState, DevilAnimation.ATTACK_MAGIC, pAgeInTicks);
        if(this.attackTime > 0){
            AnimationUtils.swingWeaponDown(rightArm, leftArm, pEntity, this.attackTime * 0.75f, pAgeInTicks);
        }
    }

    private void animateWalk(float pLimbSwing, float pLimbSwingAmount){
        float f = Math.min(0.5F, 3.0F * pLimbSwingAmount);
        float f1 = pLimbSwing * 0.8662F;
        this.leftWing.yRot = -0.45f;
        this.rightWing.yRot = 0.45f;
        this.leftWing.yRot -= (0.65F * Mth.cos(f1 + (float)Math.PI) * f);
        this.rightWing.yRot += (0.65F * Mth.cos(f1 + (float)Math.PI) * f);
        this.resetArmPoses();
    }

    private void resetArmPoses(){
        this.leftArm.yRot = 0.0F;
        this.leftArm.z = 0;
        this.leftArm.x = 4;
        this.leftArm.y = 0;

        this.rightArm.yRot = 0.0F;
        this.rightArm.z = 0;
        this.rightArm.x = -4;
        this.rightArm.y = 0;
    }

    private void animateIdlePose(float pAgeInTicks){
        float f = pAgeInTicks * 0.1F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f);
        this.head.zRot += 0.03F * f1;
        this.head.xRot += 0.03F * f2;
        this.body.zRot += 0.0025F * f2;
        this.body.xRot += 0.0025F * f1;
    }

    @Override
    public ModelPart root(){
        return this.root;
    }

    public ModelPart getHead(){
        return this.head;
    }

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    @Override
    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack){
        this.getArm(pSide).translateAndRotate(pPoseStack);
    }
}
