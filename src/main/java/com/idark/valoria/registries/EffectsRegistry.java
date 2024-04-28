package com.idark.valoria.registries;

import com.idark.valoria.Valoria;
import com.idark.valoria.registries.effect.AloeRegenEffect;
import com.idark.valoria.registries.effect.BleedingEffect;
import com.idark.valoria.registries.effect.StunEffect;
import com.idark.valoria.registries.effect.TipsyEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectsRegistry {
    private final static String MODID = Valoria.ID;
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<MobEffect> ALOEREGEN = EFFECTS.register("aloeregen", AloeRegenEffect::new);
    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
    public static final RegistryObject<MobEffect> TIPSY = EFFECTS.register("tipsy", TipsyEffect::new);
    public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}