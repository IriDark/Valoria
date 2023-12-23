package com.idark.darkrpg.client.render.curio.model;

import com.google.common.collect.ImmutableList;
import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class NecklaceModel extends HumanoidModel<LivingEntity> {
    public ModelPart root, model;
    public static ModelLayerLocation NECKLACE_LAYER = new ModelLayerLocation(new ResourceLocation(DarkRPG.MOD_ID, "amulet"), "main");

    public NecklaceModel(ModelPart root) {
        super(root);
        this.root = root;
        this.model = root.getChild("body").getChild("model");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition hat = root.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_arm = root.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm = root.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_leg = root.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_leg = root.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition model = body.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.model.yRot = Mth.sin(ageInTicks * 0.03F) * 0.03F;
        this.model.zRot = Mth.sin(ageInTicks * 0.03F) * 0.03F;
        this.model.xRot = Mth.sin(limbSwing * 0.50F) * 0.03F;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(root.getChild("head"));
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(root.getChild("body"));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}