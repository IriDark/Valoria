package com.idark.valoria.core.loot.conditions;

import com.google.gson.*;
import com.idark.valoria.core.config.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.jetbrains.annotations.*;

public class FirstJoinRewardCondition implements LootItemCondition {
    private static final FirstJoinRewardCondition INSTANCE = new FirstJoinRewardCondition();

    private FirstJoinRewardCondition() {}

    @NotNull
    public LootItemConditionType getType(){
        return LootConditionsRegistry.FIRST_JOIN_REWARD.get();
    }

    public boolean test(LootContext lootContext){
        return ServerConfig.ENABLE_FIRST_JOIN_REWARD.get();
    }

    public static FirstJoinRewardCondition.Builder condition(){
        return new FirstJoinRewardCondition.Builder();
    }

    public static class Builder implements LootItemCondition.Builder {
        public FirstJoinRewardCondition build(){
            return INSTANCE;
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FirstJoinRewardCondition> {
        @Override
        public void serialize(JsonObject json, FirstJoinRewardCondition condition, JsonSerializationContext context){}

        @Override
        public FirstJoinRewardCondition deserialize(JsonObject json, JsonDeserializationContext context){
            return INSTANCE;
        }
    }
}
