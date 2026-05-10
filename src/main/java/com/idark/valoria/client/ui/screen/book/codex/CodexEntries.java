package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.npc.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import pro.komaru.tridot.api.render.text.DotStyleEffects.*;
import pro.komaru.tridot.client.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.struct.data.*;

import javax.annotation.*;
import java.util.*;

// todo data driven chapters and pages?
public class CodexEntries{
    public static Seq<CodexEntry> entries = new Seq<>();
    public static Seq<CodexEntry> openedEntries = new Seq<>();
    public static Seq<SidebarEntry> sidebarEntries = new Seq<>();

    private static ChapterNode rootNode;

    public static Chapter MAIN_CHAPTER, PAGES_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, SURVIVAL, COMBAT, CRAFTING,

    PICK, HEAVY_WORKBENCH, KEG, KILN, JEWELER_TABLE, ALCHEMY_STATION, ELEMENTAL_MANIPULATOR, NETHER_ALCHEMY, ELEMENTAL_ALCHEMY, VALORIA_ALCHEMY, STONE_CRUSHER,
    NIHILITY_MONITOR, VALORIA_PORTAL, SHADE_BLOSSOM,

    LITHIC_RUNES, RUNES, VOID_SLATE_RUNES,
    WARRIOR_ARSENAL, KATANAS, SCYTHES, SPEARS,
    SOUL_ARTS, SOUL_ESSENCE, SOUL_INFUSER,
    BLOODBORNE_PATH, COLOSSI_REMAINS, CRIMTANE_FORGING,
    BESTIARY, SCAVENGERS, FLESH_SENTINELS,
    CHRONICLES, ELEMENTAL_COLLAPSE, RED_GAZE,

    BOSSES,
    UNDEAD,
    ELEMENTALS, ENT, KING_CRAB, DEVIL,
    NECROMANCER_GRIMOIRE, NECROMANCER,
    SUSPICIOUS_GEM, WICKED_CRYSTAL,

    CRYPT, FORTRESS,

    ELEMENTAL_EMPERORS,
    HARMONY_CROWN, DRYADOR, OBSIDIAN_HEART, FIRRON,

    CRUSHABLES,
    MATERIALS, COBALT,
    BLACK_GOLD,
    NATURE_CORE, AQUARIUS_CORE, INFERNAL_CORE, VOID_CORE,

    ROT, JADE, PEARLIUM, PYRATITE, ANCIENT_METALS, ETHEREAL,
    NATURE_GOLEM, RIVER_GOLEM, DRAUGR, SORCERER, GOBLIN, TROLL, CORRUPTED_TROLL, SWAMP_WANDERER, SCOURGE, CORRUPTED, SHADEWOOD_SPIDER, WICKED_SCORPION, THE_END

    ;

