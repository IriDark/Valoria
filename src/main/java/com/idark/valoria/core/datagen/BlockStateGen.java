package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.block.types.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;

public class BlockStateGen extends CoreStateGen {

    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Valoria.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerKit(BlockRegistry.ancientStone.get(), BlockRegistry.ancientStoneStairs.get(), BlockRegistry.ancientStoneSlab.get(), BlockRegistry.ancientStoneWall.get());
        registerKit(BlockRegistry.polishedAncientStone.get(), BlockRegistry.polishedAncientStoneStairs.get(), BlockRegistry.polishedAncientStoneSlab.get(), BlockRegistry.polishedAncientStoneWall.get());
        registerKit(BlockRegistry.picrite.get(), BlockRegistry.picriteStairs.get(), BlockRegistry.picriteSlab.get(), BlockRegistry.picriteWall.get());
        registerKit(BlockRegistry.polishedPicrite.get(), BlockRegistry.polishedPicriteStairs.get(), BlockRegistry.polishedPicriteSlab.get(), BlockRegistry.polishedPicriteWall.get());
        registerKit(BlockRegistry.picriteBricks.get(), BlockRegistry.picriteBricksStairs.get(), BlockRegistry.picriteBricksSlab.get(), BlockRegistry.picriteBricksWall.get());
        registerKit(BlockRegistry.deepMarble.get(), BlockRegistry.deepMarbleStairs.get(), BlockRegistry.deepMarbleSlab.get(), BlockRegistry.deepMarbleWall.get());
        registerKit(BlockRegistry.polishedDeepMarble.get(), BlockRegistry.polishedDeepMarbleStairs.get(), BlockRegistry.polishedDeepMarbleSlab.get(), BlockRegistry.polishedDeepMarbleWall.get());
        registerKit(BlockRegistry.cobbledShale.get(), BlockRegistry.cobbledShaleStairs.get(), BlockRegistry.cobbledShaleSlab.get(), BlockRegistry.cobbledShaleWall.get());
        registerKit(BlockRegistry.cobbledShaleBricks.get(), BlockRegistry.cobbledShaleBricksStairs.get(), BlockRegistry.cobbledShaleBricksSlab.get(), BlockRegistry.cobbledShaleBricksWall.get());
        registerKit(BlockRegistry.crackedCobbledShaleBricks.get(), BlockRegistry.crackedCobbledShaleBricksStairs.get(), BlockRegistry.crackedCobbledShaleBricksSlab.get(), BlockRegistry.crackedCobbledShaleBricksWall.get());
        registerKit(BlockRegistry.polishedCobbledShale.get(), BlockRegistry.polishedCobbledShaleStairs.get(), BlockRegistry.polishedCobbledShaleSlab.get(), BlockRegistry.polishedCobbledShaleWall.get());
        registerKit(BlockRegistry.ephemarite.get(), BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemariteWall.get());
        registerKit(BlockRegistry.ephemariteLow.get(), BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLowWall.get());
        registerKit(BlockRegistry.polishedEphemarite.get(), BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemariteWall.get());
        registerKit(BlockRegistry.polishedEphemariteLow.get(), BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLowWall.get());

        registerKit(BlockRegistry.ash.get(), BlockRegistry.ashStairs.get(), BlockRegistry.ashSlab.get(), BlockRegistry.ashWall.get());
        registerKit(BlockRegistry.ashBricks.get(), BlockRegistry.ashBricksStairs.get(), BlockRegistry.ashBricksSlab.get(), BlockRegistry.ashBricksWall.get());
        simpleBlockWithItem(BlockRegistry.ashTiles.get());
        registerKit(BlockRegistry.polishedAsh.get(), BlockRegistry.polishedAshStairs.get(), BlockRegistry.polishedAshSlab.get(), BlockRegistry.polishedAshWall.get());
        registerKit(BlockRegistry.polishedAshBricks.get(), BlockRegistry.polishedAshBricksStairs.get(), BlockRegistry.polishedAshBricksSlab.get(), BlockRegistry.polishedAshBricksWall.get());
        simpleBlockWithItem(BlockRegistry.polishedAshTiles.get());
        registerKit(BlockRegistry.smoothAsh.get(), BlockRegistry.smoothAshStairs.get(), BlockRegistry.smoothAshSlab.get(), BlockRegistry.smoothAshWall.get());

        simpleBlockWithItem(BlockRegistry.crackedTombstoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.crackedTombstoneBricksSlab.get(), BlockRegistry.crackedTombstoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.crackedTombstoneBricksStairs.get(), BlockRegistry.crackedTombstoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.crackedTombstoneBricksWall.get(), BlockRegistry.crackedTombstoneBricks.get());

        simpleBlockWithItem(BlockRegistry.ambaneStoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.ambaneStoneBricksSlab.get(), BlockRegistry.ambaneStoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.ambaneStoneBricksStairs.get(), BlockRegistry.ambaneStoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.ambaneStoneBricksWall.get(), BlockRegistry.ambaneStoneBricks.get());

        randomRotationBlock(BlockRegistry.ambaneStone.get(), "ambane_stone", "ambane_stone_mirrored");
        slabWithItem((SlabBlock) BlockRegistry.ambaneStoneSlab.get(), BlockRegistry.ambaneStone.get());
        stairsWithItem((StairBlock) BlockRegistry.ambaneStoneStairs.get(), BlockRegistry.ambaneStone.get());
        wallWithItem((WallBlock) BlockRegistry.ambaneStoneWall.get(), BlockRegistry.ambaneStone.get());

        simpleBlockWithItem(BlockRegistry.bronzeBlock.get());
        slabWithItem((SlabBlock) BlockRegistry.bronzeBlockSlab.get(), BlockRegistry.bronzeBlock.get());
        stairsWithItem((StairBlock) BlockRegistry.bronzeBlockStairs.get(), BlockRegistry.bronzeBlock.get());

        simpleBlockWithItem(BlockRegistry.crystalStoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.crystalStoneBricksSlab.get(), BlockRegistry.crystalStoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.crystalStoneBricksStairs.get(), BlockRegistry.crystalStoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.crystalStoneBricksWall.get(), BlockRegistry.crystalStoneBricks.get());

        randomRotationBlock(BlockRegistry.crystalStone.get(), "crystal_stone", "crystal_stone_mirrored");
        slabWithItem((SlabBlock) BlockRegistry.crystalStoneSlab.get(), BlockRegistry.crystalStone.get());
        stairsWithItem((StairBlock) BlockRegistry.crystalStoneStairs.get(), BlockRegistry.crystalStone.get());
        wallWithItem((WallBlock) BlockRegistry.crystalStoneWall.get(), BlockRegistry.crystalStone.get());

        simpleBlockWithItem(BlockRegistry.cutBronze.get());
        slabWithItem((SlabBlock) BlockRegistry.cutBronzeSlab.get(), BlockRegistry.cutBronze.get());
        stairsWithItem((StairBlock) BlockRegistry.cutBronzeStairs.get(), BlockRegistry.cutBronze.get());

