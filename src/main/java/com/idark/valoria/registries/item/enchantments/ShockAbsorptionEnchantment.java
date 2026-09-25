package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class ShockAbsorptionEnchantment extends Enchantment {
    public ShockAbsorptionEnchantment() {
        super(Rarity.RARE, EnchantmentsRegistry.HAMMER_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 15 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 30;
    }
}
