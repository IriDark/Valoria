package com.idark.valoria.client.render.model.blockentity;

import com.idark.valoria.registries.world.block.entity.JewelryBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class JewelryBlockEntityRender implements BlockEntityRenderer<JewelryBlockEntity> {

    public JewelryBlockEntityRender() {
    }

    @Override
    public void render(JewelryBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        int lightAbove = LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above());
        final double spacing = .189;
        final double offset = .31;

        for (int i = 0; i < 2; i++) {
            ItemStack item = pBlockEntity.itemHandler.getStackInSlot(i);
            if (item.isEmpty()) continue;

            pPoseStack.pushPose();
            pPoseStack.translate(spacing * i +offset, 1.025F, spacing * i +offset);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            pPoseStack.scale(0.65F, 0.65F, 0.65F);

            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GROUND, lightAbove, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
            pPoseStack.popPose();
        }

        ItemStack item = pBlockEntity.itemOutputHandler.getStackInSlot(0);

        pPoseStack.pushPose();
        pPoseStack.translate(spacing * 2.1 +offset, 1.06F, spacing / 2 +offset);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        pPoseStack.scale(0.65F, 0.65F, 0.65F);

        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GROUND, lightAbove, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
        pPoseStack.popPose();
    }
}
