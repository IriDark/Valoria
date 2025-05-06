package com.idark.valoria.registries.item.types.curio.charm.rune;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class RuneDeep extends AbstractRuneItem{
    public RuneDeep(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(!player.level().isClientSide() && !player.hasEffect(MobEffects.WATER_BREATHING)){
            if(player.isUnderWater() || player.isInWater()){
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.deep").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public RuneType runeType(){
        return RuneType.DEPTHS;
    }
}
