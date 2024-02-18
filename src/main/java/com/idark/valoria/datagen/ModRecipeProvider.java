package com.idark.valoria.datagen;

import com.idark.valoria.block.ModBlocks;
import com.idark.valoria.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE.get(), ModBlocks.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_LOW.get(), ModBlocks.EPHEMARITE_LOW.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE.get(), ModBlocks.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_STAIRS.get(), ModBlocks.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_SLAB.get(), ModBlocks.AMBANE_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_WALL.get(), ModBlocks.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_BRICKS.get(), ModBlocks.AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_AMBANE_STONE.get(), ModBlocks.AMBANE_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_BRICKS_STAIRS.get(), ModBlocks.AMBANE_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_BRICKS_SLAB.get(), ModBlocks.AMBANE_STONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMBANE_STONE_BRICKS_WALL.get(), ModBlocks.AMBANE_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_AMBANE_STONE_BRICKS.get(), ModBlocks.AMBANE_STONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE.get(), ModBlocks.POLISHED_AMBANE_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE_SLAB.get(), ModBlocks.POLISHED_AMBANE_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE_STAIRS.get(), ModBlocks.POLISHED_AMBANE_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_DUNESTONE.get(), ModBlocks.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_STAIRS.get(), ModBlocks.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_SLAB.get(), ModBlocks.DUNESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_WALL.get(), ModBlocks.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_BRICKS.get(), ModBlocks.DUNESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_DUNESTONE.get(), ModBlocks.DUNESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_BRICKS_STAIRS.get(), ModBlocks.DUNESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_BRICKS_SLAB.get(), ModBlocks.DUNESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DUNESTONE_BRICKS_WALL.get(), ModBlocks.DUNESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE.get(), ModBlocks.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_STAIRS.get(), ModBlocks.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_SLAB.get(), ModBlocks.LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_WALL.get(), ModBlocks.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_BRICKS.get(), ModBlocks.LIMESTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LIMESTONE.get(), ModBlocks.LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_BRICKS_STAIRS.get(), ModBlocks.LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_BRICKS_SLAB.get(), ModBlocks.LIMESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_BRICKS_WALL.get(), ModBlocks.LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_LIMESTONE_BRICKS.get(), ModBlocks.LIMESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_LIMESTONE_BRICKS_SLAB.get(), ModBlocks.CRACKED_LIMESTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_LIMESTONE_BRICKS_WALL.get(), ModBlocks.CRACKED_LIMESTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_LIMESTONE_BRICKS_STAIRS.get(), ModBlocks.CRACKED_LIMESTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LIMESTONE_SLAB.get(), ModBlocks.CUT_LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_LIMESTONE_STAIRS.get(), ModBlocks.CUT_LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_SLAB.get(), ModBlocks.POLISHED_LIMESTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_STAIRS.get(), ModBlocks.POLISHED_LIMESTONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_CRYSTAL_STONE.get(), ModBlocks.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_STAIRS.get(), ModBlocks.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_SLAB.get(), ModBlocks.CRYSTAL_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_WALL.get(), ModBlocks.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_BRICKS.get(), ModBlocks.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_CRYSTAL_STONE.get(), ModBlocks.CRYSTAL_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_PILLAR.get(), ModBlocks.CRYSTAL_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_POLISHED_CRYSTAL_STONE.get(), ModBlocks.POLISHED_CRYSTAL_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_BRICKS_STAIRS.get(), ModBlocks.CRYSTAL_STONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_BRICKS_SLAB.get(), ModBlocks.CRYSTAL_STONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_STONE_BRICKS_WALL.get(), ModBlocks.CRYSTAL_STONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_TOMBSTONE.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_STAIRS.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_SLAB.get(), ModBlocks.TOMBSTONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_WALL.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_BRICKS.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_TOMBSTONE.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_PILLAR.get(), ModBlocks.TOMBSTONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_TOMBSTONE_PILLAR.get(), ModBlocks.TOMBSTONE_PILLAR.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_BRICKS_STAIRS.get(), ModBlocks.TOMBSTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_BRICKS_SLAB.get(), ModBlocks.TOMBSTONE_BRICKS.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TOMBSTONE_BRICKS_WALL.get(), ModBlocks.TOMBSTONE_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRACKED_TOMBSTONE_BRICKS.get(), ModBlocks.TOMBSTONE_BRICKS.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_VOID_STONE.get(), ModBlocks.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_STONE_STAIRS.get(), ModBlocks.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_STONE_SLAB.get(), ModBlocks.VOID_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_STONE_WALL.get(), ModBlocks.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK.get(), ModBlocks.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_PILLAR.get(), ModBlocks.VOID_STONE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_TOMBSTONE_PILLAR.get(), ModBlocks.VOID_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_STAIRS.get(), ModBlocks.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_SLAB.get(), ModBlocks.VOID_BRICK.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_BRICK_WALL.get(), ModBlocks.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CRACKED_BRICK.get(), ModBlocks.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICK.get(), ModBlocks.VOID_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICKS.get(), ModBlocks.VOID_BRICK.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CRACKED_BRICK_STAIRS.get(), ModBlocks.VOID_CRACKED_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CRACKED_BRICK_SLAB.get(), ModBlocks.VOID_CRACKED_BRICK.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CRACKED_BRICK_WALL.get(), ModBlocks.VOID_CRACKED_BRICK.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICKS_STAIRS.get(), ModBlocks.VOID_CHISELED_BRICK.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICKS_SLAB.get(), ModBlocks.VOID_CHISELED_BRICK.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICKS_STAIRS.get(), ModBlocks.VOID_CHISELED_BRICKS.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.VOID_CHISELED_BRICKS_SLAB.get(), ModBlocks.VOID_CHISELED_BRICKS.get(), 2);

        netheriteSmithing(pWriter, ModItems.GOLDEN_NECKLACE_AMBER.get(), RecipeCategory.MISC, ModItems.NETHERITE_NECKLACE_AMBER.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_NECKLACE_DIAMOND.get(), RecipeCategory.MISC, ModItems.NETHERITE_NECKLACE_DIAMOND.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_NECKLACE_EMERALD.get(), RecipeCategory.MISC, ModItems.NETHERITE_NECKLACE_EMERALD.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_NECKLACE_RUBY.get(), RecipeCategory.MISC, ModItems.NETHERITE_NECKLACE_RUBY.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_NECKLACE_SAPPHIRE.get(), RecipeCategory.MISC, ModItems.NETHERITE_NECKLACE_SAPPHIRE.get());

        netheriteSmithing(pWriter, ModItems.GOLDEN_RING_AMBER.get(), RecipeCategory.MISC, ModItems.NETHERITE_RING_AMBER.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_RING_DIAMOND.get(), RecipeCategory.MISC, ModItems.NETHERITE_RING_DIAMOND.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_RING_EMERALD.get(), RecipeCategory.MISC, ModItems.NETHERITE_RING_EMERALD.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_RING_RUBY.get(), RecipeCategory.MISC, ModItems.NETHERITE_RING_RUBY.get());
        netheriteSmithing(pWriter, ModItems.GOLDEN_RING_SAPPHIRE.get(), RecipeCategory.MISC, ModItems.NETHERITE_RING_SAPPHIRE.get());
    }

    public static void cutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, ItemLike pMaterial, int pCount) {
        stonecutterResultFromBase(pFinishedRecipeConsumer, pCategory, pResult, pMaterial, pCount);
    }
}