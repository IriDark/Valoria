package com.idark.valoria.registries.menus.slots;

import com.idark.valoria.registries.block.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.items.*;

import javax.annotation.*;

public class KegResultSlot extends SlotItemHandler {
    public KegBlockEntity tile;
    public KegResultSlot(KegBlockEntity tile, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tile;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player playerIn){
        return false;
    }
}