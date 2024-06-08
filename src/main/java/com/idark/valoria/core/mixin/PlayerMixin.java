package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Player.class)
public abstract class PlayerMixin{

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attack(Entity pTarget, CallbackInfo ci) {
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            ci.cancel();
        }
    }

    @Inject(method = "blockActionRestricted", at = @At("HEAD"), cancellable = true)
    public void blockActionRestricted(Level pLevel, BlockPos pPos, GameType pGameMode, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mayBuild", at = @At("HEAD"), cancellable = true)
    public void mayBuild(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "mayUseItemAt", at = @At("RETURN"), cancellable = true)
    public void mayUseItemAt(BlockPos pPos, Direction pFacing, ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "jumpFromGround", at = @At("RETURN"), cancellable = true)
    protected void jumpFromGround(CallbackInfo ci){
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            ci.cancel();
        }
    }

    @Inject(method = "isImmobile", at = @At("RETURN"), cancellable = true)
    protected void isImmobile(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) ((Object) this);
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
    public void eat(Level pLevel, ItemStack pFood, CallbackInfoReturnable<ItemStack> cir) {
        Player player = (Player) ((Object) this);
        if(pFood.isEdible() && player.hasEffect(EffectsRegistry.STUN.get())){
            player.stopUsingItem();
        }

        cir.setReturnValue(pFood);
    }
}