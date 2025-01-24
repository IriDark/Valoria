package com.idark.valoria.registries.effect;

import com.idark.valoria.util.ColorUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class StunEffect extends MobEffect{

    public StunEffect(){
        super(MobEffectCategory.HARMFUL, ColorUtil.hexToDecimal("F6F1C5"));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}