package com.idark.valoria.entity.model;

import com.idark.valoria.entity.custom.GoblinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GoblinModel<T extends GoblinEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "goblin"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart arm1;
    private final ModelPart arm2;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart bone;

    public GoblinModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.arm1 = root.getChild("arm1");
        this.arm2 = root.getChild("arm2");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -7.0F, -3.0F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(3.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 0).mirror().addBox(3.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 2).addBox(-4.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 3).addBox(-6.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-3.5F, -14.0F, -2.0F, 7.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition arm1 = partdefinition.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(22, 13).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 11.0F, 0.0F));

        PartDefinition arm2 = partdefinition.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(0, 25).addBox(-2.5F, -1.001F, -1.4564F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 11.0F, 0.0F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(12, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 18.0F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(24, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, 0.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.5F, 17.0F, -4.0F, 0.1309F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.getChild("cube_r1").yRot = Mth.sin(ageInTicks * 0.06F) * 0.06F;
        this.head.getChild("cube_r2").yRot = Mth.sin(ageInTicks * -0.06F) * 0.06F;
        this.head.getChild("cube_r1").xRot = Mth.sin(ageInTicks * 0.01F) * 0.01F;
        this.head.getChild("cube_r2").xRot = Mth.sin(ageInTicks * -0.01F) * 0.01F;
        float f = 0.4F * limbSwingAmount;
        this.arm1.yRot = Mth.sin(ageInTicks * 0.06F) * 0.06F;
        this.arm2.yRot = Mth.sin(ageInTicks * -0.06F) * 0.06F;
        this.arm1.zRot = Mth.sin(ageInTicks * 0.06F) * 0.06F;
        this.arm2.zRot = Mth.sin(ageInTicks * -0.06F) * 0.06F;
        this.leg1.xRot = Mth.cos(limbSwing * 0.7F + (float)Math.PI) * f;
        this.leg2.xRot = Mth.cos(limbSwing * 0.7F) * f;
        this.arm1.xRot = Mth.cos(limbSwing * 0.7F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.arm2.xRot = Mth.cos(limbSwing * 0.7F) * 2.0F * limbSwingAmount * 0.5F;
        if (entity.isAggressive()) {
            float f1 = Mth.sin(this.attackTime * (float)Math.PI);
            float f2 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float)Math.PI);
            this.arm1.zRot = 0.0F;
            this.arm2.zRot = 0.0F;
            this.arm1.yRot = -(0.1F - f1 * 0.6F);
            this.arm2.yRot = 0.1F - f1 * 0.6F;
            this.arm1.xRot -= f1 * 1.2F - f2 * 0.4F;
            this.arm2.xRot -= f1 * 1.2F - f2 * 0.4F;
            AnimationUtils.bobArms(this.arm1, this.arm2, ageInTicks);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        arm1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        arm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}