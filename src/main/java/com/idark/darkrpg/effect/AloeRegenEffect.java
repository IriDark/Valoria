package com.idark.darkrpg.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AloeRegenEffect extends Effect {
	
    public AloeRegenEffect() {
        super(EffectType.BENEFICIAL, 0x6CCE31);
    }
	
	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    super.applyEffectTick(entityLivingBaseIn, amplifier);
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(0.005F);
        }
	}
	
	@Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
    return true;
    }
}