package com.idark.valoria.core.proxy;

import net.minecraft.client.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

import java.util.*;

public class ClientProxy implements ISidedProxy {
    public static Map<UUID, String> bossbars = new HashMap<>();

    @Override
    public Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getLevel() {
        return Minecraft.getInstance().level;
    }

    public static void removeBossBarRender(UUID bossBar) {
        bossbars.remove(bossBar);
    }

    public static void setBossBarRender(UUID bossBar, String id) {
        bossbars.put(bossBar, id);
    }
}