package com.idark.valoria.registries.effect;

import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import pro.komaru.tridot.client.graphics.*;

public class StunEffect extends MobEffect{

    public StunEffect(){
        super(MobEffectCategory.HARMFUL, Clr.hexToDecimal("F6F1C5"));
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "0340e1c5-92f3-4c99-80e1-c592f3ec99a8", -0.5D, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier){
        return true;
    }
}