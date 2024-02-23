package com.idark.valoria.mixin;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({Attributes.class})
public class AttributeMixin {
    static {
        ((RangedAttributeMixin) Attributes.ARMOR).setMaxValue(100000.0);
        ((RangedAttributeMixin) Attributes.ARMOR_TOUGHNESS).setMaxValue(100000.0);
    }
}