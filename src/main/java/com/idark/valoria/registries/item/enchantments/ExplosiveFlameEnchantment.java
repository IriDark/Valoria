package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class ExplosiveFlameEnchantment extends Enchantment{

    public ExplosiveFlameEnchantment(){
        super(Rarity.RARE, EnchantmentsRegistry.BLAZE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean checkCompatibility(Enchantment pEnchantment){
        return super.checkCompatibility(pEnchantment) && pEnchantment != Enchantments.FIRE_ASPECT;
    }
}