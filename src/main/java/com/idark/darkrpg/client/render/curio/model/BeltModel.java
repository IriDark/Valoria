package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class BeltModel extends BipedModel<LivingEntity> {

  public ModelRenderer belt;
  public BeltModel() {
    super(1.0F);	  
    this.texWidth = 32;
    this.texHeight = 32;
	this.body  = new ModelRenderer(this, 0, 0);
	this.body .setPos(0.0F, 18.0F, 0.0F);
	this.body .addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.3F);
   }

  @Override
  public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
	int light, int overlay, float red, float green, float blue, float alpha) {
	this.body.render(matrixStack, vertexBuilder, light, overlay);
  }
}