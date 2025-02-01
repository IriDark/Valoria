package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import pro.komaru.tridot.client.graphics.*;

public class StunEffect extends MobEffect{

    public StunEffect(){
        super(MobEffectCategory.HARMFUL, Clr.hexToDecimal("F6F1C5"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}