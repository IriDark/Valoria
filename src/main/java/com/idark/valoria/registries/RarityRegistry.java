package com.idark.valoria.registries;

import net.minecraft.world.item.*;

import static com.idark.valoria.util.Styles.*;

public class RarityRegistry{
    public static final Rarity
    HALLOWEEN = Rarity.create("halloween", apply(halloween)),
    BLOODY = Rarity.create("bloody", apply(bloody)),
    MARSH = Rarity.create("marsh", apply(marsh)),
    SPIDER = Rarity.create("spider", apply(spider)),
    INFERNAL = Rarity.create("infernal", apply(infernal)),
    AQUARIUS = Rarity.create("aquarius", apply(aquarius)),
    ETHEREAL = Rarity.create("ethereal", apply(ethereal)),
    NATURE = Rarity.create("nature", apply(nature)),
    VOID = Rarity.create("void", apply(nihility)),
    ELEMENTAL = Rarity.create("elemental", apply(elemental)),
    PHANTASM = Rarity.create("phantasm", apply(phantasm));

}