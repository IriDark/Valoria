package com.idark.valoria.registries.effect;

import com.idark.valoria.registries.*;
import com.idark.valoria.registries.item.types.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import pro.komaru.tridot.util.*;

public class HammerSmashEffect extends MobEffect {

    public HammerSmashEffect() {
        super(MobEffectCategory.BENEFICIAL, Col.hexToDecimal("E2A645"));
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier){
        super.applyEffectTick(entity, amplifier);
        if (!(entity instanceof Player player)) return true;
        if (player.isInWater() || player.isInLava() || player.onClimbable() || player.isFallFlying() || player.isPassenger()) {
            player.removeEffect(EffectsRegistry.HAMMER_SMASH);
            return true;
        }

        if (!(player.getMainHandItem().getItem() instanceof HammerItem hammer)) {
            player.removeEffect(EffectsRegistry.HAMMER_SMASH);
            return true;
        }

        MobEffectInstance inst = player.getEffect(EffectsRegistry.HAMMER_SMASH);
        if (inst != null && inst.getDuration() <= 58) {
            if (player.onGround() && player.getDeltaMovement().y <= 0.05) {
                if (!player.level().isClientSide) {
                    hammer.performSmash(player.getMainHandItem(), player.level(), player);
                }

                player.removeEffect(EffectsRegistry.HAMMER_SMASH);
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier){
        return true;
    }
}
