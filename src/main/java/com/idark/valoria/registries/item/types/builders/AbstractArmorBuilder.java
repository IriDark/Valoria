package com.idark.valoria.registries.item.types.builders;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public abstract class AbstractArmorBuilder<T extends ArmorMaterial>{
    public String name;
    public int durabilityMultiplier;
    public int headPercent = 20;
    public int chestPercent = 35;
    public int leggingsPercent = 25;
    public int bootsPercent = 20;
    public int headProtectionAmount;
    public int chestplateProtectionAmount;
    public int leggingsProtectionAmount;
    public int bootsProtectionAmount;
    public int enchantmentValue;
    public SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_IRON;
    public float toughness;
    public float knockbackResistance;
    public Supplier<Ingredient> repairIngredient;
    public int[] durability = {11, 16, 16, 13};

    public AbstractArmorBuilder(String name){
        this.name = name;
    }

    public AbstractArmorBuilder<T> mul(int durabilityMul){
        this.durabilityMultiplier = durabilityMul;
        return this;
    }

    public AbstractArmorBuilder<T> durability(int head, int chest, int leggings, int boots){
        this.durability = new int[]{head, chest, leggings, boots};
        return this;
    }

    public AbstractArmorBuilder<T> protection(int percent){
        int head = (percent * headPercent) / 100;
        int chest = (percent * chestPercent) / 100;
        int leggings = (percent * leggingsPercent) / 100;
        int boots = (percent * bootsPercent) / 100;
        int remainder = percent - (head + chest + leggings + boots);
        chest += remainder;

        this.headProtectionAmount = head;
        this.chestplateProtectionAmount = chest;
        this.leggingsProtectionAmount = leggings;
        this.bootsProtectionAmount = boots;
        return this;
    }

    /**
     * Armor percent distribution between parts
     *
     * @apiNote Default: 20, 35, 25, 20
     */
    public AbstractArmorBuilder<T> distribution(int head, int chest, int leggings, int boots){
        this.headPercent = head;
        this.chestPercent = chest;
        this.leggingsPercent = leggings;
        this.bootsPercent = boots;
        return this;
    }

    public AbstractArmorBuilder<T> protection(int head, int chest, int leggings, int boots){
        this.headProtectionAmount = head;
        this.chestplateProtectionAmount = chest;
        this.leggingsProtectionAmount = leggings;
        this.bootsProtectionAmount = boots;
        return this;
    }

    public AbstractArmorBuilder<T> enchantValue(int value){
        this.enchantmentValue = value;
        return this;
    }

    public AbstractArmorBuilder<T> sound(SoundEvent sound){
        this.equipSound = sound;
        return this;
    }

    public AbstractArmorBuilder<T> toughness(float value){
        this.toughness = value;
        return this;
    }

    public AbstractArmorBuilder<T> knockbackRes(float value){
        this.knockbackResistance = value;
        return this;
    }

    public AbstractArmorBuilder<T> ingredient(Supplier<Ingredient> value){
        this.repairIngredient = value;
        return this;
    }

    public abstract T build();
}