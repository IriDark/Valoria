package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import pro.komaru.tridot.util.*;

public class ExhaustionEffect extends MobEffect{

    public ExhaustionEffect(){
        super(MobEffectCategory.HARMFUL, Col.hexToDecimal("c6223b"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}