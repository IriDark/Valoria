package com.idark.valoria.client.ui.screen.book;

import com.idark.valoria.client.ui.screen.book.pages.*;
import com.idark.valoria.client.ui.screen.book.unlockable.*;
import com.idark.valoria.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;

import java.util.*;

public class LexiconChapters {
    public static List<Bookmark> categories = new ArrayList<>();
    public static Bookmark LEXICON, TREASURES, MEDICINE, CRYPT;
    public static Chapter MAIN_CHAPTER, TREASURES_CHAPTER, MEDICINE_CHAPTER, CRYPT_CHAPTER;

    public static void init() {
        MAIN_CHAPTER = new Chapter(
                "lexicon.valoria.main.name",
                new MainPage("lexicon.valoria.main"),
                new TextPage("lexicon.valoria.knowledge")
                        .withCraftEntry(ItemsRegistry.lexicon.get().getDefaultInstance(), Items.PAPER.getDefaultInstance(), Items.BOOK.getDefaultInstance())
        );

        TREASURES_CHAPTER = new Chapter(
                "lexicon.valoria.jewelry",
                new TextPage("lexicon.valoria.treasures"),
                new TextPage("lexicon.valoria.treasure.gems"),
                new TextPage("lexicon.valoria.treasure.gems.about")
                        .withCustomTitle("lexicon.valoria.treasure.gems.name")
        );

        MEDICINE_CHAPTER = new Chapter(
                "lexicon.valoria.medicine",
                new TextPage("lexicon.valoria.medicine")
        );

        CRYPT_CHAPTER = new Chapter(
                "lexicon.valoria.crypt.name",
                new TextPage("lexicon.valoria.crypt")
        );

        LEXICON = new Bookmark(0, ItemsRegistry.lexicon.get(), Component.translatable("lexicon.valoria.main.name"), MAIN_CHAPTER);
        TREASURES = new Bookmark(1, ItemsRegistry.amethystGem.get(), Component.translatable("lexicon.valoria.jewelry"), TREASURES_CHAPTER);
        MEDICINE = new Bookmark(2, ItemsRegistry.aloeBandage.get(), Component.translatable("lexicon.valoria.medicine.name"), MEDICINE_CHAPTER);
        CRYPT = new Bookmark(3, ItemsRegistry.cryptPage.get(), Component.translatable("lexicon.valoria.crypt.name"), CRYPT_CHAPTER, RegisterUnlockables.CRYPT);
    }
}