package com.idark.valoria.core.datagen;

import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class LootTableSubprovider extends BlockLootSubProvider {
    public final List<Block> blocks = new ArrayList<>();
    public LootTableSubprovider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected void add(Block pBlock, Function<Block, LootTable.Builder> pFactory) {
        this.add(pBlock, pFactory.apply(pBlock));
        blocks.add(pBlock);
    }

    protected void dropSelf(Block pBlock) {
        this.dropOther(pBlock, pBlock);
        blocks.add(pBlock);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }

    @Override
    protected void generate() {
        this.add(BlockRegistry.ELDRITCH_DOOR.get(), block -> createDoorTable(BlockRegistry.ELDRITCH_DOOR.get()));
        this.add(BlockRegistry.ELDRITCH_PLANKS_SLAB.get(), block -> createSlabItemTable(BlockRegistry.ELDRITCH_PLANKS_SLAB.get()));
        this.add(BlockRegistry.ELDRITCH_LEAVES.get(), block -> createLeavesDrops(block, BlockRegistry.ELDRITCH_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.ELDRITCH_SIGN.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.ELDRITCH_WALL_SIGN.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.ELDRITCH_HANGING_SIGN.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.ELDRITCH_WALL_HANGING_SIGN.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.PYRATITE_ORE.get(), block -> createOreDrop(block, ItemsRegistry.PYRATITE.get()));

        this.dropSelf(BlockRegistry.BRONZE_GLASS_PANE.get());
        this.dropSelf(BlockRegistry.ELDRITCH_LOG.get());
        this.dropSelf(BlockRegistry.STRIPPED_ELDRITCH_LOG.get());
        this.dropSelf(BlockRegistry.ELDRITCH_PLANKS.get());
        this.dropSelf(BlockRegistry.ELDRITCH_PLANKS_STAIRS.get());
        this.dropSelf(BlockRegistry.ELDRITCH_TRAPDOOR.get());
        this.dropSelf(BlockRegistry.ELDRITCH_FENCE.get());
        this.dropSelf(BlockRegistry.ELDRITCH_FENCE_GATE.get());
        this.dropSelf(BlockRegistry.ELDRITCH_BUTTON.get());
        this.dropSelf(BlockRegistry.ELDRITCH_PRESSURE_PLATE.get());
        this.dropSelf(BlockRegistry.ELDRITCH_SAPLING.get());
        if(QuarkIntegration.isLoaded()) {
            this.dropSelf(QuarkIntegration.LoadedOnly.ELDRITCH_LEAF_CARPET.get());
            this.dropSelf(QuarkIntegration.LoadedOnly.ELDRITCH_LADDER.get());
            this.dropSelf(QuarkIntegration.LoadedOnly.ELDRITCH_LOG_POST.get());
            this.dropSelf(QuarkIntegration.LoadedOnly.ELDRITCH_PLANKS_VERTICAL_SLAB.get());
            this.add(QuarkIntegration.LoadedOnly.ELDRITCH_BOOKSHELF.get(), block -> createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        }
    }
}
