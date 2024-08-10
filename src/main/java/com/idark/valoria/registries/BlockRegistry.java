package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.block.types.*;
import com.idark.valoria.registries.levelgen.tree.*;
import net.minecraft.sounds.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

public class BlockRegistry{
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, Valoria.ID);

    // Door & Trapdoors
    public static final RegistryObject<Block> ELDRITCH_DOOR = registerBlock("eldritch_door",
    () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_MAGENTA).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ELDRITCH_TRAPDOOR = registerBlock("eldritch_trapdoor",
    () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_MAGENTA).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADEWOOD_DOOR = registerBlock("shadewood_door",
    () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).mapColor(MapColor.COLOR_PURPLE).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADEWOOD_TRAPDOOR = registerBlock("shadewood_trapdoor",
    () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.COLOR_PURPLE).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> BRONZE_DOOR = registerBlock("bronze_door",
    () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).noOcclusion().mapColor(MapColor.COLOR_BROWN), BlockSetType.IRON));
    public static final RegistryObject<Block> BRONZE_TRAPDOOR = registerBlock("bronze_trapdoor",
    () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).noOcclusion().mapColor(MapColor.COLOR_BROWN), BlockSetType.IRON));
    public static final RegistryObject<Block> BRONZE_TRAPDOOR_GLASS = registerBlock("bronze_trapdoor_glass",
    () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).noOcclusion().mapColor(MapColor.COLOR_BROWN), BlockSetType.IRON));
    // Umbral
    public static final RegistryObject<Block> VALORIA_PORTAL_FRAME = registerBlock("valoria_portal_frame",
    () -> new ValoriaPortalFrame(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(42f, 3600000.8F).sound(SoundType.DEEPSLATE_TILES)));
    public static final RegistryObject<Block> UMBRAL_KEYPAD = registerBlock("umbral_keypad",
    () -> new UmbralKeyPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
    public static final RegistryObject<Block> CUT_UMBRAL_BLOCK = registerBlock("cut_umbral_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
    public static final RegistryObject<Block> UMBRAL_BRICKS = registerBlock("umbral_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
    public static final RegistryObject<Block> UMBRAL_BLOCK = registerBlock("umbral_block",
    () -> new UmbralBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
    public static final RegistryObject<Block> UMBRAL_ACTIVATOR = registerBlock("umbral_activator",
    () -> new UmbralActivatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).strength(-1f, 3600000.8F).sound(SoundType.NETHER_BRICKS).noLootTable()));
    // Metal
    public static final RegistryObject<Block> AMETHYST_BLOCK = registerBlock("amethyst_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> COBALT_BLOCK = registerBlock("cobalt_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> NATURE_BLOCK = registerBlock("nature_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> AQUARIUS_BLOCK = registerBlock("aquarius_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> INFERNAL_BLOCK = registerBlock("infernal_block",
    () -> new InfernalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> AWAKENED_VOID_BLOCK = registerBlock("awakened_void_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BRONZE_BLOCK_STAIRS = registerBlock("bronze_block_stairs",
    () -> new StairBlock(() -> BRONZE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BRONZE_BLOCK_SLAB = registerBlock("bronze_block_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
    public static final RegistryObject<Block> CUT_BRONZE = registerBlock("cut_bronze",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> CUT_BRONZE_STAIRS = registerBlock("cut_bronze_stairs",
    () -> new StairBlock(() -> CUT_BRONZE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(2f, 4f).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> CUT_BRONZE_SLAB = registerBlock("cut_bronze_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK).strength(6f, 4f)));
    public static final RegistryObject<Block> BRONZE_VENT = registerBlock("bronze_vent",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(3f, 4f).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> BRONZE_GLASS = registerBlock("bronze_glass",
    () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion().sound(SoundType.GLASS)));
    public static final RegistryObject<Block> BRONZE_GLASS_PANE = registerBlock("bronze_glass_pane",
    () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(1f, 4f).noOcclusion().sound(SoundType.GLASS)));
    // Raw
    public static final RegistryObject<Block> RAW_COBALT_ORE_BLOCK = registerBlock("raw_cobalt_ore",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK).mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(3f, 4f)));
    // Stone Types
    public static final RegistryObject<Block> EYE_MEAT = registerBlock("eye_meat",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 4f)));
    public static final RegistryObject<Block> EYE_STONE = registerBlock("eye_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(6f, 6f)));
    public static final RegistryObject<Block> DEEP_MARBLE = registerBlock("deep_marble",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEP_MARBLE_STAIRS = registerBlock("deep_marble_stairs",
    () -> new StairBlock(() -> DEEP_MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEP_MARBLE_SLAB = registerBlock("deep_marble_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEP_MARBLE_WALL = registerBlock("deep_marble_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_DEEP_MARBLE = registerBlock("polished_deep_marble",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_DEEP_MARBLE_STAIRS = registerBlock("polished_deep_marble_stairs",
    () -> new StairBlock(() -> POLISHED_DEEP_MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_DEEP_MARBLE_SLAB = registerBlock("polished_deep_marble_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_DEEP_MARBLE_WALL = registerBlock("polished_deep_marble_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PICRITE = registerBlock("picrite",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PICRITE_STAIRS = registerBlock("picrite_stairs",
    () -> new StairBlock(() -> PICRITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PICRITE_SLAB = registerBlock("picrite_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PICRITE_WALL = registerBlock("picrite_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PICRITE = registerBlock("polished_picrite",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PICRITE_STAIRS = registerBlock("polished_picrite_stairs",
    () -> new StairBlock(() -> POLISHED_PICRITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_STAIRS).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PICRITE_SLAB = registerBlock("polished_picrite_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_SLAB).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_PICRITE_WALL = registerBlock("polished_picrite_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICK_WALL).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE_LOW = BLOCK.register("ephemarite_low",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE_LOW_SLAB = registerBlock("ephemarite_low_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> EPHEMARITE_LOW_STAIRS = registerBlock("ephemarite_low_stairs",
    () -> new StairBlock(() -> EPHEMARITE_LOW.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE_LOW_WALL = registerBlock("ephemarite_low_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE = BLOCK.register("ephemarite",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE_SLAB = registerBlock("ephemarite_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> EPHEMARITE_STAIRS = registerBlock("ephemarite_stairs",
    () -> new StairBlock(() -> EPHEMARITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EPHEMARITE_WALL = registerBlock("ephemarite_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE_LOW = registerBlock("polished_ephemarite_low",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE_LOW_SLAB = registerBlock("polished_ephemarite_low_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE_LOW_STAIRS = registerBlock("polished_ephemarite_low_stairs",
    () -> new StairBlock(() -> POLISHED_EPHEMARITE_LOW.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE = registerBlock("polished_ephemarite",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE_SLAB = registerBlock("polished_ephemarite_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> POLISHED_EPHEMARITE_STAIRS = registerBlock("polished_ephemarite_stairs",
    () -> new StairBlock(() -> POLISHED_EPHEMARITE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MEAT_BLOCK = registerBlock("meat_block",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE = registerBlock("ambane_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_STAIRS = registerBlock("ambane_stone_stairs",
    () -> new StairBlock(() -> AMBANE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_SLAB = registerBlock("ambane_stone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_WALL = registerBlock("ambane_stone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_BRICKS = registerBlock("ambane_stone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_BRICKS_STAIRS = registerBlock("ambane_stone_bricks_stairs",
    () -> new StairBlock(() -> AMBANE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_BRICKS_SLAB = registerBlock("ambane_stone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBANE_STONE_BRICKS_WALL = registerBlock("ambane_stone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_AMBANE_STONE = registerBlock("polished_ambane_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_AMBANE_STONE_STAIRS = registerBlock("polished_ambane_stone_stairs",
    () -> new StairBlock(() -> POLISHED_AMBANE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_AMBANE_STONE_SLAB = registerBlock("polished_ambane_stone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_AMBANE_STONE = registerBlock("cut_ambane_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHISELED_AMBANE_STONE_BRICKS = registerBlock("chiseled_ambane_stone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.WARPED_WART_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE = registerBlock("dunestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_STAIRS = registerBlock("dunestone_stairs",
    () -> new StairBlock(() -> DUNESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_SLAB = registerBlock("dunestone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_WALL = registerBlock("dunestone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_BRICKS = registerBlock("dunestone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_BRICKS_STAIRS = registerBlock("dunestone_bricks_stairs",
    () -> new StairBlock(() -> DUNESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_BRICKS_SLAB = registerBlock("dunestone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DUNESTONE_BRICKS_WALL = registerBlock("dunestone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_DUNESTONE = registerBlock("cut_dunestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_DUNESTONE = registerBlock("polished_dunestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.SAND).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE = registerBlock("limestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_STAIRS = registerBlock("limestone_stairs",
    () -> new StairBlock(() -> LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_SLAB = registerBlock("limestone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_WALL = registerBlock("limestone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_LIMESTONE = registerBlock("cut_limestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_LIMESTONE_STAIRS = registerBlock("cut_limestone_stairs",
    () -> new StairBlock(() -> CUT_LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_LIMESTONE_SLAB = registerBlock("cut_limestone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_BRICKS = registerBlock("limestone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS = registerBlock("limestone_bricks_stairs",
    () -> new StairBlock(() -> LIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB = registerBlock("limestone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> LIMESTONE_BRICKS_WALL = registerBlock("limestone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS = registerBlock("cracked_limestone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_STAIRS = registerBlock("cracked_limestone_bricks_stairs",
    () -> new StairBlock(() -> CRACKED_LIMESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_SLAB = registerBlock("cracked_limestone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS = registerBlock("cracked_tombstone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_TOMBSTONE_BRICKS_WALL = registerBlock("cracked_tombstone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_WALL = registerBlock("cracked_limestone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE = registerBlock("crystal_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_SLAB = registerBlock("crystal_stone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRYSTAL_STONE_STAIRS = registerBlock("crystal_stone_stairs",
    () -> new StairBlock(() -> CRYSTAL_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_WALL = registerBlock("crystal_stone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_PILLAR = registerBlock("crystal_stone_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_CRYSTAL_STONE = registerBlock("cut_crystal_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CUT_POLISHED_CRYSTAL_STONE = registerBlock("cut_polished_crystal_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS = registerBlock("crystal_stone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_SLAB = registerBlock("crystal_stone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_STAIRS = registerBlock("crystal_stone_bricks_stairs",
    () -> new StairBlock(() -> CRYSTAL_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_WALL = registerBlock("crystal_stone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHARGED_VOID_PILLAR = registerBlock("charged_void_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS).lightLevel(setLightValue(4))));
    public static final RegistryObject<Block> POLISHED_CRYSTAL_STONE = registerBlock("polished_crystal_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_LIMESTONE = registerBlock("polished_limestone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs",
    () -> new StairBlock(() -> POLISHED_LIMESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PEARLIUM = registerBlock("pearlium",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> VOID_STONE = registerBlock("void_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_STONE_STAIRS = registerBlock("void_stone_stairs",
    () -> new StairBlock(() -> VOID_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).sound(SoundsRegistry.VOID_STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VOID_STONE_SLAB = registerBlock("void_stone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_STONE_WALL = registerBlock("void_stone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_PILLAR_AMETHYST = registerBlock("void_pillar_amethyst",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE).lightLevel(setLightValue(4))));
    public static final RegistryObject<Block> VOID_PILLAR = registerBlock("void_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_BRICK = registerBlock("void_brick",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_BRICK_STAIRS = registerBlock("void_brick_stairs",
    () -> new StairBlock(() -> VOID_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops().sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_BRICK_SLAB = registerBlock("void_brick_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_BRICK_WALL = registerBlock("void_brick_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_CRACKED_BRICK = registerBlock("void_cracked_brick",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_CRACKED_BRICK_STAIRS = registerBlock("void_cracked_brick_stairs",
    () -> new StairBlock(() -> VOID_CRACKED_BRICK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops().sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_CRACKED_BRICK_SLAB = registerBlock("void_cracked_brick_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> VOID_CRACKED_BRICK_WALL = registerBlock("void_cracked_brick_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> POLISHED_VOID_STONE = registerBlock("polished_void_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundsRegistry.VOID_STONE)));
    public static final RegistryObject<Block> POLISHED_TOMBSTONE = registerBlock("polished_tombstone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(3f, 6f)));
    public static final RegistryObject<Block> VOID_CHISELED_BRICK = registerBlock("void_chiseled_brick",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS = registerBlock("void_chiseled_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS_STAIRS = registerBlock("void_chiseled_bricks_stairs",
    () -> new StairBlock(() -> VOID_CHISELED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).strength(3f, 6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS_SLAB = registerBlock("void_chiseled_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 6f).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> VOID_GRASS = registerBlock("void_grass",
    () -> new VoidGrassBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundsRegistry.VOID_GRASS)));
    public static final RegistryObject<Block> VOID_TAINT = registerBlock("void_taint",
    () -> new VoidTaintBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundsRegistry.VOID_GRASS)));
    public static final RegistryObject<Block> VOID_TAINT_LANTERN = registerBlock("void_taint_lantern",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.FROGLIGHT).lightLevel((p_152688_) -> 9)));
    public static final RegistryObject<Block> ABYSSAL_LANTERN = registerBlock("abyssal_lantern",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2f, 4f).sound(SoundType.FROGLIGHT).lightLevel((p_152688_) -> 15)));
    public static final RegistryObject<Block> TOMBSTONE = registerBlock("tombstone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<CrushableBlock> SUSPICIOUS_ICE = registerBlock("suspicious_ice",
    () -> new CrushableBlock(true, Blocks.ICE, BlockBehaviour.Properties.copy(Blocks.ICE).friction(0.98F).noOcclusion().strength(0.5F).mapColor(MapColor.ICE).instrument(NoteBlockInstrument.SNARE).sound(SoundsRegistry.SUSPICIOUS_TOMBSTONE).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL));
    public static final RegistryObject<CrushableBlock> SUSPICIOUS_TOMBSTONE = registerBlock("suspicious_tombstone",
    () -> new CrushableBlock(false, TOMBSTONE.get(), BlockBehaviour.Properties.of().mapColor(MapColor.STONE).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.SNARE).strength(0.85F).sound(SoundsRegistry.SUSPICIOUS_TOMBSTONE).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_GRAVEL));
    public static final RegistryObject<Block> SPIKES = registerBlock("spikes",
    () -> new SpikeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_GRAY).strength(-1f, 3600000.8F).noLootTable().dynamicShape().noOcclusion()));
    public static final RegistryObject<Block> CUT_TOMBSTONE = registerBlock("cut_tombstone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3f, 4f)));
    public static final RegistryObject<Block> TOMBSTONE_FIRECHARGE_TRAP = registerBlock("tombstone_firecharge_trap",
    () -> new FireTrapBlock(BlockRegistry.POLISHED_TOMBSTONE.get().defaultBlockState(), 6.0F, 8, new int[]{255, 145, 45, 45, 0, 0}, BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_SPIKES_TRAP = registerBlock("tombstone_spikes_trap",
    () -> new SpikeTrapBlock(BlockRegistry.POLISHED_TOMBSTONE.get().defaultBlockState(), BlockRegistry.SPIKES.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(3f, 4f)));
    public static final RegistryObject<Block> VOID_FIRECHARGE_TRAP = registerBlock("void_firecharge_trap",
    () -> new FireTrapBlock(BlockRegistry.POLISHED_VOID_STONE.get().defaultBlockState(), 12.0F, 14, new int[]{185, 65, 145, 45, 25, 5}, BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops(), new MobEffectInstance(MobEffects.WITHER, 160, 0, false, true)));
    public static final RegistryObject<Block> VOID_SPIKES_TRAP = registerBlock("void_spikes_trap",
    () -> new SpikeTrapBlock(BlockRegistry.POLISHED_VOID_STONE.get().defaultBlockState(), BlockRegistry.SPIKES.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(3f, 4f)));
    public static final RegistryObject<Block> CUT_TOMBSTONE_PILLAR = registerBlock("cut_tombstone_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> WICKED_TOMBSTONE_PILLAR = registerBlock("wicked_tombstone_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_PILLAR = registerBlock("tombstone_pillar",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_SLAB = registerBlock("tombstone_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> TOMBSTONE_STAIRS = registerBlock("tombstone_stairs",
    () -> new StairBlock(() -> TOMBSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_WALL = registerBlock("tombstone_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_BRICKS = registerBlock("tombstone_bricks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_BRICKS_SLAB = registerBlock("tombstone_bricks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> TOMBSTONE_BRICKS_STAIRS = registerBlock("tombstone_bricks_stairs",
    () -> new StairBlock(() -> TOMBSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TOMBSTONE_BRICKS_WALL = registerBlock("tombstone_bricks_wall",
    () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops()));
    // Wood
    public static final RegistryObject<Block> SHADEWOOD_PRESSURE_PLATE = registerBlock("shadewood_pressure_plate",
    () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> SHADEWOOD_BUTTON = registerBlock("shadewood_button",
    () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> SHADELOG = registerBlock("shadelog",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> STRIPPED_SHADELOG = registerBlock("stripped_shadelog",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD = registerBlock("shadewood",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> STRIPPED_SHADEWOOD = registerBlock("stripped_shadewood",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD_PLANKS = registerBlock("shadewood_planks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD_PLANKS_SLAB = registerBlock("shadewood_planks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD_PLANKS_STAIRS = registerBlock("shadewood_planks_stairs",
    () -> new StairBlock(() -> SHADEWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD_LEAVES = registerBlock("shadewood_leaves",
    () -> new ShadeLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> SHADEWOOD_BRANCH = registerBlock("shadewood_branch",
    () -> new ShadeBranchBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SHADEWOOD_SAPLING = registerBlock("shadewood_sapling",
    () -> new ValoriaSaplingBlock(new ShadeWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_CYAN)));
    public static final RegistryObject<Block> POTTED_SHADEWOOD_SAPLING = BLOCK.register("potted_shadewood_sapling",
    () -> new FlowerPotBlock(SHADEWOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SHADEWOOD_FENCE = registerBlock("shadewood_fence",
    () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> SHADEWOOD_FENCE_GATE = registerBlock("shadewood_fence_gate",
    () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_PURPLE), ModWoodTypes.SHADEWOOD));

    public static final RegistryObject<Block> ELDRITCH_PRESSURE_PLATE = registerBlock("eldritch_pressure_plate",
    () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> ELDRITCH_BUTTON = registerBlock("eldritch_button",
    () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.WOOD).noCollission(), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ELDRITCH_LOG = registerBlock("eldritch_log",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> STRIPPED_ELDRITCH_LOG = registerBlock("stripped_eldritch_log",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_WOOD = registerBlock("eldritch_wood",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> STRIPPED_ELDRITCH_WOOD = registerBlock("stripped_eldritch_wood",
    () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_PLANKS = registerBlock("eldritch_planks",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_PLANKS_SLAB = registerBlock("eldritch_planks_slab",
    () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_PLANKS_STAIRS = registerBlock("eldritch_planks_stairs",
    () -> new StairBlock(() -> ELDRITCH_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> ELDRITCH_LEAVES = registerBlock("eldritch_leaves",
    () -> new ShadeLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_SAPLING = registerBlock("eldritch_sapling",
    () -> new ValoriaSaplingBlock(new EldritchTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_ELDRITCH_SAPLING = BLOCK.register("potted_eldritch_sapling",
    () -> new FlowerPotBlock(ELDRITCH_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).mapColor(MapColor.COLOR_MAGENTA).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ELDRITCH_FENCE = registerBlock("eldritch_fence",
    () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ELDRITCH_FENCE_GATE = registerBlock("eldritch_fence_gate",
    () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).mapColor(MapColor.COLOR_MAGENTA), ModWoodTypes.ELDRITCH));
    // Signs
    public static final RegistryObject<Block> SHADEWOOD_SIGN = BLOCK.register("shadewood_sign",
    () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
    public static final RegistryObject<Block> SHADEWOOD_WALL_SIGN = BLOCK.register("shadewood_wall_sign",
    () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
    public static final RegistryObject<Block> SHADEWOOD_HANGING_SIGN = BLOCK.register("shadewood_hanging_sign",
    () -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));
    public static final RegistryObject<Block> SHADEWOOD_WALL_HANGING_SIGN = BLOCK.register("shadewood_wall_hanging_sign",
    () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission(), ModWoodTypes.SHADEWOOD));

    public static final RegistryObject<Block> ELDRITCH_SIGN = BLOCK.register("eldritch_sign",
    () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
    public static final RegistryObject<Block> ELDRITCH_WALL_SIGN = BLOCK.register("eldritch_wall_sign",
    () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
    public static final RegistryObject<Block> ELDRITCH_HANGING_SIGN = BLOCK.register("eldritch_hanging_sign",
    () -> new ModCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
    public static final RegistryObject<Block> ELDRITCH_WALL_HANGING_SIGN = BLOCK.register("eldritch_wall_hanging_sign",
    () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollission(), ModWoodTypes.ELDRITCH));
    // Other
    public static final RegistryObject<Block> VALORIA_PORTAL = registerBlock("valoria_portal",
    () -> new ValoriaPortalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> GEODITE_DIRT = registerBlock("geodite_dirt",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.5f, 2f).sound(SoundType.ROOTED_DIRT)));
    public static final RegistryObject<Block> GEODITE_STONE = registerBlock("geodite_stone",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(2f, 4f)));
    public static final RegistryObject<Block> STONE_CRUSHER = registerBlock("stone_crusher",
    () -> new CrusherBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1f, 2f)));
    public static final RegistryObject<Block> JEWELER_TABLE = registerBlock("jeweler_table",
    () -> new JewelerBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).requiresCorrectToolForDrops().strength(1f, 1f)));
    public static final RegistryObject<Block> TOMB = registerBlock("tomb",
    () -> new TombBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1f, 1f).noOcclusion()));
    public static final RegistryObject<Block> KEG = registerBlock("keg",
    () -> new KegBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).requiresCorrectToolForDrops().strength(1f, 1f).noOcclusion()));
    public static final RegistryObject<Block> SARCOPHAGUS = registerBlock("sarcophagus",
    () -> new SarcophagusBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f).noOcclusion()));
    public static final RegistryObject<Block> ARCHAEOLOGY_TABLE = registerBlock("archaeology_table",
    () -> new ArchaeologyTableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f).noOcclusion()));
    public static final RegistryObject<Block> TILE = registerBlock("quartz_blackstone_tile",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3f, 1f)));
    public static final RegistryObject<Block> QUICKSAND = registerBlock("quicksand",
    () -> new QuickSandBlock(BlockBehaviour.Properties.copy(Blocks.SAND).dynamicShape().requiresCorrectToolForDrops().strength(0.5f, 0.5f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> ELEMENTAL_MANIPULATOR = registerBlock("elemental_manipulator",
    () -> new ManipulatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3f, 1f).lightLevel(s -> 4).noOcclusion()));
    public static final RegistryObject<Block> ELEGANT_PEDESTAL = registerBlock("elegant_pedestal",
    () -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_RED).requiresCorrectToolForDrops().strength(3f, 1f).noOcclusion()));
    public static final RegistryObject<Block> BRONZE_LAMP = registerBlock("bronze_lamp",
    () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit())));
    public static final RegistryObject<Block> DECORATED_BRONZE_LAMP = registerBlock("decorated_bronze_lamp",
    () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit())));
    public static final RegistryObject<Block> BRONZE_LAMP_BLOCK = registerBlock("bronze_lamp_block",
    () -> new RedstoneLampBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.COLOR_BROWN).strength(3f, 4f).noOcclusion().sound(SoundType.GLASS).lightLevel(getLightValueLit())));
    public static final RegistryObject<Block> SPIDER_EGG = registerBlock("spider_egg",
    () -> new SpiderBlock(BlockBehaviour.Properties.copy(Blocks.STONE).instabreak().noOcclusion().sound(SoundType.FROGSPAWN)));
    // Ore
    public static final RegistryObject<Block> PYRATITE_ORE = registerBlock("pyratite_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(DEEP_MARBLE.get()).requiresCorrectToolForDrops().strength(10f, 12f), UniformInt.of(2, 4)));
    public static final RegistryObject<Block> AMBER_ORE = registerBlock("amber_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> COBALT_ORE = registerBlock("cobalt_ore",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_AMBER_ORE = registerBlock("deepslate_amber_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
    () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(0, 2)));
    public static final RegistryObject<Block> DEEPSLATE_COBALT_ORE = registerBlock("deepslate_cobalt_ore",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> WICKED_AMETHYST_ORE = registerBlock("wicked_amethyst_ore",
    () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 1)));
    public static final RegistryObject<Block> DORMANT_CRYSTALS = registerBlock("dormant_crystals",
    () -> new WickedOreBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(8f, 12f).sound(SoundType.NETHER_BRICKS), UniformInt.of(0, 3)));
    public static final RegistryObject<Block> PEARLIUM_ORE = registerBlock("pearlium_ore",
    () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    // Crystals
    public static final RegistryObject<Block> VOID_CRYSTAL = registerBlock("void_crystal",
    () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> AMBER_CRYSTAL = registerBlock("amber_crystal",
    () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> AMETHYST_CRYSTAL = registerBlock("amethyst_crystal",
    () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> RUBY_CRYSTAL = registerBlock("ruby_crystal",
    () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> SAPPHIRE_CRYSTAL = registerBlock("sapphire_crystal",
    () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).strength(1f, 0f).sound(SoundType.GLASS).noOcclusion()));
    // Pots
    public static final RegistryObject<Block> POT_SMALL = registerBlock("pot_small",
    () -> new PotBlock(false, BlockBehaviour.Properties.copy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    public static final RegistryObject<Block> POT_SMALL_HANDLES = registerBlock("pot_small_handles",
    () -> new PotBlock(false, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(BlockRegistry.POT_SMALL).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    public static final RegistryObject<Block> POT_LONG = registerBlock("pot_long",
    () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    public static final RegistryObject<Block> POT_LONG_HANDLES = registerBlock("pot_long_handles",
    () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(BlockRegistry.POT_LONG).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    public static final RegistryObject<Block> POT_LONG_MOSSY = registerBlock("pot_long_mossy",
    () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(BlockRegistry.POT_LONG).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    public static final RegistryObject<Block> POT_LONG_MOSSY_HANDLES = registerBlock("pot_long_mossy_handles",
    () -> new PotBlock(true, BlockBehaviour.Properties.copy(Blocks.GLASS).lootFrom(BlockRegistry.POT_LONG).instabreak().noOcclusion().sound(SoundsRegistry.POT)));
    // Plants`
    public static final RegistryObject<Block> TAINTED_ROOTS = BLOCK.register("tainted_roots",
    () -> new TaintedRootsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).mapColor(MapColor.COLOR_MAGENTA).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> VIOLET_SPROUT_PLANT = BLOCK.register("violet_sprout_plant",
    () -> new VioletSproutPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA), false));
    public static final RegistryObject<Block> VIOLET_SPROUT = BLOCK.register("violet_sprout",
    () -> new VioletSproutBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_MAGENTA), false));
    public static final RegistryObject<Block> GLOW_VIOLET_SPROUT_PLANT = BLOCK.register("glow_violet_sprout_plant",
    () -> new VioletSproutPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA).lightLevel(getPlantLightValue()), true));
    public static final RegistryObject<Block> GLOW_VIOLET_SPROUT = BLOCK.register("glow_violet_sprout",
    () -> new VioletSproutBlock(BlockBehaviour.Properties.copy(Blocks.KELP).lightLevel(getPlantLightValue()), true));
    public static final RegistryObject<Block> ABYSSAL_GLOWFERN_PLANT = BLOCK.register("abyssal_glowfern_plant",
    () -> new AbyssalGlowFernPlantBlock(BlockBehaviour.Properties.copy(Blocks.KELP_PLANT).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> ABYSSAL_GLOWFERN = BLOCK.register("abyssal_glowfern",
    () -> new AbyssalGlowFernBlock(BlockBehaviour.Properties.copy(Blocks.KELP).mapColor(MapColor.COLOR_MAGENTA).lightLevel(getPlantLightValue())));
    public static final RegistryObject<Block> ALOE_SMALL = registerBlock("aloe_small",
    () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> POTTED_ALOE_SMALL = BLOCK.register("potted_aloe_small",
    () -> new FlowerPotBlock(ALOE_SMALL.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> DRIED_PLANT = registerBlock("dried_plant",
    () -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> POTTED_DRIED_PLANT = BLOCK.register("potted_dried_plant",
    () -> new FlowerPotBlock(DRIED_PLANT.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> DRIED_ROOTS = registerBlock("dried_roots",
    () -> new DriedBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> POTTED_DRIED_ROOTS = BLOCK.register("potted_dried_roots",
    () -> new FlowerPotBlock(DRIED_ROOTS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ALOE = registerBlock("aloe",
    () -> new TallSandFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> CATTAIL = registerBlock("cattail",
    () -> new TallWaterFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> SOULROOT = registerBlock("soulroot",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_SOULROOT = BLOCK.register("potted_soulroot",
    () -> new FlowerPotBlock(SOULROOT.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> CRIMSON_SOULROOT = registerBlock("crimson_soulroot",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_CRIMSON_SOULROOT = BLOCK.register("potted_crimson_soulroot",
    () -> new FlowerPotBlock(CRIMSON_SOULROOT.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> DOUBLE_SOULROOT = registerBlock("double_crimson_soulroot",
    () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> MAGMAROOT = registerBlock("crimson_magmaroot",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_MAGMAROOT = BLOCK.register("potted_crimson_magmaroot",
    () -> new FlowerPotBlock(MAGMAROOT.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> DOUBLE_MAGMAROOT = registerBlock("double_crimson_magmaroot",
    () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> GOLDY = registerBlock("crimson_goldy",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_GOLDY = BLOCK.register("potted_crimson_goldy",
    () -> new FlowerPotBlock(GOLDY.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> DOUBLE_GOLDY = registerBlock("double_crimson_goldy",
    () -> new TallNetherFlowerBlock(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> BLOODROOT = registerBlock("bloodroot",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_BLOODROOT = BLOCK.register("potted_bloodroot",
    () -> new FlowerPotBlock(BLOODROOT.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> RAJUSH = registerBlock("crimson_rajush",
    () -> new RootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS)));
    public static final RegistryObject<Block> POTTED_RAJUSH = BLOCK.register("potted_crimson_rajush",
    () -> new FlowerPotBlock(RAJUSH.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> FALSEFLOWER = registerBlock("falseflower",
    () -> new VoidFlowerBlock(() -> MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_FALSEFLOWER = BLOCK.register("potted_falseflower",
    () -> new FlowerPotBlock(FALSEFLOWER.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> FALSEFLOWER_SMALL = registerBlock("falseflower_small",
    () -> new VoidFlowerBlock(() -> MobEffects.POISON, 2, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_FALSEFLOWER_SMALL = BLOCK.register("potted_falseflower_small",
    () -> new FlowerPotBlock(FALSEFLOWER_SMALL.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SOULFLOWER = registerBlock("soulflower",
    () -> new VoidFlowerBlock(() -> MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_SOULFLOWER = BLOCK.register("potted_soulflower",
    () -> new FlowerPotBlock(SOULFLOWER.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> VOID_ROOTS = registerBlock("void_roots",
    () -> new VoidRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_VOID_ROOTS = BLOCK.register("potted_void_roots",
    () -> new FlowerPotBlock(VOID_ROOTS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> VOID_SERPENTS = registerBlock("void_serpents",
    () -> new VoidRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_VOID_SERPENTS = BLOCK.register("potted_void_serpents",
    () -> new FlowerPotBlock(VOID_SERPENTS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> VOIDVINE = BLOCK.register("voidvine",
    () -> new VoidvineBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_VOIDVINE = BLOCK.register("potted_voidvine",
    () -> new FlowerPotBlock(VOIDVINE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()));
    public static final RegistryObject<Block> GAIB_ROOTS = registerBlock("gaib_roots",
    () -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> KARUSAKAN_ROOTS = registerBlock("karusakan_roots",
    () -> new TallRootsBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> SHADE_BLOSSOM = registerBlock("shade_blossom",
    () -> new ShadeBlossomBlock(BlockBehaviour.Properties.copy(Blocks.SPORE_BLOSSOM).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCK.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCK.register(eventBus);
    }

    private static ToIntFunction<BlockState> getLightValueLit(){
        return (state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0;
    }

    private static ToIntFunction<BlockState> setLightValue(int pValue){
        return (state) -> !state.isAir() ? pValue : 0;
    }

    private static ToIntFunction<BlockState> getPlantLightValue(){
        return (state) -> !state.isAir() ? 9 : 0;
    }
}