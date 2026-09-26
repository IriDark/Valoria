package com.idark.valoria.registries.item.types.curio.charm.rune;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import java.util.*;

public class EmptyRuneItem extends Item{
    public EmptyRuneItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.rune").withStyle(ChatFormatting.GRAY));
    }
}
