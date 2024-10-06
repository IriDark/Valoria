package com.idark.valoria.client.ui.menus.slots;

import net.minecraft.world.item.*;
import net.minecraftforge.items.*;

import javax.annotation.*;

public class SmithingTemplateSlot extends SlotItemHandler {
    public SmithingTemplateSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof SmithingTemplateItem;
    }
}
