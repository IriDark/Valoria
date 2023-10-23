package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.entity.projectile.KunaiEntity;
import com.idark.darkrpg.entity.model.KunaiModel;
import com.idark.darkrpg.entity.projectile.PoisonedKunaiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoisonedKunaiRenderer extends EntityRenderer<PoisonedKunaiEntity> {
	public static final ResourceLocation KUNAI = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/poisoned_kunai.png");
	private final KunaiModel kunai = new KunaiModel();

	public PoisonedKunaiRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

    public void render(KunaiEntity entityIn, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
		/*if (!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.isOnGround())){
            entityIn.rotationVelocity = MathUtils.interpolate(entityIn.rotationVelocity,entityIn.rotationVelocity+10,partialTicks);
        }
		
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot) + 90.0F-entityIn.rotationVelocity));
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getFoilBufferDirect(bufferIn, this.kunai.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
        this.kunai.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);*/
    }
    
	
	public ResourceLocation getTextureLocation(PoisonedKunaiEntity entity) {
		return KUNAI;
	}
}