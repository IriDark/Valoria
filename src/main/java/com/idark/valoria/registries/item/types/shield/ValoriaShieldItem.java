package com.idark.valoria.registries.item.types.shield;

import com.idark.valoria.registries.*;
import net.minecraft.sounds.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.types.*;

public class ValoriaShieldItem extends ConfiguredShield {
    public ValoriaShieldItem(Properties pProperties){
        super(pProperties);
        canParry = true;
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

    @Override
    public @Nullable SoundEvent parrySound(){
        return SoundsRegistry.SHIELD_PARRY.get();
    }
}