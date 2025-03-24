package com.idark.valoria.registries.item.types;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class ValoriaPickaxe extends PickaxeItem{
    public ValoriaPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.valoria.efficiency", speed).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.valoria.harvest", getTier().getLevel()).withStyle(ChatFormatting.GRAY));
    }
}
