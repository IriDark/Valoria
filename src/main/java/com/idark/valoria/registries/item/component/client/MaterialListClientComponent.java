package com.idark.valoria.registries.item.component.client;

import com.idark.valoria.registries.item.recipe.WorkbenchRecipe.*;
import com.mojang.datafixers.util.*;
import net.minecraft.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.joml.*;
import pro.komaru.tridot.api.*;

import java.awt.*;
import java.util.List;

public class MaterialListClientComponent implements ClientTooltipComponent{
    private final List<Pair<Ingredient, RecipeData>> items;
    private final int iconSize = 16;
    private final int padding = 2;
    private int textCurrentY = 0;

    public MaterialListClientComponent(List<Pair<Ingredient, RecipeData>> items) {
        this.items = items;
    }

    public static MaterialListClientComponent create(List<Pair<Ingredient, RecipeData>> items) {
        return new MaterialListClientComponent(items);
    }

    @Override
    public int getHeight() {
        return (iconSize + padding) * items.size() + 24;
    }

    @Override
    public int getWidth(Font font) {
        int maxWidth = 0;
        for (var entry : items) {
            ItemStack stack = entry.getFirst().getItems()[0];
            int currentWidth = iconSize + padding + font.width(stack.getHoverName());
            if (entry.getSecond().count > 1) {
                currentWidth += font.width(Component.literal(" " + entry.getSecond().current + "/" + entry.getSecond().count).withStyle(ChatFormatting.GRAY));
            }

            if (currentWidth > maxWidth) {
                maxWidth = currentWidth + 32;
            }
        }

        return maxWidth;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource) {
        textCurrentY = y + 12;
        font.drawInBatch(Component.translatable("tooltip.valoria.materials_needed").withStyle(ChatFormatting.GRAY), x, textCurrentY, -1, true, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
        textCurrentY += 12;
        for (var entry : items) {
            ItemStack stack = entry.getFirst().getItems()[0];
            Component itemName = stack.getHoverName().copy().withStyle(ChatFormatting.DARK_GRAY);
            int nameX = x + 16 + iconSize + padding;
            int nameY = textCurrentY + (iconSize - font.lineHeight) / 2;

            font.drawInBatch(itemName, nameX, nameY, -1, false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            if (entry.getSecond().count > 1) {
                Component quantityText = Component.literal(" " + entry.getSecond().current).withStyle(entry.getSecond().isEnough ? ChatFormatting.GRAY : ChatFormatting.DARK_RED).append(Component.literal(" / " + entry.getSecond().count).withStyle(ChatFormatting.GRAY));
                font.drawInBatch(entry.getSecond().isEnough ? "✔" : "❌", x + 4, nameY, entry.getSecond().isEnough ? Color.GREEN.getRGB() : Color.RED.getRGB(), false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
                font.drawInBatch(quantityText, nameX + font.width(itemName), nameY, -1, false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            }

            textCurrentY += iconSize + padding;
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        int lineY = y + (font.lineHeight / 2) - 1;
        graphics.fill(x, lineY, x + TooltipTracker.getWidth(), lineY + 1, new Color(1, 1,1, 0.25f).getRGB());
        int currentY = y + 10;
        for (var entry : items) {
            ItemStack stack = entry.getFirst().getItems()[0];
            graphics.renderItem(stack, x + 14, currentY + 12);
            currentY += iconSize + padding;
        }
    }
}