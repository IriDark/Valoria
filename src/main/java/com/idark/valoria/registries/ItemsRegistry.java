package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.client.particle.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.core.enums.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.particle.*;
import com.idark.valoria.registries.item.*;
import com.idark.valoria.registries.item.armor.*;
import com.idark.valoria.registries.item.armor.item.*;
import com.idark.valoria.registries.item.skins.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.KatanaItem.*;
import com.idark.valoria.registries.item.types.curio.*;
import com.idark.valoria.registries.item.types.curio.charm.*;
import com.idark.valoria.registries.item.types.curio.hands.*;
import com.idark.valoria.registries.item.types.curio.necklace.*;
import com.idark.valoria.registries.item.types.food.*;
import com.idark.valoria.registries.item.types.ranged.*;
import com.idark.valoria.registries.item.types.ranged.bows.*;
import com.idark.valoria.util.*;
import net.minecraft.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorItem.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;
import org.joml.*;
import pro.komaru.tridot.api.*;
import pro.komaru.tridot.client.gfx.particle.data.*;
import pro.komaru.tridot.common.registry.item.*;
import pro.komaru.tridot.common.registry.item.skins.*;
import pro.komaru.tridot.common.registry.item.types.*;
import pro.komaru.tridot.util.*;

import java.awt.*;
import java.lang.Math;
import java.util.List;
import java.util.function.*;

