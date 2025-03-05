package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioStrength extends AbstractCurioItem{
    public CurioStrength(Properties properties){
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.strength").withStyle(ChatFormatting.GRAY));
    }
}