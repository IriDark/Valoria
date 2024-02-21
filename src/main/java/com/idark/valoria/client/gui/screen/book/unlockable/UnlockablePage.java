package com.idark.valoria.client.gui.screen.book.unlockable;

import com.idark.valoria.client.gui.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class UnlockablePage extends Page {
    UnlockableEntry[] entries;

    public UnlockablePage(UnlockableEntry... pages) {
        this.entries = pages;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) {
            if (entries[i].isUnlocked()) {
                gui.renderItem(entries[i].icon,x + 3, y + 8 + i * 20);
                gui.renderItemDecorations(Minecraft.getInstance().font, entries[i].icon,x + 3, y + 8 + i * 20, null);
                drawText(gui, I18n.get(entries[i].chapter.titleKey), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight, false);
            } else {
                //gui.blit(new ResourceLocation(Valoria.MOD_ID, ""), x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                drawText(gui, I18n.get("gui.valoria.unknown"), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight, false);
            }
        }
    }
}