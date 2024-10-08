package com.idark.valoria.core.datagen;

import com.idark.valoria.registries.*;
import net.minecraft.data.loot.*;
import net.minecraft.world.flag.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.*;

import java.util.*;
import java.util.function.*;

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
        this.add(BlockRegistry.PYRATITE_ORE.get(), block -> createOreDrop(block, ItemsRegistry.pyratite.get()));

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
    }
}
