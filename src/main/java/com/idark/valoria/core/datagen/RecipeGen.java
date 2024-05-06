package com.idark.valoria.core.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
import com.idark.valoria.registries.TagsRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider implements IConditionBuilder {

    public RecipeGen(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        spearRecipe(pWriter, ItemsRegistry.PYRATITE.get(), ItemsRegistry.PYRATITE_SPEAR.get());
        stainedGlassPaneFromStainedGlass(pWriter, BlockRegistry.BRONZE_GLASS_PANE.get(), BlockRegistry.BRONZE_GLASS.get());

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_AMBANE_STONE.get(), BlockRegistry.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_STAIRS.get(), BlockRegistry.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_SLAB.get(), BlockRegistry.AMBANE_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_WALL.get(), BlockRegistry.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_BRICKS.get(), BlockRegistry.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_AMBANE_STONE.get(), BlockRegistry.AMBANE_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_BRICKS_STAIRS.get(), BlockRegistry.AMBANE_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_BRICKS_SLAB.get(), BlockRegistry.AMBANE_STONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.AMBANE_STONE_BRICKS_WALL.get(), BlockRegistry.AMBANE_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CHISELED_AMBANE_STONE_BRICKS.get(), BlockRegistry.AMBANE_STONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_AMBANE_STONE_SLAB.get(), BlockRegistry.POLISHED_AMBANE_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_AMBANE_STONE_STAIRS.get(), BlockRegistry.POLISHED_AMBANE_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_SLAB.get(), BlockRegistry.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_STAIRS.get(), BlockRegistry.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_WALL.get(), BlockRegistry.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW.get(), BlockRegistry.EPHEMARITE.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_SLAB.get(), BlockRegistry.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_STAIRS.get(), BlockRegistry.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_WALL.get(), BlockRegistry.EPHEMARITE_LOW.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE.get(), BlockRegistry.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_STAIRS.get(), BlockRegistry.POLISHED_EPHEMARITE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_LOW.get(), BlockRegistry.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_DUNESTONE.get(), BlockRegistry.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_STAIRS.get(), BlockRegistry.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_SLAB.get(), BlockRegistry.DUNESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_WALL.get(), BlockRegistry.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_BRICKS.get(), BlockRegistry.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_DUNESTONE.get(), BlockRegistry.DUNESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_BRICKS_STAIRS.get(), BlockRegistry.DUNESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_BRICKS_SLAB.get(), BlockRegistry.DUNESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DUNESTONE_BRICKS_WALL.get(), BlockRegistry.DUNESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_LIMESTONE.get(), BlockRegistry.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_STAIRS.get(), BlockRegistry.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_SLAB.get(), BlockRegistry.LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_WALL.get(), BlockRegistry.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_BRICKS.get(), BlockRegistry.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_LIMESTONE.get(), BlockRegistry.LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_BRICKS_STAIRS.get(), BlockRegistry.LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_BRICKS_SLAB.get(), BlockRegistry.LIMESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.LIMESTONE_BRICKS_WALL.get(), BlockRegistry.LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRACKED_LIMESTONE_BRICKS.get(), BlockRegistry.LIMESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRACKED_LIMESTONE_BRICKS_SLAB.get(), BlockRegistry.CRACKED_LIMESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRACKED_LIMESTONE_BRICKS_WALL.get(), BlockRegistry.CRACKED_LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRACKED_LIMESTONE_BRICKS_STAIRS.get(), BlockRegistry.CRACKED_LIMESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_LIMESTONE_SLAB.get(), BlockRegistry.CUT_LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_LIMESTONE_STAIRS.get(), BlockRegistry.CUT_LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_LIMESTONE_SLAB.get(), BlockRegistry.POLISHED_LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_LIMESTONE_STAIRS.get(), BlockRegistry.POLISHED_LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_CRYSTAL_STONE.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_STAIRS.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_SLAB.get(), BlockRegistry.CRYSTAL_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_WALL.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_BRICKS.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_CRYSTAL_STONE.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_PILLAR.get(), BlockRegistry.CRYSTAL_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_POLISHED_CRYSTAL_STONE.get(), BlockRegistry.POLISHED_CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_BRICKS_STAIRS.get(), BlockRegistry.CRYSTAL_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_BRICKS_SLAB.get(), BlockRegistry.CRYSTAL_STONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRYSTAL_STONE_BRICKS_WALL.get(), BlockRegistry.CRYSTAL_STONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_TOMBSTONE.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_STAIRS.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_SLAB.get(), BlockRegistry.TOMBSTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_WALL.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_BRICKS.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_TOMBSTONE.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_PILLAR.get(), BlockRegistry.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_TOMBSTONE_PILLAR.get(), BlockRegistry.TOMBSTONE_PILLAR.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_BRICKS_STAIRS.get(), BlockRegistry.TOMBSTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_BRICKS_SLAB.get(), BlockRegistry.TOMBSTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.TOMBSTONE_BRICKS_WALL.get(), BlockRegistry.TOMBSTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CRACKED_TOMBSTONE_BRICKS.get(), BlockRegistry.TOMBSTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_VOID_STONE.get(), BlockRegistry.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_STONE_STAIRS.get(), BlockRegistry.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_STONE_SLAB.get(), BlockRegistry.VOID_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_STONE_WALL.get(), BlockRegistry.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_BRICK.get(), BlockRegistry.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_PILLAR.get(), BlockRegistry.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.CUT_TOMBSTONE_PILLAR.get(), BlockRegistry.VOID_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_BRICK_STAIRS.get(), BlockRegistry.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_BRICK_SLAB.get(), BlockRegistry.VOID_BRICK.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_BRICK_WALL.get(), BlockRegistry.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CRACKED_BRICK.get(), BlockRegistry.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICK.get(), BlockRegistry.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICKS.get(), BlockRegistry.VOID_BRICK.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CRACKED_BRICK_STAIRS.get(), BlockRegistry.VOID_CRACKED_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CRACKED_BRICK_SLAB.get(), BlockRegistry.VOID_CRACKED_BRICK.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CRACKED_BRICK_WALL.get(), BlockRegistry.VOID_CRACKED_BRICK.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICKS_STAIRS.get(), BlockRegistry.VOID_CHISELED_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICKS_SLAB.get(), BlockRegistry.VOID_CHISELED_BRICK.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICKS_STAIRS.get(), BlockRegistry.VOID_CHISELED_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.VOID_CHISELED_BRICKS_SLAB.get(), BlockRegistry.VOID_CHISELED_BRICKS.get(), 2);

        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_NECKLACE_AMBER.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_NECKLACE_AMBER.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_NECKLACE_DIAMOND.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_NECKLACE_DIAMOND.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_NECKLACE_EMERALD.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_NECKLACE_EMERALD.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_NECKLACE_RUBY.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_NECKLACE_RUBY.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_NECKLACE_SAPPHIRE.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_NECKLACE_SAPPHIRE.get());

        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_RING_AMBER.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_RING_AMBER.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_RING_DIAMOND.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_RING_DIAMOND.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_RING_EMERALD.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_RING_EMERALD.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_RING_RUBY.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_RING_RUBY.get());
        netheriteSmithing(pWriter, ItemsRegistry.GOLDEN_RING_SAPPHIRE.get(), RecipeCategory.MISC, ItemsRegistry.NETHERITE_RING_SAPPHIRE.get());
        if(QuarkIntegration.isLoaded()) {
            verticalSlabRecipe(pWriter, BlockRegistry.SHADEWOOD_PLANKS_SLAB.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_PLANKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.ELDRITCH_PLANKS_SLAB.get(), QuarkIntegration.LoadedOnly.ELDRITCH_PLANKS_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.BRONZE_BLOCK_SLAB.get(), QuarkIntegration.LoadedOnly.BRONZE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.CUT_BRONZE_SLAB.get(), QuarkIntegration.LoadedOnly.CUT_BRONZE_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.EPHEMARITE_SLAB.get(), QuarkIntegration.LoadedOnly.EPHEMARITE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.EPHEMARITE_LOW_SLAB.get(), QuarkIntegration.LoadedOnly.EPHEMARITE_LOW_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_LOW_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.AMBANE_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.AMBANE_STONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.DUNESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.DUNESTONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.TOMBSTONE_SLAB.get(), QuarkIntegration.LoadedOnly.TOMBSTONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.LIMESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.LIMESTONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.DEEP_MARBLE_SLAB.get(), QuarkIntegration.LoadedOnly.DEEP_MARBLE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.PICRITE_SLAB.get(), QuarkIntegration.LoadedOnly.PICRITE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.VOID_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_STONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.CRYSTAL_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.CRYSTAL_STONE_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_AMBANE_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_AMBANE_STONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_LIMESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_LIMESTONE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_DEEP_MARBLE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_DEEP_MARBLE_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.POLISHED_PICRITE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_PICRITE_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.AMBANE_STONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.AMBANE_STONE_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.DUNESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.DUNESTONE_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.TOMBSTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.TOMBSTONE_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.LIMESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.LIMESTONE_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.VOID_BRICK_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_BRICK_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.CRYSTAL_STONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CRYSTAL_STONE_BRICKS_VERTICAL_SLAB.get());

            verticalSlabRecipe(pWriter, BlockRegistry.VOID_CHISELED_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CHISELED_VOID_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.CRACKED_LIMESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CRACKED_LIMESTONE_BRICKS_VERTICAL_SLAB.get());
            verticalSlabRecipe(pWriter, BlockRegistry.VOID_CRACKED_BRICK_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_CRACKED_BRICK_VERTICAL_SLAB.get());

            ladderRecipe(pWriter, BlockRegistry.SHADEWOOD_PLANKS.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_LADDER.get());
            ladderRecipe(pWriter, BlockRegistry.ELDRITCH_PLANKS.get(), QuarkIntegration.LoadedOnly.ELDRITCH_LADDER.get());
            chestRecipes(pWriter, QuarkIntegration.LoadedOnly.SHADEWOOD_CHEST.get(), QuarkIntegration.LoadedOnly.TRAPPED_SHADEWOOD_CHEST.get(), BlockRegistry.SHADEWOOD_PLANKS.get(), TagsRegistry.SHADEWOOD);
            chestRecipes(pWriter, QuarkIntegration.LoadedOnly.ELDRITCH_CHEST.get(), QuarkIntegration.LoadedOnly.TRAPPED_ELDRITCH_CHEST.get(), BlockRegistry.ELDRITCH_PLANKS.get(), TagsRegistry.ELDRITCH);

            postRecipe(pWriter, BlockRegistry.STRIPPED_SHADELOG.get(), QuarkIntegration.LoadedOnly.STRIPPED_SHADELOG_POST.get());
            postRecipe(pWriter, BlockRegistry.SHADELOG.get(), QuarkIntegration.LoadedOnly.SHADELOG_POST.get());

            hedgeRecipe(pWriter, TagsRegistry.SHADEWOOD, BlockRegistry.SHADEWOOD_LEAVES.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_HEDGE.get());
            leafCarpetRecipe(pWriter, BlockRegistry.SHADEWOOD_LEAVES.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_LEAF_CARPET.get());
            hedgeRecipe(pWriter, TagsRegistry.ELDRITCH, BlockRegistry.ELDRITCH_LEAVES.get(), QuarkIntegration.LoadedOnly.ELDRITCH_LEAF_HEDGE.get());
            leafCarpetRecipe(pWriter, BlockRegistry.ELDRITCH_LEAVES.get(), QuarkIntegration.LoadedOnly.ELDRITCH_LEAF_CARPET.get());

            bookshelfRecipe(pWriter, BlockRegistry.SHADEWOOD_PLANKS.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_BOOKSHELF.get());
            bookshelfRecipe(pWriter, BlockRegistry.ELDRITCH_PLANKS.get(), QuarkIntegration.LoadedOnly.ELDRITCH_BOOKSHELF.get());
        }

        fence(pWriter, BlockRegistry.SHADEWOOD_FENCE.get(), BlockRegistry.SHADEWOOD_PLANKS.get());
        fenceGate(pWriter, BlockRegistry.SHADEWOOD_FENCE_GATE.get(), BlockRegistry.SHADEWOOD_PLANKS.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_SLAB.get(), BlockRegistry.EPHEMARITE.get());
        stairs(pWriter, BlockRegistry.EPHEMARITE_STAIRS.get(), BlockRegistry.EPHEMARITE.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_WALL.get(), BlockRegistry.EPHEMARITE.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE.get(), BlockRegistry.POLISHED_EPHEMARITE.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_SLAB.get(), BlockRegistry.EPHEMARITE_LOW.get());
        stairs(pWriter, BlockRegistry.EPHEMARITE_LOW_STAIRS.get(), BlockRegistry.EPHEMARITE_LOW.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_WALL.get(), BlockRegistry.EPHEMARITE_LOW.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE.get());
        stairs(pWriter, BlockRegistry.POLISHED_EPHEMARITE_STAIRS.get(), BlockRegistry.POLISHED_EPHEMARITE.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get());
        stairs(pWriter, BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get());
    }

    public static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStairs, ItemLike pMaterial) {
        stairBuilder(pStairs, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void fence(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFence, ItemLike pMaterial) {
        fenceBuilder(pFence, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void fenceGate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFenceGate, ItemLike pMaterial) {
        fenceGateBuilder(pFenceGate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void spearRecipe(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike spear) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, spear)
                .define('/', Items.STICK)
                .define('X', material)
                .pattern(" XX")
                .pattern(" /X")
                .pattern("/  ")
                .unlockedBy(getHasName(spear), has(spear))
                .save(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(spear)));
    }

    public static void bookshelfRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike bookshelf) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf)
                        .define('#', plank)
                        .define('B', Items.BOOK)
                        .pattern("###")
                        .pattern("BBB")
                        .pattern("###")
                        .unlockedBy(getHasName(plank), has(plank)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(bookshelf)));
    }

    public static void ladderRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike ladder) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ladder, 4)
                        .define('#', Items.STICK)
                        .define('W', plank)
                        .pattern("# #")
                        .pattern("#W#")
                        .pattern("# #")
                        .unlockedBy(getHasName(plank), has(plank)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(ladder)));
    }

    public static void postRecipe(Consumer<FinishedRecipe> consumer, ItemLike wood, ItemLike post) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, post, 8)
                        .define('#', wood)
                        .pattern("#")
                        .pattern("#")
                        .pattern("#")
                        .unlockedBy(getHasName(wood), has(wood)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(post)));
    }

    public static void hedgeRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> log, ItemLike leaves, ItemLike hedge) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, hedge, 2)
                        .define('#', log)
                        .define('L', leaves)
                        .pattern("L")
                        .pattern("#")
                        .unlockedBy(getHasName(leaves), has(leaves)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(hedge)));
    }

    public static void leafCarpetRecipe(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike carpet) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, carpet, 3)
                        .define('#', leaves)
                        .pattern("##")
                        .unlockedBy(getHasName(leaves), has(leaves)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(carpet)));
    }

    public static void chestRecipes(Consumer<FinishedRecipe> consumer, Block pNormal, Block pTrapped, ItemLike planks, TagKey<Item> log) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pNormal)
                        .define('#', planks)
                        .pattern("###")
                        .pattern("# #")
                        .pattern("###")
                        .unlockedBy(getHasName(planks), has(planks))
                        .save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pNormal))))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pNormal)
                )
        );

        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pNormal, 4)
                        .define('#', log)
                        .pattern("###")
                        .pattern("# #")
                        .pattern("###")
                        .unlockedBy(getHasName(pNormal), has(pNormal))
                        .save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pNormal) + "_wood")))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pNormal) + "_wood"
                )
        );

        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pTrapped)
                        .requires(pNormal)
                        .requires(Items.TRIPWIRE_HOOK)
                        .unlockedBy(getHasName(pNormal), has(pNormal))
                        .save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pTrapped))))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pTrapped)
                )
        );
    }

    public static void cutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, ItemLike pMaterial, int pCount) {
        stonecutterResultFromBase(pFinishedRecipeConsumer, pCategory, pResult, pMaterial, pCount);
    }

    public static void verticalSlabRecipe(Consumer<FinishedRecipe> consumer, ItemLike slab, ItemLike verticalSlab) {
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, verticalSlab, 3)
                        .define('#', slab)
                        .pattern("#")
                        .pattern("#")
                        .pattern("#")
                        .unlockedBy(getHasName(slab), has(slab)).save(consumer1, new ResourceLocation(Valoria.ID, getItemName(verticalSlab))))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(verticalSlab)));
    }
}