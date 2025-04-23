package com.idark.valoria.client.ui.screen.book;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.language.*;
import net.minecraft.network.chat.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public abstract class Page{

    @OnlyIn(Dist.CLIENT)
    public static void drawText(GuiGraphics gui, String text, int x, int y, boolean Centered){
        Font font = Minecraft.getInstance().font;
        if(!Centered){
            gui.drawString(font, I18n.get(text), x, y, Col.packColor(255, 220, 200, 180), true);
        }else{
            gui.drawCenteredString(font, I18n.get(text), x, y, Col.packColor(255, 220, 200, 180));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawWrappingText(GuiGraphics gui, String string, int x, int y, int wrap, boolean centered) {
        Font font = Minecraft.getInstance().font;
        List<Component> lines = DotTextParser.parse(string, wrap, font);
        for (int i = 0; i < lines.size(); i++) {
            Component lineText = lines.get(i);
            int drawX = centered ? x + (wrap - font.width(lineText)) / 2 : x;
            gui.drawString(font, lineText, drawX, y + i * (font.lineHeight + 1), Col.packColor(255, 220, 200, 180), true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void tick(){
    }

    @OnlyIn(Dist.CLIENT)
    public void fullRender(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
        render(gui, x, y, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY){
    }
}