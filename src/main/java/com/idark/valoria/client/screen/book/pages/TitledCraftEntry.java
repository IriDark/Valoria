package com.idark.valoria.client.screen.book.pages;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.screen.book.LexiconGui;
import com.idark.valoria.client.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TitledCraftEntry extends Page {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");
    public ItemStack result;
    public ItemStack[] inputs;
    public String text, title;

    public TitledCraftEntry(String textKey, ItemStack result, ItemStack... inputs) {
        this.text = textKey;
        this.title = textKey + ".name";
        this.result = result;
        this.inputs = inputs;
    }

    /**
     *  Rendering an ItemStack with slot
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;
        int titleWidth = font.width(I18n.get(this.title));

        int xOffset = (125 - titleWidth) / 2;
        gui.blit(BACKGROUND, x + 40, y + 25, 97, 180, 38, 13, 512, 512);
        drawText(gui, I18n.get(this.title), x + xOffset, y + 22 - Minecraft.getInstance().font.lineHeight, false);
        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty())
                    LexiconGui.drawItemWithTooltip(inputs[index],  x + 22 + j * 18, y + 38 + i * 18 + 50, gui, mouseX, mouseY, true);

                gui.blit(BACKGROUND, x + 21 + j * 18, y + 37 + i * 18 + 50, 287, 15, 18, 18, 512, 512);
            }
        }

        gui.blit(BACKGROUND, x + 88, y + 56 + 50, 287, 15, 18, 18, 512, 512);
        LexiconGui.drawItemWithTooltip(result,x + 89, y + 57 + 50, gui, mouseX, mouseY, true);
        resultArrow(gui, x, y);
    }

    /**
     *  Renders an Result ItemStack Arrow Near.
     */
    public void resultArrow(GuiGraphics gui, int x,int y) {
        gui.blit(BACKGROUND, x + 77, y + 61 + 50, 306, 15, 9, 7, 512, 512);
    }
}
