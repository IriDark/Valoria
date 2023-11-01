package com.idark.darkrpg.entity.renderer;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.client.event.ClientTickHandler;
import com.idark.darkrpg.entity.projectile.KunaiEntity;
import com.idark.darkrpg.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KunaiRenderer extends EntityRenderer<KunaiEntity> {
	public static final ResourceLocation KUNAI = new ResourceLocation(DarkRPG.MOD_ID, "textures/entity/kunai.png");
	public KunaiRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public void render(KunaiEntity entityIn, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
		if (!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.onGround())){
			entityIn.rotationVelocity = Mth.lerp(entityIn.rotationVelocity,entityIn.rotationVelocity+10,partialTicks);
		}

		ms.pushPose();
		ms.translate(0.5F, 1.1875F, 0.5F);
		ms.scale(0.5F, 0.5F, 0.5F);
		ItemStack stack = ModItems.SAMURAI_KUNAI.get().getDefaultInstance();
		Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, entityIn.level(), 0);
		ms.popPose();
	}

	public ResourceLocation getTextureLocation(KunaiEntity entity) {
		return null;
	}
}