package com.idark.valoria.client.render.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ShadewoodSpiderModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart root;
    private final ModelPart body0;
    private final ModelPart body1;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightMiddleHindLeg;
    private final ModelPart leftMiddleHindLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart right_chelicer;
    private final ModelPart left_chelicer;

    public ShadewoodSpiderModel(ModelPart root) {
        this.root = root;
        this.body0 = root.getChild("body0");
        this.body1 = root.getChild("body1");
        this.head = root.getChild("head");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightMiddleHindLeg = root.getChild("right_middle_hind_leg");
        this.leftMiddleHindLeg = root.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = root.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = root.getChild("left_middle_front_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.right_chelicer = head.getChild("right_chelicer");
        this.left_chelicer = head.getChild("left_chelicer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body0 = partdefinition.addOrReplaceChild("body0", CubeListBuilder.create().texOffs(0, 40).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body1 = partdefinition.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition body1_r1 = body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.75F, 0.0F, 8.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 3.0F, 0.2618F, 0.0F, 0.0F));
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-4.0F, -13.0F, -11.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(27, 4).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        CubeListBuilder cubelistbuilder1 = CubeListBuilder.create().texOffs(27, 4).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        PartDefinition rightHindLeg = partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 2.0F));
        PartDefinition leftHindLeg = partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 2.0F));
        PartDefinition rightMiddleHindLeg = partdefinition.addOrReplaceChild("right_middle_hind_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 1.0F));
        PartDefinition leftMiddleHindLeg = partdefinition.addOrReplaceChild("left_middle_hind_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 1.0F));
        PartDefinition rightMiddleFrontLeg = partdefinition.addOrReplaceChild("right_middle_front_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, 0.0F));
        PartDefinition leftMiddleFrontLeg = partdefinition.addOrReplaceChild("left_middle_front_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, 0.0F));
        PartDefinition rightFrontLeg = partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-4.0F, 15.0F, -1.0F));
        PartDefinition leftFrontLeg = partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder1, PartPose.offset(4.0F, 15.0F, -1.0F));

        PartDefinition right_chelicer = head.addOrReplaceChild("right_chelicer", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -8.0F, -12.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_chelicer = head.addOrReplaceChild("left_chelicer", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -8.0F, -12.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
        this.rightHindLeg.zRot = (-(float)Math.PI / 4F);
        this.leftHindLeg.zRot = ((float)Math.PI / 4F);
        this.rightMiddleHindLeg.zRot = -0.58119464F;
        this.leftMiddleHindLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = (-(float)Math.PI / 4F);
        this.leftFrontLeg.zRot = ((float)Math.PI / 4F);
        this.rightHindLeg.yRot = ((float)Math.PI / 4F);
        this.leftHindLeg.yRot = (-(float)Math.PI / 4F);
        this.rightMiddleHindLeg.yRot = ((float)Math.PI / 8F);
        this.leftMiddleHindLeg.yRot = (-(float)Math.PI / 8F);
        this.rightMiddleFrontLeg.yRot = (-(float)Math.PI / 8F);
        this.leftMiddleFrontLeg.yRot = ((float)Math.PI / 8F);
        this.rightFrontLeg.yRot = (-(float)Math.PI / 4F);
        this.leftFrontLeg.yRot = ((float)Math.PI / 4F);
        float f3 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * pLimbSwingAmount;
        float f4 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + (float)Math.PI) * 0.4F) * pLimbSwingAmount;
        float f5 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + ((float)Math.PI / 2F)) * 0.4F) * pLimbSwingAmount;
        float f6 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + ((float)Math.PI * 1.5F)) * 0.4F) * pLimbSwingAmount;
        float f7 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + 0.0F) * 0.4F) * pLimbSwingAmount;
        float f8 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + (float)Math.PI) * 0.4F) * pLimbSwingAmount;
        float f9 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + ((float)Math.PI / 2F)) * 0.4F) * pLimbSwingAmount;
        float f10 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + ((float)Math.PI * 1.5F)) * 0.4F) * pLimbSwingAmount;
        this.rightHindLeg.yRot += f3;
        this.leftHindLeg.yRot += -f3;
        this.rightMiddleHindLeg.yRot += f4;
        this.leftMiddleHindLeg.yRot += -f4;
        this.rightMiddleFrontLeg.yRot += f5;
        this.leftMiddleFrontLeg.yRot += -f5;
        this.rightFrontLeg.yRot += f6;
        this.leftFrontLeg.yRot += -f6;
        this.rightHindLeg.zRot += f7;
        this.leftHindLeg.zRot += -f7;
        this.rightMiddleHindLeg.zRot += f8;
        this.leftMiddleHindLeg.zRot += -f8;
        this.rightMiddleFrontLeg.zRot += f9;
        this.leftMiddleFrontLeg.zRot += -f9;
        this.rightFrontLeg.zRot += f10;
        this.leftFrontLeg.zRot += -f10;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body0.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightMiddleHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftMiddleHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightMiddleFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftMiddleFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public ModelPart root() {
        return this.root;
    }
}
