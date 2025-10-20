package com.idark.valoria.util;

import net.minecraft.network.chat.*;
import pro.komaru.tridot.api.render.text.DotStyleEffects.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.util.*;

import java.util.function.*;

public class Styles{
    public static UnaryOperator<Style> apply(Style st){
        return style -> st;
    }

    public static DotStyle create(Col color){
        return DotStyle.of().color(color);
    }

    public static DotStyle white = create(Col.white);
    public static DotStyle marsh = create(Pal.marsh);
    public static DotStyle bloody = create(Pal.lightCarminePink).effects(ShakeFX.of((0.1f)));
    public static DotStyle infernal = create(Pal.infernal);
    public static DotStyle aquarius = create(Pal.oceanic);
    public static DotStyle soul = create(Pal.softBlue);
    public static DotStyle nature = create(Pal.nature);
    public static DotStyle ethereal = create(Pal.ethereal).effects(GlintFX.of(1f));
    public static DotStyle nihility = create(Pal.softMagenta).effects(WaveFX.of(0.25f, 0.1f));
    public static DotStyle halloween = create(Pal.mandarin).effects(WaveFX.of(0.25f));
    public static DotStyle phantasm = create(Pal.softBlue).effects(PulseAlphaFX.of(0.75f));
    public static DotStyle arcaneGold = create(Pal.arcaneGold);
    public static DotStyle spider = create(Pal.crystalBlue).effects(WaveFX.of(0.15f, 0.1f), AdvanceFX.of(0.15f));
    public static DotStyle elemental = DotStyle.of().effects(RainbowFX.of(0.5f));
}