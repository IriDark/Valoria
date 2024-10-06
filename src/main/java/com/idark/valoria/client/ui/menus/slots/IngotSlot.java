package com.idark.valoria.client.ui.menus.slots;

import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.items.*;

import javax.annotation.*;

public class IngotSlot extends SlotItemHandler {

    public IngotSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return stack.is(Tags.Items.INGOTS);
    }
}
