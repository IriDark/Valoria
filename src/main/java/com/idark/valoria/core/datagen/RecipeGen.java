package com.idark.valoria.core.datagen;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.BlockRegistry;
import com.idark.valoria.registries.ItemsRegistry;
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

public class RecipeGen extends RecipeProvider implements IConditionBuilder{

    public RecipeGen(PackOutput pOutput){
        super(pOutput);
    }

    public static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStairs, ItemLike pMaterial){
        stairBuilder(pStairs, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void fence(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFence, ItemLike pMaterial){
        fenceBuilder(pFence, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void fenceGate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFenceGate, ItemLike pMaterial){
        fenceGateBuilder(pFenceGate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    public static void spearRecipe(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike spear){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, spear)
                .define('/', Items.STICK)
                .define('X', material)
                .pattern(" XX")
                .pattern(" /X")
                .pattern("/  ")
                .unlockedBy(getHasName(spear), has(spear))
                .save(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(spear)));
    }

    public static void bookshelfRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike bookshelf){
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

    public static void ladderRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike ladder){
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

    public static void postRecipe(Consumer<FinishedRecipe> consumer, ItemLike wood, ItemLike post){
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

    public static void hedgeRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> log, ItemLike leaves, ItemLike hedge){
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

    public static void leafCarpetRecipe(Consumer<FinishedRecipe> consumer, ItemLike leaves, ItemLike carpet){
        ConditionalRecipe.builder()
                .addCondition(new ModLoadedCondition("quark"))
                .addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, carpet, 3)
                        .define('#', leaves)
                        .pattern("##")
                        .unlockedBy(getHasName(leaves), has(leaves)).save(consumer1))
                .build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(carpet)));
    }

    public static void chestRecipes(Consumer<FinishedRecipe> consumer, Block pNormal, Block pTrapped, ItemLike planks, TagKey<Item> log){
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

    public static void cutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pCategory, ItemLike pResult, ItemLike pMaterial, int pCount){
        stonecutterResultFromBase(pFinishedRecipeConsumer, pCategory, pResult, pMaterial, pCount);
    }

    public static void verticalSlabRecipe(Consumer<FinishedRecipe> consumer, ItemLike slab, ItemLike verticalSlab){
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

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter){
        spearRecipe(pWriter, ItemsRegistry.pyratite.get(), ItemsRegistry.pyratiteSpear.get());
        stainedGlassPaneFromStainedGlass(pWriter, BlockRegistry.bronzeGlassPane.get(), BlockRegistry.bronzeGlass.get());

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedAmbaneStone.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneStairs.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneSlab.get(), BlockRegistry.ambaneStone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneWall.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneBricks.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutAmbaneStone.get(), BlockRegistry.ambaneStone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneBricksStairs.get(), BlockRegistry.ambaneStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneBricksSlab.get(), BlockRegistry.ambaneStoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ambaneStoneBricksWall.get(), BlockRegistry.ambaneStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.chiseledAmbaneStoneBricks.get(), BlockRegistry.ambaneStoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedAmbaneStoneSlab.get(), BlockRegistry.polishedAmbaneStone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedAmbaneStoneStairs.get(), BlockRegistry.polishedAmbaneStone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteWall.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLow.get(), BlockRegistry.ephemarite.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLowWall.get(), BlockRegistry.ephemariteLow.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemarite.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemarite.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemarite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteWall.get(), BlockRegistry.polishedEphemarite.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteLow.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLow.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLow.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteLowWall.get(), BlockRegistry.polishedEphemariteLow.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.picriteBricks.get(), BlockRegistry.picrite.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.picriteBricksSlab.get(), BlockRegistry.picriteBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.picriteBricksStairs.get(), BlockRegistry.picriteBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.picriteBricksWall.get(), BlockRegistry.picriteBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedDunestone.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneStairs.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneSlab.get(), BlockRegistry.dunestone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneWall.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneBricks.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutDunestone.get(), BlockRegistry.dunestone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneBricksStairs.get(), BlockRegistry.dunestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneBricksSlab.get(), BlockRegistry.dunestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.dunestoneBricksWall.get(), BlockRegistry.dunestoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedLimestone.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneStairs.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneSlab.get(), BlockRegistry.limestone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneWall.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneBricks.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutLimestone.get(), BlockRegistry.limestone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneBricksStairs.get(), BlockRegistry.limestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneBricksSlab.get(), BlockRegistry.limestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.limestoneBricksWall.get(), BlockRegistry.limestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crackedLimestoneBricks.get(), BlockRegistry.limestoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crackedLimestoneBricksSlab.get(), BlockRegistry.crackedLimestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crackedLimestoneBricksWall.get(), BlockRegistry.crackedLimestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crackedLimestoneBricksStairs.get(), BlockRegistry.crackedLimestoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutLimestoneSlab.get(), BlockRegistry.cutLimestone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutLimestoneStairs.get(), BlockRegistry.cutLimestone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedLimestoneSlab.get(), BlockRegistry.polishedLimestone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedLimestoneStairs.get(), BlockRegistry.polishedLimestone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedCrystalStone.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneStairs.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneSlab.get(), BlockRegistry.crystalStone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneWall.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneBricks.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutCrystalStone.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStonePillar.get(), BlockRegistry.crystalStone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutPolishedCrystalStone.get(), BlockRegistry.polishedCrystalStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneBricksStairs.get(), BlockRegistry.crystalStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneBricksSlab.get(), BlockRegistry.crystalStoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crystalStoneBricksWall.get(), BlockRegistry.crystalStoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedTombstone.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneStairs.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneSlab.get(), BlockRegistry.tombstone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneWall.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneBricks.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutTombstone.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstonePillar.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutTombstonePillar.get(), BlockRegistry.tombstonePillar.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneBricksStairs.get(), BlockRegistry.tombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneBricksSlab.get(), BlockRegistry.tombstoneBricks.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.tombstoneBricksWall.get(), BlockRegistry.tombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.crackedTombstoneBricks.get(), BlockRegistry.tombstoneBricks.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedVoidStone.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidStoneStairs.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidStoneSlab.get(), BlockRegistry.voidStone.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidStoneWall.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidBrick.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidPillar.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.cutTombstonePillar.get(), BlockRegistry.voidStone.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidBrickStairs.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidBrickSlab.get(), BlockRegistry.voidBrick.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidBrickWall.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidCrackedBrick.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBrick.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBricks.get(), BlockRegistry.voidBrick.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidCrackedBrickStairs.get(), BlockRegistry.voidCrackedBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidCrackedBrickSlab.get(), BlockRegistry.voidCrackedBrick.get(), 2);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidCrackedBrickWall.get(), BlockRegistry.voidCrackedBrick.get(), 1);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBricksStairs.get(), BlockRegistry.voidChiseledBrick.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBricksSlab.get(), BlockRegistry.voidChiseledBrick.get(), 2);

        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBricksStairs.get(), BlockRegistry.voidChiseledBricks.get(), 1);
        cutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.voidChiseledBricksSlab.get(), BlockRegistry.voidChiseledBricks.get(), 2);

        netheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceAmber.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceAmber.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceDiamond.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceDiamond.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceEmerald.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceEmerald.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceRuby.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceRuby.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceSapphire.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceSapphire.get());

        netheriteSmithing(pWriter, ItemsRegistry.goldenRingAmber.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingAmber.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenRingDiamond.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingDiamond.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenRingEmerald.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingEmerald.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenRingRuby.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingRuby.get());
        netheriteSmithing(pWriter, ItemsRegistry.goldenRingSapphire.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingSapphire.get());

        fence(pWriter, BlockRegistry.shadewoodFence.get(), BlockRegistry.shadewoodPlanks.get());
        fenceGate(pWriter, BlockRegistry.shadewoodFenceGate.get(), BlockRegistry.shadewoodPlanks.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemarite.get());
        stairs(pWriter, BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemarite.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteWall.get(), BlockRegistry.ephemarite.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemarite.get(), BlockRegistry.polishedEphemarite.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLow.get());
        stairs(pWriter, BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLow.get());
        wall(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLowWall.get(), BlockRegistry.ephemariteLow.get());
        polished(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.ephemariteLow.get(), BlockRegistry.polishedEphemariteLow.get());

        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemarite.get());
        stairs(pWriter, BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemarite.get());
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLow.get());
        stairs(pWriter, BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLow.get());
    }
}