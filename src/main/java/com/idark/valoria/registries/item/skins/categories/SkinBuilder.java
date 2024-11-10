package com.idark.valoria.registries.item.skins.categories;

import com.idark.valoria.*;
import mod.maxbogomol.fluffy_fur.common.itemskin.*;
import net.minecraft.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;

public class SkinBuilder{
    public String namespace = Valoria.ID;
    public String name;
    public List<ItemSkinEntry> skinEntries = new ArrayList<>();
    public Color color;
    public MutableComponent component;
    public SkinBuilder(String name){
        this.name = namespace + name;
    }

    public SkinBuilder setColor(Color color){
        this.color = color;
        return this;
    }

    public SkinBuilder addEntry(ItemSkinEntry entry){
        this.skinEntries.add(entry);
        return this;
    }

    public SkinBuilder setComponent(MutableComponent component){
        this.component = component;
        return this;
    }

    public SkinBuilder withStyle(UnaryOperator<Style> pModifyFunc) {
        component.withStyle(pModifyFunc);
        return this;
    }

    public SkinBuilder withStyle(Style pStyle) {
        component.withStyle(pStyle);
        return this;
    }

    public SkinBuilder withStyle(ChatFormatting... pFormats) {
        component.withStyle(pFormats);
        return this;
    }

    public SkinBuilder withStyle(ChatFormatting pFormat) {
        component.withStyle(pFormat);
        return this;
    }

    public SkinBuilder setContributor(String name){
        this.component = Component.literal(" ༶" + name + "༶");
        return this;
    }

    public ItemSkin build() {
        ItemSkin skin = new ItemSkin(name, color);
        for(ItemSkinEntry entry : skinEntries){
            skin.addSkinEntry(entry);
        }

        return skin;
    }
}
