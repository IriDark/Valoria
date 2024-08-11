package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.player.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.*;

/**
 * The main functional done in a CriticalHitEvent
 * @see com.idark.valoria.Events#critDamage(CriticalHitEvent) here
 */
public class RuneAccuracy extends Item implements ICurioItem{

    public RuneAccuracy(Properties properties){
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack){
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        return ValoriaUtils.onePerTypeEquip(slotContext, stack);
    }

    @Override
    public boolean isEnchantable(ItemStack pStack){
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.crit").withStyle(ChatFormatting.GRAY));
    }
}