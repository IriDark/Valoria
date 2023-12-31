package com.idark.valoria.effect;

import com.idark.valoria.Valoria;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	private final static String MODID = Valoria.MOD_ID;
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
	
	public static final RegistryObject<MobEffect> ALOEREGEN = EFFECTS.register("aloeregen", AloeRegenEffect::new);
	public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
	public static final RegistryObject<MobEffect> TIPSY = EFFECTS.register("tipsy", TipsyEffect::new);
	public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}