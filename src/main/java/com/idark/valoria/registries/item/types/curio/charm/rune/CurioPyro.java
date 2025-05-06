package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioPyro extends AbstractRuneItem{
    public CurioPyro(Properties properties){
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack){
        Player player = (Player)slotContext.entity();
        if(!player.level().isClientSide() && !player.hasEffect(MobEffects.FIRE_RESISTANCE)){
            if(player.isInLava() || player.isOnFire()){
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.immunity", MobEffects.POISON.getDisplayName()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY)
        .append(Component.translatable("tooltip.tridot.value", Blocks.FIRE.getName()).withStyle(Styles.create(Pal.infernalBright))));
        tooltip.add(Component.empty());
    }

    @Override
    public RuneType runeType(){
        return RuneType.PYRO;
    }
}