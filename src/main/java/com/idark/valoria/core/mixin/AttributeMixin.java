package com.idark.valoria.core.mixin;

import net.minecraft.world.entity.ai.attributes.*;
import org.spongepowered.asm.mixin.*;

@Mixin({Attributes.class})
public class AttributeMixin {
    static {
        ((RangedAttributeMixin) Attributes.MAX_HEALTH).setMaxValue(100000.0);
    }
}