package com.idark.valoria.registries.item;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public interface ISoulItem{
    String LEGACY_KEY = "Souls";

    default boolean barVisible(ItemStack pStack){
        return getCurrentSouls(pStack) > 0 && getCurrentSouls(pStack) < getMaxSouls();
    }

    default ItemStack setSoulItem(ItemStack pStack){
        pStack.set(DataComponentsRegistry.SOULS, getBaseSouls());
        return pStack;
    }

    default int barWidth(ItemStack pStack){
        return Math.round((float)getCurrentSouls(pStack) * 13.0F / (float)getMaxSouls());
    }

    default int barColor(){
        return Pal.oceanic.rgb();
    }

    int getBaseSouls();

    int getMaxSouls();

    default int getCurrentSouls(ItemStack pStack) {
        return DataComponentsRegistry.getInt(pStack, DataComponentsRegistry.SOULS.get(), LEGACY_KEY);
    }

    default void setSouls(int count, ItemStack pStack){
        pStack.set(DataComponentsRegistry.SOULS, count);
    }

    default SoundEvent getCollectSound(){
        return SoundsRegistry.SOUL_COLLECT.get();
    }

    default void consumeSouls(int count, ItemStack pStack){
        pStack.set(DataComponentsRegistry.SOULS, Math.max(this.getCurrentSouls(pStack) - count, 0));
    }

    default void addCount(int count, ItemStack pStack, Player player){
        if(getCurrentSouls(pStack) < getMaxSouls()){
            pStack.set(DataComponentsRegistry.SOULS, getCurrentSouls(pStack) + count);
            player.level().playSound(null, player.getOnPos(), getCollectSound(), SoundSource.PLAYERS, 1, player.level().random.nextFloat());
        }
    }
}
