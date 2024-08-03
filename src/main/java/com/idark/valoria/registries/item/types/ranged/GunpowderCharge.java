package com.idark.valoria.registries.item.types.ranged;

import net.minecraft.world.item.*;

public class GunpowderCharge extends Item{
    private final float radius;
    public GunpowderCharge(float pRadius, Properties pProperties){
        super(pProperties);
        radius = pRadius;
    }

    public float getRadius(){
        return radius;
    }
}
