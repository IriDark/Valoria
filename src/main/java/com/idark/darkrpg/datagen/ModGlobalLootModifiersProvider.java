package com.idark.darkrpg.datagen;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, DarkRPG.MOD_ID);
    }

    @Override
    protected void start() {

        add("miners_bag_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/abandoned_mineshaft")).build() }, ModItems.MINERS_BAG.get(), 1, 0.3f));
        add("miners_bag_from_buried_treasure", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build() }, ModItems.MINERS_BAG.get(), 1, 0.3f));
        add("miners_bag_from_underwater_ruin_big", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/underwater_ruin_big")).build() }, ModItems.MINERS_BAG.get(), 1, 0.3f));
        add("miners_bag_from_underwater_ruin_small", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/underwater_ruin_small")).build() }, ModItems.MINERS_BAG.get(), 1, 0.1f));

        add("gems_bag_from_abandoned_mineshaft", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/abandoned_mineshaft")).build() }, ModItems.GEM_BAG.get(),1, 0.3f));
        add("gems_bag_from_buried_treasure", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build() }, ModItems.GEM_BAG.get(),1, 0.3f));
        add("gems_bag_from_shipwreck_supply", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/shipwreck_supply")).build() }, ModItems.GEM_BAG.get(), 1, 0.2f));
        add("gems_bag_from_shipwreck_treasure", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/shipwreck_treasure")).build() }, ModItems.GEM_BAG.get(), 1, 0.5f));

    }
}