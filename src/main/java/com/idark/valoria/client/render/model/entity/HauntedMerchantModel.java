package com.idark.valoria.client.render.model.entity;

import com.idark.valoria.client.render.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class HauntedMerchantModel<T extends HauntedMerchant> extends HierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart eyes;
    private final ModelPart eyes2;
    private final ModelPart eyes3;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart candle;
    private final ModelPart candle2;
    private final ModelPart candle3;
    private final ModelPart candle4;
    public HauntedMerchantModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.eyes = body.getChild("eyes");
        this.eyes2 = body.getChild("eyes2");
        this.eyes3 = body.getChild("eyes3");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.candle = root.getChild("candle");
        this.candle2 = root.getChild("candle2");
        this.candle3 = root.getChild("candle3");
        this.candle4 = root.getChild("candle4");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 48).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, -2.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, -20.0F, -5.0F, 12.0F, 20.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(30, 38).addBox(-8.0F, 0.0F, -5.0F, 16.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-10.0F, 4.0F, -5.0F, 20.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -1.0F));

        PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -9.5F, -4.1F));

        PartDefinition eyes2 = body.addOrReplaceChild("eyes2", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.5F, -4.1F));

        PartDefinition eyes3 = body.addOrReplaceChild("eyes3", CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 7.5F, -8.1F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 20).addBox(-1.5F, -7.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(40, 52).addBox(-1.5F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, -1.0F, -2.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 20).mirror().addBox(-2.5F, -7.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(40, 52).mirror().addBox(-0.5F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, -1.0F, -2.0F));

        PartDefinition candle = partdefinition.addOrReplaceChild("candle", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 0.6667F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.5F, -11.6667F, -2.0F));
        PartDefinition cube_r1 = candle.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r2 = candle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition candle2 = partdefinition.addOrReplaceChild("candle2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.6667F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(16.5F, -11.6667F, -2.0F));
        PartDefinition cube_r3 = candle2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r4 = candle2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition candle3 = partdefinition.addOrReplaceChild("candle3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.6667F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.4468F, -11.9651F, 14.6096F));
        PartDefinition cube_r5 = candle3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r6 = candle3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition candle4 = partdefinition.addOrReplaceChild("candle4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 0.6667F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(7.8875F, -11.2544F, 12.6472F));
        PartDefinition cube_r7 = candle4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r8 = candle4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.8333F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(pEntity.idleAnimationState, HauntedMerchantAnimations.IDLE, pAgeInTicks, 1f);
    }

    public ModelPart getHead(){
        return this.head;
    }

    @Override
    public ModelPart root(){
        return this.root;
    }
}