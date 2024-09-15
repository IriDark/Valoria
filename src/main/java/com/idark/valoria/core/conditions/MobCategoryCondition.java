package com.idark.valoria.core.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class MobCategoryCondition implements LootItemCondition {
    private final MobCategory category;

    public MobCategoryCondition(MobCategory category) {
        this.category = category;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (lootContext.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof LivingEntity livingEntity) {
            return livingEntity.getType().getCategory() == this.category;
        }
        return false;
    }

    @Override
    public LootItemConditionType getType() {
        return LootConditionsRegistry.MOB_CATEGORY_CONDITION;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MobCategoryCondition> {
        @Override
        public void serialize(JsonObject json, MobCategoryCondition condition, JsonSerializationContext context) {
            json.addProperty("mob_category", condition.category.getName());
        }

        @Override
        public MobCategoryCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            String categoryStr = GsonHelper.getAsString(json, "mob_category");
            return new MobCategoryCondition(MobCategory.byName(categoryStr));
        }
    }
}