package com.idark.valoria.client.render.gui.book.newbook;

import com.idark.valoria.Valoria;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Bookmark {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");
    private final Item item;
    private final int x;
    private final int y;
    public Chapter chapter;

    private final String translate;

    public Bookmark(Item item, String pTranslate, int x, int y, Chapter chapter) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.translate = pTranslate;
        this.chapter = chapter;
    }

    public void render(GuiGraphics gui, int guiLeft, int guiTop, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 287, 15, 18, 18, 512, 512);
        if (!item.getDefaultInstance().isEmpty()) {
            gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 0, 193, 35, 25, 512, 512);
            gui.renderItem(item.getDefaultInstance(), guiLeft + x + 2, guiTop + y + 4);
            if (mouseX >= guiLeft + x + 2 && mouseX < guiLeft + x + 35 && mouseY >= guiTop + y + 4 && mouseY < guiTop + y + 25) {
                gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 0, 218, 35, 25, 512, 512);
                if (ShowTooltip) {
                    renderTooltip(gui, Component.translatable(translate), guiLeft + x + 10, guiTop + y + 20);
                }
            }
        }
    }

    public void renderTooltip(GuiGraphics gui, MutableComponent component, int x, int y) {
        gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
    }
}
