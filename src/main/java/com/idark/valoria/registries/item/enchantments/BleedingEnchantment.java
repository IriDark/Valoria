package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;

public class BleedingEnchantment extends Enchantment{

    public BleedingEnchantment(){
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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

    public void doPostAttack(LivingEntity pUser, Entity pTarget, int pLevel){
        if(RandomUtil.percentChance(0.05f * pLevel) || pUser.getUseItem().getEnchantmentLevel(Enchantments.PIERCING) > 0){
            if(pTarget instanceof LivingEntity livingentity){
                if(pLevel > 0){
                    int i = 40 + pUser.getRandom().nextInt(85 * pLevel);
                    livingentity.addEffect(new MobEffectInstance(EffectsRegistry.BLEEDING.get(), i, pLevel - 1, false, false));
                }
            }
        }
    }

    public boolean checkCompatibility(Enchantment pEnchantment){
        return super.checkCompatibility(pEnchantment) && pEnchantment != Enchantments.SHARPNESS;
    }
}