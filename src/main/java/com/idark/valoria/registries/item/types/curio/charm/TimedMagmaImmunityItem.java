package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class TimedMagmaImmunityItem extends ValoriaCurioItem{
    private final int time;
    public TimedMagmaImmunityItem(int sec, Properties properties){
        super(properties);
        this.time = sec;
    }

    @Override
    public int immunityTime(){
        return time;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack){
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magma -> {
                magma.addMaxAmount(player, time);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magma -> {
                magma.decreaseMaxAmount(player,time);
            });
        }
    }
}