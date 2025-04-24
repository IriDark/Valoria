package com.idark.valoria.core.loot.conditions;

import com.google.gson.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.jetbrains.annotations.*;

public class UnlockableCondition implements LootItemCondition{
    public final String id;
    UnlockableCondition(String id){
        this.id = id;
    }

    @NotNull
    public LootItemConditionType getType(){
        return LootConditionsRegistry.UNLOCKABLE_CONDITION;
    }

    public boolean test(LootContext lootContext){
        var entity = lootContext.getParam(LootContextParams.THIS_ENTITY);
        if(entity instanceof Player plr){
            Unlockable unlockable = Unlockables.getUnlockable(id);
            return !UnlockUtils.isUnlocked(plr, unlockable);
        }

        return false;
    }

    public static UnlockableCondition.Builder unlockable(String id){
        return new UnlockableCondition.Builder(id);
    }

    public record Builder(String id) implements LootItemCondition.Builder{
        public UnlockableCondition build(){
            return new UnlockableCondition(id);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<UnlockableCondition>{
        @Override
        public void serialize(JsonObject json, UnlockableCondition condition, JsonSerializationContext context){
            json.add("id", context.serialize(condition.id));
        }

        @Override
        public UnlockableCondition deserialize(JsonObject json, JsonDeserializationContext context){
            String id = GsonHelper.getAsObject(json, "id", context, String.class);
            return new UnlockableCondition(id);
        }
    }
}
