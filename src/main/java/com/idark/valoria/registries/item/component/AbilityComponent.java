package com.idark.valoria.registries.item.component;

import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.inventory.tooltip.*;

public record AbilityComponent(MutableComponent component, ResourceLocation icon) implements TooltipComponent{
}