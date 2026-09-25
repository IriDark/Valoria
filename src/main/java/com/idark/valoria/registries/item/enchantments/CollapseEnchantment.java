package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class CollapseEnchantment extends Enchantment {
    public CollapseEnchantment() {
        super(Rarity.RARE, EnchantmentsRegistry.HAMMER_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return 15 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 30;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other)
            && other != EnchantmentsRegistry.CONCUSSION.get()
            && other != EnchantmentsRegistry.REPULSION.get()
            && other != EnchantmentsRegistry.SUNDERING.get();
    }
}
