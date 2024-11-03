package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class TalismanItem extends AbstractTieredAccessory{
    public Builder builder;
    public TalismanItem(Builder builder){
        super(Tiers.GOLD, builder.properties);
        this.builder = builder;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.OFFHAND ? builder.attributes : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public static class Builder extends AbstractTalismanBuilder<TalismanItem>{
        public Builder(Properties pProperties){
            super(pProperties);
        }

        /**
         * @return Build of KatanaItem with all the configurations you set :p
         */
        public TalismanItem build() {
            return new TalismanItem(this);
        }
    }

}