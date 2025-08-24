package com.idark.valoria.registries.item.component;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.network.chat.*;
import net.minecraftforge.api.distmarker.*;

@OnlyIn(Dist.CLIENT)
public class AbilitiesSeparatorClientComponent implements ClientTooltipComponent{
    public static ClientTooltipComponent create() {
        return new AbilitiesSeparatorClientComponent();
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public int getWidth(Font pFont) {
        return 200;
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        var text = Component.translatable("tooltip.valoria.abilities");
        int textWidth = pFont.width(text);
        int padding = 6;

        int lineTotalWidth = this.getWidth(pFont) - textWidth - (padding * 2);
        int lineSegmentWidth = lineTotalWidth / 2;

        int lineY = pY + (pFont.lineHeight / 2) - 1;
        pGuiGraphics.fill(pX, lineY, pX + lineSegmentWidth, lineY + 1, -1);
        pGuiGraphics.drawString(pFont, text, pX + lineSegmentWidth + padding, pY, -1, false);
        int rightLineX = pX + lineSegmentWidth + padding + textWidth + padding;
        pGuiGraphics.fill(rightLineX, lineY, rightLineX + lineSegmentWidth, lineY + 1, -1);
    }
}
