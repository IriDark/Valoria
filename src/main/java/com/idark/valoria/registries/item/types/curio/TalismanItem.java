package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.*;
import com.idark.valoria.registries.item.types.builders.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.*;

public class TalismanItem extends AbstractTieredAccessory{
    public Builder builder;
    public TalismanItem(Builder builder){
        super(Tiers.GOLD, builder.properties);
        this.builder = builder;
        rmbEquip = false;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.OFFHAND ? builder.attributes : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.talisman").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.talisman.desc")));
        tooltip.add(Component.empty());
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