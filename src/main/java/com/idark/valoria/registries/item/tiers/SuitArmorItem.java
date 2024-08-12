package com.idark.valoria.registries.item.tiers;

import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class SuitArmorItem extends ArmorItem{

    public SuitArmorItem(ArmorMaterial material, Type type, Properties properties){
        super(material, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        Player player = Minecraft.getInstance().player;
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.valoria.equipped").withStyle(ChatFormatting.GRAY));
        list.add(getArmorSetItemComponent(EquipmentSlot.HEAD, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.CHEST, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.LEGS, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.FEET, player));
    }

    public ItemStack getArmorSetItem(EquipmentSlot slot){
        return ItemStack.EMPTY;
    }

    public boolean hasArmorItem(EquipmentSlot slot, Player player){
        return player.getItemBySlot(slot).getItem() == getArmorSetItem(slot).getItem();
    }

    public ChatFormatting getDisplayColor(){
        return ChatFormatting.GREEN;
    }

    public MutableComponent getArmorSetItemComponent(EquipmentSlot slot, Player player){
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorItem(slot, player) ? getDisplayColor() : ChatFormatting.RED)));
    }
}