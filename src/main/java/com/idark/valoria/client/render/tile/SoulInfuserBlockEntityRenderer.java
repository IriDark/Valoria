package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.*;

public class SoulInfuserBlockEntityRenderer implements BlockEntityRenderer<SoulInfuserBlockEntity>{

    public SoulInfuserBlockEntityRenderer(){
    }

    public boolean shouldRenderOffScreen(SoulInfuserBlockEntity pBlockEntity){
        return true;
    }

    @Override
    public void render(SoulInfuserBlockEntity infuser, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        if(!infuser.itemHandler.getStackInSlot(0).isEmpty()){
            ms.pushPose();
            float ticksUp = (ClientTick.ticksInGame + partialTicks) * 8;
            ticksUp = (ticksUp) % 360;
            float ticks = (ClientTick.ticksInGame + partialTicks) * 4;
            ticks = (ticks) % 360;

            ms.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.translate(0.5F, 1.75F, 0.5F);
            ms.mulPose(Axis.YP.rotationDegrees(ticks));
            ms.scale(0.5F, 0.5F, 0.5F);

            ItemStack stack = infuser.itemHandler.getStackInSlot(0);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, infuser.getLevel(), 0);
            ms.popPose();
        }
    }
}