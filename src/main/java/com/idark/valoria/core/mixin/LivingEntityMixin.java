package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin{

    @Shadow
    public abstract boolean hasEffect(MobEffect pEffect);

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract void stopUsingItem();

    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand pHand);

    @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"), cancellable = true)
    public void canAttack(LivingEntity pTarget, CallbackInfoReturnable<Boolean> cir){
        if(this.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/ai/targeting/TargetingConditions;)Z", at = @At("RETURN"), cancellable = true)
    public void canAttack(LivingEntity pTarget, TargetingConditions pCondition, CallbackInfoReturnable<Boolean> cir){
        if(this.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onClimbable", at = @At("RETURN"), cancellable = true)
    public void onClimbable(CallbackInfoReturnable<Boolean> cir){
        if(this.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "jumpFromGround", at = @At("RETURN"), cancellable = true)
    protected void jumpFromGround(CallbackInfo ci){
        if(this.hasEffect(EffectsRegistry.STUN.get())){
            ci.cancel();
        }
    }

    @Inject(method = "isImmobile", at = @At("RETURN"), cancellable = true)
    protected void isImmobile(CallbackInfoReturnable<Boolean> cir) {
        if(this.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "updatingUsingItem", at = @At("RETURN"))
    private void updatingUsingItem(CallbackInfo ci) {
        if (this.isUsingItem()){
            if(this.hasEffect(EffectsRegistry.STUN.get())){
                this.stopUsingItem();
            }
        }
    }

    @Inject(method = "startUsingItem", at = @At("RETURN"))
    public void startUsingItem(InteractionHand pHand, CallbackInfo ci) {
        ItemStack itemstack = this.getItemInHand(pHand);
        if (!itemstack.isEmpty() && !this.isUsingItem()) {
            if(this.hasEffect(EffectsRegistry.STUN.get())){
                this.stopUsingItem();
            }
        }
    }

    @Inject(method = "eat", at = @At("RETURN"), cancellable = true)
    public void eat(Level pLevel, ItemStack pFood, CallbackInfoReturnable<ItemStack> cir) {
        if(pFood.isEdible() && this.hasEffect(EffectsRegistry.STUN.get())){
            this.stopUsingItem();
        }

        cir.setReturnValue(pFood);
    }
}