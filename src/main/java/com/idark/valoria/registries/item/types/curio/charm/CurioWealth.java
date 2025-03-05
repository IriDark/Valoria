package com.idark.valoria.registries.item.types.curio.charm;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioWealth extends AbstractCharm{
    public CurioWealth(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(arcRandom.chance(0.005f)){
            stack.hurtAndBreak(1, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", 0.5, AttributeModifier.Operation.ADDITION));
        return atts;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.wealth").withStyle(ChatFormatting.GRAY));
    }
}