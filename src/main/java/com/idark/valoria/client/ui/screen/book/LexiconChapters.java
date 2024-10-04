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
                "gui.valoria.main.name",
                new MainPage("gui.valoria.main"),
                new TextPage("gui.valoria.knowledge")
                        .withCraftEntry(ItemsRegistry.lexicon.get().getDefaultInstance(), Items.PAPER.getDefaultInstance(), Items.BOOK.getDefaultInstance())
        );

        TREASURES_CHAPTER = new Chapter(
                "gui.valoria.jewelry",
                new TextPage("gui.valoria.treasures"),
                new TextPage("gui.valoria.treasure.gems"),
                new TextPage("gui.valoria.treasure.gems.about")
                        .withCustomTitle("gui.valoria.treasure.gems.name")
        );

        MEDICINE_CHAPTER = new Chapter(
                "gui.valoria.medicine",
                new TextPage("gui.valoria.medicine")
        );

        CRYPT_CHAPTER = new Chapter(
                "gui.valoria.crypt.name",
                new TextPage("gui.valoria.crypt")
        );

        LEXICON = new Bookmark(0, ItemsRegistry.lexicon.get(), Component.translatable("gui.valoria.main.name"), MAIN_CHAPTER);
        TREASURES = new Bookmark(1, ItemsRegistry.amethystGem.get(), Component.translatable("gui.valoria.jewelry"), TREASURES_CHAPTER);
        MEDICINE = new Bookmark(2, ItemsRegistry.aloeBandage.get(), Component.translatable("gui.valoria.medicine.name"), MEDICINE_CHAPTER);
        CRYPT = new Bookmark(3, ItemsRegistry.cryptPage.get(), Component.translatable("gui.valoria.crypt.name"), CRYPT_CHAPTER, RegisterUnlockables.CRYPT);
    }
}