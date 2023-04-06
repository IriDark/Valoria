package com.idark.darkrpg.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

	//1 Harvest 2 Uses 3 Efficiency 4 Damage 5 Enchant
    //OTHER
    COBALT(1, 160, 5f, 0f, 18,
	    () -> Ingredient.fromItems(ModItems.COBALT_INGOT.get())),
    //ELEMENTAL
    NATURE(3, 2400, 10f, 0f, 17,
	    () -> Ingredient.fromItems(ModItems.NATURE_INGOT.get())),
	AQUARIUS(4, 2600, 11f, 0f, 15,
	    () -> Ingredient.fromItems(ModItems.AQUARIUS_INGOT.get())),
    INFERNAL(5, 2800, 12f, 0f, 16,
	    () -> Ingredient.fromItems(ModItems.INFERNAL_INGOT.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency,
	        float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
	        this.harvestLevel = harvestLevel;
	        this.maxUses = maxUses;
	        this.efficiency = efficiency;
	        this.attackDamage = attackDamage;
	        this.enchantability = enchantability;
	        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getMaxUses() {
		return maxUses;
    }

    @Override
    public float getEfficiency() {
		return efficiency;
    }

    @Override
    public float getAttackDamage() {
		return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
		return harvestLevel;
    }

    @Override
    public int getEnchantability() {
		return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
		return repairMaterial.getValue();
    }
}