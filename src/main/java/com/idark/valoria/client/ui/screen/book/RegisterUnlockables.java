package com.idark.valoria.client.ui.screen.book;

import com.idark.valoria.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.level.*;
import net.minecraft.world.item.*;

import static com.idark.valoria.Valoria.loc;

public class RegisterUnlockables{

    public static Unlockable
    aloe, pick, netherAlchemy, elementalAlchemy, valoriaAlchemy,
    crushables, lithicRunes, runes, voidSlateRunes,

    undead, kingCrab, harmonyEntities, devil, scavenger, fleshSentinel,
    necromancerGrimoire, necromancer, harmonyCrown, dryador, suspiciousGem, wickedCrystal,
    crypt, monstrosities, fortress, valoriaPortal, valoriaVisit, shadeBlossom,

    cobalt, blackGold, crimtane, remains, soulCollector,
    natureCore, aquariusCore, infernalCore, voidCore, // elemental

    rot, jade, pearlium, pyratite, ancientMetals, ethereal,
    goblin, troll, draugr, sorcerer, natureGolem, riverGolem, corruptedTroll, swampWanderer, scourge, corrupted, shadewoodSpider, wickedScorpion

    ;

    public static void init() {
        crypt = register(new DungeonTagUnlockable(Valoria.ID + ":crypt", Items.SKELETON_SKULL, TagsRegistry.CRYPTS));
        monstrosities = register(new DungeonTagUnlockable(Valoria.ID + ":monstrosities", ItemsRegistry.remains.get(), TagsRegistry.MONSTROSITIES));
        kingCrab = register(new EntityUnlockable(Valoria.ID + ":king_crab", ItemsRegistry.crabClaw.get(), EntityTypeRegistry.KING_CRAB.get()));
        devil = register(new EntityUnlockable(Valoria.ID + ":devil", ItemsRegistry.devilMeat.get(), EntityTypeRegistry.DEVIL.get()));
        harmonyEntities = register(new EntityTagUnlockable(Valoria.ID + ":harmony_entities", ItemsRegistry.harmonyHeart.get(), TagsRegistry.HARMONY_CREATURES));
        fortress = register(new DungeonUnlockable(Valoria.ID + ":fortress", ItemsRegistry.wickedAmethyst.get(), LevelGen.VALORIA_FORTRESS));
        aloe = register(new ItemUnlockable(Valoria.ID + ":aloe", ItemsRegistry.aloePiece.get()));
        rot = register(new ItemUnlockable(Valoria.ID + ":rot", ItemsRegistry.rot.get()));
        lithicRunes = register(new ItemUnlockable(Valoria.ID + ":lithic_runes", ItemsRegistry.lithicRune.get()));
        runes = register(new ItemUnlockable(Valoria.ID + ":runes", ItemsRegistry.rune.get()));
        voidSlateRunes = register(new ItemUnlockable(Valoria.ID + ":void_slate_runes", ItemsRegistry.voidSlateRune.get()));
        pick = register(new ItemUnlockable(Valoria.ID + ":pick", ItemsRegistry.pick.get()).addAward(loc("items/crusher")));
        shadeBlossom = register(new ItemUnlockable(Valoria.ID + ":shade_blossom", ItemsRegistry.shadeBlossomLeaf.get()));
        netherAlchemy = register(new ItemUnlockable(Valoria.ID + ":netherAlchemy", BlockRegistry.alchemyStationTier2.get().asItem()));
        elementalAlchemy = register(new ItemUnlockable(Valoria.ID + ":elementalAlchemy", BlockRegistry.alchemyStationTier3.get().asItem()));
        valoriaAlchemy = register(new ItemUnlockable(Valoria.ID + ":valoriaAlchemy", BlockRegistry.alchemyStationTier4.get().asItem()));
        crushables = register(new TagUnlockable(Valoria.ID + ":crushables", TagsRegistry.CRUSHABLE));
        undead = register(new EntityUnlockable(Valoria.ID + ":undead", Items.SKELETON_SKULL, EntityTypeRegistry.UNDEAD.get()));
        scavenger = register(new EntityUnlockable(Valoria.ID + ":scavenger", ItemsRegistry.scavenger.get(), EntityTypeRegistry.SCAVENGER.get()));
        fleshSentinel = register(new EntityUnlockable(Valoria.ID + ":flesh_sentinel", ItemsRegistry.fleshSentinel.get(), EntityTypeRegistry.FLESH_SENTINEL.get()));
        valoriaPortal = register(new ItemUnlockable(Valoria.ID + ":valoria_portal", false, ItemsRegistry.valoriaPortalFrameShard.get()));
        valoriaVisit = register(new DimensionUnlockable(Valoria.ID + ":valoria_visit", false, ItemsRegistry.nihilityShard.get(), LevelGen.VALORIA_KEY));
        necromancerGrimoire = register(new ItemUnlockable(Valoria.ID + ":necromancer_grimoire", false, ItemsRegistry.necromancerGrimoire.get()));
        necromancer = register(new EntityUnlockable(Valoria.ID + ":necromancer", ItemsRegistry.necromancerGrimoire.get(), EntityTypeRegistry.NECROMANCER.get()));
        harmonyCrown = register(new ItemUnlockable(Valoria.ID + ":harmony_crown", false, ItemsRegistry.harmonyCrown.get()));
        dryador = register(new EntityUnlockable(Valoria.ID + ":dryador", ItemsRegistry.harmonyCrown.get(), EntityTypeRegistry.DRYADOR.get()));
        suspiciousGem = register(new ItemUnlockable(Valoria.ID + ":suspicious_gem", false, ItemsRegistry.suspiciousGem.get()));
        wickedCrystal = register(new EntityUnlockable(Valoria.ID + ":wicked_crystal", ItemsRegistry.suspiciousGem.get(), EntityTypeRegistry.WICKED_CRYSTAL.get()));
        cobalt = register(new ItemUnlockable(Valoria.ID + ":cobalt", Items.DIAMOND_PICKAXE));
        blackGold = register(new ItemUnlockable(Valoria.ID + ":black_gold", ItemsRegistry.blackGold.get()));
        crimtane = register(new ItemUnlockable(Valoria.ID + ":crimtane", ItemsRegistry.crimtaneIngot.get()));
        remains = register(new ItemUnlockable(Valoria.ID + ":remains", ItemsRegistry.remains.get()));
        soulCollector = register(new ItemUnlockable(Valoria.ID + ":soul_collector", ItemsRegistry.soulCollector.get()));
        natureCore = register(new ItemUnlockable(Valoria.ID + ":nature_core", false, ItemsRegistry.natureCore.get()));
        aquariusCore = register(new ItemUnlockable(Valoria.ID + ":aquarius_core", false, ItemsRegistry.aquariusCore.get()));
        infernalCore = register(new ItemUnlockable(Valoria.ID + ":infernal_core", false, ItemsRegistry.infernalCore.get()));
        voidCore = register(new ItemUnlockable(Valoria.ID + ":void_core", false, ItemsRegistry.voidCore.get()));

        jade = register(new ItemUnlockable(Valoria.ID + ":jade", false, ItemsRegistry.jade.get()));
        pearlium = register(new ItemUnlockable(Valoria.ID + ":pearlium", false, ItemsRegistry.pearliumIngot.get()));
        pyratite = register(new ItemUnlockable(Valoria.ID + ":pyratite", false, ItemsRegistry.pyratite.get()));
        ancientMetals = register(new ItemUnlockable(Valoria.ID + ":ancient_metals", false, ItemsRegistry.ancientIngot.get()));
        ethereal = register(new ItemUnlockable(Valoria.ID + ":ethereal", false, ItemsRegistry.etherealShard.get()));

        goblin = register(new EntityUnlockable(Valoria.ID + ":goblin", ItemsRegistry.goblinMeat.get(), EntityTypeRegistry.GOBLIN.get()));
        troll = register(new EntityUnlockable(Valoria.ID + ":troll", ItemsRegistry.troll.get(), EntityTypeRegistry.TROLL.get()));
        draugr = register(new EntityUnlockable(Valoria.ID + ":draugr", ItemsRegistry.draugr.get(), EntityTypeRegistry.DRAUGR.get()));
        sorcerer = register(new EntityUnlockable(Valoria.ID + ":sorcerer", ItemsRegistry.sorcerer.get(), EntityTypeRegistry.SORCERER.get()));
        natureGolem = register(new EntityUnlockable(Valoria.ID + ":nature_golem", ItemsRegistry.natureGolem.get(), EntityTypeRegistry.NATURE_GOLEM.get()));
        riverGolem = register(new EntityUnlockable(Valoria.ID + ":river_golem", ItemsRegistry.riverGolem.get(), EntityTypeRegistry.RIVER_GOLEM.get()));
        corruptedTroll = register(new EntityUnlockable(Valoria.ID + ":corrupted_troll", ItemsRegistry.corruptedTroll.get(), EntityTypeRegistry.CORRUPTED_TROLL.get()));
        swampWanderer = register(new EntityUnlockable(Valoria.ID + ":swamp_wanderer", ItemsRegistry.swampWanderer.get(), EntityTypeRegistry.SWAMP_WANDERER.get()));
        scourge = register(new EntityUnlockable(Valoria.ID + ":scourge", ItemsRegistry.scourge.get(), EntityTypeRegistry.SCOURGE.get()));
        corrupted = register(new EntityUnlockable(Valoria.ID + ":corrupted", ItemsRegistry.corrupted.get(), EntityTypeRegistry.CORRUPTED.get()));
        shadewoodSpider = register(new EntityUnlockable(Valoria.ID + ":shadewood_spider", ItemsRegistry.shadeSpider.get(), EntityTypeRegistry.SHADEWOOD_SPIDER.get()));
        wickedScorpion = register(new EntityUnlockable(Valoria.ID + ":wicked_scorpion", ItemsRegistry.scorpion.get(), EntityTypeRegistry.WICKED_SCORPION.get()));
    }

    public static Unlockable register(Unlockable unlockable){
        Unlockables.register(unlockable);
        return unlockable;
    }
}