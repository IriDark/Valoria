package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BeltModel extends BipedModel<LivingEntity> {

  public ModelRenderer belt;
  public BeltModel() {
    super(1.0F);	  
    this.textureWidth = 32;
    this.textureHeight = 32;
	this.bipedBody  = new ModelRenderer(this, 0, 0);
	this.bipedBody .setRotationPoint(0.0F, 18.0F, 0.0F);
	this.bipedBody .addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.3F);
   }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
	int light, int overlay, float red, float green, float blue, float alpha) {
	this.bipedBody.render(matrixStack, vertexBuilder, light, overlay);
  }
}