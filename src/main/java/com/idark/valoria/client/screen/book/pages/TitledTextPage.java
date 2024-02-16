package com.idark.valoria.client.screen.book.pages;

import com.idark.valoria.client.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledTextPage extends Page {
    public String text, title;
    public int xBonus;
    public boolean pOperation;


    // Костыль бтв
    /**
     * @param textKey Text
     * @param xBonus offset
     * @param pOperation True = minus, False = plus
     */
    public TitledTextPage(String textKey, int xBonus, boolean pOperation) {
        this.text = textKey;
        this.title = textKey + ".name";
        this.xBonus = xBonus;
        this.pOperation = pOperation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(gui, I18n.get(this.title), pOperation ? x + 96  - titleWidth / 2 - xBonus : x + 96  - titleWidth / 2 + xBonus, y + 22 - Minecraft.getInstance().font.lineHeight, false);
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
    }
}