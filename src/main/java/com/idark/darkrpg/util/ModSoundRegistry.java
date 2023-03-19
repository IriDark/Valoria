package com.idark.darkrpg.util;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.util.ModSoundType;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModSoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DarkRPG.MOD_ID);

    public static final RegistryObject<SoundEvent> MAGIC_USE = registerSound("item.magic.use");
    public static final RegistryObject<SoundEvent> SWIFTSLICE = registerSound("item.swiftslice.use");
	public static final RegistryObject<SoundEvent> RECHARGE = registerSound("item.recharge.use");
	public static final RegistryObject<SoundEvent> BLAZECHARGE = registerSound("item.blazecharge.use");
	public static final RegistryObject<SoundEvent> ERUPTION = registerSound("item.eruption.use");
	public static final RegistryObject<SoundEvent> PIERCE1 = registerSound("item.pierce1.ambient");
	public static final RegistryObject<SoundEvent> PIERCE2 = registerSound("item.pierce2.ambient");
	public static final RegistryObject<SoundEvent> PIERCE3 = registerSound("item.pierce3.ambient");
	public static final RegistryObject<SoundEvent> EGG_BREAK = registerSound("block.egg_break.ambient");
    public static final RegistryObject<SoundEvent> EGG_STEP = registerSound("block.egg_step.ambient");

    //SoundType
	public static final ModSoundType SPIDER_EGG = new ModSoundType(1, 1, ()->EGG_BREAK.get(), ()->EGG_STEP.get(), ()->SoundEvents.BLOCK_STONE_PLACE, ()->SoundEvents.BLOCK_STONE_HIT, ()->SoundEvents.BLOCK_STONE_FALL);

	public static RegistryObject<SoundEvent> registerSound(String name){
        return SOUNDS.register(name,()->new SoundEvent(new ResourceLocation(DarkRPG.MOD_ID,name)));
	}
}