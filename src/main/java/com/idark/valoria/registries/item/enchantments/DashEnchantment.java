package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.EnchantmentsRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class DashEnchantment extends Enchantment{
    public DashEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentsRegistry.DASH_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    public boolean checkCompatibility(@NotNull Enchantment pEnchantment){
        return pEnchantment != Enchantments.FIRE_ASPECT || pEnchantment != EnchantmentsRegistry.RADIUS.get();
    }
}
