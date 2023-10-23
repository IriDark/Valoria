package com.idark.darkrpg.entity.model;

import com.idark.darkrpg.entity.custom.SwampWandererEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import com.idark.darkrpg.entity.custom.MannequinEntity;

public class MannequinModel <T extends MannequinEntity> extends EntityModel<T> {
	/*private final ModelRenderer Head;
	private final ModelRenderer Body;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	private T entit;*/

	public MannequinModel() {
		/*texWidth = 64;
		texHeight = 64;

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 24.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 24.0F, 0.0F);
		Body.texOffs(0, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		Body.texOffs(14, 41).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
		Body.texOffs(24, 0).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 6.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(0.0F, 24.0F, 0.0F);
		RightArm.texOffs(0, 32).addBox(-7.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);
		RightArm.texOffs(38, 15).addBox(-8.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(0.0F, 24.0F, 0.0F);
		LeftArm.texOffs(24, 16).addBox(4.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);
		LeftArm.texOffs(32, 7).addBox(4.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(0.0F, 24.0F, 0.0F);
		RightLeg.texOffs(30, 32).addBox(-3.9F, -12.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(0.0F, 24.0F, 0.0F);
		LeftLeg.texOffs(14, 32).addBox(-0.1F, -12.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);*/
	}
	
	@Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch) {
        /*float factor = (float)Math.sin(ageInTicks*2f);
        float speed = 0.1f;
        this.Body.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.Body.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.Head.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.Head.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.LeftArm.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.LeftArm.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.RightArm.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.RightArm.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.LeftLeg.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.LeftLeg.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.RightLeg.xRot = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.RightLeg.zRot = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;*/
    }

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		/*Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);*/
	}
		public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}