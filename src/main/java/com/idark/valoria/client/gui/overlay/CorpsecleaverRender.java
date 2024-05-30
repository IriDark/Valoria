package com.idark.valoria.client.gui.overlay;

import com.idark.valoria.*;
import com.idark.valoria.core.config.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.*;

@OnlyIn(Dist.CLIENT)
public class CorpsecleaverRender{

    private CorpsecleaverRender(){
    }

    public static boolean isThrow = false;
    public static int ThrowTime = 0;

    public static void tick(TickEvent.ClientTickEvent event){
        if(isThrow){
            if(ThrowTime < 35){
                ThrowTime = ThrowTime + 1;
            }else{
                ThrowTime = 0;
                isThrow = false;
            }
        }
    }

    public static void showOverlay(Player playerEntity){
        if(ClientConfig.BLOOD_OVERLAY.get() || !playerEntity.isCreative()){
            CorpsecleaverRender.isThrow = true;
        }
    }

    private static final ResourceLocation BLOOD = new ResourceLocation(Valoria.ID, "textures/gui/overlay/blood.png");

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event){
        if(ThrowTime > 0){
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics gui = event.getGuiGraphics();

            gui.pose().pushPose();
            gui.pose().translate(0, 0, -200);
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();
            float f = 0.1F;

            float ticks = ThrowTime + event.getPartialTick();
            float alpha = 0.5F;

            if(ticks < 10){
                alpha = 5 / ticks;
            }

            if(ticks >= 20){
                alpha = 0.1F - ((ticks - 20) / 15);
            }

            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            gui.setColor(f * alpha, f * alpha, f * alpha, 1.0F);
            gui.blit(BLOOD, 0, 0, 0, 0, 1920, 1080, width, height);
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
