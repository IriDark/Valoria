package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DescriptionBlockItem extends BlockItem{
    private final String translatable;

    public DescriptionBlockItem(String pDescriptionID, Block pBlock, Item.Properties pProperties){
        super(pBlock, pProperties);
        this.translatable = pDescriptionID;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable(translatable).withStyle(ChatFormatting.GRAY));
    }
}