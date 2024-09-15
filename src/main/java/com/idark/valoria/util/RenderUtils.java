package com.idark.valoria.util;

import com.idark.valoria.Valoria;
import com.idark.valoria.ValoriaClient;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.ModList;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RenderUtils {
    @OnlyIn(Dist.CLIENT)
    public static Matrix4f particleMVMatrix = null;

    @OnlyIn(Dist.CLIENT)
    public static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderStateShard.TransparencyStateShard NORMAL_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderType GLOWING = RenderType.create(Valoria.ID + ":glowing",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setShaderState(new RenderStateShard.ShaderStateShard(ValoriaClient::getGlowingShader))
                    .createCompositeState(false)
    );

    public static RenderType GLOWING_PARTICLE = RenderType.create(Valoria.ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(ValoriaClient::getGlowingParticleShader))
                    .createCompositeState(false));

    public static RenderType DELAYED_PARTICLE = RenderType.create(Valoria.ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(ValoriaClient::getSpriteParticleShader))
                    .createCompositeState(false));

    /**
     * This code belongs to its author, and licensed under GPL-2.0 license
     *
     * @author MaxBogomol
     */
    @Deprecated
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


    @Deprecated(forRemoval = true, since = "0.6b")
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
    @Deprecated(forRemoval = true, since = "0.6b")
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

    /**
     * This code belongs to its author, and licensed under GPL-2.0 license
     *
     * @author MaxBogomol
     */
    @Deprecated(forRemoval = true, since = "0.6b")
    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r, float g, float b, float a) {
        ray(mStack, buf, width, height, endOffset, r, g, b, a, r, g, b, a);
    }

    /**
     * This code belongs to its author, and licensed under GPL-2.0 license
     *
     * @author MaxBogomol
     */
    @Deprecated(forRemoval = true, since = "0.6b")
    public static void ray(PoseStack mStack, MultiBufferSource buf, float width, float height, float endOffset, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        VertexConsumer builder = buf.getBuffer(GLOWING);
        Matrix4f mat = mStack.last().pose();

        builder.vertex(mat, -width, width, -width).color(r1, g1, b1, a1).endVertex();
        builder.vertex(mat, height, width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, height, -width * endOffset, -width * endOffset).color(r2, g2, b2, a2).endVertex();
        builder.vertex(mat, -width, -width, -width).color(r1, g1, b1, a1).endVertex();
    }

    public static void afterLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (particleMVMatrix != null) {
                RenderSystem.getModelViewStack().mulPoseMatrix(particleMVMatrix);
            }

            RenderSystem.applyModelViewMatrix();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            getDelayedRender().endBatch(RenderUtils.DELAYED_PARTICLE);
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            getDelayedRender().endBatch(RenderUtils.GLOWING_PARTICLE);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            getDelayedRender().endBatch(RenderUtils.GLOWING);
        }
    }

    static MultiBufferSource.BufferSource DELAYED_RENDER = null;

    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    RenderUtils.DELAYED_PARTICLE,
                    RenderUtils.GLOWING_PARTICLE,
                    RenderUtils.GLOWING}) {
                buffers.put(type, new BufferBuilder(ModList.get().isLoaded("rubidium") ? 32768 : type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(128));
        }

        return DELAYED_RENDER;
    }
}