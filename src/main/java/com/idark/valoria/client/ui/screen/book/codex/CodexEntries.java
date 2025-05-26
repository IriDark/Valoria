package com.idark.valoria.client.ui.screen.book.codex;

import com.idark.valoria.api.events.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.api.unlockable.types.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
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
    public static Chapter MAIN_CHAPTER, PAGES_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER,

    PICK, TINKERER_WORKBENCH,

    BOSSES,
    UNDEAD,
    NECROMANCER_GRIMOIRE, NECROMANCER,
    HARMONY_CROWN, DRYADOR,
    SUSPICIOUS_GEM, WICKED_CRYSTAL,

    CRYPT, FORTRESS,

    BLACK_GOLD,
    NATURE_CORE, AQUARIUS_CORE, INFERNAL_CORE, VOID_CORE

    ;

    public static void initChapters(){
        MAIN_CHAPTER = new Chapter(
        "codex.valoria.main.name",
        new MainPage("codex.valoria.main"),
        new TextPage("codex.valoria.knowledge")
        .withCraftEntry(ItemsRegistry.codex.get().getDefaultInstance(), Items.PAPER.getDefaultInstance(), Items.BOOK.getDefaultInstance()));

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

        PICK = new Chapter(
        "codex.valoria.pick.name",
        new TextPage("codex.valoria.pick"));

        TINKERER_WORKBENCH = new Chapter(
        "codex.valoria.tinkerer_workbench.name",
        new TextPage("codex.valoria.tinkerer_workbench"));

        BOSSES = new Chapter(
        "codex.valoria.bosses.name",
        new TextPage("codex.valoria.bosses"),
        new TextPage("codex.valoria.bosses_continuation").hideTitle());

        NECROMANCER_GRIMOIRE = new Chapter(
        "codex.valoria.necromancer_grimoire.name",
        new TextPage("codex.valoria.necromancer_grimoire"),
        new TextPage("codex.valoria.necromancer_grimoire_continuation").hideTitle());

        NECROMANCER = new Chapter(
        "codex.valoria.necromancer.name",
        new TextPage("codex.valoria.necromancer"),
        new TextPage("codex.valoria.necromancer_continuation").hideTitle());

        HARMONY_CROWN = new Chapter(
        "codex.valoria.harmony_crown.name",
        new TextPage("codex.valoria.harmony_crown"),
        new TextPage("codex.valoria.harmony_crown.continuation").hideTitle());

        DRYADOR = new Chapter(
        "codex.valoria.dryador.name",
        new TextPage("codex.valoria.dryador"),
        new TextPage("codex.valoria.dryador.continuation").hideTitle());

        SUSPICIOUS_GEM = new Chapter(
        "codex.valoria.suspicious_gem.name",
        new TextPage("codex.valoria.suspicious_gem"),
        new TextPage("codex.valoria.suspicious_gem_continuation").hideTitle());

        WICKED_CRYSTAL = new Chapter(
        "codex.valoria.wicked_crystal.name",
        new TextPage("codex.valoria.wicked_crystal"),
        new TextPage("codex.valoria.wicked_crystal_continuation").hideTitle());

        UNDEAD = new Chapter(
        "codex.valoria.undead.name",
        new TextPage("codex.valoria.undead"));

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
        new TextPage("codex.valoria.crypt_continuation").hideTitle());

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
        ChapterNode root = new ChapterNode(PAGES_CHAPTER, ItemsRegistry.page.get(), Style.GOLD).addChild(new ChapterNode(MAIN_CHAPTER, ItemsRegistry.codex.get(), Style.GOLD)
            .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
            .addChild(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage)
            .addChild(new ChapterNode(PICK, ItemsRegistry.pick.get(), Style.STANDARD, RegisterUnlockables.pick))
                .addChild(new ChapterNode(TINKERER_WORKBENCH, BlockRegistry.tinkererWorkbench.get().asItem(), Style.STANDARD, RegisterUnlockables.tinkererWorkbench)
                    .addHintsDescription(
                        Component.translatable("codex.valoria.tinkerer_workbench.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                    )
                )

            .addChild(new ChapterNode(CRYPT, ItemsRegistry.cryptPage.get(), Style.CRYPT, RegisterUnlockables.crypt)
                .addHintsDescription(
                    Component.translatable("codex.valoria.crypt.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                )
            )

            .addChild(new ChapterNode(FORTRESS, ItemsRegistry.wickedAmethyst.get(), Style.CRYPT, RegisterUnlockables.fortress)
                .addHintsDescription(
                    Component.translatable("codex.valoria.fortress.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                )
            )

        .addChild(new ChapterNode(BOSSES, Items.SKELETON_SKULL, Style.CRYPT)
            .addChild(new ChapterNode(NECROMANCER_GRIMOIRE, ItemsRegistry.necromancerGrimoire.get(), Style.IRON, RegisterUnlockables.necromancerGrimoire)
                .addHintsDescription(
                    Component.translatable("codex.valoria.necromancer_grimoire.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
                )

                .addChild(new ChapterNode(UNDEAD, Items.SKELETON_SKULL, Style.STANDARD, RegisterUnlockables.undead))
                .addChild(new ChapterNode(NECROMANCER, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.necromancer))
            )

            .addChild(new ChapterNode(HARMONY_CROWN, ItemsRegistry.harmonyCrown.get(), Style.IRON, RegisterUnlockables.harmonyCrown)
                .addChild(new ChapterNode(DRYADOR, Items.SKELETON_SKULL, Style.GOLD, RegisterUnlockables.dryador))
            )

            .addChild(new ChapterNode(SUSPICIOUS_GEM, ItemsRegistry.suspiciousGem.get(), Style.IRON, RegisterUnlockables.suspiciousGem)
                .addChild(new ChapterNode(WICKED_CRYSTAL, Items.SKELETON_SKULL, Style.DIAMOND, RegisterUnlockables.wickedCrystal))
            )
        )

        .addChild(new ChapterNode(BLACK_GOLD, ItemsRegistry.blackGold.get(), Style.IRON, RegisterUnlockables.blackGold)
            .addHintsDescription(
                Component.translatable("codex.valoria.black_gold.hint").withStyle(DotStyle.of().color(Col.gray).effect(PulseAlphaFX.of(1f)))
            )

            .addChild(new ChapterNode(NATURE_CORE, ItemsRegistry.natureCore.get(), Style.GOLD, RegisterUnlockables.natureCore))
            .addChild(new ChapterNode(AQUARIUS_CORE, ItemsRegistry.aquariusCore.get(), Style.GOLD, RegisterUnlockables.aquariusCore))
            .addChild(new ChapterNode(INFERNAL_CORE, ItemsRegistry.infernalCore.get(), Style.GOLD, RegisterUnlockables.infernalCore))
            .addChild(new ChapterNode(VOID_CORE, ItemsRegistry.voidCore.get(), Style.GOLD, RegisterUnlockables.voidCore))
        ))

        ;

        int offset = 0;
        if(!onInit(root)){
            layoutTree(root, 0, -measureWidth(root) / 5 - 140 + ((root.children.size % 2 == 1) ? -6 : 0) + offset);
        }
    }

    static int spacingX = 35;
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

        if(node.children.isEmpty()){
            CodexEntry entry = addEntry(node, x, y);
            if(!onEntryAdded(entry)){
                entries.add(entry);
            } else {
                entry.hide();
            }

            return spacingX;
        }

        int totalWidth = 0;
        int totalWidth2 = 0;
        List<Integer> childCenters = new ArrayList<>();
        List<Integer> childCenters2 = new ArrayList<>();

        int depthChange = 1;
        for(ChapterNode child : node.children){
            int newDepth = depth == 0 ? depth + depthChange : depth + (depth < 0 ? -1 : 1);
            int totWidth = newDepth < 0 ? totalWidth2 : totalWidth;

            int childWidth = measureWidth(child);
            int childX = x + totWidth;

            layoutTree(child, newDepth, childX);
            if(newDepth < 0) childCenters2.add(childX);
            else childCenters.add(childX);
            if(newDepth < 0) totalWidth2 += childWidth;
            else totalWidth += childWidth;

            depthChange *= -1;
        }


        int centerX = childCenters.isEmpty() ? 0 : (Collections.min(childCenters) + Collections.max(childCenters)) / 2;
        int centerX2 = childCenters2.isEmpty() ? 0 :(Collections.min(childCenters2) + Collections.max(childCenters2)) / 2;

        centerX = Math.max(centerX,centerX2);
        centerX = Mth.clamp(centerX, -512, 512);
        int we = depth < 0 ? totalWidth2 : totalWidth;

        CodexEntry entry = addEntry(node, centerX, y);
        if(!onEntryAdded(entry)){
            entries.add(entry);
        } else {
            entry.hide();
        }

        return we;
    }

    private static boolean onInit(ChapterNode root) {
        return MinecraftForge.EVENT_BUS.post(new CodexEvent.OnInit(root));
    }

    private static boolean onEntryAdded(CodexEntry entry) {
        return MinecraftForge.EVENT_BUS.post(new EntryAdded(entry));
    }

    private static CodexEntry addEntry(ChapterNode node, int x, int y) {
        return new CodexEntry(node, x, y);
    }
}