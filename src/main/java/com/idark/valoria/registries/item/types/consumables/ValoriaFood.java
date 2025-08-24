package com.idark.valoria.registries.item.types.consumables;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ValoriaFood extends Item{
    float voidHarm;
    public ValoriaFood(float voidHarm, Properties pProperties){
        super(pProperties);
        this.voidHarm = voidHarm;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced){
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tooltip.valoria.raw_food", voidHarm).withStyle(ChatFormatting.GRAY).withStyle(style -> style.withFont(Valoria.FONT)));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity){
        pLivingEntity.getCapability(INihilityLevel.INSTANCE).ifPresent(k -> {
            k.modifyAmount(pLivingEntity, voidHarm);
        });

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }
}
