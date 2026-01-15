package com.idark.valoria.client.model.entity;// Made with Blockbench 4.12.5

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;

public class KingCrabModel<T extends KingCrabEntity> extends AbstractHierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart arm_left_base;
	private final ModelPart arm_left;
	private final ModelPart claw_left;
	private final ModelPart arm_right_base;
	private final ModelPart arm_right;
	private final ModelPart claw_right;
	private final ModelPart leg_left_1;
	private final ModelPart leg_right_1;
	private final ModelPart leg_left_3;
	private final ModelPart leg_right_3;
	private final ModelPart leg_left_2;
	private final ModelPart leg_right_2;

	public KingCrabModel(ModelPart root) {
        this.root = root;

		this.body = root.getChild("body");
        this.eye_left = this.body.getChild("eye_left");
        this.eye_right = this.body.getChild("eye_right");

        this.arm_left_base = root.getChild("arm_left_base");
		this.arm_left = this.arm_left_base.getChild("arm_left");
		this.claw_left = this.arm_left.getChild("claw_left");

        this.arm_right_base = root.getChild("arm_right_base");
		this.arm_right = this.arm_right_base.getChild("arm_right");
		this.claw_right = this.arm_right.getChild("claw_right");

        this.leg_left_1 = root.getChild("leg_left_1");
		this.leg_right_1 = root.getChild("leg_right_1");
		this.leg_left_3 = root.getChild("leg_left_3");
		this.leg_right_3 = root.getChild("leg_right_3");
		this.leg_left_2 = root.getChild("leg_left_2");
		this.leg_right_2 = root.getChild("leg_right_2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -1.5F, -7.6F, 32.0F, 13.0F, 16.0F, new CubeDeformation(0.0F))
        .texOffs(0, 29).addBox(8.0F, -3.5F, -7.6F, 10.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
        .texOffs(0, 29).mirror().addBox(-14.0F, -3.5F, -7.6F, 10.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 5.5F, -0.4F));

        PartDefinition eye_left = body.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(0, 65).addBox(-1.5F, -5.5F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, -8.1F));

        PartDefinition eye_right = body.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-1.5F, -5.5F, -1.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, -8.1F));


        PartDefinition arm_left_base = partdefinition.addOrReplaceChild("arm_left_base", CubeListBuilder.create(), PartPose.offsetAndRotation(13.8333F, 10.1667F, -16.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition arm_left = arm_left_base.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 47).addBox(-9.8333F, -5.1667F, -4.0F, 10.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition claw_left = arm_left.addOrReplaceChild("claw_left", CubeListBuilder.create().texOffs(64, 53).addBox(0.1667F, 0.8333F, -4.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(36, 47).addBox(0.1667F, -5.1667F, -4.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_right_base = partdefinition.addOrReplaceChild("arm_right_base", CubeListBuilder.create(), PartPose.offsetAndRotation(-13.8333F, 10.1667F, -16.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition arm_right = arm_right_base.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 47).mirror().addBox(-0.1667F, -5.1667F, -4.0F, 10.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition claw_right = arm_right.addOrReplaceChild("claw_right", CubeListBuilder.create().texOffs(64, 53).mirror().addBox(-8.0F, 1.0F, -4.0F, 3.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(36, 47).mirror().addBox(-11.0F, -5.0F, -4.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.8333F, -0.1667F, 0.0F));

		PartDefinition leg_left_1 = partdefinition.addOrReplaceChild("leg_left_1", CubeListBuilder.create(), PartPose.offsetAndRotation(21.201F, 18.25F, -6.5F, 0.0F, 0.3491F, 0.0F));

		PartDefinition cube_r1 = leg_left_1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 82).addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition leg_right_1 = partdefinition.addOrReplaceChild("leg_right_1", CubeListBuilder.create(), PartPose.offsetAndRotation(-21.201F, 18.25F, -6.5F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r2 = leg_right_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition leg_left_3 = partdefinition.addOrReplaceChild("leg_left_3", CubeListBuilder.create(), PartPose.offsetAndRotation(21.201F, 18.25F, 6.5F, 0.0F, -0.3491F, 0.0F));

		PartDefinition cube_r3 = leg_left_3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 82).addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition leg_right_3 = partdefinition.addOrReplaceChild("leg_right_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-21.201F, 18.25F, 6.5F, 0.0F, 0.3491F, 0.0F));

		PartDefinition cube_r4 = leg_right_3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition leg_left_2 = partdefinition.addOrReplaceChild("leg_left_2", CubeListBuilder.create(), PartPose.offset(22.201F, 18.25F, 0.0F));

		PartDefinition cube_r5 = leg_left_2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 82).addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition leg_right_2 = partdefinition.addOrReplaceChild("leg_right_2", CubeListBuilder.create(), PartPose.offset(-22.201F, 18.25F, 0.0F));

		PartDefinition cube_r6 = leg_right_2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 82).mirror().addBox(-8.0F, -2.0F, -1.5F, 16.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
    public ModelPart root(){
        return this.root;
    }

    @Override
    public ModelPart getHead(){
        return null;
    }

    /**
     * Sets this entity's model rotation angles
     */
    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(KingCrabAnimations.WALK, pLimbSwing, pLimbSwingAmount, 4, 2);
        this.animateIdle(pEntity.idleAnimationState, KingCrabAnimations.IDLE, pLimbSwingAmount, pAgeInTicks, 1);
        this.animate(pEntity.hideAnimationState, KingCrabAnimations.HIDE, pAgeInTicks, 1);
        this.animate(pEntity.revealAnimationState, KingCrabAnimations.REVEAL, pAgeInTicks, 1);
        this.animate(pEntity.deathAnimationState, KingCrabAnimations.DEATH, pAgeInTicks, 1);

        this.animate(pEntity.attackAnimationState, KingCrabAnimations.ATTACK1, pAgeInTicks, 2);
        this.animate(pEntity.splashAttackAnimationState, KingCrabAnimations.ATTACK2, pAgeInTicks, 1);
        this.animate(pEntity.hookAttackAnimationState, KingCrabAnimations.ATTACK3, pAgeInTicks, 1);

        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);

        eye_left.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        eye_right.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
    }
}