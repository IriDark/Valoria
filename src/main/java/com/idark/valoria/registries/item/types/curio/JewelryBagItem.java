package com.idark.valoria.registries.item.types.curio;

import com.idark.valoria.*;
import net.minecraft.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class JewelryBagItem extends DyeableCurioItem{
    public JewelryBagItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack){
        List<ItemStack> items = new ArrayList<>();
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(slotContext.getWearer(), stack.getItem());
        for(SlotResult slot : curioSlots){
            items.add(slot.stack());
        }

        return items.isEmpty() || slotContext.cosmetic();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("tooltip.valoria.trinkets_bag", ValoriaClient.BAG_MENU_KEY.getKey().getDisplayName()).withStyle(ChatFormatting.GRAY));
    }
}
