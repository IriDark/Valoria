package com.idark.valoria.registries.item.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.ArmorItem.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;

public class ArmorRegistry implements ArmorMaterial{
    public Builder builder;

    public ArmorRegistry(Builder builder){
        this.builder = builder;
    }

    @Override
    public int getDurabilityForType(Type pType){
        return builder.durability[pType.ordinal()] * builder.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(Type pType){
        return switch(pType){
            case HELMET -> builder.headProtectionAmount;
            case CHESTPLATE -> builder.chestplateProtectionAmount;
            case LEGGINGS -> builder.leggingsProtectionAmount;
            case BOOTS -> builder.bootsProtectionAmount;
        };
    }

    @Override
    public int getEnchantmentValue(){
        return builder.enchantmentValue;
    }

    @Override
    @NotNull
    public SoundEvent getEquipSound(){
        return builder.equipSound;
    }

    @Override
    @NotNull
    public Ingredient getRepairIngredient(){
        return builder.repairIngredient.get();
    }

    @Override
    @NotNull
    public String getName(){
        return Valoria.ID + ":" + builder.name;
    }

    @Override
    public float getToughness(){
        return builder.toughness;
    }

    @Override
    public float getKnockbackResistance(){
        return builder.knockbackResistance;
    }

    // every level of Protection (added up across all pieces) reduces damage by 4%
    // Leather - 3.6% -> 4%
    // Golden - 8.8% -> 9%
    // Chainmail - 9.6% -> 10%
    // Iron - 12%
    // Diamond - 16%
    // Netherite - 16%
    public static final ArmorRegistry COBALT = new ArmorRegistry.Builder("cobalt").protection(18).mul(46).enchantValue(18).knockbackRes(0.05f).ingredient(() -> Ingredient.of(ItemsRegistry.cobaltIngot.get())).build();
    public static final ArmorRegistry SAMURAI = new ArmorRegistry.Builder("samurai").protection(20).mul(55).enchantValue(16).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.ancientIngot.get())).build();
    public static final ArmorRegistry SPIDER = new ArmorRegistry.Builder("spider").protection(22).mul(68).enchantValue(14).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.spiderFang.get())).build();
    public static final ArmorRegistry NATURE = new ArmorRegistry.Builder("nature").protection(24).mul(66).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.natureIngot.get())).build();
    public static final ArmorRegistry DEPTH = new ArmorRegistry.Builder("depth").protection(26).mul(72).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.aquariusIngot.get())).build();
    public static final ArmorRegistry INFERNAL = new ArmorRegistry.Builder("infernal").protection(28).mul(76).enchantValue(14).knockbackRes(0.12f).ingredient(() -> Ingredient.of(ItemsRegistry.infernalIngot.get())).build();
    public static final ArmorRegistry VOID = new ArmorRegistry.Builder("awakened_void").protection(30).mul(76).enchantValue(10).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.voidIngot.get())).build();
    public static final ArmorRegistry PHANTASM = new ArmorRegistry.Builder("phantasm").protection(35).mul(82).enchantValue(12).knockbackRes(0.25f).ingredient(() -> Ingredient.of(ItemsRegistry.illusionStone.get())).build();

    public static class Builder extends pro.komaru.tridot.registry.item.builders.AbstractArmorBuilder<ArmorRegistry>{
        public Builder(String name){
            super(name);
        }

        @Override
        public ArmorRegistry build(){
            return new ArmorRegistry(this);
        }
    }
}