        simpleBlockWithItem(BlockRegistry.cutLimestone.get());
        slabWithItem((SlabBlock) BlockRegistry.cutLimestoneSlab.get(), BlockRegistry.cutLimestone.get());
        stairsWithItem((StairBlock) BlockRegistry.cutLimestoneStairs.get(), BlockRegistry.cutLimestone.get());

        simpleBlockWithItem(BlockRegistry.cutTombstone.get());
        slabWithItem((SlabBlock) BlockRegistry.cutTombstoneSlab.get(), BlockRegistry.cutTombstone.get());
        stairsWithItem((StairBlock) BlockRegistry.cutTombstoneStairs.get(), BlockRegistry.cutTombstone.get());
        wallWithItem((WallBlock) BlockRegistry.cutTombstoneWall.get(), BlockRegistry.cutTombstone.get());

        simpleBlockWithItem(BlockRegistry.dunestoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.dunestoneBricksSlab.get(), BlockRegistry.dunestoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.dunestoneBricksStairs.get(), BlockRegistry.dunestoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.dunestoneBricksWall.get(), BlockRegistry.dunestoneBricks.get());

        randomRotationBlock(BlockRegistry.dunestone.get(), "dunestone", "dunestone_mirrored");
        slabWithItem((SlabBlock) BlockRegistry.dunestoneSlab.get(), BlockRegistry.dunestone.get());
        stairsWithItem((StairBlock) BlockRegistry.dunestoneStairs.get(), BlockRegistry.dunestone.get());
        wallWithItem((WallBlock) BlockRegistry.dunestoneWall.get(), BlockRegistry.dunestone.get());

        simpleBlockWithItem(BlockRegistry.limestoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.limestoneBricksSlab.get(), BlockRegistry.limestoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.limestoneBricksStairs.get(), BlockRegistry.limestoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.limestoneBricksWall.get(), BlockRegistry.limestoneBricks.get());

        randomRotationBlock(BlockRegistry.limestone.get(), "limestone", "limestone_mirrored");
        slabWithItem((SlabBlock) BlockRegistry.limestoneSlab.get(), BlockRegistry.limestone.get());
        stairsWithItem((StairBlock) BlockRegistry.limestoneStairs.get(), BlockRegistry.limestone.get());
        wallWithItem((WallBlock) BlockRegistry.limestoneWall.get(), BlockRegistry.limestone.get());

        simpleBlockWithItem(BlockRegistry.mossyTombstoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.mossyTombstoneBricksSlab.get(), BlockRegistry.mossyTombstoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.mossyTombstoneBricksStairs.get(), BlockRegistry.mossyTombstoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.mossyTombstoneBricksWall.get(), BlockRegistry.mossyTombstoneBricks.get());

        simpleBlockWithItem(BlockRegistry.mossyTombstone.get());
        slabWithItem((SlabBlock) BlockRegistry.mossyTombstoneSlab.get(), BlockRegistry.mossyTombstone.get());
        stairsWithItem((StairBlock) BlockRegistry.mossyTombstoneStairs.get(), BlockRegistry.mossyTombstone.get());
        wallWithItem((WallBlock) BlockRegistry.mossyTombstoneWall.get(), BlockRegistry.mossyTombstone.get());

        simpleBlockWithItem(BlockRegistry.polishedAmbaneStone.get());
        slabWithItem((SlabBlock) BlockRegistry.polishedAmbaneStoneSlab.get(), BlockRegistry.polishedAmbaneStone.get());
        stairsWithItem((StairBlock) BlockRegistry.polishedAmbaneStoneStairs.get(), BlockRegistry.polishedAmbaneStone.get());

        simpleBlockWithItem(BlockRegistry.polishedLimestone.get());
        slabWithItem((SlabBlock) BlockRegistry.polishedLimestoneSlab.get(), BlockRegistry.polishedLimestone.get());
        stairsWithItem((StairBlock) BlockRegistry.polishedLimestoneStairs.get(), BlockRegistry.polishedLimestone.get());

        simpleBlockWithItem(BlockRegistry.tombstoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.tombstoneBricksSlab.get(), BlockRegistry.tombstoneBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.tombstoneBricksStairs.get(), BlockRegistry.tombstoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.tombstoneBricksWall.get(), BlockRegistry.tombstoneBricks.get());

        simpleBlockWithItem(BlockRegistry.tombstone.get());
        slabWithItem((SlabBlock) BlockRegistry.tombstoneSlab.get(), BlockRegistry.tombstone.get());
        stairsWithItem((StairBlock) BlockRegistry.tombstoneStairs.get(), BlockRegistry.tombstone.get());
        wallWithItem((WallBlock) BlockRegistry.tombstoneWall.get(), BlockRegistry.tombstone.get());

        simpleBlockWithItem(BlockRegistry.voidBrick.get());
        slabWithItem((SlabBlock) BlockRegistry.voidBrickSlab.get(), BlockRegistry.voidBrick.get());
        stairsWithItem((StairBlock) BlockRegistry.voidBrickStairs.get(), BlockRegistry.voidBrick.get());
        wallWithItem((WallBlock) BlockRegistry.voidBrickWall.get(), BlockRegistry.voidBrick.get());

        simpleBlockWithItem(BlockRegistry.voidChiseledBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.voidChiseledBricksSlab.get(), BlockRegistry.voidChiseledBricks.get());
        stairsWithItem((StairBlock) BlockRegistry.voidChiseledBricksStairs.get(), BlockRegistry.voidChiseledBricks.get());

        simpleBlockWithItem(BlockRegistry.voidCrackedBrick.get());
        slabWithItem((SlabBlock) BlockRegistry.voidCrackedBrickSlab.get(), BlockRegistry.voidCrackedBrick.get());
        stairsWithItem((StairBlock) BlockRegistry.voidCrackedBrickStairs.get(), BlockRegistry.voidCrackedBrick.get());
        wallWithItem((WallBlock) BlockRegistry.voidCrackedBrickWall.get(), BlockRegistry.voidCrackedBrick.get());

        simpleBlockWithItem(BlockRegistry.voidCutSandstone.get());
        slabWithItem((SlabBlock) BlockRegistry.voidCutSandstoneSlab.get(), BlockRegistry.voidCutSandstone.get());

        simpleBlockWithItem(BlockRegistry.voidSandstone.get());
        slabWithItem((SlabBlock) BlockRegistry.voidSandstoneSlab.get(), BlockRegistry.voidSandstone.get());
        stairsWithItem((StairBlock) BlockRegistry.voidSandstoneStairs.get(), BlockRegistry.voidSandstone.get());
        wallWithItem((WallBlock) BlockRegistry.voidSandstoneWall.get(), BlockRegistry.voidSandstone.get());

        randomRotationBlock(BlockRegistry.voidStone.get(), "void_stone", "void_stone_mirrored");
        slabWithItem((SlabBlock) BlockRegistry.voidStoneSlab.get(), BlockRegistry.voidStone.get());
        stairsWithItem((StairBlock) BlockRegistry.voidStoneStairs.get(), BlockRegistry.voidStone.get());
        wallWithItem((WallBlock) BlockRegistry.voidStoneWall.get(), BlockRegistry.voidStone.get());

