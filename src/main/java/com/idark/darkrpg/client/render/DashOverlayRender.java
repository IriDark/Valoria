package com.idark.darkrpg.client.render;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraft.client.Minecraft;
import org.codehaus.plexus.util.dag.Vertex;
import org.lwjgl.opengl.GL11;

public class DashOverlayRender {

    private DashOverlayRender() {}

    public static int dashTime = 0;
    public static boolean isDash = false;
	private static final ResourceLocation DASH = new ResourceLocation(DarkRPG.MOD_ID + ":textures/gui/speedlines.png");

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

    //TODO: FIX ALPHA CHANNEL
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event) {
        if (dashTime > 0) {
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics gui = event.getGuiGraphics();
            float ticks = dashTime + event.getPartialTick();
            float alpha = 0.5F;

            if (ticks < 10) {
                alpha = 5 /ticks;
            }

            if (ticks >= 20) {
                alpha = 0.5F - ((ticks - 20) / 15);
            }

            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            float f = 0.05F;
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.depthMask(false);
            RenderSystem.clearColor(f, f, f, alpha);
            mc.textureManager.bindForSetup(DASH);
            gui.blit(DASH, 0, 0, 0, 0, 1920, 1080, width, height);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}