package com.idark.valoria.registries.item.component;

import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.inventory.tooltip.*;

import javax.annotation.*;
import java.util.*;

public record ClientEffectsListClientComponent(List<MobEffectInstance> list, @Nullable Component component) implements TooltipComponent{
}