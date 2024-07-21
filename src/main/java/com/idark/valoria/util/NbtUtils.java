package com.idark.valoria.util;

import net.minecraft.nbt.*;
import net.minecraft.world.item.*;


public class NbtUtils{
    public static int readNbt(ItemStack stack, String name){
        CompoundTag nbt = stack.getOrCreateTag();
        if(nbt.contains(name)){
            return nbt.getInt(name);
        }

        return 0;
    }

    public static void writeIntNbt(ItemStack stack, String name, int value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(name, value);
        stack.setTag(nbt);
    }

    public static void writeDoubleNbt(ItemStack stack, String name, double value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putDouble(name, value);
    }

    public static void writeFloatNbt(ItemStack stack, String name, float value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putFloat(name, value);
    }

    public static void writeBooleanNbt(ItemStack stack, String name, boolean value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean(name, value);
    }

    public static void writeStringNbt(ItemStack stack, String name, String value){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString(name, value);
    }
}
