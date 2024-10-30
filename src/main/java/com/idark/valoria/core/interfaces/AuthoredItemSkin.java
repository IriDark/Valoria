package com.idark.valoria.core.interfaces;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

@FunctionalInterface
public interface AuthoredItemSkin {
    Component getContributorComponent(ItemStack stack);
}
