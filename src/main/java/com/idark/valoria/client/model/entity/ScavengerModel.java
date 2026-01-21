package com.idark.valoria.client.model.entity;


import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.elemental.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class ScavengerModel<T extends Scavenger> extends AbstractHierarchicalModel<T>{
	private final ModelPart root;
    private final ModelPart head;

	public ScavengerModel(ModelPart root) {
		this.root = root;
        this.head = root.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -0.125F, -13.0F, 12.0F, 13.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(70, 15).addBox(-3.0F, -6.125F, -7.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(70, 27).addBox(-2.0F, -4.125F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 78).addBox(0.0F, -2.125F, 3.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.875F, 3.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 64).addBox(-2.0F, -0.5F, -4.5F, 4.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(46, 73).addBox(0.0F, -3.5F, -3.5F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 17.5F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 36).addBox(-2.5F, 0.0F, -8.2F, 5.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-2.5F, -5.0F, -12.2F, 5.0F, 5.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(56, 56).addBox(0.0F, -9.0F, -1.2F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 78).addBox(-2.5F, 0.0F, -12.2F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -20.8F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 64).addBox(-2.5F, -11.9286F, 0.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(70, 80).addBox(0.0F, -5.9286F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 64).addBox(0.5F, -0.9286F, -2.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(70, 73).addBox(-2.5F, -0.9286F, -2.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(22, 78).addBox(-1.5F, 1.0714F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 78).addBox(1.5F, 1.0714F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(72, 80).addBox(0.0F, 1.0714F, 5.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 20.9286F, -0.5F));

		PartDefinition bone2 = left_leg.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(60, 73).addBox(-3.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9341F, -9.4665F, 5.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 64).mirror().addBox(-2.5F, -11.9286F, 0.5F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(70, 80).mirror().addBox(0.0F, -5.9286F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(46, 64).mirror().addBox(-2.5F, -0.9286F, -2.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(70, 73).mirror().addBox(0.5F, -0.9286F, -2.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 78).mirror().addBox(1.5F, 1.0714F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(18, 78).mirror().addBox(-1.5F, 1.0714F, -4.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(72, 80).mirror().addBox(0.0F, 1.0714F, 5.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 20.9286F, -0.5F));

		PartDefinition bone = right_leg.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(60, 73).mirror().addBox(3.0F, -2.5F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.9341F, -9.4665F, 5.0F));

		PartDefinition Neck = partdefinition.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(70, 0).addBox(-2.0F, -3.5F, -4.0F, 4.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0208F, -0.5323F, -13.7877F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Scavenger pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ScavengerAnimations.WALK, pLimbSwing, pLimbSwingAmount, 2, 3);
        this.animateIdle(pEntity.idleAnimationState, ScavengerAnimations.IDLE_DEFAULT, pLimbSwingAmount, pAgeInTicks, 1);
        this.animateIdle(pEntity.idleDiggingAnimationState, ScavengerAnimations.IDLE_DIG, pLimbSwingAmount, pAgeInTicks, 1);
        this.animateIdle(pEntity.idleEatingAnimationState, ScavengerAnimations.IDLE_EATING, pLimbSwingAmount, pAgeInTicks, 1);
        this.animate(pEntity.angryAnimationState, ScavengerAnimations.AGRESSIVE, pAgeInTicks, 1);

        this.animate(pEntity.attackAnimationState, ScavengerAnimations.ATTACK, pAgeInTicks, 1);
    }

    @Override
    public ModelPart root(){
        return root;
    }

    @Override
    public ModelPart getHead(){
        return head;
    }
}