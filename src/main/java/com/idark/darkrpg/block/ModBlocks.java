package com.idark.darkrpg.block;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.types.*;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.*;
import com.idark.darkrpg.world.tree.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
	private final static String MODID = DarkRPG.MOD_ID;
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	// Door & Trapdoors
	public static final RegistryObject<Block> SHADEWOOD_DOOR = registerBlock("shadewood_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).requiresCorrectToolForDrops().strength(2f, 4f).noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<Block> SHADEWOOD_TRAPDOOR = registerBlock("shadewood_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<Block> BRONZE_DOOR = registerBlock("bronze_door",
			() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).requiresCorrectToolForDrops().strength(4f, 4f).noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<Block> BRONZE_TRAPDOOR = registerBlock("bronze_trapdoor",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).requiresCorrectToolForDrops().strength(2f, 4f).noOcclusion(), BlockSetType.OAK));
	public static final RegistryObject<Block> BRONZE_TRAPDOOR_GLASS = registerBlock("bronze_trapdoor_glass",
			() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).requiresCorrectToolForDrops().strength(3f, 4f).noOcclusion(), BlockSetType.OAK));
	// Key
	public static final RegistryObject<Block> KEYBLOCK = BLOCK.register("keyblock",
			() -> new KeyPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
	public static final RegistryObject<Block> CUT_KEYBLOCK = BLOCK.register("cut_keyblock",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
	public static final RegistryObject<Block> KEYBLOCK_BRICKS = BLOCK.register("keyblock_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
	public static final RegistryObject<Block> KEYBLOCK_RUNE = BLOCK.register("keyblock_rune",
			() -> new KeyBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
	// Metal
	public static final RegistryObject<Block> COBALT_BLOCK = BLOCK.register("cobalt_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> NATURE_BLOCK = BLOCK.register("nature_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> AQUARIUS_BLOCK = BLOCK.register("aquarius_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> INFERNAL_BLOCK = BLOCK.register("infernal_block",
			() -> new InfernalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> AWAKENED_VOID_BLOCK = BLOCK.register("awakened_void_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> BRONZE_BLOCK = BLOCK.register("bronze_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> BRONZE_BLOCK_STAIRS = registerBlock("bronze_block_stairs",
			() -> new StairBlock(() -> BRONZE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> BRONZE_BLOCK_SLAB = registerBlock("bronze_block_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
	public static final RegistryObject<Block> CUT_BRONZE = BLOCK.register("cut_bronze",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> CUT_BRONZE_STAIRS = registerBlock("cut_bronze_stairs",
			() -> new StairBlock(() -> CUT_BRONZE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> CUT_BRONZE_SLAB = registerBlock("cut_bronze_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
	public static final RegistryObject<Block> BRONZE_VENT = BLOCK.register("bronze_vent",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> BRONZE_GLASS = BLOCK.register("bronze_glass",
			() -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion().sound(SoundType.GLASS)));
	// Raw
	public static final RegistryObject<Block> RAW_COBALT_ORE_BLOCK = BLOCK.register("raw_cobalt_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f)));
	// Stone Types
	public static final RegistryObject<Block> MEAT_BLOCK = BLOCK.register("meat_block",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE = BLOCK.register("ambane_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE_STAIRS = registerBlock("ambane_stone_stairs",
			() -> new StairBlock(() -> AMBANE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> AMBANE_STONE_SLAB = registerBlock("ambane_stone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE_WALL = BLOCK.register("ambane_stone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS = BLOCK.register("ambane_stone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_STAIRS = registerBlock("ambane_stone_bricks_stairs",
			() -> new StairBlock(() -> AMBANE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_SLAB = registerBlock("ambane_stone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_WALL = BLOCK.register("ambane_stone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE = BLOCK.register("polished_ambane_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_STAIRS = registerBlock("polished_ambane_stone_stairs",
			() -> new StairBlock(() -> POLISHED_AMBANE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_SLAB = registerBlock("polished_ambane_stone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> CUT_AMBANE_STONE = BLOCK.register("cut_ambane_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CHISELED_AMBANE_STONE_BRICKS = BLOCK.register("chiseled_ambane_stone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> DUNESTONE = BLOCK.register("dunestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> DUNESTONE_STAIRS = registerBlock("dunestone_stairs",
			() -> new StairBlock(() -> DUNESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DUNESTONE_SLAB = registerBlock("dunestone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> DUNESTONE_WALL = BLOCK.register("dunestone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> DUNESTONE_BRICKS = BLOCK.register("dunestone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_STAIRS = registerBlock("dunestone_bricks_stairs",
			() -> new StairBlock(() -> DUNESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_SLAB = registerBlock("dunestone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_WALL = BLOCK.register("dunestone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CUT_DUNESTONE = BLOCK.register("cut_dunestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> POLISHED_DUNESTONE = BLOCK.register("polished_dunestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> LIMESTONE = BLOCK.register("limestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> LIMESTONE_STAIRS = registerBlock("limestone_stairs",
			() -> new StairBlock(() -> LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIMESTONE_SLAB = registerBlock("limestone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> LIMESTONE_WALL = BLOCK.register("limestone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CUT_LIMESTONE = BLOCK.register("cut_limestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CUT_LIMESTONE_STAIRS = registerBlock("cut_limestone_stairs",
			() -> new StairBlock(() -> CUT_LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CUT_LIMESTONE_SLAB = registerBlock("cut_limestone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS = BLOCK.register("limestone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS = registerBlock("limestone_bricks_stairs",
			() -> new StairBlock(() -> LIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB = registerBlock("limestone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_WALL = BLOCK.register("limestone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS = BLOCK.register("cracked_limestone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_STAIRS = registerBlock("cracked_limestone_bricks_stairs",
			() -> new StairBlock(() -> CRACKED_LIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_SLAB = registerBlock("cracked_limestone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS = BLOCK.register("cracked_tombstone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS_WALL = BLOCK.register("cracked_tombstone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_WALL = BLOCK.register("cracked_limestone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRYSTAL_STONE = BLOCK.register("crystal_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_SLAB = registerBlock("crystal_stone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> CRYSTAL_STONE_STAIRS = registerBlock("crystal_stone_stairs",
			() -> new StairBlock(() -> CRYSTAL_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CRYSTAL_STONE_WALL = BLOCK.register("crystal_stone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_PILLAR = BLOCK.register("crystal_stone_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CUT_CRYSTAL_STONE = BLOCK.register("cut_crystal_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CUT_POLISHED_CRYSTAL_STONE = BLOCK.register("cut_polished_crystal_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS = BLOCK.register("crystal_stone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_SLAB = registerBlock("crystal_stone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_STAIRS = registerBlock("crystal_stone_bricks_stairs",
			() -> new StairBlock(() -> CRYSTAL_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_WALL = BLOCK.register("crystal_stone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> CHARGED_VOID_PILLAR = BLOCK.register("charged_void_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> POLISHED_CRYSTAL_STONE = BLOCK.register("polished_crystal_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> POLISHED_LIMESTONE = BLOCK.register("polished_limestone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs",
			() -> new StairBlock(() -> POLISHED_LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f)));
	public static final RegistryObject<Block> PEARLIUM = BLOCK.register("pearlium",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_STONE = BLOCK.register("void_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_STONE_STAIRS = registerBlock("void_stone_stairs",
			() -> new StairBlock(() -> VOID_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> VOID_STONE_SLAB = registerBlock("void_stone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_STONE_WALL = BLOCK.register("void_stone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_PILLAR_AMETHYST = BLOCK.register("void_pillar_amethyst",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_PILLAR = BLOCK.register("void_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_BRICK = BLOCK.register("void_brick",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_BRICK_STAIRS = registerBlock("void_brick_stairs",
			() -> new StairBlock(() -> VOID_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_BRICK_SLAB = registerBlock("void_brick_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_BRICK_WALL = BLOCK.register("void_brick_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK = BLOCK.register("void_cracked_brick",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_STAIRS = registerBlock("void_cracked_brick_stairs",
			() -> new StairBlock(() -> VOID_CRACKED_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_SLAB = registerBlock("void_cracked_brick_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_WALL = BLOCK.register("void_cracked_brick_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> POLISHED_VOID_STONE = BLOCK.register("polished_void_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> POLISHED_TOMBSTONE = BLOCK.register("polished_tombstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK = BLOCK.register("void_chiseled_brick",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS = BLOCK.register("void_chiseled_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS_STAIRS = registerBlock("void_chiseled_bricks_stairs",
			() -> new StairBlock(() -> VOID_CHISELED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS_SLAB = registerBlock("void_chiseled_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> VOID_GRASS = BLOCK.register("void_grass",
			() -> new VoidGrassBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> TOMBSTONE = BLOCK.register("tombstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_SPIKES_TRAP = BLOCK.register("tombstone_spikes_trap",
			() -> new SpikeTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> SPIKES = BLOCK.register("spikes",
			() -> new SpikeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(-1f, 3600000.8F).noLootTable()));
	public static final RegistryObject<Block> CUT_TOMBSTONE = BLOCK.register("cut_tombstone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_FIRECHARGE_TRAP = BLOCK.register("tombstone_firecharge_trap",
			() -> new FireTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> CUT_TOMBSTONE_PILLAR = BLOCK.register("cut_tombstone_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> WICKED_TOMBSTONE_PILLAR = BLOCK.register("wicked_tombstone_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_PILLAR = BLOCK.register("tombstone_pillar",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_SLAB = registerBlock("tombstone_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> TOMBSTONE_STAIRS = registerBlock("tombstone_stairs",
			() -> new StairBlock(() -> TOMBSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f, 4f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> TOMBSTONE_WALL = BLOCK.register("tombstone_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS = BLOCK.register("tombstone_bricks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_SLAB = registerBlock("tombstone_bricks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(6f, 4f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_STAIRS = registerBlock("tombstone_bricks_stairs",
			() -> new StairBlock(() -> TOMBSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f).requiresCorrectToolForDrops()));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_WALL = BLOCK.register("tombstone_bricks_wall",
			() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	// Wood
	public static final RegistryObject<Block> SHADEWOOD_PRESSURE_PLATE = BLOCK.register("shadewood_pressure_plate",
			() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), BlockSetType.OAK));
	public static final RegistryObject<Block> SHADEWOOD_BUTTON = BLOCK.register("shadewood_button",
			() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
	public static final RegistryObject<Block> SHADELOG = BLOCK.register("shadelog",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> STRIPPED_SHADELOG = BLOCK.register("stripped_shadelog",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
	public static final RegistryObject<Block> SHADEWOOD = BLOCK.register("shadewood",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> STRIPPED_SHADEWOOD = BLOCK.register("stripped_shadewood",
			() -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS = BLOCK.register("shadewood_planks",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS_SLAB = registerBlock("shadewood_planks_slab",
			() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS_STAIRS = registerBlock("shadewood_planks_stairs",
			() -> new StairBlock(() -> SHADEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
	public static final RegistryObject<Block> SHADEWOOD_LEAVES = BLOCK.register("shadewood_leaves",
			() -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
	public static final RegistryObject<Block> SHADEWOOD_SAPLING = BLOCK.register("shadewood_sapling",
			() -> new ShadeSaplingBlock(new ShadeWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
	public static final RegistryObject<Block> POTTED_SHADEWOOD_SAPLING = BLOCK.register("potted_shadewood_sapling",
			() -> new FlowerPotBlock(SHADEWOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	// Signs
	public static final RegistryObject<Block> SHADEWOOD_SIGN = BLOCK.register("shadewood_sign",
			() -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
	public static final RegistryObject<Block> SHADEWOOD_WALL_SIGN = BLOCK.register("shadewood_wall_sign",
			() -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
	public static final RegistryObject<Block> SHADEWOOD_HANGING_SIGN = BLOCK.register("shadewood_hanging_sign",
			() -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
	public static final RegistryObject<Block> SHADEWOOD_WALL_HANGING_SIGN = BLOCK.register("shadewood_wall_hanging_sign",
			() -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));

	// Other
	public static final RegistryObject<Block> GEODITE_DIRT = BLOCK.register("geodite_dirt",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.5f, 2f).sound(SoundType.ROOTED_DIRT)));
	public static final RegistryObject<Block> GEODITE_STONE = BLOCK.register("geodite_stone",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
	public static final RegistryObject<Block> STONE_CRUSHER = BLOCK.register("stone_crusher",
			() -> new CrusherBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1f, 2f)));
	public static final RegistryObject<Block> JEWELER_TABLE = BLOCK.register("jeweler_table",
			() -> new JewelerBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1f, 1f)));
	public static final RegistryObject<Block> TOMB = BLOCK.register("tomb",
			() -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1f, 1f)));
	public static final RegistryObject<Block> KEG = BLOCK.register("keg",
			() -> new KegBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).requiresCorrectToolForDrops().strength(1f, 1f)));
	public static final RegistryObject<Block> SARCOPHAGUS = BLOCK.register("sarcophagus",
		() -> new SarcoBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f)));
	public static final RegistryObject<Block> TILE = BLOCK.register("quartz_blackstone_tile",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 1f)));
	public static final RegistryObject<Block> QUICKSAND = BLOCK.register("quicksand",
			() -> new QuickSandBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 0.5f).sound(SoundType.SAND)));
	public static final RegistryObject<Block> ELEMENTAL_MANIPULATOR = BLOCK.register("elemental_manipulator",
			() -> new ManipulatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 1f).lightLevel(s -> 4).noOcclusion()));
	public static final RegistryObject<Block> SKULLY_PEDESTAL = BLOCK.register("skully_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 1f).noOcclusion()));
	public static final RegistryObject<Block> ELEGANT_PEDESTAL = BLOCK.register("elegant_pedestal",
			() -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 1f).noOcclusion()));
	public static final RegistryObject<Block> BRONZE_LAMP = BLOCK.register("bronze_lamp",
			() -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> DECORATED_BRONZE_LAMP = BLOCK.register("decorated_bronze_lamp",
			() -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> BRONZE_LAMP_BLOCK = BLOCK.register("bronze_lamp_block",
			() -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> SPIDER_EGG = BLOCK.register("spider_egg",
			() -> new SpiderBlock(BlockBehaviour.Properties.copy(Blocks.STONE).instabreak().noOcclusion().sound(SoundType.FROGSPAWN)));
	// Ore
	public static final RegistryObject<Block> AMBER_ORE = BLOCK.register("amber_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
	public static final RegistryObject<Block> AMETHYST_ORE = BLOCK.register("amethyst_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
	public static final RegistryObject<Block> RUBY_ORE = BLOCK.register("ruby_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
	public static final RegistryObject<Block> SAPPHIRE_ORE = BLOCK.register("sapphire_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
	public static final RegistryObject<Block> COBALT_ORE = BLOCK.register("cobalt_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(4f, 4f)));
	public static final RegistryObject<Block> DEEPSLATE_AMBER_ORE = BLOCK.register("deepslate_amber_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(6f, 8f)));
	public static final RegistryObject<Block> DEEPSLATE_AMETHYST_ORE = BLOCK.register("deepslate_amethyst_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(6f, 8f)));
	public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = BLOCK.register("deepslate_ruby_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(6f, 8f)));
	public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = BLOCK.register("deepslate_sapphire_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(6f, 8f)));
	public static final RegistryObject<Block> DEEPSLATE_COBALT_ORE = BLOCK.register("deepslate_cobalt_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().strength(6f, 8f)));
	public static final RegistryObject<Block> WICKED_AMETHYST_ORE = BLOCK.register("wicked_amethyst_ore",
			() -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(4f, 8f).sound(SoundType.NETHER_BRICKS)));
	public static final RegistryObject<Block> PEARLIUM_ORE = BLOCK.register("pearlium_ore",
			() -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
	// Crystals
	public static final RegistryObject<Block> VOID_CRYSTAL = BLOCK.register("void_crystal",
			() -> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
	public static final RegistryObject<Block> AMBER_CRYSTAL = BLOCK.register("amber_crystal",
			() -> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
	public static final RegistryObject<Block> AMETHYST_CRYSTAL = BLOCK.register("amethyst_crystal",
			() -> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
	public static final RegistryObject<Block> RUBY_CRYSTAL = BLOCK.register("ruby_crystal",
			() -> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
	public static final RegistryObject<Block> SAPPHIRE_CRYSTAL = BLOCK.register("sapphire_crystal",
			() -> new CrystalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
	// Pots
	public static final RegistryObject<Block> POT_SMALL = BLOCK.register("pot_small",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_SMALL_HANDLES = BLOCK.register("pot_small_handles",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).dropsLike(ModBlocks.POT_SMALL.get()).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG = BLOCK.register("pot_long",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_HANDLES = BLOCK.register("pot_long_handles",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).dropsLike(ModBlocks.POT_LONG.get()).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_MOSSY = BLOCK.register("pot_long_mossy",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).dropsLike(ModBlocks.POT_LONG.get()).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_MOSSY_HANDLES = BLOCK.register("pot_long_mossy_handles",
			() -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).dropsLike(ModBlocks.POT_LONG.get()).instabreak().noOcclusion().sound(ModSoundRegistry.POT)));
	// Plants
	public static final RegistryObject<Block> ALOE_SMALL = BLOCK.register("aloe_small",
			() -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> POTTED_ALOE_SMALL = BLOCK.register("potted_aloe_small",
			() -> new FlowerPotBlock(ALOE_SMALL.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> DRIED_PLANT = BLOCK.register("dried_plant",
			() -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> POTTED_DRIED_PLANT = BLOCK.register("potted_dried_plant",
			() -> new FlowerPotBlock(DRIED_PLANT.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> DRIED_ROOTS = BLOCK.register("dried_roots",
			() -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> POTTED_DRIED_ROOTS = BLOCK.register("potted_dried_roots",
			() -> new FlowerPotBlock(DRIED_ROOTS.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> ALOE = BLOCK.register("aloe",
			() -> new TallSandFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> CATTAIL = BLOCK.register("cattail",
			() -> new TallWaterFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> SOULROOT = BLOCK.register("soulroot",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_SOULROOT = BLOCK.register("potted_soulroot",
			() -> new FlowerPotBlock(SOULROOT.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> CRIMSON_SOULROOT = BLOCK.register("crimson_soulroot",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_CRIMSON_SOULROOT = BLOCK.register("potted_crimson_soulroot",
			() -> new FlowerPotBlock(CRIMSON_SOULROOT.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> DOUBLE_SOULROOT = BLOCK.register("double_crimson_soulroot",
			() -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> MAGMAROOT = BLOCK.register("crimson_magmaroot",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_MAGMAROOT = BLOCK.register("potted_crimson_magmaroot",
			() -> new FlowerPotBlock(MAGMAROOT.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> DOUBLE_MAGMAROOT = BLOCK.register("double_crimson_magmaroot",
			() -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> GOLDY = BLOCK.register("crimson_goldy",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_GOLDY = BLOCK.register("potted_crimson_goldy",
			() -> new FlowerPotBlock(GOLDY.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> DOUBLE_GOLDY = BLOCK.register("double_crimson_goldy",
			() -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> BLOODROOT = BLOCK.register("bloodroot",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_BLOODROOT = BLOCK.register("potted_bloodroot",
			() -> new FlowerPotBlock(BLOODROOT.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> RAJUSH = BLOCK.register("crimson_rajush",
			() -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_RAJUSH = BLOCK.register("potted_crimson_rajush",
			() -> new FlowerPotBlock(RAJUSH.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> FALSEFLOWER = BLOCK.register("falseflower",
			() -> new VoidFlowerBlock(MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_FALSEFLOWER = BLOCK.register("potted_falseflower",
			() -> new FlowerPotBlock(FALSEFLOWER.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> FALSEFLOWER_SMALL = BLOCK.register("falseflower_small",
			() -> new VoidFlowerBlock(MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_FALSEFLOWER_SMALL = BLOCK.register("potted_falseflower_small",
			() -> new FlowerPotBlock(FALSEFLOWER_SMALL.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> SOULFLOWER = BLOCK.register("soulflower",
			() -> new VoidFlowerBlock(MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_SOULFLOWER = BLOCK.register("potted_soulflower",
			() -> new FlowerPotBlock(SOULFLOWER.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> VOID_ROOTS = BLOCK.register("void_roots",
			() -> new VoidRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> POTTED_VOID_ROOTS = BLOCK.register("potted_void_roots",
			() -> new FlowerPotBlock(VOID_ROOTS.get(), BlockBehaviour.Properties.copy(Blocks.GRASS).instabreak().noOcclusion()));
	public static final RegistryObject<Block> GAIB_ROOTS = BLOCK.register("gaib_roots",
			() -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> KARUSAKAN_ROOTS = BLOCK.register("karusakan_roots",
			() -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));

	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCK.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
				new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCK.register(eventBus);
	}

	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (state) -> {
			return state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}
}