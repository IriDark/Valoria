package com.idark.valoria.core.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.compat.quark.QuarkIntegration;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
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

public class RecipeGen extends RecipeProvider implements IConditionBuilder {

    public RecipeGen(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> pWriter) {
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

        verticalSlabRecipe(pWriter, BlockRegistry.SHADEWOOD_PLANKS_SLAB.get(), QuarkIntegration.LoadedOnly.SHADEWOOD_PLANKS_VERTICAL_SLAB.get());

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

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_SLAB.get(), BlockRegistry.EPHEMARITE.get());
        stairBuilder(BlockRegistry.EPHEMARITE_STAIRS.get(), Ingredient.of(BlockRegistry.EPHEMARITE.get()));
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_WALL.get(), BlockRegistry.EPHEMARITE.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE.get(), BlockRegistry.POLISHED_EPHEMARITE.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_SLAB.get(), BlockRegistry.EPHEMARITE_LOW.get());
        stairBuilder(BlockRegistry.EPHEMARITE_LOW_STAIRS.get(), Ingredient.of(BlockRegistry.EPHEMARITE_LOW.get()));
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW_WALL.get(), BlockRegistry.EPHEMARITE_LOW.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.EPHEMARITE_LOW.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE.get());
        stairBuilder(BlockRegistry.POLISHED_EPHEMARITE_STAIRS.get(), Ingredient.of(BlockRegistry.POLISHED_EPHEMARITE.get()));
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.POLISHED_EPHEMARITE_LOW_SLAB.get(), BlockRegistry.POLISHED_EPHEMARITE_LOW.get());
        stairBuilder(BlockRegistry.POLISHED_EPHEMARITE_LOW_STAIRS.get(), Ingredient.of(BlockRegistry.POLISHED_EPHEMARITE_LOW.get()));
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