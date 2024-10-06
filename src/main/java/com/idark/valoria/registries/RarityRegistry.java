package com.idark.valoria.registries;

import net.minecraft.world.item.*;

import static com.idark.valoria.util.Styles.*;

public class RarityRegistry {
    public static final Rarity HALLOWEEN = Rarity.create("halloween", halloween_modifier);
    public static final Rarity INFERNAL = Rarity.create("infernal", infernal_modifier);
    public static final Rarity AQUARIUS = Rarity.create("aquarius", aquarius_modifier);
    public static final Rarity NATURE = Rarity.create("nature", nature_modifier);
    public static final Rarity VOID = Rarity.create("void", void_modifier);
    public static final Rarity PHANTASM = Rarity.create("phantasm", phantasm_modifier);
}