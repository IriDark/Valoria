package com.idark.darkrpg.item;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {
		/**
		 * OTHER
		 */
	COBALT("cobalt", 16, new int[] { 2, 3, 4, 2 }, 18,
	SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.COBALT_INGOT.get());
	}),
	SAMURAI("samurai", 17, new int[] { 3, 6, 4, 2 }, 14,
	SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.ANCIENT_INGOT.get());
	}),

	/**
	* ELEMENTAL
	*/
	NATURE("nature", 30, new int[] { 3, 5, 4, 3 }, 17,
	SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.NATURE_INGOT.get());
	}),
	DEPTH("depth", 32, new int[] { 4, 8, 6, 4 }, 15,
	SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.AQUARIUS_INGOT.get());
	}),
	INFERNAL("infernal", 35, new int[] { 4, 8, 6, 4 }, 14,
	SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.INFERNAL_INGOT.get());
	}),
	AWAKENED_VOID("awakened_void", 37, new int[] { 6, 8, 7, 5 }, 10,
	SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.VOID_INGOT.get());
	}),
	PHANTASM("phantasm", 50, new int[] { 15, 30, 20, 10 }, 30,
	SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.of(ModItems.ILLUSION_STONE.get());
	});

	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;

	private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

	ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
					  float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type pType) {
		return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type pType) {
		return this.protectionAmounts[pType.ordinal()];
	}

	@Override
	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@Override
	public String getName() {
		return DarkRPG.MOD_ID + ":" + this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}