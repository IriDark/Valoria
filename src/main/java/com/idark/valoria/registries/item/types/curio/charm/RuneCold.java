package com.idark.valoria.registries.item.types.curio.charm;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class RuneCold extends AbstractCharm{
    public RuneCold(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(player.getTicksFrozen() > 0){
            if(arcRandom.chance(0.005f)){
                stack.hurtAndBreak(1, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }

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
}
