package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.gui.overlay.*;
import pro.komaru.tridot.common.config.*;

//todo cap fix
@OnlyIn(Dist.CLIENT)
public class NihilityHudOverlay implements IGuiOverlay{
    public static final NihilityHudOverlay instance = new NihilityHudOverlay();
    private final ResourceLocation location = Valoria.loc("textures/gui/overlay/corruption.png");

    private void renderOverlay(GuiGraphics gui, float alpha, int width, int height){
        gui.pose().pushPose();
        gui.pose().translate(0, 0, -200);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        gui.setColor(alpha, alpha, alpha, alpha);
        gui.blit(location, 0, 0, 0, 0, 1920, 1080, width, height);
        gui.setColor(1, 1, 1, 1);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        gui.pose().popPose();
    }

    private float getAlpha(float current, float max) {
        return Mth.clamp(current / max, 0.0f, 1.0f);
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight){
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (ClientConfig.ABILITY_OVERLAY.get()) {
            player.getCapability(NihilityLevelProvider.INSTANCE).ifPresent(nihility -> {
                float max = nihility.getMaxAmount(player);
                float amount = nihility.getAmount();
                float alpha = getAlpha(amount, max);
                renderOverlay(guiGraphics, alpha, screenWidth, screenHeight);
            });
        }
    }
}