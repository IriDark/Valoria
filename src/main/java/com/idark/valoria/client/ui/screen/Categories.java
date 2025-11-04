package com.idark.valoria.client.ui.screen;

import com.idark.valoria.registries.*;
import net.minecraft.world.item.*;

public enum Categories{
    ALL("all", Items.COMPASS),
    MATERIALS("materials", ItemsRegistry.cobaltIngot.get()),
    EQUIPMENT("equipment", ItemsRegistry.fallenCollectorCoat.get()),
    TOOLS("tools", ItemsRegistry.etherealMultiTool.get());

    public final String category;
    public final Item item;
    Categories(String category, Item item){
        this.item = item;
        this.category = category;
    }
}