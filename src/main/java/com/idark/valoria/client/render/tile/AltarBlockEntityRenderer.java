package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.*;

public class AltarBlockEntityRenderer implements BlockEntityRenderer<AbstractAltarBlockEntity>{

    public AltarBlockEntityRenderer(){
    }

    public boolean shouldRenderOffScreen(AbstractAltarBlockEntity pBlockEntity){
        return true;
    }

    @Override
    public void render(AbstractAltarBlockEntity altar, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        if(!altar.isSummoning) return;
        double progress = 1.5f;
        progress /= (double)altar.progressMax / altar.progress;

        double ticks = partialTicks + (ClientTick.ticksInGame + progress);

        ms.pushPose();
        ms.translate(0.5F, 1.75F + progress, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float)ticks * 6));
        ms.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = altar.getItemHandler().getItem(0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, altar.getLevel(), 0);
        ms.popPose();
    }
}