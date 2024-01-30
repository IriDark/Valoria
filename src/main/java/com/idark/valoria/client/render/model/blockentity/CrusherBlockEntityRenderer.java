package com.idark.valoria.client.render.model.blockentity;

import com.idark.valoria.block.blockentity.CrusherBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrusherBlockEntityRenderer implements BlockEntityRenderer<CrusherBlockEntity> {

    public CrusherBlockEntityRenderer() {}

    @Override
    public void render(CrusherBlockEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ItemStack stack = pedestal.getItemHandler().getItem(0);
        if (!stack.isEmpty()) {
            ms.pushPose();
            ms.translate(0.5D, 1.05D, 0.5D);
            ms.mulPose(Axis.XP.rotationDegrees(90.0F));
            ms.scale(0.7F, 0.7F, 0.7F);
            // Weird error with light so it`s static
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, 14680064, overlay, ms, buffers, pedestal.getLevel(), 0);
            ms.popPose();
        }
    }
}