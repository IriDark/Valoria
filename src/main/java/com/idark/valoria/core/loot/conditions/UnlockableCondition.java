package com.idark.valoria.core.loot.conditions;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import org.jetbrains.annotations.*;

public record UnlockableCondition(String id) implements LootItemCondition{
    public static final MapCodec<UnlockableCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(UnlockableCondition::id)
    ).apply(instance, UnlockableCondition::new));

    @NotNull
    public LootItemConditionType getType(){
        return LootConditionsRegistry.UNLOCKABLE_CONDITION.get();
    }

    public boolean test(LootContext lootContext){
        var entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
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
}
