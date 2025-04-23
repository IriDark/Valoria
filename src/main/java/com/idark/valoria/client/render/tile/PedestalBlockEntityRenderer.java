package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.common.registry.book.*;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity>{

    public PedestalBlockEntityRenderer(){
    }

    @Override
    public void render(PedestalBlockEntity pedestal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay){
        Book book = pedestal.getBook();
        if(book != null){
            BookComponent component = pedestal.bookComponent;
            ItemStack stack = pedestal.getItemHandler().getItem(0);
            if(component != null){
                ms.pushPose();
                ms.translate(0.5F, 0.85F, 0.5F);
                float f = (float)component.time + partialTicks;
                ms.translate(0.0F, 0.1F + Mth.sin(f * 0.1F) * 0.01F, 0.0F);

                float f1;
                for(f1 = component.rot - component.oRot; f1 >= (float)Math.PI; f1 -= ((float)Math.PI * 2F)){}
                while(f1 < -(float)Math.PI){
                    f1 += ((float)Math.PI * 2F);
                }

                float f2 = component.oRot + f1 * partialTicks;
                ms.mulPose(Axis.YP.rotation(-f2));
                ms.mulPose(Axis.ZP.rotationDegrees(80.0F));
                ms.mulPose(Axis.XP.rotationDegrees(180f));
                ms.scale(0.75f, 0.75f, 0.75f);
                book.render(pedestal.getLevel(), pedestal.getBlockPos().getCenter(), stack, component, partialTicks, ms, buffers, light, overlay);
                ms.popPose();
            }
        }else{
            double ticks = (ClientTick.ticksInGame + partialTicks) * 2;
            double ticksUp = (ClientTick.ticksInGame + partialTicks) * 4;
            ticksUp = (ticksUp) % 360;

            ms.pushPose();
            ms.translate(0.5F, 1.1875F, 0.5F);
            ms.translate(0F, (float)(Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float)ticks));
            ms.scale(0.5F, 0.5F, 0.5F);
            ItemStack stack = pedestal.getItemHandler().getItem(0);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffers, pedestal.getLevel(), 0);
            ms.popPose();
        }
    }
}