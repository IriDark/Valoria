package com.idark.valoria.registries;

import net.minecraft.world.item.*;

import static com.idark.valoria.util.Styles.*;

public class RarityRegistry{
    public static final Rarity HALLOWEEN = Rarity.create("halloween", apply(halloween));
    public static final Rarity SPIDER = Rarity.create("spider", apply(spider));
    public static final Rarity INFERNAL = Rarity.create("infernal", apply(infernal));
    public static final Rarity AQUARIUS = Rarity.create("aquarius", apply(aquarius));
    public static final Rarity ETHEREAL = Rarity.create("ethereal", apply(ethereal));
    public static final Rarity NATURE = Rarity.create("nature", apply(nature));
    public static final Rarity VOID = Rarity.create("void", apply(nihility));
    public static final Rarity PHANTASM = Rarity.create("phantasm", apply(phantasm));
    public static final Rarity BLOODY = Rarity.create("bloody", apply(bloody));
    public static final Rarity MARSH = Rarity.create("marsh", apply(marsh));

}