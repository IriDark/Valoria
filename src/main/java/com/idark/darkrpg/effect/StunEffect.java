package com.idark.darkrpg.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class StunEffect extends Effect {
	
    public StunEffect() {
        super(EffectType.HARMFUL, 0x6CCE31);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", (double)-1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
   		addAttributesModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", (double)-1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
       	addAttributesModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        entityLivingBaseIn.jumpMovementFactor = 0;
        entityLivingBaseIn.rotationYaw = entityLivingBaseIn.prevRotationYaw;
        entityLivingBaseIn.rotationPitch = entityLivingBaseIn.prevRotationPitch;
        entityLivingBaseIn.rotationYawHead = entityLivingBaseIn.prevRotationYawHead;
    }
	
	@Override
    public boolean isReady(int duration, int amplifier) {
    return true;
    }
}