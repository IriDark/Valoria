package com.idark.valoria.core.interfaces;

import com.idark.valoria.client.model.animations.ItemAnims;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomAnimationItem {
    @OnlyIn(Dist.CLIENT)
    ItemAnims getAnimation(ItemStack stack);
}