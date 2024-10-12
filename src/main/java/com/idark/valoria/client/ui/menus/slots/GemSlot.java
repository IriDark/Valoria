package com.idark.valoria.client.ui.menus.slots;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.skins.*;
import net.minecraft.world.item.*;
import net.minecraftforge.items.*;

public class GemSlot extends SlotItemHandler {

    public GemSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public static boolean isGems(ItemStack pStack) {
        return pStack.is(TagsRegistry.GEMS) || pStack.getItem() instanceof SkinTrimItem;
    }

    public boolean mayPlace(ItemStack pStack) {
        return isGems(pStack);
    }
}