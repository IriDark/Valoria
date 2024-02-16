package com.idark.valoria.client.screen.book.pages;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledTextPage extends Page {
    public String text, title;
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");

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
        //gui.blit(BACKGROUND, x + 39, y + 25, 97, 180, 38, 13, 512, 512);
        drawText(gui, I18n.get(this.title), x + xOffset, y + 22 - Minecraft.getInstance().font.lineHeight, false);
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
    }
}