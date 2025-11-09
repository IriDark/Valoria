package com.idark.valoria.client.ui.screen;

import com.idark.valoria.registries.*;
import net.minecraft.world.item.*;

public enum AlchemyCategories{
    ALL("all", Items.COMPASS),
    POTIONS("potions", ItemsRegistry.healingElixir.get()),
    EQUIPMENT("equipment", ItemsRegistry.gasMask.get());

    public final String category;
    public final Item item;
    AlchemyCategories(String category, Item item){
        this.item = item;
        this.category = category;
    }
}