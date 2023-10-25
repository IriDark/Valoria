package com.idark.darkrpg.client.render.curio.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class BeltModel extends HumanoidModel {
  public ModelPart root, model;

  public BeltModel(ModelPart root) {
      super(root);
    /*super(1.0F);
    this.texWidth = 32;
    this.texHeight = 32;
	this.body  = new ModelRenderer(this, 0, 0);
	this.body .setPos(0.0F, 18.0F, 0.0F);
	this.body .addBox(-4.0F, 10.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.3F);*/
   }

  @Override
  public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
	//this.body.render(matrixStack, vertexBuilder, light, overlay);
  }
}