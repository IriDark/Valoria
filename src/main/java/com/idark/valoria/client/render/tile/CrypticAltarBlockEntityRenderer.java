package com.idark.valoria.client.render.tile;

import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.registries.block.entity.CrypticAltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrypticAltarBlockEntityRenderer implements BlockEntityRenderer<CrypticAltarBlockEntity>{

    public CrypticAltarBlockEntityRenderer(){
    }

    public boolean shouldRenderOffScreen(CrypticAltarBlockEntity pBlockEntity){
        return true;
    }

    @Override
    public void render(CrypticAltarBlockEntity altar, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        if(!altar.isSummoning) return;
        double progress = 1.5f;
        progress /= (double)altar.progressMax / altar.progress;

        double ticks = partialTicks + (ClientTickHandler.ticksInGame + progress);

        ms.pushPose();
        ms.translate(0.5F, 1.75F + progress, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float)ticks * 6));
        ms.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = altar.getItemHandler().getItem(0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, altar.getLevel(), 0);
        ms.popPose();
    }
}