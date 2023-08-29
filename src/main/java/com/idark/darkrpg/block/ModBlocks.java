package com.idark.darkrpg.block;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.types.*;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.ModItems;
import com.idark.darkrpg.util.*;
import com.idark.darkrpg.world.tree.*;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

	public class ModBlocks {
	private final static String MODID = DarkRPG.MOD_ID;
	public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	// Door & Trapdoors
	public static final RegistryObject<Block> SHADEWOOD_DOOR = registerBlock("shadewood_door",
	() -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD).harvestLevel(1).setRequiresTool().harvestTool(ToolType.AXE).hardnessAndResistance(2f).notSolid()));
	public static final RegistryObject<Block> SHADEWOOD_TRAPDOOR = registerBlock("shadewood_trapdoor",
	() -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD).harvestLevel(1).setRequiresTool().harvestTool(ToolType.AXE).hardnessAndResistance(1f).notSolid()));
	public static final RegistryObject<Block> BRONZE_DOOR = registerBlock("bronze_door",
	() -> new DoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4f).notSolid()));
	public static final RegistryObject<Block> BRONZE_TRAPDOOR2 = registerBlock("bronze_trapdoor2",
	() -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2f).notSolid()));
	public static final RegistryObject<Block> BRONZE_TRAPDOOR = registerBlock("bronze_trapdoor",
	() -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3f).notSolid()));
	// Key
	public static final RegistryObject<Block> KEYBLOCK = BLOCK.register("keyblock",
	() -> new KeyPadBlock(Properties.create(Material.IRON).hardnessAndResistance(-1f, 3600000.8F).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> KEYBLOCK_BRICKS = BLOCK.register("keyblock_bricks",
	() -> new Block(Properties.create(Material.IRON).hardnessAndResistance(-1f, 3600000.8F).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> KEYBLOCK_RUNE = BLOCK.register("keyblock_rune",
	() -> new KeyBlock(Properties.create(Material.IRON).hardnessAndResistance(-1f, 3600000.8F).sound(SoundType.NETHER_BRICK)));
	// Metal
	public static final RegistryObject<Block> COBALT_BLOCK = BLOCK.register("cobalt_block",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> NATURE_BLOCK = BLOCK.register("nature_block",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> AQUARIUS_BLOCK = BLOCK.register("aquarius_block",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> INFERNAL_BLOCK = BLOCK.register("infernal_block",
	() -> new InfernalBlock(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> AWAKENED_VOID_BLOCK = BLOCK.register("awakened_void_block",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_BLOCK = BLOCK.register("bronze_block",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_BLOCK_STAIRS = registerBlock("bronze_block_stairs",
	() -> new StairsBlock(() -> BRONZE_BLOCK.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_BLOCK_SLAB = registerBlock("bronze_block_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> BRONZE_PLATE = BLOCK.register("bronze_plate",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_PLATE_STAIRS = registerBlock("bronze_plate_stairs",
	() -> new StairsBlock(() -> BRONZE_PLATE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_PLATE_SLAB = registerBlock("bronze_plate_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE = BLOCK.register("cut_bronze",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE_STAIRS = registerBlock("cut_bronze_stairs",
	() -> new StairsBlock(() -> CUT_BRONZE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE_SLAB = registerBlock("cut_bronze_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> BRONZE_VENT = BLOCK.register("bronze_vent",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_GLASS = BLOCK.register("bronze_glass",
	() -> new GlassBlock(Properties.create(Material.GLASS).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1f).notSolid().sound(SoundType.GLASS)));
	// Stone Types
	public static final RegistryObject<Block> AMBANE_STONE = BLOCK.register("ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> AMBANE_STONE_STAIRS = registerBlock("ambane_stone_stairs",
	() -> new StairsBlock(() -> AMBANE_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> AMBANE_STONE_SLAB = registerBlock("ambane_stone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> AMBANE_STONE_WALL = BLOCK.register("ambane_stone_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS = BLOCK.register("ambane_stone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_STAIRS = registerBlock("ambane_stone_bricks_stairs",
	() -> new StairsBlock(() -> AMBANE_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_SLAB = registerBlock("ambane_stone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> AMBANE_STONE_BRICKS_WALL = BLOCK.register("ambane_stone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE = BLOCK.register("polished_ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_STAIRS = registerBlock("polished_ambane_stone_stairs",
	() -> new StairsBlock(() -> POLISHED_AMBANE_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_SLAB = registerBlock("polished_ambane_stone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> CUT_AMBANE_STONE = BLOCK.register("cut_ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CHISELED_AMBANE_STONE_BRICKS = BLOCK.register("chiseled_ambane_stone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> DUNESTONE = BLOCK.register("dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> DUNESTONE_STAIRS = registerBlock("dunestone_stairs",
	() -> new StairsBlock(() -> DUNESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> DUNESTONE_SLAB = registerBlock("dunestone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> DUNESTONE_WALL = BLOCK.register("dunestone_wall",
    () -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
    public static final RegistryObject<Block> DUNESTONE_BRICKS = BLOCK.register("dunestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
    public static final RegistryObject<Block> DUNESTONE_BRICKS_STAIRS = registerBlock("dunestone_bricks_stairs",
	() -> new StairsBlock(() -> DUNESTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_SLAB = registerBlock("dunestone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_WALL = BLOCK.register("dunestone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_DUNESTONE = BLOCK.register("cut_dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_DUNESTONE = BLOCK.register("polished_dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> LIMESTONE = BLOCK.register("limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> LIMESTONE_STAIRS = registerBlock("limestone_stairs",
	() -> new StairsBlock(() -> LIMESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> LIMESTONE_SLAB = registerBlock("limestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> LIMESTONE_WALL = BLOCK.register("limestone_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_LIMESTONE = BLOCK.register("cut_limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_LIMESTONE_STAIRS = registerBlock("cut_limestone_stairs",
	() -> new StairsBlock(() -> CUT_LIMESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CUT_LIMESTONE_SLAB = registerBlock("cut_limestone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS = BLOCK.register("limestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS = registerBlock("limestone_bricks_stairs",
	() -> new StairsBlock(() -> LIMESTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB = registerBlock("limestone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_WALL = BLOCK.register("limestone_bricks_wall",
    () -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS = BLOCK.register("cracked_limestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_STAIRS = registerBlock("cracked_limestone_bricks_stairs",
	() -> new StairsBlock(() -> CRACKED_LIMESTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_SLAB = registerBlock("cracked_limestone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS = BLOCK.register("cracked_tombstone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS_WALL = BLOCK.register("cracked_tombstone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_WALL = BLOCK.register("cracked_limestone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRYSTAL_STONE = BLOCK.register("crystal_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_SLAB = registerBlock("crystal_stone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> CRYSTAL_STONE_STAIRS = registerBlock("crystal_stone_stairs",
	() -> new StairsBlock(() -> CRYSTAL_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CRYSTAL_STONE_WALL = BLOCK.register("crystal_stone_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_PILLAR = BLOCK.register("crystal_stone_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_CRYSTAL_STONE = BLOCK.register("cut_crystal_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_POLISHED_CRYSTAL_STONE = BLOCK.register("cut_polished_crystal_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS = BLOCK.register("crystal_stone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_SLAB = registerBlock("crystal_stone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_STAIRS = registerBlock("crystal_stone_bricks_stairs",
	() -> new StairsBlock(() -> CRYSTAL_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_WALL = BLOCK.register("crystal_stone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CHARGED_VOID_PILLAR = BLOCK.register("charged_void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> POLISHED_CRYSTAL_STONE = BLOCK.register("polished_crystal_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_LIMESTONE = BLOCK.register("polished_limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs",
	() -> new StairsBlock(() -> POLISHED_LIMESTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> PEARLIUM = BLOCK.register("pearlium",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_STONE = BLOCK.register("void_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_STONE_STAIRS = registerBlock("void_stone_stairs",
	() -> new StairsBlock(() -> VOID_STONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).sound(SoundType.NETHER_BRICK).setRequiresTool()));
	public static final RegistryObject<Block> VOID_STONE_SLAB = registerBlock("void_stone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_STONE_WALL = BLOCK.register("void_stone_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_PILLAR_AMETHYST = BLOCK.register("void_pillar_amethyst",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_PILLAR = BLOCK.register("void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_BRICK = BLOCK.register("void_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_BRICK_STAIRS = registerBlock("void_brick_stairs",
	() -> new StairsBlock(() -> VOID_BRICK.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_BRICK_SLAB = registerBlock("void_brick_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_BRICK_WALL = BLOCK.register("void_brick_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK = BLOCK.register("void_cracked_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_STAIRS = registerBlock("void_cracked_brick_stairs",
	() -> new StairsBlock(() -> VOID_CRACKED_BRICK.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(1).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_SLAB = registerBlock("void_cracked_brick_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_WALL = BLOCK.register("void_cracked_brick_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> POLISHED_VOID_STONE = BLOCK.register("polished_void_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> POLISHED_TOMBSTONE = BLOCK.register("polished_tombstone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK = BLOCK.register("void_chiseled_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS = BLOCK.register("void_chiseled_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS_STAIRS = registerBlock("void_chiseled_bricks_stairs",
	() -> new StairsBlock(() -> VOID_CHISELED_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS_SLAB = registerBlock("void_chiseled_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> VOID_GRASS = BLOCK.register("void_grass",
	() -> new VoidGrassBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> TOMBSTONE = BLOCK.register("tombstone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_SPIKES_TRAP = BLOCK.register("tombstone_spikes_trap",
	() -> new SpikeTrapBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> SPIKES = BLOCK.register("spikes",
	() -> new SpikeBlock(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(-1f, 3600000.8F)));
	public static final RegistryObject<Block> CUT_TOMBSTONE = BLOCK.register("cut_tombstone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_FIRECHARGE_TRAP = BLOCK.register("tombstone_firecharge_trap",
	() -> new FireTrapBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CUT_TOMBSTONE_PILLAR = BLOCK.register("cut_tombstone_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> WICKED_TOMBSTONE_PILLAR = BLOCK.register("wicked_tombstone_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_PILLAR = BLOCK.register("tombstone_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_SLAB = registerBlock("tombstone_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> TOMBSTONE_STAIRS = registerBlock("tombstone_stairs",
	() -> new StairsBlock(() -> TOMBSTONE.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> TOMBSTONE_WALL = BLOCK.register("tombstone_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS = BLOCK.register("tombstone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_SLAB = registerBlock("tombstone_bricks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_STAIRS = registerBlock("tombstone_bricks_stairs",
	() -> new StairsBlock(() -> TOMBSTONE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> TOMBSTONE_BRICKS_WALL = BLOCK.register("tombstone_bricks_wall",
	() -> new WallBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	// Wood
	public static final RegistryObject<Block> SHADEWOOD_PRESSURE_PLATE = BLOCK.register("shadewood_pressure_plate",
	() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid().doesNotBlockMovement()));
	public static final RegistryObject<Block> SHADEWOOD_BUTTON = BLOCK.register("shadewood_button",
	() -> new WoodButtonBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD).doesNotBlockMovement()));
	public static final RegistryObject<Block> SHADELOG = BLOCK.register("shadelog",
	() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> STRIPPED_SHADELOG = BLOCK.register("stripped_shadelog",
	() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_LOG)));
	public static final RegistryObject<Block> SHADEWOOD = BLOCK.register("shadewood",
	() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> STRIPPED_SHADEWOOD = BLOCK.register("stripped_shadewood",
	() -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS = BLOCK.register("shadewood_planks",
	() -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS_SLAB = registerBlock("shadewood_planks_slab",
	() -> new SlabBlock(AbstractBlock.Properties.from(Blocks.OAK_SLAB)));
	public static final RegistryObject<Block> SHADEWOOD_PLANKS_STAIRS = registerBlock("shadewood_planks_stairs",
	() -> new StairsBlock(() -> SHADEWOOD_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.OAK_STAIRS)));
	public static final RegistryObject<Block> SHADEWOOD_LEAVES = BLOCK.register("shadewood_leaves",
	() -> new LeavesBlock(Properties.from(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> SHADEWOOD_SAPLING = BLOCK.register("shadewood_sapling",
	() -> new ShadeSaplingBlock(new ShadeWoodTree(), AbstractBlock.Properties.from(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_SHADEWOOD_SAPLING = BLOCK.register("potted_shadewood_sapling",
	() -> new FlowerPotBlock(SHADEWOOD_SAPLING.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	// Signs
	public static final RegistryObject<Block> SHADEWOOD_SIGN = BLOCK.register("shadewood_sign",
    () -> new ModStandingSignBlock(AbstractBlock.Properties.create(Material.IRON).notSolid().doesNotBlockMovement(), ModWoodTypes.SHADEWOOD));
    public static final RegistryObject<Block> SHADEWOOD_WALL_SIGN = BLOCK.register("shadewood_wall_sign",
    () -> new ModWallSignBlock(AbstractBlock.Properties.create(Material.IRON).notSolid().doesNotBlockMovement(), ModWoodTypes.SHADEWOOD));
	// Other
	public static final RegistryObject<Block> GEODITE_DIRT = BLOCK.register("geodite_dirt",
	() -> new Block(Properties.create(Material.ORGANIC).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.PLANT)));	
	public static final RegistryObject<Block> GEODITE_STONE = BLOCK.register("geodite_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));		
	public static final RegistryObject<Block> STONE_CRUSHER = BLOCK.register("stone_crusher",
	() -> new CrusherBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1f)));	
	public static final RegistryObject<Block> JEWELER_TABLE = BLOCK.register("jeweler_table",
	() -> new JewelerBlock(Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.AXE).setRequiresTool().hardnessAndResistance(1f)));	
	public static final RegistryObject<Block> TOMB = BLOCK.register("tomb",
	() -> new TombBlock(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1f)));
	public static final RegistryObject<Block> KEG = BLOCK.register("keg", 
	() -> new KegBlock(AbstractBlock.Properties.create(Material.WOOD).harvestLevel(1).harvestTool(ToolType.AXE).setRequiresTool().hardnessAndResistance(1f)));
	public static final RegistryObject<Block> SARCOPHAGUS = BLOCK.register("sarcophagus", 
	() -> new SarcoBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> SARCO_HALF = BLOCK.register("sarcophagus_half", 
	() -> new SarcoHalfBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> PLATE = BLOCK.register("plate", 
	() -> new Block(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> QUICKSAND = BLOCK.register("quicksand", 
	() -> new QuickSandBlock(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.SHOVEL).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.SAND)));
	public static final RegistryObject<Block> ELEMENTAL_MANIPULATOR = BLOCK.register("elemental_manipulator", 
	() -> new ManipulatorBlock(Properties.create(Material.IRON).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).setLightLevel(s -> 4).notSolid()));
	public static final RegistryObject<Block> SKULLY_PEDESTAL = BLOCK.register("skully_pedestal", 
	() -> new PedestalBlock(Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).notSolid()));
	public static final RegistryObject<Block> ELEGANT_PEDESTAL = BLOCK.register("elegant_pedestal", 
	() -> new PedestalBlock(Properties.create(Material.ROCK).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).notSolid()));
	public static final RegistryObject<Block> BRONZE_LAMP = BLOCK.register("bronze_lamp", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3f).notSolid().sound(SoundType.GLASS).setLightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> DECORATED_BRONZE_LAMP = BLOCK.register("decorated_bronze_lamp", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3f).notSolid().sound(SoundType.GLASS).setLightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> BRONZE_LAMP_BLOCK = BLOCK.register("bronze_lamp_block", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3f).notSolid().sound(SoundType.GLASS).setLightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> SPIDER_EGG = BLOCK.register("spider_egg", 
	() -> new SpiderBlock(Properties.create(Material.ROCK).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.SPIDER_EGG)));
	// Ore
	public static final RegistryObject<Block> AMBER_ORE = BLOCK.register("amber_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> AMETHYST_ORE = BLOCK.register("amethyst_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> RUBY_ORE = BLOCK.register("ruby_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> SAPPHIRE_ORE = BLOCK.register("sapphire_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> COBALT_ORE = BLOCK.register("cobalt_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f)));
	public static final RegistryObject<Block> WICKED_AMETHYST_ORE = BLOCK.register("wicked_amethyst_ore",
	() -> new WickedOreBlock(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f).sound(SoundType.NETHER_BRICK)));
	public static final RegistryObject<Block> PEARLIUM_ORE = BLOCK.register("pearlium_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	// Crystals
	public static final RegistryObject<Block> VOID_CRYSTAL = BLOCK.register("void_crystal", 
	() -> new CrystalBlock(Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.GLASS).notSolid()));
	public static final RegistryObject<Block> AMBER_CRYSTAL = BLOCK.register("amber_crystal", 
	() -> new CrystalBlock(Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.GLASS).notSolid()));
	public static final RegistryObject<Block> AMETHYST_CRYSTAL = BLOCK.register("amethyst_crystal", 
	() -> new CrystalBlock(Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.GLASS).notSolid()));
	public static final RegistryObject<Block> RUBY_CRYSTAL = BLOCK.register("ruby_crystal", 
	() -> new CrystalBlock(Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.GLASS).notSolid()));
	public static final RegistryObject<Block> SAPPHIRE_CRYSTAL = BLOCK.register("sapphire_crystal", 
	() -> new CrystalBlock(Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(1f).sound(SoundType.GLASS).notSolid()));
	// Pots
	public static final RegistryObject<Block> POT_SMALL = BLOCK.register("pot_small", 
	() -> new PotBlock(Properties.create(Material.GLASS).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_SMALL_HANDLESS = BLOCK.register("pot_small_handless", 
	() -> new PotBlock(Properties.create(Material.GLASS).lootFrom(ModBlocks.POT_SMALL.get()).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG = BLOCK.register("pot_long", 
	() -> new PotBlock(Properties.create(Material.GLASS).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_HANDLESS = BLOCK.register("pot_long_handless", 
	() -> new PotBlock(Properties.create(Material.GLASS).lootFrom(ModBlocks.POT_LONG.get()).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_MOSSY = BLOCK.register("pot_long_mossy", 
	() -> new PotBlock(Properties.create(Material.GLASS).lootFrom(ModBlocks.POT_LONG.get()).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	public static final RegistryObject<Block> POT_LONG_MOSSY_HANDLESS = BLOCK.register("pot_long_mossy_handless", 
	() -> new PotBlock(Properties.create(Material.GLASS).lootFrom(ModBlocks.POT_LONG.get()).harvestLevel(0).zeroHardnessAndResistance().notSolid().sound(ModSoundRegistry.POT)));
	// Plants	
	public static final RegistryObject<Block> ALOE_SMALL = BLOCK.register("aloe_small",
	() -> new DeadBushBlock(Properties.from(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> POTTED_ALOE_SMALL = BLOCK.register("potted_aloe_small",
	() -> new FlowerPotBlock(ALOE_SMALL.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> DRIED_PLANT = BLOCK.register("dried_plant",
	() -> new DriedBlock(Properties.from(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> POTTED_DRIED_PLANT = BLOCK.register("potted_dried_plant",
	() -> new FlowerPotBlock(DRIED_PLANT.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> DRIED_ROOTS = BLOCK.register("dried_roots",
	() -> new DriedBlock(Properties.from(Blocks.SUNFLOWER)));	
    public static final RegistryObject<Block> POTTED_DRIED_ROOTS = BLOCK.register("potted_dried_roots",
	() -> new FlowerPotBlock(DRIED_ROOTS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> ALOE = BLOCK.register("aloe",
	() -> new TallSandFlowerBlock(Properties.from(Blocks.SUNFLOWER)));	
	public static final RegistryObject<Block> CATTAIL = BLOCK.register("cattail",
	() -> new TallWaterFlowerBlock(Properties.from(Blocks.SUNFLOWER)));	
	public static final RegistryObject<Block> SOULROOT = BLOCK.register("soulroot",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_SOULROOT = BLOCK.register("potted_soulroot",
	() -> new FlowerPotBlock(SOULROOT.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> CRIMSON_SOULROOT = BLOCK.register("crimson_soulroot",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_CRIMSON_SOULROOT = BLOCK.register("potted_crimson_soulroot",
	() -> new FlowerPotBlock(CRIMSON_SOULROOT.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));	
	public static final RegistryObject<Block> DOUBLE_SOULROOT = BLOCK.register("double_crimson_soulroot",
	() -> new TallNetherFlowerBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> MAGMAROOT = BLOCK.register("crimson_magmaroot",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_MAGMAROOT = BLOCK.register("potted_crimson_magmaroot",
	() -> new FlowerPotBlock(MAGMAROOT.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> DOUBLE_MAGMAROOT = BLOCK.register("double_crimson_magmaroot",
	() -> new TallNetherFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> GOLDY = BLOCK.register("crimson_goldy",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_GOLDY = BLOCK.register("potted_crimson_goldy",
	() -> new FlowerPotBlock(GOLDY.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> DOUBLE_GOLDY = BLOCK.register("double_crimson_goldy",
	() -> new TallNetherFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> BLOODROOT = BLOCK.register("bloodroot",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_BLOODROOT = BLOCK.register("potted_bloodroot",
	() -> new FlowerPotBlock(BLOODROOT.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));	
	public static final RegistryObject<Block> RAJUSH = BLOCK.register("crimson_rajush",
	() -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_RAJUSH = BLOCK.register("potted_crimson_rajush",
	() -> new FlowerPotBlock(RAJUSH.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> FALSEFLOWER = BLOCK.register("falseflower",
	() -> new VoidFlowerBlock(Effects.POISON, 2, AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_FALSEFLOWER = BLOCK.register("potted_falseflower",
	() -> new FlowerPotBlock(FALSEFLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> FALSEFLOWER_SMALL = BLOCK.register("falseflower_small",
	() -> new VoidFlowerBlock(Effects.POISON, 2, AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_FALSEFLOWER_SMALL = BLOCK.register("potted_falseflower_small",
	() -> new FlowerPotBlock(FALSEFLOWER_SMALL.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> SOULFLOWER = BLOCK.register("soulflower",
	() -> new VoidFlowerBlock(Effects.NIGHT_VISION, 5, AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_SOULFLOWER = BLOCK.register("potted_soulflower",
	() -> new FlowerPotBlock(SOULFLOWER.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> VOID_ROOTS = BLOCK.register("void_roots",
	() -> new VoidRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_VOID_ROOTS = BLOCK.register("potted_void_roots",
	() -> new FlowerPotBlock(VOID_ROOTS.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()));
	public static final RegistryObject<Block> GAIB_ROOTS = BLOCK.register("gaib_roots",
	() -> new TallRootsBlock(Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> KARUSAKAN_ROOTS = BLOCK.register("karusakan_roots",
	() -> new TallRootsBlock(Properties.from(Blocks.CRIMSON_ROOTS)));
	
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = BLOCK.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}
	
	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
		new Item.Properties().group(ModItemGroup.DARKRPG_BLOCKS_GROUP)));
	}
	
	public static void register(IEventBus eventBus) {
		BLOCK.register(eventBus);
	}
	
	private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
		return (state) -> {
		return state.get(BlockStateProperties.LIT) ? lightValue : 0;
		};
	}
}