        simpleBlockWithItem(BlockRegistry.smoothVoidSandstone.get(), modLoc("block/void_sandstone_top"));
        slabBlock((SlabBlock) BlockRegistry.smoothVoidSandstoneSlab.get(), modLoc("block/void_smooth_sandstone"), modLoc("block/void_sandstone_top"));
        simpleBlockItem(BlockRegistry.smoothVoidSandstoneSlab.get(), models().getExistingFile(modLoc("block/" + name(BlockRegistry.smoothVoidSandstoneSlab.get()))));
        stairsBlock((StairBlock) BlockRegistry.smoothVoidSandstoneStairs.get(), modLoc("block/void_sandstone_top"));
        simpleBlockItem(BlockRegistry.smoothVoidSandstoneStairs.get(), models().getExistingFile(modLoc("block/" + name(BlockRegistry.smoothVoidSandstoneStairs.get()))));

        paneBlockWithRenderType((IronBarsBlock) BlockRegistry.bronzeGlassPane.get(), blockTexture(BlockRegistry.bronzeGlass.get()), blockTexture(BlockRegistry.bronzeBlock.get()), "translucent");
        itemModels().withExistingParent(name(BlockRegistry.bronzeGlassPane.get()), "item/generated").texture("layer0", modLoc("block/" + name(BlockRegistry.bronzeGlass.get())));


        doorBlockWithRenderType((DoorBlock) BlockRegistry.bronzeDoor.get(), modLoc("block/bronze_door_bottom"), modLoc("block/bronze_door_top"), "translucent");
        itemModels().withExistingParent(name(BlockRegistry.bronzeDoor.get()), "item/generated").texture("layer0", modLoc("item/bronze_door"));
        trapdoorBlockWithRenderType((TrapDoorBlock) BlockRegistry.bronzeTrapdoor.get(), modLoc("block/bronze_trapdoor"), true, "translucent");
        simpleBlockItem(BlockRegistry.bronzeTrapdoor.get(), models().getExistingFile(modLoc("block/bronze_trapdoor_bottom")));

        woodset(BlockRegistry.eldritchLog.get(), BlockRegistry.strippedEldritchLog.get(), BlockRegistry.eldritchPlanks.get(), BlockRegistry.eldritchLeaves.get(),
        (DoorBlock) BlockRegistry.eldritchDoor.get(), (TrapDoorBlock) BlockRegistry.eldritchTrapdoor.get(),
        (StandingSignBlock) BlockRegistry.eldritchSign.get(), (WallSignBlock) BlockRegistry.eldritchWallSign.get(),
        BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get(),
        (ButtonBlock) BlockRegistry.eldritchButton.get(), (PressurePlateBlock) BlockRegistry.eldritchPressurePlate.get(),
        (StairBlock) BlockRegistry.eldritchPlanksStairs.get(), (SlabBlock) BlockRegistry.eldritchPlanksSlab.get(),
        (FenceBlock) BlockRegistry.eldritchFence.get(), (FenceGateBlock) BlockRegistry.eldritchFenceGate.get());

        woodset(BlockRegistry.shadeLog.get(), BlockRegistry.strippedShadeLog.get(), BlockRegistry.shadePlanks.get(), BlockRegistry.shadeLeaves.get(),
                (DoorBlock) BlockRegistry.shadeDoor.get(), (TrapDoorBlock) BlockRegistry.shadeTrapdoor.get(),
                (StandingSignBlock) BlockRegistry.shadeSign.get(), (WallSignBlock) BlockRegistry.shadeWallSign.get(),
                BlockRegistry.shadeHangingSign.get(), BlockRegistry.shadeWallHangingSign.get(),
                (ButtonBlock) BlockRegistry.shadeButton.get(), (PressurePlateBlock) BlockRegistry.shadePressurePlate.get(),
                (StairBlock) BlockRegistry.shadePlanksStairs.get(), (SlabBlock) BlockRegistry.shadePlanksSlab.get(),
                (FenceBlock) BlockRegistry.shadeFence.get(), (FenceGateBlock) BlockRegistry.shadeFenceGate.get());

        ModelFile dreadLogModel0 = models().cubeColumn("dread_log", modLoc("block/dread_log"), modLoc("block/dread_log_top"));
        ModelFile dreadLogModel1 = models().cubeColumn("dread_log_1", modLoc("block/dread_log_1"), modLoc("block/dread_log_top"));
        getVariantBuilder(BlockRegistry.dreadwoodLog.get())
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .addModels(new ConfiguredModel(dreadLogModel0), new ConfiguredModel(dreadLogModel1))
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .addModels(new ConfiguredModel(dreadLogModel0, 90, 0, false), new ConfiguredModel(dreadLogModel1, 90, 0, false))
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .addModels(new ConfiguredModel(dreadLogModel0, 90, 90, false), new ConfiguredModel(dreadLogModel1, 90, 90, false));
        simpleBlockItem(BlockRegistry.dreadwoodLog.get(), dreadLogModel0);

        logBlock((RotatedPillarBlock) BlockRegistry.strippedDreadwoodLog.get());
        simpleBlockWithItem(BlockRegistry.dreadwoodPlanks.get());

        doorBlockWithRenderType((DoorBlock)BlockRegistry.dreadwoodDoor.get(), modLoc("block/" + name(BlockRegistry.dreadwoodDoor.get()) + "_bottom"), modLoc("block/" + name(BlockRegistry.dreadwoodDoor.get()) + "_top"), "cutout");
        simpleItem(BlockRegistry.dreadwoodDoor.get(), modLoc("item/" + name(BlockRegistry.dreadwoodDoor.get())));

        trapdoorBlockWithRenderType((TrapDoorBlock)BlockRegistry.dreadwoodTrapdoor.get(), modLoc("block/" + name(BlockRegistry.dreadwoodTrapdoor.get())), true, "cutout");
        simpleBlockItem(BlockRegistry.dreadwoodTrapdoor.get(), models().getExistingFile(modLoc("block/" + name(BlockRegistry.dreadwoodTrapdoor.get()) + "_bottom")));

        signBlock((StandingSignBlock)BlockRegistry.dreadwoodSign.get(), (WallSignBlock)BlockRegistry.dreadwoodWallSign.get(), modLoc("block/" + name(BlockRegistry.dreadwoodPlanks.get())));
        simpleItem(BlockRegistry.dreadwoodSign.get(), modLoc("item/" + name(BlockRegistry.dreadwoodSign.get())));
        hangingSignBlock(BlockRegistry.dreadwoodHangingSign.get(), BlockRegistry.dreadwoodWallHangingSign.get(), modLoc("block/" + name(BlockRegistry.dreadwoodPlanks.get())));
        simpleItem(BlockRegistry.dreadwoodHangingSign.get(), modLoc("item/" + name(BlockRegistry.dreadwoodHangingSign.get())));

        buttonBlock((ButtonBlock)BlockRegistry.dreadwoodButton.get(), modLoc("block/" + name(BlockRegistry.dreadwoodPlanks.get())));
        simpleBlockItem(BlockRegistry.dreadwoodButton.get(), models().getExistingFile(modLoc("block/" + name(BlockRegistry.dreadwoodButton.get()))));

