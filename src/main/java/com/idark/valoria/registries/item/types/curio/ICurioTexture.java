package com.idark.valoria.registries.item.types.curio;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ICurioTexture{
    ResourceLocation getTexture(ItemStack stack, LivingEntity entity);
}