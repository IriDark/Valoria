package com.idark.darkrpg.client.render.model.tileentity;

import com.idark.darkrpg.client.event.ClientTickHandler;
import com.idark.darkrpg.tileentity.CrusherTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3f;

public class CrusherTileEntityRenderer extends TileEntityRenderer<CrusherTileEntity> {

    public CrusherTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(CrusherTileEntity pedestal, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {

        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.push();
        ms.translate(0.5F, 1.1875F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.rotate(Vector3f.XP.rotationDegrees((float) 180));
        ms.scale(0.5F, 0.5F, 0.5F);
        ItemStack stack = pedestal.getItemHandler().getStackInSlot(0);
		Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.FIXED, light, overlay, ms, buffers);
        ms.pop();
    }
}