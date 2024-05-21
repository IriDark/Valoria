package com.idark.valoria.registries.menus.slots;

import com.idark.valoria.registries.TagsRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TrinketsSlot extends SlotItemHandler{

    public TrinketsSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition){
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public static boolean isTrinkets(ItemStack pStack){
        return pStack.is(TagsRegistry.TRINKETS);
    }

    public boolean mayPlace(ItemStack pStack){
        return isTrinkets(pStack);
    }
}