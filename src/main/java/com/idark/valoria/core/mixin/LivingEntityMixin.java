package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{
    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    @Inject(method = {"canAttack*"}, at = @At(value = "HEAD"), cancellable = true)
    public void canAttack(LivingEntity p_21171_, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasEffect(EffectsRegistry.STUN.get())) {
            cir.setReturnValue(false);
        }

    }
}