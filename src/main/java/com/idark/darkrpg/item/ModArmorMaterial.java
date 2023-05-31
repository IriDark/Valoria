package com.idark.darkrpg.item;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

	public enum ModArmorMaterial implements IArmorMaterial {
	//OTHER
	COBALT("cobalt", 17, new int[] { 2, 3, 4, 2 }, 18,
	SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.COBALT_INGOT.get());
	}),
	//ELEMENTAL
	NATURE("nature", 30, new int[] { 4, 5, 6, 4 }, 17,
	SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.NATURE_INGOT.get());
	}),
	DEPTH("depth", 32, new int[] { 4, 8, 8, 4 }, 15,
	SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.AQUARIUS_INGOT.get());
	}),
	INFERNAL("infernal", 35, new int[] { 4, 8, 8, 6 }, 14,
	SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.INFERNAL_INGOT.get());
	}),
	AWAKENED_VOID("awakened_void", 37, new int[] { 6, 8, 8, 6 }, 10,
	SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.VOID_INGOT.get());
	}),
	PHANTASM("phantasm", 50, new int[] { 15, 30, 20, 10 }, 30,
	SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f, () -> {
	return Ingredient.fromItems(ModItems.ILLUSION_STONE.get());
	});
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final LazyValue<Ingredient> repairMaterial;
	
	private ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
		SoundEvent soundEvent, float toughness, float knockbackResistance,
		Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}
		
		
	public int getDurability(EquipmentSlotType slotIn) {
	return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}
		
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	return this.damageReductionAmountArray[slotIn.getIndex()];
	}
		
	public int getEnchantability() {
	return this.enchantability;
	}
		
	public SoundEvent getSoundEvent() {
	return this.soundEvent;
	}
		
	public Ingredient getRepairMaterial() {
	return this.repairMaterial.getValue();
	}
	
	@OnlyIn(Dist.CLIENT)
	public String getName() {
	return DarkRPG.MOD_ID + ":" + this.name;
	}

	public float getToughness() {
	return this.toughness;
	}

	public float getKnockbackResistance() {
	return this.knockbackResistance;
	}
}