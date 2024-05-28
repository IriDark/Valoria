package com.idark.valoria.registries.item.types.curio;

import net.minecraft.world.item.*;
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
}
