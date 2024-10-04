package com.idark.valoria.registries.item.tiers;

import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class SuitArmorItem extends ArmorItem {

    public SuitArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        var player = Minecraft.getInstance().player;
        if(player != null && flags.isAdvanced()){
            if(Screen.hasShiftDown()){
                list.add(Component.translatable("tooltip.valoria.equipped").withStyle(ChatFormatting.GRAY));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.HEAD));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.CHEST));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.LEGS));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.FEET));
            } else {
                list.add(Component.translatable("tooltip.valoria.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
            }

            list.add(Component.empty());
        }
    }

    public static Map<EquipmentSlot, ItemStack> getFullArmorSet(ArmorMaterial material) {
        Map<EquipmentSlot, ItemStack> armorSet = new EnumMap<>(EquipmentSlot.class);
        for (var item : ForgeRegistries.ITEMS) {
            if (item instanceof ArmorItem armorItem) {
                if (armorItem.getMaterial() == material) {
                    EquipmentSlot slot = armorItem.getEquipmentSlot();
                    armorSet.put(slot, new ItemStack(armorItem));
                }
            }
        }

        armorSet.putIfAbsent(EquipmentSlot.HEAD, ItemStack.EMPTY);
        armorSet.putIfAbsent(EquipmentSlot.CHEST, ItemStack.EMPTY);
        armorSet.putIfAbsent(EquipmentSlot.LEGS, ItemStack.EMPTY);
        armorSet.putIfAbsent(EquipmentSlot.FEET, ItemStack.EMPTY);
        return armorSet;
    }

    public static ItemStack getArmorSetItem(ItemStack stack, EquipmentSlot slot) {
        if(stack.getItem() instanceof ArmorItem armorItem){
            Map<EquipmentSlot, ItemStack> armorSet = getFullArmorSet(armorItem.getMaterial());
            return armorSet.get(slot);
        }

        return ItemStack.EMPTY;
    }

    public boolean hasArmorItem(Player player, ItemStack stack, EquipmentSlot slot) {
        return player != null && player.getItemBySlot(slot).getItem() == getArmorSetItem(stack, slot).getItem();
    }

    public ChatFormatting getDisplayColor() {
        return ChatFormatting.GREEN;
    }

    public MutableComponent getArmorSetItemComponent(Player player, ItemStack stack, EquipmentSlot slot) {
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(stack, slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorItem(player, stack, slot) ? getDisplayColor() : ChatFormatting.RED)));
    }
}