package com.idark.valoria.client.ui.screen.book.lexicon;

import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.world.item.*;

import java.util.*;

public class ChapterNode {
    public Chapter chapter;
    public Item item;
    public List<ChapterNode> children = new ArrayList<>();
    public Unlockable unlockable;

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
}