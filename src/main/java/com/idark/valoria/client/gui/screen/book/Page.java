package com.idark.valoria.client.gui.screen.book;

import com.idark.valoria.util.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {

    public Page() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawText(GuiGraphics gui, String text, int x, int y, boolean Centered) {
        Font font = Minecraft.getInstance().font;
        if (!Centered) {
            gui.drawString(font, I18n.get(text), x, y, ColorUtils.packColor(255, 220, 200, 180), true);
        } else {
            gui.drawCenteredString(font, I18n.get(text), x, y, ColorUtils.packColor(255, 220, 200, 180));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(GuiGraphics gui, String text, int x, int y, int wrap, boolean Centered) {
        Font font = Minecraft.getInstance().font;
        List<String> lines = new ArrayList<>();
        String[] words = I18n.get(text).split(" ");
        String line = "";
        for (String s : words) {
            if (s.equals("\n")) {
                lines.add(line);
                line = "";
            } else if (font.width(line) + font.width(s) > wrap) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++) {
            drawText(gui, lines.get(i), x, y + i * (font.lineHeight + 1), Centered);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void tick() {
    }

    @OnlyIn(Dist.CLIENT)
    public void fullRender(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        render(gui, x, y, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
    }
}