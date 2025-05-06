package com.idark.valoria.registries.item.types.curio.charm.rune;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class RuneCold extends AbstractRuneItem{
    public RuneCold(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(player.getTicksFrozen() > 0){
            player.setTicksFrozen(0);
        }
    }

    @Override
    public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack){
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.cold").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public RuneType runeType(){
        return RuneType.ICE;
    }
}
