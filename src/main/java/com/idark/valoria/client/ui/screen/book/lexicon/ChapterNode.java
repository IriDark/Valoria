package com.idark.valoria.client.ui.screen.book.lexicon;

import com.google.common.collect.*;
import com.idark.valoria.api.unlockable.*;
import com.idark.valoria.client.ui.screen.book.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.*;
import pro.komaru.tridot.util.struct.data.*;

import java.util.*;

public class ChapterNode {
    public Chapter chapter;
    public Item item;
    public Seq<ChapterNode> children = Seq.with();
    public Unlockable unlockable;
    public Style style;
    public CodexEntry entry; //backcompatibility
    public List<Component> description = Lists.newArrayList();
    public List<Component> hints = Lists.newArrayList();

    public ChapterNode(Chapter chapter, Item item) {
        this(chapter, item, Style.STANDARD, null);
    }

    public ChapterNode(Chapter chapter, Item item, Style style) {
        this(chapter, item, style, null);
    }

    public ChapterNode(Chapter chapter, Item item, Style style, Unlockable unlockable) {
        this.chapter = chapter;
        this.item = item;
        this.unlockable = unlockable;
        this.style = style;
    }

    public ChapterNode(Chapter chapter, Item item, Unlockable unlockable) {
        this.chapter = chapter;
        this.item = item;
        this.unlockable = unlockable;
        this.style = Style.STANDARD;
    }

    public ChapterNode addHintsDescription(MutableComponent... components) {
        Collections.addAll(this.hints, components);
        return this;
    }

    public ChapterNode addDescription(MutableComponent... components) {
        Collections.addAll(this.description, components);
        return this;
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