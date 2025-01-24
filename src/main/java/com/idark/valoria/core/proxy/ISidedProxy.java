package com.idark.valoria.core.proxy;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public interface ISidedProxy{
    Player getPlayer();

    Level getLevel();

    void removeBossBarRender(UUID bossBar);

    void setBossBarRender(UUID bossBar, String id, SoundEvent bossMusic);
}