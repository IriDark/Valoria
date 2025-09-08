package com.idark.valoria.registries.level;

import com.idark.valoria.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.registries.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.TickEvent.*;
import pro.komaru.tridot.client.render.gui.*;
import pro.komaru.tridot.util.*;


public class NihilityMeter{
    public static float damagingLevel = 0.5f;
    public static float criticalLevel = 0.75f;

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
        float maxClient = nihilityLevel.getMaxAmount(player, true);
        float amountClient = nihilityLevel.getAmount(true);
        if(isDamagingLevel(player, amountClient, maxClient)){
            float ratio = amountClient / maxClient;
            boolean flag = ratio >= damagingLevel;
            if(flag){
                OverlayInstance instance = new OverlayInstance();
                instance.setTexture(Valoria.loc("textures/gui/overlay/corruption.png"));
                instance.setShowTime(20);
                OverlayHandler.addInstance(instance);
            }
        }
    }

    private static boolean isDamagingLevel(Player player, float amountClient, float maxClient){
        return player.tickCount % (amountClient < maxClient * criticalLevel ? 40 : 20) == 0;
    }

    private static void valoriaTick(INihilityLevel nihilityLevel, Player player, float amount, float max){
        if(player.tickCount % player.getAttributeValue(AttributeReg.NIHILITY_RESILIENCE.get()) == 0){
            double resistance = player.getAttributeValue(AttributeReg.NIHILITY_RESILIENCE.get());
            double factor = Math.max(0.05, 1.0 - (resistance * 0.05));
            nihilityLevel.modifyAmount(player, (int)Math.ceil(Tmp.rnd.nextInt(1, 5) * factor));
        }
    }
}
