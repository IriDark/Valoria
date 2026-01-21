package com.idark.valoria.core.compat.jade;

import com.idark.valoria.core.interfaces.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

public class EffectiveWeaponHandler {
    private final String name;
    private final Supplier<List<ItemStack>> items;

    public EffectiveWeaponHandler(String name, Supplier<List<ItemStack>> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ItemStack test(IEffectiveWeaponEntity entity) {
        for (ItemStack item : items.get()) {
            if(item.is(entity.getEffective())) {
                return item;
            }
        }

        return ItemStack.EMPTY;
    }

    public List<ItemStack> getWeapons(){
        return items.get();
    }
}