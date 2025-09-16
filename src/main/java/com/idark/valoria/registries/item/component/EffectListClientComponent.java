package com.idark.valoria.registries.item.component;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.inventory.tooltip.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import org.joml.*;

import java.util.*;

public class EffectListClientComponent implements ClientTooltipComponent{
    private final List<MobEffectInstance> effects;
    private final Component component;
    private final int iconSize = 10;
    private final int padding = 0;
    private int textCurrentY = 0;

    public EffectListClientComponent(List<MobEffectInstance> items, Component component) {
        this.effects = items;
        this.component = component;
    }

    public static EffectListClientComponent create(List<MobEffectInstance> items, Component component) {
        return new EffectListClientComponent(items, component);
    }

    @Override
    public int getHeight() {
        int offset = 4;
        if(component != null && component != Component.empty()){
            offset += 10;
        }

        return (iconSize + padding) * effects.size() + offset;
    }

    @Override
    public int getWidth(Font font) {
        int maxWidth = 0;
        if(component != null && component != Component.empty()){
            maxWidth += font.width(component);
        }

        for (var entry : effects) {
            int currentWidth = iconSize + padding + font.width(Component.translatable(entry.getDescriptionId()));
            if (currentWidth > maxWidth) {
                maxWidth = currentWidth + 50;
            }
        }

        return maxWidth;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource) {
        textCurrentY = y;
        if(component != null && component != Component.empty()){
            font.drawInBatch(component, x, y, -1, false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            textCurrentY = y + 10;
        }

        for (var entry : effects) {
            MutableComponent mutablecomponent = Component.translatable(entry.getDescriptionId());
            MobEffect mobeffect = entry.getEffect();
            if(entry.getAmplifier() > 0){
                mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + entry.getAmplifier()));
            }

            if(!entry.endsWithin(20)){
                mutablecomponent = Component.translatable("potion.withDuration", mutablecomponent, MobEffectUtil.formatDuration(entry, 1));
            }

            int nameX = x + iconSize + 2 + padding;
            int nameY = textCurrentY + 2 + (iconSize - font.lineHeight) / 2;
            font.drawInBatch(mutablecomponent.withStyle(mobeffect.getCategory().getTooltipFormatting()), nameX, nameY, -1, false, matrix, bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
            textCurrentY += iconSize + padding;
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        int currentY = y;
        if(component != null && component != Component.empty()) currentY += 10;
        for (var entry : effects) {
            var sprite = Minecraft.getInstance().getMobEffectTextures().get(entry.getEffect());
            graphics.blit(x, currentY, 0, iconSize, iconSize, sprite);
            currentY += iconSize + padding;
        }
    }
}