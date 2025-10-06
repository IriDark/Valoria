package com.idark.valoria.client.ui;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.common.config.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class BasicBossbar extends ClientBossbar{
    public int fadeTime = 0;
    public float shakeSpeed = 1.5f;
    public float shakeThreshold = 0.25f;
    public float maxOffset = 1f;
    public Col color = Col.white.copy();

    public BasicBossbar(UUID uuid, Component component){
        super(uuid, component);
    }

    @Override
    public TridotBossBar setHealth(float health, float maxHealth){
        return super.setHealth(health, maxHealth);
    }

    public void render(BossBarsOverlay overlay, GuiGraphics pGuiGraphics, int baseOffset, Minecraft mc){
        int screenWidth = pGuiGraphics.guiWidth();
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        calculateShake(poseStack);
        if(isAboutToDie() && fadeTime <= 100) {
            fadeTime++;
        }

        RenderSystem.enableBlend();
        float alpha = Mth.lerp(Interp.pow2Out.apply((float)fadeTime / 100), 1, 0);
        int argb = color.a(alpha + 0.1f).toARGB();
        pGuiGraphics.setColor(1, 1, 1, alpha);
        if(ClientConfig.BOSSBAR_TITLE.get()){
            this.increase(32);
            Component name = this.getName().copy().append(": " + (int)this.getHealth() + "/" + (int)this.getMaxHealth());
            renderBossbar(baseOffset, screenWidth, pGuiGraphics, mc);
            int nameX = (screenWidth / 2) - (mc.font.width(name) / 2);
            int nameY = baseOffset + 30;
            pGuiGraphics.drawString(mc.font, name, nameX, nameY, argb);
        }else{
            this.increase(26);
            renderBossbar(baseOffset, screenWidth, pGuiGraphics, mc);
        }

        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private void calculateShake(PoseStack poseStack){
        float shakeIntensity = 0.0F;
        if (percentage <= shakeThreshold) {
            shakeIntensity = 1.0F - (percentage / shakeThreshold);
            shakeIntensity = Math.max(0.0F, Math.min(1.0F, shakeIntensity));
        }

        if (shakeIntensity > 0.0F) {
            float offsetX = (float) Math.sin((ClientTick.ticksInGame * shakeSpeed) * 0.5) * maxOffset * shakeIntensity;
            float offsetY = (float) Math.sin((ClientTick.ticksInGame * shakeSpeed) * 0.7) * maxOffset * shakeIntensity;
            poseStack.translate(offsetX, offsetY, 0);
        }
    }

    public float getPercentage() {
        float time = Util.getMillis() - this.setTime;
        float delta = Mth.clamp(time / 200.0F, 0.0F, 1.0F);
        return Mth.lerp(Interp.sine.apply(delta), this.percentage, this.targetPercent);
    }

    public void renderBossbar(int baseOffset, int screenWidth, GuiGraphics pGuiGraphics, Minecraft mc){
        int yOffset = baseOffset + 6;
        int xOffset = screenWidth / 2 - 91;
        Minecraft.getInstance().getProfiler().push("BossBar");
        pGuiGraphics.blit(this.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
        int i = (int)(this.getPercentage() * 177.0F);
        if(i > 0){
            Col color = this.isRainbow() ? Col.rainbow(mc.level.getGameTime() / 1.5f) : this.getColor();
            pGuiGraphics.setColor(color.r, color.g, color.b, 1);
            pGuiGraphics.blit(this.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
        }

        pGuiGraphics.setColor(1, 1, 1, 1);
    }
}