package com.idark.valoria.registries.item.interfaces;

import com.idark.valoria.client.render.model.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;

public interface ICustomAnimationItem{
    @OnlyIn(Dist.CLIENT)
    ItemAnims getAnimation(ItemStack stack);
}