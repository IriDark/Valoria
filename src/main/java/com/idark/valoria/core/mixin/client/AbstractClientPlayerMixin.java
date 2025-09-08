package com.idark.valoria.core.mixin.client;

import com.idark.valoria.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Shadow
    protected abstract PlayerInfo getPlayerInfo();

    @Inject(method = "getCloakTextureLocation", at = @At(value = "HEAD"), cancellable = true)
    private void valoria$getCloakTextureLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        PlayerInfo playerInfo = this.getPlayerInfo();
        if (playerInfo == null || !playerInfo.getProfile().isComplete()) return;

        String playerName = playerInfo.getProfile().getName();
        for(Cloaks cape : Cloaks.values()) {
            if(playerName.equals(cape.name)) cir.setReturnValue(cape.texture);
        }
    }
}