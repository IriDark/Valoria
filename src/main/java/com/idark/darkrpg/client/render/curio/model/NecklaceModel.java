package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class NecklaceModel<T extends LivingEntity> extends EntityModel<T> {

  public ModelRenderer necklace;
  public NecklaceModel() {
    this.textureWidth = 32;
    this.textureHeight = 32;
	this.necklace  = new ModelRenderer(this, 0, 0);
	this.necklace .setRotationPoint(0.0F, 24.0F, 0.0F);
	this.necklace .addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.3F);
   }

  @Override
  public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float netHeadPitch) {
  }

  @Override
  public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
  int light, int overlay, float red, float green, float blue, float alpha) {
  this.necklace.render(matrixStack, vertexBuilder, light, overlay);
  }
}
