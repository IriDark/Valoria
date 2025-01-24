package com.idark.valoria.core.datagen;

import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class LootTableSubprovider extends BlockLootSubProvider{
    public final List<Block> blocks = new ArrayList<>();

    public LootTableSubprovider(){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected void add(Block pBlock, Function<Block, LootTable.Builder> pFactory){
        this.add(pBlock, pFactory.apply(pBlock));
        blocks.add(pBlock);
    }

    protected void dropSelf(Block pBlock){
        this.dropOther(pBlock, pBlock);
        blocks.add(pBlock);
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return blocks;
    }

    @Override
    protected void generate(){
        this.add(BlockRegistry.eldritchDoor.get(), block -> createDoorTable(BlockRegistry.eldritchDoor.get()));
        this.add(BlockRegistry.eldritchPlanksSlab.get(), block -> createSlabItemTable(BlockRegistry.eldritchPlanksSlab.get()));
        this.add(BlockRegistry.eldritchLeaves.get(), block -> createLeavesDrops(block, BlockRegistry.eldritchSapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.eldritchSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchWallSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchHangingSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.eldritchWallHangingSign.get(), block -> createSingleItemTable(block));
        this.add(BlockRegistry.pyratiteOre.get(), block -> createOreDrop(block, ItemsRegistry.pyratite.get()));

        this.dropSelf(BlockRegistry.bronzeGlassPane.get());
        this.dropSelf(BlockRegistry.eldritchLog.get());
        this.dropSelf(BlockRegistry.strippedEldritchLog.get());
        this.dropSelf(BlockRegistry.eldritchPlanks.get());
        this.dropSelf(BlockRegistry.eldritchPlanksStairs.get());
        this.dropSelf(BlockRegistry.eldritchTrapdoor.get());
        this.dropSelf(BlockRegistry.eldritchFence.get());
        this.dropSelf(BlockRegistry.eldritchFenceGate.get());
        this.dropSelf(BlockRegistry.eldritchButton.get());
        this.dropSelf(BlockRegistry.eldritchPressurePlate.get());
        this.dropSelf(BlockRegistry.eldritchSapling.get());
    }
}
