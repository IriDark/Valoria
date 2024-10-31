package com.idark.valoria.client.ui.bossbars;

import com.idark.valoria.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;

import java.util.*;

public class Bossbar{
    public static Map<String, Bossbar> bossbars = new HashMap<>();
    static{
        bossbars.put("Necromancer", new Bossbar(new ResourceLocation(Valoria.ID, "textures/gui/bossbars/necromancer.png")));
    }

    private final ResourceLocation texture;
    public Bossbar(ResourceLocation tex) {
        this.texture = tex;
    }

    public ResourceLocation getTexture(){
        return texture;
    }

    public void render(Bossbar boss, LerpingBossEvent event, int screenWidth, GuiGraphics pGuiGraphics, int offset) {
        int yOffset = offset + 6;
        int xOffset = screenWidth / 2 - 91;
        drawBar(boss.getTexture(), pGuiGraphics, xOffset, yOffset, event);
    }

    public void renderTitled(Bossbar boss, LerpingBossEvent event, int screenWidth, GuiGraphics pGuiGraphics, Minecraft mc, int offset) {
        int yOffset = offset + 6;
        int xOffset = screenWidth / 2 - 91;
        drawBar(boss.getTexture(), pGuiGraphics, xOffset, yOffset, event);
    }

    private void drawBar(ResourceLocation texture, GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent){
        Minecraft.getInstance().getProfiler().push("BossBar");
        pGuiGraphics.blit(texture, pX, pY, 0, 0, 183, 24, 256, 64);
        int i = (int)(pBossEvent.getProgress() * 177.0F);
        if(i > 0){
            if(pBossEvent.getOverlay() == BossEvent.BossBarOverlay.PROGRESS){
                RenderSystem.enableBlend();
                pGuiGraphics.blit(texture, pX + 3, pY + 14, 3, 30, i, 4, 256, 64);
                RenderSystem.disableBlend();
            }
        }
    }
}