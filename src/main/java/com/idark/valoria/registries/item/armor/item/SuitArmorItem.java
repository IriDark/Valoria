package com.idark.valoria.registries.item.armor.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SuitArmorItem extends SkinableArmorItem{

    public SuitArmorItem(ArmorMaterial material, Type type, Properties properties){
        super(material, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags){
        super.appendHoverText(stack, world, list, flags);
        var player = Minecraft.getInstance().player;
        if(player != null && flags.isAdvanced()){
            if(Screen.hasShiftDown()){
                list.add(Component.translatable("tooltip.valoria.equipped").withStyle(ChatFormatting.GRAY));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.HEAD));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.CHEST));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.LEGS));
                list.add(getArmorSetItemComponent(player, stack, EquipmentSlot.FEET));
            }else{
                list.add(Component.translatable("tooltip.valoria.shift_for_details", Component.translatable("key.keyboard.left.shift").getString()).withStyle(ChatFormatting.GRAY));
            }

            list.add(Component.empty());
        }
    }

    public static Map<EquipmentSlot, ItemStack> getFullArmorSet(ArmorMaterial material){
        Map<EquipmentSlot, ItemStack> armorSet = new EnumMap<>(EquipmentSlot.class);
        for(var item : ForgeRegistries.ITEMS){
            if(item instanceof ArmorItem armorItem){
                if(armorItem.getMaterial() == material){
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

    public static ItemStack getArmorSetItem(ItemStack stack, EquipmentSlot slot){
        if(stack.getItem() instanceof ArmorItem armorItem){
            Map<EquipmentSlot, ItemStack> armorSet = getFullArmorSet(armorItem.getMaterial());
            return armorSet.get(slot);
        }

        return ItemStack.EMPTY;
    }

    public boolean hasArmorItem(Player player, ItemStack stack, EquipmentSlot slot){
        return player != null && player.getItemBySlot(slot).getItem() == getArmorSetItem(stack, slot).getItem();
    }

    public ChatFormatting getDisplayColor(){
        return ChatFormatting.GREEN;
    }

    public MutableComponent getArmorSetItemComponent(Player player, ItemStack stack, EquipmentSlot slot){
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(stack, slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorItem(player, stack, slot) ? getDisplayColor() : ChatFormatting.RED)));
    }
}