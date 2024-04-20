package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.EnchantmentsRegistry;
import com.idark.valoria.registries.item.types.BlazeReapItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ExplosiveFlameEnchantment extends Enchantment {

    public ExplosiveFlameEnchantment() {
        super(Rarity.RARE, EnchantmentsRegistry.BLAZE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return stack.isEnchantable() && (item instanceof BlazeReapItem);
    }

    public boolean checkCompatibility(Enchantment pEnchantment) {
        return super.checkCompatibility(pEnchantment) && pEnchantment != Enchantments.FIRE_ASPECT;
    }
}