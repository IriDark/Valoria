package com.idark.valoria.registries.item.armor.tiers;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import net.minecraft.world.item.ArmorItem.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class ArmorRegistry implements ArmorMaterial{
    public static final ArmorRegistry COBALT = new ArmorRegistry("cobalt"); {{
        durabilityMultiplier = 46;
        equipSound = SoundEvents.ARMOR_EQUIP_IRON;
        toughness = 1;
        knockbackResistance = 0.05f;
        repairIngredient = () -> Ingredient.of(ItemsRegistry.cobaltIngot.get());
        setProtection(4, 10, 8, 4);
    }}

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
