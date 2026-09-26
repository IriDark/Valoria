package com.idark.valoria.registries.block.types;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;

import java.util.*;

public class DescriptionBlock extends Block{
    public MutableComponent pTooltip;

    public DescriptionBlock(MutableComponent pTooltip, Properties pProperties){
        super(pProperties);
        this.pTooltip = pTooltip;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag){
        super.appendHoverText(pStack, pContext, pTooltip, pFlag);
        pTooltip.add(this.pTooltip);
    }
}
