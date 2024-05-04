package com.idark.valoria.client.render.entity;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.entity.projectile.ThrownSpearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ThrownSpearRenderer extends EntityRenderer<ThrownSpearEntity> {
    public ThrownSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(ThrownSpearEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        ms.pushPose();
        if (!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.onGround())) {
            entityIn.rotationVelocity = Mth.lerp(partialTicks, entityIn.rotationVelocity, (entityIn.rotationVelocity + 1.2f) + (float) entityIn.getDeltaMovement().x);
        }

        ms.scale(1.5f, 1.5f, 1.5f);
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()) + entityIn.rotationVelocity));

        ModelResourceLocation MODEL = new ModelResourceLocation(Valoria.ID, ForgeRegistries.ITEMS.getKey(entityIn.getItem().getItem()).getPath() + "_in_hand", "inventory");
        BakedModel spear = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(MODEL);
        Minecraft.getInstance().getItemRenderer().render(entityIn.getItem(), ItemDisplayContext.FIXED, false, ms, buffers, light, OverlayTexture.NO_OVERLAY, spear);
        ms.popPose();
        super.render(entityIn, entityYaw, partialTicks, ms, buffers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownSpearEntity pEntity) {
        return null;
    }
}