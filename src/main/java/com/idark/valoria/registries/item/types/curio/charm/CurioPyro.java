package com.idark.valoria.registries.item.types.curio.charm;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.core.math.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioPyro extends CurioRune{
    public ArcRandom arcRandom = new ArcRandom();

    public CurioPyro(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(!player.level().isClientSide() && !player.hasEffect(MobEffects.FIRE_RESISTANCE)){
            if(player.isInLava() || player.isOnFire()){
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200));
                if(arcRandom.fiftyFifty()){
                    stack.hurtAndBreak(1, player, (p_220045_0_) -> p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.pyro").withStyle(ChatFormatting.GRAY));
    }
}