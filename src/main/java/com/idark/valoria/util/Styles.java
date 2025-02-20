package com.idark.valoria.util;

import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.awt.*;
import java.util.function.*;

public class Styles{
    public static UnaryOperator<Style> create(Color color){
        return style -> style
                .withColor(TextColor.fromRgb(color.getRGB()))
                .withBold(false)
                .withItalic(false)
                .withUnderlined(false)
                .withStrikethrough(false)
                .withObfuscated(false)
                .withFont(new ResourceLocation("minecraft", "default"));
    }

    public static UnaryOperator<Style> bloody = create(Pal.lightCarminePink);
    public static UnaryOperator<Style> infernal = create(Pal.infernal);
    public static UnaryOperator<Style> aquarius = create(Pal.oceanic);
    public static UnaryOperator<Style> nature = create(Pal.nature);
    public static UnaryOperator<Style> ethereal = create(Pal.ethereal);
    public static UnaryOperator<Style> nihility = create(Pal.softMagenta);
    public static UnaryOperator<Style> halloween = create(Pal.mandarin);
    public static UnaryOperator<Style> phantasm = create(Pal.softBlue);
    public static UnaryOperator<Style> arcaneGold = create(Pal.arcaneGold);
    public static UnaryOperator<Style> spider = create(Pal.crystalBlue);
}