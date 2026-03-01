package com.idark.valoria.registries.item.ability.itemComponents;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.ability.AbilityHelper.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import org.joml.*;
import pro.komaru.tridot.api.*;

import java.lang.Math;
import java.util.*;

public class AbilityPaginationClientComponent implements ClientTooltipComponent{
    public final ResourceLocation bg = Valoria.loc("textures/gui/tooltips/ability_slot.png");
    private List<ActiveAbility> list;

    private final int totalPages;
    private final int currentPage;
    private final int iconSize = 32;
    private final int spacing = 2;
    private final int textMargin = 6;
    public static float smoothIndex = 0f;

    public AbilityPaginationClientComponent(AbilityPaginationComponent component) {
        this.list = component.list();
        this.totalPages = list.size();
        this.currentPage = component.currentPage();
    }

    @Override
    public int getHeight() {
        return iconSize + 10 + textMargin;
    }

    @Override
    public int getWidth(Font pFont) {
        return (iconSize * 4) + textMargin + (spacing * (4 - 1));
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        RenderSystem.enableBlend();
        float max = totalPages;
        float delta = (float) Math.IEEEremainder((float)currentPage - smoothIndex, max);
        smoothIndex += delta * 0.2f;

        if (smoothIndex < -0.5f) smoothIndex += max;
        if (smoothIndex >= max - 0.5f) smoothIndex -= max;

        int x = pX + 30;
        int tooltipWidth = getWidth(pFont);
        if(totalPages > 1){
            pGuiGraphics.blit(bg, x + 2, pY + 8, 5, 40, 9, 16, 64, 64);
            pGuiGraphics.blit(bg, x + tooltipWidth - 11, pY + 8, 18, 40, 9, 16, 64, 64);
        }

        pGuiGraphics.enableScissor(x + 12, pY, x + tooltipWidth - 12, pY + iconSize + 10);
        int centerX = x + (tooltipWidth / 2) - (iconSize / 2);
        for (int i = 0; i < totalPages; i++) {
            var element = list.get(i);
            float offset = (float) Math.IEEEremainder(i - smoothIndex, max);
            float drawX = centerX + (offset * 35f);

            float scale = 1.0f - (Math.abs(offset) * 0.25f);
            scale = Math.max(0.6f, scale);

            float color = 1.0f - (Math.abs(offset) * 0.4f);
            color = Math.max(0.4f, color);

            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(drawX + iconSize / 2f, pY + iconSize / 2f, 0);
            pGuiGraphics.pose().scale(scale, scale, 1.0f);
            pGuiGraphics.pose().translate(-(drawX + iconSize / 2f), -(pY + iconSize / 2f), 0);
            pGuiGraphics.setColor(color, color, color, 1.0F);

            int uOffset = (Math.abs(offset) < 0.5f) ? 0 : iconSize;
            pGuiGraphics.blit(bg, (int)drawX, pY, uOffset, 0, iconSize, iconSize, 64, 64);
            pGuiGraphics.blit(element.ability().icon, (int)drawX + 8, pY + 6, 0, 0, 18, 18, 18, 18);

            pGuiGraphics.pose().popPose();
        }

        pGuiGraphics.disableScissor();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        var activeElement = list.get(currentPage);
        var component = Component.literal("ability." + activeElement.ability().type.id.getNamespace() + "." + activeElement.ability().type.id.getPath());

        int textWidth = pFont.width(component);
        int padding = 6;

        int lineTotalWidth = TooltipTracker.getWidth() - textWidth - (padding * 2);
        int lineSegmentWidth = lineTotalWidth / 2;
        int lineY = pY + 35 + (pFont.lineHeight / 2) - 1;

        pGuiGraphics.fill(pX, lineY, pX + lineSegmentWidth, lineY + 1, -1);
        pGuiGraphics.drawString(pFont, component, pX + lineSegmentWidth + padding, pY + 35, -1, false);

        int rightLineX = pX + lineSegmentWidth + padding + textWidth + padding;
        pGuiGraphics.fill(rightLineX, lineY, rightLineX + lineSegmentWidth, lineY + 1, -1);

        RenderSystem.disableBlend();
    }

    @Override
    public void renderText(Font pFont, int pX, int pY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {

    }
}