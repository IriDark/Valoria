package com.idark.valoria.core.mixin;

import com.idark.valoria.registries.EffectsRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {

    @Final
    @Shadow
    public Player player;

    @Final
    @Shadow
    public NonNullList<ItemStack> items;

    @Inject(method = "setPickedItem(Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private void setPickedItem(CallbackInfo info) {
        if (player.hasEffect(EffectsRegistry.STUN.get())) {
            info.cancel();
        }
    }

    @Inject(method = "pickSlot(I)V", at = @At("HEAD"), cancellable = true)
    private void onPickSlot(int slot, CallbackInfo info) {
        if (player.hasEffect(EffectsRegistry.STUN.get())) {
            info.cancel();
        }
    }

    @Inject(method = "swapPaint(D)V", at = @At("HEAD"), cancellable = true)
    private void onSwapPaint(double direction, CallbackInfo info) {
        if (player.hasEffect(EffectsRegistry.STUN.get())) {
            info.cancel();
        }
    }
}
