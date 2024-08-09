package com.idark.valoria.registries.menus.slots;

import net.minecraft.world.item.*;
import net.minecraftforge.items.*;

import javax.annotation.*;

public class ResultSlot extends SlotItemHandler{

    public ResultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return false;
    }
}