package com.idark.valoria.client.render.entity;

import com.idark.valoria.registries.entity.projectile.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;

@OnlyIn(Dist.CLIENT)
public class ThrownSpearRenderer extends EntityRenderer<ThrownSpearEntity>{
    public ThrownSpearRenderer(EntityRendererProvider.Context context){
        super(context);
    }

    public void render(ThrownSpearEntity entityIn, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light){
        ms.pushPose();
        if(!Minecraft.getInstance().isPaused() && !(entityIn.inGround || entityIn.onGround())){
            entityIn.rotationVelocity = Mth.lerp(partialTicks, entityIn.rotationVelocity, (entityIn.rotationVelocity + 1.2f) + (float)entityIn.getDeltaMovement().x);
        }

        ms.scale(1.5f, 1.5f, 1.5f);
        ms.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) + 90.0F));
        ms.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()) + entityIn.rotationVelocity));

        ModelResourceLocation MODEL = new ModelResourceLocation(ForgeRegistries.ITEMS.getKey(entityIn.getItem().getItem()).getNamespace(), ForgeRegistries.ITEMS.getKey(entityIn.getItem().getItem()).getPath(), "inventory");
        BakedModel spear = Minecraft.getInstance().getModelManager().getModel(MODEL);
        Minecraft.getInstance().getItemRenderer().render(entityIn.getItem(), ItemDisplayContext.FIXED, false, ms, buffers, light, OverlayTexture.NO_OVERLAY, spear.applyTransform(ItemDisplayContext.NONE, ms, false));
        ms.popPose();
        super.render(entityIn, entityYaw, partialTicks, ms, buffers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownSpearEntity pEntity){
        return null;
    }
}