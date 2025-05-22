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
import net.minecraft.world.level.levelgen.structure.*;

public class TagsRegistry{

    public static TagKey<Item> item(final ResourceLocation name){
        return TagKey.create(Registries.ITEM, name);
    }

    public static TagKey<Block> block(final ResourceLocation name){
        return TagKey.create(Registries.BLOCK, name);
    }

    public static TagKey<EntityType<?>> entity(final ResourceLocation name){
        return TagKey.create(Registries.ENTITY_TYPE, name);
    }

    private static TagKey<DamageType> damage(final ResourceLocation name){
        return TagKey.create(Registries.DAMAGE_TYPE, name);
    }

    public static TagKey<PaintingVariant> painting(final ResourceLocation name){
        return TagKey.create(Registries.PAINTING_VARIANT, name);
    }

    public static TagKey<Structure> dungeon(final ResourceLocation name){
        return TagKey.create(Registries.STRUCTURE, name);
    }

    public static final TagKey<Item> EXCLUDED_FROM_TAB = item(new ResourceLocation(Valoria.ID, "excluded"));

    public static final TagKey<EntityType<?>> MINIONS = entity(new ResourceLocation(Valoria.ID, "minions"));

    public static final TagKey<Structure> ON_NECROMANCER_CRYPT_EXPLORER_MAPS = dungeon(new ResourceLocation(Valoria.ID, "on_necromancer_crypt_explorer_maps"));
    public static final TagKey<Structure> ON_CRYPT_EXPLORER_MAPS = dungeon(new ResourceLocation(Valoria.ID, "on_crypt_explorer_maps"));

    public static final TagKey<Block> MEAT = block(new ResourceLocation(Valoria.ID, "meat"));
    public static final TagKey<Block> VOID_STONE_ORE_REPLACEABLES = block(new ResourceLocation(Valoria.ID, "void_stone_ore_replaceables"));
    public static final TagKey<Block> PICRITE_ORE_REPLACEABLES = block(new ResourceLocation(Valoria.ID, "picrite_ore_replaceables"));
    public static final TagKey<Block> VOID_STONES = block(new ResourceLocation(Valoria.ID, "void_stones"));
    public static final TagKey<Block> VOID_BLOCKS = block(new ResourceLocation(Valoria.ID, "void_blocks"));
    public static final TagKey<Block> UNPACK_LOOT = block(new ResourceLocation(Valoria.ID, "unpack_loot"));
    public static final TagKey<Block> KEY_BLOCKS = block(new ResourceLocation(Valoria.ID, "key_blocks"));
    public static final TagKey<Block> ALLOWED_TO_BREAK = block(new ResourceLocation(Valoria.ID, "allowed_to_break"));

    public static final TagKey<Block> NEEDS_BRONZE_TOOL = block(new ResourceLocation(Valoria.ID, "needs_bronze_tool"));
    public static final TagKey<Block> NEEDS_PEARLIUM_TOOL = block(new ResourceLocation(Valoria.ID, "needs_pearlium_tool"));
    public static final TagKey<Block> NEEDS_COBALT_TOOL = block(new ResourceLocation(Valoria.ID, "needs_cobalt_tool"));
    public static final TagKey<Block> NEEDS_ETHEREAL_TOOL = block(new ResourceLocation(Valoria.ID, "needs_ethereal_tool"));
    public static final TagKey<Block> NEEDS_NATURE_TOOL = block(new ResourceLocation(Valoria.ID, "needs_pearlium_tool"));
    public static final TagKey<Block> NEEDS_DEPTH_TOOL = block(new ResourceLocation(Valoria.ID, "needs_depth_tool"));
    public static final TagKey<Block> NEEDS_INFERNAL_TOOL = block(new ResourceLocation(Valoria.ID, "needs_infernal_tool"));
    public static final TagKey<Block> NEEDS_JADE_TOOL = block(new ResourceLocation(Valoria.ID, "needs_jade_tool"));
    public static final TagKey<Block> NEEDS_VOID_TOOL = block(new ResourceLocation(Valoria.ID, "needs_void_tool"));

    public static final TagKey<Item> INFLICTS_FIRE = item(new ResourceLocation(Valoria.ID, "inflicts_fire"));
    public static final TagKey<Item> GRANTS_IMMUNITIES = item(new ResourceLocation(Valoria.ID, "accessories/grants_immunities"));
    public static final TagKey<Item> BLEEDING_IMMUNE = item(new ResourceLocation(Valoria.ID, "accessories/bleeding_immune"));
    public static final TagKey<Item> POISON_IMMUNE = item(new ResourceLocation(Valoria.ID, "accessories/poison_immune"));
    public static final TagKey<Item> FIRE_IMMUNE = item(new ResourceLocation(Valoria.ID, "accessories/fire_immune"));
    public static final TagKey<Item> ELDRITCH = item(new ResourceLocation(Valoria.ID, "logs/eldritch"));
    public static final TagKey<Item> SHADEWOOD = item(new ResourceLocation(Valoria.ID, "logs/shadewood"));
    public static final TagKey<Item> BOWS = item(new ResourceLocation(Valoria.ID, "bows"));
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