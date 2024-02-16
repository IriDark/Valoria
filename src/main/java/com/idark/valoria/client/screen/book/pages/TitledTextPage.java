package com.idark.valoria.client.screen.book.pages;

import com.idark.valoria.client.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledTextPage extends Page {
    public String text, title;

    public TitledTextPage(String textKey) {
        this.text = textKey;
        this.title = textKey + ".name";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;
        int titleWidth = font.width(I18n.get(this.title));

        int xOffset = (115 - titleWidth) / 2;
        drawText(gui, I18n.get(this.title), x + xOffset, y + 22 - Minecraft.getInstance().font.lineHeight, false);
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
    }
}