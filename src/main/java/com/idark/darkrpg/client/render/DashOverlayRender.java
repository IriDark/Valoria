package com.idark.darkrpg.client.render;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import org.lwjgl.opengl.GL11;

public class DashOverlayRender {

    private DashOverlayRender() {}

    public static int dashTime = 0;
    public static boolean isDash = false;

    public static void tick(TickEvent.ClientTickEvent event) {
        if (isDash) {
            if (dashTime < 35) {
                dashTime = dashTime + 1;
            } else {
                dashTime = 0;
                isDash = false;
            }
        }
    }

    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        if (dashTime > 0) {
            Minecraft mc = Minecraft.getInstance();
            MatrixStack ms = event.getMatrixStack();

            float ticks = dashTime + event.getPartialTicks();
            float alpha = 1F;

            if (ticks < 10) {
                alpha = 10 /ticks;
            }
            if (ticks >= 20) {
                alpha = 1F - ((ticks - 20) / 15);
            }

            int i = mc.getMainWindow().getScaledWidth();
            int j = mc.getMainWindow().getScaledHeight();
            double d0 = MathHelper.lerp((double)1D, 2.0D, 1.0D);
            float f = 0.05F * 1F;
            float f1 = 0.05F * 1F;
            float f2 = 0.05F * 1F;
            double d1 = (double)i * d0;
            double d2 = (double)j * d0;
            double d3 = ((double)i - d1) / 2.0D;
            double d4 = ((double)j - d2) / 2.0D;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.color4f(f, f1, f2, alpha);
            mc.getTextureManager().bindTexture(new ResourceLocation(DarkRPG.MOD_ID + ":textures/gui/speedlines.png"));
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(d3, d4 + d2, -90.0D).tex(0.0F, 1.0F).endVertex();
            bufferbuilder.pos(d3 + d1, d4 + d2, -90.0D).tex(1.0F, 1.0F).endVertex();
            bufferbuilder.pos(d3 + d1, d4, -90.0D).tex(1.0F, 0.0F).endVertex();
            bufferbuilder.pos(d3, d4, -90.0D).tex(0.0F, 0.0F).endVertex();
            tessellator.draw();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}