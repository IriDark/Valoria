package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class HandsModel extends BipedModel<LivingEntity> {

  public ModelRenderer hands;
  public HandsModel() {
    super(1.0F);
    this.textureWidth = 32;
    this.textureHeight = 32;
    this.bipedRightArm  = new ModelRenderer(this, 0, 0);
    this.bipedRightArm .setRotationPoint(-5.0F, 2.0F, 0.0F);
	this.bipedRightArm .addBox(-3.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.3F);
    this.bipedLeftArm  = new ModelRenderer(this, 0, 0);
    this.bipedLeftArm .mirror = true;
    this.bipedLeftArm .setRotationPoint(5.0F, 2.0F, 0.0F);
	this.bipedLeftArm .addBox(-1.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.3F);
   }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
    int light, int overlay, float red, float green, float blue, float alpha) {
    this.bipedRightArm.render(matrixStack, vertexBuilder, light, overlay);
	this.bipedLeftArm.render(matrixStack, vertexBuilder, light, overlay);
  }
}