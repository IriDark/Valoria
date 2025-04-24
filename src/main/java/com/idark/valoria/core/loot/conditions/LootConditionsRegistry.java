package com.idark.valoria.core.loot.conditions;

import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.storage.loot.predicates.*;

public class LootConditionsRegistry{
    public static final LootItemConditionType UNLOCKABLE_CONDITION = new LootItemConditionType(new UnlockableCondition.Serializer());

    public static void register(){
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, new ResourceLocation("valoria", "unlockable"), UNLOCKABLE_CONDITION);
    }
}
