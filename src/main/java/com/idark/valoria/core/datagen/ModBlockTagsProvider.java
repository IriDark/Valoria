package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Valoria.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Automatic category tag assignment based on block type
        for (RegistryObject<Block> entry : BlockRegistry.BLOCK.getEntries()) {
            Block block = entry.get();

            // Structure / Shape Category Tags
            if (block instanceof SlabBlock) {
                tag(BlockTags.SLABS).add(block);
            } else if (block instanceof StairBlock) {
                tag(BlockTags.STAIRS).add(block);
            } else if (block instanceof WallBlock) {
                tag(BlockTags.WALLS).add(block);
            } else if (block instanceof FenceBlock) {
                tag(BlockTags.FENCES).add(block);
            } else if (block instanceof FenceGateBlock) {
                tag(BlockTags.FENCE_GATES).add(block);
            } else if (block instanceof DoorBlock) {
                tag(BlockTags.DOORS).add(block);
            } else if (block instanceof TrapDoorBlock) {
                tag(BlockTags.TRAPDOORS).add(block);
            } else if (block instanceof ButtonBlock) {
                tag(BlockTags.BUTTONS).add(block);
            } else if (block instanceof PressurePlateBlock) {
                tag(BlockTags.PRESSURE_PLATES).add(block);
            } else if (block instanceof StandingSignBlock || block instanceof WallSignBlock) {
                tag(BlockTags.STANDING_SIGNS).add(block);
            } else if (block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock) {
                tag(BlockTags.CEILING_HANGING_SIGNS).add(block);
            } else if (block instanceof LeavesBlock) {
                tag(BlockTags.LEAVES).add(block);
            } else if (block instanceof SaplingBlock) {
                tag(BlockTags.SAPLINGS).add(block);
            }

            // Mining Tool Tags
            String name = entry.getId().getPath();
            if (isWoodenBlock(block, name)) {
                tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            } else if (isShovelBlock(block, name)) {
                tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block);
            } else if (isHoeBlock(block, name)) {
                tag(BlockTags.MINEABLE_WITH_HOE).add(block);
            } else {
                // Default most stones, ores, metals, altars, bricks to pickaxe mineable
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            }

            // Mining Tier Tags
            if (name.contains("cobalt") || name.contains("ruby") || name.contains("sapphire") || name.contains("amber") || name.contains("pyratite")) {
                tag(BlockTags.NEEDS_IRON_TOOL).add(block);
            } else if (name.contains("awakened") || name.contains("black_gold") || name.contains("crimtane") || name.contains("void_stone")) {
                tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
            } else if (!isWoodenBlock(block, name) && !isShovelBlock(block, name) && !isHoeBlock(block, name)) {
                tag(BlockTags.NEEDS_STONE_TOOL).add(block);
            }
        }

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(BlockRegistry.dunestone.get(), BlockRegistry.dunestoneStairs.get(), BlockRegistry.dunestoneSlab.get(), BlockRegistry.dunestoneWall.get(), BlockRegistry.dunestoneBricks.get())
            .add(BlockRegistry.dunestoneBricksStairs.get(), BlockRegistry.dunestoneBricksSlab.get(), BlockRegistry.dunestoneBricksWall.get(), BlockRegistry.tombstone.get())
            .add(BlockRegistry.tombstoneStairs.get(), BlockRegistry.tombstoneSlab.get(), BlockRegistry.tombstoneWall.get(), BlockRegistry.tombstoneBricks.get(), BlockRegistry.tombstoneBricksStairs.get())
            .add(BlockRegistry.tombstoneBricksSlab.get(), BlockRegistry.tombstoneBricksWall.get(), BlockRegistry.crackedTombstoneBricks.get(), BlockRegistry.crackedTombstoneBricksSlab.get(), BlockRegistry.crackedTombstoneBricksStairs.get())
            .add(BlockRegistry.crystalStone.get(), BlockRegistry.crystalStoneStairs.get(), BlockRegistry.crystalStoneSlab.get(), BlockRegistry.crystalStoneWall.get(), BlockRegistry.crystalStoneBricks.get())
            .add(BlockRegistry.crystalStoneBricksStairs.get(), BlockRegistry.crystalStoneBricksSlab.get(), BlockRegistry.crystalStoneBricksWall.get(), BlockRegistry.ambaneStone.get())
            .add(BlockRegistry.ambaneStoneStairs.get(), BlockRegistry.ambaneStoneSlab.get(), BlockRegistry.ambaneStoneWall.get(), BlockRegistry.ambaneStoneBricks.get(), BlockRegistry.ambaneStoneBricksStairs.get())
            .add(BlockRegistry.ambaneStoneBricksSlab.get(), BlockRegistry.ambaneStoneBricksWall.get(), BlockRegistry.limestone.get(), BlockRegistry.limestoneStairs.get())
            .add(BlockRegistry.limestoneSlab.get(), BlockRegistry.limestoneWall.get(), BlockRegistry.limestoneBricks.get(), BlockRegistry.limestoneBricksStairs.get(), BlockRegistry.limestoneBricksSlab.get())
            .add(BlockRegistry.limestoneBricksWall.get(), BlockRegistry.crackedLimestoneBricks.get(), BlockRegistry.voidStone.get(), BlockRegistry.voidStoneStairs.get(), BlockRegistry.voidStoneSlab.get())
            .add(BlockRegistry.voidStoneWall.get(), BlockRegistry.voidBrick.get(), BlockRegistry.voidBrickStairs.get(), BlockRegistry.voidBrickSlab.get(), BlockRegistry.voidBrickWall.get())
            .add(BlockRegistry.voidCrackedBrick.get(), BlockRegistry.voidCrackedBrickStairs.get(), BlockRegistry.voidCrackedBrickSlab.get(), BlockRegistry.voidCrackedBrickWall.get(), BlockRegistry.crystalStoneBricks.get())
            .add(BlockRegistry.voidPillar.get(), BlockRegistry.voidPillarAmethyst.get(), BlockRegistry.chargedVoidPillar.get(), BlockRegistry.ancientStone.get(), BlockRegistry.ancientStoneStairs.get());

        // Specific custom tags
        tag(BlockTags.LOGS)
                .add(BlockRegistry.shadeLog.get(), BlockRegistry.strippedShadeLog.get(), BlockRegistry.shadeWood.get(), BlockRegistry.strippedShadeWood.get())
                .add(BlockRegistry.eldritchLog.get(), BlockRegistry.strippedEldritchLog.get(), BlockRegistry.eldritchWood.get(), BlockRegistry.strippedEldritchWood.get())
                .add(BlockRegistry.dreadwoodLog.get(), BlockRegistry.strippedDreadwoodLog.get(), BlockRegistry.dreadWood.get(), BlockRegistry.strippedDreadWood.get());

        tag(BlockTags.PLANKS)
                .add(BlockRegistry.shadePlanks.get())
                .add(BlockRegistry.eldritchPlanks.get())
                .add(BlockRegistry.dreadwoodPlanks.get());
    }

    private boolean isWoodenBlock(Block block, String name) {
        return block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceBlock
                || block instanceof FenceGateBlock || block instanceof ButtonBlock || block instanceof PressurePlateBlock
                || name.contains("wood") || name.contains("log") || name.contains("plank") || name.contains("shade")
                || name.contains("eldritch") || name.contains("dreadwood") || name.contains("keg");
    }

    private boolean isShovelBlock(Block block, String name) {
        return name.contains("sand") || name.contains("quicksand") || name.contains("ash") || name.contains("dirt") || name.contains("gravel");
    }

    private boolean isHoeBlock(Block block, String name) {
        return block instanceof LeavesBlock || name.contains("leaves") || name.contains("vine") || name.contains("roots") || name.contains("sprout");
    }
}
