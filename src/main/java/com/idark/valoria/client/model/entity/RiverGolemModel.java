package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class RiverGolemModel<T extends RiverGolem> extends AbstractHierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public RiverGolemModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
	}

    @Override
    public ModelPart getHead(){
        return head;
    }

    @Override
    public ModelPart root(){
        return root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 19).addBox(-6.8F, 5.0F, -6.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
        .texOffs(60, 34).addBox(-5.8F, 3.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
        .texOffs(60, 0).addBox(-6.8F, -4.0F, -6.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F))
        .texOffs(26, 64).addBox(-2.8F, -7.0F, -6.0F, 4.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
        .texOffs(58, 83).addBox(1.2F, -5.0F, -6.0F, 4.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.8F, -22.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.0F, -7.0F, 16.0F, 18.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(0, 32).addBox(-8.0F, -9.0F, -7.0F, 16.0F, 18.0F, 14.0F, new CubeDeformation(0.45F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(58, 69).addBox(-3.75F, -8.5F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(60, 47).addBox(-3.75F, -2.5F, -3.0F, 7.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(11.75F, -5.5F, 0.0F));

        PartDefinition left_arm_decor_r1 = left_arm.addOrReplaceChild("left_arm_decor_r1", CubeListBuilder.create().texOffs(38, 93).mirror().addBox(-3.0F, -1.5F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.25F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition left_arm_decor_r2 = left_arm.addOrReplaceChild("left_arm_decor_r2", CubeListBuilder.create().texOffs(38, 97).mirror().addBox(-3.0F, -1.5F, 0.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.25F, -11.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(26, 79).addBox(-4.25F, -8.5F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(0, 64).addBox(-3.25F, -2.5F, -3.0F, 7.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.75F, -5.5F, 0.0F));

        PartDefinition right_arm_decor_r1 = right_arm.addOrReplaceChild("right_arm_decor_r1", CubeListBuilder.create().texOffs(24, 98).addBox(-4.0F, -2.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition right_arm_decor_r2 = right_arm.addOrReplaceChild("right_arm_decor_r2", CubeListBuilder.create().texOffs(24, 93).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -11.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 86).addBox(-3.25F, -11.5F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(90, 66).addBox(-3.25F, 1.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.25F, 15.5F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(90, 80).addBox(-3.75F, 1.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
        .texOffs(86, 47).addBox(-2.75F, -11.5F, -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.25F, 15.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
	public void setupAnim(T e, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(e, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.animateIdle(e.idleAnimationState, GolemAnimation.IDLE, limbSwingAmount, ageInTicks, 1);
        this.animateWalk(GolemAnimation.WALK, limbSwing, limbSwingAmount, 6, 6);

        this.animate(e.attackAnimationState, GolemAnimation.ATTACK1, ageInTicks);
        this.animate(e.stompAttackAnimationState, GolemAnimation.ATTACK2, ageInTicks);
        this.animate(e.attackSlapAnimationState, GolemAnimation.ATTACK3, ageInTicks);
        this.animate(e.groundPunchAnimationState, GolemAnimation.ATTACK4, ageInTicks);
    }
}