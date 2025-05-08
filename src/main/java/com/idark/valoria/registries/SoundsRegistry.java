package com.idark.valoria.registries;

import com.idark.valoria.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

/**
 * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
 */
public class SoundsRegistry{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Valoria.ID);
    public static final RegistryObject<SoundEvent> ENDURING = registerSound("music.valoria.enduring");
    public static final RegistryObject<SoundEvent> SHADED_LANDS = registerSound("music.valoria.shaded_lands");
    public static final RegistryObject<SoundEvent> RISING = registerSound("music.valoria.rising");
    public static final RegistryObject<SoundEvent> ARRIVING = registerSound("music.valoria.arriving");
    public static final RegistryObject<SoundEvent> BLOOD_POLE = registerSound("music.valoria.blood_pole");
    public static final RegistryObject<SoundEvent> CARRION = registerSound("music.valoria.carrion");

    public static final RegistryObject<SoundEvent> HALLOWEEN_SLICE_LEGACY = registerSound("item.halloween_slice_legacy.use"); // calamity
    public static final RegistryObject<SoundEvent> HALLOWEEN_SLICE = registerSound("item.halloween_slice.use");
    public static final RegistryObject<SoundEvent> SWIFTSLICE_LEGACY = registerSound("item.swiftslice_legacy.use"); // calamity
    public static final RegistryObject<SoundEvent> SWIFTSLICE = registerSound("item.swiftslice.use");
    public static final RegistryObject<SoundEvent> RECHARGE = registerSound("item.recharge.use");
    public static final RegistryObject<SoundEvent> BLAZECHARGE_LEGACY = registerSound("item.blazecharge_legacy.use"); // calamity
    public static final RegistryObject<SoundEvent> BLAZECHARGE = registerSound("item.blazecharge.use");
    public static final RegistryObject<SoundEvent> ERUPTION = registerSound("item.eruption.use");
    public static final RegistryObject<SoundEvent> DISAPPEAR = registerSound("item.disappear.ambient");
    public static final RegistryObject<SoundEvent> EQUIP_CURSE = registerSound("item.curse.ambient");
    public static final RegistryObject<SoundEvent> REPAIR = registerSound("item.repair.ambient");
    public static final RegistryObject<SoundEvent> POT_BREAK = registerSound("block.pot.break");
    public static final RegistryObject<SoundEvent> POT_STEP = registerSound("block.pot.step");
    public static final RegistryObject<SoundEvent> POT_PLACE = registerSound("block.pot.place");
    public static final RegistryObject<SoundEvent> BAG_OPEN = registerSound("item.bag_open.use");
    public static final RegistryObject<SoundEvent> WATER_ABILITY = registerSound("item.water_ability.use");
    public static final RegistryObject<SoundEvent> PHANTASM_ABILITY_LEGACY = registerSound("item.phantasm_ability_legacy.use"); // calamity
    public static final RegistryObject<SoundEvent> PHANTASM_ABILITY = registerSound("item.phantasm_ability.use");
    public static final RegistryObject<SoundEvent> BLOODHOUND_ABILITY_LEGACY = registerSound("item.bloodhound_ability_legacy.use"); // calamity
    public static final RegistryObject<SoundEvent> BLOODHOUND_ABILITY = registerSound("item.bloodhound_ability.use");

    public static final RegistryObject<SoundEvent> SPEAR_GROUND_IMPACT = registerSound("item.spear.hit_ground");
    public static final RegistryObject<SoundEvent> SPEAR_RETURN = registerSound("item.spear.return");
    public static final RegistryObject<SoundEvent> SPEAR_THROW = registerSound("item.spear.throw");
    public static final RegistryObject<SoundEvent> SOUL_COLLECT_FULL = registerSound("item.soul_collect.full");
    public static final RegistryObject<SoundEvent> SOUL_COLLECT = registerSound("item.soul_collect");

    public static final RegistryObject<SoundEvent> MANIPULATOR_LOOP = registerSound("block.elemental_manipulator.loop");
    public static final RegistryObject<SoundEvent> KEG_BREW = registerSound("block.keg.brew");
    public static final RegistryObject<SoundEvent> KEG_AMBIENT = registerSound("block.keg.ambient");
    public static final RegistryObject<SoundEvent> VOID_STONE_PLACE = registerSound("block.void_stone.place");
    public static final RegistryObject<SoundEvent> VOID_STONE_BREAK = registerSound("block.void_stone.break");
    public static final RegistryObject<SoundEvent> VOID_STONE_STEP = registerSound("block.void_stone.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_STEP = registerSound("block.void_grass.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_BREAK = registerSound("block.void_grass.break");
    public static final RegistryObject<SoundEvent> VALORIA_PORTAL_SPAWN = registerSound("block.valoria_portal.spawn");
    public static final RegistryObject<SoundEvent> SARCOPHAGUS_OPEN = registerSound("block.sarcophagus.open");

    public static final RegistryObject<SoundEvent> FLESH_PLACE = registerSound("block.flesh.place");
    public static final RegistryObject<SoundEvent> FLESH_BREAK = registerSound("block.flesh.break");
    public static final RegistryObject<SoundEvent> FLESH_STEP = registerSound("block.flesh.step");
    public static final RegistryObject<SoundEvent> FLESH_HIT = registerSound("block.flesh.hit");
    public static final RegistryObject<SoundEvent> FLESH_FALL = registerSound("block.flesh.fall");
    public static final RegistryObject<SoundEvent> CYST_BREAK = registerSound("block.cyst.break");
    public static final RegistryObject<SoundEvent> CYST_FALL = registerSound("block.cyst.fall");
    public static final RegistryObject<SoundEvent> CYST_SPREAD = registerSound("block.cyst.spreads");
    public static final RegistryObject<SoundEvent> CYST_SUMMON = registerSound("block.cyst.summon");

    public static final RegistryObject<SoundEvent> TOMBSTONE_BREAK = registerSound("block.tombstone.break");
    public static final RegistryObject<SoundEvent> TOMBSTONE_HIT = registerSound("block.tombstone.hit");
    public static final RegistryObject<SoundEvent> TOMBSTONE_BRICKS_BREAK = registerSound("block.tombstone_bricks.break");
    public static final RegistryObject<SoundEvent> TOMBSTONE_STEP = registerSound("block.tombstone.step");
    public static final RegistryObject<SoundEvent> TOMBSTONE_FALL = registerSound("block.tombstone.fall");
    public static final RegistryObject<SoundEvent> TOMBSTONE_PLACE = registerSound("block.tombstone.place");
    public static final RegistryObject<SoundEvent> TOMBSTONE_BRICKS_HIT = registerSound("block.tombstone_bricks.hit");
    public static final RegistryObject<SoundEvent> TOMBSTONE_BRICKS_STEP = registerSound("block.tombstone_bricks.step");
    public static final RegistryObject<SoundEvent> TOMBSTONE_BRICKS_FALL = registerSound("block.tombstone_bricks.fall");
    public static final RegistryObject<SoundEvent> TOMBSTONE_BRICKS_PLACE = registerSound("block.tombstone_bricks.place");
    public static final RegistryObject<SoundEvent> SUSPICIOUS_TOMBSTONE_BREAK = registerSound("block.suspicious_tombstone.break");
    public static final RegistryObject<SoundEvent> SUSPICIOUS_TOMBSTONE_STEP = registerSound("block.suspicious_tombstone.step");

    public static final RegistryObject<SoundEvent> GOBLIN_IDLE = registerSound("mob.goblin.idle");
    public static final RegistryObject<SoundEvent> GOBLIN_HURT = registerSound("mob.goblin.hurt");
    public static final RegistryObject<SoundEvent> GOBLIN_DEATH = registerSound("mob.goblin.death");

    public static final RegistryObject<SoundEvent> STOMP = registerSound("mob.dryador.stomp");

    public static final RegistryObject<SoundEvent> TROLL_DISAPPEAR = registerSound("mob.troll.disappear");
    public static final RegistryObject<SoundEvent> TROLL_HURT = registerSound("mob.troll.hurt");
    public static final RegistryObject<SoundEvent> TROLL_IDLE = registerSound("mob.troll.idle");
    public static final RegistryObject<SoundEvent> TROLL_DEATH = registerSound("mob.troll.death");

    public static final RegistryObject<SoundEvent> NECROMANCER_SUMMON = registerSound("mob.necromancer_summon");
    public static final RegistryObject<SoundEvent> NECROMANCER_SUMMON_GROUND = registerSound("mob.necromancer_summon.ground");
    public static final RegistryObject<SoundEvent> NECROMANCER_SUMMON_AIR = registerSound("mob.necromancer_summon.air");

    public static final RegistryObject<SoundEvent> WICKED_CRYSTAL_ALTAR = registerSound("mob.wicked_crystal.altar");
    public static final RegistryObject<SoundEvent> WICKED_CRYSTAL_TRANSFORM = registerSound("mob.wicked_crystal.transform");
    public static final RegistryObject<SoundEvent> WICKED_CRYSTAL_HURT = registerSound("mob.wicked_crystal.hurt");
    public static final RegistryObject<SoundEvent> WICKED_CRYSTAL_DEATH = registerSound("mob.wicked_crystal.death");
    public static final RegistryObject<SoundEvent> WICKED_CRYSTAL_SUMMON = registerSound("mob.wicked_crystal.summon");
    public static final RegistryObject<SoundEvent> CRYSTAL_FROST = registerSound("mob.crystal_frost.attack");
    public static final RegistryObject<SoundEvent> CRYSTAL_FIRE = registerSound("mob.crystal_fire.attack");
    public static final RegistryObject<SoundEvent> CRYSTAL_ACID = registerSound("mob.crystal_acid.attack");
    public static final RegistryObject<SoundEvent> CRYSTAL_FROST_PREPARE = registerSound("mob.crystal_frost.prepare");
    public static final RegistryObject<SoundEvent> CRYSTAL_FIRE_PREPARE = registerSound("mob.crystal_fire.prepare");
    public static final RegistryObject<SoundEvent> CRYSTAL_ACID_PREPARE = registerSound("mob.crystal_acid.prepare");
    public static final RegistryObject<SoundEvent> CRYSTAL_STORM = registerSound("mob.crystal.storm");
    public static final RegistryObject<SoundEvent> CRYSTAL_FALL = registerSound("mob.crystal.fall");

    public static final RegistryObject<SoundEvent> MAGIC_HIT = registerSound("mob.magic.hit");
    public static final RegistryObject<SoundEvent> MAGIC_SHOOT = registerSound("mob.magic.shoot");

    public static final RegistryObject<SoundEvent> DEVIL_HURT = registerSound("mob.devil.hurt");
    public static final RegistryObject<SoundEvent> DEVIL_IDLE = registerSound("mob.devil.idle");
    public static final RegistryObject<SoundEvent> DEVIL_DEATH = registerSound("mob.devil.death");
    public static final RegistryObject<SoundEvent> DEVIL_ATTACK= registerSound("mob.devil.attack");

    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_DEATH = registerSound("mob.haunted_merchant.death");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_HURT = registerSound("mob.haunted_merchant.hurt");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_IDLE = registerSound("mob.haunted_merchant.idle");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_NO = registerSound("mob.haunted_merchant.no");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_YES = registerSound("mob.haunted_merchant.yes");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_MELEE = registerSound("mob.haunted_merchant.melee_attack");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_RANGE = registerSound("mob.haunted_merchant.range_attack");

    public static final RegistryObject<SoundEvent> MUSIC_NECROMANCER = registerSound("boss.necromancer.music");
    public static final RegistryObject<SoundEvent> MUSIC_NECROMANCER_DUNGEON = registerSound("dungeon.necromancer.music");

    //SoundType
    public static final ForgeSoundType CYST = new ForgeSoundType(0.5F, 0.85f, CYST_BREAK, FLESH_STEP, FLESH_PLACE, FLESH_HIT, CYST_FALL);
    public static final ForgeSoundType FLESH = new ForgeSoundType(0.5F, 0.85f, FLESH_BREAK, FLESH_STEP, FLESH_PLACE, FLESH_HIT, FLESH_FALL);
    public static final ForgeSoundType POT = new ForgeSoundType(1.0F, 1.0F, POT_BREAK, POT_STEP, POT_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType VOID_STONE = new ForgeSoundType(0.75F, 0.87F, VOID_STONE_BREAK, VOID_STONE_STEP, VOID_STONE_PLACE, () -> SoundEvents.NETHER_BRICKS_HIT, () -> SoundEvents.NETHER_BRICKS_FALL);
    public static final ForgeSoundType VOID_GRASS = new ForgeSoundType(0.75F, 0.87F, VOID_GRASS_BREAK, VOID_GRASS_STEP, VOID_STONE_PLACE, () -> SoundEvents.FROGLIGHT_HIT, () -> SoundEvents.FROGLIGHT_FALL);
    public static final ForgeSoundType TOMBSTONE = new ForgeSoundType(0.65F, normalizedPitch(1.0f), TOMBSTONE_BREAK, TOMBSTONE_STEP, TOMBSTONE_PLACE, TOMBSTONE_HIT, TOMBSTONE_FALL);
    public static final ForgeSoundType TOMBSTONE_BRICKS = new ForgeSoundType(1.0F, normalizedPitch(1.0f), TOMBSTONE_BRICKS_BREAK, TOMBSTONE_BRICKS_STEP, TOMBSTONE_BRICKS_PLACE, TOMBSTONE_BRICKS_HIT, TOMBSTONE_BRICKS_FALL);
    public static final ForgeSoundType SUSPICIOUS_TOMBSTONE = new ForgeSoundType(1.0F, normalizedPitch(1.0f), SUSPICIOUS_TOMBSTONE_BREAK, SUSPICIOUS_TOMBSTONE_STEP, TOMBSTONE_BRICKS_PLACE, TOMBSTONE_HIT, TOMBSTONE_HIT);

    /**
     * Used in blocks to normalize pitch, because mojang did weird thing: pitch value * 0.8f
     *
     * @return Normalized pitch
     */
    public static float normalizedPitch(float pitch){
        return pitch / 0.8f;
    }

    public static RegistryObject<SoundEvent> registerSound(String name){
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Valoria.ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUNDS.register(eventBus);
    }
}