    public static void initChapters(){
        ROT = new Chapter(
        "codex.valoria.rot.name",
        new GeneralPage("codex.valoria.rot"),
        new GeneralPage().addItem(ItemsRegistry.rot.get().getDefaultInstance(), false, 10, 10, 100)
        );

        JADE = new Chapter(
        "codex.valoria.jade.name",
        new GeneralPage("codex.valoria.jade"),
        new GeneralPage().addRecipe(ItemsRegistry.jadeSword.get().getDefaultInstance()).addSpace(12).addRecipe(ItemsRegistry.jadePickaxe.get().getDefaultInstance())
        );

        PEARLIUM = new Chapter(
        "codex.valoria.pearlium.name",
        new GeneralPage("codex.valoria.pearlium"),
        new GeneralPage().addRecipe(ItemsRegistry.pearliumSword.get().getDefaultInstance()).addSpace(12).addRecipe(ItemsRegistry.pearliumPickaxe.get().getDefaultInstance())
        );

        PYRATITE = new Chapter(
        "codex.valoria.pyratite.name",
        new GeneralPage("codex.valoria.pyratite"),
        new GeneralPage().addRecipe(ItemsRegistry.pyratiteSpear.get().getDefaultInstance()).addSpace(12).addRecipe(ItemsRegistry.pyratiteCharge.get().getDefaultInstance())
        );

        ANCIENT_METALS = new Chapter(
        "codex.valoria.ancient_metals.name",
        new GeneralPage("codex.valoria.ancient_metals"),
        new GeneralPage().addRecipe(ItemsRegistry.ancientIngot.get().getDefaultInstance()).addSpace(12).addRecipe(ItemsRegistry.relicGold.get().getDefaultInstance())
        );

        ETHEREAL = new Chapter(
        "codex.valoria.ethereal.name",
        new GeneralPage("codex.valoria.ethereal"),
        new GeneralPage().addItem(ItemsRegistry.etherealShard.get().getDefaultInstance(), false, 10, 10, 100).addText("codex.valoria.ethereal_shard")
        );

        MAIN_CHAPTER = new Chapter(
        "codex.valoria.main.name",
        new GeneralPage("codex.valoria.main"),
        new GeneralPage("codex.valoria.main.continuation").hideTitle());

        PAGES_CHAPTER = new Chapter(
        "codex.valoria.pages.name",
        new GeneralPage("codex.valoria.pages"));

        TREASURES_CHAPTER = new Chapter(
        "codex.valoria.jewelry",
        new GeneralPage("codex.valoria.treasures"),
        new GeneralPage("codex.valoria.treasure.gems"));

        CRAFTING = new Chapter(
        "codex.valoria.crafting.name",
        new GeneralPage("codex.valoria.crafting"));

        MEDICINE_CHAPTER = new Chapter(
        "codex.valoria.medicine.name",
        new GeneralPage("codex.valoria.medicine"),
        new GeneralPage().addRecipe(ItemsRegistry.aloeBandage.get().getDefaultInstance()).addSpace(12).addRecipe(ItemsRegistry.aloeBandageUpgraded.get().getDefaultInstance())
        );

        NIHILITY_MONITOR = new Chapter(
        "codex.valoria.nihility_monitor.name",
        new GeneralPage().addTitle("codex.valoria.nihility_monitor.name").addText("codex.valoria.nihility_monitor"),
        new GeneralPage().addRecipe(ItemsRegistry.nihilityMonitor.get().getDefaultInstance()));

        VALORIA_PORTAL = new Chapter(
        "codex.valoria.valoria_portal.name",
        new GeneralPage("codex.valoria.valoria_portal"),
        new GeneralPage().addImage(new ResourceLocation(Valoria.ID, "textures/gui/book/valoria_portal.png"), 124, 124));

        PICK = new Chapter(
        "codex.valoria.pick.name",
        new GeneralPage("codex.valoria.pick"));

        ELEMENTAL_MANIPULATOR = new Chapter(
        "codex.valoria.elemental_manipulator.name",
        new GeneralPage("codex.valoria.elemental_manipulator").addRecipe(BlockRegistry.elementalManipulator.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.elementalManipulator.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        STONE_CRUSHER = new Chapter(
        "codex.valoria.stone_crusher.name",
        new GeneralPage("codex.valoria.stone_crusher").addRecipe(BlockRegistry.stoneCrusher.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.stoneCrusher.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        HEAVY_WORKBENCH = new Chapter(
        "codex.valoria.heavy_workbench.name",
        new GeneralPage("codex.valoria.heavy_workbench").addRecipe(BlockRegistry.heavyWorkbench.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.heavyWorkbench.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        KEG = new Chapter(
        "codex.valoria.keg.name",
        new GeneralPage("codex.valoria.keg").addRecipe(BlockRegistry.keg.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.keg.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        KILN = new Chapter(
        "codex.valoria.kiln.name",
        new GeneralPage("codex.valoria.kiln").addRecipe(BlockRegistry.kiln.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.kiln.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        LITHIC_RUNES = new Chapter(
        "codex.valoria.lithic_runes.name",
        new GeneralPage("codex.valoria.lithic_runes"),
        new GeneralPage().addItem(ItemsRegistry.lithicRune.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        RUNES = new Chapter(
        "codex.valoria.runes.name",
        new GeneralPage("codex.valoria.runes"),
        new GeneralPage().addItem(ItemsRegistry.rune.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        VOID_SLATE_RUNES = new Chapter(
        "codex.valoria.void_slate_runes.name",
        new GeneralPage("codex.valoria.void_slate_runes"),
        new GeneralPage().addItem(ItemsRegistry.voidSlateRune.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        JEWELER_TABLE = new Chapter(
        "codex.valoria.jeweler_table.name",
        new GeneralPage("codex.valoria.jeweler_table").addRecipe(BlockRegistry.jewelerTable.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.jewelerTable.get().asItem().getDefaultInstance(), false, 10, 10, 100),
        new GeneralPage("codex.valoria.jeweler_table.villager").hideTitle(),
        new GeneralPage().addSpace(5).addEntity(EntityType.VILLAGER, 45, false, villager -> {
            int cycle = (ClientTick.ticksInGame / 40) % 8;
            int levelCycle = (ClientTick.ticksInGame / 80) % 6;
            VillagerType type;
            switch (cycle) {
                case 1: {
                    type = VillagerType.PLAINS;
                    break;
                }
                case 2: {
                    type = VillagerType.SNOW;
                    break;
                }
                case 3: {
                    type = VillagerType.JUNGLE;
                    break;
                }
                case 4: {
                    type = VillagerType.TAIGA;
                    break;
                }
                case 5: {
                    type = VillagerType.DESERT;
                    break;
                }
                case 6: {
                    type = VillagerType.SAVANNA;
                    break;
                }
                case 7: {
                    type = VillagerType.SWAMP;
                    break;
                }

                default: type = VillagerType.PLAINS;
            }

            villager.setVillagerData(new VillagerData(type, VillagerProfessionRegistry.JEWELER.get(), levelCycle));
        }));

        SHADE_BLOSSOM = new Chapter(
        "codex.valoria.shade_blossom.name",
        new GeneralPage("codex.valoria.shade_blossom"),
        new GeneralPage().addItem(BlockRegistry.shadeBlossom.get().asItem().getDefaultInstance(), false, 10, 10, 100),
        new GeneralPage().addItem(ItemsRegistry.shadeBlossomSeeds.get().asItem().getDefaultInstance(), false, 10, 0, 50).addText("codex.valoria.shade_blossom_seeds"),
        new GeneralPage().addItem(ItemsRegistry.shadeBlossomLeaf.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        ALCHEMY_STATION = new Chapter(
        "codex.valoria.alchemy_station.name",
        new GeneralPage("codex.valoria.alchemy_station").addRecipe(BlockRegistry.alchemyStationTier1.get().asItem().getDefaultInstance()),
        new GeneralPage().addItem(BlockRegistry.alchemyStationTier1.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        NETHER_ALCHEMY = new Chapter(
        "codex.valoria.nether_alchemy.name",
        new GeneralPage("codex.valoria.alchemy_station"));

        ELEMENTAL_ALCHEMY = new Chapter(
        "codex.valoria.elemental_alchemy.name",
        new GeneralPage("codex.valoria.elemental_alchemy"));

        VALORIA_ALCHEMY = new Chapter(
        "codex.valoria.valoria_alchemy.name",
        new GeneralPage("codex.valoria.valoria_alchemy"));

        KING_CRAB = new Chapter(
        "codex.valoria.king_crab.name",
        new GeneralPage("codex.valoria.king_crab"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.KING_CRAB.get(), 32, false));

        ENT = new Chapter(
        "codex.valoria.ent.name",
        new GeneralPage("codex.valoria.ent"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.ENT.get(), 42, false));

        NATURE_GOLEM = new Chapter(
        "codex.valoria.nature_golem.name",
        new GeneralPage("codex.valoria.nature_golem"),
        new GeneralPage().addSpace(45).addEntity(EntityTypeRegistry.NATURE_GOLEM.get(), 32, false));

        RIVER_GOLEM = new Chapter(
        "codex.valoria.river_golem.name",
        new GeneralPage("codex.valoria.river_golem"),
        new GeneralPage().addSpace(45).addEntity(EntityTypeRegistry.RIVER_GOLEM.get(), 32, false));

        DEVIL = new Chapter(
        "codex.valoria.devil.name",
        new GeneralPage("codex.valoria.devil"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.DEVIL.get(), 42, false));

        DRAUGR = new Chapter(
        "codex.valoria.draugr.name",
        new GeneralPage("codex.valoria.draugr"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.DRAUGR.get(), 42, false));

        SORCERER = new Chapter(
        "codex.valoria.sorcerer.name",
        new GeneralPage("codex.valoria.sorcerer"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.SORCERER.get(), 42, false));

        GOBLIN = new Chapter(
        "codex.valoria.goblin.name",
        new GeneralPage("codex.valoria.goblin"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.GOBLIN.get(), 42, false));

        TROLL = new Chapter(
        "codex.valoria.troll.name",
        new GeneralPage("codex.valoria.troll"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.TROLL.get(), 42, false));

        CORRUPTED_TROLL = new Chapter(
        "codex.valoria.corrupted_troll.name",
        new GeneralPage("codex.valoria.corrupted_troll"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.CORRUPTED_TROLL.get(), 42, false));

        SWAMP_WANDERER = new Chapter(
        "codex.valoria.swamp_wanderer.name",
        new GeneralPage("codex.valoria.swamp_wanderer"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.SWAMP_WANDERER.get(), 42, false));

        SCOURGE = new Chapter(
        "codex.valoria.scourge.name",
        new GeneralPage("codex.valoria.scourge"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.SCOURGE.get(), 42, false));

        CORRUPTED = new Chapter(
        "codex.valoria.corrupted.name",
        new GeneralPage("codex.valoria.corrupted"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.CORRUPTED.get(), 42, false));

        SHADEWOOD_SPIDER = new Chapter(
        "codex.valoria.shadewood_spider.name",
        new GeneralPage("codex.valoria.shadewood_spider"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.SHADEWOOD_SPIDER.get(), 42, false));

        WICKED_SCORPION = new Chapter(
        "codex.valoria.wicked_scorpion.name",
        new GeneralPage("codex.valoria.wicked_scorpion"),
        new GeneralPage().addSpace(15).addEntity(EntityTypeRegistry.WICKED_SCORPION.get(), 42, false));

        BOSSES = new Chapter(
        "codex.valoria.bosses.name",
        new GeneralPage("codex.valoria.bosses"));

        NECROMANCER_GRIMOIRE = new Chapter(
        "codex.valoria.necromancer_grimoire.name",
        new GeneralPage("codex.valoria.necromancer_grimoire"),
        new GeneralPage("codex.valoria.necromancer_grimoire_continuation").hideTitle());

        NECROMANCER = new Chapter(
        "codex.valoria.necromancer.name",
        new GeneralPage("codex.valoria.necromancer"),
        new GeneralPage("codex.valoria.necromancer_continuation").hideTitle().addEntity(EntityTypeRegistry.NECROMANCER.get(), 32, false));

        HARMONY_CROWN = new Chapter(
        "codex.valoria.harmony_crown.name",
        new GeneralPage("codex.valoria.harmony_crown"),
        new GeneralPage("codex.valoria.harmony_crown.continuation").hideTitle());

        DRYADOR = new Chapter(
        "codex.valoria.dryador.name",
        new GeneralPage("codex.valoria.dryador").hideTitle(),
        new GeneralPage().addSpace(60).addEntity(EntityTypeRegistry.DRYADOR.get(), 26, false));

        OBSIDIAN_HEART = new Chapter(
        "codex.valoria.obsidian_heart.name",
        new GeneralPage("codex.valoria.obsidian_heart"),
        new GeneralPage("codex.valoria.obsidian_heart.continuation").hideTitle());

        FIRRON = new Chapter(
        "codex.valoria.firron.name",
        new GeneralPage("codex.valoria.firron").hideTitle(),
        new GeneralPage().addSpace(60).addEntity(EntityTypeRegistry.FIRRON.get(), 26, false));

        SUSPICIOUS_GEM = new Chapter(
        "codex.valoria.suspicious_gem.name",
        new GeneralPage("codex.valoria.suspicious_gem"),
        new GeneralPage("codex.valoria.suspicious_gem_continuation").hideTitle());

        WICKED_CRYSTAL = new Chapter(
        "codex.valoria.wicked_crystal.name",
        new GeneralPage("codex.valoria.wicked_crystal"),
        new GeneralPage().addSpace(60).addEntity(EntityTypeRegistry.WICKED_CRYSTAL.get(), 26, false),
        new GeneralPage("codex.valoria.wicked_crystal_continuation").hideTitle());

        THE_END = new Chapter(
        "codex.valoria.the_end.name",
        new GeneralPage("codex.valoria.the_end"),
        new GeneralPage().addSpace(15).addImage(Valoria.loc("textures/gui/developers.png"), 128, 78),
        new GeneralPage("codex.valoria.the_end.history"));

        UNDEAD = new Chapter(
        "codex.valoria.undead.name",
        new GeneralPage("codex.valoria.undead"),
        new GeneralPage().addSpace(25).addEntity(EntityTypeRegistry.UNDEAD.get(), 32, false));

        ELEMENTALS = new Chapter(
        "codex.valoria.elementals.name",
        new GeneralPage("codex.valoria.elementals"),
        new GeneralPage("codex.valoria.elementals.continuation").hideTitle());

        ELEMENTAL_EMPERORS = new Chapter(
        "codex.valoria.elemental_emperors.name",
        new GeneralPage("codex.valoria.elemental_emperors").hideTitle(),
        new GeneralPage("codex.valoria.elemental_emperors.continuation").hideTitle());

        CRUSHABLES = new Chapter(
        "codex.valoria.crushables.name",
        new GeneralPage("codex.valoria.crushables"));

        MATERIALS = new Chapter(
        "codex.valoria.materials.name",
        new GeneralPage("codex.valoria.materials"));

        COBALT = new Chapter(
        "codex.valoria.cobalt.name",
        new GeneralPage("codex.valoria.cobalt").addItem(ItemsRegistry.cobaltIngot.get().getDefaultInstance(), false, 40, 0, 32),
        new GeneralPage("codex.valoria.cobalt_continuation").hideTitle());

        BLACK_GOLD = new Chapter(
        "codex.valoria.black_gold.name",
        new GeneralPage("codex.valoria.black_gold"),
        new GeneralPage().addItem(ItemsRegistry.blackGold.get().getDefaultInstance(), false, 10, 10, 100));

        NATURE_CORE = new Chapter(
        "codex.valoria.nature_core.name",
        new GeneralPage("codex.valoria.nature_core"),
        new GeneralPage().addItem(ItemsRegistry.natureCore.get().getDefaultInstance(), false, 10, 10, 100));

        AQUARIUS_CORE = new Chapter(
        "codex.valoria.aquarius_core.name",
        new GeneralPage("codex.valoria.aquarius_core"),
        new GeneralPage().addItem(ItemsRegistry.aquariusCore.get().getDefaultInstance(), false, 10, 10, 100));

        INFERNAL_CORE = new Chapter(
        "codex.valoria.infernal_core.name",
        new GeneralPage("codex.valoria.infernal_core"),
        new GeneralPage().addItem(ItemsRegistry.infernalCore.get().getDefaultInstance(), false, 10, 10, 100));

        VOID_CORE = new Chapter(
        "codex.valoria.void_core.name",
        new GeneralPage("codex.valoria.void_core"),
        new GeneralPage().addItem(ItemsRegistry.voidCore.get().getDefaultInstance(), false, 10, 10, 100));

        CRYPT = new Chapter(
        "codex.valoria.crypt.name",
        new GeneralPage("codex.valoria.crypt"),
        new GeneralPage("codex.valoria.crypt_continuation").hideTitle().addImage(new ResourceLocation(Valoria.ID, "textures/gui/book/crypt.png"), -5, -25, 128, 128));

        FORTRESS = new Chapter(
        "codex.valoria.fortress.name",
        new GeneralPage("codex.valoria.fortress"),
        new GeneralPage("codex.valoria.fortress_continuation").hideTitle());

        SURVIVAL = new Chapter(
        "codex.valoria.survival.name",
        new GeneralPage("codex.valoria.survival"),
        new GeneralPage("codex.valoria.nihility"),
        new GeneralPage("codex.valoria.nihility_continuation").addItem(ItemsRegistry.gasMask.get().getDefaultInstance(), false, 40, 0, 32).hideTitle(),
        new GeneralPage("codex.valoria.rotting"));

        COMBAT = new Chapter(
        "codex.valoria.combat.name",
        new GeneralPage("codex.valoria.combat"),
        new GeneralPage("codex.valoria.attributes"),
        new GeneralPage("codex.valoria.elemental_combat"),
        new GeneralPage("codex.valoria.elemental_combat_continuation").hideTitle());

        WARRIOR_ARSENAL = new Chapter(
        "codex.valoria.warrior_arsenal.name",
        new GeneralPage("codex.valoria.warrior_arsenal"));

        KATANAS = new Chapter(
        "codex.valoria.katanas.name",
        new GeneralPage("codex.valoria.katanas"));

        SCYTHES = new Chapter(
        "codex.valoria.scythes.name",
        new GeneralPage("codex.valoria.scythes"));

        SPEARS = new Chapter(
        "codex.valoria.spears.name",
        new GeneralPage("codex.valoria.spears"));

        SOUL_ARTS = new Chapter(
        "codex.valoria.soul_arts.name",
        new GeneralPage("codex.valoria.soul_arts"));

        SOUL_ESSENCE = new Chapter(
        "codex.valoria.soul_essence.name",
        new GeneralPage("codex.valoria.soul_essence"),
        new GeneralPage().addItem(ItemsRegistry.soulCollector.get().getDefaultInstance(), false, 10, 10, 100));

        SOUL_INFUSER = new Chapter(
        "codex.valoria.soul_infuser.name",
        new GeneralPage("codex.valoria.soul_infuser"),
        new GeneralPage().addSpace(35).addRecipe(BlockRegistry.soulInfuser.get().asItem().getDefaultInstance()));

        BLOODBORNE_PATH = new Chapter(
        "codex.valoria.bloodborne_path.name",
        new GeneralPage("codex.valoria.bloodborne_path"));

        COLOSSI_REMAINS = new Chapter(
        "codex.valoria.colossi_remains.name",
        new GeneralPage("codex.valoria.colossi_remains"));

        CRIMTANE_FORGING = new Chapter(
        "codex.valoria.crimtane_forging.name",
        new GeneralPage("codex.valoria.crimtane_forging"),
        new GeneralPage().addSpace(35).addRecipe(ItemsRegistry.crimtaneIngot.get().getDefaultInstance()));

        BESTIARY = new Chapter(
        "codex.valoria.bestiary.name",
        new GeneralPage("codex.valoria.bestiary"));

        SCAVENGERS = new Chapter(
        "codex.valoria.scavengers.name",
        new GeneralPage("codex.valoria.scavengers"),
        new GeneralPage().addSpace(25).addEntity(EntityTypeRegistry.SCAVENGER.get(), 32, false));

        FLESH_SENTINELS = new Chapter(
        "codex.valoria.flesh_sentinels.name",
        new GeneralPage("codex.valoria.flesh_sentinels"),
        new GeneralPage().addEntity(EntityTypeRegistry.FLESH_SENTINEL.get(), 32, false));

        CHRONICLES = new Chapter(
        "codex.valoria.chronicles.name",
        new GeneralPage("codex.valoria.chronicles"));

        ELEMENTAL_COLLAPSE = new Chapter(
        "codex.valoria.elemental_collapse.name",
        new GeneralPage("codex.valoria.elemental_collapse"));

        RED_GAZE = new Chapter(
        "codex.valoria.red_gaze.name",
        new GeneralPage("codex.valoria.red_gaze"));
    }

    @Nullable
    public static ChapterNode getNode(Unlockable unlockable) {
        init();
        for(CodexEntry entry : entries) {
            if(entry.node.unlockable == unlockable) {
                return entry.node;
            }
        }

        return null;
    }

    public static void init(){
        CodexEntries.entries.clear();
        CodexEntries.openedEntries.clear();

        rootNode = new ChapterNode(PAGES_CHAPTER, ItemsRegistry.page.get(), Style.GOLD)
        .addChild(new ChapterNode(MAIN_CHAPTER, ItemsRegistry.codex.get(), Style.GOLD)
            .addChild(new ChapterNode(COMBAT, ItemsRegistry.etherealSword.get(), Style.IRON)
                .addChild(new ChapterNode(WARRIOR_ARSENAL, ItemsRegistry.silkenBlade.get(), Style.IRON)
                    .addChild(new ChapterNode(KATANAS, ItemsRegistry.ironKatana.get()))
                    .addChild(new ChapterNode(SCYTHES, ItemsRegistry.ironScythe.get()))
                    .addChild(new ChapterNode(SPEARS, ItemsRegistry.ironSpear.get()))
                )
                .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get(), RegisterUnlockables.aloe)
                    .addHintsDescription(Component.translatable("codex.valoria.medicine.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
            )


            .addChild(new ChapterNode(BESTIARY, Items.KNOWLEDGE_BOOK, Style.IRON)
                .addChild(new ChapterNode(GOBLIN, ItemsRegistry.goblinMeat.get(), RegisterUnlockables.goblin)
                    .addHintsDescription(Component.translatable("codex.valoria.goblin.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(TROLL, ItemsRegistry.troll.get(), RegisterUnlockables.troll)
                        .addHintsDescription(Component.translatable("codex.valoria.troll.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(CORRUPTED_TROLL, ItemsRegistry.corruptedTroll.get(), RegisterUnlockables.corruptedTroll)
                            .addHintsDescription(Component.translatable("codex.valoria.corrupted_troll.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        )
                    )
                )
                .addChild(new ChapterNode(ELEMENTALS, ItemsRegistry.elementalCrystal.get(), Style.GOLD)
                    .addChild(new ChapterNode(ENT, ItemsRegistry.harmonyHeart.get(), RegisterUnlockables.harmonyEntities)
                        .addHintsDescription(Component.translatable("codex.valoria.ent.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(NATURE_GOLEM, ItemsRegistry.natureGolem.get(), RegisterUnlockables.natureGolem)
                            .addHintsDescription(Component.translatable("codex.valoria.nature_golem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))))
                    .addChild(new ChapterNode(KING_CRAB, ItemsRegistry.crabClaw.get(), RegisterUnlockables.kingCrab)
                            .addHintsDescription(Component.translatable("codex.valoria.king_crab.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(RIVER_GOLEM, ItemsRegistry.riverGolem.get(), RegisterUnlockables.riverGolem)
                                .addHintsDescription(Component.translatable("codex.valoria.river_golem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                            .addChild(new ChapterNode(SWAMP_WANDERER, ItemsRegistry.swampWanderer.get(), RegisterUnlockables.swampWanderer)
                                .addHintsDescription(Component.translatable("codex.valoria.swamp_wanderer.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                .addChild(new ChapterNode(SCOURGE, ItemsRegistry.scourge.get(), RegisterUnlockables.scourge)
                                    .addHintsDescription(Component.translatable("codex.valoria.scourge.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                )
                            )
                    )
                    .addChild(new ChapterNode(DEVIL, ItemsRegistry.devilHeart.get(), RegisterUnlockables.devil)
                        .addHintsDescription(Component.translatable("codex.valoria.devil.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )
                .addChild(new ChapterNode(SCAVENGERS, ItemsRegistry.scavenger.get(), RegisterUnlockables.scavenger)
                    .addHintsDescription(Component.translatable("codex.valoria.scavenger.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                )
                .addChild(new ChapterNode(FLESH_SENTINELS, ItemsRegistry.fleshSentinel.get(), RegisterUnlockables.fleshSentinel)
                    .addHintsDescription(Component.translatable("codex.valoria.flesh_sentinel.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(CORRUPTED, ItemsRegistry.corrupted.get(), RegisterUnlockables.corrupted)
                        .addHintsDescription(Component.translatable("codex.valoria.corrupted.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )
                .addChild(new ChapterNode(SHADEWOOD_SPIDER, ItemsRegistry.shadeSpider.get(), RegisterUnlockables.shadewoodSpider)
                    .addHintsDescription(Component.translatable("codex.valoria.shadewood_spider.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(WICKED_SCORPION, ItemsRegistry.scorpion.get(), RegisterUnlockables.wickedScorpion)
                        .addHintsDescription(Component.translatable("codex.valoria.wicked_scorpion.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    )
                )
                .addChild(new ChapterNode(BOSSES, Items.SKELETON_SKULL, Style.CRYPT)
                    .addChild(new ChapterNode(CRYPT, ItemsRegistry.page.get(), Style.CRYPT, RegisterUnlockables.crypt)
                        .addHintsDescription(Component.translatable("codex.valoria.crypt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(NECROMANCER_GRIMOIRE, ItemsRegistry.necromancerGrimoire.get(), Style.IRON, RegisterUnlockables.necromancerGrimoire)
                            .addHintsDescription(Component.translatable("codex.valoria.necromancer_grimoire.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(NECROMANCER, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.necromancer)
                                .addChild(new ChapterNode(UNDEAD, Items.BONE, Style.STANDARD, RegisterUnlockables.undead))
                                .addChild(new ChapterNode(DRAUGR, ItemsRegistry.draugr.get(), RegisterUnlockables.draugr)
                                .addHintsDescription(Component.translatable("codex.valoria.draugr.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                                .addChild(new ChapterNode(SORCERER, ItemsRegistry.sorcerer.get(), RegisterUnlockables.sorcerer)
                                .addHintsDescription(Component.translatable("codex.valoria.sorcerer.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                            )
                        )
                    )

                    .addChild(new ChapterNode(ELEMENTAL_EMPERORS, ItemsRegistry.harmonyCrown.get(), Style.GOLD)
                        .addChild(new ChapterNode(HARMONY_CROWN, ItemsRegistry.harmonyCrown.get(), Style.IRON, RegisterUnlockables.harmonyCrown)
                            .addHintsDescription(Component.translatable("codex.valoria.harmony_crown.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(DRYADOR, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.dryador))
                        )

                        .addChild(new ChapterNode(OBSIDIAN_HEART, ItemsRegistry.obsidianHeart.get(), Style.IRON, RegisterUnlockables.obsidianHeart)
                            .addHintsDescription(Component.translatable("codex.valoria.firron.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(FIRRON, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.firron))
                        )

                        .addChild(new ChapterNode(FORTRESS, ItemsRegistry.wickedAmethyst.get(), Style.CRYPT, RegisterUnlockables.fortress)
                                .addHintsDescription(Component.translatable("codex.valoria.fortress.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(SUSPICIOUS_GEM, ItemsRegistry.suspiciousGem.get(), Style.IRON, RegisterUnlockables.suspiciousGem)
                                .addHintsDescription(Component.translatable("codex.valoria.suspicious_gem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                .addChild(new ChapterNode(WICKED_CRYSTAL, Items.SKELETON_SKULL, Style.DIAMOND, RegisterUnlockables.wickedCrystal)
                                    .addChild(new ChapterNode(THE_END, ItemsRegistry.medicatedHarmonyHeart.get(), Style.DIAMOND, true))
                                )
                            )
                        )
                    )
                )

                .addChild(new ChapterNode(CHRONICLES, ItemsRegistry.page.get(), Style.GOLD)
                    .addChild(new ChapterNode(ELEMENTAL_COLLAPSE, ItemsRegistry.elementalCrystal.get()))
                    .addChild(new ChapterNode(RED_GAZE, ItemsRegistry.wickedAmethyst.get()))
                )

                .addChild(new ChapterNode(CRAFTING, Items.CRAFTING_TABLE)
                    .addChild(new ChapterNode(SOUL_ARTS, ItemsRegistry.soulShard.get(), Style.CRYPT, RegisterUnlockables.soulCollector)
                    .addHintsDescription(Component.translatable("codex.valoria.soul_arts.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(SOUL_ESSENCE, ItemsRegistry.soulCollector.get(), Style.CRYPT, true))
                        .addChild(new ChapterNode(SOUL_INFUSER, BlockRegistry.soulInfuser.get().asItem(), Style.IRON, true))
                    )
                    .addChild(new ChapterNode(ALCHEMY_STATION, BlockRegistry.alchemyStationTier1.get().asItem(), Style.STANDARD)
                        .addHintsDescription(Component.translatable("codex.valoria.alchemy.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(NETHER_ALCHEMY, BlockRegistry.alchemyStationTier2.get().asItem(), Style.IRON, RegisterUnlockables.netherAlchemy)
                            .addHintsDescription(Component.translatable("codex.valoria.alchemy.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                .addChild(new ChapterNode(ELEMENTAL_ALCHEMY, BlockRegistry.alchemyStationTier3.get().asItem(), Style.GOLD, RegisterUnlockables.elementalAlchemy)
                                    .addHintsDescription(Component.translatable("codex.valoria.alchemy.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                .addChild(new ChapterNode(VALORIA_ALCHEMY, BlockRegistry.alchemyStationTier4.get().asItem(), Style.DIAMOND, RegisterUnlockables.valoriaAlchemy)
                                    .addHintsDescription(Component.translatable("codex.valoria.alchemy.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                                )
                            )
                        )
                    )
                    .addChild(new ChapterNode(JEWELER_TABLE, BlockRegistry.jewelerTable.get().asItem()))
                    .addChild(new ChapterNode(KEG, BlockRegistry.keg.get().asItem()))
                    .addChild(new ChapterNode(STONE_CRUSHER, BlockRegistry.stoneCrusher.get().asItem())
                        .addChild(new ChapterNode(CRUSHABLES, ItemsRegistry.stoneGeode.get(), RegisterUnlockables.crushables)
                            .addHintsDescription(Component.translatable("codex.valoria.crushables.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                    )
                    .addChild(new ChapterNode(KILN, BlockRegistry.kiln.get().asItem())
                        .addChild(new ChapterNode(LITHIC_RUNES, ItemsRegistry.lithicRune.get(), RegisterUnlockables.lithicRunes)
                            .addHintsDescription(Component.translatable("codex.valoria.lithic_runes.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                        .addChild(new ChapterNode(RUNES, ItemsRegistry.rune.get(), RegisterUnlockables.runes)
                            .addHintsDescription(Component.translatable("codex.valoria.runes.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                        .addChild(new ChapterNode(VOID_SLATE_RUNES, ItemsRegistry.voidSlateRune.get(), RegisterUnlockables.voidSlateRunes)
                            .addHintsDescription(Component.translatable("codex.valoria.void_slate_runes.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                    )
                    .addChild(new ChapterNode(HEAVY_WORKBENCH, BlockRegistry.heavyWorkbench.get().asItem())
                        .addHintsDescription(Component.translatable("codex.valoria.heavy_workbench.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(PICK, ItemsRegistry.pick.get(), Style.STANDARD, RegisterUnlockables.pick)
                            .addHintsDescription(Component.translatable("codex.valoria.archaeology.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                    )
                )
            )

            .addChild(new ChapterNode(MATERIALS, Items.IRON_PICKAXE, Style.GOLD)
                .addChild(new ChapterNode(COBALT, ItemsRegistry.rawCobalt.get(), Style.IRON, RegisterUnlockables.cobalt)
                    .addHintsDescription(Component.translatable("codex.valoria.cobalt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(BLACK_GOLD, ItemsRegistry.blackGold.get(), Style.IRON, RegisterUnlockables.blackGold)
                        .addHintsDescription(Component.translatable("codex.valoria.black_gold.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(ELEMENTAL_MANIPULATOR, BlockRegistry.elementalManipulator.get().asItem(), Style.GOLD, true)
                            .addHintsDescription(Component.translatable("codex.valoria.elemental_manipulator.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                            .addChild(new ChapterNode(NATURE_CORE, ItemsRegistry.natureCore.get(), Style.IRON, RegisterUnlockables.natureCore))
                            .addChild(new ChapterNode(AQUARIUS_CORE, ItemsRegistry.aquariusCore.get(), Style.IRON, RegisterUnlockables.aquariusCore))
                            .addChild(new ChapterNode(INFERNAL_CORE, ItemsRegistry.infernalCore.get(), Style.IRON, RegisterUnlockables.infernalCore))
                            .addChild(new ChapterNode(VOID_CORE, ItemsRegistry.voidCore.get(), Style.IRON, RegisterUnlockables.voidCore))
                        )
                    )
                )
                .addChild(new ChapterNode(ROT, ItemsRegistry.rot.get(), Style.CRYPT, RegisterUnlockables.rot)
                    .addHintsDescription(Component.translatable("codex.valoria.rot.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(JADE, ItemsRegistry.jade.get(), Style.IRON, RegisterUnlockables.jade))
                .addChild(new ChapterNode(PEARLIUM, ItemsRegistry.pearliumIngot.get(), Style.IRON, RegisterUnlockables.pearlium))
                .addChild(new ChapterNode(PYRATITE, ItemsRegistry.pyratite.get(), Style.GOLD, RegisterUnlockables.pyratite)
                    .addHintsDescription(Component.translatable("codex.valoria.pyratite.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(ANCIENT_METALS, ItemsRegistry.ancientIngot.get(), Style.IRON, RegisterUnlockables.ancientMetals)
                    .addHintsDescription(Component.translatable("codex.valoria.ancient_metals.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(ETHEREAL, ItemsRegistry.etherealShard.get(), Style.GOLD, RegisterUnlockables.ethereal)
                    .addHintsDescription(Component.translatable("codex.valoria.ethereal.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
            )

            .addChild(new ChapterNode(VALORIA_PORTAL, BlockRegistry.valoriaPortalFrame.get().asItem(), Style.GOLD, RegisterUnlockables.valoriaPortal)
                .addHintsDescription(Component.translatable("codex.valoria.valoria_portal.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(SURVIVAL, ItemsRegistry.gasMask.get(), Style.IRON, RegisterUnlockables.valoriaVisit)
                    .addHintsDescription(Component.translatable("codex.valoria.valoria_dimension.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(NIHILITY_MONITOR, ItemsRegistry.nihilityMonitor.get(), Style.GOLD, true))
                .addChild(new ChapterNode(SHADE_BLOSSOM, ItemsRegistry.shadeBlossomLeaf.get(), Style.DIAMOND, RegisterUnlockables.shadeBlossom)
                    .addHintsDescription(Component.translatable("codex.valoria.shade_blossom.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )
                .addChild(new ChapterNode(BLOODBORNE_PATH, ItemsRegistry.remains.get(), Style.CRYPT, RegisterUnlockables.remains)
                    .addHintsDescription(Component.translatable("codex.valoria.bloodborne_path.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(COLOSSI_REMAINS, ItemsRegistry.remains.get(), RegisterUnlockables.monstrosities))
                .addChild(new ChapterNode(CRIMTANE_FORGING, ItemsRegistry.crimtaneIngot.get(), RegisterUnlockables.crimtane)
                    .addHintsDescription(Component.translatable("codex.valoria.crimtane.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )
            )
        );

        int offset = 0;
        if(onInit(rootNode)){
            layoutTree(rootNode, 0, -512 + offset);
        }

        rebuildSidebar();
    }

    public static void rebuildSidebar() {
        rebuildSidebar("");
    }

    public static void rebuildSidebar(String filter) {
        sidebarEntries.clear();
        if (rootNode != null) buildSidebarDFS(rootNode, 0, filter.toLowerCase());
    }

    private static void buildSidebarDFS(ChapterNode node, int depth, String filter) {
        if (node.entry != null) {
            SidebarEntry sidebarEntry = new SidebarEntry(node.entry, depth);

            String title = node.entry.translate.getString().toLowerCase();
            boolean matches = filter.isEmpty() || (title.contains(filter) && sidebarEntry.entry.isUnlocked());
            if (matches) {
                sidebarEntries.add(sidebarEntry);
            }
        }

        if (!node.isCollapsed || !filter.isEmpty()) {
            for (ChapterNode child : node.children) {
                buildSidebarDFS(child, depth + 1, filter);
            }
        }
    }

    private static final int spacingX = 25;
    private static final int spacingY = 35;
    private static int measureWidth(ChapterNode node){
        if(node.children.isEmpty()) return spacingX;
        int width = 0;
        for(ChapterNode child : node.children){
            width += measureWidth(child);
        }

        return Math.max(width, spacingX);
    }

    private static int layoutTree(ChapterNode node, int depth, int x){
        int y = depth * -spacingY;
        if (node.children.isEmpty()) {
            int nodeWidth = measureWidth(node);
            placeEntry(node, x + nodeWidth / 2, y);
            return nodeWidth + spacingX;
        }

        int totalChildrenWidth = 0;
        int currentChildX = x;

        List<Integer> childNodeCenters = new ArrayList<>();
        List<ChapterNode> reversedChildren = new ArrayList<>();
        for (ChapterNode child : node.children) reversedChildren.add(child);
        Collections.reverse(reversedChildren);
        for (ChapterNode child : reversedChildren) {
            int childSubtreeWidth = layoutTree(child, depth + 1, currentChildX);
            totalChildrenWidth += childSubtreeWidth;
            currentChildX = x + totalChildrenWidth;
            childNodeCenters.add(child.entry.x);
        }

        int centerX = (Collections.min(childNodeCenters) + Collections.max(childNodeCenters)) / 2;
        placeEntry(node, centerX, y);
        int nodeWidth = measureWidth(node);
        int childrenSpan = totalChildrenWidth - spacingX;
        return Math.max(childrenSpan, nodeWidth) + spacingX;
    }

    private static void placeEntry(ChapterNode node, int x, int y){
        CodexEntry entry = addEntry(node, x, y);
        if(onEntryAdded(entry)){
            entries.add(entry);
            if (entry.isUnlocked() && !entry.isHidden()) {
                openedEntries.add(entry);
            }
        } else {
            entry.hide();
        }
    }

    private static boolean onInit(ChapterNode root) {
        return !MinecraftForge.EVENT_BUS.post(new OnInit(root));
    }

    private static boolean onEntryAdded(CodexEntry entry) {
        return !MinecraftForge.EVENT_BUS.post(new EntryAdded(entry));
    }

    private static CodexEntry addEntry(ChapterNode node, int x, int y) {
        return new CodexEntry(node, x, y);
    }
}