package com.idark.valoria.core.mixin;

import com.idark.valoria.core.datafix.*;
import com.mojang.datafixers.*;
import net.minecraft.util.datafix.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {

    // why I cant register my own data fix Mojang????
    @Inject(method = "addFixers", at = @At("TAIL"))
    private static void valoria$registerCustomDataFixers(DataFixerBuilder builder, CallbackInfo ci) {
        ValoriaDataFixers.register(builder);
    }
}