package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;
import org.jetbrains.annotations.*;

public class RadiusEnchantment extends Enchantment{

    public RadiusEnchantment(){
        super(Rarity.RARE, EnchantmentsRegistry.RADIUS_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel(){
        return 2;
    }

    public int getMinCost(int pEnchantmentLevel){
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel){
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public boolean checkCompatibility(@NotNull Enchantment pEnchantment){
        return pEnchantment != Enchantments.SWEEPING_EDGE;
    }
}
