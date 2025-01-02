package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.living.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class ScourgeModel<T extends ScourgeEntity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;

    public ScourgeModel(ModelPart root) {
        this.root = root;
        this.Head = root.getChild("head");
        this.Body = root.getChild("body");
        this.RightArm = root.getChild("right_arm");
        this.LeftArm = root.getChild("left_arm");
        this.RightLeg = root.getChild("right_leg");
        this.LeftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
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

    public ModelPart getHead() {
        return this.Head;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch){

    }
}
