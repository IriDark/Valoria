package com.idark.valoria.registries.world.item.enchant.types;

import com.idark.valoria.registries.world.effect.ModEffects;
import com.idark.valoria.util.math.RandUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class BleedingEnchantment extends Enchantment {

    public BleedingEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return stack.isEnchantable() && (item instanceof SwordItem);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    public void doPostAttack(LivingEntity pUser, Entity pTarget, int pLevel) {
        if (RandUtils.doWithChance(10 + pLevel)) {
            if (pTarget instanceof LivingEntity livingentity) {
                if (pLevel > 0) {
                    int i = 20 + pUser.getRandom().nextInt(85 * pLevel);
                    livingentity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), i, pLevel));
                }
            }
        }
    }

    public boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.SWEEPING_EDGE;
    }
}