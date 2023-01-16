package com.idark.darkrpg.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.MathHelper;
import com.idark.darkrpg.entity.custom.GoblinEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GoblinModel <T extends GoblinEntity> extends EntityModel<T> {
	private final ModelRenderer Head;
	private final ModelRenderer LeftEar;
	private final ModelRenderer RightEar;
	private final ModelRenderer Body;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer LeftLeg;
	private final ModelRenderer RightLeg;
	private T entit;

	public GoblinModel() {
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 10.0F, 0.0F);
		Head.setTextureOffset(0, 0).addBox(-4.5F, -7.0F, -3.0F, 9.0F, 7.0F, 6.0F, 0.0F, false);

		LeftEar = new ModelRenderer(this);
		LeftEar.setRotationPoint(0.0F, 14.0F, 0.0F);
		Head.addChild(LeftEar);
		setRotationAngle(LeftEar, 0.0F, -0.3054F, 0.0F);
		LeftEar.setTextureOffset(0, 0).addBox(3.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, true);
		LeftEar.setTextureOffset(24, 0).addBox(3.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, 0.0F, true);

		RightEar = new ModelRenderer(this);
		RightEar.setRotationPoint(0.0F, 14.0F, 0.0F);
		Head.addChild(RightEar);
		setRotationAngle(RightEar, 0.0F, 0.3054F, 0.0F);
		RightEar.setTextureOffset(0, 2).addBox(-4.9F, -18.0F, -2.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		RightEar.setTextureOffset(24, 3).addBox(-6.9F, -20.0F, -2.5F, 3.0F, 2.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 24.0F, 0.0F);
		Body.setTextureOffset(0, 13).addBox(-3.5F, -14.0F, -2.0F, 7.0F, 8.0F, 4.0F, 0.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(4.0F, 11.0F, 0.0F);
		LeftArm.setTextureOffset(22, 13).addBox(-0.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-4.0F, 11.0F, 0.0F);
		RightArm.setTextureOffset(0, 25).addBox(-2.5F, -1.001F, -1.4564F, 3.0F, 9.0F, 3.0F, 0.0F, false);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(2.0F, 18.0F, 0.0F);
		LeftLeg.setTextureOffset(12, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-2.0F, 18.0F, 0.0F);
		RightLeg.setTextureOffset(24, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, 0.0F, false);
	}
	
	@Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount,
                                  float ageInTicks, float netHeadYaw, float HeadPitch) {
        this.Head.rotateAngleX = HeadPitch * ((float)Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}