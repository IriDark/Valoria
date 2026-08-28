package com.idark.valoria.registries.item.types.ranged.bows;

import com.idark.valoria.registries.entity.projectile.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WickedArrowItem extends ArrowItem implements DispensedArrow{
    public WickedArrowItem(Item.Properties pProperties){
        super(pProperties);
    }

    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter){
        return new WickedArrow(pLevel, pShooter, pStack);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack){
        return new WickedArrow(pLevel, pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(Component.translatable("tooltip.valoria.arrow_damage", 4).withStyle(ChatFormatting.GRAY));
        pTooltip.add(Component.translatable("tooltip.valoria.wicked_arrow.effect").withStyle(ChatFormatting.DARK_PURPLE));
    }
}