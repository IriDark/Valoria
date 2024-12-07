package com.idark.valoria.registries.block.types;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;

import javax.annotation.*;
import java.util.*;

public class DescriptionBlock extends Block{
    public MutableComponent pTooltip;
    public DescriptionBlock(MutableComponent pTooltip, Properties pProperties){
        super(pProperties);
        this.pTooltip = pTooltip;
    }

    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(this.pTooltip);
    }
}
