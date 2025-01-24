package com.idark.valoria.client.ui.menus.slots;

import com.idark.valoria.registries.block.entity.KegBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class KegResultSlot extends SlotItemHandler{
    public KegBlockEntity tile;

    public KegResultSlot(KegBlockEntity tile, IItemHandler itemHandler, int index, int xPosition, int yPosition){
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tile;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack){
        return false;
    }

    @Override
    public boolean mayPickup(Player playerIn){
        return false;
    }
}