package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class AccuracyEnchantment extends Enchantment{
    public AccuracyEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentsRegistry.ACCURACY_CATEGORY, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel(){
        return 3;
    }

    public int getMinCost(int pEnchantmentLevel){
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel){
        return super.getMinCost(pEnchantmentLevel) + 50;
    }
}