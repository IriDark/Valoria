package com.idark.valoria.core.mixin;

import net.minecraft.world.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(RangedAttribute.class)
public interface RangedAttributeMixin{

    @Accessor("maxValue")
    @Mutable
    void setMaxValue(double customMaxValue);
}
