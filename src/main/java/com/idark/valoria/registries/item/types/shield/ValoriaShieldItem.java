package com.idark.valoria.registries.item.types.shield;

import com.idark.valoria.core.config.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.*;
import pro.komaru.tridot.common.registry.item.types.*;

import java.util.*;

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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag){
        if(pStack.is(Items.SHIELD)){
            if(!CommonConfig.VANILLA_SHIELD_MODIFY.get()) {
                return;
            }
        }

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public float onPostBlock(ItemStack stack, float armor){
        if(stack.is(Items.SHIELD)){
            if(!CommonConfig.VANILLA_SHIELD_MODIFY.get()){
                armor = 1;
            }
        }

        return super.onPostBlock(stack, armor);
    }
}