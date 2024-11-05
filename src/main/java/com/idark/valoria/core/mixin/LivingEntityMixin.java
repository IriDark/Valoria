package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    //TODO
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(Vec3 pTravelVector, CallbackInfo ci) {
        if (this.hasEffect(EffectsRegistry.STUN.get())) {
            ci.cancel();
        }
    }
}