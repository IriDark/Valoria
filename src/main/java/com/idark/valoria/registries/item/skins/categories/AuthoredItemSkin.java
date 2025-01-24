package com.idark.valoria.registries.item.skins.categories;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import net.minecraft.network.chat.Component;

public class AuthoredItemSkin extends ItemSkin{
    public SkinBuilder skinBuilder;

    public AuthoredItemSkin(SkinBuilder skinBuilder){
        super(skinBuilder.name, skinBuilder.color);
        this.skinBuilder = skinBuilder;
    }

    public Component getContributorComponent(){
        return skinBuilder.component;
    }
}
