package com.idark.valoria.client.ui;

import com.idark.valoria.core.config.ClientConfig;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;

@OnlyIn(Dist.CLIENT)
public class OverlayRender{
    public static int showTick = 0;
    public static int showTime;
    public static boolean isUsed = false;
    private static ResourceLocation TEXTURE;

    public static void showOverlay(int showTime){
        if(ClientConfig.ABILITY_OVERLAY.get()){
            OverlayRender.isUsed = true;
            OverlayRender.showTime = showTime;
        }
    }

    public static void setOverlayTexture(ResourceLocation texture){
        OverlayRender.TEXTURE = texture;
    }

    public static void tick(TickEvent.ClientTickEvent event){
        if(isUsed){
            if(showTick < showTime){
                showTick = showTick + 1;
            }else{
                showTick = 0;
                isUsed = false;
            }
        }
    }

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        if(showTick > 0){
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics gui = event.getGuiGraphics();
            gui.pose().pushPose();
            gui.pose().translate(0, 0, -200);
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            float alpha = getAlpha(event);
            float f = 0.1F;

            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            gui.setColor(f * alpha, f * alpha, f * alpha, 1.0F);
            gui.blit(TEXTURE, 0, 0, 0, 0, 1920, 1080, width, height);
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();

            RenderSystem.applyModelViewMatrix();
            gui.pose().popPose();
        }
    }

    private static float getAlpha(RenderGuiOverlayEvent.Post event){
        float ticks = showTick + event.getPartialTick();
        float alpha = 1;
        if(ticks >= 20){
            float fadeStart = 20;
            float fadeEnd = fadeStart + showTime;
            if(ticks < fadeEnd){
                float progress = (ticks - fadeStart) / (fadeEnd - fadeStart) * 2;
                alpha = 1.0F - progress;
            }else{
                alpha = 0.0F;
            }
        }
        return alpha;
    }
}