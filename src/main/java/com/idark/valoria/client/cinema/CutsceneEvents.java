package com.idark.valoria.client.cinema;

import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.*;

public class CutsceneEvents{

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event){
        if(event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if(!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer){
            int cinematicTimer = player.getPersistentData().getInt("ValoriaCinematicTicks");
            if(cinematicTimer > 0){
                player.getPersistentData().putInt("ValoriaCinematicTicks", cinematicTimer - 1);
                if(cinematicTimer == 1){
                    player.getPersistentData().putBoolean("ValoriaCinematic", false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event){
        if(event.getEntity() instanceof Player player && player.getPersistentData().getBoolean("ValoriaCinematic")){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMobTarget(LivingChangeTargetEvent event){
        if(event.getNewTarget() instanceof Player player && player.getPersistentData().getBoolean("ValoriaCinematic")){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event){
        var entity = event.getEntity();
        if(entity instanceof Player player && player.getPersistentData().getBoolean("ValoriaCinematic")){
            event.setCanceled(true);
        }
    }
}