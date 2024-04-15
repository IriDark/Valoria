package com.idark.valoria.client.gui.screen.book.pages;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.gui.screen.book.LexiconGui;
import com.idark.valoria.client.gui.screen.book.Page;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextPage extends Page {
    public String text, title;
    private boolean hasTitle = true;
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Valoria.MOD_ID, "textures/gui/book/lexicon.png");
    public ItemStack result;
    public ItemStack[] inputs;
    private boolean hasRecipe = false;

    public TextPage(String textKey) {
        this.text = textKey;
        this.title = textKey + ".name";
    }

    public TextPage hideTitle() {
        hasTitle = false;
        return this;
    }

    public TextPage withCustomTitle(String title) {
        this.title = title;
        return this;
    }

    public TextPage withCraftEntry(ItemStack result, ItemStack... inputs) {
        this.result = result;
        this.inputs = inputs;
        this.hasRecipe = true;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        if(hasTitle) {
            drawText(gui, I18n.get(this.title), x + (115 - Minecraft.getInstance().font.width(I18n.get(this.title))) / 2, y + 22 - Minecraft.getInstance().font.lineHeight, false);
        }

        drawWrappingText(gui, I18n.get(text), x + 4, y + 35, 120, false);
        if(hasRecipe) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int index = i * 3 + j;
                    if (index < inputs.length && !inputs[index].isEmpty())
                        LexiconGui.drawItemWithTooltip(inputs[index], x + 22 + j * 18, y + 38 + i * 18 + 50, gui, mouseX, mouseY, true);

                    gui.blit(BACKGROUND, x + 21 + j * 18, y + 37 + i * 18 + 50, 287, 15, 18, 18, 512, 512);
                }
            }

            gui.blit(BACKGROUND, x + 88, y + 56 + 50, 287, 15, 18, 18, 512, 512);
            LexiconGui.drawItemWithTooltip(result, x + 89, y + 57 + 50, gui, mouseX, mouseY, true);
            resultArrow(gui, x, y);
        }
    }

    /**
     * Renders an Result ItemStack Arrow Near.
     */
    public void resultArrow(GuiGraphics gui, int x, int y) {
        gui.blit(BACKGROUND, x + 77, y + 111, 306, 15, 9, 7, 512, 512);
    }
}