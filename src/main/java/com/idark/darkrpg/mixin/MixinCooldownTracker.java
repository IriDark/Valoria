package com.idark.darkrpg.mixin;

import com.idark.darkrpg.item.CooldownHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ServerCooldownTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerCooldownTracker.class})
public class MixinCooldownTracker {

    @Shadow
    @Final
    private ServerPlayerEntity player;


    @Inject(method = "onCooldownEnded",at = @At("HEAD"))
    public void onRemove(Item itemIn, CallbackInfo ci) {
        CooldownHandler.onCooldownEnd(player,itemIn);
    }

}