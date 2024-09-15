package com.idark.valoria.registries.item.types.curio.charm;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.List;

/**
 * The main functional done in a CriticalHitEvent
 *
 * @see com.idark.valoria.Events#critDamage(CriticalHitEvent) here
 */
public class RuneAccuracy extends CurioRune {
    public RuneAccuracy(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.crit").withStyle(ChatFormatting.GRAY));
    }
}