public class ItemsRegistry{
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Valoria.ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Valoria.ID);
    public static RegistryObject<Item>
    // Block items
    shadewoodBoat, shadewoodChestBoat, shadewoodSign, shadewoodHangingSign, eldritchBoat, eldritchChestBoat, eldritchSign, eldritchHangingSign,

    // armor
    blackGoldHelmet, blackGoldChestplate, blackGoldLeggings, blackGoldBoots,
    cobaltHelmet, cobaltChestplate, cobaltLeggings, cobaltBoots,
    samuraiKabuto, samuraiChestplate, samuraiLeggings, samuraiBoots,
    marshHelmet, marshChestplate, marshLeggings, marshBoots,
    etherealHelmet, etherealChestplate, etherealLeggings, etherealBoots,
    spiderHelmet, spiderChestplate, spiderLeggings, spiderBoots,
    pyratiteHelmet, pyratiteChestplate, pyratiteLeggings, pyratiteBoots,
    natureHelmet, natureChestplate, natureLeggings, natureBoots,
    depthHelmet, depthChestplate, depthLeggings, depthBoots,
    infernalHelmet, infernalChestplate, infernalLeggings, infernalBoots,
    awakenedVoidHelmet, awakenedVoidChestplate, awakenedVoidLeggings, awakenedVoidBoots,
    phantasmHelmet, phantasmChestplate, phantasmLeggings, phantasmBoots,

    // materials
    rawCobalt, runicDust, amberGem, amethystGem, rubyGem, sapphireGem, wickedAmethyst, soulShard, unchargedShard, spiderFang, remains,
    natureGift, oceanicShell, infernalStone, rottenBone, marshCloth, painCrystal, nihilityShard, illusionStone, elementalCrystal,
    natureCore, aquariusCore, infernalCore, voidCore,
    valoriaPortalFrameShard, blackGoldUpgrade, natureUpgrade, aquariusUpgrade, infernalUpgrade, voidUpgrade,
    gaibRoot, karusakanRoot, shadeBlossomLeaf, aloePiece,
    dunestoneBrick, tombstoneBrick, ambaneStoneBrick, limestoneBrick, crystalStoneBrick, voidStoneBrick,
    bronzeIngot, bronzeNugget, pearliumIngot, cobaltIngot, cobaltNugget, etherealShard, blackGold, blackGoldNugget, ancientIngot, natureIngot, aquariusIngot, infernalIngot, voidIngot, pyratite, relicGold, ancientShard,
    emptyGazer, emptyWinglet, emptyTotem,

    // skin
    arcaneTrim, muramasaFragment, murasameFragment, fishFragment, neroFragment, cyberpunkQunatumFragment, midnightQunatumFragment, theFallenTrim, starDivider,

    // loot bags
    minersBag, gemBag, necromancerTreasureBag, crystalTreasureBag, dirtGeode, stoneGeode,

    // boss summonables
    necromancerGrimoire, suspciousGem,

    // misc
    debugItem, summonBook, crystalSummonBook, soulCollectorEmpty, soulCollector, lexicon, cryptPage, voidKey, spectralBladeThrown, pick,

    // weapons
    club, bronzeSword, spectralBlade, corpseCleaver,
    samuraiKunai, samuraiPoisonedKunai, samuraiKatana, samuraiLongBow,
    silkenBlade, silkenKunai, silkenWakizashi, meatCutter, quantumReaper, bloodHound,
    blazeReap, gunpowderCharge, pyratiteCharge,
    ironKatana, goldenKatana, diamondKatana, netheriteKatana, murasama,
    ironScythe, goldenScythe, diamondScythe, netheriteScythe, beast,
    woodenSpear, stoneSpear, ironSpear, goldenSpear, diamondSpear, netheriteSpear, pyratiteSpear, glaive,
    woodenRapier, stoneRapier, ironRapier, goldenRapier, diamondRapier, netheriteRapier,
    throwableBomb, dynamite, crystalShard,

    // tools
    pearliumSword, pearliumPickaxe, pearliumAxe,
    cobaltSword, cobaltPickaxe, cobaltAxe, cobaltShovel, cobaltHoe,
    etherealSword, etherealSpear, etherealPickaxe, etherealAxe,
    ent, natureScythe, naturePickaxe, natureAxe, natureShovel, natureHoe, natureBow,
    coralReef, aquariusScythe, aquariusPickaxe, aquariusAxe, aquariusShovel, aquariusHoe, aquariusBow,
    infernalSword, infernalScythe, infernalPickaxe, infernalAxe, infernalShovel, infernalHoe, infernalBow, infernalSpear,
    voidEdge, voidScythe, voidPickaxe, voidAxe, voidShovel, voidHoe, voidBow,
    phantom, phantasmBow, eternity, wickedArrow, pyratiteArrow, soulArrow,

    // event
    holidayCandy, holidayKatana, holidayPickaxe, holidayAxe, candyCorn, pumpkinBomb, wraithKatana, reaperScythe, dreadAxe, soulReaver,

    // accessories
    ironChain, ironNecklaceAmber, ironNecklaceDiamond, ironNecklaceEmerald, ironNecklaceRuby, ironNecklaceSapphire, ironNecklaceHealth, ironNecklaceArmor, ironNecklaceWealth,
    goldenChain, goldenNecklaceAmber, goldenNecklaceDiamond, goldenNecklaceEmerald, goldenNecklaceRuby, goldenNecklaceSapphire, goldenNecklaceHealth, goldenNecklaceArmor, goldenNecklaceWealth,
    netheriteChain, netheriteNecklaceAmber, netheriteNecklaceDiamond, netheriteNecklaceEmerald, netheriteNecklaceRuby, netheriteNecklaceSapphire, netheriteNecklaceHealth, netheriteNecklaceArmor, netheriteNecklaceWealth,
    leatherBelt, samuraiBelt,
    ironRing, ironRingAmber, ironRingDiamond, ironRingRuby, ironRingEmerald, ironRingSapphire,
    goldenRing, goldenRingAmber, goldenRingDiamond, goldenRingRuby, goldenRingEmerald, goldenRingSapphire,
    netheriteRing, netheriteRingAmber, netheriteRingDiamond, netheriteRingRuby, netheriteRingEmerald, netheriteRingSapphire,
    leatherGloves, ironGloves, goldenGloves, diamondGloves, netheriteGloves,
    amberTotem, amberWinglet, amberGazer, emeraldTotem, emeraldWinglet, emeraldGazer, amethystTotem, amethystWinglet, amethystGazer, rubyTotem, rubyWinglet, rubyGazer,
    brokenMonocle, monocle, jewelryBag, pickNecklace,
    bandage, devilHeart, harmonyHeart, medicatedDevilHeart, medicatedHarmonyHeart, elementalCharm,
    skeletalVambrace, magmaticVambrace, magmaticGauntlet,

    // runes
    lesserRune, lesserRuneVision, lesserRuneWealth, lesserRuneCurses, lesserRuneStrength, lesserRuneAccuracy, lesserRuneDeep,
    rune, runeVision, runeWealth, runeCurses, runeStrength, runeAccuracy, runeDeep, runePyro, runeCold,
    aloeBandage, aloeBandageUpgraded, shadeBlossomBandage, // food
    applePie, eyeChunk, taintedBerries, cookedGlowVioletSprout, cookedAbyssalGlowfern, goblinMeat, cookedGoblinMeat, cup, cacaoCup, coffeeCup, teaCup, greenTeaCup, woodenCup, beerCup, rumCup, bottle, kvassBottle, wineBottle, akvavitBottle, sakeBottle, liquorBottle, rumBottle, meadBottle, cognacBottle, whiskeyBottle, cokeBottle, toxinsBottle,

    necromancerMusicDisc,
    wickedShield,

    // spawn eggs
    pumpkinContract, goblin, dryador, entMob, draugr, swampWanderer, scourge, maggot, wickedCrystal, crystal, sorcerer, necromancer, undead, shadewoodSpider, devil, troll, corruptedTroll, fleshSentinel, mannequin;

    public static void load(IEventBus eventBus){
        shadewoodBoat = BLOCK_ITEMS.register("shadewood_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1), EntityTypeRegistry.SHADEWOOD_BOAT));
        shadewoodChestBoat = BLOCK_ITEMS.register("shadewood_chest_boat", () -> new CustomChestBoatItem(new Item.Properties().stacksTo(1), EntityTypeRegistry.SHADEWOOD_CHEST_BOAT));
        shadewoodSign = BLOCK_ITEMS.register("shadewood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.shadewoodSign.get(), BlockRegistry.shadewoodWallSign.get()));
        shadewoodHangingSign = BLOCK_ITEMS.register("shadewood_hanging_sign", () -> new HangingSignItem(BlockRegistry.shadewoodHangingSign.get(), BlockRegistry.shadewoodWallHangingSign.get(), new Item.Properties().stacksTo(16)));
        eldritchBoat = BLOCK_ITEMS.register("eldritch_boat", () -> new CustomBoatItem(new Properties().stacksTo(1), EntityTypeRegistry.ELDRITCH_BOAT));
        eldritchChestBoat = BLOCK_ITEMS.register("eldritch_chest_boat", () -> new CustomChestBoatItem(new Item.Properties().stacksTo(1), EntityTypeRegistry.ELDRITCH_CHEST_BOAT));
        eldritchSign = BLOCK_ITEMS.register("eldritch_sign", () -> new SignItem(new Item.Properties().stacksTo(16), BlockRegistry.eldritchSign.get(), BlockRegistry.eldritchWallSign.get()));
        eldritchHangingSign = BLOCK_ITEMS.register("eldritch_hanging_sign", () -> new HangingSignItem(BlockRegistry.eldritchHangingSign.get(), BlockRegistry.eldritchWallHangingSign.get(), new Item.Properties().stacksTo(16)));

        blackGoldHelmet = registerItem("black_gold_helmet", () -> new PercentageArmorItem(ArmorRegistry.BLACK_GOLD, Type.HELMET, new Properties()));
        blackGoldChestplate = registerItem("black_gold_chestplate", () -> new PercentageArmorItem(ArmorRegistry.BLACK_GOLD, Type.CHESTPLATE, new Properties()));
        blackGoldLeggings = registerItem("black_gold_leggings", () -> new PercentageArmorItem(ArmorRegistry.BLACK_GOLD, Type.LEGGINGS, new Properties()));
        blackGoldBoots = registerItem("black_gold_boots", () -> new PercentageArmorItem(ArmorRegistry.BLACK_GOLD, Type.BOOTS, new Properties()));
        cobaltHelmet = registerItem("cobalt_helmet", () -> new SkinableArmorItem(ArmorRegistry.COBALT, ArmorItem.Type.HELMET, new Item.Properties()));
        cobaltChestplate = registerItem("cobalt_chestplate", () -> new SkinableArmorItem(ArmorRegistry.COBALT, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        cobaltLeggings = registerItem("cobalt_leggings", () -> new SkinableArmorItem(ArmorRegistry.COBALT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        cobaltBoots = registerItem("cobalt_boots", () -> new SkinableArmorItem(ArmorRegistry.COBALT, ArmorItem.Type.BOOTS, new Item.Properties()));
        samuraiKabuto = registerItem("samurai_kabuto", () -> new SamuraiArmorItem(ArmorRegistry.SAMURAI, ArmorItem.Type.HELMET, new Item.Properties()));
        samuraiChestplate = registerItem("samurai_chestplate", () -> new SamuraiArmorItem(ArmorRegistry.SAMURAI, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
        samuraiLeggings = registerItem("samurai_leggings", () -> new SamuraiArmorItem(ArmorRegistry.SAMURAI, ArmorItem.Type.LEGGINGS, new Item.Properties()));
        samuraiBoots = registerItem("samurai_boots", () -> new SamuraiArmorItem(ArmorRegistry.SAMURAI, ArmorItem.Type.BOOTS, new Item.Properties()));
        marshHelmet = registerItem("marsh_helmet", () -> new SkinableArmorItem(ArmorRegistry.MARSH, ArmorItem.Type.HELMET, new Item.Properties().rarity(RarityRegistry.MARSH)));
        marshChestplate = registerItem("marsh_chestplate", () -> new SkinableArmorItem(ArmorRegistry.MARSH, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(RarityRegistry.MARSH)));
        marshLeggings = registerItem("marsh_leggings", () -> new SkinableArmorItem(ArmorRegistry.MARSH, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(RarityRegistry.MARSH)));
        marshBoots = registerItem("marsh_boots", () -> new SkinableArmorItem(ArmorRegistry.MARSH, ArmorItem.Type.BOOTS, new Item.Properties().rarity(RarityRegistry.MARSH)));
        etherealHelmet = registerEffectArmor("ethereal_helmet", ArmorItem.Type.HELMET, ArmorRegistry.ETHEREAL, new Item.Properties().rarity(RarityRegistry.ETHEREAL));
        etherealChestplate = registerEffectArmor("ethereal_chestplate", ArmorItem.Type.CHESTPLATE, ArmorRegistry.ETHEREAL, new Item.Properties().rarity(RarityRegistry.ETHEREAL));
        etherealLeggings = registerEffectArmor("ethereal_leggings", ArmorItem.Type.LEGGINGS, ArmorRegistry.ETHEREAL, new Item.Properties().rarity(RarityRegistry.ETHEREAL));
        etherealBoots = registerEffectArmor("ethereal_boots", ArmorItem.Type.BOOTS, ArmorRegistry.ETHEREAL, new Item.Properties().rarity(RarityRegistry.ETHEREAL));
        spiderHelmet = registerItem("spider_helmet", () -> new HitEffectArmorItem(ArmorRegistry.SPIDER, ArmorItem.Type.HELMET, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.5f, new MobEffectInstance(MobEffects.WEAKNESS, 60)));
        spiderChestplate = registerItem("spider_chestplate", () -> new HitEffectArmorItem(ArmorRegistry.SPIDER, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.5f, new MobEffectInstance(MobEffects.BLINDNESS, 60)));
        spiderLeggings = registerItem("spider_leggings", () -> new HitEffectArmorItem(ArmorRegistry.SPIDER, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.5f, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60)));
        spiderBoots = registerItem("spider_boots", () -> new HitEffectArmorItem(ArmorRegistry.SPIDER, ArmorItem.Type.BOOTS, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.5f, new MobEffectInstance(MobEffects.CONFUSION, 60)));
        pyratiteHelmet = registerItem("pyratite_helmet",  () -> new InfernalArmorItem(Type.HELMET, ArmorRegistry.PYRATITE, new Item.Properties().rarity(RarityRegistry.PYRATITE)));
        pyratiteChestplate = registerItem("pyratite_chestplate",  () -> new InfernalArmorItem(Type.CHESTPLATE, ArmorRegistry.PYRATITE, new Item.Properties().rarity(RarityRegistry.PYRATITE)));
        pyratiteLeggings = registerItem("pyratite_leggings",  () -> new InfernalArmorItem(Type.LEGGINGS, ArmorRegistry.PYRATITE, new Item.Properties().rarity(RarityRegistry.PYRATITE)));
        pyratiteBoots = registerItem("pyratite_boots",  () -> new InfernalArmorItem(Type.BOOTS, ArmorRegistry.PYRATITE, new Item.Properties().rarity(RarityRegistry.PYRATITE)));

        // elemental
        natureHelmet = registerEffectArmor("nature_helmet", Type.HELMET, ArmorRegistry.NATURE, new Item.Properties().rarity(RarityRegistry.NATURE));
        natureChestplate = registerEffectArmor("nature_chestplate", Type.CHESTPLATE, ArmorRegistry.NATURE, new Item.Properties().rarity(RarityRegistry.NATURE));
        natureLeggings = registerEffectArmor("nature_leggings", Type.LEGGINGS, ArmorRegistry.NATURE, new Item.Properties().rarity(RarityRegistry.NATURE));
        natureBoots = registerEffectArmor("nature_boots", Type.BOOTS, ArmorRegistry.NATURE, new Item.Properties().rarity(RarityRegistry.NATURE));
        depthHelmet = registerEffectArmor("depth_helmet", Type.HELMET, ArmorRegistry.DEPTH, new Item.Properties().rarity(RarityRegistry.AQUARIUS));
        depthChestplate = registerEffectArmor("depth_chestplate", Type.CHESTPLATE, ArmorRegistry.DEPTH, new Item.Properties().rarity(RarityRegistry.AQUARIUS));
        depthLeggings = registerEffectArmor("depth_leggings", Type.LEGGINGS, ArmorRegistry.DEPTH, new Item.Properties().rarity(RarityRegistry.AQUARIUS));
        depthBoots = registerEffectArmor("depth_boots", Type.BOOTS, ArmorRegistry.DEPTH, new Item.Properties().rarity(RarityRegistry.AQUARIUS));
        infernalHelmet = registerItem("infernal_helmet", () -> new InfernalArmorItem(Type.HELMET, ArmorRegistry.INFERNAL, new Item.Properties().rarity(RarityRegistry.INFERNAL).fireResistant()));
        infernalChestplate = registerItem("infernal_chestplate", () -> new InfernalArmorItem(Type.CHESTPLATE, ArmorRegistry.INFERNAL, new Item.Properties().rarity(RarityRegistry.INFERNAL).fireResistant()));
        infernalLeggings = registerItem("infernal_leggings", () -> new InfernalArmorItem(Type.LEGGINGS, ArmorRegistry.INFERNAL, new Item.Properties().rarity(RarityRegistry.INFERNAL).fireResistant()));
        infernalBoots = registerItem("infernal_boots", () -> new InfernalArmorItem(Type.BOOTS, ArmorRegistry.INFERNAL, new Item.Properties().rarity(RarityRegistry.INFERNAL).fireResistant()));
        awakenedVoidHelmet = registerItem("awakened_void_helmet", () -> new VoidArmorItem(Type.HELMET, ArmorRegistry.VOID, new Item.Properties().rarity(RarityRegistry.VOID).fireResistant()));
        awakenedVoidChestplate = registerItem("awakened_void_chestplate", () -> new VoidArmorItem(Type.CHESTPLATE, ArmorRegistry.VOID, new Item.Properties().rarity(RarityRegistry.VOID).fireResistant()));
        awakenedVoidLeggings = registerItem("awakened_void_leggings", () -> new VoidArmorItem(Type.LEGGINGS, ArmorRegistry.VOID, new Item.Properties().rarity(RarityRegistry.VOID).fireResistant()));
        awakenedVoidBoots = registerItem("awakened_void_boots", () -> new VoidArmorItem(Type.BOOTS, ArmorRegistry.VOID, new Item.Properties().rarity(RarityRegistry.VOID).fireResistant()));
        phantasmHelmet = registerItem("phantasm_helmet", () -> new PhantasmArmorItem(Type.HELMET, ArmorRegistry.PHANTASM, new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant()));
        phantasmChestplate = registerItem("phantasm_chestplate", () -> new PhantasmArmorItem(Type.CHESTPLATE, ArmorRegistry.PHANTASM, new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant()));
        phantasmLeggings = registerItem("phantasm_leggings", () -> new PhantasmArmorItem(Type.LEGGINGS, ArmorRegistry.PHANTASM, new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant()));
        phantasmBoots = registerItem("phantasm_boots", () -> new PhantasmArmorItem(Type.BOOTS, ArmorRegistry.PHANTASM, new Item.Properties().rarity(RarityRegistry.PHANTASM).fireResistant()));

        //materials
        rawCobalt = registerItem("raw_cobalt");
        runicDust = registerItem("runic_dust");
        amberGem = registerItem("amber_gem", () -> new CrushableItem(new Item.Properties()));
        amethystGem = registerItem("amethyst_gem", () -> new CrushableItem(new Item.Properties()));
        rubyGem = registerItem("ruby_gem", () -> new CrushableItem(new Item.Properties()));
        sapphireGem = registerItem("sapphire_gem", () -> new CrushableItem(new Item.Properties()));
        wickedAmethyst = registerItem("wicked_amethyst", () -> new TransformShardItem(new Item.Properties().rarity(RarityRegistry.VOID)));
        soulShard = registerItem("soul_shard", () -> new TransformShardItem(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        unchargedShard = registerItem("uncharged_shard");
        spiderFang = registerItem("spider_fang");
        remains = registerItem("remains");
        gaibRoot = registerItem("gaib_root", () -> new Item(new Item.Properties().stacksTo(16)));
        karusakanRoot = registerItem("karusakan_root", () -> new Item(new Item.Properties().stacksTo(16)));
        aloePiece = registerItem("aloe_piece");
        shadeBlossomLeaf = registerItem("shade_blossom_leaf");
        dunestoneBrick = registerItem("dunestone_brick");
        tombstoneBrick = registerItem("tombstone_brick");
        ambaneStoneBrick = registerItem("ambane_stone_brick");
        limestoneBrick = registerItem("limestone_brick");
        crystalStoneBrick = registerItem("crystal_stone_brick");
        voidStoneBrick = registerItem("void_stone_brick");
        bronzeNugget = registerItem("bronze_nugget");
        cobaltNugget = registerItem("cobalt_nugget");
        blackGoldNugget = registerItem("black_gold_nugget");
        bronzeIngot = registerItem("bronze_ingot");
        pearliumIngot = registerItem("pearlium_ingot");
        cobaltIngot = registerItem("cobalt_ingot");
        etherealShard = registerItem("ethereal_shard", () -> new Item(new Item.Properties().rarity(RarityRegistry.ETHEREAL)));
        blackGold = registerItem("black_gold_ingot");
        ancientIngot = registerItem("ancient_ingot");
        natureIngot = registerItem("nature_ingot", () -> new Item(new Item.Properties().rarity(RarityRegistry.NATURE)));
        aquariusIngot = registerItem("aquarius_ingot", () -> new Item(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        infernalIngot = registerItem("infernal_ingot", () -> new Item(new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        voidIngot = registerItem("void_ingot", () -> new Item(new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
        pyratite = registerItem("pyratite", () -> new Item(new Item.Properties().rarity(RarityRegistry.INFERNAL)));
        relicGold = registerItem("relic_gold", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        ancientShard = registerItem("ancient_shard", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        natureGift = registerItem("nature_gift", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.NATURE), 0.35f, ColorParticleData.create(Pal.nature, Pal.vividCyan).build(), ParticleRegistry.SPHERE.get()));
        rottenBone = registerItem("rotten_bone", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.MARSH), 0.35f, ColorParticleData.create(Pal.vividGreen, Pal.cyan).build(), ParticleRegistry.SPHERE.get()));
        marshCloth = registerItem("marsh_cloth", () -> new Item(new Item.Properties().rarity(RarityRegistry.MARSH)));
        oceanicShell = registerItem("oceanic_shell", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.AQUARIUS), 0.35f, ColorParticleData.create(Pal.oceanic, Pal.magmatic).build(), ParticleRegistry.SPHERE.get()));
        infernalStone = registerItem("infernal_stone", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.INFERNAL), 0.35f, ColorParticleData.create(Pal.infernalBright, Pal.magmatic).build(), ParticleRegistry.SPHERE.get()));
        painCrystal = registerItem("pain_crystal", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.BLOODY), 0.35f, ColorParticleData.create(Pal.strongRed, Pal.moderateViolet).build(), ParticleRegistry.SPHERE.get()));
        nihilityShard = registerItem("nihility_shard", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.VOID), 0.35f, ColorParticleData.create(Pal.softMagenta).build(), ParticleRegistry.SPHERE.get()));
        illusionStone = registerItem("illusion_stone", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.PHANTASM), 0.35f, ColorParticleData.create(Pal.softBlue, Col.white).build(), ParticleRegistry.SPHERE.get()));
        elementalCrystal = registerItem("elemental_crystal", () -> new ParticleMaterialItem(new Item.Properties().rarity(RarityRegistry.ELEMENTAL), 0.35f, ColorParticleData.create(Pal.nature, Pal.strongRed).build(), ParticleRegistry.SPHERE.get()));
        natureCore = registerItem("nature_core", () -> new CoreItem(ParticleRegistry.SPHERE.get(), new Item.Properties().fireResistant().rarity(RarityRegistry.NATURE), 1, Pal.nature, Pal.vividCyan, "nature_core"));
        aquariusCore = registerItem("aquarius_core", () -> new CoreItem(ParticleRegistry.SPHERE.get(), new Item.Properties().fireResistant().rarity(RarityRegistry.AQUARIUS), 1, Pal.oceanic, Pal.magmatic, "aquarius_core"));
        infernalCore = registerItem("infernal_core", () -> new CoreItem(ParticleRegistry.SPHERE.get(), new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL), 1, Pal.infernalBright, Pal.magmatic, "infernal_core"));
        voidCore = registerItem("void_core", () -> new CoreItem(ParticleRegistry.SPHERE.get(), new Item.Properties().fireResistant().rarity(RarityRegistry.VOID), 1, Pal.softMagenta, Pal.softMagenta, "void_core"));

        valoriaPortalFrameShard = registerItem("valoria_portal_frame_shard");
        blackGoldUpgrade = registerItem("black_gold_upgrade_smithing_template", () -> new ElementalSmithingTemplateItem(new Item.Properties()));
        natureUpgrade = registerItem("nature_upgrade_smithing_template", () -> new ElementalSmithingTemplateItem(new Item.Properties()));
        aquariusUpgrade = registerItem("aquarius_upgrade_smithing_template", () -> new ElementalSmithingTemplateItem(new Item.Properties()));
        infernalUpgrade = registerItem("infernal_upgrade_smithing_template", () -> new ElementalSmithingTemplateItem(new Item.Properties()));
        voidUpgrade = registerItem("void_upgrade_smithing_template", () -> new ElementalSmithingTemplateItem(new Item.Properties()));
        cyberpunkQunatumFragment = registerItem("cyberpunk_quantum_reaper_fragment", () -> new SkinFragmentItem(SkinsRegistry.CYBERPUNK, new Item.Properties(), () -> quantumReaper.get()));
        midnightQunatumFragment = registerItem("midnight_quantum_reaper_fragment", () -> new SkinFragmentItem(SkinsRegistry.MIDNIGHT, new Item.Properties(), () -> quantumReaper.get()));
        starDivider = registerItem("star_divider_fragment", () -> new SkinFragmentItem(SkinsRegistry.STAR_DIVIDER, new Item.Properties(), () -> quantumReaper.get()));
        muramasaFragment = registerItem("muramasa_fragment", () -> new SkinFragmentItem(SkinsRegistry.MURAMASA, new Item.Properties(), () -> murasama.get()));
        murasameFragment = registerItem("murasame_fragment", () -> new SkinFragmentItem(SkinsRegistry.MURASAME, new Item.Properties(), () -> murasama.get()));
        fishFragment = registerItem("fish_fragment", () -> new SkinFragmentItem(SkinsRegistry.FISH, new Item.Properties(), () -> murasama.get()));
        neroFragment = registerItem("nero_fragment", () -> new SkinFragmentItem(SkinsRegistry.NERO, new Item.Properties(), () -> phantom.get()));

        theFallenTrim = registerItem("the_fallen_trim", () -> new SkinTrimItem(SkinsRegistry.THE_FALLEN_COLLECTOR, new Item.Properties()));
        arcaneTrim = registerItem("arcane_trim", () -> new SkinTrimItem(SkinsRegistry.ARCANE_GOLD, new Item.Properties()));

        // loot bags
        dirtGeode = registerItem("dirt_geode", () -> new CrushableItem(new Item.Properties().rarity(Rarity.RARE)));
        stoneGeode = registerItem("stone_geode", () -> new CrushableItem(new Item.Properties().rarity(Rarity.RARE)));
        minersBag = registerItem("miners_bag", () -> new TreasureBag(new ResourceLocation(Valoria.ID, "items/miners_bag"), new Item.Properties().rarity(Rarity.EPIC)));
        gemBag = registerItem("gem_bag", () -> new TreasureBag(new ResourceLocation(Valoria.ID, "items/gem_bag"), new Item.Properties().rarity(Rarity.EPIC)));
        necromancerTreasureBag = registerItem("necromancer_treasure_bag", () -> new TreasureBag(new ResourceLocation(Valoria.ID, "items/necromancer_treasure_bag"), new Item.Properties().rarity(Rarity.EPIC)));
        crystalTreasureBag = registerItem("wicked_crystal_treasure_bag", () -> new TreasureBag(new ResourceLocation(Valoria.ID, "items/wicked_crystal_treasure_bag"), new Item.Properties().rarity(Rarity.EPIC)));

        // boss summonables
        necromancerGrimoire = registerItem("necromancer_grimoire", () -> new Item(new Item.Properties()){
            @Override
            public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
                super.appendHoverText(stack, world, tooltip, flags);
                tooltip.add(Component.translatable("tooltip.valoria.boss_summonable", EntityTypeRegistry.NECROMANCER.get().getDescription()).withStyle(ChatFormatting.GRAY));
            }
        });

        suspciousGem = registerItem("suspicious_gem", () -> new Item(new Item.Properties()){
            @Override
            public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
                super.appendHoverText(stack, world, tooltip, flags);
                tooltip.add(Component.translatable("tooltip.valoria.boss_summonable", EntityTypeRegistry.WICKED_CRYSTAL.get().getDescription()).withStyle(ChatFormatting.GRAY));
            }
        });

        // misc
        debugItem = registerItem("debug_item", () -> new DebugItem(new Item.Properties()));
        summonBook = registerItem("summon_book", () -> new SummonBook(30, 3, new Item.Properties().rarity(Rarity.EPIC)));
        crystalSummonBook = registerItem("crystal_summon_book", () -> new CrystalSummonBook(30, 3, new Item.Properties().rarity(Rarity.EPIC)));
        soulCollectorEmpty = registerItem("soul_collector_empty", () -> new SoulCollectorItem(new Item.Properties().stacksTo(1).rarity(RarityRegistry.PHANTASM)));
        soulCollector = registerItem("soul_collector", () -> new SoulCollectorItem(50, 50, new Item.Properties().rarity(RarityRegistry.PHANTASM)));
        lexicon = registerItem("lexicon", () -> new LexiconItem(new Item.Properties().stacksTo(1)));
        cryptPage = registerItem("page", () -> new LexiconPageItem(new Item.Properties().stacksTo(1), RegisterUnlockables.CRYPT, "lexicon.valoria.crypt.name"));
        voidKey = registerItem("void_key", () -> new Item(new Item.Properties().stacksTo(16).rarity(RarityRegistry.VOID)));
        pick = registerItem("prospectors_pick", () -> new PickItem(new Item.Properties().fireResistant().stacksTo(1).durability(64), 1, -2.8f, 5));

        // weapons
        club = registerItem("club", () -> new HitEffectItem(Tiers.WOOD, 5, -3.2f, new Item.Properties(), 0.1f, new MobEffectInstance(EffectsRegistry.STUN.get(), 60, 0)));
        bronzeSword = registerItem("bronze_sword", () -> new SwordItem(ModItemTier.BRONZE, 6, -2.4f, new Item.Properties()));
        quantumReaper = registerItem("quantum_reaper", () -> new SwordItem(ModItemTier.NONE, 8, -3f, new Item.Properties().rarity(RarityRegistry.VOID)){
            @Override
            public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags){
                super.appendHoverText(stack, world, tooltip, flags);
                Utils.Items.addContributorTooltip(stack, tooltip);
            }
        });

        bloodHound = registerItem("bloodhound", () -> new HoundItem(ModItemTier.BLOOD, 6, -2.2f, new Item.Properties()));
        blazeReap = registerItem("blaze_reap", () -> new BlazeReapItem(ModItemTier.NONE, 3, -3.4f, new Item.Properties()));
        gunpowderCharge = registerItem("gunpowder_charge", () -> new GunpowderCharge(4f, 25f, new Item.Properties().stacksTo(1)));
        pyratiteCharge = registerItem("pyratite_charge", () -> new GunpowderCharge(6f, 40f, new Item.Properties().stacksTo(1)));
        spectralBlade = registerItem("spectral_blade", () -> new SpectralBladeItem(3, -2.3f, new Item.Properties().durability(852)));
        corpseCleaver = registerItem("corpsecleaver", () -> new CorpseCleaverItem(ModItemTier.BLOOD, 2, -2.4F, new Item.Properties().durability(1151).rarity(RarityRegistry.BLOODY)));
        throwableBomb = registerItem("throwable_bomb", () -> new ThrowableBombItem(Level.ExplosionInteraction.NONE, 2.45f, 60, new Item.Properties().stacksTo(16)));
        dynamite = registerItem("dynamite", () -> new ThrowableBombItem(3f, 60, new Item.Properties().stacksTo(16)));
        crystalShard = registerItem("crystal_shard", () -> new CrystalShardItem(6d, new Item.Properties().stacksTo(16), new MobEffectInstance(MobEffects.WITHER, 120), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60)));

        // winter
        holidayCandy = registerItem("holiday_candy", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
        holidayKatana = registerItem("holiday_katana", () -> new KatanaItem(ModItemTier.HOLIDAY, 2, -2.2f, new Item.Properties()));
        holidayPickaxe = registerItem("holiday_pickaxe", () -> new ValoriaPickaxe(ModItemTier.HOLIDAY, -1, -3f, new Item.Properties()));
        holidayAxe = registerItem("holiday_axe", () -> new AxeItem(ModItemTier.HOLIDAY, 1, -3f, new Item.Properties()));

        // halloween
        candyCorn = registerItem("candy_corn", () -> new Item(new Item.Properties().rarity(RarityRegistry.HALLOWEEN).stacksTo(64).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
        pumpkinBomb = registerItem("pumpkin_bomb", () -> new ThrowableBombItem(new Item.Properties().rarity(RarityRegistry.HALLOWEEN).stacksTo(16)));
        wraithKatana = registerItem("wraith_katana", () -> new KatanaItem.Builder(3, -2.2f, new Item.Properties().rarity(RarityRegistry.HALLOWEEN)).setTier(ModItemTier.HALLOWEEN).setDashDistance(1.6f).setDashSound(SoundsRegistry.HALLOWEEN_SLICE.get()).removeLargeModelCheck().setOverlay(new ResourceLocation(Valoria.ID, "textures/gui/overlay/roots.png")).usePacket(Pal.mandarin.toJava()).build());
        reaperScythe = registerItem("reaper_scythe", () -> new ScytheItem.Builder(9, -3.0f, new Properties().fireResistant().rarity(RarityRegistry.HALLOWEEN)).setEffects(0.5f, new MobEffectInstance(MobEffects.DARKNESS, 90, 0)).setAttackSound(SoundsRegistry.HALLOWEEN_SLICE.get()).setTier(ModItemTier.HALLOWEEN).build());
        dreadAxe = registerItem("dread_axe", () -> new AxeItem(ModItemTier.HALLOWEEN, 6.5f, -2.8f, new Item.Properties().rarity(RarityRegistry.HALLOWEEN)));
        soulReaver = registerItem("soul_reaver", () -> new HitEffectItem(ModItemTier.HALLOWEEN, 4, -2.8f, new Item.Properties().rarity(RarityRegistry.HALLOWEEN), 0.25f, new MobEffectInstance(MobEffects.DARKNESS, 40, 0), new MobEffectInstance(MobEffects.WEAKNESS, 60, 1)));
        spectralBladeThrown = registerItem("spectral_blade_thrown"); // for rendering
        woodenSpear = registerItem("wooden_spear", () -> new SpearItem(Tiers.WOOD, 3, -3f, new Item.Properties()));
        stoneSpear = registerItem("stone_spear", () -> new SpearItem(Tiers.STONE, 3, -3f, new Item.Properties()));
        ironSpear = registerItem("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -3f, new Item.Properties()));
        goldenSpear = registerItem("golden_spear", () -> new SpearItem(Tiers.GOLD, 5, -2.9f, new Item.Properties()));
        diamondSpear = registerItem("diamond_spear", () -> new SpearItem(Tiers.DIAMOND, 5, -2.9f, new Item.Properties()));
        netheriteSpear = registerItem("netherite_spear", () -> new SpearItem(Tiers.NETHERITE, 5, -2.9f, new Item.Properties()));
        pyratiteSpear = registerItem("pyratite_spear", () -> new SpearItem(ModItemTier.PYRATITE, 5, -2.9f, new Item.Properties().rarity(RarityRegistry.INFERNAL)));
        glaive = registerItem("glaive", () -> new SpearItem(Tiers.IRON, 10, -3.2f, false, new Item.Properties()));
        woodenRapier = registerItem("wooden_rapier", () -> new SwordItem(Tiers.WOOD, 0, -1.8f, new Item.Properties()));
        stoneRapier = registerItem("stone_rapier", () -> new SwordItem(Tiers.STONE, 0, -1.8f, new Item.Properties()));
        ironRapier = registerItem("iron_rapier", () -> new SwordItem(Tiers.IRON, 1, -1.7f, new Item.Properties()));
        goldenRapier = registerItem("golden_rapier", () -> new SwordItem(Tiers.GOLD, 0, -1.5f, new Item.Properties()));
        diamondRapier = registerItem("diamond_rapier", () -> new SwordItem(Tiers.DIAMOND, 2, -1.5f, new Item.Properties()));
        netheriteRapier = registerItem("netherite_rapier", () -> new SwordItem(Tiers.NETHERITE, 2, -1.5f, new Item.Properties()));
        ironScythe = registerItem("iron_scythe", () -> new ScytheItem(Tiers.IRON, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties()));
        goldenScythe = registerItem("golden_scythe", () -> new ScytheItem.Builder(ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties()).setTier(Tiers.GOLD).setAttackCount(2, 4).build());
        diamondScythe = registerItem("diamond_scythe", () -> new ScytheItem(Tiers.DIAMOND, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties()));
        netheriteScythe = registerItem("netherite_scythe", () -> new ScytheItem(Tiers.NETHERITE, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties().fireResistant()));
        beast = registerItem("beast", () -> new BeastScytheItem.Builder(ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties()).setTier(ModItemTier.NONE).setCooldownTime(40, 150).build());
        ironKatana = registerItem("iron_katana", () -> new KatanaItem(Tiers.IRON, ToolStats.katana.damage, ToolStats.katana.speed, new Item.Properties()));
        goldenKatana = registerItem("golden_katana", () -> new KatanaItem(Tiers.GOLD, ToolStats.katana.damage, ToolStats.katana.speed, new Item.Properties()));
        diamondKatana = registerItem("diamond_katana", () -> new KatanaItem(Tiers.DIAMOND, ToolStats.katana.damage, ToolStats.katana.speed, 0.8f, new Item.Properties()));
        netheriteKatana = registerItem("netherite_katana", () -> new KatanaItem(Tiers.NETHERITE, ToolStats.katana.damage, ToolStats.katana.speed, 0.8f, new Item.Properties().fireResistant()));
        murasama = registerItem("murasama", ItemsRegistry::murasamaProps);
        samuraiKunai = registerItem("samurai_kunai", () -> new KunaiItem(3, -1.9F, new Item.Properties().durability(360)));
        samuraiPoisonedKunai = registerItem("samurai_poisoned_kunai", () -> new KunaiItem(3, -1.9F, new Item.Properties().durability(360), new MobEffectInstance(MobEffects.POISON, 170, 0)));
        samuraiKatana = registerItem("samurai_katana", () -> new KatanaItem(ModItemTier.SAMURAI, ToolStats.katana.damage, ToolStats.katana.speed, new Item.Properties()));
        samuraiLongBow = registerItem("samurai_long_bow", () -> new ConfigurableBowItem(3, new Item.Properties().stacksTo(1).durability(684)));
        silkenBlade = registerItem("silken_blade", () -> new HitEffectItem(ModItemTier.SPIDER, ToolStats.sword.damage, ToolStats.sword.speed, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.25f, new MobEffectInstance(MobEffects.DARKNESS, 120, 0), new MobEffectInstance(MobEffects.POISON, 45, 0)));
        silkenWakizashi = registerItem("silken_wakizashi", () -> new KatanaItem.Builder(ToolStats.katana.damage, ToolStats.katana.speed, new Item.Properties().rarity(RarityRegistry.SPIDER)).setTier(ModItemTier.SPIDER).setDashDistance(1.5f).setEffects(0.25f, new MobEffectInstance(MobEffects.DARKNESS, 120, 0), new MobEffectInstance(MobEffects.POISON, 45, 0)).removeLargeModelCheck().build());
        silkenKunai = registerItem("silken_kunai", () -> new KunaiItem(0, -2.2f, new Item.Properties().rarity(RarityRegistry.SPIDER), 0.25f, new MobEffectInstance(MobEffects.DARKNESS, 120, 0), new MobEffectInstance(MobEffects.POISON, 60, 1)));
        meatCutter = registerItem("meatcutter", () -> new Builder(ToolStats.katana.damage, ToolStats.katana.speed, new Properties().rarity(RarityRegistry.BLOODY)).setDashDistance(1f).setEffects(0.25f, new MobEffectInstance(EffectsRegistry.BLEEDING.get(), 120, 0)).build());

        pearliumSword = registerItem("pearlium_sword", () -> new ValoriaSword(ModItemTier.PEARLIUM, ToolStats.sword.damage, ToolStats.sword.speed, new Item.Properties()));
        pearliumPickaxe = registerItem("pearlium_pickaxe", () -> new ValoriaPickaxe(ModItemTier.PEARLIUM, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties()));
        pearliumAxe = registerItem("pearlium_axe", () -> new AxeItem(ModItemTier.PEARLIUM, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties()));

        cobaltSword = registerItem("cobalt_sword", () -> new ValoriaSword(ModItemTier.COBALT, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties()));
        cobaltPickaxe = registerItem("cobalt_pickaxe", () -> new ValoriaPickaxe(ModItemTier.COBALT, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties()));
        cobaltAxe = registerItem("cobalt_axe", () -> new AxeItem(ModItemTier.COBALT, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties()));
        cobaltShovel = registerItem("cobalt_shovel", () -> new ShovelItem(ModItemTier.COBALT, ToolStats.shovel.damage, ToolStats.shovel.speed, new Item.Properties()));
        cobaltHoe = registerItem("cobalt_hoe", () -> new HoeItem(ModItemTier.COBALT, (int)(ToolStats.hoe.damage), ToolStats.hoe.speed, new Item.Properties()));

        etherealSword = registerItem("ethereal_sword", () -> new ValoriaSword(ModItemTier.ETHEREAL, ToolStats.sword.damage, ToolStats.sword.speed, new Item.Properties().rarity(RarityRegistry.ETHEREAL)));
        etherealSpear = registerItem("ethereal_spear", () -> new SpearItem(ModItemTier.ETHEREAL, ToolStats.spear.damage, ToolStats.spear.speed, new Item.Properties().rarity(RarityRegistry.ETHEREAL)));
        etherealPickaxe = registerItem("ethereal_pickaxe", () -> new ValoriaPickaxe(ModItemTier.ETHEREAL, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties().rarity(RarityRegistry.ETHEREAL)));
        etherealAxe = registerItem("ethereal_axe", () -> new AxeItem(ModItemTier.ETHEREAL, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties().rarity(RarityRegistry.ETHEREAL)));

        ent = registerItem("ent", () -> new ValoriaSword(ModItemTier.NATURE, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        naturePickaxe = registerItem("nature_pickaxe", () -> new ValoriaPickaxe(ModItemTier.NATURE, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        natureScythe = registerItem("nature_scythe", () -> new ScytheItem(ModItemTier.NATURE, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        natureAxe = registerItem("nature_axe", () -> new AxeItem(ModItemTier.NATURE, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        natureShovel = registerItem("nature_shovel", () -> new ShovelItem(ModItemTier.NATURE, ToolStats.shovel.damage, ToolStats.shovel.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        natureHoe = registerItem("nature_hoe", () -> new HoeItem(ModItemTier.NATURE, (int)(ToolStats.hoe.damage), ToolStats.hoe.speed, new Item.Properties().rarity(RarityRegistry.NATURE)));
        natureBow = registerItem("nature_bow", () -> new ConfigurableBowItem(2, new Item.Properties().stacksTo(1).durability(1024).rarity(RarityRegistry.NATURE)));

        coralReef = registerItem("coral_reef", () -> new CoralReefItem(ModItemTier.AQUARIUS, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusScythe = registerItem("aquarius_scythe", () -> new AquariusScytheItem(ModItemTier.AQUARIUS, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusPickaxe = registerItem("aquarius_pickaxe", () -> new ValoriaPickaxe(ModItemTier.AQUARIUS, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusAxe = registerItem("aquarius_axe", () -> new AxeItem(ModItemTier.AQUARIUS, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusShovel = registerItem("aquarius_shovel", () -> new ShovelItem(ModItemTier.AQUARIUS, ToolStats.shovel.damage, ToolStats.shovel.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusHoe = registerItem("aquarius_hoe", () -> new HoeItem(ModItemTier.AQUARIUS, (int)(ToolStats.hoe.damage), ToolStats.hoe.speed, new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        aquariusBow = registerItem("aquarius_bow", () -> new ConfigurableBowItem(3, new Item.Properties().stacksTo(1).durability(1324).fireResistant().rarity(RarityRegistry.AQUARIUS)));

        infernalSword = registerItem("infernal_sword", () -> new MagmaSwordItem(ModItemTier.INFERNAL, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalScythe = registerItem("infernal_scythe", () -> new InfernalScytheItem(ModItemTier.INFERNAL, ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalSpear = registerItem("infernal_spear", () -> new SpearItem(ModItemTier.INFERNAL, ToolStats.spear.damage, ToolStats.spear.speed, new Item.Properties().fireResistant().stacksTo(1).durability(1684).rarity(RarityRegistry.INFERNAL)));
        infernalPickaxe = registerItem("infernal_pickaxe", () -> new ValoriaPickaxe(ModItemTier.INFERNAL, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalAxe = registerItem("infernal_axe", () -> new AxeItem(ModItemTier.INFERNAL, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalShovel = registerItem("infernal_shovel", () -> new ShovelItem(ModItemTier.INFERNAL, ToolStats.shovel.damage, ToolStats.shovel.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalHoe = registerItem("infernal_hoe", () -> new HoeItem(ModItemTier.INFERNAL, (int)(ToolStats.hoe.damage), ToolStats.hoe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.INFERNAL)));
        infernalBow = registerItem("infernal_bow", () -> new ConfigurableBowItem(EntityTypeRegistry.INFERNAL_ARROW, 4, 3, new Item.Properties().fireResistant().stacksTo(1).durability(1684).rarity(RarityRegistry.INFERNAL)));

        voidEdge = registerItem("void_edge", () -> new ValoriaSword(ModItemTier.NIHILITY, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties().rarity(RarityRegistry.VOID)));
        voidScythe = registerItem("void_scythe", () -> new ScytheItem.Builder(ToolStats.scythe.damage, ToolStats.scythe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)).setTier(ModItemTier.NIHILITY).setEffects(0.5f, new MobEffectInstance(MobEffects.DARKNESS, 90, 0)).build());
        voidPickaxe = registerItem("void_pickaxe", () -> new ValoriaPickaxe(ModItemTier.NIHILITY, ToolStats.pickaxe.damage, ToolStats.pickaxe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
        voidAxe = registerItem("void_axe", () -> new AxeItem(ModItemTier.NIHILITY, ToolStats.axe.damage, ToolStats.axe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
        voidShovel = registerItem("void_shovel", () -> new ShovelItem(ModItemTier.NIHILITY, ToolStats.shovel.damage, ToolStats.shovel.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
        voidHoe = registerItem("void_hoe", () -> new HoeItem(ModItemTier.NIHILITY, (int)(ToolStats.hoe.damage), ToolStats.hoe.speed, new Item.Properties().fireResistant().rarity(RarityRegistry.VOID)));
        voidBow = registerItem("bow_of_darkness", () -> new ConfigurableBowItem(4.25f, new Item.Properties().stacksTo(1).durability(2048).fireResistant().rarity(RarityRegistry.VOID)));
        phantom = registerItem("phantom", () -> new PhantomItem(ModItemTier.PHANTOM, ToolStats.large_sword.damage, ToolStats.large_sword.speed, new Item.Properties().rarity(RarityRegistry.PHANTASM)));
        phantasmBow = registerItem("phantasm_bow", () -> new ConfigurableBowItem(EntityTypeRegistry.PHANTOM_ARROW, 6, 4, new Item.Properties().fireResistant().stacksTo(1).durability(4028).rarity(RarityRegistry.PHANTASM)));
        wickedArrow = registerItem("wicked_arrow", () -> new WickedArrowItem(new Item.Properties().rarity(RarityRegistry.VOID)));
        pyratiteArrow = registerItem("pyratite_arrow", () -> new PyratiteArrowItem(new Item.Properties().rarity(RarityRegistry.PYRATITE)));
        soulArrow = registerItem("soul_arrow", () -> new SoulArrowItem(new Item.Properties().rarity(RarityRegistry.AQUARIUS)));
        eternity = registerItem("eternity");

        // accessories
        ironChain = registerItem("iron_chain", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
        ironNecklaceAmber = registerItem("iron_necklace_amber", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceDiamond = registerItem("iron_necklace_diamond", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceEmerald = registerItem("iron_necklace_emerald", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceRuby = registerItem("iron_necklace_ruby", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceSapphire = registerItem("iron_necklace_sapphire", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceHealth = registerItem("iron_necklace_health", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceArmor = registerItem("iron_necklace_armor", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        ironNecklaceWealth = registerItem("iron_necklace_wealth", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        goldenChain = registerItem("golden_chain", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
        goldenNecklaceAmber = registerItem("golden_necklace_amber", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceDiamond = registerItem("golden_necklace_diamond", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceEmerald = registerItem("golden_necklace_emerald", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceRuby = registerItem("golden_necklace_ruby", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceSapphire = registerItem("golden_necklace_sapphire", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceHealth = registerItem("golden_necklace_health", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceArmor = registerItem("golden_necklace_armor", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        goldenNecklaceWealth = registerItem("golden_necklace_wealth", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(200).rarity(Rarity.EPIC)));
        netheriteChain = registerItem("netherite_chain", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
        netheriteNecklaceAmber = registerItem("netherite_necklace_amber", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceDiamond = registerItem("netherite_necklace_diamond", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceEmerald = registerItem("netherite_necklace_emerald", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceRuby = registerItem("netherite_necklace_ruby", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceSapphire = registerItem("netherite_necklace_sapphire", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceHealth = registerItem("netherite_necklace_health", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.HEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceArmor = registerItem("netherite_necklace_armor", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.ARMOR, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        netheriteNecklaceWealth = registerItem("netherite_necklace_wealth", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.NECKLACE, AccessoryGem.WEALTH, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(500).rarity(Rarity.EPIC)));
        leatherBelt = registerItem("leather_belt", () -> new CurioItemProperty(ModItemTier.NONE, AccessoryType.BELT, AccessoryGem.BELT, AccessoryMaterial.LEATHER, new Item.Properties().stacksTo(1).durability(250).rarity(Rarity.EPIC)));
        samuraiBelt = registerItem("samurai_belt", () -> new CurioItemProperty(ModItemTier.NONE, AccessoryType.BELT, AccessoryGem.BELT, AccessoryMaterial.NONE, new Item.Properties().stacksTo(1).durability(640).rarity(Rarity.EPIC)));
        ironRing = registerItem("iron_ring", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.UNCOMMON)));
        ironRingAmber = registerItem("iron_ring_amber", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.EPIC)));
        ironRingDiamond = registerItem("iron_ring_diamond", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.EPIC)));
        ironRingEmerald = registerItem("iron_ring_emerald", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.EPIC)));
        ironRingRuby = registerItem("iron_ring_ruby", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.EPIC)));
        ironRingSapphire = registerItem("iron_ring_sapphire", () -> new CurioItemProperty(Tiers.IRON, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(450).rarity(Rarity.EPIC)));
        goldenRing = registerItem("golden_ring", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.UNCOMMON)));
        goldenRingAmber = registerItem("golden_ring_amber", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));
        goldenRingDiamond = registerItem("golden_ring_diamond", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));
        goldenRingEmerald = registerItem("golden_ring_emerald", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));
        goldenRingRuby = registerItem("golden_ring_ruby", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));
        goldenRingSapphire = registerItem("golden_ring_sapphire", () -> new CurioItemProperty(Tiers.GOLD, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));
        netheriteRing = registerItem("netherite_ring", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.NONE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.UNCOMMON)));
        netheriteRingAmber = registerItem("netherite_ring_amber", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.AMBER, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.EPIC)));
        netheriteRingDiamond = registerItem("netherite_ring_diamond", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.DIAMOND, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.EPIC)));
        netheriteRingEmerald = registerItem("netherite_ring_emerald", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.EMERALD, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.EPIC)));
        netheriteRingRuby = registerItem("netherite_ring_ruby", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.RUBY, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.EPIC)));
        netheriteRingSapphire = registerItem("netherite_ring_sapphire", () -> new CurioItemProperty(Tiers.NETHERITE, AccessoryType.RING, AccessoryGem.SAPPHIRE, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(650).rarity(Rarity.EPIC)));
        leatherGloves = registerItem("leather_gloves", () -> new DyeableGlovesItem(ModItemTier.NONE, new Item.Properties().stacksTo(1).durability(100).rarity(Rarity.UNCOMMON)));
        ironGloves = registerItem("iron_gloves", () -> new GlovesItem(Tiers.IRON, AccessoryGem.ARMOR, AccessoryMaterial.IRON, new Item.Properties().stacksTo(1).durability(190).rarity(Rarity.UNCOMMON)));
        goldenGloves = registerItem("golden_gloves", () -> new GlovesItem(Tiers.GOLD, AccessoryGem.TOUGH, AccessoryMaterial.GOLD, new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.UNCOMMON)));
        diamondGloves = registerItem("diamond_gloves", () -> new GlovesItem(Tiers.DIAMOND, AccessoryGem.DIAMOND, AccessoryMaterial.DIAMOND, new Item.Properties().stacksTo(1).durability(240).rarity(Rarity.UNCOMMON)));
        netheriteGloves = registerItem("netherite_gloves", () -> new GlovesItem(Tiers.NETHERITE, AccessoryGem.TANK, AccessoryMaterial.NETHERITE, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.UNCOMMON)));
        emptyGazer = registerItem("empty_gazer", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        emptyTotem = registerItem("empty_totem", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        emptyWinglet = registerItem("empty_winglet", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        amberTotem = registerItem("amber_golden_totem", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MAX_HEALTH, 5).put(Attributes.ATTACK_DAMAGE, 1).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, -0.15).build());
        amberWinglet = registerItem("amber_golden_winglet", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, 0.15).build());
        amberGazer = registerItem("amber_golden_gazer", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.ARMOR, Operation.MULTIPLY_TOTAL, 0.5).put(Attributes.ATTACK_SPEED, -0.25).build());
        emeraldTotem = registerItem("emerald_golden_totem", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MAX_HEALTH, 5).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, -0.15).put(Attributes.ATTACK_DAMAGE, 1).build());
        emeraldWinglet = registerItem("emerald_golden_winglet", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.LUCK, 0.25).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, 0.15).build());
        emeraldGazer = registerItem("emerald_golden_gazer", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.LUCK, 2).put(Attributes.ATTACK_SPEED, -0.25).build());
        amethystTotem = registerItem("amethyst_golden_totem", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MAX_HEALTH, 4).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, -0.15).build());
        amethystWinglet = registerItem("amethyst_golden_winglet", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, 0.15).put(Attributes.ATTACK_DAMAGE, 0.05).build());
        amethystGazer = registerItem("amethyst_golden_gazer", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.ATTACK_DAMAGE, 2).put(Attributes.ATTACK_SPEED, -0.25).build());
        rubyTotem = registerItem("ruby_golden_totem", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.MAX_HEALTH, 3).build());
        rubyWinglet = registerItem("ruby_golden_winglet", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)).put(Attributes.ATTACK_SPEED, 0.35).put(Attributes.MOVEMENT_SPEED, Operation.MULTIPLY_TOTAL, 0.15).build());
        rubyGazer = registerItem("ruby_golden_gazer", () -> new TalismanItem.Builder(new Item.Properties().stacksTo(1).durability(140).rarity(Rarity.EPIC)).put(Attributes.MAX_HEALTH, 2).put(Attributes.ATTACK_SPEED, -0.25).build());
        brokenMonocle = registerItem("broken_bloodsight_monocle", () -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
        monocle = registerItem("bloodsight_monocle", () -> new BloodSight(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).durability(300)));
        jewelryBag = registerItem("jewelry_bag", () -> new JewelryBagItem(new Item.Properties().stacksTo(1)));
        pickNecklace = registerItem("pick_necklace", () -> new PickNecklace(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        bandage = registerItem("bandage", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        devilHeart = registerItem("devil_heart", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        harmonyHeart = registerItem("harmony_heart", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        medicatedDevilHeart = registerItem("medicated_devil_heart", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        medicatedHarmonyHeart = registerItem("medicated_harmony_heart", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        elementalCharm = registerItem("elemental_charm", () -> new ImmunityItem(new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        skeletalVambrace = registerItem("skeletal_vambrace", () -> new VambraceItem("skeletal", Tiers.NETHERITE, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        magmaticVambrace = registerItem("magmatic_vambrace", () -> new VambraceItem("magmatic", Tiers.NETHERITE, new Item.Properties().stacksTo(1).durability(320).rarity(Rarity.EPIC)));
        magmaticGauntlet = registerItem("magmatic_gauntlet", () -> new MagmaticGauntletItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).durability(300).rarity(Rarity.EPIC)));

        lesserRune = registerItem("lesser_rune", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)){
            @Override
            public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
                super.appendHoverText(stack, world, tooltip, flags);
                tooltip.add(Component.translatable("tooltip.valoria.rune").withStyle(ChatFormatting.GRAY));
            }
        });

        lesserRuneVision = registerItem("lesser_rune_of_vision", () -> new CurioVision(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC),  1200));
        lesserRuneWealth = registerItem("lesser_rune_of_wealth", () -> new CurioWealth(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC)));
        lesserRuneCurses = registerItem("lesser_rune_of_curses", () -> new CurioCurses(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC)));
        lesserRuneStrength = registerItem("lesser_rune_of_strength", () -> new CurioStrength(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC)));
        lesserRuneAccuracy = registerItem("lesser_rune_of_accuracy", () -> new RuneAccuracy(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC)));
        lesserRuneDeep = registerItem("lesser_rune_of_deep", () -> new RuneDeep(new Item.Properties().stacksTo(1).durability(6).rarity(Rarity.EPIC)));
        rune = registerItem("rune", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)){
            @Override
            public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags){
                super.appendHoverText(stack, world, tooltip, flags);
                tooltip.add(Component.translatable("tooltip.valoria.rune").withStyle(ChatFormatting.GRAY));
            }
        });

        runeVision = registerItem("rune_of_vision", () -> new CurioVision(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC), 3600));
        runeWealth = registerItem("rune_of_wealth", () -> new CurioWealth(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC)));
        runeCurses = registerItem("rune_of_curses", () -> new CurioCurses(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        runeStrength = registerItem("rune_of_strength", () -> new CurioStrength(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC)));
        runeAccuracy = registerItem("rune_of_accuracy", () -> new RuneAccuracy(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC)));
        runeDeep = registerItem("rune_of_deep", () -> new RuneDeep(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC)));
        runePyro = registerItem("rune_of_pyro", () -> new CurioPyro(new Item.Properties().stacksTo(1).durability(32).rarity(Rarity.EPIC)));
        runeCold = registerItem("rune_of_cold", () -> new RuneCold(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

        // medicine
        aloeBandage = registerItem("aloe_bandage", () -> new BandageItem(false, 1600, 0));
        aloeBandageUpgraded = registerItem("aloe_bandage_upgraded", () -> new BandageItem(true, 1450, 1));
        shadeBlossomBandage = registerItem("shade_blossom_bandage", () -> new BandageItem(true, 1750, 1)); //todo custom effect

        //food
        applePie = registerItem("apple_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1.4f).build())));
        eyeChunk = registerItem("eye_chunk", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.POISON, 100), 0.4f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300), 1f).nutrition(1).saturationMod(0.2f).fast().build())));
        taintedBerries = registerItem("tainted_berries", () -> new ItemNameBlockItem(BlockRegistry.taintedRoots.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).fast().build())));
        cookedGlowVioletSprout = registerItem("cooked_glow_violet_sprout", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1f).build())));
        cookedAbyssalGlowfern = registerItem("cooked_abyssal_glowfern", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1f).build())));
        goblinMeat = registerItem("goblin_meat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
        cookedGoblinMeat = registerItem("cooked_goblin_meat", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build())));
        cup = registerItem("cup", () -> new BlockItem(BlockRegistry.cup.get(), new Item.Properties().stacksTo(64)));
        cacaoCup = registerItem("cacao_cup", () -> new PlaceableDrinkItem(BlockRegistry.cacaoCup.get(), 0, 1, 64, ItemsRegistry.cup.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
        coffeeCup = registerItem("coffee_cup", () -> new PlaceableDrinkItem(BlockRegistry.coffeeCup.get(), 0, 1, 64, ItemsRegistry.cup.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
        teaCup = registerItem("tea_cup", () -> new PlaceableDrinkItem(BlockRegistry.teaCup.get(), 0, 1, 16, ItemsRegistry.cup.get(), new MobEffectInstance(MobEffects.DIG_SPEED, 100)));
        greenTeaCup = registerItem("green_tea_cup", () -> new PlaceableDrinkItem(BlockRegistry.greenTeaCup.get(), 0, 1, 64, ItemsRegistry.cup.get(), new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 1800)));
        woodenCup = registerItem("wooden_cup", () -> new BlockItem(BlockRegistry.woodenCup.get(), new Item.Properties()));
        beerCup = registerItem("beer_cup", () -> new PlaceableDrinkItem(BlockRegistry.beerCup.get(), 0, 1, 64, ItemsRegistry.woodenCup.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 400, 0)));
        rumCup = registerItem("rum_cup", () -> new PlaceableDrinkItem(BlockRegistry.rumCup.get(), 0, 1, 64, ItemsRegistry.woodenCup.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 400, 0), new MobEffectInstance(MobEffects.CONFUSION, 120, 0)));
        bottle = registerItem("bottle", () -> new BlockItem(BlockRegistry.glassBottle.get(), new Item.Properties().stacksTo(64)));
        kvassBottle = registerItem("kvass_bottle", () -> new PlaceableDrinkItem(BlockRegistry.kvassBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.ALOEREGEN.get(), 200)));
        wineBottle = registerItem("wine_bottle", () -> new PlaceableDrinkItem(BlockRegistry.wineBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 125)));
        akvavitBottle = registerItem("akvavit_bottle", () -> new PlaceableDrinkItem(BlockRegistry.akvavitBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 500, 1), new MobEffectInstance(MobEffects.CONFUSION, 125)));
        sakeBottle = registerItem("sake_bottle", () -> new PlaceableDrinkItem(BlockRegistry.sakeBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 700, 1), new MobEffectInstance(MobEffects.CONFUSION, 175)));
        liquorBottle = registerItem("liquor_bottle", () -> new PlaceableDrinkItem(BlockRegistry.liquorBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 350, 1), new MobEffectInstance(MobEffects.CONFUSION, 60)));
        rumBottle = registerItem("rum_bottle", () -> new PlaceableDrinkItem(BlockRegistry.rumBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 650, 1), new MobEffectInstance(MobEffects.CONFUSION, 100)));
        meadBottle = registerItem("mead_bottle", () -> new PlaceableDrinkItem(BlockRegistry.meadBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 700, 0), new MobEffectInstance(MobEffects.CONFUSION, 100)));
        cognacBottle = registerItem("cognac_bottle", () -> new PlaceableDrinkItem(BlockRegistry.cognacBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 800, 2), new MobEffectInstance(MobEffects.CONFUSION, 175)));
        whiskeyBottle = registerItem("whiskey_bottle", () -> new PlaceableDrinkItem(BlockRegistry.whiskeyBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(EffectsRegistry.TIPSY.get(), 450, 1), new MobEffectInstance(MobEffects.CONFUSION, 125)));
        cokeBottle = registerItem("coke_bottle", () -> new PlaceableDrinkItem(BlockRegistry.cokeBottle.get(), 0, 1, 64, ItemsRegistry.bottle.get(), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 250)));
        toxinsBottle = registerItem("toxins_bottle", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
        necromancerMusicDisc = registerItem("music_disc_necromancer", () -> new RecordItem(15, SoundsRegistry.MUSIC_NECROMANCER.get(), (new Properties()).stacksTo(1).rarity(Rarity.RARE), 97));

        wickedShield = registerItem("wicked_shield", () -> new ShieldItem((new Properties()).stacksTo(1).rarity(Rarity.EPIC)));

        // spawn eggs
        pumpkinContract = registerItem("pumpkin_contract", () -> new TexturedSpawnEggItem(EntityTypeRegistry.HAUNTED_MERCHANT, new Item.Properties()));
        mannequin = registerItem("mannequin_spawn_egg", () -> new MannequinSpawnItem(new Item.Properties()));
        dryador = registerItem("dryador_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.DRYADOR, Col.hexToDecimal("3f3226"), Col.colorToDecimal(Pal.nature.toJava()), new Item.Properties()));
        entMob = registerItem("ent_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.ENT, Col.hexToDecimal("52392e"), Col.colorToDecimal(Pal.nature.toJava()), new Item.Properties()));
        goblin = registerItem("goblin_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.GOBLIN, Col.hexToDecimal("185b36"), Col.hexToDecimal("6BB447"), new Item.Properties()));
        draugr = registerItem("draugr_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.DRAUGR, Col.hexToDecimal("61523f"), Col.hexToDecimal("beb4aa"), new Item.Properties()));
        swampWanderer = registerItem("swamp_wanderer_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.SWAMP_WANDERER, Col.hexToDecimal("4d5030"), Col.hexToDecimal("b8b377"), new Item.Properties()));
        scourge = registerItem("scourge_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.SCOURGE, Col.hexToDecimal("5D5F36"), Col.hexToDecimal("bdae86"), new Item.Properties()));
        maggot = registerItem("maggot_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.MAGGOT, Col.hexToDecimal("6F5B45"), Col.hexToDecimal("e3d0cc"), new Item.Properties()));
        wickedCrystal = registerItem("wicked_crystal_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.WICKED_CRYSTAL, Col.hexToDecimal("562a8a"), Col.hexToDecimal("ff62f8"), new Item.Properties()));
        crystal = registerItem("crystal_spawn_egg", () -> new TexturedSpawnEggItem(EntityTypeRegistry.CRYSTAL, new Item.Properties()));
        sorcerer = registerItem("sorcerer_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.SORCERER, Col.hexToDecimal("6e4e3f"), Col.hexToDecimal("e09f59"), new Item.Properties()));
        necromancer = registerItem("necromancer_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.NECROMANCER, Col.hexToDecimal("4b4857"), Col.hexToDecimal("958fb7"), new Item.Properties()));
        undead = registerItem("undead_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.UNDEAD, Col.hexToDecimal("7d7266"), Col.hexToDecimal("d6d0c9"), new Item.Properties()));
        shadewoodSpider = registerItem("shadewood_spider_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.SHADEWOOD_SPIDER, Col.hexToDecimal("373C53"), Col.hexToDecimal("6EABB7"), new Item.Properties()));
        devil = registerItem("devil_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.DEVIL, Col.hexToDecimal("b64841"), Col.hexToDecimal("3a3b62"), new Item.Properties()));
        troll = registerItem("troll_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.TROLL, Col.hexToDecimal("232b3a"), Col.hexToDecimal("43596a"), new Item.Properties()));
        corruptedTroll = registerItem("corrupted_troll_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.CORRUPTED_TROLL, Col.hexToDecimal("41273E"), Col.hexToDecimal("884f72"), new Item.Properties()));
        fleshSentinel = registerItem("flesh_sentinel_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeRegistry.FLESH_SENTINEL, Col.hexToDecimal("720706"), Col.hexToDecimal("ffc650"), new Item.Properties()));

        ITEMS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }

    private static RegistryObject<Item> registerEffectArmor(String name, ArmorItem.Type type, ArmorMaterial material, Item.Properties props){
        return ITEMS.register(name, () -> new EffectArmorItem(material, type, props));
    }

    private static RegistryObject<Item> registerItem(String name){
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> item){
        return ITEMS.register(name, item);
    }

    private static KatanaItem murasamaProps(){
        return new KatanaItem(ModItemTier.SAMURAI, ToolStats.katana.damage, ToolStats.katana.speed, new Item.Properties()){
            {
                builder.chargeTime = 20;
                builder.chargedSound = SoundsRegistry.RECHARGE.get();
            }

            private static void spawnParticles(@NotNull Player player, Vector3d pos, ServerLevel srv, double X, double Y, double Z, Color color){
                for(int count = 0; count < 1 + Mth.nextInt(RandomSource.create(), 0, 2); count += 1){
                    PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, (pos.x + X), (pos.y + Y), (pos.z + Z), color.getRed(), color.getGreen(), color.getBlue()));
                }
            }

            public void onUseTick(@NotNull Level worldIn, @NotNull LivingEntity livingEntityIn, @NotNull ItemStack stack, int count){
                Player player = (Player)livingEntityIn;
                if(worldIn instanceof ServerLevel srv){
                    ItemSkin skin = ItemSkin.itemSkin(stack);
                    Color color = skin != null ? skin.color.toJava() : new Color(235, 0, 25);
                    for(int ii = 0; ii < 1 + Mth.nextInt(RandomSource.create(), 0, 2); ii += 1){
                        PacketHandler.sendToTracking(srv, player.getOnPos(), new MurasamaParticlePacket(3F, player.getX(), (player.getY() + (player.getEyeHeight() / 2)), player.getZ(), color.getRed(), color.getGreen(), color.getBlue()));
                    }
                }

                if(player.getTicksUsingItem() == 20){
                    player.playNotifySound(SoundsRegistry.RECHARGE.get(), SoundSource.PLAYERS, 0.6f, 1);
                }
            }

            public void performDash(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player, Vector3d pos, RandomSource rand){
                double pitch = ((player.getRotationVector().x + 90) * Math.PI) / 180;
                double yaw = ((player.getRotationVector().y + 90) * Math.PI) / 180;
                double dashDistance = getDashDistance(player);
                Vec3 dir = (player.getViewVector(0.0f).scale(dashDistance));
                player.push(dir.x, dir.y * 0.25, dir.z);
                double ii = 1D;
                if(level instanceof ServerLevel srv){
                    for(int i = 0; i < 10; i += 1){
                        double locDistance = i * 0.5D;
                        double X = Math.sin(pitch) * Math.cos(yaw) * locDistance;
                        double Y = Math.cos(pitch) * locDistance;
                        double Z = Math.sin(pitch) * Math.sin(yaw) * locDistance;
                        level.addParticle(ParticleTypes.WAX_OFF, pos.x + X + (rand.nextDouble() - 0.5D), pos.y + Y, pos.z + Z + (rand.nextDouble() - 0.5D), 0d, 0.05d, 0d);
                        ItemSkin skin = ItemSkin.itemSkin(stack);
                        if(skin != null){
                            spawnParticles(player, pos, srv, X, Y, Z, skin.color().toJava());
                        }else{
                            spawnParticles(player, pos, srv, X, Y, Z, Color.RED);
                        }

                        List<LivingEntity> detectedEntities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.x + X - 0.5D, pos.y + Y - 0.5D, pos.z + Z - 0.5D, pos.x + X + 0.5D, pos.y + Y + 0.5D, pos.z + Z + 0.5D));
                        for(LivingEntity entity : detectedEntities){
                            if(!entity.equals(player)){
                                entity.hurt(level.damageSources().playerAttack(player), (float)((player.getAttributeValue(Attributes.ATTACK_DAMAGE) * ii) + EnchantmentHelper.getSweepingDamageRatio(player) + EnchantmentHelper.getDamageBonus(stack, entity.getMobType())) * 1.35f);
                                performEffects(entity, player);
                                Utils.Entities.applyWithChance(entity, builder.effects, builder.chance, arcRandom);
                                if(!player.isCreative()){
                                    stack.hurtAndBreak(getHurtAmount(detectedEntities), player, (plr) -> plr.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                                }
                            }

                            ii = ii - (1F / (detectedEntities.size() * 2));
                        }

                        if(locDistance >= distance(dashDistance, level, player)){
                            break;
                        }
                    }
                }
            }
        };
    }
}