package com.idark.valoria.core.proxy;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

public interface ISidedProxy{
    Player getPlayer();

    Level getWorld();
}