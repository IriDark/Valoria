package com.idark.valoria.core.mixin;

import com.idark.valoria.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin({ServerItemCooldowns.class})
public abstract class MixinCooldownTracker{

    @Shadow
    @Final
    private ServerPlayer player;

    @Inject(method = "onCooldownEnded", at = @At("HEAD"))
    public void onRemove(Item itemIn, CallbackInfo ci){
        CooldownHandler.onCooldownEnd(player, itemIn);
    }
}