package com.idark.valoria.client.render.gui.book;

import com.idark.valoria.Valoria;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CraftEntry {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");
    private final ItemStack item;
    private final int x;
    private final int y;

    public CraftEntry(ItemStack item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }


    /**
     *  Rendering an ItemStack with slot, not rendering anything when ItemStack is Items.AIR to optimize rendering
     */
    public void render(GuiGraphics gui, int guiLeft, int guiTop, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 287, 15, 18, 18, 512, 512);
        if (!item.isEmpty()) {
            LexiconGui.drawItemWithTooltip(item, guiLeft + x + 1, guiTop + y + 1, gui, mouseX, mouseY, ShowTooltip);
        }
    }

    /**
     *  Renders an Result ItemStack Arrow Near.
     */
    public void resultArrow(GuiGraphics gui, int guiLeft, int guiTop,int x,int y, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 306, 15, 9, 7, 512, 512);
    }
}