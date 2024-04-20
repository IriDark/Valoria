package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.sounds.ModSoundType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Some sounds taken from the CalamityMod (Terraria) in a <a href="https://calamitymod.wiki.gg/wiki/Category:Sound_effects">Calamity Mod Wiki.gg</a>
 */
public class SoundsRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Valoria.MOD_ID);

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

    public static final RegistryObject<SoundEvent> VOID_STONE_PLACE = registerSound("block.void_stone.place");
    public static final RegistryObject<SoundEvent> VOID_STONE_BREAK = registerSound("block.void_stone.break");
    public static final RegistryObject<SoundEvent> VOID_STONE_STEP = registerSound("block.void_stone.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_STEP = registerSound("block.void_grass.step");
    public static final RegistryObject<SoundEvent> VOID_GRASS_BREAK = registerSound("block.void_grass.break");

    //SoundType
    public static final ModSoundType POT = new ModSoundType(1, 1, POT_BREAK, POT_STEP, POT_PLACE, () -> SoundEvents.STONE_HIT, () -> SoundEvents.STONE_FALL);
    public static final ModSoundType SUSPICIOUS_TOMBSTONE = new ModSoundType(1.0F, 1.0F, () -> SoundEvents.SUSPICIOUS_SAND_BREAK, () -> SoundEvents.MUD_BRICKS_STEP, () -> SoundEvents.STONE_PLACE, () -> SoundEvents.SUSPICIOUS_GRAVEL_HIT, () -> SoundEvents.STONE_FALL);
    public static final ModSoundType VOID_STONE = new ModSoundType(0.75F, 0.87F, VOID_STONE_BREAK, VOID_STONE_STEP, VOID_STONE_PLACE, () -> SoundEvents.NETHER_BRICKS_HIT, () -> SoundEvents.NETHER_BRICKS_FALL);
    public static final ModSoundType VOID_GRASS = new ModSoundType(0.75F, 0.87F, VOID_GRASS_BREAK, VOID_GRASS_STEP, VOID_STONE_PLACE, () -> SoundEvents.FROGLIGHT_HIT, () -> SoundEvents.FROGLIGHT_FALL);

    public static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Valoria.MOD_ID, name)));
    }
}