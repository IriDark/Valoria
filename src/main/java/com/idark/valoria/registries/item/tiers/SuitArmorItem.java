package com.idark.valoria.registries.item.tiers;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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

import java.util.List;

public class SuitArmorItem extends ArmorItem {

    public SuitArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        Player player = Minecraft.getInstance().player;
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.valoria.equipped").withStyle(ChatFormatting.GRAY));
        list.add(getArmorSetItemComponent(EquipmentSlot.HEAD, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.CHEST, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.LEGS, player));
        list.add(getArmorSetItemComponent(EquipmentSlot.FEET, player));
    }

    public ItemStack getArmorSetItem(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public boolean hasArmorItem(EquipmentSlot slot, Player player) {
        return player.getItemBySlot(slot).getItem() == getArmorSetItem(slot).getItem();
    }

    public ChatFormatting getDisplayColor() {
        return ChatFormatting.GREEN;
    }

    public MutableComponent getArmorSetItemComponent(EquipmentSlot slot, Player player) {
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorItem(slot, player) ? getDisplayColor() : ChatFormatting.RED)));
    }
}