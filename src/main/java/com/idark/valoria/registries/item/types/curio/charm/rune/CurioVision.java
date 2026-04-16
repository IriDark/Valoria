package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.google.common.collect.*;
import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import pro.komaru.tridot.api.*;

import java.util.*;

public class CurioVision extends AbstractRuneItem implements InputListener{
    int duration;
    public CurioVision(Properties properties, int pDuration){
        super(properties);
        this.duration = pDuration;
    }

    public void applyEffects(Player player, ItemStack stack){
        if(!player.getCooldowns().isOnCooldown(stack.getItem())){
            if(!player.hasEffect(MobEffects.NIGHT_VISION)){
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, duration));
                player.getCooldowns().addCooldown(stack.getItem(), duration + 300);
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

    @Override
    public void onInput(ServerPlayer player, ItemStack stack, int event){
        if(event == 0) {
            applyEffects(player, stack);
        }
    }
}