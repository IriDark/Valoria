package com.idark.valoria.registries.item.component;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.api.*;

@OnlyIn(Dist.CLIENT)
public class LineSeparatorClientComponent implements ClientTooltipComponent{
    public static ClientTooltipComponent create() {
        return new LineSeparatorClientComponent();
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
        int lineY = pY + (pFont.lineHeight / 2) - 1;
        pGuiGraphics.fill(pX, lineY, pX + TooltipTracker.getWidth() - 6, lineY + 1, -1);
    }
}
