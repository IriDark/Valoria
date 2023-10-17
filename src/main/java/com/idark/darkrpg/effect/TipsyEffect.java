package com.idark.darkrpg.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class TipsyEffect extends Effect {
	
    public TipsyEffect() {
        super(EffectType.HARMFUL, 0x6CCE31);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.ARMOR, "74841448-7BD1-4C3F-924D-EED3F7A6E439", (double)-0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", (double)0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
       	addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
	
	@Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
    return true;
    }
	
	/*@Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.world.isRemote && entityLivingBaseIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entityLivingBaseIn;
		}
    }*/
}