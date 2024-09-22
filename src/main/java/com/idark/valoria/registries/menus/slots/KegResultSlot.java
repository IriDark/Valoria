package com.idark.valoria.registries.menus.slots;

import com.idark.valoria.registries.*;
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

    public int findSlotMatchingItem(Player player, Item item) {
        for(int i = 0; i < player.getInventory().items.size(); ++i) {
            if (player.getInventory().getItem(i).is(item)) {
                return i;
            }
        }

        return -1;
    }

    //fixme
    public boolean hasAny(Player player) {
        ItemStack itemStack = tile.itemOutputHandler.getStackInSlot(0);
        if(!player.getInventory().getItem(findSlotMatchingItem(player, ItemsRegistry.BOTTLE.get())).isEmpty() && itemStack.is(TagsRegistry.BOTTLE_DRINKS)){
            player.getInventory().getItem(findSlotMatchingItem(player, ItemsRegistry.BOTTLE.get())).shrink(1);
            return true;
        }

        if(player.getInventory().hasAnyMatching((p) -> p.is(ItemsRegistry.WOODEN_CUP.get())) && itemStack.is(TagsRegistry.CUP_DRINKS)){
            player.getInventory().getItem(findSlotMatchingItem(player, ItemsRegistry.WOODEN_CUP.get())).shrink(1);
            return true;
        }

        return false;
    }

    @Override
    public boolean mayPickup(Player playerIn){
        return super.mayPickup(playerIn) && hasAny(playerIn);
    }
}