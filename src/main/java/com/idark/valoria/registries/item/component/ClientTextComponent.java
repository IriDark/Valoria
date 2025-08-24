package com.idark.valoria.registries.item.component;

import net.minecraft.network.chat.*;
import net.minecraft.world.inventory.tooltip.*;

public record ClientTextComponent(MutableComponent component) implements TooltipComponent{
}