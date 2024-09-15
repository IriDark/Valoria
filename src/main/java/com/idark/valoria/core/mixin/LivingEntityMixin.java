package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.EffectsRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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