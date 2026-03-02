package com.idark.valoria.core.interfaces;

import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

public interface ICurioTexture{
    ResourceLocation getTexture(ItemStack stack, LivingEntity entity);
}