package com.idark.valoria.client.render.gui.book.pages;

import com.idark.valoria.client.render.gui.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledTextPage extends Page {
    public String text, title;
    public boolean xBonus;

    public TitledTextPage(String textKey, boolean xBonus) {
        this.text = textKey;
        this.title = textKey + ".name";
        this.xBonus = xBonus;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(gui, I18n.get(this.title), xBonus ? x + 142 - titleWidth / 2 : x + 123 - titleWidth / 2, y + 22 - Minecraft.getInstance().font.lineHeight, true);
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
    }
}