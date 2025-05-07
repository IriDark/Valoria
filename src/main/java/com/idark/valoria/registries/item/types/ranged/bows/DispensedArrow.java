package com.idark.valoria.registries.item.types.ranged.bows;

import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public interface DispensedArrow {
    AbstractArrow createArrow(Level pLevel, ItemStack pStack);
}
