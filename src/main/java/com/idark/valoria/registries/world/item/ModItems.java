package com.idark.valoria.registries.world.item;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.client.gui.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.registries.world.effect.ModEffects;
import com.idark.valoria.registries.world.entity.ModEntityTypes;
import com.idark.valoria.registries.world.entity.decoration.CustomBoatEntity;
import com.idark.valoria.registries.world.item.tiers.ModArmorItem;
import com.idark.valoria.registries.world.item.tiers.ModArmorMaterial;
import com.idark.valoria.registries.world.item.tiers.ModItemTier;
import com.idark.valoria.registries.world.item.types.*;
import com.idark.valoria.registries.world.item.types.curio.*;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryGem;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryMaterial;
import com.idark.valoria.registries.world.item.types.curio.enums.AccessoryType;
import com.idark.valoria.registries.world.item.types.curio.necklace.PickNecklace;
import com.idark.valoria.registries.world.item.types.food.ModDrinkItem;
import com.idark.valoria.registries.world.item.types.food.AloeBandageItem;
import com.idark.valoria.registries.world.item.types.mana.staffs.StaffItem;
import com.idark.valoria.registries.world.item.types.curio.charm.*;
import com.idark.valoria.util.ColorUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Valoria.MOD_ID);

	// Plants
	public static final RegistryObject<Item> VOIDVINE = ITEMS.register("voidvine", () -> new TaintTransformBlockItem(ModBlocks.VOIDVINE.get(), new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_SPROUT = ITEMS.register("violet_sprout", () -> new TaintTransformBlockItem(ModBlocks.VIOLET_SPROUT.get(), new Item.Properties()));
	public static final RegistryObject<Item> GLOW_VIOLET_SPROUT = ITEMS.register("glow_violet_sprout", () -> new TaintTransformBlockItem(ModBlocks.GLOW_VIOLET_SPROUT.get(), new Item.Properties()));
	public static final RegistryObject<Item> ABYSSAL_GLOWFERN = ITEMS.register("abyssal_glowfern", () -> new TaintTransformBlockItem(ModBlocks.ABYSSAL_GLOWFERN.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_BRANCH = ITEMS.register("shadewood_branch", () -> new BlockItem(ModBlocks.SHADEWOOD_BRANCH.get(), new Item.Properties()));
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
	public static final RegistryObject<Item> VOID_SERPENTS = ITEMS.register("void_serpents", () -> new BlockItem(ModBlocks.VOID_SERPENTS.get(), new Item.Properties()));

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
	public static final RegistryObject<Item> DORMANT_CRYSTALS = ITEMS.register("dormant_crystals", () -> new BlockItem(ModBlocks.DORMANT_CRYSTALS.get(), new Item.Properties()));

	// Raw
	public static final RegistryObject<Item> RAW_COBALT_ORE_BLOCK = ITEMS.register("raw_cobalt_ore", () -> new BlockItem(ModBlocks.RAW_COBALT_ORE_BLOCK.get(), new Item.Properties()));

	// Crystals
	public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new BlockItem(ModBlocks.VOID_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBER_CRYSTAL = ITEMS.register("amber_crystal", () -> new BlockItem(ModBlocks.AMBER_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST_CRYSTAL = ITEMS.register("amethyst_crystal", () -> new BlockItem(ModBlocks.AMETHYST_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUBY_CRYSTAL = ITEMS.register("ruby_crystal", () -> new BlockItem(ModBlocks.RUBY_CRYSTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_CRYSTAL = ITEMS.register("sapphire_crystal", () -> new BlockItem(ModBlocks.SAPPHIRE_CRYSTAL.get(), new Item.Properties()));

	// Ingot Blocks
	public static final RegistryObject<Item> AMETHYST_BLOCK = ITEMS.register("amethyst_block", () -> new BlockItem(ModBlocks.AMETHYST_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_BLOCK = ITEMS.register("sapphire_block", () -> new BlockItem(ModBlocks.SAPPHIRE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> AMBER_BLOCK = ITEMS.register("amber_block", () -> new BlockItem(ModBlocks.AMBER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUBY_BLOCK = ITEMS.register("ruby_block", () -> new BlockItem(ModBlocks.RUBY_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> COBALT_BLOCK = ITEMS.register("cobalt_block", () -> new BlockItem(ModBlocks.COBALT_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> NATURE_BLOCK = ITEMS.register("nature_block", () -> new BlockItem(ModBlocks.NATURE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_BLOCK = ITEMS.register("aquarius_block", () -> new BlockItem(ModBlocks.AQUARIUS_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> INFERNAL_BLOCK = ITEMS.register("infernal_block", () -> new BlockItem(ModBlocks.INFERNAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> AWAKENED_VOID_BLOCK = ITEMS.register("awakened_void_block", () -> new BlockItem(ModBlocks.AWAKENED_VOID_BLOCK.get(), new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> PEARLIUM = ITEMS.register("pearlium", () -> new BlockItem(ModBlocks.PEARLIUM.get(), new Item.Properties()));

	// Stones
	public static final RegistryObject<Item> EYE_MEAT = ITEMS.register("eye_meat", () -> new BlockItem(ModBlocks.EYE_MEAT.get(), new Item.Properties()));
	public static final RegistryObject<Item> EYE_STONE = ITEMS.register("eye_stone", () -> new BlockItem(ModBlocks.EYE_STONE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEP_MARBLE = ITEMS.register("deep_marble", () -> new BlockItem(ModBlocks.DEEP_MARBLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_DEEP_MARBLE = ITEMS.register("polished_deep_marble", () -> new BlockItem(ModBlocks.POLISHED_DEEP_MARBLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEP_MARBLE_WALL = ITEMS.register("deep_marble_wall", () -> new BlockItem(ModBlocks.DEEP_MARBLE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_DEEP_MARBLE_WALL = ITEMS.register("polished_deep_marble_wall", () -> new BlockItem(ModBlocks.POLISHED_DEEP_MARBLE_WALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> EPHEMARITE_LOW = ITEMS.register("ephemarite_low", () -> new BlockItem(ModBlocks.EPHEMARITE_LOW.get(), new Item.Properties()));
	public static final RegistryObject<Item> EPHEMARITE = ITEMS.register("ephemarite", () -> new BlockItem(ModBlocks.EPHEMARITE.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_EPHEMARITE_LOW = ITEMS.register("polished_ephemarite_low", () -> new BlockItem(ModBlocks.POLISHED_EPHEMARITE_LOW.get(), new Item.Properties()));
	public static final RegistryObject<Item> POLISHED_EPHEMARITE = ITEMS.register("polished_ephemarite", () -> new BlockItem(ModBlocks.POLISHED_EPHEMARITE.get(), new Item.Properties()));
	public static final RegistryObject<Item> MEAT_BLOCK = ITEMS.register("meat_block", () -> new BlockItem(ModBlocks.MEAT_BLOCK.get(), new Item.Properties()));
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
	public static final RegistryObject<Item> SUSPICIOUS_ICE = ITEMS.register("suspicious_ice", () -> new BlockItem(ModBlocks.SUSPICIOUS_ICE.get(), new Item.Properties()));
	public static final RegistryObject<Item> SUSPICIOUS_TOMBSTONE = ITEMS.register("suspicious_tombstone", () -> new BlockItem(ModBlocks.SUSPICIOUS_TOMBSTONE.get(), new Item.Properties()));
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
	public static final RegistryObject<Item> SARCOPHAGUS = ITEMS.register("sarcophagus", () -> new BlockItem(ModBlocks.SARCOPHAGUS.get(), new Item.Properties()));
	public static final RegistryObject<Item> SPIKES = ITEMS.register("spikes", () -> new BlockItem(ModBlocks.SPIKES.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_GRASS = ITEMS.register("void_grass", () -> new BlockItem(ModBlocks.VOID_GRASS.get(), new Item.Properties()));
	public static final RegistryObject<Item> VOID_TAINT = ITEMS.register("void_taint", () -> new BlockItem(ModBlocks.VOID_TAINT.get(), new Item.Properties()));
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
	public static final RegistryObject<Item> SHADEWOOD_HANGING_SIGN = ITEMS.register("shadewood_hanging_sign", () -> new HangingSignItem(ModBlocks.SHADEWOOD_HANGING_SIGN.get(), ModBlocks.SHADEWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> SHADEWOOD_LEAVES = ITEMS.register("shadewood_leaves", () -> new BlockItem(ModBlocks.SHADEWOOD_LEAVES.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_SAPLING = ITEMS.register("shadewood_sapling", () -> new BlockItem(ModBlocks.SHADEWOOD_SAPLING.get(), new Item.Properties()));
	public static final RegistryObject<Item> SHADEWOOD_BOAT_ITEM = ITEMS.register("shadewood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.SHADEWOOD, new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SHADEWOOD_CHEST_BOAT_ITEM = ITEMS.register("shadewood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.SHADEWOOD, new Item.Properties().stacksTo(1)));

	// BlockItem Misc
	public static final RegistryObject<Item> VOID_TAINT_LANTERN = ITEMS.register("void_taint_lantern", () -> new BlockItem(ModBlocks.VOID_TAINT_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item> ABYSSAL_LANTERN = ITEMS.register("abyssal_lantern", () -> new BlockItem(ModBlocks.ABYSSAL_LANTERN.get(), new Item.Properties()));
	public static final RegistryObject<Item> VALORIA_PORTAL = ITEMS.register("valoria_portal", () -> new BlockItem(ModBlocks.VALORIA_PORTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> STONE_CRUSHER = ITEMS.register("stone_crusher", () -> new BlockItem(ModBlocks.STONE_CRUSHER.get(), new Item.Properties()));
	public static final RegistryObject<Item> JEWELER_TABLE = ITEMS.register("jeweler_table", () -> new BlockItem(ModBlocks.JEWELER_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Item> KEG = ITEMS.register("keg", () -> new BlockItem(ModBlocks.KEG.get(), new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_KEYPAD = ITEMS.register("umbral_keypad", () -> new BlockItem(ModBlocks.UMBRAL_KEYPAD.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_UMBRAL_BLOCK = ITEMS.register("cut_umbral_block", () -> new BlockItem(ModBlocks.CUT_UMBRAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_BRICKS = ITEMS.register("umbral_bricks", () -> new BlockItem(ModBlocks.UMBRAL_BRICKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_BLOCK = ITEMS.register("umbral_block", () -> new BlockItem(ModBlocks.UMBRAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> TILE = ITEMS.register("quartz_blackstone_tile", () -> new BlockItem(ModBlocks.TILE.get(), new Item.Properties()));
	public static final RegistryObject<Item> QUICKSAND = ITEMS.register("quicksand", () -> new BlockItem(ModBlocks.QUICKSAND.get(), new Item.Properties()));
	public static final RegistryObject<Item> ELEMENTAL_MANIPULATOR = ITEMS.register("elemental_manipulator", () -> new BlockItem(ModBlocks.ELEMENTAL_MANIPULATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> ELEGANT_PEDESTAL = ITEMS.register("elegant_pedestal", () -> new BlockItem(ModBlocks.ELEGANT_PEDESTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> TOMB = ITEMS.register("tomb", () -> new BlockItem(ModBlocks.TOMB.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_SMALL = ITEMS.register("pot_small", () -> new BlockItem(ModBlocks.POT_SMALL.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_SMALL_HANDLESS = ITEMS.register("pot_small_handles", () -> new BlockItem(ModBlocks.POT_SMALL_HANDLES.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG = ITEMS.register("pot_long", () -> new BlockItem(ModBlocks.POT_LONG.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_HANDLES = ITEMS.register("pot_long_handles", () -> new BlockItem(ModBlocks.POT_LONG_HANDLES.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_MOSSY = ITEMS.register("pot_long_mossy", () -> new BlockItem(ModBlocks.POT_LONG_MOSSY.get(), new Item.Properties()));
	public static final RegistryObject<Item> POT_LONG_MOSSY_HANDLES = ITEMS.register("pot_long_mossy_handles", () -> new BlockItem(ModBlocks.POT_LONG_MOSSY_HANDLES.get(), new Item.Properties()));
	public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg", () -> new BlockItem(ModBlocks.SPIDER_EGG.get(), new Item.Properties()));

	// Bronze
	public static final RegistryObject<Item> BRONZE_BLOCK = ITEMS.register("bronze_block", () -> new BlockItem(ModBlocks.BRONZE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_VENT = ITEMS.register("bronze_vent", () -> new BlockItem(ModBlocks.BRONZE_VENT.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_GLASS = ITEMS.register("bronze_glass", () -> new BlockItem(ModBlocks.BRONZE_GLASS.get(), new Item.Properties()));
	public static final RegistryObject<Item> CUT_BRONZE = ITEMS.register("cut_bronze", () -> new BlockItem(ModBlocks.CUT_BRONZE.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_LAMP = ITEMS.register("bronze_lamp", () -> new BlockItem(ModBlocks.BRONZE_LAMP.get(), new Item.Properties()));
	public static final RegistryObject<Item> DECORATED_BRONZE_LAMP = ITEMS.register("decorated_bronze_lamp", () -> new BlockItem(ModBlocks.DECORATED_BRONZE_LAMP.get(), new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_LAMP_BLOCK = ITEMS.register("bronze_lamp_block", () -> new BlockItem(ModBlocks.BRONZE_LAMP_BLOCK.get(), new Item.Properties()));

	// ARMOR
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

	// ITEMS
	public static final RegistryObject<Item> STAFF = ITEMS.register("basic_staff", () -> new StaffItem(new Item.Properties()));
	public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMBER_GEM = ITEMS.register("amber_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST_GEM = ITEMS.register("amethyst_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RUBY_GEM = ITEMS.register("ruby_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SAPPHIRE_GEM = ITEMS.register("sapphire_gem", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> AMETHYST = ITEMS.register("wicked_amethyst", () -> new TransformShardItem(TransformType.WICKED, new Item.Properties()));
	public static final RegistryObject<Item> SOUL_SHARD = ITEMS.register("soul_shard", () -> new TransformShardItem(TransformType.SOUL, new Item.Properties()));
	public static final RegistryObject<Item> UNCHARGED_SHARD = ITEMS.register("uncharged_shard", () -> new Item(new Item.Properties()));
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
	public static final RegistryObject<Item> VOID_KEY = ITEMS.register("void_key", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GAIB_ROOT = ITEMS.register("gaib_root", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> KARUSAKAN_ROOT = ITEMS.register("karusakan_root", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> WOODEN_CUP = ITEMS.register("wooden_cup",() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CUP = ITEMS.register("cup", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> BOTTLE = ITEMS.register("bottle", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> ALOE_PIECE = ITEMS.register("aloe_piece", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> LEXICON = ITEMS.register("lexicon", () -> new LexiconItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CRYPT = ITEMS.register("page", () -> new LexiconPageItem(new Item.Properties().stacksTo(1), RegisterUnlockables.CRYPT, "gui.valoria.crypt.name"));

	public static final RegistryObject<Item> ALOE_BANDAGE = ITEMS.register("aloe_bandage", () -> new AloeBandageItem(1600, 0));
	public static final RegistryObject<Item> ALOE_BANDAGE_UPGRADED = ITEMS.register("aloe_bandage_upgraded", () -> new AloeBandageItem(1450, 1));
	public static final RegistryObject<Item> CACAO_CUP = ITEMS.register("cacao_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.CUP.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
	public static final RegistryObject<Item> COFFE_CUP = ITEMS.register("coffe_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.CUP.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
	public static final RegistryObject<Item> TEA_CUP = ITEMS.register("tea_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.CUP.get(), new MobEffectInstance(MobEffects.DIG_SPEED, 100)));
	public static final RegistryObject<Item> GREEN_TEA_CUP = ITEMS.register("green_tea_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.CUP.get(), new MobEffectInstance(ModEffects.ALOEREGEN.get(), 1800)));
	public static final RegistryObject<Item> BEER_CUP = ITEMS.register("beer_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.WOODEN_CUP.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 400, 0)));
	public static final RegistryObject<Item> RUM_CUP = ITEMS.register("rum_cup", () -> new ModDrinkItem(0, 1, 1, ModItems.WOODEN_CUP.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 400, 0), new MobEffectInstance(MobEffects.CONFUSION, 120, 0)));
	public static final RegistryObject<Item> KVASS_BOTTLE = ITEMS.register("kvass_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(MobEffects.SATURATION, 300)));
	public static final RegistryObject<Item> WINE_BOTTLE = ITEMS.register("wine_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 300)));
	public static final RegistryObject<Item> AKVAVIT_BOTTLE = ITEMS.register("akvavit_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 500, 1), new MobEffectInstance(MobEffects.CONFUSION, 320)));
	public static final RegistryObject<Item> LIQUOR_BOTTLE = ITEMS.register("liquor_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 350, 1), new MobEffectInstance(MobEffects.CONFUSION, 120)));
	public static final RegistryObject<Item> RUM_BOTTLE = ITEMS.register("rum_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 650, 1), new MobEffectInstance(MobEffects.CONFUSION, 250)));
	public static final RegistryObject<Item> MEAD_BOTTLE = ITEMS.register("mead_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 700, 0), new MobEffectInstance(MobEffects.CONFUSION, 240)));
	public static final RegistryObject<Item> COGNAC_BOTTLE = ITEMS.register("cognac_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 800, 1), new MobEffectInstance(MobEffects.CONFUSION, 350)));
	public static final RegistryObject<Item> WHISKEY_BOTTLE = ITEMS.register("whiskey_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(ModEffects.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 150)));
	public static final RegistryObject<Item> COKE_BOTTLE = ITEMS.register("coke_bottle", () -> new ModDrinkItem(0, 1, 1, ModItems.BOTTLE.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
	public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1.4f).build())));
	public static final RegistryObject<Item> HOLIDAY_CANDY = ITEMS.register("holiday_candy", () -> new Item(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
	public static final RegistryObject<Item> EYE_CHUNK = ITEMS.register("eye_chunk", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.POISON, 100), 0.4f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300), 1f).nutrition(1).saturationMod(0.2f).build())));
	public static final RegistryObject<Item> TAINTED_BERRIES = ITEMS.register("tainted_berries", () -> new ItemNameBlockItem(ModBlocks.TAINTED_ROOTS.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).build())));
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
	// Cores
	public static final RegistryObject<Item> NATURE_CORE = ITEMS.register("nature_core", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> AQUARIUS_CORE = ITEMS.register("aquarius_core", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_CORE = ITEMS.register("infernal_core", () -> new Item(new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> VOID_CORE = ITEMS.register("void_core", () -> new Item(new Item.Properties().fireResistant()));

	// TOOLS (category)
	public static final RegistryObject<Item> CLUB = ITEMS.register("club",
			() -> new ClubItem(Tiers.WOOD, 5, -3.2f, new Item.Properties()));
	public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
			() -> new SwordItem(Tiers.IRON, 6, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> VOID_EDGE = ITEMS.register("void_edge",
			() -> new SwordItem(Tiers.NETHERITE, 7, -3f, new Item.Properties()));
	public static final RegistryObject<Item> QUANTUM_REAPER = ITEMS.register("quantum_reaper",
			() -> new SwordItem(Tiers.NETHERITE, 8, -3f, new Item.Properties()));
	public static final RegistryObject<Item> BLOODHOUND = ITEMS.register("bloodhound",
			() -> new HoundItem(Tiers.NETHERITE, 6, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap",
			() -> new BlazeReapItem(Tiers.NETHERITE, 3, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> PHANTOM = ITEMS.register("phantom",
			() -> new PhantomItem(Tiers.NETHERITE, 6, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> MURASAMA = ITEMS.register("murasama",
			() -> new MurasamaItem(ModItemTier.SAMURAI, 5, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> SAMURAI_KUNAI = ITEMS.register("samurai_kunai",
			() -> new KunaiItem(new Item.Properties().durability(360)));
	public static final RegistryObject<Item> SAMURAI_POISONED_KUNAI = ITEMS.register("samurai_poisoned_kunai",
			() -> new PoisonedKunaiItem(new Item.Properties().durability(360)));
	public static final RegistryObject<Item> SPECTRAL_BLADE = ITEMS.register("spectral_blade",
			() -> new SpectralBladeItem(new Item.Properties().durability(852)));
	public static final RegistryObject<Item> CORPSECLEAVER = ITEMS.register("corpsecleaver",
			() -> new CorpsecleaverItem(Tiers.NETHERITE, 2, -2.4F, new Item.Properties().durability(1151)));

	// Placeholder for Entity Render
	public static final RegistryObject<Item> SPECTRAL_BLADE_THROWN = ITEMS.register("spectral_blade_thrown",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
			() -> new SpearItem(Tiers.WOOD, 3, -3f, new Item.Properties()));
	public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
			() -> new SpearItem(Tiers.STONE, 4, -3f, new Item.Properties()));
	public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
			() -> new SpearItem(Tiers.IRON, 5, -3f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
			() -> new SpearItem(Tiers.GOLD, 6, -2.9f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
			() -> new SpearItem(Tiers.DIAMOND, 3, -2.9f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
			() -> new SpearItem(Tiers.NETHERITE, 3, -2.9f, new Item.Properties()));
	public static final RegistryObject<Item> GLAIVE = ITEMS.register("glaive",
			() -> new SpearItem(Tiers.NETHERITE, 6, -3.2f, new Item.Properties()));
	public static final RegistryObject<Item> WOODEN_RAPIER = ITEMS.register("wooden_rapier",
			() -> new SwordItem(Tiers.WOOD, 0, -1.8f, new Item.Properties()));
	public static final RegistryObject<Item> STONE_RAPIER = ITEMS.register("stone_rapier",
			() -> new SwordItem(Tiers.STONE, 0, -1.8f, new Item.Properties()));
	public static final RegistryObject<Item> IRON_RAPIER = ITEMS.register("iron_rapier",
			() -> new SwordItem(Tiers.IRON, 0, -1.7f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_RAPIER = ITEMS.register("golden_rapier",
			() -> new SwordItem(Tiers.GOLD, 1, -1.5f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_RAPIER = ITEMS.register("diamond_rapier",
			() -> new SwordItem(Tiers.DIAMOND, 0, -1.5f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_RAPIER = ITEMS.register("netherite_rapier",
			() -> new SwordItem(Tiers.NETHERITE, 0, -1.5f, new Item.Properties()));
	public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
			() -> new ScytheItem(Tiers.IRON, 4, -3.2f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
			() -> new ScytheItem(Tiers.GOLD, 4, -3.1f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
			() -> new ScytheItem(Tiers.DIAMOND, 4, -3.0f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
			() -> new ScytheItem(Tiers.NETHERITE, 4, -3.0f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> BEAST = ITEMS.register("beast",
			() -> new BeastScytheItem(Tiers.DIAMOND, 8, -3.2f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_SCYTHE = ITEMS.register("aquarius_scythe",
			() -> new AquariusScytheItem(ModItemTier.AQUARIUS, 10, -3.0f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_SCYTHE = ITEMS.register("nature_scythe",
			() -> new ScytheItem(ModItemTier.NATURE, 9, -3.0f, new Item.Properties()));
	public static final RegistryObject<Item> INFERNAL_SCYTHE = ITEMS.register("infernal_scythe",
			() -> new InfernalScytheItem(ModItemTier.INFERNAL, 11, -3.0f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> HOLIDAY_KATANA = ITEMS.register("holiday_katana",
			() -> new KatanaItem(ModItemTier.HOLIDAY, 0, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana",
			() -> new KatanaItem(Tiers.IRON, 0, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_KATANA = ITEMS.register("golden_katana",
			() -> new KatanaItem(Tiers.GOLD, 2, -1.8f, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_KATANA = ITEMS.register("diamond_katana",
			() -> new KatanaItem(Tiers.DIAMOND, 1, -2f, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_KATANA = ITEMS.register("netherite_katana",
			() -> new KatanaItem(Tiers.NETHERITE, 2, -1.8f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> SAMURAI_KATANA = ITEMS.register("samurai_katana",
			() -> new KatanaItem(ModItemTier.SAMURAI, 3, -2f, new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_SWORD = ITEMS.register("pearlium_sword",
			() -> new SwordItem(ModItemTier.PEARLIUM, 5, -2.6f, new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_PICKAXE = ITEMS.register("pearlium_pickaxe",
			() -> new PickaxeItem(ModItemTier.PEARLIUM, -1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> PEARLIUM_AXE = ITEMS.register("pearlium_axe",
			() -> new AxeItem(ModItemTier.PEARLIUM, 6, -3f, new Item.Properties()));
	public static final RegistryObject<Item> HOLIDAY_PICKAXE = ITEMS.register("holiday_pickaxe",
			() -> new PickaxeItem(ModItemTier.HOLIDAY, -1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> HOLIDAY_AXE = ITEMS.register("holiday_axe",
			() -> new AxeItem(ModItemTier.HOLIDAY, 1, -3f, new Item.Properties()));
	public static final RegistryObject<Item> XMAS_SWORD = ITEMS.register("xmas_sword",
			() -> new SwordItem(ModItemTier.HOLIDAY, 3, -2.3f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
			() -> new SwordItem(ModItemTier.COBALT, 7, -2.2f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
			() -> new PickaxeItem(ModItemTier.COBALT, 2, -3f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
			() -> new ShovelItem(ModItemTier.COBALT, 2, -3f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
			() -> new AxeItem(ModItemTier.COBALT, 9, -3.4f, new Item.Properties()));
	public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
			() -> new HoeItem(ModItemTier.COBALT, 0, 0f, new Item.Properties()));
	public static final RegistryObject<Item> ENT = ITEMS.register("ent",
			() -> new SwordItem(ModItemTier.NATURE, 7, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_PICKAXE = ITEMS.register("nature_pickaxe",
			() -> new PickaxeItem(ModItemTier.NATURE, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_SHOVEL = ITEMS.register("nature_shovel",
			() -> new ShovelItem(ModItemTier.NATURE, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_AXE = ITEMS.register("nature_axe",
			() -> new AxeItem(ModItemTier.NATURE, 5, -3f, new Item.Properties()));
	public static final RegistryObject<Item> NATURE_HOE = ITEMS.register("nature_hoe",
			() -> new HoeItem(ModItemTier.NATURE, 0, 0f, new Item.Properties()));
	public static final RegistryObject<Item> CORAL_REEF = ITEMS.register("coral_reef",
			() -> new CoralReefItem(ModItemTier.AQUARIUS, 8, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_PICKAXE = ITEMS.register("aquarius_pickaxe",
			() -> new PickaxeItem(ModItemTier.AQUARIUS, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_SHOVEL = ITEMS.register("aquarius_shovel",
			() -> new ShovelItem(ModItemTier.AQUARIUS, 0, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_AXE = ITEMS.register("aquarius_axe",
			() -> new AxeItem(ModItemTier.AQUARIUS, 10, -3f, new Item.Properties()));
	public static final RegistryObject<Item> AQUARIUS_HOE = ITEMS.register("aquarius_hoe",
			() -> new HoeItem(ModItemTier.AQUARIUS, 0, 0f, new Item.Properties()));
	public static final RegistryObject<Item> INFERNAL_SWORD = ITEMS.register("infernal_sword",
			() -> new MagmaSwordItem(ModItemTier.INFERNAL, 9, -2.6f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_PICKAXE = ITEMS.register("infernal_pickaxe",
			() -> new PickaxeItem(ModItemTier.INFERNAL, 0, -2.8f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_SHOVEL = ITEMS.register("infernal_shovel",
			() -> new ShovelItem(ModItemTier.INFERNAL, 0, -2.9f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
			() -> new AxeItem(ModItemTier.INFERNAL, 10, -2.9f, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> INFERNAL_HOE = ITEMS.register("infernal_hoe",
			() -> new HoeItem(ModItemTier.INFERNAL, 0, 0f, new Item.Properties().fireResistant()));
	// ACCESSORIES (category)
	public static final RegistryObject<Item> PICK_NECKLACE = ITEMS.register("pick_necklace",
			() -> new PickNecklace(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> IRON_CHAIN = ITEMS.register("iron_chain",
			() -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
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
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_ARMOR = ITEMS.register("golden_necklace_armor",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> GOLDEN_NECKLACE_WEALTH = ITEMS.register("golden_necklace_wealth",
			() -> new CurioItemProperty(AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
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
			() -> new CurioItemProperty(AccessoryType.BELT, AccessoryGem.BELT, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
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
			() -> new CurioVision(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_WEALTH = ITEMS.register("rune_of_wealth",
			() -> new CurioWealth(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_CURSES = ITEMS.register("rune_of_curses",
			() -> new CurioCurses(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_STRENGTH = ITEMS.register("rune_of_strength",
			() -> new CurioStrength(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_ACCURACY = ITEMS.register("rune_of_accuracy",
			() -> new RuneAccuracy(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_DEEP = ITEMS.register("rune_of_deep",
			() -> new RuneDeep(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_PYRO = ITEMS.register("rune_of_pyro",
			() -> new CurioPyro(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> RUNE_OF_COLD = ITEMS.register("rune_of_cold",
			() -> new RuneCold(new Item.Properties().stacksTo(1).durability(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> BROKEN_BLOODSIGHT_MONOCLE = ITEMS.register("broken_bloodsight_monocle",
			() -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
	public static final RegistryObject<Item> BLOODSIGHT_MONOCLE = ITEMS.register("bloodsight_monocle",
			() -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
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
	public static final RegistryObject<Item> PICK = ITEMS.register("pick",
			() -> new PickItem(new Item.Properties().fireResistant().stacksTo(1).durability(64), 1, -2.8f, 5));

	public static final RegistryObject<ForgeSpawnEggItem> GOBLIN_SPAWN_EGG = ITEMS.register("goblin_spawn_egg",
		() -> new ForgeSpawnEggItem(ModEntityTypes.GOBLIN, ColorUtils.hexToDecimal("185b36"), ColorUtils.hexToDecimal("6BB447"), new Item.Properties()));
	public static final RegistryObject<ForgeSpawnEggItem> DRAUGR_SPAWN_EGG = ITEMS.register("draugr_spawn_egg",
		() -> new ForgeSpawnEggItem(ModEntityTypes.DRAUGR, ColorUtils.hexToDecimal("76695C"), ColorUtils.hexToDecimal("d6d0c9"), new Item.Properties()));
	public static final RegistryObject<ForgeSpawnEggItem> SWAMP_WANDERER_SPAWN_EGG = ITEMS.register("swamp_wanderer_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.SWAMP_WANDERER, ColorUtils.hexToDecimal("606239"), ColorUtils.hexToDecimal("b8b377"), new Item.Properties()));
	public static final RegistryObject<MannequinSpawnItem> MANNEQUIN_SPAWN_EGG = ITEMS.register("mannequin_spawn_egg",
		() -> new MannequinSpawnItem(new Item.Properties()));

	public static final RegistryObject<Item> ETERNITY = ITEMS.register("eternity", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}