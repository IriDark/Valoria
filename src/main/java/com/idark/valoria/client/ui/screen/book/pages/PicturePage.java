package com.idark.valoria.client.ui.screen.book.pages;

import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraftforge.api.distmarker.*;

public class PicturePage extends TextPage{
    private final ResourceLocation TEX;
    private int xOffset = 0, yOffset = 0;
    public PicturePage(String textKey, ResourceLocation pic){
        super(textKey);
        this.TEX = pic;
    }

    public PicturePage(String textKey, ResourceLocation pic, int x, int y){
        super(textKey);
        this.TEX = pic;
        this.xOffset = x;
        this.yOffset = y;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
        super.render(gui, x, y, mouseX, mouseY);
        gui.blit(TEX, x + xOffset, y + yOffset, 0, 0, 128, 128, 128, 128);
    }
}
