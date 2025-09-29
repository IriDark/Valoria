package com.idark.valoria.client.ui;

import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.*;
import pro.komaru.tridot.api.render.bossbars.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.common.config.*;
import pro.komaru.tridot.util.*;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class BasicBossbar extends ClientBossbar{

    public BasicBossbar(UUID uuid, Component component){
        super(uuid, component);
    }

    public void render(BossBarsOverlay overlay, GuiGraphics pGuiGraphics, int baseOffset, Minecraft mc){
        int screenWidth = pGuiGraphics.guiWidth();
        if(ClientConfig.BOSSBAR_TITLE.get()){
            this.increase(32);
            Component name = this.getName().copy().append(": " + (int)this.getHealth() + "/" + this.getMaxHealth());
            renderBossbar(baseOffset, screenWidth, pGuiGraphics, mc);
            int nameX = (screenWidth / 2) - (mc.font.width(name) / 2);
            int nameY = baseOffset + 30;
            pGuiGraphics.drawString(mc.font, name, nameX, nameY, 16777215);
        }else{
            this.increase(26);
            renderBossbar(baseOffset, screenWidth, pGuiGraphics, mc);
        }
    }

    public void renderBossbar(int baseOffset, int screenWidth, GuiGraphics pGuiGraphics, Minecraft mc){
        int yOffset = baseOffset + 6;
        int xOffset = screenWidth / 2 - 91;
        Minecraft.getInstance().getProfiler().push("BossBar");
        pGuiGraphics.blit(this.getTexture(), xOffset, yOffset, 0, 0, 183, 24, 256, 64);
        int i = (int)(this.getPercentage() * 177.0F);
        if(i > 0){
            if(Objects.equals(this.getTexture(), new ResourceLocation(Tridot.ID, "textures/gui/bossbars/base.png"))){
                Col color = this.isRainbow() ? Col.rainbow(mc.level.getGameTime() / 1.5f) : this.getColor();
                pGuiGraphics.setColor(color.r, color.g, color.b, 1);
            }

            RenderSystem.enableBlend();
            pGuiGraphics.blit(this.getTexture(), xOffset + 3, yOffset + 14, 3, 30, i, 4, 256, 64);
            RenderSystem.disableBlend();
            pGuiGraphics.setColor(1, 1, 1, 1);
        }
    }
}