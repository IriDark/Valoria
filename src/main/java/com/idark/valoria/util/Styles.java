package com.idark.valoria.util;

import net.minecraft.network.chat.*;
import net.minecraft.resources.*;

import java.awt.*;
import java.util.function.*;

public class Styles{
    public UnaryOperator<Style> create(Color color) {
        return style -> style
        .withColor(TextColor.fromRgb(color.getRGB()))
        .withBold(false)
        .withItalic(false)
        .withUnderlined(false)
        .withStrikethrough(false)
        .withObfuscated(false)
        .withFont(new ResourceLocation("minecraft", "default"));
    }

    public static UnaryOperator<Style> infernal = style -> style
    .withColor(TextColor.fromRgb(Pal.infernal.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> aquarius = style -> style
    .withColor(TextColor.fromRgb(Pal.oceanic.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));


    public static UnaryOperator<Style> nature = style -> style
    .withColor(TextColor.fromRgb(Pal.nature.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> nihility = style -> style
    .withColor(TextColor.fromRgb(Pal.softMagenta.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> halloween = style -> style
    .withColor(TextColor.fromRgb(Pal.halloween.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> phantasm = style -> style
    .withColor(TextColor.fromRgb(Pal.softBlue.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> arcaneGold = style -> style
    .withColor(TextColor.fromRgb(Pal.arcaneGold.getRGB()))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));
}