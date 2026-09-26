package com.idark.valoria.core.mixin.client;

import com.idark.valoria.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.player.*;
import net.minecraft.client.resources.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Shadow
    protected abstract PlayerInfo getPlayerInfo();

    @Inject(method = "getSkin", at = @At(value = "RETURN"), cancellable = true)
    private void valoria$getSkin(CallbackInfoReturnable<PlayerSkin> cir) {
        PlayerInfo playerInfo = this.getPlayerInfo();
        if (playerInfo == null || playerInfo.getProfile().getName() == null || playerInfo.getProfile().getName().isEmpty()) return;

        String playerName = playerInfo.getProfile().getName();
        PlayerSkin skin = cir.getReturnValue();
        if (skin == null) return;
        for(Cloaks cape : Cloaks.values()) {
            if(playerName.equals(cape.name)) {
                cir.setReturnValue(new PlayerSkin(skin.texture(), skin.textureUrl(), cape.texture, skin.elytraTexture(), skin.model(), skin.secure()));
            }
        }
    }
}
