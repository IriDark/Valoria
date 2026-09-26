package com.idark.valoria.core.interfaces;

import net.minecraft.core.component.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;

public interface DyeableItem{
    int DEFAULT_LEATHER_COLOR = DyedItemColor.LEATHER_COLOR;
    default int getColor(ItemStack stack){
        return DyedItemColor.getOrDefault(stack, DEFAULT_LEATHER_COLOR);
    }

    default boolean hasCustomColor(ItemStack stack){
        return stack.has(DataComponents.DYED_COLOR);
    }

    default void setColor(ItemStack stack, int color){
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true));
    }

    default void clearColor(ItemStack stack){
        stack.remove(DataComponents.DYED_COLOR);
    }
}
