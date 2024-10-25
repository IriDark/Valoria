package com.idark.valoria.registries.item.armor;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.armor.item.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ArmorItem.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class ArmorRegistry implements ArmorMaterial{
    public static final ArmorRegistry COBALT = new ArmorRegistry("cobalt") {{
        durabilityMultiplier = 46;
        equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        toughness = 1;
        knockbackResistance = 0.05f;
        enchantmentValue = 18;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.cobaltIngot.get());
        setProtection(4, 10, 8, 4);
    }};

    public static final ArmorRegistry SAMURAI = new ArmorRegistry("samurai") {{
        durabilityMultiplier = 55;
        equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        toughness = 2.5f;
        knockbackResistance = 0.15f;
        enchantmentValue = 16;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(6, 12, 10, 5);
    }};

    public static final ArmorRegistry NATURE = new ArmorRegistry("nature") {{
        durabilityMultiplier = 66;
        equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        toughness = 3f;
        enchantmentValue = 17;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(6, 10, 9, 5);
    }};

    public static final ArmorRegistry DEPTH = new ArmorRegistry("depth") {{
        durabilityMultiplier = 72;
        equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        toughness = 3.6f;
        enchantmentValue = 15;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(6, 11, 9, 6);
    }};

    public static final ArmorRegistry INFERNAL = new ArmorRegistry("infernal") {{
        durabilityMultiplier = 76;
        equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
        toughness = 4.5f;
        knockbackResistance = 0.1f;
        enchantmentValue = 14;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(7, 12, 9, 7);
    }};

    public static final ArmorRegistry VOID = new ArmorRegistry("awakened_void") {{
        durabilityMultiplier = 82;
        equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
        toughness = 4.5f;
        knockbackResistance = 0.2f;
        enchantmentValue = 10;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(8, 12, 10, 8);
    }};

    public static final ArmorRegistry PHANTASM = new ArmorRegistry("phantasm") {{
        durabilityMultiplier = 96;
        equipSound = SoundEvents.ARMOR_EQUIP_NETHERITE;
        toughness = 30;
        knockbackResistance = 0.5f;
        enchantmentValue = 15;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.ancientIngot.get());
        setProtection(14, 18, 16, 12);
    }};

    public String name;
    public int durabilityMultiplier;
    public int headProtectionAmount;
    public int chestplateProtectionAmount;
    public int leggingsProtectionAmount;
    public int bootsProtectionAmount;
    public int enchantmentValue;
    public SoundEvent equipSound;
    public float toughness;
    public float knockbackResistance;
    public Supplier<Ingredient> repairIngredient;

    private static final int[] durability = {11, 16, 16, 13};
    public ArmorRegistry(String name) {
        this.name = name;
    }

    public void setProtection(int headProtectionAmount, int chestplateProtectionAmount, int leggingsProtectionAmount, int bootsProtectionAmount) {
        this.headProtectionAmount = headProtectionAmount;
        this.chestplateProtectionAmount = chestplateProtectionAmount;
        this.leggingsProtectionAmount = leggingsProtectionAmount;
        this.bootsProtectionAmount = bootsProtectionAmount;
    }

    @Override
    public int getDurabilityForType(Type pType){
        return durability[pType.ordinal()] * this.durabilityMultiplier;
    }

    public int getTotalDefense(LivingEntity entity){
        int armorValue = 0;
        if(!entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof PercentageArmorItem armor) {
            armorValue += armor.material.getDefenseForType(Type.HELMET);
        }

        if(!entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty() && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof PercentageArmorItem armor) {
            armorValue += armor.material.getDefenseForType(Type.CHESTPLATE);
        }

        if(!entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof PercentageArmorItem armor) {
            armorValue += armor.material.getDefenseForType(Type.LEGGINGS);
        }

        if(!entity.getItemBySlot(EquipmentSlot.FEET).isEmpty() && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof PercentageArmorItem armor) {
            armorValue += armor.material.getDefenseForType(Type.BOOTS);
        }

        return armorValue;
    }

    @Override
    public int getDefenseForType(Type pType){
        return switch(pType) {
            case HELMET -> headProtectionAmount;
            case CHESTPLATE -> chestplateProtectionAmount;
            case LEGGINGS -> leggingsProtectionAmount;
            case BOOTS -> bootsProtectionAmount;
        };
    }

    @Override
    public int getEnchantmentValue(){
        return enchantmentValue;
    }

    @Override
    @NotNull
    public SoundEvent getEquipSound(){
        return equipSound;
    }

    @Override
    @NotNull
    public Ingredient getRepairIngredient(){
        return repairIngredient.get();
    }

    @Override
    @NotNull
    public String getName(){
        return Valoria.ID + ":" + name;
    }

    @Override
    public float getToughness(){
        return toughness;
    }

    @Override
    public float getKnockbackResistance(){
        return knockbackResistance;
    }
}
