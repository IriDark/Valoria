package com.idark.valoria.core.interfaces;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

public interface IAuthorItemSkin{
    Component getContributorComponent(ItemStack stack);
}
