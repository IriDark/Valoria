package com.idark.darkrpg.enchant;

import com.idark.darkrpg.item.types.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class ExplosiveFlameEnchantment extends Enchantment {
	
    public ExplosiveFlameEnchantment() {
        super(Rarity.RARE, ModEnchantments.BLAZE, new EquipmentSlotType[] {EquipmentSlotType.MAINHAND});
    }
	
	public boolean canEnchant(ItemStack stack) {
		Item item = stack.getItem();
		return stack.isEnchantable() && (item instanceof BlazeReapItem);
	}
	
    @Override
    public int getMaxLevel() {
        return 1;
    }	
	
	public boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench) && ench != Enchantments.FIRE_ASPECT;
    }	
}