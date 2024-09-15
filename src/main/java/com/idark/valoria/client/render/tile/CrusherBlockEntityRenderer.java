package com.idark.valoria.client.render.tile;

import com.idark.valoria.registries.block.entity.CrusherBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CrusherBlockEntityRenderer implements BlockEntityRenderer<CrusherBlockEntity> {

    public CrusherBlockEntityRenderer() {
    }

    @Override
    public void render(CrusherBlockEntity crusherBlockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0.5F, 1.025F, 0.5F);
        ms.mulPose(Axis.XP.rotationDegrees(90.0F));
        ms.scale(0.55F, 0.55F, 0.55F);
        ItemStack stack = crusherBlockEntity.getItemHandler().getItem(0);

        int lightAbove = LevelRenderer.getLightColor(crusherBlockEntity.getLevel(), crusherBlockEntity.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, lightAbove, overlay, ms, buffers, crusherBlockEntity.getLevel(), 0);
        ms.popPose();
    }
}