package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
 */
public class SoundsRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Valoria.ID);
    public static final RegistryObject<SoundEvent> HALLOWEEN_SLICE = registerSound("item.halloween_slice.use");
    public static final RegistryObject<SoundEvent> SWIFTSLICE = registerSound("item.swiftslice.use");
    public static final RegistryObject<SoundEvent> RECHARGE = registerSound("item.recharge.use");
    public static final RegistryObject<SoundEvent> BLAZECHARGE = registerSound("item.blazecharge.use");
    public static final RegistryObject<SoundEvent> ERUPTION = registerSound("item.eruption.use");
    //public static final RegistryObject<SoundEvent> PIERCE = registerSound("item.pierce.ambient");
    public static final RegistryObject<SoundEvent> DISAPPEAR = registerSound("item.disappear.ambient");
    public static final RegistryObject<SoundEvent> EQUIP_CURSE = registerSound("item.curse.ambient");
    public static final RegistryObject<SoundEvent> REPAIR = registerSound("item.repair.ambient");
    public static final RegistryObject<SoundEvent> POT_BREAK = registerSound("block.pot.break");
    public static final RegistryObject<SoundEvent> POT_STEP = registerSound("block.pot.step");
    public static final RegistryObject<SoundEvent> POT_PLACE = registerSound("block.pot.place");
    public static final RegistryObject<SoundEvent> BAG_OPEN = registerSound("item.bag_open.use");
    public static final RegistryObject<SoundEvent> WATER_ABILITY = registerSound("item.water_ability.use");
    public static final RegistryObject<SoundEvent> PHANTASM_ABILITY = registerSound("item.phantasm_ability.use");
    public static final RegistryObject<SoundEvent> BLOODHOUND_ABILITY = registerSound("item.bloodhound_ability.use");
    public static final RegistryObject<SoundEvent> SPEAR_GROUND_IMPACT = registerSound("item.spear.hit_ground");
    public static final RegistryObject<SoundEvent> SPEAR_RETURN = registerSound("item.spear.return");
    public static final RegistryObject<SoundEvent> SPEAR_THROW = registerSound("item.spear.throw");

    public static final RegistryObject<SoundEvent> VOID_STONE_PLACE = registerSound("block.void_stone.place");
    public static final RegistryObject<SoundEvent> VOID_STONE_BREAK = registerSound("block.void_stone.break");
    public static final RegistryObject<SoundEvent> VOID_STONE_STEP = registerSound("block.void_stone.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_STEP = registerSound("block.void_grass.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_BREAK = registerSound("block.void_grass.break");

    public static final RegistryObject<SoundEvent> GOBLIN_IDLE = registerSound("mob.goblin.idle");
    public static final RegistryObject<SoundEvent> GOBLIN_HURT = registerSound("mob.goblin.hurt");
    public static final RegistryObject<SoundEvent> GOBLIN_DEATH = registerSound("mob.goblin.death");

    public static final RegistryObject<SoundEvent> TROLL_DISAPPEAR = registerSound("mob.troll.disappear");
    public static final RegistryObject<SoundEvent> TROLL_HURT = registerSound("mob.troll.hurt");

    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_DEATH = registerSound("mob.haunted_merchant.death");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_HURT = registerSound("mob.haunted_merchant.hurt");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_IDLE = registerSound("mob.haunted_merchant.idle");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_NO = registerSound("mob.haunted_merchant.no");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_YES = registerSound("mob.haunted_merchant.yes");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_MELEE = registerSound("mob.haunted_merchant.melee_attack");
    public static final RegistryObject<SoundEvent> HAUNTED_MERCHANT_RANGE = registerSound("mob.haunted_merchant.range_attack");

    //SoundType
    public static final ForgeSoundType POT = new ForgeSoundType(1, 1, POT_BREAK, POT_STEP, POT_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType SUSPICIOUS_TOMBSTONE = new ForgeSoundType(1.0F, 1.0F, () -> SoundEvents.SUSPICIOUS_SAND_BREAK, () -> SoundEvents.MUD_BRICKS_STEP, () -> SoundEvents.STONE_PLACE, () -> SoundEvents.SUSPICIOUS_GRAVEL_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType VOID_STONE = new ForgeSoundType(0.75F, 0.87F, VOID_STONE_BREAK, VOID_STONE_STEP, VOID_STONE_PLACE, () -> SoundEvents.NETHER_BRICKS_HIT, () -> SoundEvents.NETHER_BRICKS_FALL);
    public static final ForgeSoundType VOID_GRASS = new ForgeSoundType(0.75F, 0.87F, VOID_GRASS_BREAK, VOID_GRASS_STEP, VOID_STONE_PLACE, () -> SoundEvents.FROGLIGHT_HIT, () -> SoundEvents.FROGLIGHT_FALL);

    public static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Valoria.ID, name)));
    }
}