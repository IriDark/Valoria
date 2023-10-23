package com.idark.darkrpg.client.render.model.tileentity;

import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrusherTileEntityRenderer implements BlockEntityRenderer<CrusherTileEntity> {

    public CrusherTileEntityRenderer() {}

    @Override
    public void render(CrusherTileEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ItemStack itemstack = pedestal.getItemHandler().getItem(0);
        if (!itemstack.isEmpty()) {
            ms.pushPose();
            ms.translate(0.5D, 1.05D, 0.5D);
            ms.mulPose(Axis.XP.rotationDegrees(90.0F));
            ms.translate(0.0D, -0.0D, 0.0D);
            ms.scale(0.7F, 0.7F, 0.7F);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, pedestal.getLevel(), 0);
            ms.popPose();
        }
    }
}