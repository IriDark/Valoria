package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.core.capability.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class CurioPyro extends AbstractRuneItem{
    private final int time;
    public CurioPyro(int sec, Properties properties){
        super(properties);
        this.time = sec;
    }

    public int immunityTime() {
        return time;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack){
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof Player player) {
            IMagmaLevel.of(player).ifPresent(magma -> {
                magma.addMaxAmount(player, time);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            IMagmaLevel.of(player).ifPresent(magma -> {
                magma.decreaseMaxAmount(player,time);
            });
        }
    }

    @Override
    public RuneType runeType(){
        return RuneType.PYRO;
    }
}