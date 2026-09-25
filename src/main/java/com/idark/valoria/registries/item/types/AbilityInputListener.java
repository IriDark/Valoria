package com.idark.valoria.registries.item.types;

import net.minecraft.world.item.*;
import pro.komaru.tridot.util.struct.data.*;

public interface AbilityInputListener {
    Seq<CurioAbility> getCurioAbilities(ItemStack stack);
}
