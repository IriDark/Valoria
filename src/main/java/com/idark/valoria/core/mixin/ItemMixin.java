package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

//TODO: Finish this
@Mixin(Item.class)
public class ItemMixin{

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if(pPlayer.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand)));
        }
    }

    @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
    public void hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir) {
        if(pAttacker.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }
}
