package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.*;
import com.idark.valoria.registries.item.types.curio.hands.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class JewelryBagItem extends DyeableCurioItem{
    public JewelryBagItem(Properties pProperties){
        super(pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.trinkets_bag", ValoriaClient.BAG_MENU_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GRAY));
    }
}
