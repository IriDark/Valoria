package com.idark.valoria.core.proxy;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

public class ServerProxy implements ISidedProxy{
    @Override
    public Player getPlayer(){
        return null;
    }

    @Override
    public Level getLevel(){
        return null;
    }
}