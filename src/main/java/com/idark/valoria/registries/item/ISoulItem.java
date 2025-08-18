package com.idark.valoria.registries.item;

import com.idark.valoria.registries.*;
import com.idark.valoria.util.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public interface ISoulItem{
    default boolean barVisible(ItemStack pStack){
        return getCurrentSouls(pStack) > 0 && getCurrentSouls(pStack) < getMaxSouls();
    }

    default ItemStack setSoulItem(ItemStack pStack){
        pStack.getOrCreateTag().putInt("Souls", getBaseSouls());
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
        return pStack.getOrCreateTag().getInt("Souls");
    }

    default void setSouls(int count, ItemStack pStack){
        pStack.removeTagKey("Souls");
        pStack.getOrCreateTag().putInt("Souls", count);
    }

    default SoundEvent getCollectSound(){
        return SoundsRegistry.SOUL_COLLECT.get();
    }

    default void consumeSouls(int count, ItemStack pStack){
        pStack.getOrCreateTag().putInt("Souls", Math.max(this.getCurrentSouls(pStack) - count, 0));
    }

    default void addCount(int count, ItemStack pStack, Player player){
        if(getCurrentSouls(pStack) < getMaxSouls()){
            pStack.getOrCreateTag().putInt("Souls", getCurrentSouls(pStack) + count);
            player.level().playSound(null, player.getOnPos(), getCollectSound(), SoundSource.PLAYERS, 1, player.level().random.nextFloat());
        }
    }
}
