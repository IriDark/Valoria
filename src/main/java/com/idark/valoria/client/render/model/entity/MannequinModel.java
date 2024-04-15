package com.idark.valoria.client.render.model.entity;

import com.idark.valoria.registries.entity.decoration.MannequinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class MannequinModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;
    private T entit;

    public MannequinModel(ModelPart root) {
        this.Head = root.getChild("Head");
        this.Body = root.getChild("Body");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -32.05F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 41).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 32).addBox(-7.01F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 15).addBox(-8.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(24, 16).addBox(4.01F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 7).addBox(4.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(30, 32).addBox(-4F, -12.01F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(14, 32).addBox(-0F, -12.01F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch) {
        if (entityIn instanceof MannequinEntity entity) {
            float factor = (float) Math.sin(ageInTicks + entity.getLastDamage() * 0.5);
            float speed = 0.1f;
            this.Body.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.Body.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
            this.Head.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.Head.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
            this.LeftArm.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.LeftArm.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
            this.RightArm.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.RightArm.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
            this.LeftLeg.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.LeftLeg.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
            this.RightLeg.xRot = Mth.cos(limbSwing * 0.5662F * speed + (float) Math.PI) * 0.4F * factor * limbSwingAmount;
            this.RightLeg.zRot = Mth.sin(limbSwing * 0.2262F * speed + (float) Math.PI) * 0.1F * factor * limbSwingAmount;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}