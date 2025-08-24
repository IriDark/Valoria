package com.idark.valoria.registries.item.component;

import com.idark.valoria.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.client.renderer.*;
import net.minecraft.locale.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import org.joml.*;

import java.lang.Math;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public class AbilityClientComponent implements ClientTooltipComponent{
    private final ResourceLocation bg = Valoria.loc("textures/gui/tooltips/background.png");
    private final ResourceLocation icon;
    private final int maxChars = 200;
    private final int iconSize = 18;
    private final int iconMargin = 14;

    private final List<FormattedCharSequence> lines;
    private final int paddingTop;
    private AbilityClientComponent(MutableComponent text, ResourceLocation icon, int paddingTop) {
        this.lines = Language.getInstance().getVisualOrder(Minecraft.getInstance().font.getSplitter().splitLines(text, maxChars, text.getStyle()));
        this.paddingTop = paddingTop;
        this.icon = icon;
    }

    public static ClientTooltipComponent create(MutableComponent text, ResourceLocation icon) {
        return new AbilityClientComponent(text, icon, 12);
    }

    @Override
    public int getHeight() {
        return Math.max(iconSize + iconMargin, (12 * lines.size()) + paddingTop);
    }

    @Override
    public int getWidth(Font pFont) {
        int width = 0;
        for (final FormattedCharSequence line : lines) {
            float scale = 1;
            int lineWidth = iconSize + iconMargin + (int) (pFont.width(line) * scale);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }

        return width;
    }

    @Override
    public void renderText(Font pFont, int pMouseX, int pMouseY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {
        final int x = pMouseX + iconSize + 4;
        int y = pMouseY + paddingTop;
        for (final FormattedCharSequence line : lines) {
            float scale = 1;
            Matrix4f scaled = new Matrix4f(pMatrix);
            scaled.scale(scale, scale, 1);
            pFont.drawInBatch(line, x / scale, (y / scale) + 1, -1, true, scaled, pBufferSource, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
            y += 9;
        }
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        RenderSystem.enableBlend();
        pGuiGraphics.blit(bg, pX, pY + (paddingTop) - 1, 0, 0, iconSize, iconSize, iconSize, iconSize);
        pGuiGraphics.blit(icon, pX, pY + (paddingTop) - 1, 0, 0, iconSize, iconSize, iconSize, iconSize);
        RenderSystem.disableBlend();
    }
}
