package com.idark.darkrpg.effect;

import com.idark.darkrpg.DarkRPG;
import com.idark.darkrpg.effect.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModEffects {
	private final static String MODID = DarkRPG.MOD_ID;
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);
	
	public static final RegistryObject<Effect> ALOEREGEN = EFFECTS.register("aloeregen", () -> new AloeRegenEffect());
	public static final RegistryObject<Effect> STUN = EFFECTS.register("stun", () -> new StunEffect());
	public static final RegistryObject<Effect> TIPSY = EFFECTS.register("tipsy", () -> new TipsyEffect());

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}