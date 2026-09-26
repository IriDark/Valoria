package com.idark.valoria.registries.effect;

import com.idark.valoria.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import pro.komaru.tridot.util.*;

public class StunEffect extends MobEffect{

    public StunEffect(){
        super(MobEffectCategory.HARMFUL, Col.hexToDecimal("F6F1C5"));
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Valoria.loc("stun_speed"), -0.5D, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier){
        return true;
    }
}