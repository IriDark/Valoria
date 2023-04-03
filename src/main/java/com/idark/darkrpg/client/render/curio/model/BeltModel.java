package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class BeltModel<T extends LivingEntity> extends EntityModel<T> {

  public ModelRenderer belt;
  public BeltModel() {
    this.textureWidth = 32;
    this.textureHeight = 32;
	this.belt  = new ModelRenderer(this, 0, 0);
	this.belt .setRotationPoint(0.0F, 18.0F, 0.0F);
	this.belt .addBox(-4.0F, -5.5F, -2.0F, 8.0F, 1.0F, 4.0F, 0.2F);
   }

  @Override
  public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float netHeadPitch) {
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
  int light, int overlay, float red, float green, float blue, float alpha) {
  this.belt.render(matrixStack, vertexBuilder, light, overlay);
  }
}