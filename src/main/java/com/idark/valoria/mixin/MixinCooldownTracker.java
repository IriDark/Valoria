package com.idark.valoria.mixin;

import com.idark.valoria.registries.world.item.CooldownHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerItemCooldowns.class})
public abstract class MixinCooldownTracker {

    @Shadow
    @Final
    private ServerPlayer player;

    @Inject(method = "onCooldownEnded", at = @At("HEAD"))
    public void onRemove(Item itemIn, CallbackInfo ci) {
        CooldownHandler.onCooldownEnd(player, itemIn);
    }
}