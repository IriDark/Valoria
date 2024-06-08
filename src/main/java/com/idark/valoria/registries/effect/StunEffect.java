package com.idark.valoria.registries.effect;

import com.idark.valoria.util.*;
import net.minecraft.world.effect.*;

public class StunEffect extends MobEffect{

    public StunEffect(){
        super(MobEffectCategory.HARMFUL, ValoriaUtils.color.hexToDecimal("F6F1C5"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}