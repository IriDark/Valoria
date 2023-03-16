package com.idark.darkrpg.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.MathHelper;
import com.idark.darkrpg.entity.custom.MannequinEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MannequinModel <T extends MannequinEntity> extends EntityModel<T> {
	private final ModelRenderer Head;
	private final ModelRenderer Body;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	private T entit;

	public MannequinModel() {
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 24.0F, 0.0F);
		Head.setTextureOffset(0, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 24.0F, 0.0F);
		Body.setTextureOffset(0, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		Body.setTextureOffset(14, 41).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
		Body.setTextureOffset(24, 0).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 6.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(0.0F, 24.0F, 0.0F);
		RightArm.setTextureOffset(0, 32).addBox(-7.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);
		RightArm.setTextureOffset(38, 15).addBox(-8.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(0.0F, 24.0F, 0.0F);
		LeftArm.setTextureOffset(24, 16).addBox(4.0F, -24.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);
		LeftArm.setTextureOffset(32, 7).addBox(4.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(0.0F, 24.0F, 0.0F);
		RightLeg.setTextureOffset(30, 32).addBox(-3.9F, -12.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(0.0F, 24.0F, 0.0F);
		LeftLeg.setTextureOffset(14, 32).addBox(-0.1F, -12.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
	}
	
	@Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch) {
        float factor = (float)Math.sin(ageInTicks*2f);
        float speed = 0.1f;
        this.Body.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.Body.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.Head.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.Head.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.LeftArm.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.RightArm.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.LeftLeg.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.5662F*speed + (float)Math.PI) * 0.4F*factor * limbSwingAmount;
        this.RightLeg.rotateAngleZ = MathHelper.sin(limbSwing * 0.2262F*speed + (float)Math.PI) * 0.1F*factor * limbSwingAmount;
    }

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}