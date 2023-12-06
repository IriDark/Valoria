package com.idark.darkrpg.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class AloeRegenEffect extends MobEffect {
	
    public AloeRegenEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x6CCE31);
    }
	
	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    super.applyEffectTick(entityLivingBaseIn, amplifier);
        if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.heal(0.010F);
        }
	}
	
	@Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
    return true;
    }
}