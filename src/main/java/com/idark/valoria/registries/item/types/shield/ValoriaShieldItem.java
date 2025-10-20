package com.idark.valoria.registries.item.types.shield;

import pro.komaru.tridot.common.registry.item.types.*;

public class ValoriaShieldItem extends ConfiguredShield{
    public ValoriaShieldItem(Properties pProperties){
        super(pProperties);
    }

    public ValoriaShieldItem(float defPercent, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
    }

    public ValoriaShieldItem(float defPercent, int useDuration, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
        this.useDuration = useDuration;
        this.infiniteUse = false;
    }

    public ValoriaShieldItem(float defPercent, int useDuration, int cooldown, Properties pProperties){
        this(pProperties);
        this.blockedPercent = defPercent;
        this.useDuration = useDuration;
        this.cooldownTicks = cooldown;
        this.infiniteUse = false;
    }
}