package com.idark.valoria.client.gui.screen.book;

import com.idark.valoria.client.gui.screen.book.pages.TitledCraftEntry;
import com.idark.valoria.client.gui.screen.book.pages.TitledTextPage;
import com.idark.valoria.client.gui.screen.book.unlockable.RegisterUnlockables;
import com.idark.valoria.client.gui.screen.book.unlockable.UnlockableBookmark;
import com.idark.valoria.registries.world.item.ModItems;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class LexiconChapters {
    public static List<Bookmark> categories = new ArrayList<>();
    public static Bookmark LEXICON, TREASURES, MEDICINE;
    public static UnlockableBookmark CRYPT;
    public static Chapter THANKS_PAGE, MAIN_PAGE, TREASURES_PAGE, MEDICINE_PAGE, CRYPT_PAGE;

    public static void init() {
        THANKS_PAGE = new Chapter(
                "gui.valoria.thanks.name",
                new TitledTextPage("gui.valoria.thanks"),
                new TitledTextPage("gui.valoria.thanks.about", "gui.valoria.thanks.name")
        );

        MAIN_PAGE = new Chapter(
                "gui.valoria.main.name",
                new TitledTextPage("gui.valoria.main"),
                new TitledCraftEntry("gui.valoria.knowledge", ModItems.LEXICON.get().getDefaultInstance(), Items.PAPER.getDefaultInstance(), Items.BOOK.getDefaultInstance())
        );

        TREASURES_PAGE = new Chapter(
                "gui.valoria.jewelry",
                new TitledTextPage("gui.valoria.treasures"),
                new TitledTextPage("gui.valoria.treasure.gems"),
                new TitledTextPage("gui.valoria.treasure.gems.about", "gui.valoria.treasure.gems.name")
        );

        MEDICINE_PAGE = new Chapter(
                "gui.valoria.medicine",
                new TitledTextPage("gui.valoria.medicine")
        );

        CRYPT_PAGE = new Chapter(
                "gui.valoria.crypt.name",
                new TitledTextPage("gui.valoria.crypt")
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