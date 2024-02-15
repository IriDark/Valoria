package com.idark.valoria.client.render.gui.book.newbook;

import com.idark.valoria.client.render.gui.book.newbook.pages.TextPage;
import com.idark.valoria.client.render.gui.book.newbook.pages.TitledTextPage;
import com.idark.valoria.client.render.gui.book.newbook.unlockable.UnlockableBookmark;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class LexiconChapters {
    public static List<Bookmark> categories = new ArrayList<>();
    public static Bookmark LEXICON, K;
    public static UnlockableBookmark TEST_U;
    public static Chapter MAIN, GEMS, TEST;

    public static void init() {
        MAIN = new Chapter(
                "test1",
                new TitledTextPage("gui.valoria.treasures", false)
                //new UnlockablePage(new UnlockableEntry(TEST, new ItemStack(ModItems.GEM_BAG.get())))
        );

        TEST = new Chapter(
                "gui.valoria.unknown",
                new TitledTextPage("gui.valoria.treasures", false)
        );

        GEMS = new Chapter(
                "test2",
                new TitledTextPage("gui.valoria.treasures", false),
                new TitledTextPage("gui.valoria.treasure.gems", true),
                new TextPage("b"),
                new TextPage("c")
        );

        LEXICON = new Bookmark(Items.BOOK, "item.valoria.lexicon", 267, 10, MAIN);
        K = new Bookmark(Items.VINE, "item.valoria.lexicon", 267, 42, GEMS);
        TEST_U = new UnlockableBookmark(Items.GLASS_PANE, "item.valoria.lexicon", 267, 42 + 32, TEST);
        categories.add(LEXICON);
        categories.add(K);
    }
}