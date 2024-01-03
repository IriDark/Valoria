package com.idark.valoria.item.types;

import com.idark.valoria.client.render.model.item.ItemAnims;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomAnimationItem {
    @OnlyIn(Dist.CLIENT)
    ItemAnims getAnimation(ItemStack stack);
}