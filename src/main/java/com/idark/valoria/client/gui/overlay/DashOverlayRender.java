package com.idark.valoria.client.gui.overlay;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.*;

@OnlyIn(Dist.CLIENT)
public class DashOverlayRender{

    private DashOverlayRender(){
    }

    public static int dashTime = 0;
    public static boolean isDash = false;
    private static final ResourceLocation DASH = new ResourceLocation(Valoria.ID + ":textures/gui/overlay/speedlines.png");

    public static void showDashOverlay(){
        if(ClientConfig.DASH_OVERLAY.get()){
            DashOverlayRender.isDash = true;
        }
    }

    public static void tick(TickEvent.ClientTickEvent event){
        if(isDash){
            if(dashTime < 35){
                dashTime = dashTime + 1;
            }else{
                dashTime = 0;
                isDash = false;
            }
        }
    }

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        if(dashTime > 0){
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics gui = event.getGuiGraphics();
            gui.pose().pushPose();
            gui.pose().translate(0, 0, -200);
            float ticks = dashTime + event.getPartialTick();
            float alpha = 0.5F;

            if(ticks < 10){
                alpha = 5 / ticks;
            }

            if(ticks >= 20){
                alpha = 0.5F - ((ticks - 20) / 15);
            }

            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            float f = 0.1F;

            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            gui.setColor(f * alpha, f * alpha, f * alpha, 1.0F);
            gui.blit(DASH, 0, 0, 0, 0, 1920, 1080, width, height);
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();

            RenderSystem.applyModelViewMatrix();
            gui.pose().popPose();
        }
    }
}