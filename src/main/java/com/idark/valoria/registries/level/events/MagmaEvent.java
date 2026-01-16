package com.idark.valoria.registries.level.events;

import com.idark.valoria.core.capability.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.TickEvent.*;

public class MagmaEvent{

    public static void tick(PlayerTickEvent event, IMagmaLevel magmaLevel, Player player){
        float max = magmaLevel.getMaxAmount(player);
        float amount = magmaLevel.getAmount();
        if(max <= 0) return;

        if(player.isOnFire() || player.isInLava()){
            if(player.tickCount % (player.isInLava() ? 20 : 60) == 0){
                magmaLevel.decrease(player, 1);
            }
        } else if(player.tickCount % 10 == 0 && amount < max){
            magmaLevel.modifyAmount(player, 1);
        }
    }
}