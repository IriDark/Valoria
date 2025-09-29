package com.idark.valoria.client.ui;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.util.math.*;

@OnlyIn(Dist.CLIENT)
public class NihilityMeterRender extends Gui{
    private final long DISPLAY_DURATION = 100L;
    public final ResourceLocation TEXTURE = new ResourceLocation(Valoria.ID, "textures/gui/nihility_meter.png");
    float animTime = 15;
    private boolean isHiding = false;
    private long counter = DISPLAY_DURATION;
    private static float previousAmount = 0F;
    private static float actualPreviousAmount = 0f;

    public NihilityMeterRender(){
        super(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGuiEvent.Post event){
        if(minecraft.level == null) return;
        GuiGraphics gui = event.getGuiGraphics();
        Player player = Minecraft.getInstance().player;
        if(player == null || !ValoriaUtils.isEquippedCurio(m -> m.is(ItemsRegistry.nihilityMonitor.get()), player)) return;
        Minecraft.getInstance().player.getCapability(INihilityLevel.INSTANCE).ifPresent((n) -> {
            float clientAmount = n.getAmount(true);
            boolean alwaysShown = ClientConfig.NIHILITY_METER_ALWAYS_VISIBLE.get() || clientAmount > 0;
            if(alwaysShown){
                actualPreviousAmount = previousAmount;
                previousAmount = clientAmount;
                counter = ClientTick.ticksInGame;
                isHiding = true;
            }

            if(!alwaysShown){
                if(clientAmount != previousAmount){
                    actualPreviousAmount = previousAmount;
                    previousAmount = clientAmount;
                    if(ClientTick.ticksInGame > counter + DISPLAY_DURATION){
                        counter = ClientTick.ticksInGame;
                        isHiding = false;
                    }
                }else if(ClientTick.ticksInGame > counter + DISPLAY_DURATION && !isHiding){
                    counter = ClientTick.ticksInGame;
                    isHiding = true;
                }
            }

            render(gui, n.getMaxAmount(player, true), clientAmount);
        });
    }

    private void render(GuiGraphics gui, float maxProgress, float currentProgress){
        gui.pose().pushPose();
        gui.pose().translate(0, 0, 300);
        int windowHeight = minecraft.getWindow().getGuiScaledHeight();
        int windowWidth = minecraft.getWindow().getGuiScaledWidth();
        int xMargin = windowWidth - ClientConfig.NIHILITY_METER_X.get();
        int yMargin = ClientConfig.NIHILITY_METER_Y.get();

        int fillHeight = 45;
        int yCord = windowHeight - yMargin - fillHeight;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        float a = getScale();
        if(ClientConfig.NIHILITY_METER_ANIMATE.get()) animate(gui, xMargin, yCord);
        int progress = (int) ((currentProgress / maxProgress) * fillHeight);
        int prevProgress = (int) ((actualPreviousAmount / maxProgress) * fillHeight);

        RenderSystem.setShaderColor(1f,1f,1f,a);
        gui.blit(TEXTURE, xMargin, yCord, getuOffset(progress), 0, 20, 59, 128, 128); // base
        if (progress >= 0) {
            float elapsed = ClientTick.ticksInGame - counter + Minecraft.getInstance().getPartialTick();
            float progress2 = Mathf.clamp((elapsed - 20f) / Math.abs(progress-prevProgress) / 1.5f);
            if(isHiding) progress2 = 1f;

            progress = (int)Mathf.lerp(prevProgress, progress, progress2);
            int vOffset = 64 + (fillHeight - progress);
            int yOffset = yCord + 6 + (fillHeight - progress);
            int vOffset2 = 64 + (fillHeight - prevProgress);
            int yOffset2 = yCord + 6 + (fillHeight - prevProgress);
            if(prevProgress < progress) {
                RenderSystem.setShaderColor(1f,1f,1f,a);
                gui.blit(TEXTURE, xMargin + 7, yOffset, 0, vOffset, 6, progress, 128, 128);
                RenderSystem.setShaderColor(1.25f,1.25f,1.25f,a);
                gui.blit(TEXTURE, xMargin + 7, yOffset2, 0, vOffset2, 6, prevProgress, 128, 128);
                RenderSystem.setShaderColor(1f,1f,1f,a);
            } else{
                RenderSystem.setShaderColor(0.15f,0.15f,0.15f,a);
                gui.blit(TEXTURE, xMargin + 7, yOffset2, 0, vOffset2, 6, prevProgress, 128, 128);
                RenderSystem.setShaderColor(1f,1f,1f,a);
                gui.blit(TEXTURE, xMargin + 7, yOffset, 0, vOffset, 6, progress, 128, 128);
            }

            float percentage = (float)progress / fillHeight * 100;
            int color = getColor(percentage);
            RenderSystem.setShaderColor(1f,1f,1f,a);
            gui.drawCenteredString(minecraft.font, getPercentageText(percentage), xMargin + 11, yCord + 62, color);
        }

        gui.pose().popPose();

        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.disableBlend();
    }

    private float getScale() {
        float timeElapsed = ClientTick.ticksInGame - counter + Minecraft.getInstance().getPartialTick();
        float animationProgress = Mth.clamp(timeElapsed / animTime, 0.0F, 1.0F);
        Interp interp = Interp.smoother;
        float scale = interp.apply(animationProgress);
        if (isHiding) {
            scale = 1.0F - scale;
        }
        return scale;
    }

    private void animate(GuiGraphics gui, int xMargin, int yCord){
        float timeElapsed = ClientTick.ticksInGame - counter + Minecraft.getInstance().getPartialTick();
        float animationProgress = Mth.clamp(timeElapsed / animTime, 0.0F, 1.0F);
        Interp interp = Interp.smoother;
        float scale = interp.apply(animationProgress);
        if (isHiding) {
            scale = 1.0F - scale;
        }

        gui.pose().translate(0, 10 * Interp.smoother.apply(1f-scale),0);

        RenderSystem.setShaderColor(scale,scale,scale,scale);
        gui.pose().translate(xMargin + 10, yCord + 29.5, 0);
        gui.pose().scale(1f+(1f-scale)*0.25f, scale*scale, 1.0f);
        gui.pose().translate(-(xMargin + 10), -(yCord + 29.5), 0);
    }

    private static @NotNull String getPercentageText(float percentage){
        String percentageText;
        if (percentage < 10) {
            percentageText = String.format("%.1f%%", percentage);
        } else {
            percentageText = String.format("%.0f%%", percentage);
        }

        return percentageText;
    }

    private static int getColor(float percentage){
        int color = 0xFFFFFFFF;
        if (percentage < 25) {
            color = 0xFF00FF00;
        } else if (percentage < 75) {
            color = 0xFFFFFF00;
        } else {
            color = 0xFFFF0000;
        }

        return color;
    }

    private static int getuOffset(int progress){
        int uOffset = 0;
        if (progress >= 75) {
            uOffset = 62;
        } else if (progress >= 45) {
            uOffset = 30;
        }

        return uOffset;
    }
}