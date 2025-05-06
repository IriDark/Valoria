package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.collect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioStrength extends AbstractRuneItem{
    private final float bonus;
    public CurioStrength(float bonus, Properties properties){
        super(properties);
        this.bonus = bonus;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "bonus", bonus, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }

    @Override
    public RuneType runeType(){
        return RuneType.STRENGTH;
    }
}