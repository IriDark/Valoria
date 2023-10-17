package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.projectile.PoisonedKunaiEntity;
import com.idark.darkrpg.entity.model.KunaiModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoisonedKunaiRenderer extends EntityRenderer<PoisonedKunaiEntity> {
	public static final ResourceLocation KUNAI = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/poisoned_kunai.png");
	private final KunaiModel kunai = new KunaiModel();

	public PoisonedKunaiRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	public void render(PoisonedKunaiEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		if (!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.isOnGround())){
            entityIn.rotationVelocity = MathUtils.interpolate(entityIn.rotationVelocity,entityIn.rotationVelocity+10,partialTicks);
        }
		
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot) + 90.0F-entityIn.rotationVelocity));
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getFoilBufferDirect(bufferIn, this.kunai.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
        this.kunai.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    
	
	public ResourceLocation getTextureLocation(PoisonedKunaiEntity entity) {
		return KUNAI;
	}
}