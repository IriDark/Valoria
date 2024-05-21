package com.idark.valoria.core.proxy;

import net.minecraft.client.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

public class ClientProxy implements ISidedProxy{
    @Override
    public Player getPlayer(){
        return Minecraft.getInstance().player;
    }

    @Override
    public Level getWorld(){
        return Minecraft.getInstance().level;
    }
}