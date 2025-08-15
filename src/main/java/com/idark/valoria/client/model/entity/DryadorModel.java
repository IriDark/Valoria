package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.boss.dryador.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class DryadorModel<T extends DryadorEntity> extends AbstractHierarchicalModel<T>{
    private final ModelPart root;
    protected final ModelPart body;
    protected final ModelPart rightArm;
    protected final ModelPart rightLeg;
    protected final ModelPart leftLeg;
    protected final ModelPart leftArm;
    private final ModelPart head;
    public DryadorModel(ModelPart root) {
        this.root = root;
        this.body = this.root.getChild("body");
        this.head = this.root.getChild("head");
        this.rightLeg = this.root.getChild("right_leg");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightArm = this.root.getChild("right_arm");
        this.leftArm = this.root.getChild("left_arm");
    }

    @Override
    public ModelPart root(){
        return root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 31).addBox(-6.0F, 1.3333F, -4.6667F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
        .texOffs(0, 37).addBox(-7.0F, -2.6667F, -6.6667F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(0, 55).addBox(-7.0F, -6.6667F, -6.6667F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -33.3333F, -0.3333F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -12.5F, -6.0F, 16.0F, 25.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.5F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 71).addBox(-3.75F, -11.75F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(56, 49).addBox(-3.75F, -0.75F, -4.0F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(11.75F, -12.25F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 71).mirror().addBox(-4.25F, -11.75F, -4.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(56, 49).mirror().addBox(-3.25F, -0.75F, -4.0F, 7.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.75F, -12.25F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-4.0F, -11.5F, -4.0F, 8.0F, 23.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 12.5F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(56, 0).addBox(-4.0F, -11.5F, -4.0F, 8.0F, 23.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 12.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if(!pEntity.isBusy()){
            this.animateWalk(DryadorAnimations.WALK, pLimbSwing, pLimbSwingAmount, 6, 8);
            this.animateIdle(pEntity.idleAnimationState, DryadorAnimations.IDLE, pLimbSwingAmount, pAgeInTicks, 1);
        }

        this.animate(pEntity.phaseTransitionAnimationState, DryadorAnimations.PHASE_TRANSITION, pAgeInTicks);
        if(!pEntity.phaseTransitionAnimationState.isPlaying){
            this.animate(pEntity.summonAnimationState, DryadorAnimations.SUMMON, pAgeInTicks);
            this.animate(pEntity.rangedAttackAnimationState, DryadorAnimations.ATTACK_RANGED, pAgeInTicks);
            this.animate(pEntity.stompAnimationState, DryadorAnimations.STOMP, pAgeInTicks);
            this.animate(pEntity.meleeAttackAnimationState, DryadorAnimations.ATTACK_MELEE, pAgeInTicks);
        }
    }

    @Override
    public ModelPart getHead(){
        return head;
    }
}