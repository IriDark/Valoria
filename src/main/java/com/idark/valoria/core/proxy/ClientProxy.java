package com.idark.valoria.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientProxy implements ISidedProxy {
    @Override
    public Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getLevel() {
        return Minecraft.getInstance().level;
    }
}