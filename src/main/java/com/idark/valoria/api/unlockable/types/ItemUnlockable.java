package com.idark.valoria.api.unlockable.types;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

import java.util.*;

public class ItemUnlockable extends Unlockable{
    public final Item item;
    public ItemUnlockable(String id, Item item){
        super(id);
        this.item = item;
    }

    public ItemUnlockable(String id, boolean rndObtain, Item item){
        super(id, rndObtain);
        this.item = item;
    }

    @Override
    public boolean canObtain(Player player){
        List<ItemStack> items = player.getInventory().items;
        for (ItemStack stack : items) {
            if(stack.is(item)) return true;
        }

        return false;
    }
}
