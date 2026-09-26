package com.idark.valoria.core.loot.conditions;

import com.idark.valoria.core.config.*;
import com.mojang.serialization.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.jetbrains.annotations.*;

public class FirstJoinRewardCondition implements LootItemCondition {
    private static final FirstJoinRewardCondition INSTANCE = new FirstJoinRewardCondition();
    public static final MapCodec<FirstJoinRewardCondition> CODEC = MapCodec.unit(INSTANCE);

    private FirstJoinRewardCondition() {}

    @Override
    @NotNull
    public LootItemConditionType getType() {
        return LootConditionsRegistry.FIRST_JOIN_REWARD.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return ServerConfig.ENABLE_FIRST_JOIN_REWARD.get();
    }

    public static Builder condition() {
        return () -> INSTANCE;
    }
}