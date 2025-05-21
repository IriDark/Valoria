package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class CorruptedModel<T extends Corrupted> extends AbstractHierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;

	public CorruptedModel(ModelPart root) {
        this.root = root;
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 32).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 74).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 18.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 26).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 90).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 18.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(41, 43).addBox(-4.0F, -10.0F, -8.0F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(1, 49).addBox(4.25F, -10.0F, -8.0F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.75F, 0.25F));

		PartDefinition flesh_r1 = body.addOrReplaceChild("flesh_r1", CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, -5.0F, -4.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -7.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.6667F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F))
		.texOffs(32, 0).addBox(-5.0F, 0.3333F, -4.0F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-4.0F, -4.6667F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -0.3333F, -5.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 8).addBox(-1.6667F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 48).addBox(-1.6667F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.6667F, 7.6667F, -4.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(14, 48).addBox(-1.3333F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(62, 8).addBox(-1.3333F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.6667F, 7.6667F, -4.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.animate(pEntity.attackAnimationState, CorruptedAnimation.ATTACK_MELEE, pAgeInTicks);
        if(!pEntity.attackAnimationState.isPlaying){
            if(pEntity.isRunning){
                this.animateWalk(CorruptedAnimation.RUN, pLimbSwing, pLimbSwingAmount, 2, pAgeInTicks);
            }else{
                this.animateWalk(CorruptedAnimation.WALK, pLimbSwing, pLimbSwingAmount, 4, pAgeInTicks);
            }
        }

        this.animate(pEntity.idleAnimationState, CorruptedAnimation.IDLE, pAgeInTicks);
    }

    @Override
    public ModelPart root(){
        return this.root;
    }

    public ModelPart getHead(){
        return this.head;
    }
}