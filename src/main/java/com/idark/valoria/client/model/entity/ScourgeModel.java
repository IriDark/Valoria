package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;

public class ScourgeModel<T extends ScourgeEntity> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public ScourgeModel(ModelPart root){
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.6667F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 8).addBox(-5.0F, -0.6667F, -4.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 0).addBox(-4.0F, -4.6667F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -3.3333F, -1.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(30, 18).addBox(-5.0F, -6.8333F, -2.0F, 10.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 34).addBox(-5.0F, -1.8333F, -3.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(58, 18).addBox(-4.0F, -6.8333F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.8333F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 24).addBox(-1.3333F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-2.3333F, -4.6667F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(14, 24).addBox(-1.3333F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-6.6667F, 4.6667F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.6667F, -4.6667F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(0, 24).mirror().addBox(-1.6667F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 40).addBox(-1.6667F, -4.6667F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(6.6667F, 4.6667F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 40).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 72).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 18.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 56).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 56).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public ModelPart getHead(){
        return this.head;
    }

    @Override
    public ModelPart root(){
        return this.root;
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(ScourgeAnimations.WALK, pLimbSwing, pLimbSwingAmount, pEntity.getSpeed(), pAgeInTicks);
        this.animateIdlePose(pAgeInTicks);
        this.animate(pEntity.idleAnimationState, ScourgeAnimations.IDLE, pAgeInTicks);
        this.animate(pEntity.attackAnimationState, ScourgeAnimations.ATTACK_MELEE, pAgeInTicks);
        this.animate(pEntity.diggingAnimationState, ScourgeAnimations.SPAWN, pAgeInTicks);
        this.animate(pEntity.deathAnimationState, ScourgeAnimations.DEATH, pAgeInTicks);

        boolean flag = pEntity.getFallFlyingTicks() > 4;
        float f = 1.0F;
        if (flag) {
            f = (float)pEntity.getDeltaMovement().lengthSqr();
            f /= 0.2F;
            f *= f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        this.rightLeg.xRot = Mth.cos(pLimbSwing * 0.3262F) * 1F * pLimbSwingAmount / f;
        this.leftLeg.xRot = Mth.cos(pLimbSwing * 0.3262F + (float)Math.PI) * 1F * pLimbSwingAmount / f;
        this.rightLeg.yRot = 0.00015F;
        this.leftLeg.yRot = -0.00015F;
        this.rightLeg.zRot = 0.00015F;
        this.leftLeg.zRot = -0.00015F;
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
}
