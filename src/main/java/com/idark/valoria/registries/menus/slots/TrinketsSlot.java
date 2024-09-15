package com.idark.valoria.registries.menus.slots;

import com.idark.valoria.registries.*;
import net.minecraft.world.item.*;
import net.minecraftforge.items.*;

public class TrinketsSlot extends SlotItemHandler {

    public TrinketsSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public static boolean isTrinkets(ItemStack pStack) {
        return pStack.is(TagsRegistry.TRINKETS);
    }

    public boolean mayPlace(ItemStack pStack) {
        return isTrinkets(pStack);
    }
}