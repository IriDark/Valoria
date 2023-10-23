package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class NecklaceModel extends HumanoidModel {
    public ModelPart necklace;

  public NecklaceModel(ModelPart root) {
      super(root);
    /*super(1.0F);
    this.texWidth = 32;
    this.texHeight = 32;
	this.body  = new ModelRenderer(this, 0, 0);
	this.body .setPos(0.0F, 24.0F, 0.0F);
	this.body .addBox(-4.0F, -0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.2F);*/
   }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    //this.body.render(matrixStack, vertexBuilder, light, overlay);
  }
}