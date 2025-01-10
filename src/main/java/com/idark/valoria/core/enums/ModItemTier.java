package com.idark.valoria.core.enums;

import com.idark.valoria.registries.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.function.*;

public enum ModItemTier implements Tier {

    NONE(0, 2031, 9.0F, 4.0F, 15, Ingredient::of),
    BRONZE(2, 1048, 4.5F, 0f, 12,
    () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())),
    COBALT(4, 2048, 8.5F, 0f, 16,
            () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())),
    PEARLIUM(3, 325, 6.0F, 2.0F, 15,
            () -> Ingredient.of(ItemsRegistry.pearliumIngot.get())),
    HOLIDAY(2, 740, 6.0F, 2.0F, 12,
            () -> Ingredient.of(ItemsRegistry.holidayCandy.get())),
    HALLOWEEN(3, 1200, 8.0F, 3.0F, 12,
            () -> Ingredient.of(ItemsRegistry.candyCorn.get())),
    SAMURAI(3, 1600, 8.5F, 3.0F, 10,
            () -> Ingredient.of(ItemsRegistry.ancientIngot.get())),
    PYRATITE(6, 3312, 11.5F, 4.0F, 12,
            () -> Ingredient.of(ItemsRegistry.pyratite.get())),
    NATURE(5, 2651, 11f, 0f, 18,
            () -> Ingredient.of(ItemsRegistry.natureIngot.get())),
    AQUARIUS(6, 3256, 12f, 0f, 15,
            () -> Ingredient.of(ItemsRegistry.aquariusIngot.get())),
    INFERNAL(7, 4256, 13f, 0f, 16,
            () -> Ingredient.of(ItemsRegistry.infernalIngot.get())),
    NIHILITY(8, 4651, 14F, 0F, 18, () -> Ingredient.of(ItemsRegistry.nihilityShard.get())),
    BLOOD(4, 2431, 9.0F, 4.0F, 15,
            () -> Ingredient.of(ItemsRegistry.painCrystal.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    @Deprecated
    private final LazyLoadedValue<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    ModItemTier(int harvestLevel, int maxUses, float efficiency, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = 0;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}