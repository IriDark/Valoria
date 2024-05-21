package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.*;
import net.minecraft.client.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Inventory.class)
public class InventoryMixin{

    @Final
    @Shadow
    public Player player;

    @Final
    @Shadow
    public NonNullList<ItemStack> items;

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "isHotbarSlot", at = @At("HEAD"), cancellable = true)
    private static void isHotbarSlot(CallbackInfoReturnable<Boolean> cir){
        if(Minecraft.getInstance().player != null){
            if(Minecraft.getInstance().player.hasEffect(EffectsRegistry.STUN.get())){
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "setPickedItem(Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void setPickedItem(CallbackInfo info){
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            info.cancel();
        }
    }

    @Inject(method = "pickSlot(I)V", at = @At("HEAD"), cancellable = true)
    private void onPickSlot(int slot, CallbackInfo info){
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            info.cancel();
        }
    }

    @Inject(method = "swapPaint(D)V", at = @At("HEAD"), cancellable = true)
    private void onSwapPaint(double direction, CallbackInfo info){
        if(player.hasEffect(EffectsRegistry.STUN.get())){
            info.cancel();
        }
    }
}
