package com.idark.valoria.registries.item.enums;

import com.idark.valoria.registries.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.function.*;

public enum ModItemTier implements Tier{

    NONE(0, 2031, 9.0F, 4.0F, 15, Ingredient::of),
    COBALT(4, 2048, 8.5F, 0f, 16,
    () -> Ingredient.of(ItemsRegistry.COBALT_INGOT.get())),
    PEARLIUM(2, 325, 6.0F, 2.0F, 15,
    () -> Ingredient.of(ItemsRegistry.PEARLIUM_INGOT.get())),
    HOLIDAY(2, 370, 6.0F, 2.0F, 11,
    () -> Ingredient.of(ItemsRegistry.HOLIDAY_CANDY.get())),
    SAMURAI(3, 1600, 8.5F, 3.0F, 10,
    () -> Ingredient.of(ItemsRegistry.ANCIENT_INGOT.get())),
    PYRATITE(6, 3312, 11.5F, 4.0F, 12,
    () -> Ingredient.of(ItemsRegistry.PYRATITE.get())),
    NATURE(5, 2651, 11f, 0f, 18,
    () -> Ingredient.of(ItemsRegistry.NATURE_INGOT.get())),
    AQUARIUS(6, 3256, 12f, 0f, 15,
    () -> Ingredient.of(ItemsRegistry.AQUARIUS_INGOT.get())),
    INFERNAL(7, 4256, 13f, 0f, 16,
    () -> Ingredient.of(ItemsRegistry.INFERNAL_INGOT.get())),
    NIHILITY(8, 4651, 14F, 0F, 18, () -> Ingredient.of(ItemsRegistry.NIHILITY_SHARD.get())),
    BLOOD(4, 2431, 9.0F, 4.0F, 15,
    () -> Ingredient.of(ItemsRegistry.PAIN_CRYSTAL.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    @Deprecated
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses(){
        return maxUses;
    }

    @Override
    public float getSpeed(){
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus(){
        return attackDamage;
    }

    @Override
    public int getLevel(){
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue(){
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient(){
        return repairMaterial.get();
    }
}