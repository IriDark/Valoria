package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.codex.checklist.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
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

public class CodexEntries{
    public static Seq<CodexEntry> entries = new Seq<>();
    public static Seq<BossEntry> bossEntries = new Seq<>();
    public static Chapter MAIN_CHAPTER, PAGES_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, BOSS_CHECKLIST,

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

    BLACK_GOLD,
    NATURE_CORE, AQUARIUS_CORE, INFERNAL_CORE, VOID_CORE

    ;

    public static void initChapters(){
        MAIN_CHAPTER = new Chapter(
        "codex.valoria.main.name",
        new TextPage("codex.valoria.main"),
        new TextPage("codex.valoria.main.continuation").hideTitle());

        BOSS_CHECKLIST = new Chapter("codex.valoria.bosses.name", new BossMainPage("codex.valoria.bosses.name", bossEntries));

        PAGES_CHAPTER = new Chapter(
        "codex.valoria.pages.name",
        new TextPage("codex.valoria.pages"));

        TREASURES_CHAPTER = new Chapter(
        "codex.valoria.jewelry",
        new TextPage("codex.valoria.treasures"),
        new TextPage("codex.valoria.treasure.gems"),
        new TextPage("codex.valoria.treasure.gems.about")
        .withCustomTitle("codex.valoria.treasure.gems.name"));

        MEDICINE_CHAPTER = new Chapter(
        "codex.valoria.medicine.name",
        new TextPage("codex.valoria.medicine"));

        VALORIA_PORTAL = new Chapter(
        "codex.valoria.valoria_portal.name",
        new PicturePage("codex.valoria.valoria_portal", new ResourceLocation(Valoria.ID, "textures/gui/book/valoria_portal.png"), 127, 15));

        PICK = new Chapter(
        "codex.valoria.pick.name",
        new TextPage("codex.valoria.pick"));

        STONE_CRUSHER = new Chapter(
        "codex.valoria.stone_crusher",
        new TextPage("codex.valoria.stone_crusher.description").withCustomTitle("codex.valoria.stone_crusher").withCraftEntry(BlockRegistry.stoneCrusher.get().asItem().getDefaultInstance()));

        HEAVY_WORKBENCH = new Chapter(
        "codex.valoria.heavy_workbench.name",
        new TextPage("codex.valoria.heavy_workbench"));

        KING_CRAB = new Chapter(
        "codex.valoria.king_crab.name",
        new TextPage("codex.valoria.king_crab").withEntity(EntityTypeRegistry.KING_CRAB.get()).setEntityData(191, 60, 32));

        BOSSES = new Chapter(
        "codex.valoria.bosses.name",
        new TextPage("codex.valoria.bosses"),
        new TextPage("codex.valoria.bosses_continuation").hideTitle());

        NECROMANCER_GRIMOIRE = new Chapter(
        "codex.valoria.necromancer_grimoire.name",
        new TextPage("codex.valoria.necromancer_grimoire"),
        new TextPage("codex.valoria.necromancer_grimoire_continuation").hideTitle()).setUnknownKey("codex.valoria.necromancer.name");

        NECROMANCER = new Chapter(
        "codex.valoria.necromancer.name",
        new TextPage("codex.valoria.necromancer"),
        new TextPage("codex.valoria.necromancer_continuation").hideTitle());

        HARMONY_CROWN = new Chapter(
        "codex.valoria.harmony_crown.name",
        new TextPage("codex.valoria.harmony_crown"),
        new TextPage("codex.valoria.harmony_crown.continuation").hideTitle()).setUnknownKey("codex.valoria.dryador.name");

        HARMONY_EMPEROR = new Chapter(
        "codex.valoria.harmony_emperor.name",
        new TextPage("codex.valoria.harmony_emperor"));

        DRYADOR = new Chapter(
        "codex.valoria.dryador.name",
        new TextPage("codex.valoria.dryador").hideTitle());

        SUSPICIOUS_GEM = new Chapter(
        "codex.valoria.suspicious_gem.name",
        new TextPage("codex.valoria.suspicious_gem"),
        new TextPage("codex.valoria.suspicious_gem_continuation").hideTitle()).setUnknownKey("codex.valoria.wicked_crystal.name");

        WICKED_CRYSTAL = new Chapter(
        "codex.valoria.wicked_crystal.name",
        new TextPage("codex.valoria.wicked_crystal"),
        new TextPage("codex.valoria.wicked_crystal_continuation").hideTitle());

        UNDEAD = new Chapter(
        "codex.valoria.undead.name",
        new TextPage("codex.valoria.undead"));

        ELEMENTALS = new Chapter(
        "codex.valoria.elementals.name",
        new TextPage("codex.valoria.elementals").hideTitle(),
        new TextPage("codex.valoria.elementals.continuation").hideTitle());

        HARMONY_ELEMENTALS = new Chapter(
        "codex.valoria.harmony_elementals.name",
        new TextPage("codex.valoria.harmony_elementals").hideTitle(),
        new TextPage("codex.valoria.harmony_elementals.continuation").hideTitle());

        ELEMENTAL_EMPERORS = new Chapter(
        "codex.valoria.elemental_emperors.name",
        new TextPage("codex.valoria.elemental_emperors").hideTitle(),
        new TextPage("codex.valoria.elemental_emperors.continuation").hideTitle());

        BLACK_GOLD = new Chapter(
        "codex.valoria.black_gold.name",
        new TextPage("codex.valoria.black_gold"));

        NATURE_CORE = new Chapter(
        "codex.valoria.nature_core.name",
        new TextPage("codex.valoria.nature_core"));

        AQUARIUS_CORE = new Chapter(
        "codex.valoria.aquarius_core.name",
        new TextPage("codex.valoria.aquarius_core"));

        INFERNAL_CORE = new Chapter(
        "codex.valoria.infernal_core.name",
        new TextPage("codex.valoria.infernal_core"));

        VOID_CORE = new Chapter(
        "codex.valoria.void_core.name",
        new TextPage("codex.valoria.void_core"));

        CRYPT = new Chapter(
        "codex.valoria.crypt.name",
        new TextPage("codex.valoria.crypt"),
        new PicturePage("codex.valoria.crypt_continuation", new ResourceLocation(Valoria.ID, "textures/gui/book/crypt.png"), -5, 40).hideTitle());

        FORTRESS = new Chapter(
        "codex.valoria.fortress.name",
        new TextPage("codex.valoria.fortress"),
        new TextPage("codex.valoria.fortress_continuation").hideTitle());
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
        ChapterNode root = new ChapterNode(PAGES_CHAPTER, ItemsRegistry.page.get(), Style.GOLD)
        .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
        .addChild(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage)

        .addChild(new ChapterNode(MAIN_CHAPTER, ItemsRegistry.codex.get(), Style.GOLD)
            .addChild(new ChapterNode(STONE_CRUSHER, BlockRegistry.stoneCrusher.get().asItem())
                .addChild(new ChapterNode(HEAVY_WORKBENCH, BlockRegistry.heavyWorkbench.get().asItem(), Style.STANDARD, RegisterUnlockables.heavyWorkbench)
                .addHintsDescription(Component.translatable("codex.valoria.heavy_workbench.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                    .addChild(new ChapterNode(PICK, ItemsRegistry.pick.get(), Style.STANDARD, RegisterUnlockables.pick))
                )
            )

            .addChild(new ChapterNode(ELEMENTALS, ItemsRegistry.elementalCrystal.get(), Style.GOLD)
                .addChild(new ChapterNode(BLACK_GOLD, ItemsRegistry.blackGold.get(), Style.IRON, RegisterUnlockables.blackGold)
                    .addHintsDescription(
                        Component.translatable("codex.valoria.black_gold.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                    )

                    .addChild(new ChapterNode(NATURE_CORE, ItemsRegistry.natureCore.get(), Style.GOLD, RegisterUnlockables.natureCore))
                    .addChild(new ChapterNode(AQUARIUS_CORE, ItemsRegistry.aquariusCore.get(), Style.GOLD, RegisterUnlockables.aquariusCore))
                    .addChild(new ChapterNode(INFERNAL_CORE, ItemsRegistry.infernalCore.get(), Style.GOLD, RegisterUnlockables.infernalCore))
                    .addChild(new ChapterNode(VOID_CORE, ItemsRegistry.voidCore.get(), Style.GOLD, RegisterUnlockables.voidCore))
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

                    .addChild(new ChapterNode(SUSPICIOUS_GEM, ItemsRegistry.suspiciousGem.get(), Style.IRON, RegisterUnlockables.suspiciousGem)
                        .addHintsDescription(Component.translatable("codex.valoria.suspicious_gem.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f))))
                        .addChild(new ChapterNode(WICKED_CRYSTAL, Items.SKELETON_SKULL, Style.DIAMOND, RegisterUnlockables.wickedCrystal)
                        )
                    )
                );

        int offset = 0;
        if(onInit(root)){
            layoutTree(root, 0, -512 + offset);
        }

        if(onInit(bossesRoot)){
            layoutTree(bossesRoot, 5, offset);
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
        if(!onEntryAdded(entry)){
            entries.add(entry);
        } else {
            entry.hide();
        }
    }

    private static boolean onInit(ChapterNode root) {
        return !MinecraftForge.EVENT_BUS.post(new OnInit(root));
    }

    private static boolean onEntryAdded(CodexEntry entry) {
        return MinecraftForge.EVENT_BUS.post(new EntryAdded(entry));
    }

    private static CodexEntry addEntry(ChapterNode node, int x, int y) {
        return new CodexEntry(node, x, y);
    }
}