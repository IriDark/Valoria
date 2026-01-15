package com.idark.valoria.registries.item.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.armor.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.builders.AbstractArmorBuilder.*;

import java.util.*;

public class ArmorRegistry extends AbstractArmorRegistry{
    public Builder builder;

    public ArmorRegistry(Builder builder, List<ArmorEffectData> data, List<HitEffectData> hitEffectData){
        super(builder, data, hitEffectData);
        this.builder = builder;
    }

    @Override
    @NotNull
    public String getName(){
        return Valoria.ID + ":" + builder.name;
    }

    public static List<ArmorEffectData> natureData = List.of(ArmorEffectData.createData(EffectsRegistry.ALOEREGEN));
    public static List<ArmorEffectData> depthData = List.of(ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, MobEffectInstance.INFINITE_DURATION), Entity::isInWater));
    public static List<ArmorEffectData> infernalData = List.of(ArmorEffectData.createData(() -> MobEffects.DAMAGE_BOOST), ArmorEffectData.createData(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400), Entity::isOnFire));
    public static List<ArmorEffectData> etherealData = List.of(ArmorEffectData.createData(() -> MobEffects.NIGHT_VISION));
    public static List<HitEffectData> crimtaneData = List.of(HitEffectData.createData(EffectsRegistry.EXHAUSTION));

    public static final ArmorRegistry FALLEN_COLLECTOR = new ArmorRegistry.Builder("fallen_collector").addAttr(AttributeReg.NECROMANCY_LIFETIME, new AttributeData(15, Operation.ADDITION)).addAttr(AttributeReg.NECROMANCY_COUNT, new AttributeData(3, Operation.ADDITION)).protection(25).mul(45).enchantValue(20).ingredient(() -> Ingredient.of(Items.LEATHER)).build();
    public static final ArmorRegistry MARSH = new ArmorRegistry.Builder("marsh").protection(20).mul(58).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.marshCloth.get())).build();
    public static final ArmorRegistry BLACK_GOLD = new ArmorRegistry.Builder("black_gold").protection(20).mul(55).enchantValue(20).ingredient(() -> Ingredient.of(ItemsRegistry.blackGold.get())).build();
    public static final ArmorRegistry COBALT = new ArmorRegistry.Builder("cobalt").protection(45).mul(46).enchantValue(18).knockbackRes(0.05f).ingredient(() -> Ingredient.of(ItemsRegistry.cobaltIngot.get())).build();
    public static final ArmorRegistry SAMURAI = new ArmorRegistry.Builder("samurai").protection(35).mul(55).enchantValue(16).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.ancientIngot.get())).build();
    public static final ArmorRegistry ETHEREAL = new ArmorRegistry.Builder("ethereal").effects(etherealData).protection(50).mul(60).enchantValue(18).knockbackRes(0.1f).ingredient(() -> Ingredient.of(ItemsRegistry.etherealShard.get())).build();
    public static final ArmorRegistry NATURE = new ArmorRegistry.Builder("nature").effects(natureData).protection(55).mul(66).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.natureIngot.get())).build();
    public static final ArmorRegistry DEPTH = new ArmorRegistry.Builder("depth").effects(depthData).protection(60).mul(72).enchantValue(16).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.aquariusIngot.get())).build();
    public static final ArmorRegistry INFERNAL = new ArmorRegistry.Builder("infernal").effects(infernalData).protection(65).mul(76).enchantValue(14).knockbackRes(0.12f).ingredient(() -> Ingredient.of(ItemsRegistry.infernalIngot.get())).build();
    public static final ArmorRegistry SPIDER = new ArmorRegistry.Builder("spider").setVoidDefence(25).protection(50).mul(68).enchantValue(14).knockbackRes(0.10f).ingredient(() -> Ingredient.of(ItemsRegistry.spiderFang.get())).build();
    public static final ArmorRegistry PYRATITE = new ArmorRegistry.Builder("pyratite").setVoidDefence(15).protection(62).mul(72).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.pyratite.get())).build();
    public static final ArmorRegistry CRIMTANE = new ArmorRegistry.Builder("crimtane").setVoidDefence(35).hitEffects(crimtaneData).protection(68).mul(72).enchantValue(12).ingredient(() -> Ingredient.of(ItemsRegistry.painCrystal.get())).build();
    public static final ArmorRegistry VOID = new ArmorRegistry.Builder("awakened_void").setVoidDefence(75).protection(70).mul(80).enchantValue(10).knockbackRes(0.15f).ingredient(() -> Ingredient.of(ItemsRegistry.voidIngot.get())).build();
    public static final ArmorRegistry PHANTASM = new ArmorRegistry.Builder("phantasm").setVoidDefence(100).protection(80).mul(85).enchantValue(12).knockbackRes(0.25f).ingredient(() -> Ingredient.of(ItemsRegistry.illusionStone.get())).build();

    public static class Builder extends AbstractArmorBuilder<ArmorRegistry>{
        public Builder(String name){
            super(name);
        }

        public Builder setVoidDefence(float def) {
            addAttr(AttributeReg.NIHILITY_RESISTANCE, new AttributeData(def, Operation.ADDITION));
            return this;
        }

        @Override
        public ArmorRegistry build(){
            return new ArmorRegistry(this, data, hitData);
        }
    }
}