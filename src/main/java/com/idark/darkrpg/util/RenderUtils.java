package com.idark.darkrpg.util;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static final RenderState.TransparencyState ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderState.TransparencyState NORMAL_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static RenderType GLOWING_PARTICLE = RenderType.create(
        DarkRPG.MOD_ID + ":glowing_particle",
        DefaultVertexFormats.PARTICLE,
        GL11.GL_QUADS, 256,
                RenderType.State.builder()
                .setShadeModelState(new RenderState.ShadeModelState(true))
                .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                .setLightmapState(new RenderState.LightmapState(false))
                .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setTextureState(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES, false, false))
                .createCompositeState(false));

    public static void renderItemModelInGui(ItemStack stack, int x, int y, int xSize, int ySize, int zSize) {
        Minecraft mc = Minecraft.getInstance();

        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)x+(xSize/2), (float)y+(ySize/2), 10.0F);
        RenderSystem.scalef((float)xSize/16, (float)ySize/16, (float)zSize/16);
        RenderSystem.translatef((float)-(x+(xSize/2)), (float)-(y+(ySize/2)), 0.0F);
        mc.getItemRenderer().renderAndDecorateItem(mc.player, stack, x, y);
        RenderSystem.popMatrix();
    }
}