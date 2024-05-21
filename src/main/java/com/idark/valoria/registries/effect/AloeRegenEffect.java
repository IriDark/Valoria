package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;

public class AloeRegenEffect extends MobEffect{

    public AloeRegenEffect(){
        super(MobEffectCategory.BENEFICIAL, 0x6CCE31);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier){
        super.applyEffectTick(entityLivingBaseIn, amplifier);
        if(entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()){
            entityLivingBaseIn.heal(0.025F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}