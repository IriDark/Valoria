package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.collect.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioWealth extends AbstractRuneItem{
    private final float luck;
    public CurioWealth(float luck, Properties properties){
        super(properties);
        this.luck = luck;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.LUCK, new AttributeModifier(uuid, "bonus", luck, AttributeModifier.Operation.ADDITION));
        return atts;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.wealth").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public RuneType runeType(){
        return RuneType.WEALTH;
    }
}