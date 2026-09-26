package com.idark.valoria.registries;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.asm.enumextension.*;

import java.util.function.*;

import static com.idark.valoria.util.Styles.*;

public class RarityRegistry{
    public static final class Proxies{
        public static final EnumProxy<Rarity> HALLOWEEN = proxy("halloween", apply(halloween));
        public static final EnumProxy<Rarity> LUNAR = proxy("lunar", apply(lunar));
        public static final EnumProxy<Rarity> BLOODY = proxy("bloody", apply(bloody));
        public static final EnumProxy<Rarity> MARSH = proxy("marsh", apply(marsh));
        public static final EnumProxy<Rarity> SPIDER = proxy("spider", apply(spider));
        public static final EnumProxy<Rarity> PYRATITE = proxy("pyratite", apply(arcaneGold));
        public static final EnumProxy<Rarity> INFERNAL = proxy("infernal", apply(infernal));
        public static final EnumProxy<Rarity> AQUARIUS = proxy("aquarius", apply(aquarius));
        public static final EnumProxy<Rarity> SOUL = proxy("soul", apply(soul));
        public static final EnumProxy<Rarity> ETHEREAL = proxy("ethereal", apply(ethereal));
        public static final EnumProxy<Rarity> NATURE = proxy("nature", apply(nature));
        public static final EnumProxy<Rarity> VOID = proxy("void", apply(nihility));
        public static final EnumProxy<Rarity> ELEMENTAL = proxy("elemental", style -> ModList.get().isLoaded("itemborders") ? white : elemental);
        public static final EnumProxy<Rarity> PHANTASM = proxy("phantasm", apply(phantasm));

        private static EnumProxy<Rarity> proxy(String name, UnaryOperator<Style> styleModifier){
            return new EnumProxy<>(Rarity.class, -1, "valoria:" + name, styleModifier);
        }
    }

    public static final Rarity
    HALLOWEEN = Proxies.HALLOWEEN.getValue(),
    LUNAR = Proxies.LUNAR.getValue(),
    BLOODY = Proxies.BLOODY.getValue(),
    MARSH = Proxies.MARSH.getValue(),
    SPIDER = Proxies.SPIDER.getValue(),
    PYRATITE = Proxies.PYRATITE.getValue(),
    INFERNAL = Proxies.INFERNAL.getValue(),
    AQUARIUS = Proxies.AQUARIUS.getValue(),
    SOUL = Proxies.SOUL.getValue(),
    ETHEREAL = Proxies.ETHEREAL.getValue(),
    NATURE = Proxies.NATURE.getValue(),
    VOID = Proxies.VOID.getValue(),
    ELEMENTAL = Proxies.ELEMENTAL.getValue(),
    PHANTASM = Proxies.PHANTASM.getValue();

}
