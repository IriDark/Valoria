package com.idark.valoria.util;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import org.joml.*;

import java.awt.*;
import java.lang.Math;

public class RenderUtils {
    /**
     * This code belongs to its author, and licensed under GPL-2.0 license
     *
     * @author MaxBogomol
     */
    public static void renderAura(PoseStack mStack, VertexConsumer builder, float radius, float size, int longs, Color color1, Color color2, float alpha1, float alpha2, boolean renderSide, boolean renderFloor) {
        float r1 = color1.getRed() / 255f;
        float g1 = color1.getGreen() / 255f;
        float b1 = color1.getBlue() / 255f;

        float r2 = color2.getRed() / 255f;
        float g2 = color2.getGreen() / 255f;
        float b2 = color2.getBlue() / 255f;

        float startU = 0;
        float endU = Mth.PI * 2;
        float stepU = (endU - startU) / longs;
        for (int i = 0; i < longs; ++i) {
            float u = i * stepU + startU;
            float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
            auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
            auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);
            if (renderSide) {
                auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
                auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            }

            if (renderFloor) {
                auraPiece(mStack, builder, 0, 0, u, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);

                if (renderSide) {
                    auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, 0, 0, u, r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);
                    auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                }
            }
        }
    }

    public static void auraPiece(PoseStack mStack, VertexConsumer builder, float radius, float size, float angle, float r, float g, float b, float alpha) {
        mStack.pushPose();
        mStack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(angle)));
        mStack.translate(radius, 0, 0);
        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, 0, size, 0).color(r, g, b, alpha).endVertex();
        mStack.popPose();
    }

    /**
     * Dimensions xSize, ySize, zSize are specified in pixels
     */
    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        PoseStack posestack = RenderSystem.getModelViewStack();
        BakedModel model = renderer.getModel(stack, null, null, 0);

        posestack.pushPose();
        posestack.translate(x, y, 100.0F);
        posestack.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        posestack.scale(xSize, -ySize, zSize);

        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        if (!model.usesBlockLight()) {
            Lighting.setupForFlatItems();
        } else {
            Lighting.setupFor3DItems();
        }

        renderer.render(stack, ItemDisplayContext.GUI, false, new PoseStack(), buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        buffer.endBatch();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}