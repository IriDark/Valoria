package com.idark.valoria.registries.item.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;

import java.util.*;

public class ArmorRegistry extends AbstractArmorRegistry{
    public Builder builder;

    public ArmorRegistry(Builder builder, List<ArmorEffectData> data){
        super(builder, data);
        this.builder = builder;
    }

    @Override
    @NotNull
    public String getName(){
        return Valoria.ID + ":" + builder.name;
    }

    // every level of Protection (added up across all pieces) reduces damage by 4%
    // Leather - 3.6% -> 4%
    // Golden - 8.8% -> 9%
    // Chainmail - 9.6% -> 10%
    // Iron - 12%
    // Diamond - 16%
    // Netherite - 16%

    public static List<ArmorEffectData> natureData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 1200)));
    public static List<ArmorEffectData> depthData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200), Entity::isInWater));
    public static List<ArmorEffectData> infernalData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200)), ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400), Entity::isOnFire));
    public static List<ArmorEffectData> etherealData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200)));
    public static List<ArmorEffectData> crimtaneData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(EffectsRegistry.EXHAUSTION.get(), 1200)));

    public static final ArmorRegistry MARSH = new ArmorRegistry.Builder("marsh").protection(20).mul(58).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.marshCloth.get())).build();
    public static final ArmorRegistry SAMURAI = new ArmorRegistry.Builder("samurai").protection(22).mul(55).enchantValue(16).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.ancientIngot.get())).build();
    public static final ArmorRegistry BLACK_GOLD = new ArmorRegistry.Builder("black_gold").protection(20).mul(55).enchantValue(20).ingredient(() -> Ingredient.of(ItemsRegistry.blackGold.get())).build();
    public static final ArmorRegistry COBALT = new ArmorRegistry.Builder("cobalt").protection(25).mul(46).enchantValue(18).knockbackRes(0.05f).ingredient(() -> Ingredient.of(ItemsRegistry.cobaltIngot.get())).build();
    public static final ArmorRegistry ETHEREAL = new ArmorRegistry.Builder("ethereal").effects(etherealData).protection(28).mul(60).enchantValue(18).knockbackRes(0.1f).ingredient(() -> Ingredient.of(ItemsRegistry.etherealShard.get())).build();
    public static final ArmorRegistry NATURE = new ArmorRegistry.Builder("nature").effects(natureData).protection(30).mul(66).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.natureIngot.get())).build();
    public static final ArmorRegistry DEPTH = new ArmorRegistry.Builder("depth").effects(depthData).protection(32).mul(72).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.aquariusIngot.get())).build();
    public static final ArmorRegistry INFERNAL = new ArmorRegistry.Builder("infernal").effects(infernalData).protection(35).mul(76).enchantValue(14).knockbackRes(0.12f).ingredient(() -> Ingredient.of(ItemsRegistry.infernalIngot.get())).build();
    public static final ArmorRegistry SPIDER = new ArmorRegistry.Builder("spider").protection(40).mul(68).enchantValue(14).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.spiderFang.get())).build();
    public static final ArmorRegistry PYRATITE = new ArmorRegistry.Builder("pyratite").protection(42).mul(72).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.pyratite.get())).build();
    public static final ArmorRegistry CRIMTANE = new ArmorRegistry.Builder("crimtane").effects(crimtaneData).protection(45).mul(72).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.painCrystal.get())).build();
    public static final ArmorRegistry VOID = new ArmorRegistry.Builder("awakened_void").protection(50).mul(80).enchantValue(10).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.voidIngot.get())).build();
    public static final ArmorRegistry PHANTASM = new ArmorRegistry.Builder("phantasm").protection(60).mul(85).enchantValue(12).knockbackRes(0.25f).ingredient(() -> Ingredient.of(ItemsRegistry.illusionStone.get())).build();

    public static class Builder extends AbstractArmorBuilder<ArmorRegistry>{
        public Builder(String name){
            super(name);
        }

        @Override
        public ArmorRegistry build(){
            return new ArmorRegistry(this, data);
        }
    }
}