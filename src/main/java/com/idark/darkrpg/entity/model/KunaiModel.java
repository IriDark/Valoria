package com.idark.darkrpg.entity.model;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class KunaiModel extends Model {
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/kunai.png");	
	private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 10, 11);

	public KunaiModel() {
		super(RenderType::entitySolid);
		this.modelRenderer.addBox(4.0F, -7.5F, 0.0F, 3.0F, 3.0F, 1.0F, -0.0003F, false);
		ModelRenderer bone = new ModelRenderer(32, 32, 0, 6);
		bone.addBox(-4.0F, 0.5F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		this.modelRenderer.addChild(bone);
		ModelRenderer bone1 = new ModelRenderer(32, 32, 12, 0);	
		bone1.addBox(-2.0F, -0.5F, 0.0F, 2.0F, 2.0F, 1.0F, 0.0002F, false);
		this.modelRenderer.addChild(bone1);
		ModelRenderer bone2 = new ModelRenderer(32, 32, 0, 11);			
		bone2.addBox(-1.0F, -3.5F, 0.0F, 4.0F, 4.0F, 1.0F, 0.0003F, false);
		this.modelRenderer.addChild(bone2);				
		ModelRenderer bone3 = new ModelRenderer(32, 32, 10, 6);			
		bone3.addBox(5.0F, -9.5F, 0.0F, 4.0F, 4.0F, 1.0F, -0.0001F, false);
		this.modelRenderer.addChild(bone3);			
		ModelRenderer bone4 = new ModelRenderer(32, 32, 0, 0);			
		bone4.addBox(1.0F, -6.5F, 0.0F, 5.0F, 5.0F, 1.0F, -0.0005F, false);
		this.modelRenderer.addChild(bone4);			
	}

	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}