package com.idark.valoria.registries.item.ability.itemComponents;

import com.idark.valoria.registries.item.ability.AbilityHelper.*;
import net.minecraft.world.inventory.tooltip.*;

import java.util.*;

public record AbilityPaginationComponent(List<ActiveAbility> list, int currentPage) implements TooltipComponent{
}