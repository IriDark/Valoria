package com.idark.valoria.registries.item.component;

import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.inventory.tooltip.*;

public record AbilityComponent(MutableComponent component, ResourceLocation icon, int paddingTop, int iconSize) implements TooltipComponent{
    public AbilityComponent(MutableComponent component, ResourceLocation icon){
        this(component, icon, 0, 18);
    }

    public AbilityComponent(MutableComponent component, ResourceLocation icon, int paddingTop){
        this(component, icon, paddingTop, 18);
    }
}