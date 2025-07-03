package com.idark.valoria.core.mixin;

import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(Mob.class)
public interface  MobAmbientSoundInvoker{

    @Invoker("getAmbientSound")
    SoundEvent invokeGetAmbientSound();
}

