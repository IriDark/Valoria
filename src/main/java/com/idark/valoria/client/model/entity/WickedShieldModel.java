package com.idark.valoria.client.model.entity;

import com.idark.valoria.registries.entity.living.minions.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;

public class WickedShieldModel<T extends WickedShield> extends EntityModel<T>{
    private final ModelPart bb_main;
    public WickedShieldModel(ModelPart root){
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 24).addBox(-5.0F, -16.0F, -1.0F, 10.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-7.0F, -30.0F, -1.0F, 14.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(24, 24).addBox(-9.0F, -31.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(0, 16).addBox(-8.0F, -17.0F, -2.0F, 16.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(24, 32).addBox(-6.0F, -14.0F, -1.25F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(32, 32).addBox(2.0F, -14.0F, -1.25F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(32, 0).addBox(3.0F, -31.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(0, 39).addBox(2.0F, -14.0F, 1.25F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(8, 39).addBox(-6.0F, -14.0F, 1.25F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(36, 8).addBox(-3.0F, -33.0F, -2.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}