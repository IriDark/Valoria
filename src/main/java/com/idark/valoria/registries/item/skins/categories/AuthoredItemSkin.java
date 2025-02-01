package com.idark.valoria.registries.item.skins.categories;

import net.minecraft.network.chat.*;
import pro.komaru.tridot.registry.item.builders.*;
import pro.komaru.tridot.registry.item.skins.*;

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
