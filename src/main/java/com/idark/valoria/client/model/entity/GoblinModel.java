package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;

public class GoblinModel<T extends Goblin> extends AbstractHierarchicalModel<T> implements ArmedModel, HeadedModel{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    public GoblinModel(ModelPart pRoot){
        this.root = pRoot;
        this.head = pRoot.getChild("head");
        this.body = pRoot.getChild("body");
        this.left_leg = pRoot.getChild("left_leg");
        this.right_leg = pRoot.getChild("right_leg");
        this.left_arm = pRoot.getChild("left_arm");
        this.right_arm = pRoot.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0575F, -2.5695F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-4.5F, -4.0575F, -2.5695F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.45F))
                .texOffs(0, 37).addBox(-1.0F, 0.7302F, -3.1695F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0576F, -0.4213F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 1).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(6.4582F, -0.0576F, 0.9043F, 0.0F, -0.3054F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 1).mirror().addBox(-2.5F, -2.0F, 0.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-6.4582F, -0.0576F, 0.9043F, 0.0F, 0.3054F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.4999F, -4.0501F, -2.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0153F, 14.0501F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(22, 13).addBox(-1.5F, -4.632F, -1.5035F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 14.6308F, -0.0918F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, -4.5224F, -1.4999F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 14.5211F, 0.0663F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(12, 25).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 34).mirror().addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offset(2.0F, 21.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(24, 25).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 34).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.45F)), PartPose.offset(-2.0F, 21.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public static LayerDefinition createOldBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -2.9F, -2.6528F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.9F, -0.3472F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(-5.2746F, -0.15F, -0.0868F));

        PartDefinition left_ear_cube_r1 = left_ear.addOrReplaceChild("left_ear_cube_r1", CubeListBuilder.create().texOffs(0, 2).addBox(-4.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 3).addBox(-6.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.2746F, 18.25F, 0.434F, 0.0F, 0.3054F, 0.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(5.2746F, -0.15F, -0.0868F));

        PartDefinition right_ear_cube_r1 = right_ear.addOrReplaceChild("right_ear_cube_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(3.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 0).mirror().addBox(3.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.2746F, 18.25F, 0.434F, 0.0F, -0.3054F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, -4.0F, -2.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(22, 13).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 14.5F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, -4.5F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 14.5F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(12, 25).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 21.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(24, 25).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 21.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.head.getChild("right_ear").yRot = Mth.sin(ageInTicks * 0.06F) * 0.06F;
        this.head.getChild("left_ear").yRot = Mth.sin(ageInTicks * -0.06F) * 0.06F;
        this.head.getChild("right_ear").xRot = Mth.sin(ageInTicks * 0.01F) * 0.01F;
        this.head.getChild("left_ear").xRot = Mth.sin(ageInTicks * -0.01F) * 0.01F;
        if(entity.isAggressive()){
            if(!entity.getMainHandItem().isEmpty()){
                this.animate(entity.attackAnimationState, GoblinAnimations.ATTACK_WEAPON, ageInTicks, 2f);
            }else{
                this.animate(entity.attackAnimationState, GoblinAnimations.ATTACK, ageInTicks, 1f);
            }

            AnimationUtils.bobArms(this.right_arm, this.left_arm, ageInTicks);
        }


        if(entity.isLowHP() || entity.isSprinting()){
            this.animateWalk(GoblinAnimations.RUN, limbSwing, limbSwingAmount, 4f, 2);
        }else{
            this.animateWalk(GoblinAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2);
        }

        this.animate(entity.idleAnimationState, GoblinAnimations.IDLE, ageInTicks, 0.5f);
    }

    protected ModelPart getArm(HumanoidArm pSide){
        return pSide == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack){
        ModelPart arm = this.getArm(pSide);
        arm.translateAndRotate(pPoseStack);

        float handOffset = pSide == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        pPoseStack.translate(handOffset / 16.0F, 0.0F, 0.0F);
        pPoseStack.translate(-0.1F, -0.32F, 0.065F);
    }

    public ModelPart getHead(){
        return this.head;
    }

    @Override
    public ModelPart root(){
        return this.root;
    }
}