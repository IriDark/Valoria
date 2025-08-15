package com.idark.valoria.client.model.entity;

import com.idark.valoria.client.model.animations.*;
import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;

public class TrollModel<T extends Troll> extends AbstractHierarchicalModel<T>{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public TrollModel(ModelPart root){
        this.root = root;
        this.head = root.getChild("head");
        this.leftEar = this.head.getChild("left_ear");
        this.rightEar = this.head.getChild("right_ear");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -1.4F, -3.8F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, 4.6F, -3.8F, 10.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-4.0F, -1.4F, -3.8F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -6.6F, -0.2F));

        PartDefinition right_horn_r1 = head.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(0.0F, -3.0F, -2.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -4.4F, -0.3F, 0.0F, 0.4363F, 0.0F));

        PartDefinition left_horn_r1 = head.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -3.0F, -2.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.4F, -0.3F, 0.0F, -0.4363F, 0.0F));

        PartDefinition leftEar = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 5.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, -5.5F, 0.0F));

        PartDefinition rightEar = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.5F, 5.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, -5.5F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 22).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 6).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 38).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 38).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(6.0F, 6.0F, 0.0F));

        PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 38).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 38).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-6.0F, 6.0F, 0.0F));

        PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 38).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 18.0F, 0.0F));

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 38).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 38).mirror().addBox(-2.0F, -6.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-2.0F, 18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart getHead(){
        return head;
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.head.getChild("right_ear").yRot = Mth.sin(pAgeInTicks * 0.25F) * 0.06F;
        this.head.getChild("left_ear").yRot = Mth.sin(pAgeInTicks * -0.25F) * 0.06F;
        this.head.getChild("right_ear").xRot = Mth.sin(pAgeInTicks * 0.15F) * 0.01F;
        this.head.getChild("left_ear").xRot = Mth.sin(pAgeInTicks * -0.15F) * 0.01F;
        if (this.riding) {
            this.head.z = 2F;
            this.head.y = -3F;
            this.body.z = 4F;
            this.body.y = 10F;
            this.leftArm.y = 8F;
            this.rightArm.y = 8F;
            this.leftArm.z = 1F;
            this.rightArm.z = 1F;

            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 12F);
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 12F);
            this.leftLeg.zRot = -0.07853982F;
        }

        this.animateWalk(GoblinAnimations.WALK, pLimbSwing, pLimbSwingAmount, 2, 6);
        this.animateIdle(pEntity.idleAnimationState, TrollAnimations.IDLE, pLimbSwingAmount, pAgeInTicks, 0.5f);
    }

    @Override
    public ModelPart root(){
        return this.root;
    }
}