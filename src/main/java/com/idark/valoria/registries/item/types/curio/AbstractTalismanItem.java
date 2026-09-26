package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;

import java.util.*;

public abstract class AbstractTalismanItem extends Item{
    public AbstractTalismanBuilder<?> builder;
    private final ItemAttributeModifiers modifiers;

    public AbstractTalismanItem(AbstractTalismanBuilder<?> builder){
        super(builder.properties);
        this.builder = builder;
        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder();
        builder.attributes.forEach((attribute, modifier) -> attributes.add(attribute, modifier, EquipmentSlotGroup.OFFHAND));
        this.modifiers = attributes.build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(){
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.talisman").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.talisman.desc")));
    }

}
