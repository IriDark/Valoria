package com.idark.valoria.core.mixin.client;

import com.idark.valoria.registries.level.*;
import com.idark.valoria.util.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ClientLevel.class)
public class ClientLevelMixin{

    @Inject(at = @At("HEAD"), method = "getSkyColor", cancellable = true)
    public void getSkyColor(Vec3 pPos, float pPartialTick, CallbackInfoReturnable<Vec3> cir) {
        Level level = Minecraft.getInstance().player.level();
        if(level.dimension() == LevelGen.VALORIA_KEY) {
            float f = level.getTimeOfDay(pPartialTick) % 24000;
            cir.setReturnValue(ColorUtil.toVec3(f > 9000 ? Pal.smokyBlack: Pal.valoriaSky));
        }
    }
}
