package com.idark.valoria.core.mixin.client;

import com.idark.valoria.registries.level.LevelGen;
import com.idark.valoria.util.ColorUtil;
import com.idark.valoria.util.Pal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin{

    @Inject(at = @At("HEAD"), method = "getSkyColor", cancellable = true)
    public void getSkyColor(Vec3 pPos, float pPartialTick, CallbackInfoReturnable<Vec3> cir){
        Level level = Minecraft.getInstance().player.level();
        if(level.dimension() == LevelGen.VALORIA_KEY){
            float f = level.getTimeOfDay(pPartialTick) % 24000;
            if(f > 9000){
                cir.setReturnValue(ColorUtil.toVec3(Pal.valoriaSky));
            }
        }
    }
}
