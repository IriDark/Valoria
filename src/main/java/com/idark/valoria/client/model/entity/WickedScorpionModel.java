package com.idark.valoria.client.model.entity;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class WickedScorpionModel<T extends WickedScorpion> extends AbstractHierarchicalModel<T> {
    private final ModelPart root;

	public WickedScorpionModel(ModelPart root) {
        this.root = root;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(36, 24).addBox(-5.625F, -4.0F, -1.125F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-6.125F, -7.0F, -3.125F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(1.625F, 17.0F, -14.875F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 15).mirror().addBox(-1.75F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 52).mirror().addBox(-1.75F, -1.75F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.875F, 2.75F, -2.125F, 0.0F, 0.1309F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 52).addBox(-0.25F, -1.75F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(52, 15).addBox(-3.25F, 0.25F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.625F, 2.75F, -2.125F, 0.0F, -0.1309F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 2.9703F, 9.7432F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 39).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 9.5297F, 1.2568F, 0.8727F, 0.0F, 0.0F));

		PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 52).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.0297F, 4.2568F, 1.3526F, 0.0F, 0.0F));

		PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(50, 39).addBox(-1.5F, -1.5F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.9534F, 1.7244F, 2.3562F, 0.0F, 0.0F));

		PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 54).mirror().addBox(0.0F, -0.5F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -6.0747F, -1.3969F, 2.3562F, 0.5236F, -0.3491F));

		PartDefinition cube_r7 = bone.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 54).addBox(0.0F, -0.5F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -6.0747F, -1.3969F, 2.3562F, -0.5236F, 0.3491F));

		PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 37).addBox(0.0F, -2.5F, -6.0F, 0.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.4697F, -5.5456F, -2.618F, 0.0F, 0.0F));

		PartDefinition left_leg_1 = partdefinition.addOrReplaceChild("left_leg_1", CubeListBuilder.create(), PartPose.offset(10.5F, 17.1F, -7.0F));

		PartDefinition cube_r9 = left_leg_1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(52, 0).addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.3054F, -0.1745F));

		PartDefinition right_leg_1 = partdefinition.addOrReplaceChild("right_leg_1", CubeListBuilder.create(), PartPose.offset(-10.5F, 17.1F, -7.0F));

		PartDefinition cube_r10 = right_leg_1.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.3054F, 0.1745F));

		PartDefinition left_leg_2 = partdefinition.addOrReplaceChild("left_leg_2", CubeListBuilder.create(), PartPose.offset(10.5F, 17.1F, 0.0F));

		PartDefinition cube_r11 = left_leg_2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(52, 0).addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition right_leg_2 = partdefinition.addOrReplaceChild("right_leg_2", CubeListBuilder.create(), PartPose.offset(-10.5F, 17.1F, 0.0F));

		PartDefinition cube_r12 = right_leg_2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition left_leg_3 = partdefinition.addOrReplaceChild("left_leg_3", CubeListBuilder.create(), PartPose.offset(10.5F, 17.1F, 7.0F));

		PartDefinition cube_r13 = left_leg_3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(52, 0).addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, -0.3054F, -0.1745F));

		PartDefinition right_leg_3 = partdefinition.addOrReplaceChild("right_leg_3", CubeListBuilder.create(), PartPose.offset(-10.5F, 17.1F, 7.0F));

		PartDefinition cube_r14 = right_leg_3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-5.5F, -7.5F, 0.0F, 11.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.3054F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
    public void setupAnim(WickedScorpion pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(WickedScorpionAnimations.WALK, pLimbSwing, pLimbSwingAmount, 4, 2);
        this.animateIdle(pEntity.idleAnimationState, WickedScorpionAnimations.IDLE, pLimbSwingAmount, pAgeInTicks, 1);
        //this.animate(pEntity.deathAnimationState, -, pAgeInTicks, 1); //todo

        this.animate(pEntity.tailAttackAnimationState, WickedScorpionAnimations.ATTACK1, pAgeInTicks, 1);
        this.animate(pEntity.attackAnimationState, WickedScorpionAnimations.ATTACK2, pAgeInTicks, 1);
        this.animate(pEntity.spitAttackAnimationState, WickedScorpionAnimations.ATTACK3, pAgeInTicks, 1);
    }

    @Override
    public ModelPart root(){
        return this.root;
    }

    @Override
    public ModelPart getHead(){
        return null;
    }
}