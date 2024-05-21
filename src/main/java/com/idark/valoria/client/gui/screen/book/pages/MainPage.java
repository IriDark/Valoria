package com.idark.valoria.client.gui.screen.book.pages;

import net.minecraft.client.gui.*;
import net.minecraftforge.api.distmarker.*;

// TODO
public class MainPage extends TextPage{
    public MainPage(String textKey){
        super(textKey);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
        super.render(gui, x, y, mouseX, mouseY);
        gui.blit(BACKGROUND, x + 9, y + 90, 150, 201, 103, 4, 512, 512);
    }
}
