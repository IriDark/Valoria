package com.idark.valoria.api.unlockable.types;

import net.minecraft.tags.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

import java.util.*;

public class TagUnlockable extends Unlockable{
    public final TagKey<Item> item;
    public TagUnlockable(String id, TagKey<Item> item){
        super(id);
        this.item = item;
    }

    @Override
    public boolean canObtain(Player player){
        List<ItemStack> items = player.getInventory().items;
        for (ItemStack stack : items) {
            return stack.is(item);
        }

        return false;
    }
}
