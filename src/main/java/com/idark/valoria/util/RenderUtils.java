package com.idark.valoria.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderUtils{
    /**
     * This code belongs to its author, and licensed under GPL-2.0 license
     *
     * @author MaxBogomol
     */
    public static void renderAura(RenderBuilder builder, PoseStack poseStack, float radius, float size, int longs, boolean floor){
        Matrix4f last = poseStack.last().pose();
        RenderBuilder.VertexConsumerActor supplier = builder.getSupplier();
        VertexConsumer vertexConsumer = builder.getVertexConsumer();

        float startU = 0;
        float endU = Mth.PI * 2;
        float stepU = (endU - startU) / longs;
        for(int i = 0; i < longs; ++i){
            float u = i * stepU + startU;
            float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
            Vector3f p0 = new Vector3f((float)Math.cos(u) * radius, 0, (float)Math.sin(u) * radius);
            Vector3f p1 = new Vector3f((float)Math.cos(un) * radius, 0, (float)Math.sin(un) * radius);

            float textureU = builder.u0;
            float textureV = builder.v0;
            float textureUN = builder.u1;
            float textureVN = builder.v1;
            if(builder.firstSide){
                supplier.placeVertex(vertexConsumer, last, builder, p0.x(), size, p0.z(), builder.r2, builder.g2, builder.b2, builder.a2, textureU, textureVN, builder.l2);
                supplier.placeVertex(vertexConsumer, last, builder, p1.x(), size, p1.z(), builder.r2, builder.g2, builder.b2, builder.a2, textureUN, textureVN, builder.l2);
                supplier.placeVertex(vertexConsumer, last, builder, p1.x(), 0, p1.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureUN, textureV, builder.l1);
                supplier.placeVertex(vertexConsumer, last, builder, p0.x(), 0, p0.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureU, textureV, builder.l1);
            }

            if(builder.secondSide){
                supplier.placeVertex(vertexConsumer, last, builder, p0.x(), 0, p0.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureUN, textureV, builder.l1);
                supplier.placeVertex(vertexConsumer, last, builder, p1.x(), 0, p1.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureU, textureV, builder.l1);
                supplier.placeVertex(vertexConsumer, last, builder, p1.x(), size, p1.z(), builder.r2, builder.g2, builder.b2, builder.a2, textureU, textureVN, builder.l2);
                supplier.placeVertex(vertexConsumer, last, builder, p0.x(), size, p0.z(), builder.r2, builder.g2, builder.b2, builder.a2, textureUN, textureVN, builder.l2);
            }

            if(floor){
                if(builder.firstSide){
                    supplier.placeVertex(vertexConsumer, last, builder, 0, 0.1f, 0, builder.r2, builder.g2, builder.b2, builder.a2, textureU, textureVN, builder.l2);
                    supplier.placeVertex(vertexConsumer, last, builder, 0, 0.1f, 0, builder.r2, builder.g2, builder.b2, builder.a2, textureUN, textureVN, builder.l2);
                    supplier.placeVertex(vertexConsumer, last, builder, p1.x(), 0, p1.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureUN, textureV, builder.l1);
                    supplier.placeVertex(vertexConsumer, last, builder, p0.x(), 0, p0.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureU, textureV, builder.l1);
                }

                if(builder.secondSide){
                    supplier.placeVertex(vertexConsumer, last, builder, p0.x(), 0, p0.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureUN, textureV, builder.l1);
                    supplier.placeVertex(vertexConsumer, last, builder, p1.x(), 0, p1.z(), builder.r1, builder.g1, builder.b1, builder.a1, textureU, textureV, builder.l1);
                    supplier.placeVertex(vertexConsumer, last, builder, 0, 0, 0, builder.r2, builder.g2, builder.b2, builder.a2, textureU, textureVN, builder.l2);
                    supplier.placeVertex(vertexConsumer, last, builder, 0, 0, 0, builder.r2, builder.g2, builder.b2, builder.a2, textureUN, textureVN, builder.l2);
                }
            }
        }
    }

    /**
     * Dimensions xSize, ySize, zSize are specified in pixels
     */
    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize){
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        PoseStack posestack = RenderSystem.getModelViewStack();
        BakedModel model = renderer.getModel(stack, null, null, 0);

        posestack.pushPose();
        posestack.translate(x, y, 100.0F);
        posestack.translate((double)xSize / 2, (double)ySize / 2, 0.0D);
        posestack.scale(xSize, -ySize, zSize);

        RenderSystem.applyModelViewMatrix();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        if(!model.usesBlockLight()){
            Lighting.setupForFlatItems();
        }else{
            Lighting.setupFor3DItems();
        }

        renderer.render(stack, ItemDisplayContext.GUI, false, new PoseStack(), buffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        buffer.endBatch();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}