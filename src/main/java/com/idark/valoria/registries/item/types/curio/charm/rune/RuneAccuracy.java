package com.idark.valoria.registries.item.types.curio.charm.rune;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.Event.*;

import java.util.*;

public class RuneAccuracy extends AbstractRuneItem implements CritDamageItem{
    float chance;
    float damageModifier;
    public RuneAccuracy(float chance, float damageModifier, Properties properties){
        super(properties);
        this.chance = chance;
        this.damageModifier = damageModifier;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.crit", String.format("%.1f%%", chance * 100)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public RuneType runeType(){
        return RuneType.ACCURACY;
    }

    /**
     * Called when a critical hit is successfully triggered by the player and the attack is fully charged, that means weak attack will be ignored.
     * <p>
     * Implementations can modify the damage, cancel the event, or apply
     * custom effects such as particles, sounds, or debuffs.
     */
    @Override
    public void critDamage(CriticalHitEvent event){
        if(arcRandom.chance(chance)){
            event.setResult(Result.ALLOW);
            event.setDamageModifier(damageModifier);
        }
    }
}