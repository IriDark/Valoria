package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.world.item.*;

public class JewelryBlockEntityRender implements BlockEntityRenderer<JewelryBlockEntity>{

    public JewelryBlockEntityRender(){
    }

    @Override
    public void render(JewelryBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay){
        int lightAbove = LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above());
        final float spacing = .189f;
        final float offset = .31f;

        for(int i = 0; i < 2; i++){
            ItemStack item = pBlockEntity.itemHandler.getStackInSlot(i);
            if(item.isEmpty()) continue;

            pPoseStack.pushPose();
            pPoseStack.translate(spacing * i + offset, 1.01F, spacing * i + offset);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            pPoseStack.scale(0.65F, 0.65F, 0.65F);

            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GROUND, lightAbove, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
            pPoseStack.popPose();
        }

        ItemStack item = pBlockEntity.itemOutputHandler.getStackInSlot(0);

        pPoseStack.pushPose();
        pPoseStack.translate(spacing * 2.1 + offset, 1.01F, spacing / 2 + offset);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        pPoseStack.scale(0.65F, 0.65F, 0.65F);

        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GROUND, lightAbove, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
        pPoseStack.popPose();
    }
}
