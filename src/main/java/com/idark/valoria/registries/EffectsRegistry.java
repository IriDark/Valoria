package com.idark.valoria.registries;

import com.idark.valoria.*;
import com.idark.valoria.registries.effect.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class EffectsRegistry{
    private final static String MODID = Valoria.ID;
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final DeferredHolder<MobEffect, MobEffect> ALOEREGEN = EFFECTS.register("aloeregen", AloeRegenEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> STUN = EFFECTS.register("stun", StunEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TIPSY = EFFECTS.register("tipsy", TipsyEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SINISTER_PREDICTION = EFFECTS.register("sinister_prediction", () -> new SinisterPredictionEffect().addAttributeModifier(Attributes.ATTACK_DAMAGE, Valoria.loc("sinister_prediction_damage"), 4.0D, AttributeModifier.Operation.ADD_VALUE).addAttributeModifier(Attributes.MAX_HEALTH, Valoria.loc("sinister_prediction_health"), 2.0D, AttributeModifier.Operation.ADD_VALUE));
    public static final DeferredHolder<MobEffect, MobEffect> EXHAUSTION = EFFECTS.register("exhaustion", ExhaustionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> RENEWAL = EFFECTS.register("renewal", RenewalEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SOUL_BURST = EFFECTS.register("soul_burst", SoulBurstEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> NIHILITY_PROTECTION = EFFECTS.register("nihility_protection", NihilityProtectionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> HAMMER_SMASH = EFFECTS.register("hammer_smash", HammerSmashEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SUNDERED = EFFECTS.register("sundered", SunderedEffect::new);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}