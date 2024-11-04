package com.idark.valoria.registries.item.types.builders;

import net.minecraft.sounds.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.function.*;

public abstract class AbstractArmorBuilder<T extends ArmorMaterial>{
    public String name;
    public int durabilityMultiplier;
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

    public AbstractArmorBuilder<T> durability(int head, int chest, int leggings, int boots) {
        this.durability = new int[]{head, chest, leggings, boots};
        return this;
    }


    public AbstractArmorBuilder<T> protection(int head, int chest, int leggings, int boots) {
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