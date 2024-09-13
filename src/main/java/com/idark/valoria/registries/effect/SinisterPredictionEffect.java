package com.idark.valoria.registries.effect;

import com.idark.valoria.util.*;
import net.minecraft.world.effect.*;

public class SinisterPredictionEffect extends MobEffect{
    public SinisterPredictionEffect(){
        super(MobEffectCategory.NEUTRAL, ColorUtil.hexToDecimal("ea7e3f"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}