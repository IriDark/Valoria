package com.idark.valoria.client.ui.menus;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.recipe.KilnRecipe.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;

public class KilnMenu extends AbstractFurnaceMenu{
    public KilnMenu(int pContainerId, Inventory pPlayerInventory) {
        super(MenuRegistry.KILN_MENU.get(), Type.INSTANCE, RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    public KilnMenu(int pContainerId, Inventory pPlayerInventory, Container pFurnaceContainer, ContainerData pFurnaceData) {
        super(MenuRegistry.KILN_MENU.get(), Type.INSTANCE, RecipeBookType.FURNACE, pContainerId, pPlayerInventory, pFurnaceContainer, pFurnaceData);
    }
}
