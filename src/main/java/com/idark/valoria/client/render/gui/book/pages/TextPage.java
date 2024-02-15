package com.idark.valoria.client.render.gui.book.pages;

import com.idark.valoria.client.render.gui.book.Page;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextPage extends Page {
    public String text;

    public TextPage(String textKey) {
        this.text = textKey;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
    }
}