package com.idark.valoria.registries.block.types;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class DescriptionBlock extends Block{
    public MutableComponent pTooltip;

    public DescriptionBlock(MutableComponent pTooltip, Properties pProperties){
        super(pProperties);
        this.pTooltip = pTooltip;
    }

    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(this.pTooltip);
    }
}
