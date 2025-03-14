package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import pro.komaru.tridot.util.*;

//todo
public class SinisterPredictionEffect extends MobEffect{
    public SinisterPredictionEffect(){
        super(MobEffectCategory.NEUTRAL, Col.hexToDecimal("ea7e3f"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}