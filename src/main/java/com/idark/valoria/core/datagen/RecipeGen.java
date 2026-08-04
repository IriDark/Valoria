package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import com.idark.valoria.registries.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class RecipeGen extends RecipeProvider implements IConditionBuilder {

    public RecipeGen(PackOutput pOutput) {
        super(pOutput);
    }

    public static void slab(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pSlab, ItemLike pMaterial) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, pSlab, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pSlab));
    }

    protected static void wall(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pWall, ItemLike pMaterial) {
        wallBuilder(RecipeCategory.BUILDING_BLOCKS, pWall, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pWall));
    }

    protected static void polished(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pMaterial) {
        polishedBuilder(RecipeCategory.BUILDING_BLOCKS, pResult, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pResult));
    }

    public static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pStairs, ItemLike pMaterial) {
        stairBuilder(pStairs, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pStairs));
    }

    public static void fence(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFence, ItemLike pMaterial) {
        fenceBuilder(pFence, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pFence));
    }

    public static void fenceGate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pFenceGate, ItemLike pMaterial) {
        fenceGateBuilder(pFenceGate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pFenceGate));
    }

    public static void trapdoor(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pTrapdoor, ItemLike pMaterial) {
        trapdoorBuilder(pTrapdoor, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pTrapdoor));
    }

    public static void door(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pDoor, ItemLike pMaterial) {
        doorBuilder(pDoor, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pDoor));
    }

    public static void button(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pButton, ItemLike pMaterial) {
        buttonBuilder(pButton, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pButton));
    }

    public static void sign(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pSign, ItemLike pMaterial) {
        signBuilder(pSign, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pSign));
    }

    public static void hangingSign(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pSign, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pSign, 6).group("hanging_sign").define('#', pMaterial).define('X', Items.CHAIN).pattern("X X").pattern("###").pattern("###").unlockedBy("has_stripped_logs", has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pSign));
    }

    public static void pressurePlate(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlate, ItemLike pMaterial) {
        pressurePlateBuilder(RecipeCategory.BUILDING_BLOCKS, pPlate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, valoriaRecipeId(pPlate));
    }

    protected static void boat(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBoat, ItemLike pMaterial) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, pBoat).define('#', pMaterial).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(pFinishedRecipeConsumer, valoriaRecipeId(pBoat));
    }

    public static void chestboat(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pBoat, ItemLike pMaterial) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, pBoat).requires(Blocks.CHEST).requires(pMaterial).group("chest_boat").unlockedBy("has_boat", has(ItemTags.BOATS)).save(pFinishedRecipeConsumer, valoriaRecipeId(pBoat));
    }

    private static ResourceLocation valoriaRecipeId(ItemLike result) {
        return new ResourceLocation(Valoria.ID, getItemName(result));
    }

    protected static void planksFromLog(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, ItemLike pLog, int pResultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pPlanks, pResultCount).requires(pLog).group("planks").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer, valoriaRecipeId(pPlanks));
    }

    protected static void woodFromLogs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pWood, ItemLike pLog) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pWood, 3).define('#', pLog).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(pLog)).save(pFinishedRecipeConsumer, valoriaRecipeId(pWood));
    }

    public static void registerWoodset(Consumer<FinishedRecipe> pWriter, ItemLike slab, ItemLike stairs, ItemLike fence, ItemLike fenceGate, ItemLike door, ItemLike trapdoor, ItemLike chestBoat, ItemLike boat, ItemLike hangingsign, ItemLike sign, ItemLike button, ItemLike pressurePlate, ItemLike planks, ItemLike wood, ItemLike log) {
        planksFromLog(pWriter, planks, log, 4);
        woodFromLogs(pWriter, wood, log);
        slab(pWriter, slab, planks);
        stairs(pWriter, stairs, planks);
        fence(pWriter, fence, planks);
        fenceGate(pWriter, fenceGate, planks);
        door(pWriter, door, planks);
        trapdoor(pWriter, trapdoor, planks);
        button(pWriter, button, planks);
        sign(pWriter, sign, planks);
        hangingSign(pWriter, hangingsign, planks);
        pressurePlate(pWriter, pressurePlate, planks);
        boat(pWriter, boat, planks);
        chestboat(pWriter, chestBoat, boat);
    }

    public static void spearRecipe(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike spear) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, spear).define('/', Items.STICK).define('X', material).pattern(" XX").pattern(" /X").pattern("/  ").unlockedBy(getHasName(spear), has(spear)).save(consumer, valoriaRecipeId(spear));
    }

    public static void spearRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> material, ItemLike spear) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, spear).define('/', Items.STICK).define('X', material).pattern(" XX").pattern(" /X").pattern("/  ").unlockedBy(getHasName(spear), has(spear)).save(consumer, valoriaRecipeId(spear));
    }

    public static void katanaRecipe(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike katana) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, katana).define('/', Items.STICK).define('X', material).pattern("  X").pattern(" X ").pattern("/  ").unlockedBy(getHasName(katana), has(katana)).save(consumer, valoriaRecipeId(katana));
    }

    public static void katanaRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> material, ItemLike katana) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, katana).define('/', Items.STICK).define('X', material).pattern("  X").pattern(" X ").pattern("/  ").unlockedBy(getHasName(katana), has(katana)).save(consumer, valoriaRecipeId(katana));
    }

    public static void scytheRecipe(Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike scythe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, scythe).define('/', Items.STICK).define('X', material).pattern("XXX").pattern("X/ ").pattern("/  ").unlockedBy(getHasName(scythe), has(scythe)).save(consumer, valoriaRecipeId(scythe));
    }

    public static void scytheRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> material, ItemLike scythe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, scythe).define('/', Items.STICK).define('X', material).pattern("XXX").pattern("X/ ").pattern("/  ").unlockedBy(getHasName(scythe), has(scythe)).save(consumer, valoriaRecipeId(scythe));
    }

    public static void bookshelfRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike bookshelf) {
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition("quark")).addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf).define('#', plank).define('B', Items.BOOK).pattern("###").pattern("BBB").pattern("###").unlockedBy(getHasName(plank), has(plank)).save(consumer1)).build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(bookshelf)));
    }

    public static void ladderRecipe(Consumer<FinishedRecipe> consumer, ItemLike plank, ItemLike ladder) {
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition("quark")).addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ladder, 4).define('#', Items.STICK).define('W', plank).pattern("# #").pattern("#W#").pattern("# #").unlockedBy(getHasName(plank), has(plank)).save(consumer1)).build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(ladder)));
    }

    public static void chestRecipes(Consumer<FinishedRecipe> consumer, Block pNormal, Block pTrapped, ItemLike planks, TagKey<Item> log) {
        String normalName = getItemName(pNormal);
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition("quark")).addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pNormal).define('#', planks).pattern("###").pattern("# #").pattern("###").unlockedBy(getHasName(planks), has(planks)).save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + normalName))).build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + normalName));
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition("quark")).addRecipe(consumer1 -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, pNormal, 4).define('#', log).pattern("###").pattern("# #").pattern("###").unlockedBy(getHasName(pNormal), has(pNormal)).save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + normalName + "_wood"))).build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + normalName + "_wood"));
        ConditionalRecipe.builder().addCondition(new ModLoadedCondition("quark")).addRecipe(consumer1 -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pTrapped).requires(pNormal).requires(Items.TRIPWIRE_HOOK).unlockedBy(getHasName(pNormal), has(pNormal)).save(consumer1, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pTrapped)))).build(consumer, new ResourceLocation(Valoria.ID, "crafting/" + getItemName(pTrapped)));
    }

    public static void cutterResultFromBase(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pMaterial, int pCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), RecipeCategory.BUILDING_BLOCKS, pResult, pCount).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer, new ResourceLocation(Valoria.ID, getConversionRecipeName(pResult, pMaterial) + "_stonecutting"));
    }

    private void foodCooking(Consumer<FinishedRecipe> consumer, ItemLike ingredient, ItemLike result) {
        String resultName = ForgeRegistries.ITEMS.getKey(result.asItem()).getPath();
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 100, RecipeSerializer.SMOKING_RECIPE).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(Valoria.ID, resultName + "_from_smoking"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(Valoria.ID, resultName + "_from_campfire_cooking"));
    }

    private void foodSmelting(Consumer<FinishedRecipe> consumer, ItemLike ingredient, ItemLike result, String recipeName) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), RecipeCategory.FOOD, result, 0.35F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(Valoria.ID, recipeName));
    }

    protected static void valoriaNetheriteSmithing(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item pIngredientItem, RecipeCategory pCategory, Item pResultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(pIngredientItem), Ingredient.of(Items.NETHERITE_INGOT), pCategory, pResultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(pFinishedRecipeConsumer, new ResourceLocation(Valoria.ID, getItemName(pResultItem) + "_smithing"));
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        generateTools(pWriter, ItemsRegistry.bronzeIngot.get(), Items.STICK, ItemsRegistry.bronzeSword.get(), null, null, null, null);
        generateIngotBlockNugget(pWriter, BlockRegistry.bronzeBlock.get(), ItemsRegistry.bronzeIngot.get(), ItemsRegistry.bronzeNugget.get());
        generateArmor(pWriter, ItemsRegistry.cobaltIngot.get(), ItemsRegistry.cobaltHelmet.get(), ItemsRegistry.cobaltChestplate.get(), ItemsRegistry.cobaltLeggings.get(), ItemsRegistry.cobaltBoots.get());
        generateTools(pWriter, ItemsRegistry.cobaltIngot.get(), Items.STICK, ItemsRegistry.cobaltSword.get(), ItemsRegistry.cobaltPickaxe.get(), ItemsRegistry.cobaltAxe.get(), ItemsRegistry.cobaltShovel.get(), ItemsRegistry.cobaltHoe.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.cobaltBlock.get(), ItemsRegistry.cobaltIngot.get(), ItemsRegistry.cobaltNugget.get());
        generateArmor(pWriter, ItemsRegistry.blackGold.get(), ItemsRegistry.blackGoldHelmet.get(), ItemsRegistry.blackGoldChestplate.get(), ItemsRegistry.blackGoldLeggings.get(), ItemsRegistry.blackGoldBoots.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.blackGoldBlock.get(), ItemsRegistry.blackGold.get(), ItemsRegistry.blackGoldNugget.get());
        generateTools(pWriter, ItemsRegistry.natureIngot.get(), Items.STICK, null, ItemsRegistry.naturePickaxe.get(), ItemsRegistry.natureAxe.get(), ItemsRegistry.natureShovel.get(), ItemsRegistry.natureHoe.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.natureBlock.get(), ItemsRegistry.natureIngot.get(), null);
        generateTools(pWriter, ItemsRegistry.aquariusIngot.get(), Items.STICK, null, ItemsRegistry.aquariusPickaxe.get(), ItemsRegistry.aquariusAxe.get(), ItemsRegistry.aquariusShovel.get(), ItemsRegistry.aquariusHoe.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.aquariusBlock.get(), ItemsRegistry.aquariusIngot.get(), null);
        generateArmor(pWriter, ItemsRegistry.infernalIngot.get(), ItemsRegistry.infernalHelmet.get(), ItemsRegistry.infernalChestplate.get(), ItemsRegistry.infernalLeggings.get(), ItemsRegistry.infernalBoots.get());
        generateTools(pWriter, ItemsRegistry.infernalIngot.get(), Items.STICK, ItemsRegistry.infernalSword.get(), ItemsRegistry.infernalPickaxe.get(), ItemsRegistry.infernalAxe.get(), ItemsRegistry.infernalShovel.get(), ItemsRegistry.infernalHoe.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.infernalBlock.get(), ItemsRegistry.infernalIngot.get(), null);
        generateTools(pWriter, ItemsRegistry.voidIngot.get(), Items.STICK, null, ItemsRegistry.voidPickaxe.get(), ItemsRegistry.voidAxe.get(), ItemsRegistry.voidShovel.get(), ItemsRegistry.voidHoe.get());
        generateTools(pWriter, ItemsRegistry.jade.get(), Items.STICK, ItemsRegistry.jadeSword.get(), ItemsRegistry.jadePickaxe.get(), ItemsRegistry.jadeAxe.get(), ItemsRegistry.jadeShovel.get(), ItemsRegistry.jadeHoe.get());
        generateArmor(pWriter, ItemsRegistry.pyratite.get(), ItemsRegistry.pyratiteHelmet.get(), ItemsRegistry.pyratiteChestplate.get(), ItemsRegistry.pyratiteLeggings.get(), ItemsRegistry.pyratiteBoots.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.pyratiteBlock.get(), ItemsRegistry.pyratite.get(), null);
        generateTools(pWriter, ItemsRegistry.pearliumIngot.get(), Items.STICK, ItemsRegistry.pearliumSword.get(), ItemsRegistry.pearliumPickaxe.get(), ItemsRegistry.pearliumAxe.get(), null, null);
        generateIngotBlockNugget(pWriter, BlockRegistry.pearlium.get(), ItemsRegistry.pearliumIngot.get(), null);
        generateArmor(pWriter, ItemsRegistry.crimtaneIngot.get(), ItemsRegistry.crimtaneHelmet.get(), ItemsRegistry.crimtaneChestplate.get(), ItemsRegistry.crimtaneLeggings.get(), ItemsRegistry.crimtaneBoots.get());
        generateTools(pWriter, ItemsRegistry.crimtaneIngot.get(), Items.STICK, ItemsRegistry.crimtaneSword.get(), ItemsRegistry.crimtanePickaxe.get(), ItemsRegistry.crimtaneAxe.get(), ItemsRegistry.crimtaneShovel.get(), ItemsRegistry.crimtaneHoe.get());
        generateIngotBlockNugget(pWriter, BlockRegistry.crimtaneBlock.get(), ItemsRegistry.crimtaneIngot.get(), null);

        java.util.Set<net.minecraft.resources.ResourceLocation> seenRecipeIds = new java.util.HashSet<>();
        Consumer<FinishedRecipe> origWriter = pWriter;
        Consumer<FinishedRecipe> deduplicatingWriter = finishedRecipe -> {
            if (seenRecipeIds.add(finishedRecipe.getId())) {
                origWriter.accept(finishedRecipe);
            }
        };
        pWriter = deduplicatingWriter;

        buildManualRecipes(pWriter);

        // ===== FOOD =====
        foodCooking(pWriter, BlockRegistry.abyssalGlowfern.get(), ItemsRegistry.cookedAbyssalGlowfern.get());
        foodCooking(pWriter, ItemsRegistry.crabLeg.get(), ItemsRegistry.cookedCrablLeg.get());
        foodCooking(pWriter, ItemsRegistry.devilMeat.get(), ItemsRegistry.cookedDevilMeat.get());
        foodCooking(pWriter, BlockRegistry.glowVioletSprout.get(), ItemsRegistry.cookedGlowVioletSprout.get());
        foodCooking(pWriter, ItemsRegistry.goblinMeat.get(), ItemsRegistry.cookedGoblinMeat.get());
        foodCooking(pWriter, ItemsRegistry.scavengerMeat.get(), ItemsRegistry.scavengerCookedMeat.get());

        foodSmelting(pWriter, BlockRegistry.abyssalGlowfern.get(), ItemsRegistry.cookedAbyssalGlowfern.get(), "cooked_abyssal_glowfern_from_smelting");
        foodSmelting(pWriter, ItemsRegistry.crabLeg.get(), ItemsRegistry.cookedCrablLeg.get(), "cooked_crab_leg");
        foodSmelting(pWriter, ItemsRegistry.devilMeat.get(), ItemsRegistry.cookedDevilMeat.get(), "cooked_devil_meat");
        foodSmelting(pWriter, BlockRegistry.glowVioletSprout.get(), ItemsRegistry.cookedGlowVioletSprout.get(), "cooked_glow_violet_sprout_from_smelting");
        foodSmelting(pWriter, ItemsRegistry.goblinMeat.get(), ItemsRegistry.cookedGoblinMeat.get(), "cooked_goblin_meat");
        foodSmelting(pWriter, ItemsRegistry.scavengerMeat.get(), ItemsRegistry.scavengerCookedMeat.get(), "cooked_scavenger_meat");

        // ===== TOOLS =====
        spearRecipe(pWriter, ItemTags.LOGS, ItemsRegistry.woodenSpear.get());
        spearRecipe(pWriter, ItemTags.STONE_TOOL_MATERIALS, ItemsRegistry.stoneSpear.get());
        spearRecipe(pWriter, Items.IRON_INGOT, ItemsRegistry.ironSpear.get());
        spearRecipe(pWriter, Items.GOLD_INGOT, ItemsRegistry.goldenSpear.get());
        spearRecipe(pWriter, Items.DIAMOND, ItemsRegistry.diamondSpear.get());
        spearRecipe(pWriter, ItemsRegistry.etherealShard.get(), ItemsRegistry.etherealSpear.get());

        katanaRecipe(pWriter, Items.IRON_INGOT, ItemsRegistry.ironKatana.get());
        katanaRecipe(pWriter, Items.GOLD_INGOT, ItemsRegistry.goldenKatana.get());
        katanaRecipe(pWriter, Items.DIAMOND, ItemsRegistry.diamondKatana.get());
        katanaRecipe(pWriter, ItemsRegistry.holidayCandy.get(), ItemsRegistry.holidayKatana.get());

        scytheRecipe(pWriter, Items.IRON_INGOT, ItemsRegistry.ironScythe.get());
        scytheRecipe(pWriter, Items.GOLD_INGOT, ItemsRegistry.goldenScythe.get());
        scytheRecipe(pWriter, Items.DIAMOND, ItemsRegistry.diamondScythe.get());
        scytheRecipe(pWriter, ItemsRegistry.crimtaneIngot.get(), ItemsRegistry.crimtaneScythe.get());

        // ===== NETHERITE SMITHING =====
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.diamondSpear.get(), RecipeCategory.MISC, ItemsRegistry.netheriteSpear.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.diamondKatana.get(), RecipeCategory.MISC, ItemsRegistry.netheriteKatana.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.diamondScythe.get(), RecipeCategory.MISC, ItemsRegistry.netheriteScythe.get());

        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceAmber.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceAmber.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceDiamond.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceDiamond.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceEmerald.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceEmerald.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceRuby.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceRuby.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenNecklaceSapphire.get(), RecipeCategory.MISC, ItemsRegistry.netheriteNecklaceSapphire.get());

        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRingAmber.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingAmber.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRingDiamond.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingDiamond.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRingEmerald.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingEmerald.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRingRuby.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingRuby.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRingSapphire.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRingSapphire.get());

        valoriaNetheriteSmithing(pWriter, ItemsRegistry.diamondRapier.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRapier.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenChain.get(), RecipeCategory.MISC, ItemsRegistry.netheriteChain.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.diamondGloves.get(), RecipeCategory.MISC, ItemsRegistry.netheriteGloves.get());
        valoriaNetheriteSmithing(pWriter, ItemsRegistry.goldenRing.get(), RecipeCategory.MISC, ItemsRegistry.netheriteRing.get());

        stainedGlassPaneFromStainedGlass(pWriter, BlockRegistry.bronzeGlassPane.get(), BlockRegistry.bronzeGlass.get());

        // ===== WOOD SETS AUTOMATION =====
        registerWoodset(pWriter, BlockRegistry.dreadwoodPlanksSlab.get(), BlockRegistry.dreadwoodPlanksStairs.get(), BlockRegistry.dreadwoodFence.get(), BlockRegistry.dreadwoodFenceGate.get(), BlockRegistry.dreadwoodDoor.get(), BlockRegistry.dreadwoodTrapdoor.get(), ItemsRegistry.dreadwoodChestBoat.get(), ItemsRegistry.dreadwoodBoat.get(), BlockRegistry.dreadwoodHangingSign.get(), BlockRegistry.dreadwoodSign.get(), BlockRegistry.dreadwoodButton.get(), BlockRegistry.dreadwoodPressurePlate.get(), BlockRegistry.dreadwoodPlanks.get(), BlockRegistry.dreadWood.get(), BlockRegistry.dreadwoodLog.get());
        registerWoodset(pWriter, BlockRegistry.eldritchPlanksSlab.get(), BlockRegistry.eldritchPlanksStairs.get(), BlockRegistry.eldritchFence.get(), BlockRegistry.eldritchFenceGate.get(), BlockRegistry.eldritchDoor.get(), BlockRegistry.eldritchTrapdoor.get(), ItemsRegistry.eldritchChestBoat.get(), ItemsRegistry.eldritchBoat.get(), BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchSign.get(), BlockRegistry.eldritchButton.get(), BlockRegistry.eldritchPressurePlate.get(), BlockRegistry.eldritchPlanks.get(), BlockRegistry.eldritchWood.get(), BlockRegistry.eldritchLog.get());
        registerWoodset(pWriter, BlockRegistry.shadePlanksSlab.get(), BlockRegistry.shadePlanksStairs.get(), BlockRegistry.shadeFence.get(), BlockRegistry.shadeFenceGate.get(), BlockRegistry.shadeDoor.get(), BlockRegistry.shadeTrapdoor.get(), ItemsRegistry.shadeChestBoat.get(), ItemsRegistry.shadeBoat.get(), BlockRegistry.shadeHangingSign.get(), BlockRegistry.shadeSign.get(), BlockRegistry.shadeButton.get(), BlockRegistry.shadePressurePlate.get(), BlockRegistry.shadePlanks.get(), BlockRegistry.shadeWood.get(), BlockRegistry.shadeLog.get());

        // ===== SLABS, STAIRS, WALLS, POLISHED (Helpers) =====

        // Ash
        slab(pWriter, BlockRegistry.ashSlab.get(), BlockRegistry.ash.get());
        stairs(pWriter, BlockRegistry.ashStairs.get(), BlockRegistry.ash.get());
        wall(pWriter, BlockRegistry.ashWall.get(), BlockRegistry.ash.get());

        slab(pWriter, BlockRegistry.polishedAshSlab.get(), BlockRegistry.polishedAsh.get());
        stairs(pWriter, BlockRegistry.polishedAshStairs.get(), BlockRegistry.polishedAsh.get());
        wall(pWriter, BlockRegistry.polishedAshWall.get(), BlockRegistry.polishedAsh.get());
        polished(pWriter, BlockRegistry.smoothAsh.get(), BlockRegistry.polishedAsh.get());

        slab(pWriter, BlockRegistry.smoothAshSlab.get(), BlockRegistry.smoothAsh.get());
        stairs(pWriter, BlockRegistry.smoothAshStairs.get(), BlockRegistry.smoothAsh.get());
        wall(pWriter, BlockRegistry.smoothAshWall.get(), BlockRegistry.smoothAsh.get());

        slab(pWriter, BlockRegistry.ashBricksSlab.get(), BlockRegistry.ashBricks.get());
        stairs(pWriter, BlockRegistry.ashBricksStairs.get(), BlockRegistry.ashBricks.get());
        wall(pWriter, BlockRegistry.ashBricksWall.get(), BlockRegistry.ashBricks.get());

        // Ambane Stone
        slab(pWriter, BlockRegistry.ambaneStoneSlab.get(), BlockRegistry.ambaneStone.get());
        stairs(pWriter, BlockRegistry.ambaneStoneStairs.get(), BlockRegistry.ambaneStone.get());
        wall(pWriter, BlockRegistry.ambaneStoneWall.get(), BlockRegistry.ambaneStone.get());
        polished(pWriter, BlockRegistry.polishedAmbaneStone.get(), BlockRegistry.ambaneStone.get());

        slab(pWriter, BlockRegistry.ambaneStoneBricksSlab.get(), BlockRegistry.ambaneStoneBricks.get());
        stairs(pWriter, BlockRegistry.ambaneStoneBricksStairs.get(), BlockRegistry.ambaneStoneBricks.get());
        wall(pWriter, BlockRegistry.ambaneStoneBricksWall.get(), BlockRegistry.ambaneStoneBricks.get());
        polished(pWriter, BlockRegistry.ambaneStoneBricks.get(), ItemsRegistry.ambaneStoneBrick.get());

        // Ancient Stone
        slab(pWriter, BlockRegistry.ancientStoneSlab.get(), BlockRegistry.ancientStone.get());
        stairs(pWriter, BlockRegistry.ancientStoneStairs.get(), BlockRegistry.ancientStone.get());
        wall(pWriter, BlockRegistry.ancientStoneWall.get(), BlockRegistry.ancientStone.get());
        polished(pWriter, BlockRegistry.polishedAncientStone.get(), BlockRegistry.ancientStone.get());

        slab(pWriter, BlockRegistry.polishedAncientStoneSlab.get(), BlockRegistry.polishedAncientStone.get());
        stairs(pWriter, BlockRegistry.polishedAncientStoneStairs.get(), BlockRegistry.polishedAncientStone.get());
        wall(pWriter, BlockRegistry.polishedAncientStoneWall.get(), BlockRegistry.polishedAncientStone.get());

        // Bronze Block
        slab(pWriter, BlockRegistry.bronzeBlockSlab.get(), BlockRegistry.bronzeBlock.get());
        stairs(pWriter, BlockRegistry.bronzeBlockStairs.get(), BlockRegistry.bronzeBlock.get());
        slab(pWriter, BlockRegistry.cutBronzeSlab.get(), BlockRegistry.cutBronze.get());
        stairs(pWriter, BlockRegistry.cutBronzeStairs.get(), BlockRegistry.cutBronze.get());

        // Crystal Stone
        wall(pWriter, BlockRegistry.crystalStoneWall.get(), BlockRegistry.crystalStone.get());
        polished(pWriter, BlockRegistry.polishedCrystalStone.get(), BlockRegistry.crystalStone.get());
        wall(pWriter, BlockRegistry.crystalStoneBricksWall.get(), BlockRegistry.crystalStoneBricks.get());
        polished(pWriter, BlockRegistry.crystalStoneBricks.get(), ItemsRegistry.crystalStoneBrick.get());

        // Deep Marble
        slab(pWriter, BlockRegistry.deepMarbleSlab.get(), BlockRegistry.deepMarble.get());
        stairs(pWriter, BlockRegistry.deepMarbleStairs.get(), BlockRegistry.deepMarble.get());
        polished(pWriter, BlockRegistry.polishedDeepMarble.get(), BlockRegistry.deepMarble.get());
        slab(pWriter, BlockRegistry.polishedDeepMarbleSlab.get(), BlockRegistry.polishedDeepMarble.get());
        stairs(pWriter, BlockRegistry.polishedDeepMarbleStairs.get(), BlockRegistry.polishedDeepMarble.get());

        // Dunestone
        slab(pWriter, BlockRegistry.dunestoneSlab.get(), BlockRegistry.dunestone.get());
        stairs(pWriter, BlockRegistry.dunestoneStairs.get(), BlockRegistry.dunestone.get());
        wall(pWriter, BlockRegistry.dunestoneWall.get(), BlockRegistry.dunestone.get());
        polished(pWriter, BlockRegistry.polishedDunestone.get(), BlockRegistry.dunestone.get());

        slab(pWriter, BlockRegistry.dunestoneBricksSlab.get(), BlockRegistry.dunestoneBricks.get());
        stairs(pWriter, BlockRegistry.dunestoneBricksStairs.get(), BlockRegistry.dunestoneBricks.get());
        wall(pWriter, BlockRegistry.dunestoneBricksWall.get(), BlockRegistry.dunestoneBricks.get());
        polished(pWriter, BlockRegistry.dunestoneBricks.get(), ItemsRegistry.dunestoneBrick.get());

        // Ephemarite
        slab(pWriter, BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemarite.get());
        stairs(pWriter, BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemarite.get());
        wall(pWriter, BlockRegistry.ephemariteWall.get(), BlockRegistry.ephemarite.get());

        slab(pWriter, BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLow.get());
        stairs(pWriter, BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLow.get());
        wall(pWriter, BlockRegistry.ephemariteLowWall.get(), BlockRegistry.ephemariteLow.get());

        slab(pWriter, BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemarite.get());
        stairs(pWriter, BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemarite.get());
        slab(pWriter, BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLow.get());
        stairs(pWriter, BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLow.get());

        // Limestone
        slab(pWriter, BlockRegistry.limestoneSlab.get(), BlockRegistry.limestone.get());
        stairs(pWriter, BlockRegistry.limestoneStairs.get(), BlockRegistry.limestone.get());
        wall(pWriter, BlockRegistry.limestoneWall.get(), BlockRegistry.limestone.get());
        polished(pWriter, BlockRegistry.polishedLimestone.get(), BlockRegistry.limestone.get());

        slab(pWriter, BlockRegistry.polishedLimestoneSlab.get(), BlockRegistry.polishedLimestone.get());
        stairs(pWriter, BlockRegistry.polishedLimestoneStairs.get(), BlockRegistry.polishedLimestone.get());

        slab(pWriter, BlockRegistry.cutLimestoneSlab.get(), BlockRegistry.cutLimestone.get());
        stairs(pWriter, BlockRegistry.cutLimestoneStairs.get(), BlockRegistry.cutLimestone.get());

        slab(pWriter, BlockRegistry.limestoneBricksSlab.get(), BlockRegistry.limestoneBricks.get());
        stairs(pWriter, BlockRegistry.limestoneBricksStairs.get(), BlockRegistry.limestoneBricks.get());
        wall(pWriter, BlockRegistry.limestoneBricksWall.get(), BlockRegistry.limestoneBricks.get());
        polished(pWriter, BlockRegistry.limestoneBricks.get(), ItemsRegistry.limestoneBrick.get());

        slab(pWriter, BlockRegistry.crackedLimestoneBricksSlab.get(), BlockRegistry.crackedLimestoneBricks.get());
        stairs(pWriter, BlockRegistry.crackedLimestoneBricksStairs.get(), BlockRegistry.crackedLimestoneBricks.get());
        wall(pWriter, BlockRegistry.crackedLimestoneBricksWall.get(), BlockRegistry.crackedLimestoneBricks.get());

        // Tombstone
        slab(pWriter, BlockRegistry.tombstoneSlab.get(), BlockRegistry.tombstone.get());
        stairs(pWriter, BlockRegistry.tombstoneStairs.get(), BlockRegistry.tombstone.get());
        wall(pWriter, BlockRegistry.tombstoneWall.get(), BlockRegistry.tombstone.get());
        polished(pWriter, BlockRegistry.polishedTombstone.get(), BlockRegistry.tombstone.get());

        slab(pWriter, BlockRegistry.cutTombstoneSlab.get(), BlockRegistry.cutTombstone.get());
        stairs(pWriter, BlockRegistry.cutTombstoneStairs.get(), BlockRegistry.cutTombstone.get());

        slab(pWriter, BlockRegistry.tombstoneBricksSlab.get(), BlockRegistry.tombstoneBricks.get());
        stairs(pWriter, BlockRegistry.tombstoneBricksStairs.get(), BlockRegistry.tombstoneBricks.get());
        wall(pWriter, BlockRegistry.tombstoneBricksWall.get(), BlockRegistry.tombstoneBricks.get());
        polished(pWriter, BlockRegistry.tombstoneBricks.get(), ItemsRegistry.tombstoneBrick.get());

        wall(pWriter, BlockRegistry.crackedTombstoneBricksWall.get(), BlockRegistry.crackedTombstoneBricks.get());

        slab(pWriter, BlockRegistry.mossyTombstoneSlab.get(), BlockRegistry.mossyTombstone.get());
        stairs(pWriter, BlockRegistry.mossyTombstoneStairs.get(), BlockRegistry.mossyTombstone.get());
        wall(pWriter, BlockRegistry.mossyTombstoneWall.get(), BlockRegistry.mossyTombstone.get());

        slab(pWriter, BlockRegistry.mossyTombstoneBricksSlab.get(), BlockRegistry.mossyTombstoneBricks.get());
        stairs(pWriter, BlockRegistry.mossyTombstoneBricksStairs.get(), BlockRegistry.mossyTombstoneBricks.get());
        wall(pWriter, BlockRegistry.mossyTombstoneBricksWall.get(), BlockRegistry.mossyTombstoneBricks.get());

        // Void Blocks
        slab(pWriter, BlockRegistry.voidStoneSlab.get(), BlockRegistry.voidStone.get());
        stairs(pWriter, BlockRegistry.voidStoneStairs.get(), BlockRegistry.voidStone.get());
        wall(pWriter, BlockRegistry.voidStoneWall.get(), BlockRegistry.voidStone.get());
        polished(pWriter, BlockRegistry.polishedVoidStone.get(), BlockRegistry.voidStone.get());

        slab(pWriter, BlockRegistry.voidBrickSlab.get(), BlockRegistry.voidBrick.get());
        stairs(pWriter, BlockRegistry.voidBrickStairs.get(), BlockRegistry.voidBrick.get());
        wall(pWriter, BlockRegistry.voidBrickWall.get(), BlockRegistry.voidBrick.get());
        polished(pWriter, BlockRegistry.voidBrick.get(), ItemsRegistry.voidStoneBrick.get());

        slab(pWriter, BlockRegistry.voidCrackedBrickSlab.get(), BlockRegistry.voidCrackedBrick.get());
        stairs(pWriter, BlockRegistry.voidCrackedBrickStairs.get(), BlockRegistry.voidCrackedBrick.get());
        wall(pWriter, BlockRegistry.voidCrackedBrickWall.get(), BlockRegistry.voidCrackedBrick.get());

        slab(pWriter, BlockRegistry.voidSandstoneSlab.get(), BlockRegistry.voidSandstone.get());
        stairs(pWriter, BlockRegistry.voidSandstoneStairs.get(), BlockRegistry.voidSandstone.get());
        wall(pWriter, BlockRegistry.voidSandstoneWall.get(), BlockRegistry.voidSandstone.get());

        slab(pWriter, BlockRegistry.voidCutSandstoneSlab.get(), BlockRegistry.voidCutSandstone.get());
        polished(pWriter, BlockRegistry.voidCutSandstone.get(), BlockRegistry.voidSandstone.get());

        slab(pWriter, BlockRegistry.smoothVoidSandstoneSlab.get(), BlockRegistry.smoothVoidSandstone.get());
        stairs(pWriter, BlockRegistry.smoothVoidSandstoneStairs.get(), BlockRegistry.smoothVoidSandstone.get());

        // ===== STONECUTTING =====
        cutterResultFromBase(pWriter, BlockRegistry.polishedAmbaneStone.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneStairs.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneSlab.get(), BlockRegistry.ambaneStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneWall.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneBricks.get(), BlockRegistry.ambaneStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutAmbaneStone.get(), BlockRegistry.ambaneStone.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneBricksStairs.get(), BlockRegistry.ambaneStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneBricksSlab.get(), BlockRegistry.ambaneStoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.ambaneStoneBricksWall.get(), BlockRegistry.ambaneStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.chiseledAmbaneStoneBricks.get(), BlockRegistry.ambaneStoneBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedAmbaneStoneSlab.get(), BlockRegistry.polishedAmbaneStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAmbaneStoneStairs.get(), BlockRegistry.polishedAmbaneStone.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.ephemariteSlab.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemariteStairs.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemariteWall.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemariteLow.get(), BlockRegistry.ephemarite.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.ephemariteLowSlab.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemariteLowStairs.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemariteLowWall.get(), BlockRegistry.ephemariteLow.get(), 1);

        // Остальной камнерез
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemarite.get(), BlockRegistry.ephemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteSlab.get(), BlockRegistry.polishedEphemarite.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteStairs.get(), BlockRegistry.polishedEphemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteWall.get(), BlockRegistry.polishedEphemarite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteLow.get(), BlockRegistry.ephemariteLow.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteLowSlab.get(), BlockRegistry.polishedEphemariteLow.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteLowStairs.get(), BlockRegistry.polishedEphemariteLow.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedEphemariteLowWall.get(), BlockRegistry.polishedEphemariteLow.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.picriteBricks.get(), BlockRegistry.picrite.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.picriteBricksSlab.get(), BlockRegistry.picriteBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.picriteBricksStairs.get(), BlockRegistry.picriteBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.picriteBricksWall.get(), BlockRegistry.picriteBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedDunestone.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneStairs.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneSlab.get(), BlockRegistry.dunestone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneWall.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneBricks.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutDunestone.get(), BlockRegistry.dunestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneBricksStairs.get(), BlockRegistry.dunestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneBricksSlab.get(), BlockRegistry.dunestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.dunestoneBricksWall.get(), BlockRegistry.dunestoneBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.ashTiles.get(), BlockRegistry.ash.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ashBricks.get(), BlockRegistry.ash.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ashBricksSlab.get(), BlockRegistry.ashBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.ashBricksStairs.get(), BlockRegistry.ashBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ashBricksWall.get(), BlockRegistry.ashBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedAsh.get(), BlockRegistry.ash.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshSlab.get(), BlockRegistry.ashBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshStairs.get(), BlockRegistry.ashBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshWall.get(), BlockRegistry.ashBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshBricks.get(), BlockRegistry.ashBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshBricks.get(), BlockRegistry.polishedAsh.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshBricksSlab.get(), BlockRegistry.polishedAshBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshBricksStairs.get(), BlockRegistry.polishedAshBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAshTiles.get(), BlockRegistry.polishedAsh.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.smoothAsh.get(), BlockRegistry.polishedAsh.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.smoothAshSlab.get(), BlockRegistry.smoothAsh.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.smoothAshStairs.get(), BlockRegistry.smoothAsh.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.smoothAshWall.get(), BlockRegistry.smoothAsh.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedLimestone.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneStairs.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneSlab.get(), BlockRegistry.limestone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneWall.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneBricks.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutLimestone.get(), BlockRegistry.limestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneBricksStairs.get(), BlockRegistry.limestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneBricksSlab.get(), BlockRegistry.limestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.limestoneBricksWall.get(), BlockRegistry.limestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crackedLimestoneBricks.get(), BlockRegistry.limestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crackedLimestoneBricksSlab.get(), BlockRegistry.crackedLimestoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.crackedLimestoneBricksWall.get(), BlockRegistry.crackedLimestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crackedLimestoneBricksStairs.get(), BlockRegistry.crackedLimestoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutLimestoneSlab.get(), BlockRegistry.cutLimestone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.cutLimestoneStairs.get(), BlockRegistry.cutLimestone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedLimestoneSlab.get(), BlockRegistry.polishedLimestone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedLimestoneStairs.get(), BlockRegistry.polishedLimestone.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedCrystalStone.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneStairs.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneSlab.get(), BlockRegistry.crystalStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneWall.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneBricks.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutCrystalStone.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStonePillar.get(), BlockRegistry.crystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutPolishedCrystalStone.get(), BlockRegistry.polishedCrystalStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneBricksStairs.get(), BlockRegistry.crystalStoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneBricksSlab.get(), BlockRegistry.crystalStoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.crystalStoneBricksWall.get(), BlockRegistry.crystalStoneBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedAncientStone.get(), BlockRegistry.ancientStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ancientStoneStairs.get(), BlockRegistry.ancientStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ancientStoneSlab.get(), BlockRegistry.ancientStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.ancientStoneWall.get(), BlockRegistry.ancientStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAncientStoneStairs.get(), BlockRegistry.polishedAncientStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAncientStoneSlab.get(), BlockRegistry.polishedAncientStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.polishedAncientStoneWall.get(), BlockRegistry.polishedAncientStone.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedTombstone.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneStairs.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneSlab.get(), BlockRegistry.tombstone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneWall.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneBricks.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutTombstone.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstonePillar.get(), BlockRegistry.tombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.cutTombstonePillar.get(), BlockRegistry.tombstonePillar.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneBricksStairs.get(), BlockRegistry.tombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneBricksSlab.get(), BlockRegistry.tombstoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.tombstoneBricksWall.get(), BlockRegistry.tombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.crackedTombstoneBricks.get(), BlockRegistry.tombstoneBricks.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.polishedVoidStone.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidStoneStairs.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidStoneSlab.get(), BlockRegistry.voidStone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.voidStoneWall.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidBrick.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidPillar.get(), BlockRegistry.voidStone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidBrickStairs.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidBrickSlab.get(), BlockRegistry.voidBrick.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.voidBrickWall.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidCrackedBrick.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBrick.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBricks.get(), BlockRegistry.voidBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidCrackedBrickStairs.get(), BlockRegistry.voidCrackedBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidCrackedBrickSlab.get(), BlockRegistry.voidCrackedBrick.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.voidCrackedBrickWall.get(), BlockRegistry.voidCrackedBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBricksStairs.get(), BlockRegistry.voidChiseledBrick.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBricksSlab.get(), BlockRegistry.voidChiseledBrick.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBricksStairs.get(), BlockRegistry.voidChiseledBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledBricksSlab.get(), BlockRegistry.voidChiseledBricks.get(), 2);

        cutterResultFromBase(pWriter, BlockRegistry.cutBronze.get(), BlockRegistry.bronzeBlock.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.deepMarbleSlab.get(), BlockRegistry.deepMarble.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.deepMarbleStairs.get(), BlockRegistry.deepMarble.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.ephemarite.get(), BlockRegistry.ephemariteLow.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneBricksSlab.get(), BlockRegistry.mossyTombstoneBricks.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneBricksStairs.get(), BlockRegistry.mossyTombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneBricksWall.get(), BlockRegistry.mossyTombstoneBricks.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneSlab.get(), BlockRegistry.mossyTombstone.get(), 2);
        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneStairs.get(), BlockRegistry.mossyTombstone.get(), 1);
        cutterResultFromBase(pWriter, BlockRegistry.mossyTombstoneWall.get(), BlockRegistry.mossyTombstone.get(), 1);

        cutterResultFromBase(pWriter, BlockRegistry.voidChiseledSandstone.get(), BlockRegistry.voidSandstone.get(), 1);

        // ===== SMELTING / BLASTING =====
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.amberOre.get()), RecipeCategory.MISC, ItemsRegistry.amberGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.amberOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "amber_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.wickedAmethystOre.get()), RecipeCategory.MISC, ItemsRegistry.wickedAmethyst.get(), 1F, 230, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.wickedAmethystOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "amethyst_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.blackGoldHelmet.get()), RecipeCategory.FOOD, ItemsRegistry.blackGoldNugget.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.blackGoldHelmet.get())).save(pWriter, new ResourceLocation(Valoria.ID, "black_gold_nugget_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.bronzeSword.get()), RecipeCategory.FOOD, ItemsRegistry.bronzeNugget.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.bronzeSword.get())).save(pWriter, new ResourceLocation(Valoria.ID, "bronze_nugget_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateCobaltOre.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateCobaltOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_from_deepslate_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.cobaltOre.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.cobaltOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.rawCobalt.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 0.7F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.rawCobalt.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_ingot_from_smelting_raw_cobalt"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.cobaltPickaxe.get()), RecipeCategory.FOOD, ItemsRegistry.cobaltNugget.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.cobaltPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_nugget_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.limestoneBricks.get()), RecipeCategory.MISC, BlockRegistry.crackedLimestoneBricks.get(), 0.3F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.limestoneBricks.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cracked_limestone_bricks"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.tombstoneBricks.get()), RecipeCategory.MISC, BlockRegistry.crackedTombstoneBricks.get(), 0.3F, 140, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.tombstoneBricks.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cracked_tombstone_bricks"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.crystalStone.get()), RecipeCategory.FOOD, ItemsRegistry.crystalStoneBrick.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.crystalStone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "crystal_stone_brick"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateAmberOre.get()), RecipeCategory.MISC, ItemsRegistry.amberGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateAmberOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_amber_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateRubyOre.get()), RecipeCategory.MISC, ItemsRegistry.rubyGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateRubyOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_ruby_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateSapphireOre.get()), RecipeCategory.MISC, ItemsRegistry.sapphireGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateSapphireOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_sapphire_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.dormantCrystals.get()), RecipeCategory.MISC, ItemsRegistry.unchargedShard.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.dormantCrystals.get())).save(pWriter, new ResourceLocation(Valoria.ID, "dormant_crystals_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.dunestone.get()), RecipeCategory.FOOD, ItemsRegistry.dunestoneBrick.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.dunestone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "dunestone_brick"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.picriteJadeOre.get()), RecipeCategory.MISC, ItemsRegistry.jade.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.picriteJadeOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "jade_from_picrite_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.jadeOre.get()), RecipeCategory.MISC, ItemsRegistry.jade.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.jadeOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "jade_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.limestone.get()), RecipeCategory.FOOD, ItemsRegistry.limestoneBrick.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.limestone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "limestone_brick"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.pyratiteOre.get()), RecipeCategory.MISC, ItemsRegistry.pyratite.get(), 1.0F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.pyratiteOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "pyratite_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.rubyOre.get()), RecipeCategory.MISC, ItemsRegistry.rubyGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.rubyOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "ruby_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.sapphireOre.get()), RecipeCategory.MISC, ItemsRegistry.sapphireGem.get(), 0.3F, 120, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.sapphireOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "sapphire_gem_from_smelting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.tombstone.get()), RecipeCategory.FOOD, ItemsRegistry.tombstoneBrick.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.tombstone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "tombstone_brick"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.voidBrick.get()), RecipeCategory.MISC, BlockRegistry.voidCrackedBrick.get(), 0.3F, 140, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.voidBrick.get())).save(pWriter, new ResourceLocation(Valoria.ID, "void_cracked_brick"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.voidSandstone.get()), RecipeCategory.FOOD, BlockRegistry.smoothVoidSandstone.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.voidSandstone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "void_smooth_sandstone"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.voidStone.get()), RecipeCategory.FOOD, ItemsRegistry.voidStoneBrick.get(), 0.1F, 200, RecipeSerializer.SMELTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.voidStone.get())).save(pWriter, new ResourceLocation(Valoria.ID, "void_stone_brick"));

        // Blasting
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.amberOre.get()), RecipeCategory.MISC, ItemsRegistry.amberGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.amberOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "amber_gem_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.wickedAmethystOre.get()), RecipeCategory.MISC, ItemsRegistry.wickedAmethyst.get(), 1F, 160, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.wickedAmethystOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "amethyst_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.blackGoldHelmet.get()), RecipeCategory.FOOD, ItemsRegistry.blackGoldNugget.get(), 0.1F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.blackGoldHelmet.get())).save(pWriter, new ResourceLocation(Valoria.ID, "black_gold_nugget_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.bronzeSword.get()), RecipeCategory.FOOD, ItemsRegistry.bronzeNugget.get(), 0.1F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.bronzeSword.get())).save(pWriter, new ResourceLocation(Valoria.ID, "bronze_nugget_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.cobaltOre.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.cobaltOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateCobaltOre.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateCobaltOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_from_deepslate_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.rawCobalt.get()), RecipeCategory.MISC, ItemsRegistry.cobaltIngot.get(), 0.7F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.rawCobalt.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_ingot_from_blasting_raw_cobalt"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemsRegistry.cobaltPickaxe.get()), RecipeCategory.FOOD, ItemsRegistry.cobaltNugget.get(), 0.1F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(ItemsRegistry.cobaltPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_nugget_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateAmberOre.get()), RecipeCategory.MISC, ItemsRegistry.amberGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateAmberOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_amber_gem_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateRubyOre.get()), RecipeCategory.MISC, ItemsRegistry.rubyGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateRubyOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_ruby_gem_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.deepslateSapphireOre.get()), RecipeCategory.MISC, ItemsRegistry.sapphireGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.deepslateSapphireOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "deepslate_sapphire_gem_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.dormantCrystals.get()), RecipeCategory.MISC, ItemsRegistry.unchargedShard.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.dormantCrystals.get())).save(pWriter, new ResourceLocation(Valoria.ID, "dormant_crystals_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.jadeOre.get()), RecipeCategory.MISC, ItemsRegistry.jade.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.jadeOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "jade_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.picriteJadeOre.get()), RecipeCategory.MISC, ItemsRegistry.jade.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.picriteJadeOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "jade_from_picrite_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.pyratiteOre.get()), RecipeCategory.MISC, ItemsRegistry.pyratite.get(), 1.0F, 100, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.pyratiteOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "pyratite_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.rubyOre.get()), RecipeCategory.MISC, ItemsRegistry.rubyGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.rubyOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "ruby_gem_from_blasting"));
        SimpleCookingRecipeBuilder.generic(Ingredient.of(BlockRegistry.sapphireOre.get()), RecipeCategory.MISC, ItemsRegistry.sapphireGem.get(), 0.3F, 60, RecipeSerializer.BLASTING_RECIPE).unlockedBy("has_item", has(BlockRegistry.sapphireOre.get())).save(pWriter, new ResourceLocation(Valoria.ID, "sapphire_gem_from_blasting"));
    }

    private void buildManualRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.abyssalLantern.get()).pattern("/X").pattern("X/").define('/', Ingredient.of(BlockRegistry.abyssalGlowfern.get())).define('X', Ingredient.of(BlockRegistry.glowVioletSprout.get())).unlockedBy("has_item", has(BlockRegistry.abyssalGlowfern.get())).save(pWriter, new ResourceLocation(Valoria.ID, "abyssal_lantern"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistry.aloeBandage.get(), 2).pattern("/#").pattern("#/").define('#', Ingredient.of(Items.STRING)).define('/', Ingredient.of(ItemsRegistry.aloePiece.get())).unlockedBy("has_item", has(Items.STRING)).save(pWriter, new ResourceLocation(Valoria.ID, "aloe_bandage"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistry.aloeBandageUpgraded.get(), 2).pattern("/#").pattern("#/").define('#', Ingredient.of(ItemsRegistry.aloeBandage.get())).define('/', Ingredient.of(Items.REDSTONE)).unlockedBy("has_item", has(ItemsRegistry.aloeBandage.get())).save(pWriter, new ResourceLocation(Valoria.ID, "aloe_bandage_upgraded"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistry.applePie.get()).pattern("AAA").pattern("///").pattern("XXX").define('/', Ingredient.of(Items.APPLE)).define('A', Ingredient.of(Items.COCOA_BEANS)).define('X', Ingredient.of(Items.MILK_BUCKET)).unlockedBy("has_item", has(Items.APPLE)).save(pWriter, new ResourceLocation(Valoria.ID, "apple_pie"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemsRegistry.aquariusArrow.get(), 4).pattern(" X ").pattern("XOX").pattern(" X ").define('O', Ingredient.of(ItemsRegistry.oceanicShell.get())).define('X', Ingredient.of(Items.ARROW)).unlockedBy("has_item", has(Items.ARROW)).save(pWriter, new ResourceLocation(Valoria.ID, "aquarius_arrow"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.bronzeDoor.get(), 3).pattern("//").pattern("//").pattern("//").define('/', Ingredient.of(ItemsRegistry.bronzeIngot.get())).unlockedBy("has_item", has(ItemsRegistry.bronzeIngot.get())).save(pWriter, new ResourceLocation(Valoria.ID, "bronze_door"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.bronzeGlass.get()).pattern(" / ").pattern("/X/").pattern(" / ").define('/', Ingredient.of(ItemsRegistry.bronzeIngot.get())).define('X', Ingredient.of(Items.GLASS)).unlockedBy("has_item", has(ItemsRegistry.bronzeIngot.get())).save(pWriter, new ResourceLocation(Valoria.ID, "bronze_glass"));



        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.aquariusMultiTool.get()).requires(Ingredient.of(ItemsRegistry.aquariusPickaxe.get())).requires(Ingredient.of(ItemsRegistry.aquariusAxe.get())).requires(Ingredient.of(ItemsRegistry.aquariusShovel.get())).requires(Ingredient.of(ItemsRegistry.aquariusHoe.get())).unlockedBy("has_item", has(ItemsRegistry.aquariusPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "aquarius_multi_tool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.cobaltMultiTool.get()).requires(Ingredient.of(ItemsRegistry.cobaltPickaxe.get())).requires(Ingredient.of(ItemsRegistry.cobaltAxe.get())).requires(Ingredient.of(ItemsRegistry.cobaltShovel.get())).requires(Ingredient.of(ItemsRegistry.cobaltHoe.get())).unlockedBy("has_item", has(ItemsRegistry.cobaltPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "cobalt_multi_tool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.crimtaneMultiTool.get()).requires(Ingredient.of(ItemsRegistry.crimtanePickaxe.get())).requires(Ingredient.of(ItemsRegistry.crimtaneAxe.get())).requires(Ingredient.of(ItemsRegistry.crimtaneShovel.get())).requires(Ingredient.of(ItemsRegistry.crimtaneHoe.get())).unlockedBy("has_item", has(ItemsRegistry.crimtanePickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "crimtane_multi_tool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.voidMultiTool.get()).requires(Ingredient.of(ItemsRegistry.voidPickaxe.get())).requires(Ingredient.of(ItemsRegistry.voidAxe.get())).requires(Ingredient.of(ItemsRegistry.voidShovel.get())).requires(Ingredient.of(ItemsRegistry.voidHoe.get())).unlockedBy("has_item", has(ItemsRegistry.voidPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "void_multi_tool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.natureMultiTool.get()).requires(Ingredient.of(ItemsRegistry.naturePickaxe.get())).requires(Ingredient.of(ItemsRegistry.natureAxe.get())).requires(Ingredient.of(ItemsRegistry.natureShovel.get())).requires(Ingredient.of(ItemsRegistry.natureHoe.get())).unlockedBy("has_item", has(ItemsRegistry.naturePickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "nature_multi_tool"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemsRegistry.infernalMultiTool.get()).requires(Ingredient.of(ItemsRegistry.infernalPickaxe.get())).requires(Ingredient.of(ItemsRegistry.infernalAxe.get())).requires(Ingredient.of(ItemsRegistry.infernalShovel.get())).requires(Ingredient.of(ItemsRegistry.infernalHoe.get())).unlockedBy("has_item", has(ItemsRegistry.infernalPickaxe.get())).save(pWriter, new ResourceLocation(Valoria.ID, "infernal_multi_tool"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND), Ingredient.of(ItemsRegistry.goldenRapier.get()), Ingredient.of(Items.DIAMOND), RecipeCategory.MISC, ItemsRegistry.diamondRapier.get()).unlocks("has_item", has(ItemsRegistry.goldenRapier.get())).save(pWriter, new ResourceLocation(Valoria.ID, "diamond_rapier"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.GOLD_INGOT), Ingredient.of(ItemsRegistry.ironRapier.get()), Ingredient.of(Items.GOLD_INGOT), RecipeCategory.MISC, ItemsRegistry.goldenRapier.get()).unlocks("has_item", has(ItemsRegistry.ironRapier.get())).save(pWriter, new ResourceLocation(Valoria.ID, "golden_rapier"));
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.IRON_INGOT), Ingredient.of(ItemsRegistry.stoneRapier.get()), Ingredient.of(Items.IRON_INGOT), RecipeCategory.MISC, ItemsRegistry.ironRapier.get()).unlocks("has_item", has(ItemsRegistry.stoneRapier.get())).save(pWriter, new ResourceLocation(Valoria.ID, "iron_rapier"));
    }

    public void generateArmor(Consumer<FinishedRecipe> pWriter, ItemLike material, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots) {
        if (helmet != null) ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet).pattern("XXX").pattern("X X").define('X', material).unlockedBy("has_material", has(material)).save(pWriter);
        if (chestplate != null) ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate).pattern("X X").pattern("XXX").pattern("XXX").define('X', material).unlockedBy("has_material", has(material)).save(pWriter);
        if (leggings != null) ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings).pattern("XXX").pattern("X X").pattern("X X").define('X', material).unlockedBy("has_material", has(material)).save(pWriter);
        if (boots != null) ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots).pattern("X X").pattern("X X").define('X', material).unlockedBy("has_material", has(material)).save(pWriter);
    }

    public void generateTools(Consumer<FinishedRecipe> pWriter, ItemLike material, ItemLike stick, ItemLike sword, ItemLike pickaxe, ItemLike axe, ItemLike shovel, ItemLike hoe) {
        if (sword != null) ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword).pattern("X").pattern("X").pattern("#").define('X', material).define('#', stick).unlockedBy("has_material", has(material)).save(pWriter);
        if (pickaxe != null) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe).pattern("XXX").pattern(" # ").pattern(" # ").define('X', material).define('#', stick).unlockedBy("has_material", has(material)).save(pWriter);
        if (axe != null) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe).pattern("XX").pattern("X#").pattern(" #").define('X', material).define('#', stick).unlockedBy("has_material", has(material)).save(pWriter);
        if (shovel != null) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel).pattern("X").pattern("#").pattern("#").define('X', material).define('#', stick).unlockedBy("has_material", has(material)).save(pWriter);
        if (hoe != null) ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe).pattern("XX").pattern(" #").pattern(" #").define('X', material).define('#', stick).unlockedBy("has_material", has(material)).save(pWriter);
    }

    public void generateIngotBlockNugget(Consumer<FinishedRecipe> pWriter, ItemLike block, ItemLike ingot, ItemLike nugget) {
        if (block != null && ingot != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block).pattern("XXX").pattern("XXX").pattern("XXX").define('X', ingot).unlockedBy("has_material", has(ingot)).save(pWriter);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingot, 9).requires(block).unlockedBy("has_material", has(block)).save(pWriter, new ResourceLocation(Valoria.ID, ForgeRegistries.ITEMS.getKey(ingot.asItem()).getPath() + "_from_block"));
        }
        if (ingot != null && nugget != null) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ingot).pattern("XXX").pattern("XXX").pattern("XXX").define('X', nugget).unlockedBy("has_material", has(nugget)).save(pWriter, new ResourceLocation(Valoria.ID, ForgeRegistries.ITEMS.getKey(ingot.asItem()).getPath() + "_from_nuggets"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nugget, 9).requires(ingot).unlockedBy("has_material", has(ingot)).save(pWriter);
        }
    }
}