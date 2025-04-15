package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.client.ui.screen.book.*;
import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
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
        ChapterNode root = new ChapterNode(MAIN_CHAPTER, ItemsRegistry.lexicon.get())
            .addChild(new ChapterNode(TREASURES_CHAPTER, ItemsRegistry.amethystGem.get()))
            .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get()))
            .addChild(new ChapterNode(CRYPT_CHAPTER, ItemsRegistry.cryptPage.get(), RegisterUnlockables.CRYPT)
                .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get()))
                .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get())
                    .addChild(new ChapterNode(MEDICINE_CHAPTER, ItemsRegistry.aloeBandage.get()))
                )
            );

        layoutTree(root, 0, -measureWidth(root) / 2);
    }

    // pivo

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
            entries.add(new CodexEntry(x, y, node.item, Component.translatable(node.chapter.titleKey), node.chapter, node, node.unlockable));
            return spacingX;
        }

        int totalWidth = 0;
        List<Integer> childCenters = new ArrayList<>();

        for(ChapterNode child : node.children){
            int childWidth = measureWidth(child);
            int childX = x + totalWidth;

            layoutTree(child, depth + 1, childX);
            childCenters.add(childX);
            totalWidth += childWidth;
        }

        int centerX = (Collections.min(childCenters) + Collections.max(childCenters)) / 2;
        centerX = Mth.clamp(centerX, -512, 512);

        entries.add(new CodexEntry(centerX, y, node.item, Component.translatable(node.chapter.titleKey), node.chapter, node, node.unlockable));
        return totalWidth;
    }
}