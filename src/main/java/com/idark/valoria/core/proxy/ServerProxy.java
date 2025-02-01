package com.idark.valoria.core.proxy;

import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

import java.util.*;

public class ServerProxy implements ISidedProxy{
    @Override
    public Player getPlayer(){
        return null;
    }

    @Override
    public Level getLevel(){
        return null;
    }

    @Override
    public void removeBossBarRender(UUID bossBar){

    }

    @Override
    public void setBossBarRender(UUID bossBar, String id, SoundEvent bossMusic){

    }
}