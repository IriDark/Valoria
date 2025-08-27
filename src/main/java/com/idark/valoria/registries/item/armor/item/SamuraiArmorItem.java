package com.idark.valoria.registries.item.armor.item;

import com.google.common.collect.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.common.registry.item.armor.*;

import java.util.*;

public class SamuraiArmorItem extends SuitArmorItem{
    public ArmorDispatcher dispatcher;
    public SamuraiArmorItem(ArmorMaterial material, Type type, Properties properties){
        super(material, type, properties);
        this.dispatcher = new ArmorDispatcher();
    }

    public float getBonusValue(EquipmentSlot slot){
        return switch(slot){
            case CHEST -> 0.15f;
            case HEAD, FEET -> 0.05f;
            case LEGS -> 0.1f;
            default -> 0;
        };
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player){
        super.onArmorTick(stack, level, player);
        if (!level.isClientSide) {
            dispatcher.idle(player, stack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot){
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(AttributeReg.DASH_DISTANCE.get(), new AttributeModifier(UUID.fromString("58c87772-fa46-4635-8877-72fa464635a6"), "bonus", getBonusValue(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }
}