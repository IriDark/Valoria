package com.idark.valoria.util;

import com.idark.valoria.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.awt.*;
import java.lang.Math;
import java.util.*;

public class RenderUtils{
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

    public static void renderAura(PoseStack mStack, VertexConsumer builder, float radius, float size, int longs, Color color1, Color color2, float alpha1, float alpha2, boolean renderSide, boolean renderFloor){
        float r1 = color1.getRed() / 255f;
        float g1 = color1.getGreen() / 255f;
        float b1 = color1.getBlue() / 255f;

        float r2 = color2.getRed() / 255f;
        float g2 = color2.getGreen() / 255f;
        float b2 = color2.getBlue() / 255f;

        float startU = 0;
        float endU = Mth.PI * 2;
        float stepU = (endU - startU) / longs;
        for(int i = 0; i < longs; ++i){
            float u = i * stepU + startU;
            float un = (i + 1 == longs) ? endU : (i + 1) * stepU + startU;
            auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
            auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);
            if(renderSide){
                auraPiece(mStack, builder, radius, 0, u, r1, g2, b1, alpha1);
                auraPiece(mStack, builder, radius, size, u, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, size, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, un, r1, g2, b1, alpha1);
            }

            if(renderFloor){
                auraPiece(mStack, builder, 0, 0, u, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);

                if(renderSide){
                    auraPiece(mStack, builder, 0, 0, un, r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, 0, 0, u, r2, g2, b2, alpha2);
                    auraPiece(mStack, builder, radius, 0, un, r1, g1, b1, alpha1);
                    auraPiece(mStack, builder, radius, 0, u, r1, g1, b1, alpha1);
                }
            }
        }
    }


    public static void auraPiece(PoseStack mStack, VertexConsumer builder, float radius, float size, float angle, float r, float g, float b, float alpha){
        mStack.pushPose();
        mStack.mulPose(Axis.YP.rotationDegrees((float)Math.toDegrees(angle)));
        mStack.translate(radius, 0, 0);
        Matrix4f mat = mStack.last().pose();
        builder.vertex(mat, 0, size, 0).color(r, g, b, alpha).endVertex();
        mStack.popPose();
    }

    /**
     * Dimensions xSize, ySize, zSize are specified in pixels
     * @apiNote this method conflicts with renderTooltip, so it's should be added separately to the item
     */
    public static void renderItemModelInGui(ItemStack stack, float x, float y, float xSize, float ySize, float zSize){
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(x, y, (100.0F));
        posestack.translate((double)xSize / 2, (double)ySize / 2, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(xSize, ySize, zSize);

        RenderSystem.applyModelViewMatrix();
        PoseStack pose = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if(flag){
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false, pose, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        RenderSystem.disableDepthTest();
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if(flag){
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static void afterLevelRender(RenderLevelStageEvent event){
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL){
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if(particleMVMatrix != null){
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

    public static MultiBufferSource.BufferSource getDelayedRender(){
        if(DELAYED_RENDER == null){
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for(RenderType type : new RenderType[]{
            RenderUtils.DELAYED_PARTICLE,
            RenderUtils.GLOWING_PARTICLE,
            RenderUtils.GLOWING}){
                buffers.put(type, new BufferBuilder(ModList.get().isLoaded("rubidium") ? 32768 : type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(128));
        }

        return DELAYED_RENDER;
    }
}