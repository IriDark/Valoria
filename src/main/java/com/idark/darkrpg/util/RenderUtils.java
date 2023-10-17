package com.idark.darkrpg.util;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RenderUtils {

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

    public static RenderType GLOWING_PARTICLE = RenderType.create(
            DarkRPG.MOD_ID + ":glowing_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getGlowingParticleShader))
                    .createCompositeState(false));
    );

    public static RenderType DELAYED_PARTICLE = RenderType.create(
            DarkRPG.MOD_ID + ":delayed_particle",
            DefaultVertexFormat.PARTICLE,
            VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setWriteMaskState(new RenderStateShard.WriteMaskStateShard(true, false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_PARTICLES, false, false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(WizardsRebornClient::getSpriteParticleShader))
                    .createCompositeState(false));


    public static void renderItemModelInGui(ItemStack stack, int x, int y, float xSize, float ySize, float zSize) {
        BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(stack, (Level)null, (LivingEntity)null, 0);

        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(x, y, (100.0F));
        posestack.translate((double) xSize / 2, (double) ySize / 2, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(xSize, ySize, zSize);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedmodel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        RenderSystem.disableDepthTest();
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();

        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}