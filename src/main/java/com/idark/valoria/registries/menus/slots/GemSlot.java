package com.idark.valoria.registries.menus.slots;

import com.idark.valoria.registries.TagsRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GemSlot extends SlotItemHandler {

    public GemSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition) {
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public boolean mayPlace(ItemStack pStack) {
        return isGems(pStack);
    }

    public static boolean isGems(ItemStack pStack) {
        return pStack.is(TagsRegistry.GEMS);
    }
}