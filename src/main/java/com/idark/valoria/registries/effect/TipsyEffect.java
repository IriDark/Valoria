package com.idark.valoria.registries.effect;

import com.idark.valoria.util.ColorUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TipsyEffect extends MobEffect {

    public TipsyEffect() {
        super(MobEffectCategory.NEUTRAL, ColorUtil.hexToDecimal("ecc597"));
        addAttributeModifier(Attributes.ARMOR, "74841448-7BD1-4C3F-924D-EED3F7A6E439", -0.35F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.45F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}