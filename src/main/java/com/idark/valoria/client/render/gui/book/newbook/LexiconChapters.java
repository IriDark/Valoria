package com.idark.valoria.client.render.gui.book.newbook;

import com.idark.valoria.Valoria;
import com.idark.valoria.client.render.gui.book.newbook.pages.TextPage;
import com.idark.valoria.client.render.gui.book.newbook.pages.TitledTextPage;
import com.idark.valoria.client.render.gui.book.newbook.pages.UnlockableEntry;
import com.idark.valoria.client.render.gui.book.newbook.pages.UnlockablePage;
import com.idark.valoria.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class LexiconChapters {
    public static List<Bookmark> categories = new ArrayList<>();
    public static Bookmark LEXICON, K;
    public static Chapter MAIN, GEMS;

    public static void init() {
        MAIN = new Chapter(
                "test1",
                new TextPage("aaaaaaaaaaaaaaa")
        );

        GEMS = new Chapter(
                "test2",
                new TitledTextPage("gui.valoria.treasures", false),
                new TitledTextPage("gui.valoria.treasure.gems", true),
                new TextPage("b"),
                new TextPage("c"),
                new UnlockablePage(new UnlockableEntry(GEMS, new ItemStack(ModItems.GEM_BAG.get()))
        )

        );

        LEXICON = new Bookmark(Items.BOOK, "item.valoria.lexicon", 267, 10, MAIN);
        K = new Bookmark(Items.VINE, "item.valoria.lexicon", 267, 42, GEMS);
        categories.add(LEXICON);
        categories.add(K);
    }
}