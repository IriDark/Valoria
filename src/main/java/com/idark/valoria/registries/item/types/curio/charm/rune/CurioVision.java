package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.collect.*;
import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioVision extends AbstractRuneItem{
    int duration;
    public CurioVision(Properties properties, int pDuration){
        super(properties);
        this.duration = pDuration;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(player.level().isClientSide() && ValoriaClient.JEWELRY_BONUSES_KEY.isDown()){
            if(!player.hasEffect(MobEffects.NIGHT_VISION)){
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, duration));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        Utils.Items.effectTooltip(ImmutableList.of(new MobEffectInstance(MobEffects.NIGHT_VISION, duration)), tooltip, 1, 1);

        tooltip.add(Component.translatable("tooltip.valoria.jewelry_bonus", ValoriaClient.JEWELRY_BONUSES_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public RuneType runeType(){
        return RuneType.VISION;
    }
}