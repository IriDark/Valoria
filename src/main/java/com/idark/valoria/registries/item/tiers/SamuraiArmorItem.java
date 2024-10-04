package com.idark.valoria.registries.item.tiers;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

import java.util.*;

public class SamuraiArmorItem extends SuitArmorItem {
    public SamuraiArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public float getBonusValue(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> 0.15f;
            case HEAD, FEET -> 0.05f;
            case LEGS -> 0.1f;
            default -> 0;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(AttributeRegistry.DASH_DISTANCE.get(), new AttributeModifier(UUID.fromString("58c87772-fa46-4635-8877-72fa464635a6"), "bonus", getBonusValue(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }
}