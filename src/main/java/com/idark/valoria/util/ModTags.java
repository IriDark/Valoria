package com.idark.valoria.util;

import com.idark.valoria.Valoria;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;

public class ModTags {
    private final static String MODID = Valoria.MOD_ID;

    public static TagKey<Item> item(final ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }

    public static TagKey<PaintingVariant> painting(final ResourceLocation name) {
        return TagKey.create(Registries.PAINTING_VARIANT, name);
    }

    public static final TagKey<Item> CUP_DRINKS = item(new ResourceLocation(MODID, "wooden_cup_drinks"));
    public static final TagKey<Item> BOTTLE_DRINKS = item(new ResourceLocation(MODID, "bottle_drinks"));
    public static final TagKey<Item> TRINKETS = item(new ResourceLocation(MODID, "trinkets"));
    public static final TagKey<Item> GEMS = item(new ResourceLocation(MODID, "gems"));

    public static final TagKey<Item> POTIONS = item(new ResourceLocation(MODID, "potions"));
    public static final TagKey<Item> ALCOHOL = item(new ResourceLocation(MODID, "alcohol"));
    public static final TagKey<Item> RUM = item(new ResourceLocation(MODID, "rum"));
    public static final TagKey<Item> GEODES = item(new ResourceLocation(MODID, "geodes"));
    public static final TagKey<PaintingVariant> MODDED = painting(new ResourceLocation(MODID, "painting"));
}