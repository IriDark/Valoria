package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.living.boss.WickedCrystal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WickedCrystalModel<T extends WickedCrystal> extends EntityModel<T>{
    private final ModelPart crystal;
    public WickedCrystalModel(ModelPart root) {
        this.crystal = root.getChild("crystal");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition crystal = partdefinition.addOrReplaceChild("crystal", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -27.0F, -10.0F, 20.0F, 54.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 74).addBox(10.0F, -16.0F, -7.0F, 6.0F, 32.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(40, 74).addBox(-16.0F, -16.0F, -7.0F, 6.0F, 32.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(80, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(80, 13).addBox(-4.0F, 27.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        crystal.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}