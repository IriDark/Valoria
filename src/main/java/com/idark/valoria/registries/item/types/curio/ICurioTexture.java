package com.idark.valoria.registries.item.types.curio;

import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface ICurioTexture{
    ResourceLocation getTexture(ItemStack stack, LivingEntity entity);
}