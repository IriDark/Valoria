package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class OverdriveEnchantment extends Enchantment {

    public OverdriveEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentsRegistry.OVERDRIVE_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean checkCompatibility(Enchantment pEnchantment) {
        return super.checkCompatibility(pEnchantment) && pEnchantment != EnchantmentsRegistry.RADIUS.get() || pEnchantment != EnchantmentsRegistry.DASH.get();
    }
}