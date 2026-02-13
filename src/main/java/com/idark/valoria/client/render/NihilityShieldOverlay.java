package com.idark.valoria.client.render;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.gui.overlay.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.common.config.*;

@OnlyIn(Dist.CLIENT)
public class NihilityShieldOverlay implements IGuiOverlay{
    public static final NihilityShieldOverlay instance = new NihilityShieldOverlay();
    private final ResourceLocation location = Valoria.loc("textures/gui/overlay/nihility_shield.png");

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

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight){
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (ClientConfig.ABILITY_OVERLAY.get()) {
            if(!player.hasEffect(EffectsRegistry.NIHILITY_PROTECTION.get())) return;

            float time = (ClientTick.ticksInGame + partialTick) * 0.15F;
            float alphaBase = (float)Math.sin(time) * 0.5F + 0.5F;
            float scaleBase = (float)Math.cos(time) * 0.5F + 0.5F;
            float alpha = 0.2F + (alphaBase * 0.15F);
            float scale = 1.0F + (scaleBase * 0.0025F);
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();

            pose.translate(screenWidth / 2.0F, screenHeight / 2.0F, 0);
            pose.scale(scale, scale, 1.0F);
            pose.translate(-screenWidth / 2.0F, -screenHeight / 2.0F, 0);

            renderOverlay(guiGraphics, alpha, screenWidth, screenHeight);
            pose.popPose();
        }
    }
}