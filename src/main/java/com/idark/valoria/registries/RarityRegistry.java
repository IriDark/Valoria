package com.idark.valoria.registries;

import net.minecraft.world.item.*;
import net.minecraftforge.fml.*;

import static com.idark.valoria.util.Styles.*;

public class RarityRegistry{
    public static final Rarity
    HALLOWEEN = Rarity.create("halloween", apply(halloween)),
    LUNAR = Rarity.create("lunar", apply(lunar)),
    BLOODY = Rarity.create("bloody", apply(bloody)),
    MARSH = Rarity.create("marsh", apply(marsh)),
    SPIDER = Rarity.create("spider", apply(spider)),
    PYRATITE = Rarity.create("pyratite", apply(arcaneGold)),
    INFERNAL = Rarity.create("infernal", apply(infernal)),
    AQUARIUS = Rarity.create("aquarius", apply(aquarius)),
    SOUL = Rarity.create("soul", apply(soul)),
    ETHEREAL = Rarity.create("ethereal", apply(ethereal)),
    NATURE = Rarity.create("nature", apply(nature)),
    VOID = Rarity.create("void", apply(nihility)),
    ELEMENTAL = Rarity.create("elemental", ModList.get().isLoaded("itemborders") ? apply(white) : apply(elemental)),
    PHANTASM = Rarity.create("phantasm", apply(phantasm));

}