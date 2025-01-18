package com.idark.valoria.client.model.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.*;

public class SorcererModel<T extends LivingEntity> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public SorcererModel(ModelPart root) {
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
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(0, 28).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 2.375F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
        .texOffs(0, 44).addBox(-3.0F, 0.375F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(0, 52).addBox(-2.0F, -2.625F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(40, 8).addBox(-1.0F, -4.625F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.625F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 51).addBox(-0.8667F, -4.9667F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(46, 54).addBox(-0.8667F, -4.9667F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.4F))
        .texOffs(48, 44).addBox(-1.7667F, -5.5667F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.2333F, 4.0667F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 12).addBox(-4.0F, -9.875F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(32, 28).addBox(-4.0F, -9.875F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F))
        .texOffs(40, 0).addBox(-4.0F, 2.125F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.4F))
        .texOffs(24, 44).addBox(-4.0F, 2.125F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.875F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(56, 8).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 59).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(2.0F, 19.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(56, 20).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(56, 32).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(-2.0F, 19.0F, 0.0F));


        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 52).addBox(-1.1333F, -4.9667F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(54, 54).addBox(-1.1333F, -4.9667F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.4F))
        .texOffs(24, 51).addBox(-1.2333F, -5.5667F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.2333F, 4.0667F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    protected ModelPart getArm(HumanoidArm pSide) {
        return pSide == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    @Override
    public ModelPart root(){
        return root;
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){

    }
}