package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.core.registries.*;
import net.minecraft.sounds.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.util.*;
import net.neoforged.neoforge.registries.*;

/**
 * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
 */
public class SoundsRegistry{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Valoria.ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> ENDURING = registerSound("music.valoria.enduring");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADED_LANDS = registerSound("music.valoria.shaded_lands");
    public static final DeferredHolder<SoundEvent, SoundEvent> RISING = registerSound("music.valoria.rising");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARRIVING = registerSound("music.valoria.arriving");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_POLE = registerSound("music.valoria.blood_pole");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARRION = registerSound("music.valoria.carrion");

    public static final DeferredHolder<SoundEvent, SoundEvent> VALORIA_ADDITIONS = registerSound("ambient.valoria.additions");
    public static final DeferredHolder<SoundEvent, SoundEvent> VALORIA_MOOD = registerSound("ambient.valoria.mood");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRIMSON_MOOD = registerSound("ambient.crimson.mood");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_FOREST_ADDITIONS = registerSound("ambient.shade_forest.additions");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_FOREST_MOOD = registerSound("ambient.shade_forest.mood");
    //public static final DeferredHolder<SoundEvent, SoundEvent> HILLS_ADDITIONS = registerSound("ambient.hills.additions");

    public static final DeferredHolder<SoundEvent, SoundEvent> UI_CLICK = registerSound("ui.click");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_CODEX_CLICK = registerSound("ui.codex_click");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ALCHEMY_BREW = registerSound("ui.alchemy.brew");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ALCHEMY_NETHER_UPGRADE = registerSound("ui.alchemy.nether_upgrade");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ALCHEMY_ELEMENTAL_UPGRADE = registerSound("ui.alchemy.elemental_upgrade");
    public static final DeferredHolder<SoundEvent, SoundEvent> UI_ALCHEMY_NIHILITY_UPGRADE = registerSound("ui.alchemy.nihility_upgrade");

