package com.idark.valoria.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.world.block.ModBlocks;
import com.idark.valoria.registries.world.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> pWriter) {
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

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE_SLAB.get(), ModBlocks.POLISHED_AMBANE_STONE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_AMBANE_STONE_STAIRS.get(), ModBlocks.POLISHED_AMBANE_STONE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_SLAB.get(), ModBlocks.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_STAIRS.get(), ModBlocks.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_WALL.get(), ModBlocks.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW.get(), ModBlocks.EPHEMARITE.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW_SLAB.get(), ModBlocks.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW_STAIRS.get(), ModBlocks.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW_WALL.get(), ModBlocks.EPHEMARITE_LOW.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE.get(), ModBlocks.EPHEMARITE.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_SLAB.get(), ModBlocks.POLISHED_EPHEMARITE.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_STAIRS.get(), ModBlocks.POLISHED_EPHEMARITE.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_LOW.get(), ModBlocks.EPHEMARITE_LOW.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_LOW_SLAB.get(), ModBlocks.POLISHED_EPHEMARITE_LOW.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_LOW_STAIRS.get(), ModBlocks.POLISHED_EPHEMARITE_LOW.get(), 1);

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

        verticalSlabRecipe(pWriter, ModBlocks.SHADEWOOD_PLANKS_SLAB.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_PLANKS_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.BRONZE_BLOCK_SLAB.get(), QuarkIntegration.LoadedOnly.BRONZE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.CUT_BRONZE_SLAB.get(), QuarkIntegration.LoadedOnly.CUT_BRONZE_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.EPHEMARITE_SLAB.get(), QuarkIntegration.LoadedOnly.EPHEMARITE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.EPHEMARITE_LOW_SLAB.get(), QuarkIntegration.LoadedOnly.EPHEMARITE_LOW_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_EPHEMARITE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_EPHEMARITE_LOW_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_EPHEMARITE_LOW_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.AMBANE_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.AMBANE_STONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.DUNESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.DUNESTONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.TOMBSTONE_SLAB.get(), QuarkIntegration.LoadedOnly.TOMBSTONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.LIMESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.LIMESTONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.DEEP_MARBLE_SLAB.get(), QuarkIntegration.LoadedOnly.DEEP_MARBLE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.PICRITE_SLAB.get(), QuarkIntegration.LoadedOnly.PICRITE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.VOID_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_STONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.CRYSTAL_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.CRYSTAL_STONE_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_AMBANE_STONE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_AMBANE_STONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_LIMESTONE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_LIMESTONE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_DEEP_MARBLE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_DEEP_MARBLE_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.POLISHED_PICRITE_SLAB.get(), QuarkIntegration.LoadedOnly.POLISHED_PICRITE_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.AMBANE_STONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.AMBANE_STONE_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.DUNESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.DUNESTONE_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.TOMBSTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.TOMBSTONE_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.LIMESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.LIMESTONE_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.VOID_BRICK_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_BRICK_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.CRYSTAL_STONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CRYSTAL_STONE_BRICKS_VERTICAL_SLAB.get());

        verticalSlabRecipe(pWriter, ModBlocks.VOID_CHISELED_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CHISELED_VOID_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.CRACKED_LIMESTONE_BRICKS_SLAB.get(), QuarkIntegration.LoadedOnly.CRACKED_LIMESTONE_BRICKS_VERTICAL_SLAB.get());
        verticalSlabRecipe(pWriter, ModBlocks.VOID_CRACKED_BRICK_SLAB.get(), QuarkIntegration.LoadedOnly.VOID_CRACKED_BRICK_VERTICAL_SLAB.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_SLAB.get(), ModBlocks.EPHEMARITE.get());
        stairBuilder(ModBlocks.EPHEMARITE_STAIRS.get(), Ingredient.of(ModBlocks.EPHEMARITE.get()));
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_WALL.get(), ModBlocks.EPHEMARITE.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE.get(), ModBlocks.POLISHED_EPHEMARITE.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW_SLAB.get(), ModBlocks.EPHEMARITE_LOW.get());
        stairBuilder(ModBlocks.EPHEMARITE_LOW_STAIRS.get(), Ingredient.of(ModBlocks.EPHEMARITE_LOW.get()));
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW_WALL.get(), ModBlocks.EPHEMARITE_LOW.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EPHEMARITE_LOW.get(), ModBlocks.POLISHED_EPHEMARITE_LOW.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_SLAB.get(), ModBlocks.POLISHED_EPHEMARITE.get());
        stairBuilder(ModBlocks.POLISHED_EPHEMARITE_STAIRS.get(), Ingredient.of(ModBlocks.POLISHED_EPHEMARITE.get()));
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_EPHEMARITE_LOW_SLAB.get(), ModBlocks.POLISHED_EPHEMARITE_LOW.get());
        stairBuilder(ModBlocks.POLISHED_EPHEMARITE_LOW_STAIRS.get(), Ingredient.of(ModBlocks.POLISHED_EPHEMARITE_LOW.get()));
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
                        .unlockedBy(getHasName(slab), has(slab)).save(consumer1, new ResourceLocation(Valoria.MOD_ID, getItemName(verticalSlab))))
                .build(consumer, new ResourceLocation(Valoria.MOD_ID, "crafting/" + getItemName(verticalSlab)));
    }
}