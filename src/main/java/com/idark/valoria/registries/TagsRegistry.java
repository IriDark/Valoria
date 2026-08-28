package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.levelgen.structure.*;

public class TagsRegistry{

    public static TagKey<Item> item(final ResourceLocation name){
        return TagKey.create(Registries.ITEM, name);
    }

    public static TagKey<Block> block(final ResourceLocation name){
        return TagKey.create(Registries.BLOCK, name);
    }

    public static TagKey<MobEffect> effect(final ResourceLocation name){
        return TagKey.create(Registries.MOB_EFFECT, name);
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

    public static TagKey<Biome> biome(final ResourceLocation name){
        return TagKey.create(Registries.BIOME, name);
    }

    public static final TagKey<Item> EXCLUDED_FROM_TAB = item(Valoria.loc("excluded"));

    public static final TagKey<EntityType<?>> MINIONS = entity(Valoria.loc("minions"));
    public static final TagKey<EntityType<?>> DAMAGE_INDICATOR_IGNORED = entity(Valoria.loc("damage_indicator_ignored"));
    public static final TagKey<EntityType<?>> HARMONY_CREATURES = entity(Valoria.loc("harmony_creatures"));
    public static final TagKey<EntityType<?>> STONE_GOLEMS = entity(Valoria.loc("stone_golems"));
    public static final TagKey<EntityType<?>> POT_SPAWNS = entity(Valoria.loc("pot_spawns"));

    public static final TagKey<Structure> NECROMANCER_CRYPT_LOCATOR = dungeon(Valoria.loc("necromancer_crypt_locator"));
    public static final TagKey<Structure> FORTRESS_LOCATOR = dungeon(Valoria.loc("fortress_locator"));
    public static final TagKey<Structure> ON_NECROMANCER_CRYPT_EXPLORER_MAPS = dungeon(Valoria.loc("on_necromancer_crypt_explorer_maps"));
    public static final TagKey<Structure> ON_CRYPT_EXPLORER_MAPS = dungeon(Valoria.loc("on_crypt_explorer_maps"));
    public static final TagKey<Structure> CRYPTS = dungeon(Valoria.loc("crypts"));
    public static final TagKey<Structure> MONSTROSITIES = dungeon(Valoria.loc("monstrosities"));

    public static final TagKey<Biome> IS_VALORIA = biome(Valoria.loc("is_valoria"));
    public static final TagKey<Biome> RIVER_GOLEM_SPAWNABLE = biome(Valoria.loc("river_golem_spawnable"));

    public static final TagKey<Biome> POT_SPAWN_BIOMES = biome(Valoria.loc("pot_spawn_biomes"));
    public static final TagKey<Biome> DESERT_POT_SPAWN_BIOMES = biome(Valoria.loc("desert_pot_spawn_biomes"));
    public static final TagKey<Biome> MOSSY_POT_SPAWN_BIOMES = biome(Valoria.loc("mossy_pot_spawn_biomes"));

    public static final TagKey<Block> MINEABLE_WITH_MULTITOOL = block(Valoria.loc("mineable/multitool"));
    public static final TagKey<Block> MEAT = block(Valoria.loc("meat"));
    public static final TagKey<Block> PICRITE_ORE_REPLACEABLES = block(Valoria.loc("picrite_ore_replaceables"));
    public static final TagKey<Block> VOID_STONES = block(Valoria.loc("void_stones"));
    public static final TagKey<Block> VOID_BLOCKS = block(Valoria.loc("void_blocks"));
    public static final TagKey<Block> UNPACK_LOOT = block(Valoria.loc("unpack_loot"));
    public static final TagKey<Block> KEY_BLOCKS = block(Valoria.loc("key_blocks"));
    public static final TagKey<Block> ALLOWED_TO_BREAK = block(Valoria.loc("allowed_to_break"));
    public static final TagKey<Block> EPHEMARITE_BLOCK = block(Valoria.loc("ephemarite"));

    public static final TagKey<Block> NEEDS_HALLOWEEN_TOOL = block(Valoria.loc("needs_halloween_tool"));
    public static final TagKey<Block> NEEDS_LUNAR_TOOL = block(Valoria.loc("needs_lunar_tool"));
    public static final TagKey<Block> NEEDS_HOLIDAY_TOOL = block(Valoria.loc("needs_holiday_tool"));
    public static final TagKey<Block> NEEDS_NONE_TOOL = block(Valoria.loc("needs_none_tool"));
    public static final TagKey<Block> NEEDS_BLAZEREAP_TOOL = block(Valoria.loc("needs_blazereap_tool"));

    public static final TagKey<Block> NEEDS_PHANTOM_TOOL = block(Valoria.loc("needs_phantom_tool"));
    public static final TagKey<Block> NEEDS_MEAT_TOOL = block(Valoria.loc("needs_meat_tool"));
    public static final TagKey<Block> NEEDS_PYRATITE_TOOL = block(Valoria.loc("needs_pyratite_tool"));
    public static final TagKey<Block> NEEDS_SPIDER_TOOL = block(Valoria.loc("needs_spider_tool"));
    public static final TagKey<Block> NEEDS_SAMURAI_TOOL = block(Valoria.loc("needs_samurai_tool"));
    public static final TagKey<Block> NEEDS_BRONZE_TOOL = block(Valoria.loc("needs_bronze_tool"));
    public static final TagKey<Block> NEEDS_PEARLIUM_TOOL = block(Valoria.loc("needs_pearlium_tool"));
    public static final TagKey<Block> NEEDS_COBALT_TOOL = block(Valoria.loc("needs_cobalt_tool"));
    public static final TagKey<Block> NEEDS_ETHEREAL_TOOL = block(Valoria.loc("needs_ethereal_tool"));
    public static final TagKey<Block> NEEDS_NATURE_TOOL = block(Valoria.loc("needs_pearlium_tool"));
    public static final TagKey<Block> NEEDS_DEPTH_TOOL = block(Valoria.loc("needs_depth_tool"));
    public static final TagKey<Block> NEEDS_INFERNAL_TOOL = block(Valoria.loc("needs_infernal_tool"));
    public static final TagKey<Block> NEEDS_JADE_TOOL = block(Valoria.loc("needs_jade_tool"));
    public static final TagKey<Block> NEEDS_VOID_TOOL = block(Valoria.loc("needs_void_tool"));

    public static final TagKey<Item> DRAUGR_SPAWNABLE_WITH = item(Valoria.loc("draugr_spawnable_with"));
    public static final TagKey<Item> GOBLIN_SPAWNABLE_WITH = item(Valoria.loc("goblin_spawnable_with"));
    public static final TagKey<Item> FROM_SARCOPHAGUS_SPAWNABLE_WITH = item(Valoria.loc("from_sarcophagus_spawnable_with"));
    public static final TagKey<Item> FROM_SARCOPHAGUS_HALLOWEEN_SPAWNABLE_WITH = item(Valoria.loc("from_sarcophagus_halloween_spawnable_with"));

    public static final TagKey<Item> EPHEMARITE_ITEMS = item(Valoria.loc("ephemarite"));
    public static final TagKey<Item> SPEARS = item(Valoria.loc("spears"));
    public static final TagKey<Item> SCYTHES = item(Valoria.loc("scythes"));
    public static final TagKey<Item> RAPIERS = item(Valoria.loc("rapiers"));
    public static final TagKey<Item> KATANAS = item(Valoria.loc("katanas"));
    public static final TagKey<Item> THROWABLES = item(Valoria.loc("throwables"));
    public static final TagKey<Item> MULTI_TOOLS = item(Valoria.loc("multi_tools"));
    public static final TagKey<Item> PAGES = item(Valoria.loc("pages"));
    public static final TagKey<Item> INFLICTS_FIRE = item(Valoria.loc("inflicts_fire"));
    public static final TagKey<Item> GRANTS_IMMUNITIES = item(Valoria.loc("accessories/grants_immunities"));
    public static final TagKey<Item> BLEEDING_IMMUNE = item(Valoria.loc("accessories/bleeding_immune"));
    public static final TagKey<Item> POISON_IMMUNE = item(Valoria.loc("accessories/poison_immune"));
    public static final TagKey<Item> FIRE_IMMUNE = item(Valoria.loc("accessories/fire_immune"));
    public static final TagKey<Item> FIRE_IMMUNE_TIMED = item(Valoria.loc("accessories/fire_immune_timed"));
    public static final TagKey<Item> ELDRITCH = item(Valoria.loc("logs/eldritch"));
    public static final TagKey<Item> SHADEWOOD = item(Valoria.loc("logs/shade"));
    public static final TagKey<Item> DREADWOOD = item(Valoria.loc("logs/dread"));
    public static final TagKey<Item> CUP_DRINKS = item(Valoria.loc("wooden_cup_drinks"));
    public static final TagKey<Item> BOTTLE_DRINKS = item(Valoria.loc("bottle_drinks"));
    public static final TagKey<Item> TRINKETS = item(Valoria.loc("trinkets"));
    public static final TagKey<Item> GEMS = item(Valoria.loc("gems"));
    public static final TagKey<Item> POTIONS = item(Valoria.loc("potions"));
    public static final TagKey<Item> ALCOHOL = item(Valoria.loc("alcohol"));
    public static final TagKey<Item> RUM = item(Valoria.loc("rum"));
    public static final TagKey<Item> CRUSHABLE = item(Valoria.loc("crushable"));
    public static final TagKey<Item> GEODES = item(Valoria.loc("geodes"));
    public static final TagKey<Item> STONE_CRUSHER_TOOL = item(Valoria.loc("stone_crusher_tool"));
    public static final TagKey<Item> SMOKE_PARTICLE = item(Valoria.loc("smoke_particle"));
    public static final TagKey<Item> ROT_IMMUNE = item(Valoria.loc("rot_immune"));
    public static final TagKey<PaintingVariant> MODDED = painting(Valoria.loc("painting"));
    public static final TagKey<MobEffect> CURSES = effect(Valoria.loc("curses"));
}
