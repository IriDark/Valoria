package com.idark.valoria.registries.item.types;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DescriptionItem extends Item {
    private final String translatable;
    public DescriptionItem(String pDescriptionID, Properties properties) {
        super(properties);
        this.translatable = pDescriptionID;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable(translatable).withStyle(ChatFormatting.GRAY));
    }
}