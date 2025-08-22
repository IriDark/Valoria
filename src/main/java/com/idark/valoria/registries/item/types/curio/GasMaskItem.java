package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import javax.annotation.*;
import java.util.*;

public class GasMaskItem extends NihilityItem{
    public GasMaskItem(Properties properties){
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack){
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(AttributeReg.NIHILITY_RESISTANCE.get(), new AttributeModifier(uuid, "bonus", 50, AttributeModifier.Operation.ADDITION));
        atts.put(AttributeReg.NIHILITY_RESILIENCE.get(), new AttributeModifier(uuid, "bonus", 0.05, Operation.MULTIPLY_TOTAL));
        atts.put(AttributeReg.MAX_NIHILITY.get(), new AttributeModifier(uuid, "bonus", 0.15, Operation.MULTIPLY_TOTAL));
        return atts;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack){
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 1.0f);
    }
}