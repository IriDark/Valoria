package com.idark.darkrpg.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AloeRegenEffect extends Effect {
	
    public AloeRegenEffect() {
        super(EffectType.BENEFICIAL, 0x6CCE31);
    }
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    super.performEffect(entityLivingBaseIn, amplifier);
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(0.1F);
        }
	}
	
	@Override
    public boolean isReady(int duration, int amplifier) {
    return true;
    }
}
