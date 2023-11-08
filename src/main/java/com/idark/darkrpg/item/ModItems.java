package com.idark.darkrpg.item;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.block.*;
import com.idark.darkrpg.item.curio.*;
import com.idark.darkrpg.item.curio.charm.*;
import com.idark.darkrpg.item.food.*;
import com.idark.darkrpg.item.types.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DarkRPG.MOD_ID);

	private final static String MODID = DarkRPG.MOD_ID;
	// BLOCK ITEMS (category)
	// Plants
	public static final RegistryObject<Item> ALOE = ITEMS.register("aloe", () -> new BlockItem(ModBlocks.ALOE.get(), new Item.Properties()));
	public static final RegistryObject<Item> ALOE_SMALL = ITEMS.register("aloe_small", () -> new BlockItem(ModBlocks.ALOE_SMALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CATTAIL = ITEMS.register("cattail", () -> new BlockItem(ModBlocks.CATTAIL.get(), new Item.Properties()));
	public static final RegistryObject<Item> KARUSAKAN_ROOTS = ITEMS.register("karusakan_roots", () -> new BlockItem(ModBlocks.KARUSAKAN_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item> GAIB_ROOTS = ITEMS.register("gaib_roots", () -> new BlockItem(ModBlocks.GAIB_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item> DRIED_PLANT = ITEMS.register("dried_plant", () -> new BlockItem(ModBlocks.DRIED_PLANT.get(), new Item.Properties()));
	public static final RegistryObject<Item> DRIED_ROOTS = ITEMS.register("dried_roots", () -> new BlockItem(ModBlocks.DRIED_ROOTS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRIMSON_SOULROOT = ITEMS.register("crimson_soulroot", () -> new BlockItem(ModBlocks.CRIMSON_SOULROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> MAGMAROOT = ITEMS.register("crimson_magmaroot", () -> new BlockItem(ModBlocks.MAGMAROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> GOLDY = ITEMS.register("crimson_goldy", () -> new BlockItem(ModBlocks.GOLDY.get(), new Item.Properties()));
	public static final RegistryObject<Item> RAJUSH = ITEMS.register("crimson_rajush", () -> new BlockItem(ModBlocks.RAJUSH.get(), new Item.Properties()));
	public static final RegistryObject<Item> DOUBLE_SOULROOT = ITEMS.register("double_crimson_soulroot", () -> new BlockItem(ModBlocks.DOUBLE_SOULROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> DOUBLE_MAGMAROOT = ITEMS.register("double_crimson_magmaroot", () -> new BlockItem(ModBlocks.DOUBLE_MAGMAROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> DOUBLE_GOLDY = ITEMS.register("double_crimson_goldy", () -> new BlockItem(ModBlocks.DOUBLE_GOLDY.get(), new Item.Properties()));
	public static final RegistryObject<Item> SOULROOT = ITEMS.register("soulroot", () -> new BlockItem(ModBlocks.SOULROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> BLOODROOT = ITEMS.register("bloodroot", () -> new BlockItem(ModBlocks.BLOODROOT.get(), new Item.Properties()));
	public static final RegistryObject<Item> FALSEFLOWER = ITEMS.register("falseflower", () -> new BlockItem(ModBlocks.FALSEFLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item> FALSEFLOWER_SMALL = ITEMS.register("falseflower_small", () -> new BlockItem(ModBlocks.FALSEFLOWER_SMALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> SOULFLOWER = ITEMS.register("soulflower", () -> new BlockItem(ModBlocks.SOULFLOWER.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_ROOTS = ITEMS.register("void_roots", () -> new BlockItem(ModBlocks.VOID_ROOTS.get(), new Item.Properties()));
	// Ore
	public static final RegistryObject<Item> GEODITE_DIRT = ITEMS.register("geodite_dirt", () -> new BlockItem(ModBlocks.GEODITE_DIRT.get(), new Item.Properties()));
	public static final RegistryObject<Item> GEODITE_STONE = ITEMS.register("geodite_stone", () -> new BlockItem(ModBlocks.GEODITE_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBER_ORE = ITEMS.register("amber_ore", () -> new BlockItem(ModBlocks.AMBER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST_ORE = ITEMS.register("amethyst_ore", () -> new BlockItem(ModBlocks.AMETHYST_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUBY_ORE = ITEMS.register("ruby_ore", () -> new BlockItem(ModBlocks.RUBY_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_ORE = ITEMS.register("sapphire_ore", () -> new BlockItem(ModBlocks.SAPPHIRE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> COBALT_ORE = ITEMS.register("cobalt_ore", () -> new BlockItem(ModBlocks.COBALT_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_AMBER_ORE = ITEMS.register("deepslate_amber_ore", () -> new BlockItem(ModBlocks.DEEPSLATE_AMBER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_AMETHYST_ORE = ITEMS.register("deepslate_amethyst_ore", () -> new BlockItem(ModBlocks.DEEPSLATE_AMETHYST_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_RUBY_ORE = ITEMS.register("deepslate_ruby_ore", () -> new BlockItem(ModBlocks.DEEPSLATE_RUBY_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_SAPPHIRE_ORE = ITEMS.register("deepslate_sapphire_ore", () -> new BlockItem(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_COBALT_ORE = ITEMS.register("deepslate_cobalt_ore", () -> new BlockItem(ModBlocks.DEEPSLATE_COBALT_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_ORE = ITEMS.register("pearlium_ore", () -> new BlockItem(ModBlocks.PEARLIUM_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> WICKED_AMETHYST_ORE = ITEMS.register("wicked_amethyst_ore", () -> new BlockItem(ModBlocks.WICKED_AMETHYST_ORE.get(), new Item.Properties()));
	// Raw
	public static final RegistryObject<Item> RAW_COBALT_ORE_BLOCK = ITEMS.register("raw_cobalt_ore", () -> new BlockItem(ModBlocks.RAW_COBALT_ORE_BLOCK.get(), new Item.Properties()));
	// Crystals
	public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new BlockItem(ModBlocks.VOID_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBER_CRYSTAL = ITEMS.register("amber_crystal", () -> new BlockItem(ModBlocks.AMBER_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST_CRYSTAL = ITEMS.register("amethyst_crystal", () -> new BlockItem(ModBlocks.AMETHYST_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUBY_CRYSTAL = ITEMS.register("ruby_crystal", () -> new BlockItem(ModBlocks.RUBY_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_CRYSTAL = ITEMS.register("sapphire_crystal", () -> new BlockItem(ModBlocks.SAPPHIRE_CRYSTAL.get(), new Item.Properties()));
	// Ingot Blocks
	public static final RegistryObject<Item> COBALT_BLOCK = ITEMS.register("cobalt_block", () -> new BlockItem(ModBlocks.COBALT_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> NATURE_BLOCK = ITEMS.register("nature_block", () -> new BlockItem(ModBlocks.NATURE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_BLOCK = ITEMS.register("aquarius_block", () -> new BlockItem(ModBlocks.AQUARIUS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> INFERNAL_BLOCK = ITEMS.register("infernal_block", () -> new BlockItem(ModBlocks.INFERNAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> AWAKENED_VOID_BLOCK = ITEMS.register("awakened_void_block", () -> new BlockItem(ModBlocks.AWAKENED_VOID_BLOCK.get(), new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> PEARLIUM = ITEMS.register("pearlium", () -> new BlockItem(ModBlocks.PEARLIUM.get(), new Item.Properties()));
	// Stones
	public static final RegistryObject<Item> AMBANE_STONE = ITEMS.register("ambane_stone", () -> new BlockItem(ModBlocks.AMBANE_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBANE_STONE_BRICKS = ITEMS.register("ambane_stone_bricks", () -> new BlockItem(ModBlocks.AMBANE_STONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBANE_STONE_WALL = ITEMS.register("ambane_stone_wall", () -> new BlockItem(ModBlocks.AMBANE_STONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBANE_STONE_BRICKS_WALL = ITEMS.register("ambane_stone_bricks_wall", () -> new BlockItem(ModBlocks.AMBANE_STONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHISELED_AMBANE_STONE_BRICKS = ITEMS.register("chiseled_ambane_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_AMBANE_STONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_AMBANE_STONE = ITEMS.register("cut_ambane_stone", () -> new BlockItem(ModBlocks.CUT_AMBANE_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_AMBANE_STONE = ITEMS.register("polished_ambane_stone", () -> new BlockItem(ModBlocks.POLISHED_AMBANE_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DUNESTONE = ITEMS.register("dunestone", () -> new BlockItem(ModBlocks.DUNESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DUNESTONE_BRICKS = ITEMS.register("dunestone_bricks", () -> new BlockItem(ModBlocks.DUNESTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> DUNESTONE_WALL = ITEMS.register("dunestone_wall", () -> new BlockItem(ModBlocks.DUNESTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> DUNESTONE_BRICKS_WALL = ITEMS.register("dunestone_bricks_wall", () -> new BlockItem(ModBlocks.DUNESTONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_DUNESTONE = ITEMS.register("cut_dunestone", () -> new BlockItem(ModBlocks.CUT_DUNESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_DUNESTONE = ITEMS.register("polished_dunestone", () -> new BlockItem(ModBlocks.POLISHED_DUNESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRACKED_LIMESTONE_BRICKS = ITEMS.register("cracked_limestone_bricks", () -> new BlockItem(ModBlocks.CRACKED_LIMESTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> LIMESTONE = ITEMS.register("limestone", () -> new BlockItem(ModBlocks.LIMESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> LIMESTONE_BRICKS = ITEMS.register("limestone_bricks", () -> new BlockItem(ModBlocks.LIMESTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> LIMESTONE_WALL = ITEMS.register("limestone_wall", () -> new BlockItem(ModBlocks.LIMESTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> LIMESTONE_BRICKS_WALL = ITEMS.register("limestone_bricks_wall", () -> new BlockItem(ModBlocks.LIMESTONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_LIMESTONE = ITEMS.register("cut_limestone", () -> new BlockItem(ModBlocks.CUT_LIMESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRACKED_LIMESTONE_BRICKS_WALL = ITEMS.register("cracked_limestone_bricks_wall", () -> new BlockItem(ModBlocks.CRACKED_LIMESTONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_LIMESTONE = ITEMS.register("polished_limestone", () -> new BlockItem(ModBlocks.POLISHED_LIMESTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE = ITEMS.register("crystal_stone", () -> new BlockItem(ModBlocks.CRYSTAL_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE_WALL = ITEMS.register("crystal_stone_wall", () -> new BlockItem(ModBlocks.CRYSTAL_STONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE_PILLAR = ITEMS.register("crystal_stone_pillar", () -> new BlockItem(ModBlocks.CRYSTAL_STONE_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE_BRICKS = ITEMS.register("crystal_stone_bricks", () -> new BlockItem(ModBlocks.CRYSTAL_STONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE_BRICKS_WALL = ITEMS.register("crystal_stone_bricks_wall", () -> new BlockItem(ModBlocks.CRYSTAL_STONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_CRYSTAL_STONE = ITEMS.register("cut_crystal_stone", () -> new BlockItem(ModBlocks.CUT_CRYSTAL_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_POLISHED_CRYSTAL_STONE = ITEMS.register("cut_polished_crystal_stone", () -> new BlockItem(ModBlocks.CUT_POLISHED_CRYSTAL_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_CRYSTAL_STONE = ITEMS.register("polished_crystal_stone", () -> new BlockItem(ModBlocks.POLISHED_CRYSTAL_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE = ITEMS.register("tombstone", () -> new BlockItem(ModBlocks.TOMBSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_TOMBSTONE = ITEMS.register("cut_tombstone", () -> new BlockItem(ModBlocks.CUT_TOMBSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_FIRECHARGE_TRAP = ITEMS.register("tombstone_firecharge_trap", () -> new BlockItem(ModBlocks.TOMBSTONE_FIRECHARGE_TRAP.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_SPIKES_TRAP = ITEMS.register("tombstone_spikes_trap", () -> new BlockItem(ModBlocks.TOMBSTONE_SPIKES_TRAP.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_BRICKS = ITEMS.register("tombstone_bricks", () -> new BlockItem(ModBlocks.TOMBSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_WALL = ITEMS.register("tombstone_wall", () -> new BlockItem(ModBlocks.TOMBSTONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_BRICKS_WALL = ITEMS.register("tombstone_bricks_wall", () -> new BlockItem(ModBlocks.TOMBSTONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRACKED_TOMBSTONE_BRICKS = ITEMS.register("cracked_tombstone_bricks", () -> new BlockItem(ModBlocks.CRACKED_TOMBSTONE_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRACKED_TOMBSTONE_BRICKS_WALL = ITEMS.register("cracked_tombstone_bricks_wall", () -> new BlockItem(ModBlocks.CRACKED_TOMBSTONE_BRICKS_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_TOMBSTONE = ITEMS.register("polished_tombstone", () -> new BlockItem(ModBlocks.POLISHED_TOMBSTONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_PILLAR = ITEMS.register("tombstone_pillar", () -> new BlockItem(ModBlocks.TOMBSTONE_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item> WICKED_TOMBSTONE_PILLAR = ITEMS.register("wicked_tombstone_pillar", () -> new BlockItem(ModBlocks.WICKED_TOMBSTONE_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_TOMBSTONE_PILLAR = ITEMS.register("cut_tombstone_pillar", () -> new BlockItem(ModBlocks.CUT_TOMBSTONE_PILLAR.get(), new Item.Properties()));
	//public static final RegistryObject<Item> SARCOPHAGUS = ITEMS.register("sarcophagus", () -> new BlockItem(ModBlocks.SARCOPHAGUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> SPIKES = ITEMS.register("spikes", () -> new BlockItem(ModBlocks.SPIKES.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_GRASS = ITEMS.register("void_grass", () -> new BlockItem(ModBlocks.VOID_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_STONE = ITEMS.register("void_stone", () -> new BlockItem(ModBlocks.VOID_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_STONE_WALL = ITEMS.register("void_stone_wall", () -> new BlockItem(ModBlocks.VOID_STONE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_BRICK = ITEMS.register("void_brick", () -> new BlockItem(ModBlocks.VOID_BRICK.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_CRACKED_BRICK = ITEMS.register("void_cracked_brick", () -> new BlockItem(ModBlocks.VOID_CRACKED_BRICK.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_BRICK_WALL = ITEMS.register("void_brick_wall", () -> new BlockItem(ModBlocks.VOID_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_CRACKED_BRICK_WALL = ITEMS.register("void_cracked_brick_wall", () -> new BlockItem(ModBlocks.VOID_CRACKED_BRICK_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_CHISELED_BRICK = ITEMS.register("void_chiseled_brick", () -> new BlockItem(ModBlocks.VOID_CHISELED_BRICK.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_CHISELED_BRICKS = ITEMS.register("void_chiseled_bricks", () -> new BlockItem(ModBlocks.VOID_CHISELED_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_VOID_STONE = ITEMS.register("polished_void_stone", () -> new BlockItem(ModBlocks.POLISHED_VOID_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_PILLAR = ITEMS.register("void_pillar", () -> new BlockItem(ModBlocks.VOID_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_PILLAR_AMETHYST = ITEMS.register("void_pillar_amethyst", () -> new BlockItem(ModBlocks.VOID_PILLAR_AMETHYST.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHARGED_VOID_PILLAR = ITEMS.register("charged_void_pillar", () -> new BlockItem(ModBlocks.CHARGED_VOID_PILLAR.get(), new Item.Properties()));
	// Wood
	public static final RegistryObject<Item> SHADELOG = ITEMS.register("shadelog", () -> new BlockItem(ModBlocks.SHADELOG.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD = ITEMS.register("shadewood", () -> new BlockItem(ModBlocks.SHADEWOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item> STRIPPED_SHADELOG = ITEMS.register("stripped_shadelog", () -> new BlockItem(ModBlocks.STRIPPED_SHADELOG.get(), new Item.Properties()));
	public static final RegistryObject<Item> STRIPPED_SHADEWOOD = ITEMS.register("stripped_shadewood", () -> new BlockItem(ModBlocks.STRIPPED_SHADEWOOD.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_PLANKS = ITEMS.register("shadewood_planks", () -> new BlockItem(ModBlocks.SHADEWOOD_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_PLANKS_BUTTON = ITEMS.register("shadewood_button", () -> new BlockItem(ModBlocks.SHADEWOOD_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("shadewood_pressure_plate", () -> new BlockItem(ModBlocks.SHADEWOOD_PRESSURE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_SIGN = ITEMS.register("shadewood_sign",() -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.SHADEWOOD_SIGN.get(), ModBlocks.SHADEWOOD_WALL_SIGN.get()));
	public static final RegistryObject<Item> SHADEWOOD_LEAVES = ITEMS.register("shadewood_leaves", () -> new BlockItem(ModBlocks.SHADEWOOD_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_SAPLING = ITEMS.register("shadewood_sapling", () -> new BlockItem(ModBlocks.SHADEWOOD_SAPLING.get(), new Item.Properties()));
	// BlockItem Misc
	public static final RegistryObject<Item> STONE_CRUSHER = ITEMS.register("stone_crusher", () -> new BlockItem(ModBlocks.STONE_CRUSHER.get(), new Item.Properties()));
	public static final RegistryObject<Item> JEWELER_TABLE = ITEMS.register("jeweler_table", () -> new BlockItem(ModBlocks.JEWELER_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> KEG = ITEMS.register("keg", () -> new BlockItem(ModBlocks.KEG.get(), new Item.Properties()));
	public static final RegistryObject<Item> KEYBLOCK = ITEMS.register("keyblock", () -> new BlockItem(ModBlocks.KEYBLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_KEYBLOCK = ITEMS.register("cut_keyblock", () -> new BlockItem(ModBlocks.CUT_KEYBLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> KEYBLOCK_BRICKS = ITEMS.register("keyblock_bricks", () -> new BlockItem(ModBlocks.KEYBLOCK_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> KEYBLOCK_RUNE = ITEMS.register("keyblock_rune", () -> new BlockItem(ModBlocks.KEYBLOCK_RUNE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TILE = ITEMS.register("quartz_blackstone_tile", () -> new BlockItem(ModBlocks.TILE.get(), new Item.Properties()));
	public static final RegistryObject<Item> QUICKSAND = ITEMS.register("quicksand", () -> new BlockItem(ModBlocks.QUICKSAND.get(), new Item.Properties()));
	public static final RegistryObject<Item> ELEMENTAL_MANIPULATOR = ITEMS.register("elemental_manipulator", () -> new BlockItem(ModBlocks.ELEMENTAL_MANIPULATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> SKULLY_PEDESTAL = ITEMS.register("skully_pedestal", () -> new BlockItem(ModBlocks.SKULLY_PEDESTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> ELEGANT_PEDESTAL = ITEMS.register("elegant_pedestal", () -> new BlockItem(ModBlocks.ELEGANT_PEDESTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMB = ITEMS.register("tomb", () -> new BlockItem(ModBlocks.TOMB.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_SMALL = ITEMS.register("pot_small", () -> new BlockItem(ModBlocks.POT_SMALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_SMALL_HANDLESS = ITEMS.register("pot_small_handless", () -> new BlockItem(ModBlocks.POT_SMALL_HANDLESS.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG = ITEMS.register("pot_long", () -> new BlockItem(ModBlocks.POT_LONG.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_HANDLESS = ITEMS.register("pot_long_handless", () -> new BlockItem(ModBlocks.POT_LONG_HANDLESS.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_MOSSY = ITEMS.register("pot_long_mossy", () -> new BlockItem(ModBlocks.POT_LONG_MOSSY.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_MOSSY_HANDLESS = ITEMS.register("pot_long_mossy_handless", () -> new BlockItem(ModBlocks.POT_LONG_MOSSY_HANDLESS.get(), new Item.Properties()));
	public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg", () -> new BlockItem(ModBlocks.SPIDER_EGG.get(), new Item.Properties()));
	// Bronze
	public static final RegistryObject<Item> BRONZE_BLOCK = ITEMS.register("bronze_block", () -> new BlockItem(ModBlocks.BRONZE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_PLATE = ITEMS.register("bronze_plate", () -> new BlockItem(ModBlocks.BRONZE_PLATE.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_VENT = ITEMS.register("bronze_vent", () -> new BlockItem(ModBlocks.BRONZE_VENT.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_GLASS = ITEMS.register("bronze_glass", () -> new BlockItem(ModBlocks.BRONZE_GLASS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_BRONZE = ITEMS.register("cut_bronze", () -> new BlockItem(ModBlocks.CUT_BRONZE.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_LAMP = ITEMS.register("bronze_lamp", () -> new BlockItem(ModBlocks.BRONZE_LAMP.get(), new Item.Properties()));
	public static final RegistryObject<Item> DECORATED_BRONZE_LAMP = ITEMS.register("decorated_bronze_lamp", () -> new BlockItem(ModBlocks.DECORATED_BRONZE_LAMP.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_LAMP_BLOCK = ITEMS.register("bronze_lamp_block", () -> new BlockItem(ModBlocks.BRONZE_LAMP_BLOCK.get(), new Item.Properties()));
	// ARMOR (category)
	public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.BOOTS, (new Item.Properties())));
	public static final RegistryObject<Item> SAMURAI_KABUTO = ITEMS.register("samurai_kabuto", () -> new ArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> SAMURAI_CHESTPLATE = ITEMS.register("samurai_chestplate", () -> new ArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> SAMURAI_LEGGINGS = ITEMS.register("samurai_leggings", () -> new ArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> SAMURAI_BOOTS = ITEMS.register("samurai_boots", () -> new ArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.BOOTS, (new Item.Properties())));
	public static final RegistryObject<Item> AWAKENED_VOID_HELMET = ITEMS.register("awakened_void_helmet", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.HELMET, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> AWAKENED_VOID_CHESTPLATE = ITEMS.register("awakened_void_chestplate", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.CHESTPLATE, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> AWAKENED_VOID_LEGGINGS = ITEMS.register("awakened_void_leggings", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.LEGGINGS, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> AWAKENED_VOID_BOOTS = ITEMS.register("awakened_void_boots", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.BOOTS, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> NATURE_HELMET = ITEMS.register("nature_helmet", () -> new ModEffectArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> NATURE_CHESTPLATE = ITEMS.register("nature_chestplate", () -> new ModEffectArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> NATURE_LEGGINGS = ITEMS.register("nature_leggings", () -> new ModEffectArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> NATURE_BOOTS = ITEMS.register("nature_boots", () -> new ModEffectArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.BOOTS, (new Item.Properties())));
	public static final RegistryObject<Item> DEPTH_HELMET = ITEMS.register("depth_helmet", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.HELMET, (new Item.Properties())));
	public static final RegistryObject<Item> DEPTH_CHESTPLATE = ITEMS.register("depth_chestplate", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
	public static final RegistryObject<Item> DEPTH_LEGGINGS = ITEMS.register("depth_leggings", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
	public static final RegistryObject<Item> DEPTH_BOOTS = ITEMS.register("depth_boots", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.BOOTS, (new Item.Properties())));
	public static final RegistryObject<Item> INFERNAL_HELMET = ITEMS.register("infernal_helmet", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.HELMET, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> INFERNAL_CHESTPLATE = ITEMS.register("infernal_chestplate", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.CHESTPLATE, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> INFERNAL_LEGGINGS = ITEMS.register("infernal_leggings", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.LEGGINGS, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> INFERNAL_BOOTS = ITEMS.register("infernal_boots", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.BOOTS, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> PHANTASM_HELMET = ITEMS.register("phantasm_helmet", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.HELMET, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> PHANTASM_CHESTPLATE = ITEMS.register("phantasm_chestplate", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.CHESTPLATE, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> PHANTASM_LEGGINGS = ITEMS.register("phantasm_leggings", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.LEGGINGS, (new Item.Properties().fireResistant())));
	public static final RegistryObject<Item> PHANTASM_BOOTS = ITEMS.register("phantasm_boots", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.BOOTS, (new Item.Properties().fireResistant())));
	// ITEMS (global category)
	public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMBER_GEM = ITEMS.register("amber_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST_GEM = ITEMS.register("amethyst_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RUBY_GEM = ITEMS.register("ruby_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_GEM = ITEMS.register("sapphire_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new TransformShardItem(TransformType.WICKED, new Item.Properties()));
	public static final RegistryObject<Item> SOULSTONE = ITEMS.register("soulstone", () -> new TransformShardItem(TransformType.SOUL, new Item.Properties()));
	public static final RegistryObject<Item> UNCHARGED_STONE = ITEMS.register("uncharged_stone", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TOXINS_BOTTLE = ITEMS.register("toxins_bottle", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> DIRT_GEODE = ITEMS.register("dirt_geode", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> STONE_GEODE = ITEMS.register("stone_geode", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> MINERS_BAG = ITEMS.register("miners_bag", () -> new DropItemProperty(DropType.MINERS, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GEM_BAG = ITEMS.register("gem_bag", () -> new DropItemProperty(DropType.GEM, new Item.Properties().rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NATURE_GIFT = ITEMS.register("nature_gift", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> OCEANIC_SHELL = ITEMS.register("oceanic_shell", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> INFERNAL_STONE = ITEMS.register("infernal_stone", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BONE_FRAGMENT = ITEMS.register("bone_fragment", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> PAIN_CRYSTAL = ITEMS.register("pain_crystal", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> ILLUSION_STONE = ITEMS.register("illusion_stone", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> SOUL_COLLECTOR_EMPTY = ITEMS.register("soul_collector_empty", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SOUL_COLLECTOR = ITEMS.register("soul_collector", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEXICON = ITEMS.register("lexicon", () -> new LexiconItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> VOID_KEY = ITEMS.register("void_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GAIB_ROOT = ITEMS.register("gaib_root", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> KARUSAKAN_ROOT = ITEMS.register("karusakan_root", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> WOODEN_CUP = ITEMS.register("wooden_cup",() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CUP = ITEMS.register("cup", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BOTTLE = ITEMS.register("bottle", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> ALOE_PIECE = ITEMS.register("aloe_piece", () -> new Item(new Item.Properties()));
	/**
	 * 	Food
	 */
	public static final RegistryObject<Item> ALOE_BANDAGE = ITEMS.register("aloe_bandage", () -> new AloeBandageItem(600, 0));
	public static final RegistryObject<Item> ALOE_BANDAGE_UPGRADED = ITEMS.register("aloe_bandage_upgraded", () -> new AloeBandageItem(700, 1));
	public static final RegistryObject<Item> CACAO_CUP = ITEMS.register("cacao_cup", () -> new CupDrinkItem(MobEffects.MOVEMENT_SPEED, 30, 0));
	public static final RegistryObject<Item> COFFE_CUP = ITEMS.register("coffe_cup", () -> new CupDrinkItem(MobEffects.MOVEMENT_SPEED, 125, 0));
	public static final RegistryObject<Item> TEA_CUP = ITEMS.register("tea_cup", () -> new CupDrinkItem(MobEffects.MOVEMENT_SPEED, 30, 0));
	public static final RegistryObject<Item> GREEN_TEA_CUP = ITEMS.register("green_tea_cup", () -> new GreenTeaCupItem(1800, 0));
	public static final RegistryObject<Item> BEER_CUP = ITEMS.register("beer_cup", () -> new AlcoholCupItem(100, 0));
	public static final RegistryObject<Item> RUM_CUP = ITEMS.register("rum_cup", () -> new AlcoholCupItem(150, 1));
	public static final RegistryObject<Item> KVASS_BOTTLE = ITEMS.register("kvass_bottle", () -> new AlcoholCupItem(125, 0));
	public static final RegistryObject<Item> VINE_BOTTLE = ITEMS.register("vine_bottle", () -> new AlcoholCupItem(325, 0));
	public static final RegistryObject<Item> AKVAVIT_BOTTLE = ITEMS.register("akvavit_bottle", () -> new AlcoholCupItem(485, 0));
	public static final RegistryObject<Item> LIQUOR_BOTTLE = ITEMS.register("liquor_bottle", () -> new AlcoholCupItem(560, 0));
	public static final RegistryObject<Item> RUM_BOTTLE = ITEMS.register("rum_bottle", () -> new AlcoholCupItem(600, 0));
	public static final RegistryObject<Item> COGNAC_BOTTLE = ITEMS.register("cognac_bottle", () -> new AlcoholCupItem(650, 0));
	public static final RegistryObject<Item> WHISKEY_BOTTLE = ITEMS.register("whiskey_bottle", () -> new AlcoholCupItem(760, 0));
	public static final RegistryObject<Item> COKE_BOTTLE = ITEMS.register("coke_bottle", () -> new BottleDrinkItem(MobEffects.MOVEMENT_SPEED, 325, 0));
	public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(6).build())));
	public static final RegistryObject<Item> HOLIDAY_CANDY = ITEMS.register("holiday_candy", () -> new Item(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(6).build())));

	//public static final RegistryObject<Item> WAND_OF_NATURE = ITEMS.register("wand_of_nature", () -> new NatureStaff(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> DUNESTONE_BRICK = ITEMS.register("dunestone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TOMBSTONE_BRICK = ITEMS.register("tombstone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMBANE_STONE_BRICK = ITEMS.register("ambane_stone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LIMESTONE_BRICK = ITEMS.register("limestone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_STONE_BRICK = ITEMS.register("crystal_stone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> VOID_STONE_BRICK = ITEMS.register("void_stone_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ANCIENT_INGOT = ITEMS.register("ancient_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_INGOT = ITEMS.register("pearlium_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> NATURE_INGOT = ITEMS.register("nature_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_INGOT = ITEMS.register("aquarius_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INFERNAL_INGOT = ITEMS.register("infernal_ingot", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot", () -> new Item(new Item.Properties().fireResistant()));
	/**
	 * 		TOOLS (category)
	 */
	public static final RegistryObject<Item> CLUB = ITEMS.register("club",
			() -> new ClubItem(Tiers.WOOD, 4, -3.2f, new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
			() -> new SwordItem(Tiers.IRON, 4, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> VOID_EDGE = ITEMS.register("void_edge",
			() -> new SwordItem(Tiers.NETHERITE, 6, -3f, new Item.Properties()));
	public static final RegistryObject<Item> BLOODHOUND = ITEMS.register("bloodhound",
			() -> new HoundItem(Tiers.NETHERITE, 4, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap",
			() -> new BlazeReapItem(Tiers.NETHERITE, 1, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> PHANTOM = ITEMS.register("phantom",
			() -> new PhantomItem(Tiers.NETHERITE, 6, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> MURASAMA = ITEMS.register("murasama",
			() -> new MurasamaItem(Tiers.NETHERITE, 4, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> SAMURAI_KUNAI = ITEMS.register("samurai_kunai",
			() -> new KunaiItem(new Item.Properties().durability(300)));
	public static final RegistryObject<Item> SAMURAI_POISONED_KUNAI = ITEMS.register("samurai_poisoned_kunai",
			() -> new PoisonedKunaiItem(new Item.Properties().durability(300)));

	public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
			() -> new SpearItem(Tiers.IRON, 3, -3.5f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
			() -> new SpearItem(Tiers.GOLD, 2, -3f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
			() -> new SpearItem(Tiers.DIAMOND, 1, -3.5f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
			() -> new SpearItem(Tiers.NETHERITE, 1, -3.5f, new Item.Properties()));
	public static final RegistryObject<Item> DOUBLE_SPEAR = ITEMS.register("double_spear",
			() -> new SpearItem(Tiers.NETHERITE, 5, -3.5f, new Item.Properties()));

	public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
			() -> new ScytheItem(Tiers.IRON, 3, -3.5f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
			() -> new ScytheItem(Tiers.GOLD, 3, -3.5f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
			() -> new ScytheItem(Tiers.DIAMOND, 3, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
			() -> new ScytheItem(Tiers.NETHERITE, 3, -3.4f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BEAST = ITEMS.register("beast",
			() -> new BeastScytheItem(Tiers.DIAMOND, 6, -3.6f, new Item.Properties()));

	public static final RegistryObject<Item> HOLIDAY_KATANA = ITEMS.register("holiday_katana",
			() -> new KatanaItem(Tiers.IRON, -1, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana",
			() -> new KatanaItem(Tiers.IRON, 0, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_KATANA = ITEMS.register("golden_katana",
			() -> new KatanaItem(Tiers.GOLD, 2, -1.8f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_KATANA = ITEMS.register("diamond_katana",
			() -> new KatanaItem(Tiers.DIAMOND, 1, -2f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_KATANA = ITEMS.register("netherite_katana",
			() -> new KatanaItem(Tiers.NETHERITE, 1, -1.8f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> SAMURAI_KATANA = ITEMS.register("samurai_katana",
			() -> new KatanaItem(Tiers.DIAMOND, 2, -2f, new Item.Properties()));

	public static final RegistryObject<Item> PEARLIUM_SWORD = ITEMS.register("pearlium_sword",
			() -> new SwordItem(Tiers.IRON, 3, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_PICKAXE = ITEMS.register("pearlium_pickaxe",
			() -> new PickaxeItem(Tiers.IRON, -1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_AXE = ITEMS.register("pearlium_axe",
			() -> new AxeItem(Tiers.IRON, 6, -3f, new Item.Properties()));

	public static final RegistryObject<Item> HOLIDAY_PICKAXE = ITEMS.register("holiday_pickaxe",
			() -> new PickaxeItem(Tiers.IRON, -1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> HOLIDAY_AXE = ITEMS.register("holiday_axe",
			() -> new AxeItem(Tiers.IRON, 1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> XMAS_SWORD = ITEMS.register("xmas_sword",
			() -> new SwordItem(Tiers.IRON, 3, -2.3f, new Item.Properties()));

	public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
			() -> new SwordItem(ModItemTier.COBALT, 5, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
			() -> new PickaxeItem(ModItemTier.COBALT, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
			() -> new ShovelItem(ModItemTier.COBALT, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
			() -> new AxeItem(ModItemTier.COBALT, 6, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
			() -> new HoeItem(ModItemTier.COBALT, -1, 0f, new Item.Properties()));

	public static final RegistryObject<Item> ENT = ITEMS.register("ent",
			() -> new SwordItem(ModItemTier.NATURE, 7, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_SCYTHE = ITEMS.register("nature_scythe",
			() -> new SwordItem(ModItemTier.NATURE, 9, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_PICKAXE = ITEMS.register("nature_pickaxe",
			() -> new PickaxeItem(ModItemTier.NATURE, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_SHOVEL = ITEMS.register("nature_shovel",
			() -> new ShovelItem(ModItemTier.NATURE, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_AXE = ITEMS.register("nature_axe",
			() -> new AxeItem(ModItemTier.NATURE, 5, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_HOE = ITEMS.register("nature_hoe",
			() -> new HoeItem(ModItemTier.NATURE, -1, 0f, new Item.Properties()));

	public static final RegistryObject<Item> CORAL_REEF = ITEMS.register("coral_reef",
			() -> new CoralReefItem(ModItemTier.AQUARIUS, 8, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_SCYTHE = ITEMS.register("aquarius_scythe",
			() -> new SwordItem(ModItemTier.AQUARIUS, 10, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_PICKAXE = ITEMS.register("aquarius_pickaxe",
			() -> new PickaxeItem(ModItemTier.AQUARIUS, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_SHOVEL = ITEMS.register("aquarius_shovel",
			() -> new ShovelItem(ModItemTier.AQUARIUS, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_AXE = ITEMS.register("aquarius_axe",
			() -> new AxeItem(ModItemTier.AQUARIUS, 10, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_HOE = ITEMS.register("aquarius_hoe",
			() -> new HoeItem(ModItemTier.AQUARIUS, -1, 0f, new Item.Properties()));

	public static final RegistryObject<Item> INFERNAL_SWORD = ITEMS.register("infernal_sword",
			() -> new MagmaSwordItem(ModItemTier.INFERNAL, 9, -2f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_SCYTHE = ITEMS.register("infernal_scythe",
			() -> new SwordItem(ModItemTier.INFERNAL, 11, -3.4f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_PICKAXE = ITEMS.register("infernal_pickaxe",
			() -> new PickaxeItem(ModItemTier.INFERNAL, 0, -2.8f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_SHOVEL = ITEMS.register("infernal_shovel",
			() -> new ShovelItem(ModItemTier.INFERNAL, 0, -2.9f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
			() -> new AxeItem(ModItemTier.INFERNAL, 10, -2.9f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_HOE = ITEMS.register("infernal_hoe",
			() -> new HoeItem(ModItemTier.INFERNAL, -1, 0f, new Item.Properties().fireResistant()));
	// ACCESORIES (category)
	public static final RegistryObject<Item> IRON_CHAIN = ITEMS.register("iron_chain",
			() -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> IRON_NECKLACE_AMBER = ITEMS.register("iron_necklace_amber",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_DIAMOND = ITEMS.register("iron_necklace_diamond",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_EMERALD = ITEMS.register("iron_necklace_emerald",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_RUBY = ITEMS.register("iron_necklace_ruby",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_SAPPHIRE = ITEMS.register("iron_necklace_sapphire",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_HEALTH = ITEMS.register("iron_necklace_health",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_ARMOR = ITEMS.register("iron_necklace_armor",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_NECKLACE_WEALTH = ITEMS.register("iron_necklace_wealth",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_CHAIN = ITEMS.register("golden_chain",
			() -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_AMBER = ITEMS.register("golden_necklace_amber",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_DIAMOND = ITEMS.register("golden_necklace_diamond",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_EMERALD = ITEMS.register("golden_necklace_emerald",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_RUBY = ITEMS.register("golden_necklace_ruby",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_SAPPHIRE = ITEMS.register("golden_necklace_sapphire",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_HEALTH = ITEMS.register("golden_necklace_health",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_ARMOR = ITEMS.register("golden_necklace_armor",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_WEALTH = ITEMS.register("golden_necklace_wealth",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_CHAIN = ITEMS.register("netherite_chain",
			() -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_AMBER = ITEMS.register("netherite_necklace_amber",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_DIAMOND = ITEMS.register("netherite_necklace_diamond",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_EMERALD = ITEMS.register("netherite_necklace_emerald",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_RUBY = ITEMS.register("netherite_necklace_ruby",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_SAPPHIRE = ITEMS.register("netherite_necklace_sapphire",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_HEALTH = ITEMS.register("netherite_necklace_health",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_ARMOR = ITEMS.register("netherite_necklace_armor",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_NECKLACE_WEALTH = ITEMS.register("netherite_necklace_wealth",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> LEATHER_BELT = ITEMS.register("leather_belt",
			() -> new CurioItemProperty(AccessoryType.BELT, AccessoryGem.BELT, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(80).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> IRON_RING_AMBER = ITEMS.register("iron_ring_amber",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_RING_DIAMOND = ITEMS.register("iron_ring_diamond",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_RING_EMERALD = ITEMS.register("iron_ring_emerald",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_RING_RUBY = ITEMS.register("iron_ring_ruby",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_RING_SAPPHIRE = ITEMS.register("iron_ring_sapphire",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_RING = ITEMS.register("golden_ring",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(40).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> GOLDEN_RING_AMBER = ITEMS.register("golden_ring_amber",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_RING_DIAMOND = ITEMS.register("golden_ring_diamond",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_RING_EMERALD = ITEMS.register("golden_ring_emerald",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_RING_RUBY = ITEMS.register("golden_ring_ruby",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_RING_SAPPHIRE = ITEMS.register("golden_ring_sapphire",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_RING = ITEMS.register("netherite_ring",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> NETHERITE_RING_AMBER = ITEMS.register("netherite_ring_amber",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_RING_DIAMOND = ITEMS.register("netherite_ring_diamond",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_RING_EMERALD = ITEMS.register("netherite_ring_emerald",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_RING_RUBY = ITEMS.register("netherite_ring_ruby",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHERITE_RING_SAPPHIRE = ITEMS.register("netherite_ring_sapphire",
			() -> new CurioItemProperty(AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves",
			() -> new CurioItemProperty(AccessoryType.GLOVES, AccessoryGem.NONE, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).durability(100).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> IRON_GLOVES = ITEMS.register("iron_gloves",
			() -> new CurioItemProperty(AccessoryType.GLOVES, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(190).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> GOLDEN_GLOVES = ITEMS.register("golden_gloves",
			() -> new CurioItemProperty(AccessoryType.GLOVES, AccessoryGem.TOUGH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> DIAMOND_GLOVES = ITEMS.register("diamond_gloves",
			() -> new CurioItemProperty(AccessoryType.GLOVES, AccessoryGem.DIAMOND, AccessoryMaterial.DIAMOND, new Item.Properties().stacksTo(1).durability(240).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> NETHERITE_GLOVES = ITEMS.register("netherite_gloves",
			() -> new CurioItemProperty(AccessoryType.GLOVES, AccessoryGem.TANK, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> AMBER_TOTEM = ITEMS.register("amber_golden_totem",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> AMBER_WINGLET = ITEMS.register("amber_golden_winglet",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> AMBER_GAZER = ITEMS.register("amber_golden_gazer",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> EMERALD_TOTEM = ITEMS.register("emerald_golden_totem",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> EMERALD_WINGLET = ITEMS.register("emerald_golden_winglet",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> EMERALD_GAZER = ITEMS.register("emerald_golden_gazer",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> AMETHYST_TOTEM = ITEMS.register("amethyst_golden_totem",
			() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> AMETHYST_WINGLET = ITEMS.register("amethyst_golden_winglet",
			() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> AMETHYST_GAZER = ITEMS.register("amethyst_golden_gazer",
			() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUBY_TOTEM = ITEMS.register("ruby_golden_totem",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUBY_WINGLET = ITEMS.register("ruby_golden_winglet",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUBY_GAZER = ITEMS.register("ruby_golden_gazer",
		() -> new Item(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE = ITEMS.register("rune",
			() -> new CurioRune(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> RUNE_OF_VISION = ITEMS.register("rune_of_vision",
			() -> new CurioVision(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_WEALTH = ITEMS.register("rune_of_wealth",
			() -> new CurioWealth(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_CURSES = ITEMS.register("rune_of_curses",
			() -> new CurioCurses(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_STRENGTH = ITEMS.register("rune_of_strength",
			() -> new CurioStrength(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_ACCURACY = ITEMS.register("rune_of_accuracy",
			() -> new Item(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_DEEP = ITEMS.register("rune_of_deep",
			() -> new Item(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_PYRO = ITEMS.register("rune_of_pyro",
			() -> new CurioPyro(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_COLD = ITEMS.register("rune_of_cold",
			() -> new Item(new Item.Properties().stacksTo(16).durability(8).rarity(Rarity.EPIC)));

	public static final RegistryObject<Item> SAMURAI_LONG_BOW = ITEMS.register("samurai_long_bow",
			() -> new BowItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> NATURE_BOW = ITEMS.register("nature_bow",
			() -> new BowItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> AQUARIUS_BOW = ITEMS.register("aquarius_bow",
			() -> new BowItem(new Item.Properties().fireResistant().stacksTo(1)));
	public static final RegistryObject<Item> BOW_OF_DARKNESS = ITEMS.register("bow_of_darkness",
			() -> new BowItem(new Item.Properties().fireResistant().stacksTo(1)));
	public static final RegistryObject<Item> PHANTASM_BOW = ITEMS.register("phantasm_bow",
			() -> new BowItem(new Item.Properties().fireResistant().stacksTo(1)));

	/*
	public static final RegistryObject<ModSpawnEggItem> SWAMP_WANDERER_SPAWN_EGG = ITEMS.register("swamp_wanderer_spawn_egg",
		() -> new ModSpawnEggItem(ModEntityTypes.SWAMP_WANDERER, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties()));
	public static final RegistryObject<ModSpawnEggItem> GOBLIN_SPAWN_EGG = ITEMS.register("goblin_spawn_egg",
		() -> new ModSpawnEggItem(ModEntityTypes.GOBLIN, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties()));
	public static final RegistryObject<ModSpawnEggItem> MANNEQUIN_SPAWN_EGG = ITEMS.register("mannequin_spawn_egg",
		() -> new ModSpawnEggItem(ModEntityTypes.MANNEQUIN, 0x00FFFFFF, 0x00FFFFFF, new Item.Properties()));


	public static final RegistryObject<Item> ETERNITY = ITEMS.register("eternity", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
	*/

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}