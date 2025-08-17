package com.idark.valoria.client.ui.menus.slots;

import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.item.*;
import net.minecraftforge.items.*;
import org.jetbrains.annotations.*;

import javax.annotation.*;

public class SoulCollectorSlot extends SlotItemHandler{

    public SoulCollectorSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack){
        return 1;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return stack.getItem() instanceof SoulCollectorItem;
    }
}
