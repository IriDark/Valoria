package com.idark.valoria.client.ui.menus.slots;

import com.idark.valoria.registries.TagsRegistry;
import com.idark.valoria.registries.item.skins.SkinTrimItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GemSlot extends SlotItemHandler{

    public GemSlot(IItemHandler itemHandler, int pSlot, int pXPosition, int pYPosition){
        super(itemHandler, pSlot, pXPosition, pYPosition);
    }

    public static boolean isGems(ItemStack pStack){
        return pStack.is(TagsRegistry.GEMS) || pStack.getItem() instanceof SkinTrimItem;
    }

    public boolean mayPlace(ItemStack pStack){
        return isGems(pStack);
    }
}