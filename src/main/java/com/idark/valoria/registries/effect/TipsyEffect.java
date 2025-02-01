package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import pro.komaru.tridot.client.graphics.*;

public class TipsyEffect extends MobEffect{

    public TipsyEffect(){
        super(MobEffectCategory.NEUTRAL, Clr.hexToDecimal("ecc597"));
        addAttributeModifier(Attributes.ARMOR, "74841448-7BD1-4C3F-924D-EED3F7A6E439", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}