    public static final DeferredHolder<SoundEvent, SoundEvent> HALLOWEEN_SLICE_LEGACY = registerSound("item.halloween_slice_legacy.use"); // calamity
    public static final DeferredHolder<SoundEvent, SoundEvent> HALLOWEEN_SLICE = registerSound("item.halloween_slice.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWIFTSLICE_LEGACY = registerSound("item.swiftslice_legacy.use"); // calamity
    public static final DeferredHolder<SoundEvent, SoundEvent> SWIFTSLICE = registerSound("item.swiftslice.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> RECHARGE = registerSound("item.recharge.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLAZECHARGE_LEGACY = registerSound("item.blazecharge_legacy.use"); // calamity
    public static final DeferredHolder<SoundEvent, SoundEvent> BLAZECHARGE = registerSound("item.blazecharge.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> ERUPTION = registerSound("item.eruption.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> DISAPPEAR = registerSound("item.disappear.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> EQUIP_CURSE = registerSound("item.curse.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> REPAIR = registerSound("item.repair.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> POT_BREAK = registerSound("block.pot.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> POT_STEP = registerSound("block.pot.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> POT_PLACE = registerSound("block.pot.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> BAG_OPEN = registerSound("item.bag_open.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> WATER_ABILITY = registerSound("item.water_ability.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> PHANTASM_ABILITY_LEGACY = registerSound("item.phantasm_ability_legacy.use"); // calamity
    public static final DeferredHolder<SoundEvent, SoundEvent> PHANTASM_ABILITY = registerSound("item.phantasm_ability.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOODHOUND_ABILITY_LEGACY = registerSound("item.bloodhound_ability_legacy.use"); // calamity
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOODHOUND_ABILITY = registerSound("item.bloodhound_ability.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_SWOOSH = registerSound("item.hammer_swoosh.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_HIT = registerSound("item.hammer_hit.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAMMER_SMASH = registerSound("item.hammer_smash.use");
    public static final DeferredHolder<SoundEvent, SoundEvent> NIHILITY_ALERT = registerSound("item.nihility_alert.active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHIELD_PARRY = registerSound("item.shield.parry");
    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRIC_RUNE = registerSound("item.vampiric_rune.activate");

    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_GROUND_IMPACT = registerSound("item.spear.hit_ground");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_RETURN = registerSound("item.spear.return");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_THROW = registerSound("item.spear.throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> SOUL_COLLECT_FULL = registerSound("item.soul_collect.full");
    public static final DeferredHolder<SoundEvent, SoundEvent> SOUL_COLLECT = registerSound("item.soul_collect");

    public static final DeferredHolder<SoundEvent, SoundEvent> MANIPULATOR_LOOP = registerSound("block.elemental_manipulator.loop");
    public static final DeferredHolder<SoundEvent, SoundEvent> KEG_BREW = registerSound("block.keg.brew");
    public static final DeferredHolder<SoundEvent, SoundEvent> KEG_AMBIENT = registerSound("block.keg.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOID_STONE_PLACE = registerSound("block.void_stone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOID_STONE_BREAK = registerSound("block.void_stone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOID_STONE_STEP = registerSound("block.void_stone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOID_GRASS_STEP = registerSound("block.void_grass.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> VOID_GRASS_BREAK = registerSound("block.void_grass.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> VALORIA_PORTAL_SPAWN = registerSound("block.valoria_portal.spawn");
    public static final DeferredHolder<SoundEvent, SoundEvent> SARCOPHAGUS_OPEN = registerSound("block.sarcophagus.open");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_PLACE = registerSound("block.shade.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_BREAK = registerSound("block.shade.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_HIT = registerSound("block.shade.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_STEP = registerSound("block.shade.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHADE_FALL = registerSound("block.shade.fall");

    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_PLACE = registerSound("block.flesh.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_BREAK = registerSound("block.flesh.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_STEP = registerSound("block.flesh.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_HIT = registerSound("block.flesh.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> FLESH_FALL = registerSound("block.flesh.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> CYST_BREAK = registerSound("block.cyst.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> CYST_FALL = registerSound("block.cyst.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> CYST_SPREAD = registerSound("block.cyst.spreads");
    public static final DeferredHolder<SoundEvent, SoundEvent> CYST_SUMMON = registerSound("block.cyst.summon");

    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BREAK = registerSound("block.tombstone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_HIT = registerSound("block.tombstone.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BRICKS_BREAK = registerSound("block.tombstone_bricks.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_STEP = registerSound("block.tombstone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_FALL = registerSound("block.tombstone.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_PLACE = registerSound("block.tombstone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BRICKS_HIT = registerSound("block.tombstone_bricks.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BRICKS_STEP = registerSound("block.tombstone_bricks.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BRICKS_FALL = registerSound("block.tombstone_bricks.fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> TOMBSTONE_BRICKS_PLACE = registerSound("block.tombstone_bricks.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUSPICIOUS_TOMBSTONE_BREAK = registerSound("block.suspicious_tombstone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> SUSPICIOUS_TOMBSTONE_STEP = registerSound("block.suspicious_tombstone.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_IDLE = registerSound("mob.goblin.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_HURT = registerSound("mob.goblin.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOBLIN_DEATH = registerSound("mob.goblin.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_ATTACK_1 = registerSound("mob.elemental_golem.attack.1");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_ATTACK_2 = registerSound("mob.elemental_golem.attack.2");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_ATTACK_3 = registerSound("mob.elemental_golem.attack.3");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_ATTACK_4 = registerSound("mob.elemental_golem.attack.4");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_HURT = registerSound("mob.elemental_golem.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_DEATH = registerSound("mob.elemental_golem.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ELEMENTAL_GOLEM_STEP = registerSound("mob.elemental_golem.step");

    public static final DeferredHolder<SoundEvent, SoundEvent> STOMP = registerSound("mob.dryador.stomp");

    public static final DeferredHolder<SoundEvent, SoundEvent> TROLL_DISAPPEAR = registerSound("mob.troll.disappear");
    public static final DeferredHolder<SoundEvent, SoundEvent> TROLL_HURT = registerSound("mob.troll.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> TROLL_IDLE = registerSound("mob.troll.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> TROLL_DEATH = registerSound("mob.troll.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> NECROMANCER_SUMMON = registerSound("mob.necromancer_summon");
    public static final DeferredHolder<SoundEvent, SoundEvent> NECROMANCER_SUMMON_GROUND = registerSound("mob.necromancer_summon.ground");
    public static final DeferredHolder<SoundEvent, SoundEvent> NECROMANCER_SUMMON_AIR = registerSound("mob.necromancer_summon.air");

    public static final DeferredHolder<SoundEvent, SoundEvent> WICKED_CRYSTAL_ALTAR = registerSound("mob.wicked_crystal.altar");
    public static final DeferredHolder<SoundEvent, SoundEvent> WICKED_CRYSTAL_TRANSFORM = registerSound("mob.wicked_crystal.transform");
    public static final DeferredHolder<SoundEvent, SoundEvent> WICKED_CRYSTAL_HURT = registerSound("mob.wicked_crystal.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WICKED_CRYSTAL_DEATH = registerSound("mob.wicked_crystal.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WICKED_CRYSTAL_SUMMON = registerSound("mob.wicked_crystal.summon");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_FROST = registerSound("mob.crystal_frost.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_FIRE = registerSound("mob.crystal_fire.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_ACID = registerSound("mob.crystal_acid.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_FROST_PREPARE = registerSound("mob.crystal_frost.prepare");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_FIRE_PREPARE = registerSound("mob.crystal_fire.prepare");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_ACID_PREPARE = registerSound("mob.crystal_acid.prepare");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_STORM = registerSound("mob.crystal.storm");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYSTAL_FALL = registerSound("mob.crystal.fall");

    public static final DeferredHolder<SoundEvent, SoundEvent> MISS = registerSound("mob.attack.miss");
    public static final DeferredHolder<SoundEvent, SoundEvent> DODGE = registerSound("mob.attack.dodge");
    public static final DeferredHolder<SoundEvent, SoundEvent> BREATH = registerSound("mob.breath");

    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_HIT = registerSound("mob.magic.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAGIC_SHOOT = registerSound("mob.magic.shoot");

    public static final DeferredHolder<SoundEvent, SoundEvent> DEVIL_HURT = registerSound("mob.devil.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEVIL_IDLE = registerSound("mob.devil.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEVIL_DEATH = registerSound("mob.devil.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEVIL_ATTACK= registerSound("mob.devil.attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_DEATH = registerSound("mob.haunted_merchant.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_HURT = registerSound("mob.haunted_merchant.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_IDLE = registerSound("mob.haunted_merchant.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_NO = registerSound("mob.haunted_merchant.no");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_YES = registerSound("mob.haunted_merchant.yes");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_MELEE = registerSound("mob.haunted_merchant.melee_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTED_MERCHANT_RANGE = registerSound("mob.haunted_merchant.range_attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_NECROMANCER = registerSound("boss.necromancer.music");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_NECROMANCER_DUNGEON = registerSound("dungeon.necromancer.music");

    //SoundType
    public static final DeferredSoundType CYST = new DeferredSoundType(0.5F, 0.85f, CYST_BREAK, FLESH_STEP, FLESH_PLACE, FLESH_HIT, CYST_FALL);
    public static final DeferredSoundType FLESH = new DeferredSoundType(0.5F, 0.85f, FLESH_BREAK, FLESH_STEP, FLESH_PLACE, FLESH_HIT, FLESH_FALL);
    public static final DeferredSoundType POT = new DeferredSoundType(1.0F, 1.0F, POT_BREAK, POT_STEP, POT_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final DeferredSoundType VOID_STONE = new DeferredSoundType(0.75F, 0.87F, VOID_STONE_BREAK, VOID_STONE_STEP, VOID_STONE_PLACE, () -> SoundEvents.NETHER_BRICKS_HIT, () -> SoundEvents.NETHER_BRICKS_FALL);
    public static final DeferredSoundType VOID_GRASS = new DeferredSoundType(0.75F, 0.87F, VOID_GRASS_BREAK, VOID_GRASS_STEP, VOID_STONE_PLACE, () -> SoundEvents.FROGLIGHT_HIT, () -> SoundEvents.FROGLIGHT_FALL);
    public static final DeferredSoundType SHADE_WOOD = new DeferredSoundType(1.0F, 1.0F, SHADE_BREAK, SHADE_STEP, SHADE_PLACE, SHADE_HIT, SHADE_FALL);
    public static final DeferredSoundType TOMBSTONE = new DeferredSoundType(0.65F, normalizedPitch(1.0f), TOMBSTONE_BREAK, TOMBSTONE_STEP, TOMBSTONE_PLACE, TOMBSTONE_HIT, TOMBSTONE_FALL);
    public static final DeferredSoundType TOMBSTONE_BRICKS = new DeferredSoundType(1.0F, normalizedPitch(1.0f), TOMBSTONE_BRICKS_BREAK, TOMBSTONE_BRICKS_STEP, TOMBSTONE_BRICKS_PLACE, TOMBSTONE_BRICKS_HIT, TOMBSTONE_BRICKS_FALL);
    public static final DeferredSoundType SUSPICIOUS_TOMBSTONE = new DeferredSoundType(1.0F, normalizedPitch(1.0f), SUSPICIOUS_TOMBSTONE_BREAK, SUSPICIOUS_TOMBSTONE_STEP, TOMBSTONE_BRICKS_PLACE, TOMBSTONE_HIT, TOMBSTONE_HIT);

    public static float normalizedPitch(float pitch){
        return pitch / 0.8f;
    }

    public static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name){
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Valoria.loc(name)));
    }

    public static void register(IEventBus eventBus){
        SOUNDS.register(eventBus);
    }
}