package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import pro.komaru.tridot.client.gfx.text.*;

import java.util.*;

public class ValoriaPickaxe extends PickaxeItem{
    public ValoriaPickaxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, pProperties.attributes(PickaxeItem.createAttributes(pTier, (int)pAttackDamageModifier, pAttackSpeedModifier)));
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext level, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.valoria.efficiency", getTier().getSpeed()).withStyle(ChatFormatting.GRAY).withStyle(DotStyle.of().font(Valoria.FONT)));
        tooltip.add(Component.translatable("tooltip.valoria.harvest", ItemTierRegistry.levelOf(getTier())).withStyle(ChatFormatting.GRAY).withStyle(DotStyle.of().font(Valoria.FONT)));
    }
}
