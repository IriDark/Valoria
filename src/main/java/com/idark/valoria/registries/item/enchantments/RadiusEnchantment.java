package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.EnchantmentsRegistry;
import com.idark.valoria.registries.item.types.MagmaSwordItem;
import com.idark.valoria.registries.item.types.PhantomItem;
import com.idark.valoria.registries.item.types.ScytheItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class RadiusEnchantment extends Enchantment {

    public RadiusEnchantment() {
        super(Rarity.RARE, EnchantmentsRegistry.RADIUS_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return stack.isEnchantable() && (item instanceof ScytheItem) || (item instanceof MagmaSwordItem) || (item instanceof PhantomItem);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.SWEEPING_EDGE;
    }
}
