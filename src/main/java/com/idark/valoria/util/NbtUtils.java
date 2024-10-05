package com.idark.valoria.util;

import net.minecraft.nbt.*;
import net.minecraft.world.item.*;

//todo remove
public class NbtUtils {
    public static int readNbt(ItemStack stack, String name) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(name)) {
            return nbt.getInt(name);
        }

        return 0;
    }

    public static void writeIntNbt(ItemStack stack, String name, int value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(name, value);
    }

    public static void addCharge(ItemStack stack, int charge) {
        CompoundTag nbt = stack.getOrCreateTag();
        int charges = nbt.getInt("charge");
        nbt.putInt("charge", charges + charge);
    }

    public static void writeDoubleNbt(ItemStack stack, String name, double value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putDouble(name, value);
    }

    public static void writeFloatNbt(ItemStack stack, String name, float value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putFloat(name, value);
    }

    public static void writeBooleanNbt(ItemStack stack, String name, boolean value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean(name, value);
    }

    public static void writeStringNbt(ItemStack stack, String name, String value) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString(name, value);
    }
}
