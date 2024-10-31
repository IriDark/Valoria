package com.idark.valoria.core.event;

import com.idark.valoria.*;
import com.idark.valoria.client.ui.bossbars.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.mixin.client.*;
import com.idark.valoria.core.proxy.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.components.*;
import net.minecraft.resources.*;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

import java.util.*;

@Mod.EventBusSubscriber(modid = Valoria.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents{
    private static final ResourceLocation VANILLA_LOC = new ResourceLocation("textures/gui/bars.png");
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBossInfoRender(CustomizeGuiOverlayEvent.BossEventProgress ev){
        Minecraft mc = Minecraft.getInstance();
        if(ev.isCanceled() || mc.level == null) return;
        Map<UUID, LerpingBossEvent> events = ((BossHealthOverlayAccessor) mc.gui.getBossOverlay()).getEvents();
        if (events.isEmpty()) return;
        GuiGraphics pGuiGraphics = ev.getGuiGraphics();
        int screenWidth = pGuiGraphics.guiWidth();
        int offset = 0;
        for (LerpingBossEvent event : events.values()){
            if(ClientProxy.bossbars.containsKey(ev.getBossEvent().getId())){
                String id = ClientProxy.bossbars.get(event.getId());
                Bossbar bossbar = Bossbar.bossbars.getOrDefault(id, null);
                if(bossbar == null){
                    ev.setIncrement(18);
                    drawVanillaBar(pGuiGraphics, screenWidth / 2 - 91, offset, event);
                    int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                    int nameY = offset + 16 - 9;
                    pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                }

                if (bossbar != null){
                    if(ClientConfig.BOSSBAR_TITLE.get()){
                        ev.setIncrement(32);
                        bossbar.renderTitled(bossbar, ev.getBossEvent(), screenWidth, pGuiGraphics, mc, offset);
                        int nameX = screenWidth / 2 - mc.font.width(event.getName()) / 2;
                        int nameY = offset + 30;
                        pGuiGraphics.drawString(mc.font, event.getName(), nameX, nameY, 16777215);
                    }else{
                        ev.setIncrement(26);
                        bossbar.render(bossbar, ev.getBossEvent(), screenWidth, pGuiGraphics, offset);
                    }
                }

                offset += ev.getIncrement();
                if(offset >= pGuiGraphics.guiHeight() / 4) break;
            }
        }

        ev.setCanceled(true);
    }

    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int offset, BossEvent pBossEvent){
        drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, 182, 0);
        int i = (int)(pBossEvent.getProgress() * 183.0F);
        if(i > 0){
            drawVanillaBar(pGuiGraphics, pX, offset + 16, pBossEvent, i, 5);
        }
    }

    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pWidth, int p_281636_){
        pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, pBossEvent.getColor().ordinal() * 5 * 2 + p_281636_, pWidth, 5);
        if(pBossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS){
            RenderSystem.enableBlend();
            pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, 80 + (pBossEvent.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, pWidth, 5);
            RenderSystem.disableBlend();
        }
    }
}