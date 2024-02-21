package com.idark.valoria.registries.sounds;

import com.idark.valoria.Valoria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Valoria.MOD_ID);

    //public static final RegistryObject<SoundEvent> MAGIC_USE = registerSound("item.magic.use");
    public static final RegistryObject<SoundEvent> SWIFTSLICE = registerSound("item.swiftslice.use");
	public static final RegistryObject<SoundEvent> RECHARGE = registerSound("item.recharge.use");
	public static final RegistryObject<SoundEvent> BLAZECHARGE = registerSound("item.blazecharge.use");
	public static final RegistryObject<SoundEvent> ERUPTION = registerSound("item.eruption.use");
	//public static final RegistryObject<SoundEvent> PIERCE = registerSound("item.pierce.ambient");
	public static final RegistryObject<SoundEvent> DISAPPEAR = registerSound("item.disappear.ambient");
	public static final RegistryObject<SoundEvent> EQUIP_CURSE = registerSound("item.curse.ambient");
	public static final RegistryObject<SoundEvent> REPAIR = registerSound("item.repair.ambient");
	public static final RegistryObject<SoundEvent> POT_BREAK = registerSound("block.pot_break.ambient");
	public static final RegistryObject<SoundEvent> POT_STEP = registerSound("block.pot_step.ambient");
	public static final RegistryObject<SoundEvent> POT_PLACE = registerSound("block.pot_place.ambient");
	public static final RegistryObject<SoundEvent> BAG_OPEN = registerSound("item.bag_open.use");

    //SoundType
	public static final ModSoundType POT = new ModSoundType(1, 1, POT_BREAK, POT_STEP, POT_PLACE, ()-> SoundEvents.STONE_HIT, ()-> SoundEvents.STONE_FALL);
	public static final ModSoundType SUSPICIOUS_TOMBSTONE = new ModSoundType(1.0F, 1.0F, ()-> SoundEvents.SUSPICIOUS_SAND_BREAK, ()-> SoundEvents.MUD_BRICKS_STEP, ()-> SoundEvents.STONE_PLACE, ()-> SoundEvents.SUSPICIOUS_GRAVEL_HIT, ()-> SoundEvents.STONE_FALL);

	public static RegistryObject<SoundEvent> registerSound(String name){
        return SOUNDS.register(name,()-> SoundEvent.createVariableRangeEvent(new ResourceLocation(Valoria.MOD_ID,name)));
	}
}