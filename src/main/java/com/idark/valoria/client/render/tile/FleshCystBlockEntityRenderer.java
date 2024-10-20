package com.idark.valoria.client.render.tile;

import com.idark.valoria.*;
import com.idark.valoria.client.event.*;
import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.world.item.*;

public class FleshCystBlockEntityRenderer implements BlockEntityRenderer<FleshCystBlockEntity> {
    public FleshCystBlockEntityRenderer() {
    }

    public static void renderCustomModel(ModelResourceLocation model, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(model);
        Minecraft.getInstance().getItemRenderer().render(new ItemStack(Items.DIRT), displayContext, leftHand, poseStack, buffer, combinedLight, combinedOverlay, bakedmodel);
    }

    @Override
    public void render(FleshCystBlockEntity keg, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        ms.pushPose();
        double sinValue = Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
        float scale = 2.15f + (float)(sinValue / 16);
        ms.translate(0.5f, 0.5f, 0.5f);
        ms.scale(scale, scale, scale);
        renderCustomModel(ValoriaClient.CYST, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }
}