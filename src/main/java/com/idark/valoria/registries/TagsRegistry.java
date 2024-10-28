package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;

public class TagsRegistry {

    public static TagKey<Item> item(final ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }

    public static TagKey<Block> block(final ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }

    public static TagKey<EntityType<?>> entity(final ResourceLocation name) {
        return TagKey.create(Registries.ENTITY_TYPE, name);
    }

    private static TagKey<DamageType> damage(final ResourceLocation name) {
        return TagKey.create(Registries.DAMAGE_TYPE, name);
    }

    public static TagKey<PaintingVariant> painting(final ResourceLocation name) {
        return TagKey.create(Registries.PAINTING_VARIANT, name);
    }

    public static final TagKey<Item> EXCLUDED_FROM_TAB = item(new ResourceLocation(Valoria.ID, "excluded"));

    public static final TagKey<EntityType<?>> MINIONS = entity(new ResourceLocation(Valoria.ID, "minions"));

    public static final TagKey<Block> MEAT = block(new ResourceLocation(Valoria.ID, "meat"));
    public static final TagKey<Block> UNPACK_LOOT = block(new ResourceLocation(Valoria.ID, "unpack_loot"));
    public static final TagKey<Block> KEY_BLOCKS = block(new ResourceLocation(Valoria.ID, "key_blocks"));
    public static final TagKey<Item> ELDRITCH = item(new ResourceLocation(Valoria.ID, "logs/eldritch"));
    public static final TagKey<Item> SHADEWOOD = item(new ResourceLocation(Valoria.ID, "logs/shadewood"));
    public static final TagKey<Item> BOWS = item(new ResourceLocation(Valoria.ID, "bows"));
    public static final TagKey<Item> CAN_DISABLE_SHIELD = item(new ResourceLocation(Valoria.ID, "can_disable_shield"));
    public static final TagKey<Item> CUP_DRINKS = item(new ResourceLocation(Valoria.ID, "wooden_cup_drinks"));
    public static final TagKey<Item> BOTTLE_DRINKS = item(new ResourceLocation(Valoria.ID, "bottle_drinks"));
    public static final TagKey<Item> TRINKETS = item(new ResourceLocation(Valoria.ID, "trinkets"));
    public static final TagKey<Item> GEMS = item(new ResourceLocation(Valoria.ID, "gems"));
    public static final TagKey<Item> POTIONS = item(new ResourceLocation(Valoria.ID, "potions"));
    public static final TagKey<Item> ALCOHOL = item(new ResourceLocation(Valoria.ID, "alcohol"));
    public static final TagKey<Item> RUM = item(new ResourceLocation(Valoria.ID, "rum"));
    public static final TagKey<Item> GEODES = item(new ResourceLocation(Valoria.ID, "geodes"));
    public static final TagKey<Item> SMOKE_PARTICLE = item(new ResourceLocation(Valoria.ID, "smoke_particle"));
    public static final TagKey<PaintingVariant> MODDED = painting(new ResourceLocation(Valoria.ID, "painting"));
}