        pressurePlateBlock((PressurePlateBlock)BlockRegistry.dreadwoodPressurePlate.get(), modLoc("block/" + name(BlockRegistry.dreadwoodPlanks.get())));
        simpleBlockItem(BlockRegistry.dreadwoodPressurePlate.get(), models().getExistingFile(modLoc("block/" + name(BlockRegistry.dreadwoodPressurePlate.get()))));

        stairsWithItem((StairBlock)BlockRegistry.dreadwoodPlanksStairs.get(), BlockRegistry.dreadwoodPlanks.get());
        slabWithItem((SlabBlock)BlockRegistry.dreadwoodPlanksSlab.get(), BlockRegistry.dreadwoodPlanks.get());
        fenceWithItem((FenceBlock)BlockRegistry.dreadwoodFence.get(), BlockRegistry.dreadwoodPlanks.get());
        fenceGateWithItem((FenceGateBlock)BlockRegistry.dreadwoodFenceGate.get(), BlockRegistry.dreadwoodPlanks.get());

        axisBlock((RotatedPillarBlock) BlockRegistry.shadeWood.get(), blockTexture(BlockRegistry.shadeLog.get()), blockTexture(BlockRegistry.shadeLog.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.strippedShadeWood.get(), blockTexture(BlockRegistry.strippedShadeLog.get()), blockTexture(BlockRegistry.strippedShadeLog.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.dreadWood.get(), blockTexture(BlockRegistry.dreadwoodLog.get()), blockTexture(BlockRegistry.dreadwoodLog.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.strippedDreadWood.get(), blockTexture(BlockRegistry.strippedDreadwoodLog.get()), blockTexture(BlockRegistry.strippedDreadwoodLog.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.eldritchWood.get(), blockTexture(BlockRegistry.eldritchLog.get()), blockTexture(BlockRegistry.eldritchLog.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.strippedEldritchWood.get(), blockTexture(BlockRegistry.strippedEldritchLog.get()), blockTexture(BlockRegistry.strippedEldritchLog.get()));

        simpleBlockWithItem(BlockRegistry.infernalBlock.get());
        simpleBlockWithItem(BlockRegistry.umbralActivator.get());
        randomRotationBlock(BlockRegistry.umbralBricks.get(), "umbral_bricks", "umbral_bricks_1");

        ModelFile jTable = models().cube("jeweler_table", modLoc("block/jeweler_table_bottom"), modLoc("block/jeweler_table_top"), modLoc("block/jeweler_table_side_1"), modLoc("block/jeweler_table_side_1"), modLoc("block/jeweler_table_side"), modLoc("block/jeweler_table_side")).texture("particle", modLoc("block/jeweler_table_side"));
        getVariantBuilder(BlockRegistry.jewelerTable.get()).partialState().addModels(new ConfiguredModel(jTable));
        simpleBlockItem(BlockRegistry.jewelerTable.get(), jTable);

        ModelFile sCrusher = models().cube("stone_crusher", modLoc("block/stonecrusher_bottom"), modLoc("block/stonecrusher_top_geode"), modLoc("block/stonecrusher_side_1"), modLoc("block/stonecrusher_side_1"), modLoc("block/stonecrusher_side_0"), modLoc("block/stonecrusher_side_1")).texture("particle", modLoc("block/stonecrusher_top_geode"));
        getVariantBuilder(BlockRegistry.stoneCrusher.get()).partialState().addModels(new ConfiguredModel(sCrusher));
        simpleBlockItem(BlockRegistry.stoneCrusher.get(), sCrusher);

        models().cross("voidthorn_top", modLoc("block/voidthorn_top")).renderType("cutout");
        models().cross("karusakan_roots_top", modLoc("block/karusakan_roots_top")).renderType("cutout");
        models().cross("gaib_roots_top", modLoc("block/gaib_roots_top")).renderType("cutout");

        randomRotationBlock(BlockRegistry.amberOre.get(), "amber_ore", "amber_ore_1", "amber_ore_mirror", "amber_ore_1_mirror");
        randomRotationBlock(BlockRegistry.cobaltOre.get(), "cobalt_ore", "cobalt_ore_mirrored");
        randomRotationBlock(BlockRegistry.deepslateAmberOre.get(), "deepslate_amber_ore", "deepslate_amber_ore_1", "deepslate_amber_ore_mirror", "deepslate_amber_ore_1_mirror");
        randomRotationBlock(BlockRegistry.deepslateCobaltOre.get(), "deepslate_cobalt_ore", "deepslate_cobalt_ore_mirrored");
        randomRotationBlock(BlockRegistry.eyeFlesh.get(), "eye_flesh", "eye_flesh_mirror");
        randomRotationBlock(BlockRegistry.eyeMeat.get(), "eye_meat", "eye_meat_mirror");
        randomRotationBlock(BlockRegistry.eyeStone.get(), "eye_stone", "eye_stone_mirror");
        randomRotationBlock(BlockRegistry.fleshRemains.get(), "flesh_remains", "flesh_remains_mirror");
        randomRotationBlock(BlockRegistry.meatBlock.get(), "meat_block", "meat_mirror");

        facingCrossBlockWithItemTex(BlockRegistry.pyratiteCrystal.get(), "pyratite_crystal");
        facingCrossBlockWithItemTex(BlockRegistry.rubyCrystal.get(), "ruby_crystal");
        facingCrossBlockWithItemTex(BlockRegistry.sapphireCrystal.get(), "sapphire_crystal");
        facingCrossBlockWithItemTex(BlockRegistry.spikes.get(), "spikes");
        facingCrossBlockWithItemTex(BlockRegistry.amethystCrystal.get(), "amethyst_crystal");
        facingCrossBlockWithItemTex(BlockRegistry.amberCrystal.get(), "amber_crystal");

        trapBlock(BlockRegistry.cobbledShaleSpikesTrap.get(), BlockRegistry.polishedCobbledShale.get());
        trapBlock(BlockRegistry.tombstoneSpikesTrap.get(), BlockRegistry.polishedTombstone.get());
        trapBlock(BlockRegistry.voidSpikesTrap.get(), BlockRegistry.polishedVoidStone.get());
        trapBlock(BlockRegistry.voidFirechargeTrap.get(), BlockRegistry.polishedVoidStone.get());
        trapBlock(BlockRegistry.tombstoneFirechargeTrap.get(), BlockRegistry.polishedTombstone.get());
        trapBlock(BlockRegistry.cobbledShaleFirechargeTrap.get(), BlockRegistry.polishedCobbledShale.get());

        horizontalBlockCustom(BlockRegistry.mossyTomb.get(), "mossy_tomb", 180, 90);
        horizontalBlockCustom(BlockRegistry.mossyWoodenTomb.get(), "mossy_wooden_tomb", 180, 90);
        horizontalBlockCustom(BlockRegistry.tomb.get(), "tomb", 180, 90);
        horizontalBlockCustom(BlockRegistry.woodenTomb.get(), "wooden_tomb", 180, 90);
        horizontalBlockCustom(BlockRegistry.grave.get(), "grave", 180, 90);

        for (Block cupBlock : new Block[]{BlockRegistry.woodenCup.get(), BlockRegistry.beerCup.get(), BlockRegistry.cacaoCup.get(), BlockRegistry.coffeeCup.get(), BlockRegistry.teaCup.get(), BlockRegistry.greenTeaCup.get(), BlockRegistry.cup.get(), BlockRegistry.rumCup.get()}) {
            String bName = name(cupBlock);
            getVariantBuilder(cupBlock).forAllStates(state -> ConfiguredModel.builder()
            .modelFile(models().getExistingFile(modLoc("block/" + bName + (state.getValue(CupBlock.CUPS) == 2 ? "_two" : ""))))
            .build());
            simpleItem(cupBlock);
        }

        for (Block bottleBlock : new Block[]{BlockRegistry.akvavitBottle.get(), BlockRegistry.cognacBottle.get(), BlockRegistry.cokeBottle.get(), BlockRegistry.glassBottle.get(), BlockRegistry.kvassBottle.get(), BlockRegistry.liquorBottle.get(), BlockRegistry.meadBottle.get(), BlockRegistry.rumBottle.get(), BlockRegistry.sakeBottle.get(), BlockRegistry.whiskeyBottle.get(), BlockRegistry.wineBottle.get()}) {
            String bName = name(bottleBlock);
            getVariantBuilder(bottleBlock).forAllStates(state -> {
                int count = state.getValue(BottleBlock.BOTTLES);
                String suffix = count == 1 ? "" : (count == 2 ? "_two" : (count == 3 ? "_three" : "_four"));
                return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + bName + suffix))).build();
            });

            if(bottleBlock == BlockRegistry.glassBottle.get()) {
                simpleItem(bottleBlock, "bottle");
            } else simpleItem(bottleBlock);
        }

        for (Block doublePlant : new Block[]{BlockRegistry.doubleGoldy.get(), BlockRegistry.doubleVoidvine.get()}) {
            String bName = name(doublePlant);
            ModelFile lowerModel = models().cross(bName, modLoc("block/" + bName)).renderType("cutout");
            ModelFile upperModel = models().cross(bName + "_top", modLoc("block/" + bName + "_top")).renderType("cutout");
            getVariantBuilder(doublePlant)
                    .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(lowerModel))
                    .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(upperModel));

            simpleItem(doublePlant, modLoc("block/" + name(doublePlant) + "_top"));
        }

