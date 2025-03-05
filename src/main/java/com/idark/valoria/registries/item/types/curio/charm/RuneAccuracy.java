package com.idark.valoria.registries.item.types.curio.charm;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.curio.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.player.*;

import java.util.*;

/**
 * The main functional done in a CriticalHitEvent
 *
 * @see com.idark.valoria.Events#critDamage(CriticalHitEvent) here
 */
public class RuneAccuracy extends AbstractCurioItem{
    public RuneAccuracy(Properties properties){
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.crit", stack.is(ItemsRegistry.runeAccuracy.get()) ? "50%" : "25%").withStyle(ChatFormatting.GRAY));
    }
}