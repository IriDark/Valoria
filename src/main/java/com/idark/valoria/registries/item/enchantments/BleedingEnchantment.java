package com.idark.valoria.registries.item.enchantments;

import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

public class BleedingEnchantment extends Enchantment{
    public ArcRandom arcRandom = Tmp.rnd;

    public BleedingEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        if(arcRandom.chance(0.05f * pLevel) || pUser.getUseItem().getEnchantmentLevel(Enchantments.PIERCING) > 0){
            if(pTarget instanceof LivingEntity livingentity){
                if(pLevel > 0){
                    int i = 25 + pUser.getRandom().nextInt(45 * pLevel);
                    livingentity.addEffect(new MobEffectInstance(EffectsRegistry.BLEEDING.get(), i, pLevel - 1, false, false), pTarget);
                }
            }
        }
    }
}