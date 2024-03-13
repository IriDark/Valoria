package com.idark.valoria.registries.world.effect;

import com.idark.valoria.util.ColorUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StunEffect extends MobEffect {

    public StunEffect() {
        super(MobEffectCategory.HARMFUL, ColorUtils.hexToDecimal("F6F1C5"));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        super.applyEffectTick(entityLivingBaseIn, amplifier);
        entityLivingBaseIn.setSpeed(0);
        entityLivingBaseIn.setYRot(entityLivingBaseIn.yRotO);
        entityLivingBaseIn.setXRot(entityLivingBaseIn.xRotO);
        entityLivingBaseIn.yHeadRot = entityLivingBaseIn.yHeadRotO;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}