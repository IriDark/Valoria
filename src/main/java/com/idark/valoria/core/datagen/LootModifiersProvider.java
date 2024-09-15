package com.idark.valoria.core.datagen;

import com.idark.valoria.Valoria;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class LootModifiersProvider extends GlobalLootModifierProvider {
    public LootModifiersProvider(PackOutput output) {
        super(output, Valoria.ID);
    }

    @Override
    protected void start() {
        //todo datagen
//        add("candy_corn_from_mobs", new AddItemModifier(new LootItemCondition[]{
//        LocalDateCondition.time(IntRange.range(1, 30), IntRange.exact(9)).build(), new MobCategoryCondition(MobCategory.MONSTER)},
//        ItemsRegistry.CANDY_CORN.get(), 1, 1));
    }
}
