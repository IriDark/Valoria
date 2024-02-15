package com.idark.valoria.client.render.gui.book;

import com.idark.valoria.client.render.gui.book.pages.TextPage;
import com.idark.valoria.client.render.gui.book.pages.TitledTextPage;
import com.idark.valoria.client.render.gui.book.unlockable.RegisterUnlockables;
import com.idark.valoria.client.render.gui.book.unlockable.UnlockableBookmark;
import com.idark.valoria.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class LexiconChapters {
    public static List<Bookmark> categories = new ArrayList<>();
    public static Bookmark LEXICON, TREASURES, MEDICINE;
    public static UnlockableBookmark CRYPT;
    public static Chapter MAIN_PAGE, TREASURES_PAGE, MEDICINE_PAGE, CRYPT_PAGE;

    public static void init() {
        MAIN_PAGE = new Chapter(
                "gui.valoria.main.name",
                new TitledTextPage("gui.valoria.main", false),
                new TitledTextPage("gui.valoria.knowledge", false)
        );

        TREASURES_PAGE = new Chapter(
                "gui.valoria.jewelry",
                new TitledTextPage("gui.valoria.treasures", false),
                new TitledTextPage("gui.valoria.treasure.gems", true),
                new TextPage("gui.valoria.treasure.gems.about")
                );

        MEDICINE_PAGE = new Chapter(
                "gui.valoria.medicine",
                new TitledTextPage("gui.valoria.medicine", false)
        );

        CRYPT_PAGE = new Chapter(
                "gui.valoria.crypt.name",
                new TitledTextPage("gui.valoria.crypt", false)
        );

        LEXICON = new Bookmark(ModItems.LEXICON.get(), "gui.valoria.main.name", 267, 10, MAIN_PAGE);
        TREASURES = new Bookmark(ModItems.AMETHYST_GEM.get(), "gui.valoria.jewelry", 267, 38, TREASURES_PAGE);
        MEDICINE = new Bookmark(ModItems.ALOE_BANDAGE.get(), "gui.valoria.medicine.name", 267, 66, MEDICINE_PAGE);
        CRYPT = new UnlockableBookmark(ModItems.CRYPT.get(), "gui.valoria.crypt.name", 267, 94, CRYPT_PAGE, RegisterUnlockables.CRYPT);

        categories.add(LEXICON);
        categories.add(TREASURES);
        categories.add(MEDICINE);
    }
}