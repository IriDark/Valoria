package com.idark.valoria.client.render.model.tileentity;

import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.client.event.ClientTickHandler;
import com.idark.valoria.tileentity.KegBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.BlockState;

public class KegBlockRenderer implements BlockEntityRenderer<KegBlockEntity> {

    public KegBlockRenderer() {}

    @Override
    public void render(KegBlockEntity keg, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        double sinValue = Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.1);
        float scale = 1.15f + (float) (sinValue / 32);

        float translationX = (1 - scale) / 2;
        float translationY = (1 - scale) / 2;

        ms.translate(translationX, translationY, -0.05F);
        ms.scale(scale, scale, scale);
        ms.mulPose(Axis.YP.rotationDegrees(keg.getBlockRotate()));

        BlockState kegBlockState = ModBlocks.KEG_BARREL.get().defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(kegBlockState, ms, buffers, light, overlay);

        ms.popPose();
    }
}