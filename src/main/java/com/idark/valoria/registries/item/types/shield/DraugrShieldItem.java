package com.idark.valoria.registries.item.types.shield;

import com.google.common.collect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class DraugrShieldItem extends ConfiguredShield{
    public final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public DraugrShieldItem(AbstractShieldBuilder<? extends ConfiguredShield> builder){
        super(builder);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attrBuilder = ImmutableMultimap.builder();
        attrBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("0340e1c5-92f3-4c99-80e1-c592f3ec99a8"), "Tool modifier", 0.1, Operation.MULTIPLY_TOTAL));
        this.defaultModifiers = attrBuilder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
        return pEquipmentSlot == EquipmentSlot.OFFHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static class Builder extends AbstractShieldBuilder<DraugrShieldItem>{

        public Builder(Properties itemProperties) {
            super(itemProperties);
        }

        public Builder(float defPercent, Properties itemProperties) {
            super(defPercent, itemProperties);
        }

        @Override
        public DraugrShieldItem build(){
            return new DraugrShieldItem(this);
        }
    }
}