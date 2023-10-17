package com.idark.darkrpg.client.render.model.tileentity;

import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class CrusherTileEntityRenderer extends TileEntityRenderer<CrusherTileEntity> {

    public CrusherTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(CrusherTileEntity pedestal, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        ItemStack itemstack = pedestal.getItemHandler().getStackInSlot(0);
        if (!itemstack.isEmpty()) {
            ms.push();
            ms.translate(0.5D, 1.05D, 0.5D);
            ms.rotate(Vector3f.XP.rotationDegrees(90.0F));
            ms.translate(0.0D, -0.0D, 0.0D);
            ms.scale(0.7F, 0.7F, 0.7F);
            Minecraft.getInstance().getItemRenderer().renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
            ms.pop();
        }
    }
}