package com.idark.valoria.registries.item.types;

import com.idark.valoria.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;

import java.util.*;

public class ValoriaPickaxe extends PickaxeItem{
    public ValoriaPickaxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties){
        super(pTier, (int)pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.valoria.efficiency", speed).withStyle(DotStyle.of().color(Col.lightGray).font(Valoria.FONT)));
        tooltip.add(Component.translatable("tooltip.valoria.harvest", getTier().getLevel()).withStyle(DotStyle.of().color(Col.lightGray).font(Valoria.FONT)));
    }
}
