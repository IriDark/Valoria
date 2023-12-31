package com.idark.valoria.enchant;

import com.idark.valoria.item.types.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ExplosiveFlameEnchantment extends Enchantment {
	
    public ExplosiveFlameEnchantment() {
        super(Rarity.RARE, ModEnchantments.BLAZE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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