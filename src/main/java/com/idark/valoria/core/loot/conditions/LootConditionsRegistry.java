package com.idark.valoria.core.loot.conditions;

import net.minecraft.core.registries.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class LootConditionsRegistry{
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, "valoria");

    public static final RegistryObject<LootItemConditionType> UNLOCKABLE_CONDITION = LOOT_CONDITION_TYPES.register("unlockable", () -> new LootItemConditionType(new UnlockableCondition.Serializer()));

    public static void init(IEventBus bus) {
        LOOT_CONDITION_TYPES.register(bus);
    }
}