package com.idark.valoria.registries.item.types.shield;

import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.builders.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

public class CrabBucklerItem extends ConfiguredShield{

    public CrabBucklerItem(AbstractShieldBuilder<? extends ConfiguredShield> builder){
        super(builder);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        pTooltip.add(Component.translatable("tooltip.valoria.shield.thorns", String.format("%.1f%%", 15f)).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void onShieldBlock(DamageSource source, float pAmount, ItemStack itemStack, LivingEntity entity){
        super.onShieldBlock(source, pAmount, itemStack, entity);
        if(source.getDirectEntity() != null){
            source.getDirectEntity().hurt(entity.level().damageSources().thorns(entity), pAmount * 0.15f);
        }
    }
}