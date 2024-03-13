package com.idark.valoria.registries.world.effect;

import com.idark.valoria.util.ColorUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TipsyEffect extends MobEffect {

    public TipsyEffect() {
        super(MobEffectCategory.HARMFUL, ColorUtils.hexToDecimal("ecc597"));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "1107DE5E-7AE8-2030-840A-21B21F160890", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ARMOR, "74841448-7BD1-4C3F-924D-EED3F7A6E439", 0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}