package com.idark.valoria.core.enums;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.function.*;

public enum ModArmorMaterial implements ArmorMaterial {

    // Helmet, Chestplate, Leggings, Boots
    COBALT("cobalt", 46, new int[]{4, 10, 8, 4}, 18,
            SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.05f, () -> Ingredient.of(ItemsRegistry.cobaltIngot.get())),
    SAMURAI("samurai", 55, new int[]{6, 12, 10, 5}, 16,
            SoundEvents.ARMOR_EQUIP_IRON, 2.5f, 0.15f, () -> Ingredient.of(ItemsRegistry.ancientIngot.get())),
    NATURE("nature", 66, new int[]{6, 10, 9, 5}, 17,
            SoundEvents.ARMOR_EQUIP_IRON, 3f, 0.0f, () -> Ingredient.of(ItemsRegistry.natureIngot.get())),
    DEPTH("depth", 72, new int[]{6, 11, 9, 6}, 15,
            SoundEvents.ARMOR_EQUIP_IRON, 3.6f, 0.0f, () -> Ingredient.of(ItemsRegistry.aquariusIngot.get())),
    INFERNAL("infernal", 76, new int[]{7, 12, 9, 7}, 14,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5f, 0.1f, () -> Ingredient.of(ItemsRegistry.infernalIngot.get())),
    AWAKENED_VOID("awakened_void", 82, new int[]{8, 10, 9, 8}, 10,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 8f, 0.2f, () -> Ingredient.of(ItemsRegistry.voidIngot.get())),
    PHANTASM("phantasm", 96, new int[]{15, 30, 20, 10}, 30,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 30f, 0.6f, () -> Ingredient.of(ItemsRegistry.illusionStone.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    ModArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
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
        return Valoria.ID + ":" + this.name;
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