package com.idark.valoria.client.ui.screen.book;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.language.*;
import net.minecraftforge.api.distmarker.*;
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
    public static void drawWrappingText(GuiGraphics gui, String text, int x, int y, int wrap, boolean Centered){
        Font font = Minecraft.getInstance().font;
        List<String> lines = new ArrayList<>();
        String[] words = I18n.get(text).split(" ");
        String line = "";
        for(String s : words){
            if(s.equals("\n")){
                lines.add(line);
                line = "";
            }else if(font.width(line) + font.width(s) > wrap){
                lines.add(line);
                line = s + " ";
            }else line += s + " ";
        }
        if(!line.isEmpty()) lines.add(line);
        for(int i = 0; i < lines.size(); i++){
            drawText(gui, lines.get(i), x, y + i * (font.lineHeight + 1), Centered);
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