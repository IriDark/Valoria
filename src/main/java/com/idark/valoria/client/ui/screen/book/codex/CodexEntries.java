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
    public static Seq<SidebarEntry> sidebarEntries = new Seq<>();
    public static Seq<SidebarEntry> openedEntries = new Seq<>();

    public static Chapter MAIN_CHAPTER, PAGES_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, SURVIVAL, COMBAT,

    PICK, HEAVY_WORKBENCH, STONE_CRUSHER, VALORIA_PORTAL,

    BOSSES,
    UNDEAD,
    KING_CRAB,
    NECROMANCER_GRIMOIRE, NECROMANCER,
    SUSPICIOUS_GEM, WICKED_CRYSTAL,

    CRYPT, FORTRESS,

    ELEMENTALS,
    HARMONY_ELEMENTALS,

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
        new GeneralPage("codex.valoria.treasure.gems"),
        new GeneralPage("codex.valoria.treasure.gems.about")
        .withCustomTitle("codex.valoria.treasure.gems.name"));

        MEDICINE_CHAPTER = new Chapter(
        "codex.valoria.medicine.name",
        new GeneralPage("codex.valoria.medicine"));

        VALORIA_PORTAL = new Chapter(
        "codex.valoria.valoria_portal.name",
        new GeneralPage().addTitle("codex.valoria.valoria_portal.name").addText("codex.valoria.valoria_portal").addImage(new ResourceLocation(Valoria.ID, "textures/gui/book/valoria_portal.png"), 127, 125),
        new GeneralPage().addTitle("codex.valoria.valoria_portal.name").addText("codex.valoria.valoria_portal").addImage(new ResourceLocation(Valoria.ID, "textures/gui/book/valoria_portal.png"), 127, 125));

        PICK = new Chapter(
        "codex.valoria.pick.name",
        new GeneralPage("codex.valoria.pick"));

        STONE_CRUSHER = new Chapter(
        "codex.valoria.stone_crusher",
        new GeneralPage("codex.valoria.stone_crusher.description").withCustomTitle("codex.valoria.stone_crusher").addRecipe(BlockRegistry.stoneCrusher.get().asItem().getDefaultInstance()));

        HEAVY_WORKBENCH = new Chapter(
        "codex.valoria.heavy_workbench.name",
        new GeneralPage("codex.valoria.heavy_workbench"));

        KING_CRAB = new Chapter(
        "codex.valoria.king_crab.name",
        new GeneralPage("codex.valoria.king_crab").addEntity(EntityTypeRegistry.KING_CRAB.get(), 32, false));

        BOSSES = new Chapter(
        "codex.valoria.bosses.name",
        new GeneralPage("codex.valoria.bosses"),
        new GeneralPage("codex.valoria.bosses_continuation").hideTitle());

        NECROMANCER_GRIMOIRE = new Chapter(
        "codex.valoria.necromancer_grimoire.name",
        new GeneralPage("codex.valoria.necromancer_grimoire"),
        new GeneralPage("codex.valoria.necromancer_grimoire_continuation").hideTitle()).setUnknownKey("codex.valoria.necromancer.name");

        NECROMANCER = new Chapter(
        "codex.valoria.necromancer.name",
        new GeneralPage("codex.valoria.necromancer"),
        new GeneralPage("codex.valoria.necromancer_continuation").hideTitle());

        HARMONY_CROWN = new Chapter(
        "codex.valoria.harmony_crown.name",
        new GeneralPage("codex.valoria.harmony_crown"),
        new GeneralPage("codex.valoria.harmony_crown.continuation").hideTitle()).setUnknownKey("codex.valoria.dryador.name");

        HARMONY_EMPEROR = new Chapter(
        "codex.valoria.harmony_emperor.name",
        new GeneralPage("codex.valoria.harmony_emperor"));

        DRYADOR = new Chapter(
        "codex.valoria.dryador.name",
        new GeneralPage("codex.valoria.dryador").hideTitle());

        SUSPICIOUS_GEM = new Chapter(
        "codex.valoria.suspicious_gem.name",
        new GeneralPage("codex.valoria.suspicious_gem"),
        new GeneralPage("codex.valoria.suspicious_gem_continuation").hideTitle()).setUnknownKey("codex.valoria.wicked_crystal.name");

        WICKED_CRYSTAL = new Chapter(
        "codex.valoria.wicked_crystal.name",
        new GeneralPage("codex.valoria.wicked_crystal"),
        new GeneralPage("codex.valoria.wicked_crystal_continuation").hideTitle());

        UNDEAD = new Chapter(
        "codex.valoria.undead.name",
        new GeneralPage("codex.valoria.undead"));

        ELEMENTALS = new Chapter(
        "codex.valoria.elementals.name",
        new GeneralPage("codex.valoria.elementals").hideTitle(),
        new GeneralPage("codex.valoria.elementals.continuation").hideTitle());

        HARMONY_ELEMENTALS = new Chapter(
        "codex.valoria.harmony_elementals.name",
        new GeneralPage("codex.valoria.harmony_elementals").hideTitle(),
        new GeneralPage("codex.valoria.harmony_elementals.continuation").hideTitle());

        ELEMENTAL_EMPERORS = new Chapter(
        "codex.valoria.elemental_emperors.name",
        new GeneralPage("codex.valoria.elemental_emperors").hideTitle(),
        new GeneralPage("codex.valoria.elemental_emperors.continuation").hideTitle());

        CRUSHABLES = new Chapter(
        "codex.valoria.crushables.name",
        new GeneralPage("codex.valoria.crushables"));

        COBALT = new Chapter(
        "codex.valoria.cobalt.name",
        new GeneralPage("codex.valoria.cobalt"),
        new GeneralPage("codex.valoria.cobalt_continuation").hideTitle());

        BLACK_GOLD = new Chapter(
        "codex.valoria.black_gold.name",
        new GeneralPage("codex.valoria.black_gold"));

        NATURE_CORE = new Chapter(
        "codex.valoria.nature_core.name",
        new GeneralPage("codex.valoria.nature_core"));

        AQUARIUS_CORE = new Chapter(
        "codex.valoria.aquarius_core.name",
        new GeneralPage("codex.valoria.aquarius_core"));

        INFERNAL_CORE = new Chapter(
        "codex.valoria.infernal_core.name",
        new GeneralPage("codex.valoria.infernal_core"));

        VOID_CORE = new Chapter(
        "codex.valoria.void_core.name",
        new GeneralPage("codex.valoria.void_core"));

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
        new GeneralPage("codex.valoria.nihility_continuation").hideTitle(),
        new GeneralPage("codex.valoria.rotting"),
        new GeneralPage("codex.valoria.rotting_continuation").hideTitle());

        COMBAT = new Chapter(
        "codex.valoria.combat.name",
        new GeneralPage("codex.valoria.combat"),
        new GeneralPage("codex.valoria.attributes"),
        new GeneralPage("codex.valoria.elemental_combat"),
        new GeneralPage("codex.valoria.elemental_combat_continuation").hideTitle());
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
        CodexEntries.sidebarEntries.clear();
        CodexEntries.openedEntries.clear();

        ChapterNode root = new ChapterNode(PAGES_CHAPTER, ItemsRegistry.page.get(), Style.GOLD)
        .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
        .addChild(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage)

        .addChild(new ChapterNode(MAIN_CHAPTER, ItemsRegistry.codex.get(), Style.GOLD)
            .addChild(new ChapterNode(COMBAT, ItemsRegistry.etherealSword.get(), Style.IRON))

            .addChild(new ChapterNode(CRUSHABLES, ItemsRegistry.stoneGeode.get().asItem(), RegisterUnlockables.crushables)
            .addHintsDescription(Component.translatable("codex.valoria.crushables.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                .addChild(new ChapterNode(STONE_CRUSHER, BlockRegistry.stoneCrusher.get().asItem(), RegisterUnlockables.stoneCrusher)
                    .addChild(new ChapterNode(HEAVY_WORKBENCH, BlockRegistry.heavyWorkbench.get().asItem(), Style.STANDARD, RegisterUnlockables.heavyWorkbench)
                    .addHintsDescription(Component.translatable("codex.valoria.heavy_workbench.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(PICK, ItemsRegistry.pick.get(), Style.STANDARD, RegisterUnlockables.pick))
                    )
                )
            )

            .addChild(new ChapterNode(ELEMENTALS, ItemsRegistry.elementalCrystal.get(), Style.GOLD)
                .addChild(new ChapterNode(COBALT, ItemsRegistry.rawCobalt.get(), Style.IRON, RegisterUnlockables.cobalt)
                    .addHintsDescription(Component.translatable("codex.valoria.cobalt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(BLACK_GOLD, ItemsRegistry.blackGold.get(), Style.IRON, RegisterUnlockables.blackGold)
                        .addHintsDescription(Component.translatable("codex.valoria.black_gold.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(NATURE_CORE, ItemsRegistry.natureCore.get(), Style.GOLD, RegisterUnlockables.natureCore))
                        .addChild(new ChapterNode(AQUARIUS_CORE, ItemsRegistry.aquariusCore.get(), Style.GOLD, RegisterUnlockables.aquariusCore))
                        .addChild(new ChapterNode(INFERNAL_CORE, ItemsRegistry.infernalCore.get(), Style.GOLD, RegisterUnlockables.infernalCore))
                        .addChild(new ChapterNode(VOID_CORE, ItemsRegistry.voidCore.get(), Style.GOLD, RegisterUnlockables.voidCore))
                    )
                )

                .addChild(new ChapterNode(KING_CRAB, ItemsRegistry.crabClaw.get(), RegisterUnlockables.kingCrab))
                .addChild(new ChapterNode(HARMONY_ELEMENTALS, ItemsRegistry.harmonyHeart.get(), RegisterUnlockables.harmonyEntities).addHintsDescription(Component.translatable("codex.valoria.harmony_elementals.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
            )

            .addChild(new ChapterNode(ELEMENTAL_EMPERORS, ItemsRegistry.harmonyCrown.get(), Style.GOLD)
                .addChild(new ChapterNode(HARMONY_EMPEROR, Items.SKELETON_SKULL))
            )
        );

        ChapterNode bossesRoot = new ChapterNode(BOSSES, Items.SKELETON_SKULL, Style.CRYPT)
                .addChild(new ChapterNode(CRYPT, ItemsRegistry.cryptPage.get(), Style.CRYPT, RegisterUnlockables.crypt)
                    .addHintsDescription(Component.translatable("codex.valoria.crypt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(NECROMANCER_GRIMOIRE, ItemsRegistry.necromancerGrimoire.get(), Style.IRON, RegisterUnlockables.necromancerGrimoire)
                        .addHintsDescription(Component.translatable("codex.valoria.necromancer_grimoire.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))
                    )
                )

                .addChild(new ChapterNode(UNDEAD, Items.BONE, Style.STANDARD, RegisterUnlockables.undead))
                    .addChild(new ChapterNode(NECROMANCER, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.necromancer))
                    )
                )

                .addChild(new ChapterNode(HARMONY_CROWN, ItemsRegistry.harmonyCrown.get(), Style.IRON, RegisterUnlockables.harmonyCrown)
                    .addHintsDescription(Component.translatable("codex.valoria.harmony_crown.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(DRYADOR, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.dryador))
                )


                .addChild(new ChapterNode(VALORIA_PORTAL, BlockRegistry.valoriaPortalFrame.get().asItem(), Style.GOLD, RegisterUnlockables.valoriaPortal)
                    .addHintsDescription(Component.translatable("codex.valoria.valoria_portal.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(FORTRESS, ItemsRegistry.wickedAmethyst.get(), Style.CRYPT, RegisterUnlockables.fortress)
                        .addHintsDescription(Component.translatable("codex.valoria.fortress.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                        )
                    )

                    .addChild(new ChapterNode(SURVIVAL, ItemsRegistry.nihilityMonitor.get(), Style.IRON, RegisterUnlockables.valoriaVisit)
                    .addHintsDescription(Component.translatable("codex.valoria.valoria_dimension.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))

                    .addChild(new ChapterNode(SUSPICIOUS_GEM, ItemsRegistry.suspiciousGem.get(), Style.IRON, RegisterUnlockables.suspiciousGem)
                        .addHintsDescription(Component.translatable("codex.valoria.suspicious_gem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))))
                        .addChild(new ChapterNode(WICKED_CRYSTAL, Items.SKELETON_SKULL, Style.DIAMOND, RegisterUnlockables.wickedCrystal))
                    )
                );

        int offset = 0;
        if(onInit(root)){
            layoutTree(root, 0, -512 + offset);
        }

        if(onInit(bossesRoot)){
            layoutTree(bossesRoot, 5, offset);
        }

        buildSidebarDFS(root, 0);
        buildSidebarDFS(bossesRoot, 0);
    }

    private static void buildSidebarDFS(ChapterNode node, int depth) {
        if (node.entry != null) {
            SidebarEntry sidebarEntry = new SidebarEntry(node.entry, depth);
            sidebarEntries.add(sidebarEntry);

            if (node.entry.isUnlocked() && !node.entry.isHidden()) {
                openedEntries.add(sidebarEntry);
            }
        }

        for (ChapterNode child : node.children) {
            buildSidebarDFS(child, depth + 1);
        }
    }

    static int spacingX = 25;
    static int spacingY = 35;

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