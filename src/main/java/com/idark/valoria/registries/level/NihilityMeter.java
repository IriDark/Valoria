package com.idark.valoria.registries.level;

import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.TickEvent.*;
import pro.komaru.tridot.util.*;

//todo fix
public class NihilityMeter{
    public static float damagingLevel = 0.5f;
    public static float criticalLevel = 0.75f;

    public static void tick(PlayerTickEvent event, INihilityLevel nihilityLevel, Player player){
        float max = nihilityLevel.getMaxAmount(player);
        float amount = nihilityLevel.getAmount();
        if(player.level().dimension() == LevelGen.VALORIA_KEY){
            valoriaTick(nihilityLevel, player);
        }else{
            if(player.tickCount % 120 == 0 && amount > 0){
                nihilityLevel.decrease(player, Tmp.rnd.nextInt(1, 5));
            }
        }

        if(isDamagingLevel(player, amount, max)){
            float ratio = amount / max;
            boolean flag = ratio >= damagingLevel;
            if(flag){
                int segments = (int)((ratio - damagingLevel) / 0.1f);
                float damage = 1 + segments * 2;
                player.hurt(DamageSourceRegistry.voidHarm(player.level()), damage);
            }

            if(amount > max * criticalLevel && !player.hasEffect(MobEffects.BLINDNESS)){
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 0));
            }
        }
    }

    public static void clientTick(INihilityLevel nihilityLevel, Player player) {

    }

    private static boolean isDamagingLevel(Player player, float amountClient, float maxClient){
        return player.tickCount % (amountClient < maxClient * criticalLevel ? 40 : 20) == 0;
    }

    private static void valoriaTick(INihilityLevel nihilityLevel, Player player){
        if(player.tickCount % (int)(player.getAttributeValue(AttributeReg.NIHILITY_RESILIENCE.get()) * 20) == 0){
            double resistance = player.getAttributeValue(AttributeReg.NIHILITY_RESISTANCE.get());
            double factor = Math.max(0.05, 1.0 - (resistance * 0.05));
            nihilityLevel.modifyAmount(player, (int)Math.ceil(Tmp.rnd.nextInt(1, 5) * factor));
        }
    }
}