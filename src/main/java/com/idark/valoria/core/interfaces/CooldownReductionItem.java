package com.idark.valoria.core.interfaces;

import com.idark.valoria.registries.*;
import net.minecraft.world.item.*;

public interface CooldownReductionItem{
    default int getCooldownReduction(ItemStack stack) {
        return stack.getEnchantmentLevel(EnchantmentsRegistry.OVERDRIVE.get()) * 5;
    }
}
