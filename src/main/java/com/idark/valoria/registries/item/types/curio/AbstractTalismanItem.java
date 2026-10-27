package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import java.util.*;

public abstract class AbstractTalismanItem extends Item{
    public AbstractTalismanBuilder<?> builder;

    public AbstractTalismanItem(AbstractTalismanBuilder<?> builder){
        super(builder.buildProperties());
        this.builder = builder;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.talisman").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.talisman.desc")));
    }

}
