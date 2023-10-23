package com.idark.darkrpg.entity.model;

import com.idark.darkrpg.entity.custom.GoblinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GoblinModel <T extends GoblinEntity> extends EntityModel<T> {
	/*private final ModelPart Head;
	private final ModelPart LeftEar;
	private final ModelPart RightEar;
	private final ModelPart Body;
	private final ModelPart LeftArm;
	private final ModelPart RightArm;
	private final ModelPart LeftLeg;
	private final ModelPart RightLeg;*/

	public GoblinModel() {
		//MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 1);
		//PartDefinition root = mesh.getRoot();
		//this.Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		//PartDefinition Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		//PartDefinition Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		//PartDefinition Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		//PartDefinition Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		//PartDefinition Head = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

		/*texWidth = 64;
		texHeight = 64;

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 10.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-4.5F, -7.0F, -3.0F, 9.0F, 7.0F, 6.0F, 0.0F, false);

		LeftEar = new ModelRenderer(this);
		LeftEar.setPos(0.0F, 14.0F, 0.0F);
		Head.addChild(LeftEar);
		setRotationAngle(LeftEar, 0.0F, -0.3054F, 0.0F);
		LeftEar.texOffs(0, 0).addBox(3.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		LeftEar.texOffs(24, 0).addBox(3.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, 0.0F, true);

		RightEar = new ModelRenderer(this);
		RightEar.setPos(0.0F, 14.0F, 0.0F);
		Head.addChild(RightEar);
		setRotationAngle(RightEar, 0.0F, 0.3054F, 0.0F);
		RightEar.texOffs(0, 2).addBox(-4.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightEar.texOffs(24, 3).addBox(-6.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 24.0F, 0.0F);
		Body.texOffs(0, 13).addBox(-3.5F, -14.0F, -2.0F, 7.0F, 8.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(4.0F, 11.0F, 0.0F);
		LeftArm.texOffs(22, 13).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-4.0F, 11.0F, 0.0F);
		RightArm.texOffs(0, 25).addBox(-2.5F, -1.001F, -1.4564F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(2.0F, 18.0F, 0.0F);
		LeftLeg.texOffs(12, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-2.0F, 18.0F, 0.0F);
		RightLeg.texOffs(24, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);*/
	}
	
	@Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch) {
        /*this.Head.xRot = HeadPitch * ((float)Math.PI / 180F);
        this.Head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.LeftLeg.xRot = (float) (Math.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount);
        this.RightLeg.xRot = (float) (Math.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);*/
    }
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		/*Head.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);*/
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}