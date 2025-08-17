package com.idark.valoria.registries.level;

import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.TickEvent.*;
import pro.komaru.tridot.util.*;
import pro.komaru.tridot.util.math.*;

public class NihilityMeter{

    public static void tick(PlayerTickEvent event, INihilityLevel nihilityLevel, Player player){
        float max = nihilityLevel.getMaxAmount(player, false);
        float amount = nihilityLevel.getAmount(false);
        if(player.level().dimension() == LevelGen.VALORIA_KEY){
            valoriaTick(nihilityLevel, player, amount, max);
        }else{
            if(player.tickCount % 120 == 0 && amount > 0){
                nihilityLevel.decrease(event.player, Tmp.rnd.nextInt(1, 5));
            }
        }
    }

    private static void valoriaTick(INihilityLevel nihilityLevel, Player player, float amount, float max){
        if(player.tickCount % 60 == 0){
            double resistance = player.getAttributeValue(AttributeReg.VOID_RESISTANCE.get());
            double factor = Math.max(0.05, 1.0 - (resistance * 0.05));
            nihilityLevel.modifyAmount(player, (int)Math.ceil(Tmp.rnd.nextInt(1, 5) * factor));
        }

        if(player.tickCount % 40 == 0){
            if(amount > max * 0.5f){
                player.hurt(DamageSourceRegistry.voidHarm(player.level()), Mathf.clamp(amount * 0.125f, 1, 15));
            }

            if(amount > max * 0.75f && !player.hasEffect(MobEffects.BLINDNESS)){
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
            }
        }
    }
}
