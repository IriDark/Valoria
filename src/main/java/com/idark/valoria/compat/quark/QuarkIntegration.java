package com.idark.valoria.compat.quark;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.block.entity.types.ModChestBlockEntity;
import com.idark.valoria.registries.block.entity.types.ModTrappedChestBlockEntity;
import com.idark.valoria.registries.block.types.ModChestBlock;
import com.idark.valoria.registries.block.types.ModTrappedChestBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.violetmoon.quark.content.building.block.*;

import java.util.function.Supplier;

public class QuarkIntegration {

    private final static String MODID = Valoria.MOD_ID;

    public static void init(IEventBus eventBus) {
        LoadedOnly.BLOCKS.register(eventBus);
        LoadedOnly.BLOCK_ENTITIES.register(eventBus);
    }

    /**
     * @return If quark is in ModList - true, otherwise false
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded("quark");
    }

    /**
     * Checks if Quark is loaded then loads the content inside the class (Auto BlockItem Reg)
     */
    public static class LoadedOnly {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
        public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Valoria.MOD_ID);

        public static final RegistryObject<Block> EPHEMARITE_VERTICAL_SLAB = registerBlock("ephemarite_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.EPHEMARITE, BlockBehaviour.Properties.copy(BlockRegistry.EPHEMARITE_SLAB.get())));
        public static final RegistryObject<Block> EPHEMARITE_LOW_VERTICAL_SLAB = registerBlock("ephemarite_low_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.EPHEMARITE_LOW, BlockBehaviour.Properties.copy(BlockRegistry.EPHEMARITE_LOW_SLAB.get())));
        public static final RegistryObject<Block> POLISHED_EPHEMARITE_LOW_VERTICAL_SLAB = registerBlock("polished_ephemarite_low_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.POLISHED_EPHEMARITE_LOW, BlockBehaviour.Properties.copy(BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get())));
        public static final RegistryObject<Block> POLISHED_EPHEMARITE_VERTICAL_SLAB = registerBlock("polished_ephemarite_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.POLISHED_EPHEMARITE, BlockBehaviour.Properties.copy(BlockRegistry.POLISHED_EPHEMARITE_SLAB.get())));
        public static final RegistryObject<Block> VERTICAL_SHADEWOOD_PLANKS_ = registerBlock("vertical_shadewood_planks",
                () -> new Block(BlockBehaviour.Properties.copy(BlockRegistry.SHADEWOOD_PLANKS.get())));
        public static final RegistryObject<Block> SHADELOG_POST = registerBlock("shadelog_post",
                () -> new WoodPostBlock(null, BlockRegistry.SHADELOG.get(), "", SoundType.WOOD));
        public static final RegistryObject<Block> STRIPPED_SHADELOG_POST = registerBlock("stripped_shadelog_post",
                () -> new WoodPostBlock(null, BlockRegistry.STRIPPED_SHADELOG.get(), "striped_", SoundType.WOOD));
        public static final RegistryObject<Block> SHADEWOOD_LEAF_CARPET = registerBlock("shadewood_leaf_carpet",
                () -> new LeafCarpetBlock("shadewood", BlockRegistry.SHADEWOOD_LEAVES.get(), null));
        public static final RegistryObject<Block> SHADEWOOD_PLANKS_VERTICAL_SLAB = registerBlock("shadewood_planks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.SHADEWOOD_PLANKS, BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
        public static final RegistryObject<Block> BRONZE_VERTICAL_SLAB = registerBlock("bronze_block_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.BRONZE_BLOCK, BlockBehaviour.Properties.copy(BlockRegistry.BRONZE_BLOCK_SLAB.get())));
        public static final RegistryObject<Block> CUT_BRONZE_VERTICAL_SLAB = registerBlock("cut_bronze_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CUT_BRONZE, BlockBehaviour.Properties.copy(BlockRegistry.BRONZE_BLOCK_SLAB.get())));
        public static final RegistryObject<Block> AMBANE_STONE_VERTICAL_SLAB = registerBlock("ambane_stone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.AMBANE_STONE, BlockBehaviour.Properties.copy(BlockRegistry.AMBANE_STONE.get())));
        public static final RegistryObject<Block> AMBANE_STONE_BRICKS_VERTICAL_SLAB = registerBlock("ambane_stone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.AMBANE_STONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.AMBANE_STONE_BRICKS.get())));
        public static final RegistryObject<Block> POLISHED_AMBANE_STONE_VERTICAL_SLAB = registerBlock("polished_ambane_stone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.POLISHED_AMBANE_STONE, BlockBehaviour.Properties.copy(BlockRegistry.POLISHED_AMBANE_STONE.get())));
        public static final RegistryObject<Block> DUNESTONE_VERTICAL_SLAB = registerBlock("dunestone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.DUNESTONE, BlockBehaviour.Properties.copy(BlockRegistry.DUNESTONE.get())));
        public static final RegistryObject<Block> DUNESTONE_BRICKS_VERTICAL_SLAB = registerBlock("dunestone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.DUNESTONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.DUNESTONE_BRICKS.get())));
        public static final RegistryObject<Block> LIMESTONE_VERTICAL_SLAB = registerBlock("limestone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.LIMESTONE, BlockBehaviour.Properties.copy(BlockRegistry.LIMESTONE.get())));
        public static final RegistryObject<Block> LIMESTONE_BRICKS_VERTICAL_SLAB = registerBlock("limestone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.LIMESTONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.LIMESTONE_BRICKS.get())));
        public static final RegistryObject<Block> CRACKED_LIMESTONE_BRICKS_VERTICAL_SLAB = registerBlock("cracked_limestone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CRACKED_LIMESTONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.CRACKED_LIMESTONE_BRICKS.get())));
        public static final RegistryObject<Block> CUT_LIMESTONE_VERTICAL_SLAB = registerBlock("cut_limestone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CUT_LIMESTONE, BlockBehaviour.Properties.copy(BlockRegistry.CUT_LIMESTONE.get())));
        public static final RegistryObject<Block> POLISHED_LIMESTONE_VERTICAL_SLAB = registerBlock("polished_limestone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CUT_LIMESTONE, BlockBehaviour.Properties.copy(BlockRegistry.CUT_LIMESTONE.get())));
        public static final RegistryObject<Block> CRYSTAL_STONE_VERTICAL_SLAB = registerBlock("crystal_stone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CRYSTAL_STONE, BlockBehaviour.Properties.copy(BlockRegistry.CRYSTAL_STONE.get())));
        public static final RegistryObject<Block> CRYSTAL_STONE_BRICKS_VERTICAL_SLAB = registerBlock("crystal_stone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.CRYSTAL_STONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.CRYSTAL_STONE_BRICKS.get())));
        public static final RegistryObject<Block> TOMBSTONE_VERTICAL_SLAB = registerBlock("tombstone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.TOMBSTONE, BlockBehaviour.Properties.copy(BlockRegistry.TOMBSTONE.get())));
        public static final RegistryObject<Block> TOMBSTONE_BRICKS_VERTICAL_SLAB = registerBlock("tombstone_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.TOMBSTONE_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.TOMBSTONE_BRICKS.get())));
        public static final RegistryObject<Block> DEEP_MARBLE_VERTICAL_SLAB = registerBlock("deep_marble_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.DEEP_MARBLE, BlockBehaviour.Properties.copy(BlockRegistry.DEEP_MARBLE.get())));
        public static final RegistryObject<Block> POLISHED_DEEP_MARBLE_VERTICAL_SLAB = registerBlock("polished_deep_marble_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.DEEP_MARBLE, BlockBehaviour.Properties.copy(BlockRegistry.DEEP_MARBLE.get())));
        public static final RegistryObject<Block> PICRITE_VERTICAL_SLAB = registerBlock("picrite_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.PICRITE, BlockBehaviour.Properties.copy(BlockRegistry.PICRITE.get())));
        public static final RegistryObject<Block> POLISHED_PICRITE_VERTICAL_SLAB = registerBlock("polished_picrite_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.PICRITE, BlockBehaviour.Properties.copy(BlockRegistry.PICRITE.get())));
        public static final RegistryObject<Block> VOID_STONE_VERTICAL_SLAB = registerBlock("void_stone_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.VOID_STONE, BlockBehaviour.Properties.copy(BlockRegistry.VOID_STONE.get())));
        public static final RegistryObject<Block> VOID_BRICK_VERTICAL_SLAB = registerBlock("void_brick_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.VOID_BRICK, BlockBehaviour.Properties.copy(BlockRegistry.VOID_BRICK.get())));
        public static final RegistryObject<Block> VOID_CRACKED_BRICK_VERTICAL_SLAB = registerBlock("void_cracked_brick_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.VOID_CRACKED_BRICK, BlockBehaviour.Properties.copy(BlockRegistry.VOID_CRACKED_BRICK.get())));
        public static final RegistryObject<Block> CHISELED_VOID_BRICKS_VERTICAL_SLAB = registerBlock("void_chiseled_bricks_vertical_slab",
                () -> new VerticalSlabBlock(BlockRegistry.VOID_CHISELED_BRICKS, BlockBehaviour.Properties.copy(BlockRegistry.VOID_CHISELED_BRICKS.get())));
        public static final RegistryObject<Block> HOLLOW_SHADELOG = registerBlock("hollow_shadelog",
                () -> new HollowLogBlock(BlockRegistry.SHADELOG.get(), null, true));
        public static final RegistryObject<Block> SHADEWOOD_LEAF_HEDGE = registerBlock("shadewood_hedge",
                () -> new HedgeBlock("shadewood", null, Blocks.OAK_FENCE, BlockRegistry.SHADEWOOD_LEAVES.get()));
        public static final RegistryObject<Block> SHADEWOOD_BOOKSHELF = registerBlock("shadewood_bookshelf",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).sound(SoundType.WOOD)));
        public static final RegistryObject<Block> SHADEWOOD_LADDER = registerBlock("shadewood_ladder",
                () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.WOOD)));
        public static final RegistryObject<Block> SHADEWOOD_CHEST = registerBlock("shadewood_chest",
                () -> new ModChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava(), LoadedOnly.CHEST_BLOCK_ENTITY::get));
        public static final RegistryObject<Block> TRAPPED_SHADEWOOD_CHEST = registerBlock("trapped_shadewood_chest",
                () -> new ModTrappedChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD).ignitedByLava(), LoadedOnly.TRAPPED_CHEST_BLOCK_ENTITY::get));

        public static final RegistryObject<BlockEntityType<ModChestBlockEntity>> CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_chest", () -> BlockEntityType.Builder.of(ModChestBlockEntity::new, LoadedOnly.SHADEWOOD_CHEST.get()).build(null));
        public static final RegistryObject<BlockEntityType<ModTrappedChestBlockEntity>> TRAPPED_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("mod_trapped_chest", () -> BlockEntityType.Builder.of(ModTrappedChestBlockEntity::new, LoadedOnly.TRAPPED_SHADEWOOD_CHEST.get()).build(null));

        private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
            RegistryObject<T> toReturn = BLOCKS.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        }

        private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
            ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }
}
