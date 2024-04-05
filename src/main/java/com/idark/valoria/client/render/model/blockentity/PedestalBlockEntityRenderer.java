package com.idark.valoria.client.render.model.blockentity;

import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.registries.block.entity.types.PedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public PedestalBlockEntityRenderer() {
    }

    @Override
    public void render(PedestalBlockEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 1.1875F, 0.5F);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        ItemStack stack = pedestal.getItemHandler().getItem(0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, pedestal.getLevel(), 0);
        ms.popPose();
    }
}