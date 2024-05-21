package com.idark.valoria.registries;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

import java.util.function.UnaryOperator;

public class RarityRegistry{

    public static UnaryOperator<Style> infernal_modifier = style -> style
    .withColor(TextColor.fromRgb(0xd8401d))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> aquarius_modifier = style -> style
    .withColor(TextColor.fromRgb(0xb6ffe7))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));


    public static UnaryOperator<Style> nature_modifier = style -> style
    .withColor(TextColor.fromRgb(0x88ed68))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> void_modifier = style -> style
    .withColor(TextColor.fromRgb(0xf06de7))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static UnaryOperator<Style> phantasm_modifier = style -> style
    .withColor(TextColor.fromRgb(0x8269db))
    .withBold(false)
    .withItalic(false)
    .withUnderlined(false)
    .withStrikethrough(false)
    .withObfuscated(false)
    .withFont(new ResourceLocation("minecraft", "default"));

    public static final Rarity INFERNAL = Rarity.create("infernal", infernal_modifier);
    public static final Rarity AQUARIUS = Rarity.create("aquarius", aquarius_modifier);
    public static final Rarity NATURE = Rarity.create("nature", nature_modifier);
    public static final Rarity VOID = Rarity.create("void", void_modifier);
    public static final Rarity PHANTASM = Rarity.create("phantasm", phantasm_modifier);
}