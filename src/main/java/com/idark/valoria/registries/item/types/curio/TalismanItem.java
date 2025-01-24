package com.idark.valoria.registries.item.types.curio;

import com.google.common.collect.Multimap;
import com.idark.valoria.registries.item.types.builders.AbstractTalismanBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class TalismanItem extends AbstractTieredAccessory{
    public Builder builder;

    public TalismanItem(Builder builder){
        super(Tiers.GOLD, builder.properties);
        this.builder = builder;
        rmbEquip = false;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot){
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
        public TalismanItem build(){
            return new TalismanItem(this);
        }
    }

}