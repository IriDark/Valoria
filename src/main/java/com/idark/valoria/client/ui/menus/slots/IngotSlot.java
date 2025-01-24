package com.idark.valoria.client.ui.menus.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class IngotSlot extends SlotItemHandler{

    public IngotSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return stack.is(Tags.Items.INGOTS);
    }
}
