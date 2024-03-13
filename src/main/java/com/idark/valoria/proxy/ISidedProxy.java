package com.idark.valoria.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface ISidedProxy {
    Player getPlayer();

    Level getWorld();
}