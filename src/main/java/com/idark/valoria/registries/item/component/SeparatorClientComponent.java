package com.idark.valoria.registries.item.component;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.network.chat.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.*;

@OnlyIn(Dist.CLIENT)
public class SeparatorClientComponent implements ClientTooltipComponent{
    Component component;
    public SeparatorClientComponent(Component component) {
        this.component = component;
    }

    public static ClientTooltipComponent create(Component component) {
        return new SeparatorClientComponent(component);
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
        int textWidth = pFont.width(component);
        int padding = 6;

        int lineTotalWidth = TooltipTracker.getWidth() - textWidth - (padding * 2);
        int lineSegmentWidth = lineTotalWidth / 2;

        int lineY = pY + (pFont.lineHeight / 2) - 1;
        pGuiGraphics.fill(pX, lineY, pX + lineSegmentWidth, lineY + 1, -1);
        pGuiGraphics.drawString(pFont, component, pX + lineSegmentWidth + padding, pY, -1, false);
        int rightLineX = pX + lineSegmentWidth + padding + textWidth + padding;
        pGuiGraphics.fill(rightLineX, lineY, rightLineX + lineSegmentWidth, lineY + 1, -1);
    }
}
