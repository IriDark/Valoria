package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DescriptionItem extends Item{
    private final String translatable;

    public DescriptionItem(String pDescriptionID, Properties properties){
        super(properties);
        this.translatable = pDescriptionID;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable(translatable).withStyle(ChatFormatting.GRAY));
    }
}