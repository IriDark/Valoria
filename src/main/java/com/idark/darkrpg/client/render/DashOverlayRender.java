package com.idark.darkrpg.client.render;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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
    //TODO: FIX THIS
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event) {
        if (dashTime > 0) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack ms = event.getGuiGraphics().pose();
            float ticks = dashTime + event.getPartialTick();
            float alpha = 1F;

            if (ticks < 10) {
                alpha = 10 /ticks;
            }
            if (ticks >= 20) {
                alpha = 1F - ((ticks - 20) / 15);
            }

            int i = mc.getWindow().getWidth();
            int j = mc.getWindow().getHeight();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            mc.getTextureManager().bindForSetup(DASH);
            event.getGuiGraphics().blit(DASH, i, j, 0, 0, i, j);
            RenderSystem.disableBlend();
        }
    }
}