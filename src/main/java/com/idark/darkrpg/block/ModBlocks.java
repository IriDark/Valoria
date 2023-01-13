package com.idark.darkrpg.block;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.item.ModItemGroup;
import com.idark.darkrpg.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;

import java.util.function.ToIntFunction;
import java.util.function.Supplier;

    public class ModBlocks {
    private final static String MODID = DarkRPG.MOD_ID;
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    //door & trapdoors
    public static final RegistryObject<Block> BRONZE_DOOR = registerBlock("bronze_door",
    () -> new DoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool()
    .harvestTool(ToolType.PICKAXE).hardnessAndResistance(4f).notSolid()));
    public static final RegistryObject<Block> BRONZE_TRAPDOOR = registerBlock("bronze_trapdoor",
    () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool()
    .harvestTool(ToolType.PICKAXE).hardnessAndResistance(2f).notSolid()));
    public static final RegistryObject<Block> BRONZE_TRAPDOOR2 = registerBlock("bronze_trapdoor_glass",
    () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).setRequiresTool()
    .harvestTool(ToolType.PICKAXE).hardnessAndResistance(3f).notSolid()));
    //TODO. ADD OTHER INGOT BLOCKS (OCEAN, INFERNAL)
    //ingot_blocks
    public static final RegistryObject<Block> COBALT_BLOCK = BLOCK.register("cobalt_block",
    () -> new Block(Properties.create(Material.IRON).harvestLevel(2)
    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
    public static final RegistryObject<Block> NATURE_BLOCK = BLOCK.register("nature_block",
    () -> new Block(Properties.create(Material.IRON).harvestLevel(2)
    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
    public static final RegistryObject<Block> AWAKENED_VOID_BLOCK = BLOCK.register("awakened_void_block",
    () -> new Block(Properties.create(Material.IRON).harvestLevel(2)
    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
    //bronze
    public static final RegistryObject<Block> BRONZE_BLOCK = BLOCK.register("bronze_block",
    () -> new Block(Properties.create(Material.IRON).harvestLevel(2)
    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
    public static final RegistryObject<Block> BRONZE_BLOCK_STAIRS = registerBlock("bronze_block_stairs",
    () -> new StairsBlock(() -> BRONZE_BLOCK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
    public static final RegistryObject<Block> BRONZE_BLOCK_SLAB = registerBlock("bronze_block_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> BRONZE_PLATE = BLOCK.register("bronze_plate",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_PLATE_STAIRS = registerBlock("bronze_plate_stairs",
    () -> new StairsBlock(() -> BRONZE_PLATE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_PLATE_SLAB = registerBlock("bronze_plate_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE = BLOCK.register("cut_bronze",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE_STAIRS = registerBlock("cut_bronze_stairs",
    () -> new StairsBlock(() -> CUT_BRONZE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool().sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> CUT_BRONZE_SLAB = registerBlock("cut_bronze_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.IRON)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> BRONZE_VENT = BLOCK.register("bronze_vent",
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).sound(SoundType.NETHERITE)));
	public static final RegistryObject<Block> BRONZE_GLASS = BLOCK.register("bronze_glass",
	() -> new GlassBlock(Properties.create(Material.GLASS).harvestLevel(1)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1f).notSolid().sound(SoundType.GLASS)));
	//ambane
	public static final RegistryObject<Block> AMBANE_STONE = BLOCK.register("ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> AMBANE_STONE_STAIRS = registerBlock("ambane_stone_stairs",
    () -> new StairsBlock(() -> AMBANE_STONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> AMBANE_STONE_SLAB = registerBlock("ambane_stone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//ambane_bricks
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS = BLOCK.register("ambane_stone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_STAIRS = registerBlock("ambane_stone_bricks_stairs",
    () -> new StairsBlock(() -> AMBANE_STONE_BRICKS.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> AMBANE_STONE_BRICKS_SLAB = registerBlock("ambane_stone_bricks_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//ambane_polished
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE = BLOCK.register("polished_ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_STAIRS = registerBlock("polished_ambane_stone_stairs",
    () -> new StairsBlock(() -> POLISHED_AMBANE_STONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> POLISHED_AMBANE_STONE_SLAB = registerBlock("polished_ambane_stone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//ambane without stairs/slabs
	public static final RegistryObject<Block> CUT_AMBANE_STONE = BLOCK.register("cut_ambane_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CHISELED_AMBANE_STONE_BRICKS = BLOCK.register("chiseled_ambane_stone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	//dunestone
	public static final RegistryObject<Block> DUNESTONE = BLOCK.register("dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> DUNESTONE_STAIRS = registerBlock("dunestone_stairs",
    () -> new StairsBlock(() -> DUNESTONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> DUNESTONE_SLAB = registerBlock("dunestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//dunestone bricks
	public static final RegistryObject<Block> DUNESTONE_BRICKS = BLOCK.register("dunestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_STAIRS = registerBlock("dunestone_bricks_stairs",
    () -> new StairsBlock(() -> DUNESTONE_BRICKS.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> DUNESTONE_BRICKS_SLAB = registerBlock("dunestone_bricks_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//dunestone without stairs/cut
	public static final RegistryObject<Block> CUT_DUNESTONE = BLOCK.register("cut_dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_DUNESTONE = BLOCK.register("polished_dunestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	//limestone
	public static final RegistryObject<Block> LIMESTONE = BLOCK.register("limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> LIMESTONE_STAIRS = registerBlock("limestone_stairs",
    () -> new StairsBlock(() -> LIMESTONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> LIMESTONE_SLAB = registerBlock("limestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//cut_limestone
	public static final RegistryObject<Block> CUT_LIMESTONE = BLOCK.register("cut_limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CUT_LIMESTONE_STAIRS = registerBlock("cut_limestone_stairs",
    () -> new StairsBlock(() -> CUT_LIMESTONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CUT_LIMESTONE_SLAB = registerBlock("cut_limestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//limestone bricks
	public static final RegistryObject<Block> LIMESTONE_BRICKS = BLOCK.register("limestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_STAIRS = registerBlock("limestone_bricks_stairs",
    () -> new StairsBlock(() -> LIMESTONE_BRICKS.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> LIMESTONE_BRICKS_SLAB = registerBlock("limestone_bricks_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//limestone cracked bricks
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS = BLOCK.register("cracked_limestone_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_STAIRS = registerBlock("cracked_limestone_bricks_stairs",
    () -> new StairsBlock(() -> CRACKED_LIMESTONE_BRICKS.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_SLAB = registerBlock("cracked_limestone_bricks_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//polished limestone
	public static final RegistryObject<Block> POLISHED_LIMESTONE = BLOCK.register("polished_limestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs",
    () -> new StairsBlock(() -> POLISHED_LIMESTONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//void stone
	public static final RegistryObject<Block> VOID_STONE = BLOCK.register("void_stone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> VOID_STONE_STAIRS = registerBlock("void_stone_stairs",
    () -> new StairsBlock(() -> VOID_STONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_STONE_SLAB = registerBlock("void_stone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//void cobblestone
    public static final RegistryObject<Block> VOID_COBBLESTONE = BLOCK.register("void_cobblestone",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> VOID_COBBLESTONE_STAIRS = registerBlock("void_cobblestone_stairs",
    () -> new StairsBlock(() -> VOID_COBBLESTONE.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_COBBLESTONE_SLAB = registerBlock("void_cobblestone_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//crystals
	public static final RegistryObject<Block> VOID_CRYSTAL = BLOCK.register("void_crystal",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f).sound(SoundType.GLASS)));
	//pillar
	public static final RegistryObject<Block> CHARGED_VOID_PILLAR = BLOCK.register("charged_void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_PILLAR = BLOCK.register("void_pillar",
	() -> new RotatedPillarBlock(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	//void brick
	public static final RegistryObject<Block> VOID_BRICK = BLOCK.register("void_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_BRICK_STAIRS = registerBlock("void_brick_stairs",
    () -> new StairsBlock(() -> VOID_BRICK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_BRICK_SLAB = registerBlock("void_brick_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK = BLOCK.register("void_cracked_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(1)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_STAIRS = registerBlock("void_cracked_brick_stairs",
    () -> new StairsBlock(() -> VOID_CRACKED_BRICK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(1).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_CRACKED_BRICK_SLAB = registerBlock("void_cracked_brick_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//void polished
	public static final RegistryObject<Block> VOID_POLISHED_BLOCK = BLOCK.register("void_polished_block",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_POLISHED_BLOCK_STAIRS = registerBlock("void_polished_block_stairs",
    () -> new StairsBlock(() -> VOID_POLISHED_BLOCK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_POLISHED_BLOCK_SLAB = registerBlock("void_polished_block_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//void chiseled
	public static final RegistryObject<Block> VOID_CHISELED_BLOCK = BLOCK.register("void_chiseled_block",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BLOCK_STAIRS = registerBlock("void_chiseled_block_stairs",
    () -> new StairsBlock(() -> VOID_CHISELED_BLOCK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_CHISELED_BLOCK_SLAB = registerBlock("void_chiseled_block_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK = BLOCK.register("void_chiseled_brick",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK_STAIRS = registerBlock("void_chiseled_brick_stairs",
    () -> new StairsBlock(() -> VOID_CHISELED_BRICK.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_CHISELED_BRICK_SLAB = registerBlock("void_chiseled_brick_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS = BLOCK.register("void_chiseled_bricks",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS_STAIRS = registerBlock("void_chiseled_bricks_stairs",
    () -> new StairsBlock(() -> VOID_CHISELED_BRICKS.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
    public static final RegistryObject<Block> VOID_CHISELED_BRICKS_SLAB = registerBlock("void_chiseled_bricks_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS1 = BLOCK.register("void_chiseled_bricks1",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS1_STAIRS = registerBlock("void_chiseled_bricks1_stairs",
    () -> new StairsBlock(() -> VOID_CHISELED_BRICKS1.get().getDefaultState(),
    AbstractBlock.Properties.create(Material.IRON).harvestLevel(2).hardnessAndResistance(2f).harvestTool(ToolType.PICKAXE).setRequiresTool()));
	public static final RegistryObject<Block> VOID_CHISELED_BRICKS1_SLAB = registerBlock("void_chiseled_bricks1_slab",
    () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK)
    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(6f)));
	//other
	public static final RegistryObject<Block> QUICKSAND = BLOCK.register("quicksand", 
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.SHOVEL).setRequiresTool().hardnessAndResistance(3f)));
	public static final RegistryObject<Block> ELEMENTAL_MANIPULATOR = BLOCK.register("elemental_manipulator", 
	() -> new Block(Properties.create(Material.IRON).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).setLightLevel(s -> 4).notSolid()));
    public static final RegistryObject<Block> PEDESTAL = BLOCK.register("pedestal", 
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(1)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(3f).notSolid()));
	public static final RegistryObject<Block> BRONZE_LAMP = BLOCK.register("bronze_lamp", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(0)
	.hardnessAndResistance(3f).notSolid().sound(SoundType.GLASS).setLightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> BRONZE_LAMP_BLOCK = BLOCK.register("bronze_lamp_block", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(0)
	.hardnessAndResistance(3f).notSolid().sound(SoundType.GLASS).setLightLevel(getLightValueLit(13))));
	public static final RegistryObject<Block> SPIDER_EGG = BLOCK.register("spider_egg", 
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.CLOTH)));
	//ore
	public static final RegistryObject<Block> AMBER_ORE = BLOCK.register("amber_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(2)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(2f)));
	public static final RegistryObject<Block> COBALT_ORE = BLOCK.register("cobalt_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f)));
	public static final RegistryObject<Block> VOID_ORE = BLOCK.register("void_ore",
	() -> new Block(Properties.create(Material.ROCK).harvestLevel(3)
	.harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(4f)));
	//crystals
	public static final RegistryObject<Block> AMBER_CRYSTAL_0 = BLOCK.register("amber_crystal_0", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(2)
	.hardnessAndResistance(1f).notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> AMBER_CRYSTAL_1 = BLOCK.register("amber_crystal_1", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(2)
	.hardnessAndResistance(1f).notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> AMBER_CRYSTAL_2 = BLOCK.register("amber_crystal_2", 
	() -> new RedstoneLampBlock(Properties.create(Material.IRON).harvestLevel(2)
	.hardnessAndResistance(1f).notSolid().sound(SoundType.GLASS)));
	//vases
	public static final RegistryObject<Block> VASE_SMALL = BLOCK.register("vase_small", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> VASE_SMALL_1 = BLOCK.register("vase_small_1", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> VASE_BIG = BLOCK.register("vase_big", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> VASE_BIG_1 = BLOCK.register("vase_big_1", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> VASE_BIG_2 = BLOCK.register("vase_big_2", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	public static final RegistryObject<Block> VASE_BIG_3 = BLOCK.register("vase_big_3", 
	() -> new Block(Properties.create(Material.GLASS).harvestLevel(0)
	.zeroHardnessAndResistance().notSolid().sound(SoundType.GLASS)));
	//plants
	public static final RegistryObject<Block> ALOE_SMALL = BLOCK.register("aloe_small",
    () -> new DeadBushBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> DRIED_PLANT = BLOCK.register("dried_plant",
    () -> new DeadBushBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> ALOE = BLOCK.register("aloe",
    () -> new SandDoubleFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
    public static final RegistryObject<Block> CATTAIL = BLOCK.register("cattail",
    () -> new WaterTallFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> SOULROOT = BLOCK.register("soulroot",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> CRIMSON_SOULROOT = BLOCK.register("crimson_soulroot",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> DOUBLE_SOULROOT = BLOCK.register("double_crimson_soulroot",
    () -> new TallNetherFlowerBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> MAGMAROOT = BLOCK.register("crimson_magmaroot",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> DOUBLE_MAGMAROOT = BLOCK.register("double_crimson_magmaroot",
    () -> new TallNetherFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> GOLDY = BLOCK.register("crimson_goldy",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> DOUBLE_GOLDY = BLOCK.register("double_crimson_goldy",
    () -> new TallNetherFlowerBlock(Properties.from(Blocks.SUNFLOWER)));
	public static final RegistryObject<Block> BLOODROOT = BLOCK.register("bloodroot",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));
	public static final RegistryObject<Block> RAJUSH = BLOCK.register("crimson_rajush",
    () -> new NetherRootsBlock(AbstractBlock.Properties.from(Blocks.CRIMSON_ROOTS)));

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
