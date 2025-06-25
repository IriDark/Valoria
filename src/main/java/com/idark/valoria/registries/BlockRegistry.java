package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.block.types.altars.*;
import com.idark.valoria.registries.block.types.plants.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.level.tree.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockBehaviour.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.block.chest.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

import static com.idark.valoria.registries.ItemsRegistry.BLOCK_ITEMS;

public class BlockRegistry{
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, Valoria.ID);
    public static RegistryObject<Block>
    shadewoodChest, shadewoodTrappedChest, eldritchChest, eldritchTrappedChest,
    eldritchDoor, eldritchTrapdoor, shadewoodDoor, shadewoodTrapdoor, dreadwoodDoor, dreadwoodTrapdoor, bronzeDoor, bronzeTrapdoor, bronzeTrapdoorGlass,
    pyratiteBlock, pyratiteOre, pyratiteCrystal, amberBlock, amberOre, deepslateAmberOre, amberCrystal, sapphireBlock, sapphireOre, deepslateSapphireOre, sapphireCrystal, amethystBlock, amethystCrystal, rubyBlock, rubyOre, deepslateRubyOre, rubyCrystal, cobaltBlock, rawCobaltOreBlock, cobaltOre, deepslateCobaltOre, jadeOre, picriteJadeOre, wickedAmethystOre, dormantCrystals, pearliumOre,
    natureBlock, aquariusBlock, infernalBlock, awakenedVoidBlock, crimtaneBlock, unchargedShardBlock, soulShardBlock, wickedAmethystBlock,
    bronzeBlock, bronzeBlockStairs, bronzeBlockSlab, bronzeVent, cutBronze, cutBronzeStairs, cutBronzeSlab, bronzeGlass, bronzeGlassPane,
    eyeFlesh, eyeMeat, eyeStone, meatBlock, fleshRemains, meatPillar, fleshBlock, fleshCyst, bloodVein,
    cobbledShale, cobbledShaleStairs, cobbledShaleSlab, cobbledShaleWall, cobbledShaleBricks, cobbledShaleBricksStairs, cobbledShaleBricksSlab, cobbledShaleBricksWall, crackedCobbledShaleBricks, crackedCobbledShaleBricksStairs, crackedCobbledShaleBricksSlab, crackedCobbledShaleBricksWall, polishedCobbledShale, polishedCobbledShaleStairs, polishedCobbledShaleSlab, polishedCobbledShaleWall, cobbledShaleFirechargeTrap, cobbledShaleSpikesTrap,
    deepMarble, deepMarbleStairs, deepMarbleSlab, deepMarbleWall, polishedDeepMarble, polishedDeepMarbleStairs, polishedDeepMarbleSlab, polishedDeepMarbleWall,
    picrite, picriteStairs, picriteSlab, picriteWall, polishedPicrite, polishedPicriteStairs, polishedPicriteSlab, polishedPicriteWall, picriteBricks, picriteBricksStairs, picriteBricksSlab, picriteBricksWall,
    ephemariteLow, ephemariteLowStairs, ephemariteLowSlab, ephemariteLowWall, polishedEphemariteLow, polishedEphemariteLowStairs, polishedEphemariteLowSlab, polishedEphemariteLowWall,
    ephemarite, ephemariteStairs, ephemariteSlab, ephemariteWall, polishedEphemarite, polishedEphemariteStairs, polishedEphemariteSlab, polishedEphemariteWall,
    ambaneStone, ambaneStoneStairs, ambaneStoneSlab, ambaneStoneWall, ambaneStoneBricks, ambaneStoneBricksStairs, ambaneStoneBricksSlab, ambaneStoneBricksWall, polishedAmbaneStone, polishedAmbaneStoneStairs, polishedAmbaneStoneSlab, polishedAmbaneStoneWall, cutAmbaneStone, chiseledAmbaneStoneBricks,
    dunestone, dunestoneStairs, dunestoneSlab, dunestoneWall, dunestoneBricks, dunestoneBricksStairs, dunestoneBricksSlab, dunestoneBricksWall, cutDunestone, polishedDunestone, //other varities?
    limestone, limestoneStairs, limestoneSlab, limestoneWall, cutLimestone, cutLimestoneStairs, cutLimestoneSlab, limestoneBricks, limestoneBricksStairs, limestoneBricksSlab, limestoneBricksWall, crackedLimestoneBricks, crackedLimestoneBricksStairs, crackedLimestoneBricksSlab, crackedLimestoneBricksWall, polishedLimestone, polishedLimestoneStairs, polishedLimestoneSlab, polishedLimestoneWall, //todo
    crystalStone, crystalStoneStairs, crystalStoneSlab, crystalStoneWall, crystalStonePillar, cutCrystalStone, cutPolishedCrystalStone, crystalStoneBricks, crystalStoneBricksStairs, crystalStoneBricksSlab, crystalStoneBricksWall, polishedCrystalStone, //other varities
    ancientStone, ancientStoneSlab, ancientStoneStairs, ancientStoneWall, polishedAncientStone, polishedAncientStoneSlab, polishedAncientStoneStairs, polishedAncientStoneWall, cutAncientStone,

    // crafting stations
    stoneCrusher, jewelerTable, keg, tinkererWorkbench, elementalManipulator, kiln,

    // boss summon altars
    crypticAltar, wickedAltar,

    pearlium,
    voidStone, voidStoneStairs, voidStoneSlab, voidStoneWall, voidPillar, voidPillarAmethyst, chargedVoidPillar, voidBrick, voidBrickStairs, voidBrickSlab, voidBrickWall, voidCrackedBrick, voidCrackedBrickStairs, voidCrackedBrickSlab, voidCrackedBrickWall, polishedVoidStone, voidFirechargeTrap, voidSpikesTrap, voidChiseledBrick, voidChiseledBricks, voidChiseledBricksStairs, voidChiseledBricksSlab, voidSand, voidSandstone, voidSandstoneStairs, voidSandstoneSlab, voidSandstoneWall, smoothVoidSandstone, smoothVoidSandstoneStairs, smoothVoidSandstoneSlab, voidChiseledSandstone, voidCutSandstone, voidCutSandstoneSlab, voidGrass, voidTaint, voidTaintLantern, abyssalLantern,
    tombstone, tombstoneStairs, tombstoneSlab, tombstoneWall, tombstoneBricks, tombstoneBricksStairs, tombstoneBricksSlab, tombstoneBricksWall, crackedTombstoneBricks, crackedTombstoneBricksWall, //other varities?
    cutTombstone, polishedTombstone, tombstoneFirechargeTrap, tombstoneSpikesTrap, tombstonePillar, cutTombstonePillar, wickedTombstonePillar, cryptLantern,

    //wood
    shadewoodPressurePlate, shadewoodButton, shadelog, strippedShadelog, shadewood, strippedShadewood, shadewoodPlanks, shadewoodPlanksStairs, shadewoodPlanksSlab, shadewoodLeaves, shadewoodBranch, shadewoodSapling, pottedShadewoodSapling, shadewoodFence, shadewoodFenceGate, shadewoodSign, shadewoodWallSign, shadewoodHangingSign, shadewoodWallHangingSign,
    eldritchPressurePlate, eldritchButton, eldritchLog, strippedEldritchLog, eldritchWood, strippedEldritchWood, eldritchPlanks, eldritchPlanksStairs, eldritchPlanksSlab, eldritchLeaves, eldritchSapling, pottedEldritchSapling, eldritchFence, eldritchFenceGate, eldritchSign, eldritchWallSign, eldritchHangingSign, eldritchWallHangingSign,
    dreadwoodPressurePlate, dreadwoodButton, dreadwoodLog, strippedDreadwoodLog, dreadWood, strippedDreadWood, dreadwoodPlanks, dreadwoodPlanksStairs, dreadwoodPlanksSlab, dreadwoodSapling, pottedDreadwoodSapling, dreadwoodFence, dreadwoodFenceGate, dreadwoodSign, dreadwoodWallSign, dreadwoodHangingSign, dreadwoodWallHangingSign,

    //loot blocks
    potSmall, potSmallHandles, potLong, potLongHandles, potLongMossy, potLongMossyHandles, cryptPot, decoratedCryptPot,

    //other
    valoriaPortal, valoriaPortalFrame, grave, tomb, mossyTomb, woodenTomb, mossyWoodenTomb, sarcophagus, umbralKeypad, umbralActivator, umbralBlock, cutUmbralBlock, umbralBricks, quicksand, bronzeLamp, decoratedBronzeLamp, bronzeLampBlock,

    // decorative
    elegantPedestal, woodenCup, beerCup, rumCup, cup, teaCup, greenTeaCup, coffeeCup, cacaoCup, glassBottle, rumBottle, cokeBottle, akvavitBottle, liquorBottle, wineBottle, meadBottle, sakeBottle, kvassBottle, whiskeyBottle, cognacBottle,
    taintedRoots, bloodVine, bloodVinePlant, caveRootPlant, caveRoot, violetSprout, violetSproutPlant, glowVioletSprout, glowVioletSproutPlant, abyssalGlowfern, abyssalGlowfernPlant, aloeSmall, aloe, pottedAloeSmall, driedPlant, pottedDriedPlant, driedRoots, pottedDriedRoots, cattail, soulroot, pottedSoulroot, soulFlower, pottedSoulFlower, crimsonSoulroot, doubleSoulroot, pottedCrimsonSoulroot, magmaroot, doubleMagmaroot, pottedMagmaroot, goldy, doubleGoldy, pottedGoldy, rajush, pottedRajush, bloodroot, pottedBloodroot, falseFlower, falseFlowerSmall, pottedFalseflower, pottedFalseflowerSmall, voidRoots, pottedVoidRoots, voidSerpents, pottedVoidSerpents, voidvine, voidthorn, blightedGrass, pottedBlightedGrass, pottedVoidvine, gaibRoots, karusakanRoots, shadeBlossom, suspiciousIce, suspiciousTombstone, spikes;

    public static void load(IEventBus eventBus){
        shadewoodChest = registerBlock("shadewood_chest", () -> new TridotChestBlock(Properties.copy(Blocks.CHEST)));
        shadewoodTrappedChest = registerBlock("trapped_shadewood_chest", () -> new TridotTrappedChestBlock(Properties.copy(Blocks.TRAPPED_CHEST)));
        eldritchChest = registerBlock("eldritch_chest", () -> new TridotChestBlock(Properties.copy(Blocks.CHEST)));
        eldritchTrappedChest = registerBlock("trapped_eldritch_chest", () -> new TridotTrappedChestBlock(Properties.copy(Blocks.TRAPPED_CHEST)));

        spikes = registerBlock("spikes", () -> new SpikeBlock(propsUnbreakable(Blocks.STONE, MapColor.COLOR_GRAY).noOcclusion().noLootTable()));
        eldritchDoor = registerBlock("eldritch_door", () -> new DoorBlock(propsDeco(Blocks.OAK_DOOR, MapColor.COLOR_MAGENTA), BlockSetType.OAK));
        eldritchTrapdoor = registerBlock("eldritch_trapdoor", () -> new TrapDoorBlock(propsDeco(Blocks.OAK_TRAPDOOR, MapColor.COLOR_MAGENTA), BlockSetType.OAK));
        shadewoodDoor = registerBlock("shadewood_door", () -> new DoorBlock(propsDeco(Blocks.OAK_DOOR, MapColor.COLOR_PURPLE), BlockSetType.OAK));
        shadewoodTrapdoor = registerBlock("shadewood_trapdoor", () -> new TrapDoorBlock(propsDeco(Blocks.OAK_TRAPDOOR, MapColor.COLOR_PURPLE), BlockSetType.OAK));
        dreadwoodDoor = registerBlock("dreadwood_door", () -> new DoorBlock(propsDeco(Blocks.OAK_DOOR, MapColor.COLOR_PURPLE), BlockSetType.OAK));
        dreadwoodTrapdoor = registerBlock("dreadwood_trapdoor", () -> new TrapDoorBlock(propsDeco(Blocks.OAK_TRAPDOOR, MapColor.COLOR_PURPLE), BlockSetType.OAK));
        bronzeDoor = registerBlock("bronze_door", () -> new DoorBlock(propsDeco(Blocks.IRON_DOOR, MapColor.COLOR_BROWN), BlockSetType.IRON));
        bronzeTrapdoor = registerBlock("bronze_trapdoor", () -> new TrapDoorBlock(propsDeco(Blocks.IRON_TRAPDOOR, MapColor.COLOR_BROWN), BlockSetType.IRON));
        bronzeTrapdoorGlass = registerBlock("bronze_trapdoor_glass", () -> new TrapDoorBlock(propsDeco(Blocks.IRON_TRAPDOOR, MapColor.COLOR_BROWN), BlockSetType.IRON));

        valoriaPortalFrame = registerBlock("valoria_portal_frame", () -> new ValoriaPortalFrame(props(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).strength(42f, 3600000.8F).sound(SoundType.DEEPSLATE_TILES)));
        valoriaPortal = BLOCK.register("valoria_portal", () -> new ValoriaPortalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).mapColor(MapColor.COLOR_PURPLE)));

        umbralKeypad = registerBlock("umbral_keypad", () -> new UmbralKeyPadBlock(propsUnbreakable(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).sound(SoundType.NETHER_BRICKS).noLootTable()));
        umbralActivator = registerBlock("umbral_activator", () -> new UmbralActivatorBlock(propsUnbreakable(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).sound(SoundType.NETHER_BRICKS).noLootTable()));
        cutUmbralBlock = registerBlock("cut_umbral_block", () -> new Block(propsUnbreakable(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).sound(SoundType.NETHER_BRICKS).noLootTable()));
        umbralBlock = registerBlock("umbral_block", () -> new UmbralBlock(propsUnbreakable(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).sound(SoundType.NETHER_BRICKS).noLootTable()));
        umbralBricks = registerBlock("umbral_bricks", () -> new UmbralBlock(propsUnbreakable(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).sound(SoundType.NETHER_BRICKS).noLootTable()));
        pyratiteBlock = registerBlock("pyratite_block", () -> new Block(props(Blocks.AMETHYST_BLOCK, MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().lightLevel(Utils.Blocks.light(9))));
        pyratiteOre = registerBlock("pyratite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(10f, 12f), UniformInt.of(2, 4)));
        pyratiteCrystal = registerBlock("pyratite_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
        amberBlock = registerBlock("amber_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        amberOre = registerBlock("amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        deepslateAmberOre = registerBlock("deepslate_amber_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        amberCrystal = registerBlock("amber_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
        amethystBlock = registerBlock("amethyst_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        amethystCrystal = registerBlock("amethyst_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
        sapphireBlock = registerBlock("sapphire_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        sapphireOre = registerBlock("sapphire_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        deepslateSapphireOre = registerBlock("deepslate_sapphire_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        sapphireCrystal = registerBlock("sapphire_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
        rubyBlock = registerBlock("ruby_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        rubyOre = registerBlock("ruby_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        deepslateRubyOre = registerBlock("deepslate_ruby_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
        rubyCrystal = registerBlock("ruby_crystal", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
        cobaltBlock = registerBlock("cobalt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        cobaltOre = registerBlock("cobalt_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops()));
        rawCobaltOreBlock = registerBlock("raw_cobalt_ore", () -> new Block(props(Blocks.RAW_GOLD_BLOCK, MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f)));
        deepslateCobaltOre = registerBlock("deepslate_cobalt_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops()));
        jadeOre = registerBlock("jade_ore", () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 1)));
        picriteJadeOre = registerBlock("picrite_jade_ore", () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 1)));
        wickedAmethystOre = registerBlock("wicked_amethyst_ore", () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 1)));
        dormantCrystals = registerBlock("dormant_crystals", () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 3)));
        pearliumOre = registerBlock("pearlium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
        natureBlock = registerBlock("nature_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        aquariusBlock = registerBlock("aquarius_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        infernalBlock = registerBlock("infernal_block", () -> new InfernalBlock(props(Blocks.IRON_BLOCK, MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        awakenedVoidBlock = registerBlock("awakened_void_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        crimtaneBlock = registerBlock("crimtane_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));

        unchargedShardBlock = registerBlock("uncharged_shard_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        soulShardBlock = registerBlock("soul_shard_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        wickedAmethystBlock = registerBlock("wicked_amethyst_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
        bronzeBlock = registerBlock("bronze_block", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        bronzeBlockStairs = registerBlock("bronze_block_stairs", () -> new StairBlock(() -> BlockRegistry.bronzeBlock.get().defaultBlockState(), props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
        bronzeBlockSlab = registerBlock("bronze_block_slab", () -> new SlabBlock(props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
        cutBronze = registerBlock("cut_bronze", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        cutBronzeStairs = registerBlock("cut_bronze_stairs", () -> new StairBlock(() -> BlockRegistry.cutBronze.get().defaultBlockState(), props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
        cutBronzeSlab = registerBlock("cut_bronze_slab", () -> new SlabBlock(props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
        bronzeVent = registerBlock("bronze_vent", () -> new Block(props(Blocks.IRON_BLOCK, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
        bronzeGlass = registerBlock("bronze_glass", () -> new GlassBlock(props(Blocks.GLASS, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion().sound(SoundType.GLASS)));
        bronzeGlassPane = registerBlock("bronze_glass_pane", () -> new IronBarsBlock(props(Blocks.GLASS_PANE, MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion().sound(SoundType.GLASS)));

        eyeFlesh = registerBlock("eye_flesh", () -> new Block(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).requiresCorrectToolForDrops().strength(3f, 4f)));
        eyeMeat = registerBlock("eye_meat", () -> new Block(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).requiresCorrectToolForDrops().strength(3f, 4f)));
        eyeStone = registerBlock("eye_stone", () -> new Block(props(Blocks.DEEPSLATE, MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(6f, 6f)));

        cobbledShale = registerBlock("cobbled_shale", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        cobbledShaleStairs = registerBlock("cobbled_shale_stairs", () -> new StairBlock(() -> BlockRegistry.cobbledShale.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        cobbledShaleSlab = registerBlock("cobbled_shale_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        cobbledShaleWall = registerBlock("cobbled_shale_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        cobbledShaleBricks = registerBlock("cobbled_shale_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        cobbledShaleBricksStairs = registerBlock("cobbled_shale_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.polishedPicrite.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        cobbledShaleBricksSlab = registerBlock("cobbled_shale_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        cobbledShaleBricksWall = registerBlock("cobbled_shale_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        crackedCobbledShaleBricks = registerBlock("cracked_cobbled_shale_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        crackedCobbledShaleBricksStairs = registerBlock("cracked_cobbled_shale_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.polishedPicrite.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        crackedCobbledShaleBricksSlab = registerBlock("cracked_cobbled_shale_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        crackedCobbledShaleBricksWall = registerBlock("cracked_cobbled_shale_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        polishedCobbledShale = registerBlock("polished_cobbled_shale", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        polishedCobbledShaleStairs = registerBlock("polished_cobbled_shale_stairs", () -> new StairBlock(() -> BlockRegistry.polishedCobbledShale.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        polishedCobbledShaleSlab = registerBlock("polished_cobbled_shale_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        polishedCobbledShaleWall = registerBlock("polished_cobbled_shale_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));

        cobbledShaleFirechargeTrap = registerBlock("cobbled_shale_firecharge_trap", () -> new FireTrapBlock(polishedCobbledShale.get().defaultBlockState(), 12.0F, 14, ColorParticleData.create(185, 65, 145, 45, 25, 5).build(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops(), new MobEffectInstance(MobEffects.WITHER, 160, 0, false, true)));
        cobbledShaleSpikesTrap = registerBlock("cobbled_shale_spikes_trap", () -> new SpikeTrapBlock(polishedCobbledShale.get().defaultBlockState(), spikes.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f)));

        deepMarble = registerBlock("deep_marble", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        deepMarbleStairs = registerBlock("deep_marble_stairs", () -> new StairBlock(() -> BlockRegistry.deepMarble.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        deepMarbleSlab = registerBlock("deep_marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        deepMarbleWall = registerBlock("deep_marble_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        polishedDeepMarble = registerBlock("polished_deep_marble", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        polishedDeepMarbleStairs = registerBlock("polished_deep_marble_stairs", () -> new StairBlock(() -> BlockRegistry.polishedDeepMarble.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        polishedDeepMarbleSlab = registerBlock("polished_deep_marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        polishedDeepMarbleWall = registerBlock("polished_deep_marble_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));

        picrite = registerBlock("picrite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        picriteStairs = registerBlock("picrite_stairs", () -> new StairBlock(() -> BlockRegistry.picrite.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        picriteSlab = registerBlock("picrite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        picriteWall = registerBlock("picrite_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        polishedPicrite = registerBlock("polished_picrite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        polishedPicriteStairs = registerBlock("polished_picrite_stairs", () -> new StairBlock(() -> BlockRegistry.polishedPicrite.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        polishedPicriteSlab = registerBlock("polished_picrite_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        polishedPicriteWall = registerBlock("polished_picrite_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
        picriteBricks = registerBlock("picrite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
        picriteBricksStairs = registerBlock("picrite_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.polishedPicrite.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
        picriteBricksSlab = registerBlock("picrite_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
        picriteBricksWall = registerBlock("picrite_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));

        ephemariteLow = registerBlock("ephemarite_low", () -> new DescriptionBlock(Component.translatable("tooltip.valoria.geode").withStyle(ChatFormatting.GRAY), props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ephemariteLowStairs = registerBlock("ephemarite_low_stairs", () -> new StairBlock(() -> BlockRegistry.ephemariteLow.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ephemariteLowSlab = registerBlock("ephemarite_low_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
        ephemariteLowWall = registerBlock("ephemarite_low_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemariteLow = registerBlock("polished_ephemarite_low", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemariteLowStairs = registerBlock("polished_ephemarite_low_stairs", () -> new StairBlock(() -> BlockRegistry.polishedEphemariteLow.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemariteLowSlab = registerBlock("polished_ephemarite_low_slab", () -> new SlabBlock(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
        polishedEphemariteLowWall = registerBlock("polished_ephemarite_low_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));

        ephemarite = registerBlock("ephemarite", () -> new DescriptionBlock(Component.translatable("tooltip.valoria.geode").withStyle(ChatFormatting.GRAY), props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ephemariteStairs = registerBlock("ephemarite_stairs", () -> new StairBlock(() -> BlockRegistry.ephemarite.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ephemariteSlab = registerBlock("ephemarite_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
        ephemariteWall = registerBlock("ephemarite_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemarite = registerBlock("polished_ephemarite", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemariteStairs = registerBlock("polished_ephemarite_stairs", () -> new StairBlock(() -> BlockRegistry.polishedEphemarite.get().defaultBlockState(), props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedEphemariteSlab = registerBlock("polished_ephemarite_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
        polishedEphemariteWall = registerBlock("polished_ephemarite_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));

        meatBlock = registerBlock("meat_block", () -> new FleshBlock(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).requiresCorrectToolForDrops()));
        meatPillar = registerBlock("reinforced_meat_pillar", () -> new RotatedPillarBlock(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundType.BONE_BLOCK).strength(1.5f).requiresCorrectToolForDrops()));
        fleshBlock = registerBlock("flesh_block", () -> new FleshBlock(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).requiresCorrectToolForDrops()));
        fleshRemains = registerBlock("flesh_remains", () -> new FleshBlock(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).strength(1.2f).requiresCorrectToolForDrops()));
        fleshCyst = registerBlock("flesh_cyst", () -> new FleshCystBlock(props(Blocks.STONE, MapColor.COLOR_RED).sound(SoundsRegistry.CYST).requiresCorrectToolForDrops()));
        bloodVein = registerBlock("blood_vein", () -> new BloodVeinBlock(props(Blocks.SCULK_VEIN, MapColor.COLOR_RED).sound(SoundsRegistry.FLESH).requiresCorrectToolForDrops().noOcclusion()));

        ambaneStone = registerBlock("ambane_stone", () -> new Block(props(Blocks.STONE, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneStairs = registerBlock("ambane_stone_stairs", () -> new StairBlock(() -> BlockRegistry.ambaneStone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneSlab = registerBlock("ambane_stone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneWall = registerBlock("ambane_stone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneBricks = registerBlock("ambane_stone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneBricksStairs = registerBlock("ambane_stone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.ambaneStoneBricks.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneBricksSlab = registerBlock("ambane_stone_bricks_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        ambaneStoneBricksWall = registerBlock("ambane_stone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        polishedAmbaneStone = registerBlock("polished_ambane_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        polishedAmbaneStoneStairs = registerBlock("polished_ambane_stone_stairs", () -> new StairBlock(() -> BlockRegistry.polishedAmbaneStone.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        polishedAmbaneStoneSlab = registerBlock("polished_ambane_stone_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        cutAmbaneStone = registerBlock("cut_ambane_stone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
        chiseledAmbaneStoneBricks = registerBlock("chiseled_ambane_stone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));

        dunestone = registerBlock("dunestone", () -> new Block(props(Blocks.STONE, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneStairs = registerBlock("dunestone_stairs", () -> new StairBlock(() -> BlockRegistry.dunestone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneSlab = registerBlock("dunestone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneWall = registerBlock("dunestone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneBricks = registerBlock("dunestone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneBricksStairs = registerBlock("dunestone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.dunestoneBricks.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneBricksSlab = registerBlock("dunestone_bricks_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.SAND).requiresCorrectToolForDrops()));
        dunestoneBricksWall = registerBlock("dunestone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.SAND).requiresCorrectToolForDrops()));
        cutDunestone = registerBlock("cut_dunestone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.SAND).requiresCorrectToolForDrops()));
        polishedDunestone = registerBlock("polished_dunestone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.SAND).requiresCorrectToolForDrops()));

        limestone = registerBlock("limestone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneStairs = registerBlock("limestone_stairs", () -> new StairBlock(() -> BlockRegistry.limestone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneSlab = registerBlock("limestone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneWall = registerBlock("limestone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        cutLimestone = registerBlock("cut_limestone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        cutLimestoneStairs = registerBlock("cut_limestone_stairs", () -> new StairBlock(() -> BlockRegistry.cutLimestone.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        cutLimestoneSlab = registerBlock("cut_limestone_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneBricks = registerBlock("limestone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneBricksStairs = registerBlock("limestone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.limestoneBricks.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneBricksSlab = registerBlock("limestone_bricks_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        limestoneBricksWall = registerBlock("limestone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        polishedLimestone = registerBlock("polished_limestone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        polishedLimestoneStairs = registerBlock("polished_limestone_stairs", () -> new StairBlock(() -> BlockRegistry.polishedLimestone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        polishedLimestoneSlab = registerBlock("polished_limestone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        crackedLimestoneBricks = registerBlock("cracked_limestone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        crackedLimestoneBricksStairs = registerBlock("cracked_limestone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.crackedLimestoneBricks.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        crackedLimestoneBricksSlab = registerBlock("cracked_limestone_bricks_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
        crackedLimestoneBricksWall = registerBlock("cracked_limestone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));

        crystalStone = registerBlock("crystal_stone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStoneSlab = registerBlock("crystal_stone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
        crystalStoneStairs = registerBlock("crystal_stone_stairs", () -> new StairBlock(() -> BlockRegistry.crystalStone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStoneWall = registerBlock("crystal_stone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStonePillar = registerBlock("crystal_stone_pillar", () -> new RotatedPillarBlock(props(Blocks.STONE, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        cutCrystalStone = registerBlock("cut_crystal_stone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        cutPolishedCrystalStone = registerBlock("cut_polished_crystal_stone", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStoneBricks = registerBlock("crystal_stone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStoneBricksSlab = registerBlock("crystal_stone_bricks_slab", () -> new SlabBlock(props(Blocks.STONE_BRICK_SLAB, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
        crystalStoneBricksStairs = registerBlock("crystal_stone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.crystalStoneBricks.get().defaultBlockState(), props(Blocks.STONE_BRICK_STAIRS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        crystalStoneBricksWall = registerBlock("crystal_stone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        polishedCrystalStone = registerBlock("polished_crystal_stone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));

        pearlium = registerBlock("pearlium", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));

        voidStone = registerBlock("void_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidStoneStairs = registerBlock("void_stone_stairs", () -> new StairBlock(() -> BlockRegistry.voidStone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).sound(SoundsRegistry.VOID_STONE).requiresCorrectToolForDrops()));
        voidStoneSlab = registerBlock("void_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidStoneWall = registerBlock("void_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidPillarAmethyst = registerBlock("void_pillar_amethyst", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE).lightLevel(Utils.Blocks.light(4))));
        chargedVoidPillar = registerBlock("charged_void_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS).lightLevel(Utils.Blocks.light(4))));
        voidPillar = registerBlock("void_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidBrick = registerBlock("void_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidBrickStairs = registerBlock("void_brick_stairs", () -> new StairBlock(() -> BlockRegistry.voidBrick.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops().sound(SoundsRegistry.VOID_STONE)));
        voidBrickSlab = registerBlock("void_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidBrickWall = registerBlock("void_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidCrackedBrick = registerBlock("void_cracked_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidCrackedBrickStairs = registerBlock("void_cracked_brick_stairs", () -> new StairBlock(() -> BlockRegistry.voidCrackedBrick.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops().sound(SoundsRegistry.VOID_STONE)));
        voidCrackedBrickSlab = registerBlock("void_cracked_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        voidCrackedBrickWall = registerBlock("void_cracked_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
        polishedVoidStone = registerBlock("polished_void_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));

        voidFirechargeTrap = registerBlock("void_firecharge_trap", () -> new FireTrapBlock(polishedVoidStone.get().defaultBlockState(), 12.0F, 14, ColorParticleData.create(185, 65, 145, 45, 25, 5).build(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops(), new MobEffectInstance(MobEffects.WITHER, 160, 0, false, true)));
        voidSpikesTrap = registerBlock("void_spikes_trap", () -> new SpikeTrapBlock(polishedVoidStone.get().defaultBlockState(), spikes.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f)));

        voidChiseledBrick = registerBlock("void_chiseled_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));
        voidChiseledBricks = registerBlock("void_chiseled_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));
        voidChiseledBricksStairs = registerBlock("void_chiseled_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.voidChiseledBricks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops()));
        voidChiseledBricksSlab = registerBlock("void_chiseled_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));

        voidSand = registerBlock("void_sand", () -> new VoidSandBlock(Col.hexToDecimal("3d313b"), BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.SAND).strength(1.25f).sound(SoundType.SAND)));
        voidSandstone = registerBlock("void_sandstone", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F)));
        voidSandstoneStairs = registerBlock("void_sandstone_stairs", () -> new StairBlock(() -> BlockRegistry.voidSandstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(2.0F, 6f).requiresCorrectToolForDrops()));
        voidSandstoneSlab = registerBlock("void_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BlockRegistry.voidSandstone.get()).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F, 6f)));
        voidSandstoneWall = registerBlock("void_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(BlockRegistry.voidSandstone.get()).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F, 6f)));
        smoothVoidSandstone = registerBlock("void_smooth_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
        smoothVoidSandstoneStairs = registerBlock("void_smooth_sandstone_stairs", () -> new StairBlock(() -> BlockRegistry.smoothVoidSandstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(2.0F, 6f).requiresCorrectToolForDrops()));
        smoothVoidSandstoneSlab = registerBlock("void_smooth_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BlockRegistry.smoothVoidSandstone.get()).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F, 6f)));
        voidChiseledSandstone = registerBlock("void_chiseled_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.2F)));
        voidCutSandstone = registerBlock("void_cut_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.2F)));
        voidCutSandstoneSlab = registerBlock("void_cut_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(BlockRegistry.voidCutSandstone.get()).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(1.2F, 6f)));

        voidGrass = registerBlock("void_grass", () -> new VoidGrassBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundsRegistry.VOID_GRASS)));
        voidTaint = registerBlock("void_taint", () -> new VoidTaintBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundsRegistry.VOID_GRASS)));
        voidTaintLantern = registerBlock("void_taint_lantern", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.FROGLIGHT).lightLevel((p_152688_) -> 9)));
        abyssalLantern = registerBlock("abyssal_lantern", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.FROGLIGHT).lightLevel((p_152688_) -> 15)));

        tombstone = registerBlock("tombstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        cutTombstone = registerBlock("cut_tombstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        polishedTombstone = registerBlock("polished_tombstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(3f, 6f)));
        tombstoneFirechargeTrap = registerBlock("tombstone_firecharge_trap", () -> new FireTrapBlock(BlockRegistry.polishedTombstone.get().defaultBlockState(), 6.0F, 8, ColorParticleData.create(255, 145, 45, 45, 0, 0).build(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        tombstoneSpikesTrap = registerBlock("tombstone_spikes_trap", () -> new SpikeTrapBlock(BlockRegistry.polishedTombstone.get().defaultBlockState(), spikes.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(3f, 4f)));
        cutTombstonePillar = registerBlock("cut_tombstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        wickedTombstonePillar = registerBlock("wicked_tombstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        tombstonePillar = registerBlock("tombstone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        tombstoneSlab = registerBlock("tombstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        tombstoneStairs = registerBlock("tombstone_stairs", () -> new StairBlock(() -> BlockRegistry.tombstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        tombstoneWall = registerBlock("tombstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE)));
        tombstoneBricks = registerBlock("tombstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        tombstoneBricksSlab = registerBlock("tombstone_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        tombstoneBricksStairs = registerBlock("tombstone_bricks_stairs", () -> new StairBlock(() -> BlockRegistry.tombstoneBricks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        tombstoneBricksWall = registerBlock("tombstone_bricks_wall", () -> new WallBlock(props(Blocks.STONE, MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        crackedTombstoneBricks = registerBlock("cracked_tombstone_bricks", () -> new Block(props(Blocks.STONE_BRICKS, MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundsRegistry.TOMBSTONE_BRICKS)));
        crackedTombstoneBricksWall = registerBlock("cracked_tombstone_bricks_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
        cryptLantern = registerBlock("crypt_lantern", () -> new CryptLantern(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(Utils.Blocks.light(10))));
        crypticAltar = registerBlock("cryptic_altar", () -> new CrypticAltar(BlockBehaviour.Properties.copy(BlockRegistry.tombstone.get()).noOcclusion().strength(-1f, 3600000.0F).lightLevel(Utils.Blocks.light(10))));
        wickedAltar = registerBlock("wicked_altar", () -> new WickedAltar(BlockBehaviour.Properties.copy(BlockRegistry.picrite.get()).noOcclusion().strength(-1f, 3600000.0F).lightLevel(Utils.Blocks.light(10))));

        ancientStone = registerBlock("ancient_stone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ancientStoneSlab = registerBlock("ancient_stone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ancientStoneStairs = registerBlock("ancient_stone_stairs", () -> new StairBlock(() -> BlockRegistry.crystalStone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        ancientStoneWall = registerBlock("ancient_stone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedAncientStone = registerBlock("polished_ancient_stone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedAncientStoneSlab = registerBlock("polished_ancient_stone_slab", () -> new SlabBlock(props(Blocks.STONE_SLAB, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        polishedAncientStoneStairs = registerBlock("polished_ancient_stone_stairs", () -> new StairBlock(() -> BlockRegistry.crystalStone.get().defaultBlockState(), props(Blocks.STONE_STAIRS, MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
        polishedAncientStoneWall = registerBlock("polished_ancient_stone_wall", () -> new WallBlock(props(Blocks.STONE_BRICK_WALL, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
        cutAncientStone = registerBlock("cut_ancient_stone", () -> new Block(props(Blocks.STONE, MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));

        //wood
        shadewoodPressurePlate = registerBlock("shadewood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), BlockSetType.OAK));
        shadewoodButton = registerBlock("shadewood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
        shadelog = registerBlock("shadelog", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
        strippedShadelog = registerBlock("stripped_shadelog", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_PURPLE)));
        shadewood = registerBlock("shadewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
        strippedShadewood = registerBlock("stripped_shadewood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
        shadewoodPlanks = registerBlock("shadewood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE)));
        shadewoodPlanksSlab = registerBlock("shadewood_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_PURPLE)));
        shadewoodPlanksStairs = registerBlock("shadewood_planks_stairs", () -> new StairBlock(() -> BlockRegistry.shadewoodPlanks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
        shadewoodLeaves = registerBlock("shadewood_leaves", () -> new ShadeLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_CYAN)));
        shadewoodBranch = registerBlock("shadewood_branch", () -> new ShadeBranchBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()));
        shadewoodSapling = registerBlock("shadewood_sapling", () -> new ValoriaSaplingBlock(new ShadeWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_CYAN)));
        pottedShadewoodSapling = BLOCK.register("potted_shadewood_sapling", () -> new FlowerPotBlock(BlockRegistry.shadewoodSapling.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()));
        shadewoodFence = registerBlock("shadewood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_PURPLE)));
        shadewoodFenceGate = registerBlock("shadewood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_PURPLE), ModWoodTypes.SHADEWOOD));

        eldritchPressurePlate = registerBlock("eldritch_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), BlockSetType.OAK));
        eldritchButton = registerBlock("eldritch_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
        eldritchLog = registerBlock("eldritch_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        strippedEldritchLog = registerBlock("stripped_eldritch_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchWood = registerBlock("eldritch_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        strippedEldritchWood = registerBlock("stripped_eldritch_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchPlanks = registerBlock("eldritch_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchPlanksSlab = registerBlock("eldritch_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchPlanksStairs = registerBlock("eldritch_planks_stairs", () -> new StairBlock(() -> BlockRegistry.eldritchPlanks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
        eldritchLeaves = registerBlock("eldritch_leaves", () -> new ShadeLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchSapling = registerBlock("eldritch_sapling", () -> new ValoriaSaplingBlock(new EldritchTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_MAGENTA)));
        pottedEldritchSapling = BLOCK.register("potted_eldritch_sapling", () -> new FlowerPotBlock(eldritchSapling.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_MAGENTA).instabreak().noOcclusion()));
        eldritchFence = registerBlock("eldritch_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_MAGENTA)));
        eldritchFenceGate = registerBlock("eldritch_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_MAGENTA), ModWoodTypes.ELDRITCH));

        dreadwoodPressurePlate = registerBlock("dreadwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), BlockSetType.OAK));
        dreadwoodButton = registerBlock("dreadwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
        dreadwoodLog = registerBlock("dreadwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        strippedDreadwoodLog = registerBlock("stripped_dreadwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_MAGENTA)));
        dreadWood = registerBlock("dreadwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        strippedDreadWood = registerBlock("stripped_dreadwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
        dreadwoodPlanks = registerBlock("dreadwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA)));
        dreadwoodPlanksSlab = registerBlock("dreadwood_planks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_MAGENTA)));
        dreadwoodPlanksStairs = registerBlock("dreadwood_planks_stairs", () -> new StairBlock(() -> BlockRegistry.eldritchPlanks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
        dreadwoodSapling = registerBlock("dreadwood_sapling", () -> new ValoriaSaplingBlock(new DreadwoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_MAGENTA)));
        pottedDreadwoodSapling = BLOCK.register("potted_dreadwood_sapling", () -> new FlowerPotBlock(dreadwoodSapling.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_MAGENTA).instabreak().noOcclusion()));
        dreadwoodFence = registerBlock("dreadwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_MAGENTA)));
        dreadwoodFenceGate = registerBlock("dreadwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_MAGENTA), ModWoodTypes.ELDRITCH));

        // Signs
        shadewoodSign = BLOCK.register("shadewood_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
        shadewoodWallSign = BLOCK.register("shadewood_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
        shadewoodHangingSign = BLOCK.register("shadewood_hanging_sign", () -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
        shadewoodWallHangingSign = BLOCK.register("shadewood_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));

        eldritchSign = BLOCK.register("eldritch_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
        eldritchWallSign = BLOCK.register("eldritch_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
        eldritchHangingSign = BLOCK.register("eldritch_hanging_sign", () -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
        eldritchWallHangingSign = BLOCK.register("eldritch_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));

        dreadwoodSign = BLOCK.register("dreadwood_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.DREADWOOD));
        dreadwoodWallSign = BLOCK.register("dreadwood_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.DREADWOOD));
        dreadwoodHangingSign = BLOCK.register("dreadwood_hanging_sign", () -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.DREADWOOD));
        dreadwoodWallHangingSign = BLOCK.register("dreadwood_wall_hanging_sign", () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.DREADWOOD));

        keg = registerBlock("keg", () -> new KegBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f, 1f).noOcclusion()));
        kiln = registerBlock("kiln", () -> new KilnBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 1f).noOcclusion().lightLevel(Utils.Blocks.lightIfLit(13))));
        jewelerTable = registerBlock("jeweler_table", () -> new JewelerBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).strength(1f, 1f)));
        stoneCrusher = registerBlock("stone_crusher", () -> new CrusherBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 2f)));
        tinkererWorkbench = registerBlock("tinkerer_workbench", () -> new TinkererWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).noOcclusion()));
        elementalManipulator = registerBlock("elemental_manipulator", () -> new ManipulatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_GREEN).strength(3f, 1f).lightLevel(s -> 4).noOcclusion()));

        grave = registerBlock("grave", () -> new TombBlock(true, BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 1f).noOcclusion()));
        tomb = registerBlock("tomb", () -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 1f).noOcclusion()));
        mossyTomb = registerBlock("mossy_tomb", () -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 1f).noOcclusion()));
        woodenTomb = registerBlock("wooden_tomb", () -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(1f, 1f).noOcclusion()));
        mossyWoodenTomb = registerBlock("mossy_wooden_tomb", () -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(1f, 1f).noOcclusion()));
        sarcophagus = registerBlock("sarcophagus", () -> new SarcophagusBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f).noOcclusion()));
        quicksand = registerBlock("quicksand", () -> new QuickSandBlock(BlockBehaviour.Properties.copy(Blocks.SAND).dynamicShape().requiresCorrectToolForDrops().strength(0.5f, 0.5f).sound(SoundType.SAND)));
        elegantPedestal = registerBlock("elegant_pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 1f).noOcclusion()));
        bronzeLamp = registerBlock("bronze_lamp", () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(Utils.Blocks.lightIfLit())));
        decoratedBronzeLamp = registerBlock("decorated_bronze_lamp", () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(Utils.Blocks.lightIfLit())));
        bronzeLampBlock = registerBlock("bronze_lamp_block", () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(Utils.Blocks.lightIfLit())));

        // Cups
        woodenCup = BLOCK.register("wooden_cup", BlockRegistry::woodenCup);
        beerCup = BLOCK.register("beer_cup", BlockRegistry::woodenCup);
        rumCup = BLOCK.register("rum_cup", BlockRegistry::woodenCup);

        cup = BLOCK.register("cup", BlockRegistry::cup);
        teaCup = BLOCK.register("tea_cup", BlockRegistry::cup);
        greenTeaCup = BLOCK.register("green_tea_cup", BlockRegistry::cup);
        coffeeCup = BLOCK.register("coffee_cup", BlockRegistry::cup);
        cacaoCup = BLOCK.register("cacao_cup", BlockRegistry::cup);

        // Bottles
        glassBottle = BLOCK.register("glass_bottle", () -> bottle(MapColor.COLOR_CYAN));
        rumBottle = BLOCK.register("rum_bottle", () -> bottle(MapColor.COLOR_RED));
        cokeBottle = BLOCK.register("coke_bottle", () -> bottle(MapColor.COLOR_BROWN));
        akvavitBottle = BLOCK.register("akvavit_bottle", () -> bottle(MapColor.COLOR_YELLOW));
        sakeBottle = BLOCK.register("sake_bottle", () -> bottle(MapColor.COLOR_MAGENTA));
        liquorBottle = BLOCK.register("liquor_bottle", () -> bottle(MapColor.COLOR_GREEN));
        wineBottle = BLOCK.register("wine_bottle", () -> bottle(MapColor.COLOR_GREEN));
        meadBottle = BLOCK.register("mead_bottle", () -> bottle(MapColor.COLOR_ORANGE));

        // Large Bottles
        kvassBottle = BLOCK.register("kvass_bottle", () -> largeBottle(MapColor.COLOR_BROWN));
        whiskeyBottle = BLOCK.register("whiskey_bottle", () -> largeBottle(MapColor.COLOR_RED));

        // Special Bottles
        cognacBottle = BLOCK.register("cognac_bottle", () -> cognacBottle(MapColor.COLOR_RED));

        // Pots
        potSmall = registerBlock("pot_small", () -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        potSmallHandles = registerBlock("pot_small_handles", () -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(potSmall).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        potLong = registerBlock("pot_long", () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        potLongHandles = registerBlock("pot_long_handles", () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(potLong).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        potLongMossy = registerBlock("pot_long_mossy", () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(potLong).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        potLongMossyHandles = registerBlock("pot_long_mossy_handles", () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(potLong).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
        cryptPot = registerBlock("crypt_pot", BlockRegistry::cryptPot);
        decoratedCryptPot = registerBlock("decorated_crypt_pot", BlockRegistry::cryptPot);

        taintedRoots = BLOCK.register("tainted_roots", () -> new TaintedRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).mapColor(MapColor.COLOR_MAGENTA).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));
        bloodVinePlant = BLOCK.register("blood_vine_plant", () -> new BloodVinePlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_RED)));
        bloodVine = registerBlock("blood_vine", () -> new BloodVineBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_RED)), () -> new BlockItem(BlockRegistry.bloodVine.get(), new Item.Properties()));

        violetSproutPlant = BLOCK.register("violet_sprout_plant", () -> new VioletSproutPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA), false));
        violetSprout = registerBlock("violet_sprout", () -> new VioletSproutBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_MAGENTA), false), () -> new TaintTransformBlockItem(BlockRegistry.violetSproutPlant.get(), new Item.Properties()));

        caveRootPlant = BLOCK.register("cave_root_plant", () -> new CaveRootPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA)));
        caveRoot = registerBlock("cave_root", () -> new CaveRootBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_MAGENTA)), () -> new TaintTransformBlockItem(BlockRegistry.caveRoot.get(), new Item.Properties()));

        glowVioletSproutPlant = BLOCK.register("glow_violet_sprout_plant", () -> new VioletSproutPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA).lightLevel(Utils.Blocks.plantLight()), true));
        glowVioletSprout = registerBlock("glow_violet_sprout", () -> new VioletSproutBlock(BlockBehaviour.Properties.copy(Blocks.KELP).lightLevel(Utils.Blocks.plantLight()), true), () -> new TaintTransformBlockItem(BlockRegistry.glowVioletSprout.get(), new Item.Properties()));

        abyssalGlowfernPlant = BLOCK.register("abyssal_glowfern_plant", () -> new AbyssalGlowFernPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA)));
        abyssalGlowfern = registerBlock("abyssal_glowfern", () -> new AbyssalGlowFernBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_MAGENTA).lightLevel(Utils.Blocks.plantLight())), () -> new TaintTransformBlockItem(BlockRegistry.abyssalGlowfern.get(), new Item.Properties()));

        aloeSmall = registerBlock("aloe_small", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        pottedAloeSmall = BLOCK.register("potted_aloe_small", () -> new FlowerPotBlock(BlockRegistry.aloeSmall.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        aloe = registerBlock("aloe", () -> new TallAloeBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        driedPlant = registerBlock("dried_plant", () -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        pottedDriedPlant = BLOCK.register("potted_dried_plant", () -> new FlowerPotBlock(BlockRegistry.driedPlant.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        driedRoots = registerBlock("dried_roots", () -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        pottedDriedRoots = BLOCK.register("potted_dried_roots", () -> new FlowerPotBlock(BlockRegistry.driedRoots.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        cattail = registerBlock("cattail", () -> new TallWaterFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        soulroot = registerBlock("soulroot", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        pottedSoulroot = BLOCK.register("potted_soulroot", () -> new FlowerPotBlock(BlockRegistry.soulroot.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        crimsonSoulroot = registerBlock("crimson_soulroot", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        pottedCrimsonSoulroot = BLOCK.register("potted_crimson_soulroot", () -> new FlowerPotBlock(BlockRegistry.crimsonSoulroot.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        doubleSoulroot = registerBlock("double_crimson_soulroot", () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        magmaroot = registerBlock("crimson_magmaroot", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        pottedMagmaroot = BLOCK.register("potted_crimson_magmaroot", () -> new FlowerPotBlock(BlockRegistry.magmaroot.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        doubleMagmaroot = registerBlock("double_crimson_magmaroot", () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        goldy = registerBlock("crimson_goldy", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        pottedGoldy = BLOCK.register("potted_crimson_goldy", () -> new FlowerPotBlock(BlockRegistry.goldy.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        doubleGoldy = registerBlock("double_crimson_goldy", () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        bloodroot = registerBlock("bloodroot", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
        pottedBloodroot = BLOCK.register("potted_bloodroot", () -> new FlowerPotBlock(BlockRegistry.bloodroot.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        rajush = registerBlock("crimson_rajush", () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)){
            protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos){
                return pState.is(TagsRegistry.MEAT) || pState.is(BlockTags.NYLIUM) || pState.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(pState, pLevel, pPos);
            }
        });

        pottedRajush = BLOCK.register("potted_crimson_rajush", () -> new FlowerPotBlock(BlockRegistry.rajush.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        falseFlower = registerBlock("falseflower", () -> new VoidFlowerBlock(() -> MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
        pottedFalseflower = BLOCK.register("potted_falseflower", () -> new FlowerPotBlock(BlockRegistry.falseFlower.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        falseFlowerSmall = registerBlock("falseflower_small", () -> new VoidFlowerBlock(() -> MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
        pottedFalseflowerSmall = BLOCK.register("potted_falseflower_small", () -> new FlowerPotBlock(BlockRegistry.falseFlowerSmall.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        soulFlower = registerBlock("soulflower", () -> new VoidFlowerBlock(() -> MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
        pottedSoulFlower = BLOCK.register("potted_soulflower", () -> new FlowerPotBlock(BlockRegistry.soulFlower.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        voidRoots = registerBlock("void_roots", () -> new VoidRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
        pottedVoidRoots = BLOCK.register("potted_void_roots", () -> new FlowerPotBlock(BlockRegistry.voidRoots.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        voidSerpents = registerBlock("void_serpents", () -> new VoidRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
        pottedVoidSerpents = BLOCK.register("potted_void_serpents", () -> new FlowerPotBlock(BlockRegistry.voidSerpents.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        voidvine = registerBlock("voidvine", () -> new VoidvineBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)), () -> new TaintTransformBlockItem(BlockRegistry.voidvine.get(), new Item.Properties()));
        voidthorn = registerBlock("voidthorn", () -> new VoidthornBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        blightedGrass = registerBlock("blighted_grass", () -> new VoidvineBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
        pottedBlightedGrass = BLOCK.register("potted_blighted_grass", () -> new FlowerPotBlock(BlockRegistry.blightedGrass.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));

        pottedVoidvine = BLOCK.register("potted_voidvine", () -> new FlowerPotBlock(BlockRegistry.voidvine.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
        gaibRoots = registerBlock("gaib_roots", () -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_BROWN)));
        karusakanRoots = registerBlock("karusakan_roots", () -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_BROWN)));
        shadeBlossom = registerBlock("shade_blossom", () -> new ShadeBlossomBlock(BlockBehaviour.Properties.copy(Blocks.SPORE_BLOSSOM).mapColor(MapColor.COLOR_LIGHT_BLUE)));

        // Sus
        suspiciousIce = registerBlock("suspicious_ice", () -> new CrushableBlock(true, Blocks.ICE, BlockBehaviour.Properties.copy(Blocks.ICE).friction(0.98F).noOcclusion().strength(0.5F).mapColor(MapColor.ICE).instrument(NoteBlockInstrument.SNARE).sound(SoundsRegistry.SUSPICIOUS_TOMBSTONE).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL));
        suspiciousTombstone = registerBlock("suspicious_tombstone", () -> new CrushableBlock(false, BlockRegistry.tombstone.get(), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.SNARE).strength(0.85F).sound(SoundsRegistry.SUSPICIOUS_TOMBSTONE).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL));

        BLOCK.register(eventBus);
    }

    public static BlockBehaviour.Properties props(BlockBehaviour behaviour, MapColor color){
        return BlockBehaviour.Properties.copy(behaviour).mapColor(color);
    }

    public static BlockBehaviour.Properties propsUnbreakable(BlockBehaviour behaviour, MapColor color){
        return BlockBehaviour.Properties.copy(behaviour).mapColor(color).strength(-1f, 3600000.8F);
    }

    public static BlockBehaviour.Properties propsDeco(BlockBehaviour behaviour, MapColor color){
        return BlockBehaviour.Properties.copy(behaviour).mapColor(color);
    }

    private static BottleBlock bottle(MapColor pMapColor){
        return new BottleBlock(BlockBehaviour.Properties.of().mapColor(pMapColor).noOcclusion().strength(0.1F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY));
    }

    private static CupBlock cup(){
        return new CupBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.1F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY));
    }

    private static CupBlock woodenCup(){
        return new CupBlock(BlockBehaviour.Properties.of().noOcclusion().strength(0.1F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    }

    private static PotBlock cryptPot(){
        return new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().noOcclusion().sound(SoundsRegistry.POT)){
            public VoxelShape makeShape(){
                VoxelShape shape = Shapes.empty();
                shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.25, 0.75, 0.625, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.375, 0.625, 0.375, 0.625, 0.6875, 0.625), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.25, 0.6875, 0.25, 0.75, 0.8125, 0.75), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0.1875, 0.75, 0.5, 0.5625, 0.9375), BooleanOp.OR);
                shape = Shapes.join(shape, Shapes.box(0.5, 0.1875, 0.0625, 0.5, 0.5625, 0.25), BooleanOp.OR);

                return shape;
            }

            @Override
            public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
                Vec3 vec3 = state.getOffset(worldIn, pos);
                return makeShape().move(vec3.x, vec3.y, vec3.z);
            }
        };
    }

    private static BottleBlock largeBottle(MapColor pMapColor){
        return new BottleBlock(BlockBehaviour.Properties.of().mapColor(pMapColor).noOcclusion().strength(0.1F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY)){
            private static final VoxelShape FIRST_AABB = Shapes.box(0.40625, 0, 0.375, 0.78125, 0.6875, 0.75);
            private static final VoxelShape SECOND_AABB = Shapes.box(0.09375, 0, 0.125, 0.96875, 0.6875, 0.9375);
            private static final VoxelShape THIRD_ABB = Shapes.box(0, 0, 0, 1, 0.6875, 1);

            public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
                Vec3 vec3 = pState.getOffset(pLevel, pPos);
                return switch(pState.getValue(BOTTLES)){
                    case 2 -> SECOND_AABB.move(vec3.x, vec3.y, vec3.z);
                    case 3, 4 -> THIRD_ABB.move(vec3.x, vec3.y, vec3.z);
                    default -> FIRST_AABB.move(vec3.x, vec3.y, vec3.z);
                };
            }
        };
    }

    private static BottleBlock cognacBottle(MapColor pMapColor){
        return new BottleBlock(BlockBehaviour.Properties.of().mapColor(pMapColor).noOcclusion().strength(0.1F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GLASS).pushReaction(PushReaction.DESTROY)){
            private static final VoxelShape FIRST_AABB = Shapes.box(0.34375, 0, 0.3125, 0.71875, 0.625, 0.6875);
            private static final VoxelShape SECOND_AABB = Shapes.box(0.09375, 0, 0.0625, 0.90625, 0.625, 0.875);
            private static final VoxelShape THIRD_ABB = Shapes.box(-0.02101600917974844, 0, 0.014467690303026637, 0.9946089908202516, 0.625, 1.0144676903030265);

            public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext){
                Vec3 vec3 = pState.getOffset(pLevel, pPos);
                return switch(pState.getValue(BOTTLES)){
                    case 2 -> SECOND_AABB.move(vec3.x, vec3.y, vec3.z);
                    case 3, 4 -> THIRD_ABB.move(vec3.x, vec3.y, vec3.z);
                    default -> FIRST_AABB.move(vec3.x, vec3.y, vec3.z);
                };
            }
        };
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Supplier<? extends BlockItem> item){
        RegistryObject<T> toReturn = BLOCK.register(name, block);
        BLOCK_ITEMS.register(name, item);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCK.register(name, block);
        BLOCK_ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }
}