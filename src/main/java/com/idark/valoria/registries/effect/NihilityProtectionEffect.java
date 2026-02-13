package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import pro.komaru.tridot.util.*;

public class NihilityProtectionEffect extends MobEffect{
    public NihilityProtectionEffect(){
        super(MobEffectCategory.HARMFUL, Col.hexToDecimal("733d9c"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}