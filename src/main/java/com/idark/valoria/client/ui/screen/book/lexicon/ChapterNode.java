package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.util.struct.data.*;

public class ChapterNode {
    public Chapter chapter;
    public Item item;
    public Seq<ChapterNode> children = Seq.with();
    public Unlockable unlockable;
    public CodexEntry entry; //backcompatibility

    public ChapterNode(Chapter chapter, Item item) {
        this.chapter = chapter;
        this.item = item;
    }

    public ChapterNode(Chapter chapter, Item item, Unlockable unlockable) {
        this.chapter = chapter;
        this.item = item;
        this.unlockable = unlockable;
    }

    public ChapterNode addChild(ChapterNode node) {
        children.add(node);
        return this;
    }
    public ChapterNode addChild(Chapter chapter, Item item) {
        return addChild(new ChapterNode(chapter,item));
    }
    public ChapterNode addChild(Chapter chapter, Item item, Unlockable unlockable) {
        return addChild(new ChapterNode(chapter,item,unlockable));
    }
    public ChapterNode addChild(Chapter chapter, RegistryObject<Item> item) {
        return addChild(new ChapterNode(chapter,item.get()));
    }
    public ChapterNode addChild(Chapter chapter, RegistryObject<Item> item, Unlockable unlockable) {
        return addChild(new ChapterNode(chapter,item.get(),unlockable));
    }
}