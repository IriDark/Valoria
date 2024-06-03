package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.client.gui.screen.book.unlockable.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.registries.entity.decoration.*;
import com.idark.valoria.registries.item.tiers.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.arrow.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.idark.valoria.registries.item.types.curio.charm.*;
import com.idark.valoria.registries.item.types.curio.enums.*;
import com.idark.valoria.registries.item.types.curio.necklace.*;
import com.idark.valoria.registries.item.types.food.*;
import com.idark.valoria.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

public class ItemsRegistry{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Valoria.ID);

    public static final RegistryObject<Item> EPHEMARITE_LOW = ITEMS.register("ephemarite_low", () -> new DescriptionBlockItem("tooltip.valoria.geode", BlockRegistry.EPHEMARITE_LOW.get(), new Item.Properties()));
    public static final RegistryObject<Item> EPHEMARITE = ITEMS.register("ephemarite", () -> new DescriptionBlockItem("tooltip.valoria.geode", BlockRegistry.EPHEMARITE.get(), new Item.Properties()));

    public static final RegistryObject<Item> VOIDVINE = ITEMS.register("voidvine", () -> new TaintTransformBlockItem(BlockRegistry.VOIDVINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> VIOLET_SPROUT = ITEMS.register("violet_sprout", () -> new TaintTransformBlockItem(BlockRegistry.VIOLET_SPROUT.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOW_VIOLET_SPROUT = ITEMS.register("glow_violet_sprout", () -> new TaintTransformBlockItem(BlockRegistry.GLOW_VIOLET_SPROUT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ABYSSAL_GLOWFERN = ITEMS.register("abyssal_glowfern", () -> new TaintTransformBlockItem(BlockRegistry.ABYSSAL_GLOWFERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHADEWOOD_BOAT_ITEM = ITEMS.register("shadewood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.SHADEWOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SHADEWOOD_CHEST_BOAT_ITEM = ITEMS.register("shadewood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.SHADEWOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SHADEWOOD_SIGN = ITEMS.register("shadewood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.SHADEWOOD_SIGN.get(), BlockRegistry.SHADEWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SHADEWOOD_HANGING_SIGN = ITEMS.register("shadewood_hanging_sign", () -> new HangingSignItem(BlockRegistry.SHADEWOOD_HANGING_SIGN.get(), BlockRegistry.SHADEWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ELDRITCH_BOAT_ITEM = ITEMS.register("eldritch_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.ELDRITCH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ELDRITCH_CHEST_BOAT_ITEM = ITEMS.register("eldritch_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.ELDRITCH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ELDRITCH_SIGN = ITEMS.register("eldritch_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.ELDRITCH_SIGN.get(), BlockRegistry.ELDRITCH_WALL_SIGN.get()));
    public static final RegistryObject<Item> ELDRITCH_HANGING_SIGN = ITEMS.register("eldritch_hanging_sign", () -> new HangingSignItem(BlockRegistry.ELDRITCH_HANGING_SIGN.get(), BlockRegistry.ELDRITCH_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    // ARMOR
    public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.HELMET, (new Item.Properties())));
    public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
    public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
    public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots", () -> new ArmorItem(ModArmorMaterial.COBALT, ArmorItem.Type.BOOTS, (new Item.Properties())));
    public static final RegistryObject<Item> SAMURAI_KABUTO = ITEMS.register("samurai_kabuto", () -> new SamuraiArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.HELMET, (new Item.Properties())));
    public static final RegistryObject<Item> SAMURAI_CHESTPLATE = ITEMS.register("samurai_chestplate", () -> new SamuraiArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.CHESTPLATE, (new Item.Properties())));
    public static final RegistryObject<Item> SAMURAI_LEGGINGS = ITEMS.register("samurai_leggings", () -> new SamuraiArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.LEGGINGS, (new Item.Properties())));
    public static final RegistryObject<Item> SAMURAI_BOOTS = ITEMS.register("samurai_boots", () -> new SamuraiArmorItem(ModArmorMaterial.SAMURAI, ArmorItem.Type.BOOTS, (new Item.Properties())));
    public static final RegistryObject<Item> AWAKENED_VOID_HELMET = ITEMS.register("awakened_void_helmet", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.HELMET, (new Item.Properties().rarity(RarityRegistry.VOID).fireResistant())));
    public static final RegistryObject<Item> AWAKENED_VOID_CHESTPLATE = ITEMS.register("awakened_void_chestplate", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.CHESTPLATE, (new Item.Properties().rarity(RarityRegistry.VOID).fireResistant())));
    public static final RegistryObject<Item> AWAKENED_VOID_LEGGINGS = ITEMS.register("awakened_void_leggings", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.LEGGINGS, (new Item.Properties().rarity(RarityRegistry.VOID).fireResistant())));
    public static final RegistryObject<Item> AWAKENED_VOID_BOOTS = ITEMS.register("awakened_void_boots", () -> new ArmorItem(ModArmorMaterial.AWAKENED_VOID, ArmorItem.Type.BOOTS, (new Item.Properties().rarity(RarityRegistry.VOID).fireResistant())));
    public static final RegistryObject<Item> NATURE_HELMET = ITEMS.register("nature_helmet", () -> new ModArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.HELMET, (new Item.Properties().rarity(RarityRegistry.NATURE))));
    public static final RegistryObject<Item> NATURE_CHESTPLATE = ITEMS.register("nature_chestplate", () -> new ModArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.CHESTPLATE, (new Item.Properties().rarity(RarityRegistry.NATURE))));
    public static final RegistryObject<Item> NATURE_LEGGINGS = ITEMS.register("nature_leggings", () -> new ModArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.LEGGINGS, (new Item.Properties().rarity(RarityRegistry.NATURE))));
    public static final RegistryObject<Item> NATURE_BOOTS = ITEMS.register("nature_boots", () -> new ModArmorItem(ModArmorMaterial.NATURE, ArmorItem.Type.BOOTS, (new Item.Properties().rarity(RarityRegistry.NATURE))));
    public static final RegistryObject<Item> DEPTH_HELMET = ITEMS.register("depth_helmet", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.HELMET, (new Item.Properties().rarity(RarityRegistry.AQUARIUS))));
    public static final RegistryObject<Item> DEPTH_CHESTPLATE = ITEMS.register("depth_chestplate", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.CHESTPLATE, (new Item.Properties().rarity(RarityRegistry.AQUARIUS))));
    public static final RegistryObject<Item> DEPTH_LEGGINGS = ITEMS.register("depth_leggings", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.LEGGINGS, (new Item.Properties().rarity(RarityRegistry.AQUARIUS))));
    public static final RegistryObject<Item> DEPTH_BOOTS = ITEMS.register("depth_boots", () -> new ModArmorItem(ModArmorMaterial.DEPTH, ArmorItem.Type.BOOTS, (new Item.Properties().rarity(RarityRegistry.AQUARIUS))));
    public static final RegistryObject<Item> INFERNAL_HELMET = ITEMS.register("infernal_helmet", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.HELMET, (new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL))));
    public static final RegistryObject<Item> INFERNAL_CHESTPLATE = ITEMS.register("infernal_chestplate", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.CHESTPLATE, (new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL))));
    public static final RegistryObject<Item> INFERNAL_LEGGINGS = ITEMS.register("infernal_leggings", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.LEGGINGS, (new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL))));
    public static final RegistryObject<Item> INFERNAL_BOOTS = ITEMS.register("infernal_boots", () -> new ModArmorItem(ModArmorMaterial.INFERNAL, ArmorItem.Type.BOOTS, (new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL))));
    public static final RegistryObject<Item> PHANTASM_HELMET = ITEMS.register("phantasm_helmet", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.HELMET, (new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant())));
    public static final RegistryObject<Item> PHANTASM_CHESTPLATE = ITEMS.register("phantasm_chestplate", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.CHESTPLATE, (new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant())));
    public static final RegistryObject<Item> PHANTASM_LEGGINGS = ITEMS.register("phantasm_leggings", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.LEGGINGS, (new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant())));
    public static final RegistryObject<Item> PHANTASM_BOOTS = ITEMS.register("phantasm_boots", () -> new ModArmorItem(ModArmorMaterial.PHANTASM, ArmorItem.Type.BOOTS, (new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant())));

    // ITEMS
    public static final RegistryObject<Item> DEBUG = ITEMS.register("debug_item", () -> new DebugItem(new Item.Properties()));
    //public static final RegistryObject<Item> STAFF = ITEMS.register("basic_staff", () -> new StaffItem(new Item.Properties()));
    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMBER_GEM = ITEMS.register("amber_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMETHYST_GEM = ITEMS.register("amethyst_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBY_GEM = ITEMS.register("ruby_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_GEM = ITEMS.register("sapphire_gem", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("wicked_amethyst", () -> new TransformShardItem(new Item.Properties().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> SOUL_SHARD = ITEMS.register("soul_shard", () -> new TransformShardItem(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> UNCHARGED_SHARD = ITEMS.register("uncharged_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOXINS_BOTTLE = ITEMS.register("toxins_bottle", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DIRT_GEODE = ITEMS.register("dirt_geode", () -> new DescriptionItem("tooltip.valoria.geode", new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> STONE_GEODE = ITEMS.register("stone_geode", () -> new DescriptionItem("tooltip.valoria.geode", new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MINERS_BAG = ITEMS.register("miners_bag", () -> new DropItemProperty(DropType.MINERS, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GEM_BAG = ITEMS.register("gem_bag", () -> new DropItemProperty(DropType.GEM, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NATURE_GIFT = ITEMS.register("nature_gift", () -> new ParticleMaterialItem(ParticleRegistry.GLOWING_SPHERE.get(), new int[]{20, 235, 0}, new int[]{132, 235, 22}, 0.35f, new Item.Properties().stacksTo(16).rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> OCEANIC_SHELL = ITEMS.register("oceanic_shell", () -> new ParticleMaterialItem(ParticleRegistry.GLOWING_SPHERE.get(), new int[]{20, 145, 235}, new int[]{45, 0, 0}, 0.35f, new Item.Properties().stacksTo(16).rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> INFERNAL_STONE = ITEMS.register("infernal_stone", () -> new ParticleMaterialItem(ParticleRegistry.GLOWING_SPHERE.get(), new int[]{255, 145, 45}, new int[]{45, 0, 0}, 0.35f, new Item.Properties().stacksTo(16).rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> BONE_FRAGMENT = ITEMS.register("bone_fragment", () -> new ParticleMaterialItem(ParticleRegistry.GLOWING_SPHERE.get(), new int[]{145, 235, 25}, new int[]{132, 215, 22}, 0.35f, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PAIN_CRYSTAL = ITEMS.register("pain_crystal", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ILLUSION_STONE = ITEMS.register("illusion_stone", () -> new Item(new Item.Properties().rarity(RarityRegistry.PHANTASM).stacksTo(16)));
    public static final RegistryObject<Item> SOUL_COLLECTOR_EMPTY = ITEMS.register("soul_collector_empty", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_COLLECTOR = ITEMS.register("soul_collector", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_KEY = ITEMS.register("void_key", () -> new Item(new Item.Properties().stacksTo(8).rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> GAIB_ROOT = ITEMS.register("gaib_root", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> KARUSAKAN_ROOT = ITEMS.register("karusakan_root", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> WOODEN_CUP = ITEMS.register("wooden_cup", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CUP = ITEMS.register("cup", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> BOTTLE = ITEMS.register("bottle", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ALOE_PIECE = ITEMS.register("aloe_piece", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LEXICON = ITEMS.register("lexicon", () -> new LexiconItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CRYPT = ITEMS.register("page", () -> new LexiconPageItem(new Item.Properties().stacksTo(1), RegisterUnlockables.CRYPT, "gui.valoria.crypt.name"));

    public static final RegistryObject<Item> JEWELRY_BAG = ITEMS.register("jewelry_bag", () -> new JewelryBagItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALOE_BANDAGE = ITEMS.register("aloe_bandage", () -> new AloeBandageItem(1600, 0));
    public static final RegistryObject<Item> ALOE_BANDAGE_UPGRADED = ITEMS.register("aloe_bandage_upgraded", () -> new AloeBandageItem(1450, 1));
    public static final RegistryObject<Item> CACAO_CUP = ITEMS.register("cacao_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.CUP.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
    public static final RegistryObject<Item> COFFEE_CUP = ITEMS.register("coffee_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.CUP.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
    public static final RegistryObject<Item> TEA_CUP = ITEMS.register("tea_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.CUP.get(), new MobEffectInstance(MobEffects.DIG_SPEED, 100)));
    public static final RegistryObject<Item> GREEN_TEA_CUP = ITEMS.register("green_tea_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.CUP.get(), new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 1800)));
    public static final RegistryObject<Item> BEER_CUP = ITEMS.register("beer_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.WOODEN_CUP.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 400, 0)));
    public static final RegistryObject<Item> RUM_CUP = ITEMS.register("rum_cup", () -> new ModDrinkItem(0, 1, 1, ItemsRegistry.WOODEN_CUP.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 400, 0), new MobEffectInstance(MobEffects.CONFUSION, 120, 0)));
    public static final RegistryObject<Item> KVASS_BOTTLE = ITEMS.register("kvass_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 200)));
    public static final RegistryObject<Item> WINE_BOTTLE = ITEMS.register("wine_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 300)));
    public static final RegistryObject<Item> AKVAVIT_BOTTLE = ITEMS.register("akvavit_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 500, 1), new MobEffectInstance(MobEffects.CONFUSION, 320)));
    public static final RegistryObject<Item> LIQUOR_BOTTLE = ITEMS.register("liquor_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 350, 1), new MobEffectInstance(MobEffects.CONFUSION, 120)));
    public static final RegistryObject<Item> RUM_BOTTLE = ITEMS.register("rum_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 650, 1), new MobEffectInstance(MobEffects.CONFUSION, 250)));
    public static final RegistryObject<Item> MEAD_BOTTLE = ITEMS.register("mead_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 700, 0), new MobEffectInstance(MobEffects.CONFUSION, 240)));
    public static final RegistryObject<Item> COGNAC_BOTTLE = ITEMS.register("cognac_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 800, 1), new MobEffectInstance(MobEffects.CONFUSION, 350)));
    public static final RegistryObject<Item> WHISKEY_BOTTLE = ITEMS.register("whiskey_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 150)));
    public static final RegistryObject<Item> COKE_BOTTLE = ITEMS.register("coke_bottle", () -> new ModDrinkItem(0, 1, 64, ItemsRegistry.BOTTLE.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
    public static final RegistryObject<Item> APPLE_PIE = ITEMS.register("apple_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1.4f).build())));
    public static final RegistryObject<Item> HOLIDAY_CANDY = ITEMS.register("holiday_candy", () -> new Item(new Item.Properties().stacksTo(16).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> EYE_CHUNK = ITEMS.register("eye_chunk", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.POISON, 100), 0.4f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300), 1f).nutrition(1).saturationMod(0.2f).fast().build())));
    public static final RegistryObject<Item> TAINTED_BERRIES = ITEMS.register("tainted_berries", () -> new ItemNameBlockItem(BlockRegistry.TAINTED_ROOTS.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).fast().build())));
    public static final RegistryObject<Item> COOKED_GLOW_VIOLET_SPROUT = ITEMS.register("cooked_glow_violet_sprout", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1f).build())));
    public static final RegistryObject<Item> COOKED_ABYSSAL_GLOWFERN = ITEMS.register("cooked_abyssal_glowfern", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1f).build())));

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
    public static final RegistryObject<Item> NATURE_INGOT = ITEMS.register("nature_ingot", () -> new Item(new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> AQUARIUS_INGOT = ITEMS.register("aquarius_ingot", () -> new Item(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> INFERNAL_INGOT = ITEMS.register("infernal_ingot", () -> new Item(new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot", () -> new Item(new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> PYRATITE = ITEMS.register("pyratite", () -> new Item(new Item.Properties().rarity(RarityRegistry.INFERNAL)));
    // Cores
    public static final RegistryObject<Item> NATURE_CORE = ITEMS.register("nature_core", () -> new CoreItem(new Item.Properties().fireResistant().rarity(RarityRegistry.NATURE), 8, new int[]{46, 204, 113}, "nature_core"));
    public static final RegistryObject<Item> AQUARIUS_CORE = ITEMS.register("aquarius_core", () -> new CoreItem(new Item.Properties().fireResistant().rarity(RarityRegistry.AQUARIUS), 8, new int[]{17, 195, 214}, "aquarius_core"));
    public static final RegistryObject<Item> INFERNAL_CORE = ITEMS.register("infernal_core", () -> new CoreItem(new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL), 8, new int[]{231, 76, 60}, "infernal_core"));
    public static final RegistryObject<Item> VOID_CORE = ITEMS.register("void_core", () -> new CoreItem(new Item.Properties().fireResistant().rarity(RarityRegistry.VOID), 8, new int[]{52, 73, 94}, "void_core"));

    // TOOLS (category)
    public static final RegistryObject<Item> CLUB = ITEMS.register("club",
    () -> new HitEffectItem(Tiers.WOOD, 5, -3.2f, new Item.Properties(), 0.1f, new MobEffectInstance(EffectsRegistry.STUN.get(), 60, 0)));
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword",
    () -> new SwordItem(Tiers.IRON, 6, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> VOID_EDGE = ITEMS.register("void_edge",
    () -> new SwordItem(ModItemTier.NONE, 7, -3f, new Item.Properties().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> QUANTUM_REAPER = ITEMS.register("quantum_reaper",
    () -> new SwordItem(ModItemTier.NONE, 8, -3f, new Item.Properties().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> BLOODHOUND = ITEMS.register("bloodhound",
    () -> new HoundItem(ModItemTier.BLOOD, 6, -2.2f, new Item.Properties()));
    public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap",
    () -> new BlazeReapItem(ModItemTier.NONE, 3, -3.4f, new Item.Properties()));
    public static final RegistryObject<Item> PHANTOM = ITEMS.register("phantom",
    () -> new PhantomItem(ModItemTier.NONE, 6, -2.4f, new Item.Properties().rarity(RarityRegistry.PHANTASM)));
    public static final RegistryObject<Item> MURASAMA = ITEMS.register("murasama",
    () -> new MurasamaItem(ModItemTier.SAMURAI, 5, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> SAMURAI_KUNAI = ITEMS.register("samurai_kunai",
    () -> new KunaiItem(new Item.Properties().durability(360)));
    public static final RegistryObject<Item> SAMURAI_POISONED_KUNAI = ITEMS.register("samurai_poisoned_kunai",
    () -> new KunaiItem(new Item.Properties().durability(360)));
    public static final RegistryObject<Item> SPECTRAL_BLADE = ITEMS.register("spectral_blade",
    () -> new SpectralBladeItem(new Item.Properties().durability(852)));
    public static final RegistryObject<Item> CORPSECLEAVER = ITEMS.register("corpsecleaver",
    () -> new CorpseCleaverItem(ModItemTier.BLOOD, 2, -2.4F, new Item.Properties().durability(1151)));

    // Placeholder for Entity Render
    public static final RegistryObject<Item> SPECTRAL_BLADE_THROWN = ITEMS.register("spectral_blade_thrown",
    () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
    () -> new SpearItem(Tiers.WOOD, 3, -3f, 1.4f, new Item.Properties()));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
    () -> new SpearItem(Tiers.STONE, 4, -3f, 2, new Item.Properties()));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
    () -> new SpearItem(Tiers.IRON, 5, -3f, 3.2f, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
    () -> new SpearItem(Tiers.GOLD, 5, -2.9f, 3.5f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
    () -> new SpearItem(Tiers.DIAMOND, 3, -2.9f, 4, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
    () -> new SpearItem(Tiers.NETHERITE, 3, -2.9f, 6, new Item.Properties()));
    public static final RegistryObject<Item> PYRATITE_SPEAR = ITEMS.register("pyratite_spear",
    () -> new ExplosiveSpearItem(ModItemTier.PYRATITE, 5, -2.9f, 7.5f, 0.75f, Level.ExplosionInteraction.NONE, new Item.Properties().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> GLAIVE = ITEMS.register("glaive",
    () -> new SpearItem(Tiers.IRON, 8, -3.2f, false, new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_RAPIER = ITEMS.register("wooden_rapier",
    () -> new SwordItem(Tiers.WOOD, 0, -1.8f, new Item.Properties()));
    public static final RegistryObject<Item> STONE_RAPIER = ITEMS.register("stone_rapier",
    () -> new SwordItem(Tiers.STONE, 0, -1.8f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_RAPIER = ITEMS.register("iron_rapier",
    () -> new SwordItem(Tiers.IRON, 1, -1.7f, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_RAPIER = ITEMS.register("golden_rapier",
    () -> new SwordItem(Tiers.GOLD, 0, -1.5f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_RAPIER = ITEMS.register("diamond_rapier",
    () -> new SwordItem(Tiers.DIAMOND, 2, -1.5f, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_RAPIER = ITEMS.register("netherite_rapier",
    () -> new SwordItem(Tiers.NETHERITE, 2, -1.5f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
    () -> new ScytheItem(Tiers.IRON, 5, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SCYTHE = ITEMS.register("golden_scythe",
    () -> new ScytheItem(Tiers.GOLD, 5, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
    () -> new ScytheItem(Tiers.DIAMOND, 8, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
    () -> new ScytheItem(Tiers.NETHERITE, 10, -3.0f, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> BEAST = ITEMS.register("beast",
    () -> new BeastScytheItem(ModItemTier.NONE, 13, -3.2f, new Item.Properties()));
    public static final RegistryObject<Item> NATURE_SCYTHE = ITEMS.register("nature_scythe",
    () -> new ScytheItem(ModItemTier.NATURE, 11, -3.0f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> AQUARIUS_SCYTHE = ITEMS.register("aquarius_scythe",
    () -> new AquariusScytheItem(ModItemTier.AQUARIUS, 12, -3.0f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> INFERNAL_SCYTHE = ITEMS.register("infernal_scythe",
    () -> new InfernalScytheItem(ModItemTier.INFERNAL, 14, -3.0f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> HOLIDAY_KATANA = ITEMS.register("holiday_katana",
    () -> new KatanaItem(ModItemTier.HOLIDAY, 0, -2.2f, new Item.Properties()));
    public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana",
    () -> new KatanaItem(Tiers.IRON, 3, -2.2f, 1f, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_KATANA = ITEMS.register("golden_katana",
    () -> new KatanaItem(Tiers.GOLD, 3, -1.8f, 1.2f, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_KATANA = ITEMS.register("diamond_katana",
    () -> new KatanaItem(Tiers.DIAMOND, 4, -2f, 1.3f, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_KATANA = ITEMS.register("netherite_katana",
    () -> new KatanaItem(Tiers.NETHERITE, 4, -1.8f, 1.5f, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SAMURAI_KATANA = ITEMS.register("samurai_katana",
    () -> new KatanaItem(ModItemTier.SAMURAI, 5, -2f, new Item.Properties()));
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
    public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
    () -> new SwordItem(ModItemTier.COBALT, 9, -2.2f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
    () -> new PickaxeItem(ModItemTier.COBALT, 4, -3f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_SHOVEL = ITEMS.register("cobalt_shovel",
    () -> new ShovelItem(ModItemTier.COBALT, 5.25f, -3f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_AXE = ITEMS.register("cobalt_axe",
    () -> new AxeItem(ModItemTier.COBALT, 10.5f, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> COBALT_HOE = ITEMS.register("cobalt_hoe",
    () -> new HoeItem(ModItemTier.COBALT, 0, 0f, new Item.Properties()));
    public static final RegistryObject<Item> ENT = ITEMS.register("ent",
    () -> new SwordItem(ModItemTier.NATURE, 11, -2.4f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> NATURE_PICKAXE = ITEMS.register("nature_pickaxe",
    () -> new PickaxeItem(ModItemTier.NATURE, 5, -3f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> NATURE_SHOVEL = ITEMS.register("nature_shovel",
    () -> new ShovelItem(ModItemTier.NATURE, 6, -3f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> NATURE_AXE = ITEMS.register("nature_axe",
    () -> new AxeItem(ModItemTier.NATURE, 12f, -2.8f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> NATURE_HOE = ITEMS.register("nature_hoe",
    () -> new HoeItem(ModItemTier.NATURE, 0, 0f, new Item.Properties().rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> CORAL_REEF = ITEMS.register("coral_reef",
    () -> new CoralReefItem(ModItemTier.AQUARIUS, 12, -2.4f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> AQUARIUS_PICKAXE = ITEMS.register("aquarius_pickaxe",
    () -> new PickaxeItem(ModItemTier.AQUARIUS, 6, -3f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> AQUARIUS_SHOVEL = ITEMS.register("aquarius_shovel",
    () -> new ShovelItem(ModItemTier.AQUARIUS, 6.25f, -3f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> AQUARIUS_AXE = ITEMS.register("aquarius_axe",
    () -> new AxeItem(ModItemTier.AQUARIUS, 13f, -2.8f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> AQUARIUS_HOE = ITEMS.register("aquarius_hoe",
    () -> new HoeItem(ModItemTier.AQUARIUS, 0, 0f, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> INFERNAL_SWORD = ITEMS.register("infernal_sword",
    () -> new MagmaSwordItem(ModItemTier.INFERNAL, 15, -2.6f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> INFERNAL_PICKAXE = ITEMS.register("infernal_pickaxe",
    () -> new PickaxeItem(ModItemTier.INFERNAL, 7, -2.8f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> INFERNAL_SHOVEL = ITEMS.register("infernal_shovel",
    () -> new ShovelItem(ModItemTier.INFERNAL, 7.5f, -2.9f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> INFERNAL_AXE = ITEMS.register("infernal_axe",
    () -> new AxeItem(ModItemTier.INFERNAL, 16.25f, -2.8f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> INFERNAL_HOE = ITEMS.register("infernal_hoe",
    () -> new HoeItem(ModItemTier.INFERNAL, 0, 0f, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
    // ACCESSORIES (category)
    public static final RegistryObject<Item> PICK_NECKLACE = ITEMS.register("pick_necklace",
    () -> new PickNecklace(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_CHAIN = ITEMS.register("iron_chain",
    () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_AMBER = ITEMS.register("iron_necklace_amber",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_DIAMOND = ITEMS.register("iron_necklace_diamond",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_EMERALD = ITEMS.register("iron_necklace_emerald",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_RUBY = ITEMS.register("iron_necklace_ruby",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_SAPPHIRE = ITEMS.register("iron_necklace_sapphire",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_HEALTH = ITEMS.register("iron_necklace_health",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_ARMOR = ITEMS.register("iron_necklace_armor",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_NECKLACE_WEALTH = ITEMS.register("iron_necklace_wealth",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_CHAIN = ITEMS.register("golden_chain",
    () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_AMBER = ITEMS.register("golden_necklace_amber",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_DIAMOND = ITEMS.register("golden_necklace_diamond",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_EMERALD = ITEMS.register("golden_necklace_emerald",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_RUBY = ITEMS.register("golden_necklace_ruby",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_SAPPHIRE = ITEMS.register("golden_necklace_sapphire",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_HEALTH = ITEMS.register("golden_necklace_health",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_ARMOR = ITEMS.register("golden_necklace_armor",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_NECKLACE_WEALTH = ITEMS.register("golden_necklace_wealth",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_CHAIN = ITEMS.register("netherite_chain",
    () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_AMBER = ITEMS.register("netherite_necklace_amber",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_DIAMOND = ITEMS.register("netherite_necklace_diamond",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_EMERALD = ITEMS.register("netherite_necklace_emerald",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_RUBY = ITEMS.register("netherite_necklace_ruby",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_SAPPHIRE = ITEMS.register("netherite_necklace_sapphire",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_HEALTH = ITEMS.register("netherite_necklace_health",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_ARMOR = ITEMS.register("netherite_necklace_armor",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_NECKLACE_WEALTH = ITEMS.register("netherite_necklace_wealth",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LEATHER_BELT = ITEMS.register("leather_belt",
    () -> new CurioItemProperty(ModItemTier.NONE, AccessoryType.BELT, AccessoryGem.BELT, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(80).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IRON_RING_AMBER = ITEMS.register("iron_ring_amber",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_RING_DIAMOND = ITEMS.register("iron_ring_diamond",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_RING_EMERALD = ITEMS.register("iron_ring_emerald",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_RING_RUBY = ITEMS.register("iron_ring_ruby",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IRON_RING_SAPPHIRE = ITEMS.register("iron_ring_sapphire",
    () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_RING = ITEMS.register("golden_ring",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(40).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GOLDEN_RING_AMBER = ITEMS.register("golden_ring_amber",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_RING_DIAMOND = ITEMS.register("golden_ring_diamond",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_RING_EMERALD = ITEMS.register("golden_ring_emerald",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_RING_RUBY = ITEMS.register("golden_ring_ruby",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_RING_SAPPHIRE = ITEMS.register("golden_ring_sapphire",
    () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_RING = ITEMS.register("netherite_ring",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NETHERITE_RING_AMBER = ITEMS.register("netherite_ring_amber",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_RING_DIAMOND = ITEMS.register("netherite_ring_diamond",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_RING_EMERALD = ITEMS.register("netherite_ring_emerald",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_RING_RUBY = ITEMS.register("netherite_ring_ruby",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NETHERITE_RING_SAPPHIRE = ITEMS.register("netherite_ring_sapphire",
    () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(420).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LEATHER_GLOVES = ITEMS.register("leather_gloves",
    () -> new GlovesItem(ModItemTier.NONE, AccessoryGem.NONE, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).durability(100).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IRON_GLOVES = ITEMS.register("iron_gloves",
    () -> new GlovesItem(Tiers.IRON, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(190).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GOLDEN_GLOVES = ITEMS.register("golden_gloves",
    () -> new GlovesItem(Tiers.GOLD, AccessoryGem.TOUGH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DIAMOND_GLOVES = ITEMS.register("diamond_gloves",
    () -> new GlovesItem(Tiers.DIAMOND, AccessoryGem.DIAMOND, AccessoryMaterial.DIAMOND, new Item.Properties().stacksTo(1).durability(240).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NETHERITE_GLOVES = ITEMS.register("netherite_gloves",
    () -> new GlovesItem(Tiers.NETHERITE, AccessoryGem.TANK, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.UNCOMMON)));
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
    () -> new CurioVision(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_WEALTH = ITEMS.register("rune_of_wealth",
    () -> new CurioWealth(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_CURSES = ITEMS.register("rune_of_curses",
    () -> new CurioCurses(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_STRENGTH = ITEMS.register("rune_of_strength",
    () -> new CurioStrength(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_ACCURACY = ITEMS.register("rune_of_accuracy",
    () -> new RuneAccuracy(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_DEEP = ITEMS.register("rune_of_deep",
    () -> new RuneDeep(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_PYRO = ITEMS.register("rune_of_pyro",
    () -> new CurioPyro(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUNE_OF_COLD = ITEMS.register("rune_of_cold",
    () -> new RuneCold(new Item.Properties().stacksTo(1).durability(16).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> BROKEN_BLOODSIGHT_MONOCLE = ITEMS.register("broken_bloodsight_monocle",
    () -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
    public static final RegistryObject<Item> BLOODSIGHT_MONOCLE = ITEMS.register("bloodsight_monocle",
    () -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
    public static final RegistryObject<Item> SAMURAI_LONG_BOW = ITEMS.register("samurai_long_bow",
    () -> new ConfigurableBowItem(2, new Item.Properties().stacksTo(1).durability(684)));
    public static final RegistryObject<Item> NATURE_BOW = ITEMS.register("nature_bow",
    () -> new ConfigurableBowItem(1, new Item.Properties().stacksTo(1).durability(1024).rarity(RarityRegistry.NATURE)));
    public static final RegistryObject<Item> AQUARIUS_BOW = ITEMS.register("aquarius_bow",
    () -> new ConfigurableBowItem(2, new Item.Properties().stacksTo(1).durability(1324).fireResistant().rarity(RarityRegistry.AQUARIUS)));
    public static final RegistryObject<Item> INFERNAL_BOW = ITEMS.register("infernal_bow",
    () -> new InfernalBowItem(new Item.Properties().fireResistant().stacksTo(1).durability(1684).rarity(RarityRegistry.INFERNAL)));
    public static final RegistryObject<Item> BOW_OF_DARKNESS = ITEMS.register("bow_of_darkness",
    () -> new ConfigurableBowItem(3, new Item.Properties().stacksTo(1).durability(2048).fireResistant().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> PHANTASM_BOW = ITEMS.register("phantasm_bow",
    () -> new PhantasmBowItem(new Item.Properties().fireResistant().stacksTo(1).durability(4028).rarity(RarityRegistry.PHANTASM)));
    public static final RegistryObject<Item> PICK = ITEMS.register("pick",
    () -> new PickItem(new Item.Properties().fireResistant().stacksTo(1).durability(64), 1, -2.8f, 5));
    public static final RegistryObject<Item> WICKED_ARROW = ITEMS.register("wicked_arrow",
    () -> new WickedArrowItem(new Item.Properties().rarity(RarityRegistry.VOID)));
    public static final RegistryObject<Item> SOUL_ARROW = ITEMS.register("soul_arrow",
    () -> new SoulArrowItem(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));

    public static final RegistryObject<ForgeSpawnEggItem> GOBLIN_SPAWN_EGG = ITEMS.register("goblin_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.GOBLIN, ValoriaUtils.color.hexToDecimal("185b36"), ValoriaUtils.color.hexToDecimal("6BB447"), new Item.Properties()));
    public static final RegistryObject<ForgeSpawnEggItem> DRAUGR_SPAWN_EGG = ITEMS.register("draugr_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.DRAUGR, ValoriaUtils.color.hexToDecimal("76695C"), ValoriaUtils.color.hexToDecimal("d6d0c9"), new Item.Properties()));
    public static final RegistryObject<ForgeSpawnEggItem> SWAMP_WANDERER_SPAWN_EGG = ITEMS.register("swamp_wanderer_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.SWAMP_WANDERER, ValoriaUtils.color.hexToDecimal("606239"), ValoriaUtils.color.hexToDecimal("b8b377"), new Item.Properties()));
    public static final RegistryObject<ForgeSpawnEggItem> NECROMANCER_SPAWN_EGG = ITEMS.register("necromancer_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.NECROMANCER, ValoriaUtils.color.hexToDecimal("4b4857"), ValoriaUtils.color.hexToDecimal("958fb7"), new Item.Properties()));
    public static final RegistryObject<ForgeSpawnEggItem> UNDEAD_SPAWN_EGG = ITEMS.register("undead_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.UNDEAD, ValoriaUtils.color.hexToDecimal("625F71"), ValoriaUtils.color.hexToDecimal("ffffff"), new Item.Properties()));
    public static final RegistryObject<ForgeSpawnEggItem> SHADEWOOD_SPIDER_SPAWN_EGG = ITEMS.register("shadewood_spider_spawn_egg",
    () -> new ForgeSpawnEggItem(EntityTypeRegistry.SHADEWOOD_SPIDER, ValoriaUtils.color.hexToDecimal("373C53"), ValoriaUtils.color.hexToDecimal("6EABB7"), new Item.Properties()));
    public static final RegistryObject<MannequinSpawnItem> MANNEQUIN_SPAWN_EGG = ITEMS.register("mannequin_spawn_egg",
    () -> new MannequinSpawnItem(new Item.Properties()));

    public static final RegistryObject<Item> ETERNITY = ITEMS.register("eternity", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}