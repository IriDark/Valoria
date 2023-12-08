package com.idark.darkrpg.client.render.gui.book;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CraftEntry {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRPG.MOD_ID, "textures/gui/book/lexicon.png");
    private final Item item;
    private final int x;
    private final int y;

    public CraftEntry(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }


    /**
     *  Rendering an ItemStack with slot, not rendering anything when ItemStack is Items.AIR to optimize rendering
     */
    public void render(GuiGraphics gui, int guiLeft, int guiTop, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 287 * 2, 15 * 2, 36, 36, 1024, 1024);
        if (!new ItemStack(item).isEmpty()) {
            LexiconGui.drawItemWithTooltip(new ItemStack(item), 32, guiLeft + x + 2, guiTop + y + 2, gui, mouseX, mouseY, ShowTooltip);
        }
    }

    /**
     *  Renders an Result ItemStack Arrow Near.
     */
    public void resultArrow(GuiGraphics gui, int guiLeft, int guiTop,int x,int y, int mouseX, int mouseY, boolean ShowTooltip) {
        gui.blit(BACKGROUND, guiLeft + x, guiTop + y, 306 * 2, 15 * 2, 36, 36, 1024, 1024);
    }
}