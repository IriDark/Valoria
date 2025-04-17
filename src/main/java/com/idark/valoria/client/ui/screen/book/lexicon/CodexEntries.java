package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.api.events.*;
import com.idark.valoria.api.events.CodexEvent.*;
import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.*;
import pro.komaru.tridot.api.render.text.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class CodexEntries{
    public static Seq<CodexEntry> entries = new Seq<>();
    public static Chapter MAIN_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, CRYPT_CHAPTER;

    public static void initChapters(){
        MAIN_CHAPTER = new Chapter(
        "lexicon.valoria.main.name",
        new MainPage("lexicon.valoria.main"),
        new TextPage("lexicon.valoria.knowledge")
        .withCraftEntry(ItemsRegistry.lexicon.get().getDefaultInstance(), Items.PAPER.getDefaultInstance(), Items.BOOK.getDefaultInstance()));

        TREASURES_CHAPTER = new Chapter(
        "lexicon.valoria.jewelry",
        new TextPage("lexicon.valoria.treasures"),
        new TextPage("lexicon.valoria.treasure.gems"),
        new TextPage("lexicon.valoria.treasure.gems.about")
        .withCustomTitle("lexicon.valoria.treasure.gems.name"));

        MEDICINE_CHAPTER = new Chapter(
        "lexicon.valoria.medicine.name",
        new TextPage("lexicon.valoria.medicine"));

        CRYPT_CHAPTER = new Chapter(
        "lexicon.valoria.crypt.name",
        new TextPage("lexicon.valoria.crypt"));
    }

    public static void init(){
        CodexEntries.entries.clear();
        ChapterNode root = new ChapterNode(MAIN_CHAPTER, ItemsRegistry.lexicon.get(), Style.GOLD)
            .addChild(TREASURES_CHAPTER, ItemsRegistry.amethystGem)
            .addChild(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage)
            .addChild(new ChapterNode(CRYPT_CHAPTER, ItemsRegistry.cryptPage.get(), Style.CRYPT, RegisterUnlockables.crypt)
            .addHintsDescription(
                Component.translatable("lexicon.valoria.crypt.hint").withStyle(DotStyle.of().color(Col.gray).effect(DotText.pulse(1f)))
            ))

            .addChild(new ChapterNode(CRYPT_CHAPTER, Items.BUNDLE, Style.DIAMOND, RegisterUnlockables.test))
        ;

        int offset = 0;
        if(!onInit(root)){
            layoutTree(root, 0, -measureWidth(root) / 4 + 27 + ((root.children.size % 2 == 1) ? -6 : 0) + offset);
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