package com.idark.valoria.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ManaItemUtils {
    public static int getMana(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt.getInt("mana");
    }

    public static void setMana(ItemStack stack, int mana) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("mana", mana);
    }

    public static void addMana(ItemStack stack, int mana, int max) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("mana", nbt.getInt("mana") + mana);
        if (max < nbt.getInt("mana")) {
            nbt.putInt("mana", max);
        }
    }

    public static int addManaRemain(ItemStack stack, int mana) {
        int remain = 0;
        CompoundTag nbt = stack.getTag();
        int max = nbt.getInt("max");
        nbt.putInt("mana", nbt.getInt("mana") + mana);
        if (max < nbt.getInt("mana")) {
            remain = nbt.getInt("mana") - max;
            nbt.putInt("mana", max);
        }
        return remain;
    }

    public static void removeMana(ItemStack stack, int mana) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("mana", nbt.getInt("mana") - mana);
        if (nbt.getInt("mana") < 0) {
            nbt.putInt("mana", 0);
        }
    }

    public static int getAddManaRemain(ItemStack stack, int mana, int max) {
        int remain = 0;
        CompoundTag nbt = stack.getTag();
        if (max < nbt.getInt("mana") + mana) {
            remain = (nbt.getInt("mana") + mana) - max;
        }
        return remain;
    }

    public static int getRemoveManaRemain(ItemStack stack, int mana) {
        int remain = 0;
        CompoundTag nbt = stack.getTag();
        if (0 < nbt.getInt("mana") - mana) {
            remain = -(nbt.getInt("mana") - mana);
        }
        return remain;
    }

    public static boolean canAddMana(ItemStack stack, int mana, int max) {
        CompoundTag nbt = stack.getTag();
        return (max >= nbt.getInt("mana") + mana);
    }

    public static boolean canRemoveMana(ItemStack stack, int mana) {
        CompoundTag nbt = stack.getTag();
        return (0 <= nbt.getInt("mana") - mana);
    }

    public static void existMana(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("mana")) {
            nbt.putInt("mana", 0);
        }
    }
}