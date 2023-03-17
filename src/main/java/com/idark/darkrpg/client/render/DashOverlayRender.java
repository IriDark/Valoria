package com.idark.darkrpg.client.render;

import com.idark.darkrpg.DarkRPG;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
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

            if (ticks < 5) {
                alpha = ticks / 5;
            }
            if (ticks >= 20) {
                alpha = 1F - ((ticks - 20) / 15);
            }

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1F, 1F, 1F, alpha);

            mc.textureManager.bindTexture(new ResourceLocation(DarkRPG.MOD_ID + ":textures/speedlines.png"));
            AbstractGui.blit(ms, 0, 0, 0, 0, event.getWindow().getWidth()/2, event.getWindow().getHeight()/2, event.getWindow().getWidth()/2, event.getWindow().getHeight()/2);

            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
        }
    }
}