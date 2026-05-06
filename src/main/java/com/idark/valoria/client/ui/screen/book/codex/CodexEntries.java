package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import pro.komaru.tridot.api.render.text.DotStyleEffects.*;
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

    private static ChapterNode rootNode, bossesRootNode;

    public static Chapter MAIN_CHAPTER, PAGES_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, SURVIVAL, COMBAT, CRAFTING,

    PICK, HEAVY_WORKBENCH, KEG, ALCHEMY_STATION, ELEMENTAL_MANIPULATOR, NETHER_ALCHEMY, ELEMENTAL_ALCHEMY, VALORIA_ALCHEMY, STONE_CRUSHER,
    NIHILITY_MONITOR, VALORIA_PORTAL, SHADE_BLOSSOM,

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
    HARMONY_EMPEROR,
    HARMONY_CROWN, DRYADOR,

    CRUSHABLES,
    COBALT,
    BLACK_GOLD,
    NATURE_CORE, AQUARIUS_CORE, INFERNAL_CORE, VOID_CORE

    ;

    public static void initChapters(){
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
        new GeneralPage("codex.valoria.heavy_workbench"),
        new GeneralPage().addItem(BlockRegistry.heavyWorkbench.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        KEG = new Chapter(
        "codex.valoria.keg.name",
        new GeneralPage("codex.valoria.keg"),
        new GeneralPage().addItem(BlockRegistry.keg.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        SHADE_BLOSSOM = new Chapter(
        "codex.valoria.shade_blossom.name",
        new GeneralPage("codex.valoria.shade_blossom"),
        new GeneralPage().addItem(BlockRegistry.shadeBlossom.get().asItem().getDefaultInstance(), false, 10, 10, 100));

        ALCHEMY_STATION = new Chapter(
        "codex.valoria.alchemy_station.name",
        new GeneralPage("codex.valoria.alchemy_station"));

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
        new GeneralPage("codex.valoria.necromancer_continuation").hideTitle());

        HARMONY_CROWN = new Chapter(
        "codex.valoria.harmony_crown.name",
        new GeneralPage("codex.valoria.harmony_crown"),
        new GeneralPage("codex.valoria.harmony_crown.continuation").hideTitle());

        HARMONY_EMPEROR = new Chapter(
        "codex.valoria.harmony_emperor.name",
        new GeneralPage("codex.valoria.harmony_emperor"));

        DRYADOR = new Chapter(
        "codex.valoria.dryador.name",
        new GeneralPage("codex.valoria.dryador").hideTitle());

        SUSPICIOUS_GEM = new Chapter(
        "codex.valoria.suspicious_gem.name",
        new GeneralPage("codex.valoria.suspicious_gem"),
        new GeneralPage("codex.valoria.suspicious_gem_continuation").hideTitle());

        WICKED_CRYSTAL = new Chapter(
        "codex.valoria.wicked_crystal.name",
        new GeneralPage("codex.valoria.wicked_crystal"),
        new GeneralPage("codex.valoria.wicked_crystal_continuation").hideTitle());

        UNDEAD = new Chapter(
        "codex.valoria.undead.name",
        new GeneralPage("codex.valoria.undead"));

        ELEMENTALS = new Chapter(
        "codex.valoria.elementals.name",
        new GeneralPage("codex.valoria.elementals"),
        new GeneralPage("codex.valoria.elementals.continuation").hideTitle());

        ENT = new Chapter(
        "codex.valoria.ent.name",
        new GeneralPage("codex.valoria.ent"),
        new GeneralPage("codex.valoria.ent.continuation").hideTitle());

        ELEMENTAL_EMPERORS = new Chapter(
        "codex.valoria.elemental_emperors.name",
        new GeneralPage("codex.valoria.elemental_emperors").hideTitle(),
        new GeneralPage("codex.valoria.elemental_emperors.continuation").hideTitle());

        CRUSHABLES = new Chapter(
        "codex.valoria.crushables.name",
        new GeneralPage("codex.valoria.crushables"));

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
        new GeneralPage("codex.valoria.crypt_continuation").hideTitle().addImage(new ResourceLocation(Valoria.ID, "textures/gui/book/crypt.png"), -5, 40, 128, 128));

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
                .addChild(new ChapterNode(WARRIOR_ARSENAL, ItemsRegistry.ironKatana.get(), Style.IRON)
                    .addChild(new ChapterNode(KATANAS, ItemsRegistry.samuraiKatana.get()))
                    .addChild(new ChapterNode(SCYTHES, ItemsRegistry.ironScythe.get()))
                    .addChild(new ChapterNode(SPEARS, ItemsRegistry.ironSpear.get()))
                )
                .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get(), RegisterUnlockables.aloe)
                .addHintsDescription(Component.translatable("codex.valoria.medicine.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
            )


            .addChild(new ChapterNode(BESTIARY, Items.KNOWLEDGE_BOOK, Style.IRON)
                .addHintsDescription(Component.translatable("codex.valoria.bestiary.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(SCAVENGERS, ItemsRegistry.scavenger.get(), RegisterUnlockables.scavenger)
                    .addHintsDescription(Component.translatable("codex.valoria.scavenger.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(FLESH_SENTINELS, ItemsRegistry.fleshSentinel.get(), RegisterUnlockables.fleshSentinel)
                    .addHintsDescription(Component.translatable("codex.valoria.flesh_sentinel.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(KING_CRAB, ItemsRegistry.crabClaw.get(), RegisterUnlockables.kingCrab)
                    .addHintsDescription(Component.translatable("codex.valoria.king_crab.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                .addChild(new ChapterNode(ENT, ItemsRegistry.harmonyHeart.get(), RegisterUnlockables.harmonyEntities)
                    .addHintsDescription(Component.translatable("codex.valoria.harmony_elementals.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
            )

            .addChild(new ChapterNode(CHRONICLES, ItemsRegistry.page.get(), Style.GOLD)
                .addChild(new ChapterNode(ELEMENTAL_COLLAPSE, ItemsRegistry.elementalCrystal.get()))
                .addChild(new ChapterNode(RED_GAZE, ItemsRegistry.wickedAmethyst.get()))
            )

            .addChild(new ChapterNode(CRAFTING, Items.CRAFTING_TABLE)
                .addChild(new ChapterNode(SOUL_ARTS, ItemsRegistry.soulShard.get(), Style.IRON, RegisterUnlockables.soulCollector)
                .addHintsDescription(Component.translatable("codex.valoria.soul_arts.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(SOUL_ESSENCE, ItemsRegistry.soulCollector.get()))
                    .addChild(new ChapterNode(SOUL_INFUSER, BlockRegistry.soulInfuser.get().asItem()))
                )

                .addChild(new ChapterNode(ALCHEMY_STATION, BlockRegistry.alchemyStationTier1.get().asItem(), Style.STANDARD, RegisterUnlockables.alchemyStation)
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

                .addChild(new ChapterNode(KEG, BlockRegistry.keg.get().asItem(), Style.STANDARD, true))
                .addChild(new ChapterNode(STONE_CRUSHER, BlockRegistry.stoneCrusher.get().asItem(), Style.STANDARD, true)
                    .addChild(new ChapterNode(CRUSHABLES, ItemsRegistry.stoneGeode.get(), RegisterUnlockables.crushables)
                    .addHintsDescription(Component.translatable("codex.valoria.crushables.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )

                .addChild(new ChapterNode(HEAVY_WORKBENCH, BlockRegistry.heavyWorkbench.get().asItem(), Style.STANDARD, RegisterUnlockables.heavyWorkbench)
                    .addHintsDescription(Component.translatable("codex.valoria.heavy_workbench.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(PICK, ItemsRegistry.pick.get(), Style.STANDARD, RegisterUnlockables.pick)
                    .addHintsDescription(Component.translatable("codex.valoria.archaeology.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                )
            )
        )

        .addChild(new ChapterNode(ELEMENTALS, ItemsRegistry.elementalCrystal.get(), Style.GOLD)
            .addChild(new ChapterNode(COBALT, ItemsRegistry.rawCobalt.get(), Style.IRON, RegisterUnlockables.cobalt)
                .addHintsDescription(Component.translatable("codex.valoria.cobalt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(BLACK_GOLD, ItemsRegistry.blackGold.get(), Style.IRON, RegisterUnlockables.blackGold)
                    .addHintsDescription(Component.translatable("codex.valoria.black_gold.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(ELEMENTAL_MANIPULATOR, BlockRegistry.elementalManipulator.get().asItem(), Style.GOLD, RegisterUnlockables.elementalManipulator)
                    .addHintsDescription(Component.translatable("codex.valoria.elemental_manipulator.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(NATURE_CORE, ItemsRegistry.natureCore.get(), Style.GOLD, RegisterUnlockables.natureCore))
                        .addChild(new ChapterNode(AQUARIUS_CORE, ItemsRegistry.aquariusCore.get(), Style.GOLD, RegisterUnlockables.aquariusCore))
                        .addChild(new ChapterNode(INFERNAL_CORE, ItemsRegistry.infernalCore.get(), Style.GOLD, RegisterUnlockables.infernalCore))
                        .addChild(new ChapterNode(VOID_CORE, ItemsRegistry.voidCore.get(), Style.GOLD, RegisterUnlockables.voidCore))
                    )
                )
            )

            .addChild(new ChapterNode(ELEMENTAL_EMPERORS, ItemsRegistry.harmonyCrown.get(), Style.GOLD)
                .addChild(new ChapterNode(HARMONY_EMPEROR, Items.SKELETON_SKULL))
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
                        .addChild(new ChapterNode(COLOSSI_REMAINS, ItemsRegistry.remains.get()))
                        .addChild(new ChapterNode(CRIMTANE_FORGING, ItemsRegistry.crimtaneIngot.get(), RegisterUnlockables.crimtane)
                            .addHintsDescription(Component.translatable("codex.valoria.crimtane.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                    )
                )
            )
        );

        bossesRootNode = new ChapterNode(BOSSES, Items.SKELETON_SKULL, Style.CRYPT)
                .addChild(new ChapterNode(CRYPT, ItemsRegistry.page.get(), Style.CRYPT, RegisterUnlockables.crypt)
                    .addHintsDescription(Component.translatable("codex.valoria.crypt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(NECROMANCER_GRIMOIRE, ItemsRegistry.necromancerGrimoire.get(), Style.IRON, RegisterUnlockables.necromancerGrimoire)
                        .addHintsDescription(Component.translatable("codex.valoria.necromancer_grimoire.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))
                    )
                )

                .addChild(new ChapterNode(UNDEAD, Items.BONE, Style.STANDARD, RegisterUnlockables.undead))
                    .addChild(new ChapterNode(NECROMANCER, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.necromancer))
                    )
                )

                .addChild(new ChapterNode(FORTRESS, ItemsRegistry.wickedAmethyst.get(), Style.CRYPT, RegisterUnlockables.fortress)
                    .addHintsDescription(Component.translatable("codex.valoria.fortress.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(SUSPICIOUS_GEM, ItemsRegistry.suspiciousGem.get(), Style.IRON, RegisterUnlockables.suspiciousGem)
                        .addHintsDescription(Component.translatable("codex.valoria.suspicious_gem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(WICKED_CRYSTAL, Items.SKELETON_SKULL, Style.DIAMOND, RegisterUnlockables.wickedCrystal))
                    )
                )

                .addChild(new ChapterNode(HARMONY_CROWN, ItemsRegistry.harmonyCrown.get(), Style.IRON, RegisterUnlockables.harmonyCrown)
                    .addHintsDescription(Component.translatable("codex.valoria.harmony_crown.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(DRYADOR, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.dryador))
                );

        int offset = 0;
        if(onInit(rootNode)){
            layoutTree(rootNode, 0, -512 + offset);
        }

        if(onInit(bossesRootNode)){
            layoutTree(bossesRootNode, 6, offset);
        }

        rebuildSidebar();
    }

    public static void rebuildSidebar() {
        rebuildSidebar("");
    }

    public static void rebuildSidebar(String filter) {
        sidebarEntries.clear();
        if (rootNode != null) buildSidebarDFS(rootNode, 0, filter.toLowerCase());
        if (bossesRootNode != null) buildSidebarDFS(bossesRootNode, 0, filter.toLowerCase());
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
        for (ChapterNode child : node.children) {
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