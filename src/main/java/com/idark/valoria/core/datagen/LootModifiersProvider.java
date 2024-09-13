package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import net.minecraft.data.*;
import net.minecraftforge.common.data.*;

public class LootModifiersProvider extends GlobalLootModifierProvider{
    public LootModifiersProvider(PackOutput output) {
        super(output, Valoria.ID);
    }

    @Override
    protected void start() {
        //todo datagen
//        add("candy_corn_from_mobs", new AddItemModifier(new LootItemCondition[]{
//        LocalDateCondition.time(IntRange.range(1, 30), IntRange.exact(9)).build(),
//        TimeCheck.time(IntRange.range(6000, 13000)).build(),
//        new MobCategoryCondition(EntityCategoryPredicate.of(MobCategory.MONSTER), EntityTarget.DIRECT_KILLER)},
//        ItemsRegistry.CANDY_CORN.get(), 1, 1));
    }
}