        for (Block lamp : new Block[]{BlockRegistry.bronzeLamp.get(), BlockRegistry.bronzeLampBlock.get(), BlockRegistry.decoratedBronzeLamp.get()}) {
            String bName = name(lamp);
            getVariantBuilder(lamp).forAllStates(state -> {
                boolean lit = state.getValue(BlockStateProperties.LIT);
                return ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + bName + (lit ? "_lit" : "")))).build();
            });
            simpleBlockItem(lamp, models().getExistingFile(modLoc("block/" + bName)));
        }

        models().cubeAll(name(BlockRegistry.umbralKeypad.get()) + "_empty", modLoc("block/umbral_keypad_empty"));
        models().cubeAll(name(BlockRegistry.umbralKeypad.get()) + "_key", modLoc("block/umbral_keypad_key"));
        getVariantBuilder(BlockRegistry.umbralKeypad.get()).forAllStates(state -> {
            boolean clicked = state.getValue(com.idark.valoria.registries.block.types.UmbralKeyPadBlock.KEY_CLICKED);
            return ConfiguredModel.builder()
            .modelFile(models().getExistingFile(modLoc("block/umbral_keypad_" + (clicked ? "key" : "empty"))))
            .build();
        });
        simpleBlockItem(BlockRegistry.umbralKeypad.get(), models().getExistingFile(modLoc("block/umbral_keypad_empty")));

        registerMultipartBloodVein();
        variantBlockWithItem(BlockRegistry.umbralBricks.get(), "umbral_bricks", "umbral_bricks_1");

        BlockModelBuilder voidSandModel = models().cubeAll("void_sand", blockTexture(BlockRegistry.voidSand.get()));
        getVariantBuilder(BlockRegistry.voidSand.get()).partialState().addModels(
                new ConfiguredModel(voidSandModel),
                new ConfiguredModel(voidSandModel, 0, 90, false),
                new ConfiguredModel(voidSandModel, 0, 180, false),
                new ConfiguredModel(voidSandModel, 0, 270, false)
        );

        simpleBlockItem(BlockRegistry.voidSand.get(), voidSandModel);
        simpleBlockWithItem(BlockRegistry.pyratiteOre.get());
        simpleBlockWithItem(BlockRegistry.abyssalLantern.get());
        simpleBlockWithItem(BlockRegistry.amberBlock.get());
        simpleBlockWithItem(BlockRegistry.amethystBlock.get());
        simpleBlockWithItem(BlockRegistry.aquariusBlock.get());
        simpleBlockWithItem(BlockRegistry.awakenedVoidBlock.get());
        simpleBlockWithItem(BlockRegistry.blackGoldBlock.get());
        tintedPlantBlock(BlockRegistry.aloeSmall.get());
        plantBlock(BlockRegistry.blightedGrass.get(), modLoc("block/" + name(BlockRegistry.blightedGrass.get())));
        plantBlock(BlockRegistry.bloodroot.get(), modLoc("block/" + name(BlockRegistry.bloodroot.get())));
        glassBlock(BlockRegistry.bronzeGlass.get());
        simpleBlockWithItem(BlockRegistry.bronzeVent.get());
        axisBlock((RotatedPillarBlock) BlockRegistry.chargedVoidPillar.get(), blockTexture(BlockRegistry.chargedVoidPillar.get()), modLoc("block/void_pillar_end"));
        simpleBlockWithItem(BlockRegistry.chiseledAmbaneStoneBricks.get());
        simpleBlockWithItem(BlockRegistry.cobaltBlock.get());
        simpleBlockWithItem(BlockRegistry.cobbledShaleFirechargeTrap.get());
        simpleBlockWithItem(BlockRegistry.crackedLimestoneBricks.get());
        plantBlock(BlockRegistry.crimsonSoulroot.get(), modLoc("block/" + name(BlockRegistry.crimsonSoulroot.get())));
        simpleBlockWithItem(BlockRegistry.crimtaneBlock.get());
        axisBlock((RotatedPillarBlock) BlockRegistry.crystalStonePillar.get(), blockTexture(BlockRegistry.crystalStonePillar.get()), blockTexture(BlockRegistry.crystalStonePillar.get()));
        simpleBlockWithItem(BlockRegistry.cutAmbaneStone.get());
        simpleBlockWithItem(BlockRegistry.cutAncientStone.get());
        simpleBlockWithItem(BlockRegistry.cutCrystalStone.get());
        simpleBlockWithItem(BlockRegistry.cutDunestone.get());
        simpleBlockWithItem(BlockRegistry.cutPolishedCrystalStone.get());
        simpleBlockWithItem(BlockRegistry.cutUmbralBlock.get());

        getVariantBuilder(BlockRegistry.decoratedCryptPot.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.decoratedCryptPot.get())))).buildLast());
        simpleItem(BlockRegistry.decoratedCryptPot.get(), modLoc("item/" + name(BlockRegistry.decoratedCryptPot.get())));

        simpleBlockWithItem(BlockRegistry.deepslateRubyOre.get());
        simpleBlockWithItem(BlockRegistry.deepslateSapphireOre.get());
        simpleBlock(BlockRegistry.dormantCrystals.get(), models().getExistingFile(modLoc("block/dormant_crystals")));
        simpleBlockItem(BlockRegistry.dormantCrystals.get(), models().getExistingFile(modLoc("block/dormant_crystals")));
        plantBlock(BlockRegistry.driedPlant.get(), modLoc("block/" + name(BlockRegistry.driedPlant.get())));
        plantBlock(BlockRegistry.driedRoots.get(), modLoc("block/" + name(BlockRegistry.driedRoots.get())));
        simpleBlockWithItem(BlockRegistry.fleshBlock.get());
        simpleBlockWithItem(BlockRegistry.jadeOre.get());
        simpleBlockWithItem(BlockRegistry.natureBlock.get());
        simpleBlockWithItem(BlockRegistry.pearlium.get());
        simpleBlockWithItem(BlockRegistry.pearliumOre.get());
        simpleBlockWithItem(BlockRegistry.picriteJadeOre.get());
        simpleBlockWithItem(BlockRegistry.polishedCrystalStone.get());
        simpleBlockWithItem(BlockRegistry.polishedDunestone.get());
        simpleBlockWithItem(BlockRegistry.polishedTombstone.get());
        simpleBlockWithItem(BlockRegistry.polishedVoidStone.get());

        horizontalBlockCustom(BlockRegistry.potLongHandles.get(), "pot_long_handles", 180, 90);
        horizontalBlockCustom(BlockRegistry.potLongMossyHandles.get(), "pot_long_mossy_handles", 180, 90);
        getVariantBuilder(BlockRegistry.potDesertHandles.get()).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int rotY = switch (dir) {
                case EAST, WEST -> 90;
                case NORTH, SOUTH -> 180;
                default -> 0;
            };
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/pot_desert_handles"))).rotationX(0).rotationY(rotY).weight(8).nextModel()
                    .modelFile(models().getExistingFile(modLoc("block/pot_desert_handles_ankh"))).rotationX(0).rotationY(rotY).weight(1).nextModel()
                    .modelFile(models().getExistingFile(modLoc("block/pot_desert_handles_scarab"))).rotationX(0).rotationY(rotY).weight(1)
                    .build();
        });
        horizontalBlockCustom(BlockRegistry.potSmallHandles.get(), "pot_small_handles", 180, 90);
        getVariantBuilder(BlockRegistry.potLong.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.potLong.get())))).buildLast());
        simpleItem(BlockRegistry.potLong.get(), modLoc("item/" + name(BlockRegistry.potLong.get())));

        getVariantBuilder(BlockRegistry.potLongMossy.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.potLongMossy.get())))).buildLast());
        simpleItem(BlockRegistry.potLongMossy.get(), modLoc("item/" + name(BlockRegistry.potLongMossy.get())));

        getVariantBuilder(BlockRegistry.potDesert.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.potDesert.get())))).buildLast());
        simpleItem(BlockRegistry.potDesert.get(), modLoc("item/" + name(BlockRegistry.potDesert.get())));

        getVariantBuilder(BlockRegistry.potSmall.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.potSmall.get())))).buildLast());
        simpleItem(BlockRegistry.potSmall.get(), modLoc("item/" + name(BlockRegistry.potSmall.get())));

        getVariantBuilder(BlockRegistry.cryptPot.get()).partialState().addModels(ConfiguredModel.builder().modelFile(models().getExistingFile(modLoc("block/" + name(BlockRegistry.cryptPot.get())))).buildLast());
        simpleItem(BlockRegistry.cryptPot.get(), modLoc("item/" + name(BlockRegistry.cryptPot.get())));
        simpleItem(BlockRegistry.voidSerpents.get(), modLoc("block/void_serpents_preview"));
        getVariantBuilder(BlockRegistry.cattail.get())
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(models().getExistingFile(modLoc("block/cattail"))))
                .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(models().getExistingFile(modLoc("block/cattail_top"))));

        simpleItem(BlockRegistry.dreadwoodSapling.get(), modLoc("block/" + name(BlockRegistry.dreadwoodSapling.get())));
        pottedPlantBlock(BlockRegistry.pottedAloeSmall.get(), "aloe_small");
        pottedPlantBlock(BlockRegistry.pottedBlightedGrass.get(), "potted_blighted_grass");
        pottedPlantBlock(BlockRegistry.pottedBloodroot.get(), "potted_bloodroot");
        pottedPlantBlock(BlockRegistry.pottedCrimsonSoulroot.get(), "potted_crimson_soulroot");
        pottedPlantBlock(BlockRegistry.pottedDriedPlant.get(), "potted_dried_plant");
        pottedPlantBlock(BlockRegistry.pottedDriedRoots.get(), "potted_dried_roots");
        pottedPlantBlock(BlockRegistry.pottedFalseflower.get(), "potted_falseflower");
        pottedPlantBlock(BlockRegistry.pottedFalseflowerSmall.get(), "potted_falseflower_small");
        pottedPlantBlock(BlockRegistry.pottedVoidvine.get(), "potted_voidvine");
        pottedPlantBlock(BlockRegistry.pottedSoulroot.get(), "potted_crimson_soulroot");
        pottedPlantBlock(BlockRegistry.pottedSoulFlower.get(), "soulflower");
        pottedPlantBlock(BlockRegistry.pottedVoidRoots.get(), "potted_void_roots");
        pottedPlantBlock(BlockRegistry.pottedDreadwoodSapling.get(), "dread_sapling");
        layeredPottedPlantBlock(BlockRegistry.pottedEldritchSapling.get(), "eldritch_sapling");
        layeredPottedPlantBlock(BlockRegistry.pottedShadewoodSapling.get(), "shade_sapling");
        pottedPlantBlock(BlockRegistry.pottedMagmaroot.get(), "potted_crimson_magmaroot");
        pottedPlantBlock(BlockRegistry.pottedGoldy.get(), "potted_crimson_goldy");
        pottedPlantBlock(BlockRegistry.pottedRajush.get(), "potted_crimson_rajush");
        pottedPlantBlock(BlockRegistry.pottedVoidSerpents.get(), "void_serpents_preview");

        simpleBlockWithItem(BlockRegistry.pyratiteBlock.get());
        simpleBlock(BlockRegistry.quicksand.get(), models().getExistingFile(modLoc("block/quicksand")));
        simpleBlockItem(BlockRegistry.quicksand.get(), models().getExistingFile(modLoc("block/quicksand")));
        simpleBlockWithItem(BlockRegistry.rawCobaltOreBlock.get());
        simpleBlockWithItem(BlockRegistry.rubyBlock.get());
        simpleBlockWithItem(BlockRegistry.rubyOre.get());
        simpleBlockWithItem(BlockRegistry.sapphireBlock.get());
        simpleBlockWithItem(BlockRegistry.sapphireOre.get());
        simpleBlockWithItem(BlockRegistry.soulShardBlock.get());
        plantBlock(BlockRegistry.soulroot.get(), modLoc("block/" + name(BlockRegistry.soulroot.get())));
        simpleBlockWithItem(BlockRegistry.umbralBlock.get());
        simpleBlockWithItem(BlockRegistry.unchargedShardBlock.get());
        simpleBlockWithItem(BlockRegistry.voidChiseledBrick.get());
        simpleBlockWithItem(BlockRegistry.voidChiseledSandstone.get());
        getVariantBuilder(BlockRegistry.voidSerpents.get()).partialState().addModels(new ConfiguredModel(
            models().withExistingParent(name(BlockRegistry.voidSerpents.get()), "valoria:block/tinted_glowing_plant")
                .texture("tinted", modLoc("block/void_serpents_tint"))
                .texture("glowing", modLoc("block/void_serpents"))
                .texture("particle", modLoc("block/soulflower"))
                .renderType("cutout")
        ));
        simpleBlockWithItem(BlockRegistry.voidTaintLantern.get());
        plantBlock(BlockRegistry.voidvine.get(), modLoc("block/" + name(BlockRegistry.voidvine.get())));
        simpleBlockWithItem(BlockRegistry.wickedAmethystBlock.get());
        simpleBlockWithItem(BlockRegistry.wickedAmethystOre.get());

        stairsWithItem((StairBlock) BlockRegistry.crackedLimestoneBricksStairs.get(), BlockRegistry.crackedLimestoneBricks.get());
        slabWithItem((SlabBlock) BlockRegistry.crackedLimestoneBricksSlab.get(), BlockRegistry.crackedLimestoneBricks.get());
        wallWithItem((WallBlock) BlockRegistry.crackedLimestoneBricksWall.get(), BlockRegistry.crackedLimestoneBricks.get());

        axisBlock((RotatedPillarBlock) BlockRegistry.tombstonePillar.get(), blockTexture(BlockRegistry.tombstonePillar.get()), blockTexture(BlockRegistry.tombstonePillar.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.cutTombstonePillar.get(), blockTexture(BlockRegistry.cutTombstonePillar.get()), blockTexture(BlockRegistry.cutTombstonePillar.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.wickedTombstonePillar.get(), blockTexture(BlockRegistry.wickedTombstonePillar.get()), blockTexture(BlockRegistry.wickedTombstonePillar.get()));
        axisBlock((RotatedPillarBlock) BlockRegistry.voidPillar.get(), blockTexture(BlockRegistry.voidPillar.get()), modLoc("block/void_pillar_end"));
        axisBlock((RotatedPillarBlock) BlockRegistry.voidPillarAmethyst.get(), blockTexture(BlockRegistry.voidPillarAmethyst.get()), modLoc("block/void_pillar_end"));
        axisBlock((RotatedPillarBlock) BlockRegistry.meatPillar.get(), blockTexture(BlockRegistry.meatPillar.get()), blockTexture(BlockRegistry.meatPillar.get()));

        plantBlock(BlockRegistry.magmaroot.get(), modLoc("block/" + name(BlockRegistry.magmaroot.get())));
        plantBlock(BlockRegistry.goldy.get(), modLoc("block/" + name(BlockRegistry.goldy.get())));
        plantBlock(BlockRegistry.rajush.get(), modLoc("block/" + name(BlockRegistry.rajush.get())));
        plantBlock(BlockRegistry.taintedRoots.get(), modLoc("block/" + name(BlockRegistry.taintedRoots.get())));

        variantBlock(BlockRegistry.suspiciousIce.get(), "suspicious_ice_0", "suspicious_ice_1", "suspicious_ice_2", "suspicious_ice_3");
        variantBlock(BlockRegistry.suspiciousTombstone.get(), "suspicious_tombstone_0", "suspicious_tombstone_1", "suspicious_tombstone_2", "suspicious_tombstone_3");
        variantBlock(BlockRegistry.voidRoots.get(), "void_roots", "void_roots_1");

        for (Block doublePlant : new Block[]{BlockRegistry.doubleSoulroot.get(), BlockRegistry.doubleMagmaroot.get(), BlockRegistry.doubleGoldy.get(), BlockRegistry.voidthorn.get(), BlockRegistry.gaibRoots.get(), BlockRegistry.karusakanRoots.get()}) {
            String bName = name(doublePlant);
            ModelFile lowerModel = models().cross(bName, modLoc("block/" + bName)).renderType("cutout");
            ModelFile upperModel = models().cross(bName + "_top", modLoc("block/" + bName + "_top")).renderType("cutout");
            getVariantBuilder(doublePlant)
                    .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(lowerModel))
                    .partialState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(upperModel));

            simpleItem(doublePlant, modLoc("block/" + name(doublePlant) + "_top"));
        }
    }

    public String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private void pottedPlantBlock(Block block, String plantTextureName) {
        ModelFile potModel = models().withExistingParent(name(block), "minecraft:block/flower_pot_cross").renderType("cutout")
        .texture("plant", modLoc("block/" + plantTextureName));
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(potModel));
    }

    private void layeredPottedPlantBlock(Block block, String plantTextureName) {
        ModelFile potModel = models().withExistingParent(name(block), "valoria:block/layered_flower_pot_cross").renderType("cutout")
        .texture("plant", modLoc("block/" + plantTextureName))
        .texture("layer", modLoc("block/" + plantTextureName + "_tint"));
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(potModel));
    }

    private void glassBlock(Block block) {
        BlockModelBuilder model = models().cubeAll(name(block), blockTexture(block)).renderType("translucent");
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
        simpleBlockItem(block, model);
    }

    private void simpleBlockWithItem(Block block) {
        BlockModelBuilder model = models().cubeAll(name(block), blockTexture(block));
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
        simpleBlockItem(block, model);
    }

    private void simpleBlockWithItem(Block block, ResourceLocation tex) {
        BlockModelBuilder model = models().cubeAll(name(block), tex);
        getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
        simpleBlockItem(block, model);
    }

    private void randomRotationBlock(Block block, String... modelNames) {
        ConfiguredModel[] confModels = new ConfiguredModel[modelNames.length * 2];
        ModelFile firstModel = null;
        for (int i = 0; i < modelNames.length; i++) {
            String mName = modelNames[i];
            ModelFile mf;
            if (mName.contains("mirror")) {
                String texName = mName.equals("meat_mirror") ? "meat_block" : mName.replace("_mirrored", "").replace("_mirror", "");
                mf = models().singleTexture(mName, mcLoc("block/cube_mirrored_all"), "all", modLoc("block/" + texName));
            } else {
                mf = models().cubeAll(mName, modLoc("block/" + mName));
            }
            if (i == 0) firstModel = mf;
            confModels[i * 2] = new ConfiguredModel(mf);
            confModels[i * 2 + 1] = new ConfiguredModel(mf, 0, 180, false);
        }
        getVariantBuilder(block).partialState().addModels(confModels);
        if (firstModel != null) {
            simpleBlockItem(block, firstModel);
        }
    }

    private void variantBlock(Block block, String... modelNames) {
        ConfiguredModel[] confModels = new ConfiguredModel[modelNames.length];
        for (int i = 0; i < modelNames.length; i++) {
            confModels[i] = new ConfiguredModel(models().getExistingFile(modLoc("block/" + modelNames[i])));
        }
        getVariantBuilder(block).partialState().addModels(confModels);
    }

    private void variantBlockWithItem(Block block, String... modelNames) {
        variantBlock(block, modelNames);
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + modelNames[0])));
    }

    private void facingCrossBlockWithItemTex(Block block, String modelName) {
        ModelFile model = models().cross(modelName, modLoc("block/" + modelName)).renderType("cutout");
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.FACING);
            int rotX = 0, rotY = 0;
            switch (dir) {
                case DOWN -> rotX = 180;
                case EAST -> { rotX = 90; rotY = 90; }
                case NORTH -> { rotX = 90; rotY = 0; }
                case SOUTH -> { rotX = 90; rotY = 180; }
                case UP -> rotX = 0;
                case WEST -> { rotX = 90; rotY = 270; }
            }
            return ConfiguredModel.builder().modelFile(model).rotationX(rotX).rotationY(rotY).build();
        });
        simpleItem(block, modLoc("block/" + modelName));
    }

    private void trapBlock(Block trapBlock, Block baseStoneBlock) {
        trapBlock(trapBlock, baseStoneBlock, name(trapBlock));
    }

    private void trapBlock(Block trapBlock, Block baseStoneBlock, String topTextureName) {
        ResourceLocation sideTex = blockTexture(baseStoneBlock);
        ResourceLocation topTex = modLoc("block/" + topTextureName);
        ModelFile model = models().cube(name(trapBlock), sideTex, topTex, sideTex, sideTex, sideTex, sideTex)
        .texture("particle", sideTex);
        getVariantBuilder(trapBlock).forAllStates(state -> {
            boolean flag = state.hasProperty(BlockStateProperties.FACING);
            if(flag){
                Direction dir = state.getValue(BlockStateProperties.FACING);
                int rotX = switch(dir){
                    case DOWN -> 180;
                    case NORTH, WEST, EAST, SOUTH -> 90;
                    default -> 0;
                };
                int rotY = switch(dir){
                    case SOUTH -> 180;
                    case EAST -> 90;
                    case WEST -> 270;
                    default -> 0;
                };

                return ConfiguredModel.builder().modelFile(model).rotationX(rotX).rotationY(rotY).build();
            }

            return ConfiguredModel.builder().modelFile(model).build();
        });

        simpleBlockItem(trapBlock, model);
    }

    private void horizontalBlockCustom(Block block, String modelName, int rotNorthSouth, int rotEastWest) {
        ModelFile model = models().getExistingFile(modLoc("block/" + modelName));
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int rotY = switch (dir) {
                case EAST, WEST -> rotEastWest;
                case NORTH, SOUTH -> rotNorthSouth;
                default -> 0;
            };
            return ConfiguredModel.builder().modelFile(model).rotationX(0).rotationY(rotY).build();
        });

        simpleItem(block, modLoc("item/" + name(block)));
    }

    private void slabWithItem(SlabBlock slab, Block baseBlock) {
        slabBlock(slab, modLoc("block/" + name(baseBlock)), modLoc("block/" + name(baseBlock)));
        simpleBlockItem(slab, models().getExistingFile(modLoc("block/" + name(slab))));
    }

    private void stairsWithItem(StairBlock stairs, Block baseBlock) {
        stairsBlock(stairs, modLoc("block/" + name(baseBlock)));
        simpleBlockItem(stairs, models().getExistingFile(modLoc("block/" + name(stairs))));
    }

    private void wallWithItem(WallBlock wall, Block baseBlock) {
        wallBlock(wall, modLoc("block/" + name(baseBlock)));
        itemModels().wallInventory(name(wall), modLoc("block/" + name(baseBlock)));
    }

    private void fenceWithItem(FenceBlock fence, Block baseBlock) {
        fenceBlock(fence, modLoc("block/" + name(baseBlock)));
        itemModels().fenceInventory(name(fence), modLoc("block/" + name(baseBlock)));
    }

    private void fenceGateWithItem(FenceGateBlock gate, Block baseBlock) {
        fenceGateBlock(gate, modLoc("block/" + name(baseBlock)));
        simpleBlockItem(gate, models().getExistingFile(modLoc("block/" + name(gate))));
    }

    private void woodset(Block log, Block strippedLog, Block planks, Block leaves,
                         DoorBlock door, TrapDoorBlock trapdoor,
                         StandingSignBlock sign, WallSignBlock wallSign,
                         Block hangingSign, Block wallHangingSign,
                         ButtonBlock button, PressurePlateBlock pressurePlate,
                         StairBlock stairs, SlabBlock slab,
                         FenceBlock fence, FenceGateBlock gate) {
        logBlock((RotatedPillarBlock) log);
        logBlock((RotatedPillarBlock) strippedLog);
        simpleBlockWithItem(planks);
        leavesBlock(leaves);

        doorBlockWithRenderType(door, modLoc("block/" + name(door) + "_bottom"), modLoc("block/" + name(door) + "_top"), "cutout");
        simpleItem(door, modLoc("item/" + name(door)));

        trapdoorBlockWithRenderType(trapdoor, modLoc("block/" + name(trapdoor)), true, "cutout");
        simpleBlockItem(trapdoor, models().getExistingFile(modLoc("block/" + name(trapdoor) + "_bottom")));

        signBlock(sign, wallSign, modLoc("block/" + name(planks)));
        simpleItem(sign, modLoc("item/" + name(sign)));
        hangingSignBlock(hangingSign, wallHangingSign, modLoc("block/" + name(planks)));
        simpleItem(hangingSign, modLoc("item/" + name(hangingSign)));

        buttonBlock(button, modLoc("block/" + name(planks)));
        simpleBlockItem(button, models().getExistingFile(modLoc("block/" + name(button))));

        pressurePlateBlock(pressurePlate, modLoc("block/" + name(planks)));
        simpleBlockItem(pressurePlate, models().getExistingFile(modLoc("block/" + name(pressurePlate))));

        stairsWithItem(stairs, planks);
        slabWithItem(slab, planks);
        fenceWithItem(fence, planks);
        fenceGateWithItem(gate, planks);
    }

    private void registerMultipartBloodVein() {
        MultiPartBlockStateBuilder veinBuilder = getMultipartBuilder(BlockRegistry.bloodVein.get());
        ModelFile veinModel = models().getExistingFile(modLoc("block/blood_vein"));

        veinBuilder.part().modelFile(veinModel).addModel().condition(BlockStateProperties.NORTH, true).end();
        veinBuilder.part().modelFile(veinModel).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();

        veinBuilder.part().modelFile(veinModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true).end();
        veinBuilder.part().modelFile(veinModel).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();
        veinBuilder.part().modelFile(veinModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true).end();
        veinBuilder.part().modelFile(veinModel).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();

        veinBuilder.part().modelFile(veinModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true).end();
        veinBuilder.part().modelFile(veinModel).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();

        veinBuilder.part().modelFile(veinModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true).end();
        veinBuilder.part().modelFile(veinModel).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();

        veinBuilder.part().modelFile(veinModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true).end();
        veinBuilder.part().modelFile(veinModel).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).condition(BlockStateProperties.EAST, false).condition(BlockStateProperties.NORTH, false).condition(BlockStateProperties.SOUTH, false).condition(BlockStateProperties.UP, false).condition(BlockStateProperties.WEST, false).end();

        simpleItem(BlockRegistry.bloodVein.get(), modLoc("block/blood_vein"));
    }
}