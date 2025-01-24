package com.idark.valoria.core.proxy;

import com.idark.valoria.ValoriaClient;
import com.idark.valoria.client.sounds.LoopedSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientProxy implements ISidedProxy{
    public static Map<UUID, String> bossbars = new HashMap<>();

    @Override
    public Player getPlayer(){
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getLevel(){
        return Minecraft.getInstance().level;
    }

    @OnlyIn(Dist.CLIENT)
    public static void playSound(SoundEvent event){
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        if(ValoriaClient.BOSS_MUSIC != null && soundManager.isActive(ValoriaClient.BOSS_MUSIC)){
            return;
        }

        ValoriaClient.BOSS_MUSIC = new LoopedSoundInstance(event, Minecraft.getInstance().player);
        soundManager.play(ValoriaClient.BOSS_MUSIC);
        if(!soundManager.isActive(ValoriaClient.BOSS_MUSIC)){
            ValoriaClient.BOSS_MUSIC = null;
        }
    }

    public void removeBossBarRender(UUID bossBar){
        bossbars.remove(bossBar);
        if(ValoriaClient.BOSS_MUSIC != null){
            Minecraft.getInstance().getSoundManager().stop(ValoriaClient.BOSS_MUSIC);
        }
    }

    public void setBossBarRender(UUID bossBar, String id, SoundEvent bossMusic){
        bossbars.put(bossBar, id);
        playSound(bossMusic);
    }
}