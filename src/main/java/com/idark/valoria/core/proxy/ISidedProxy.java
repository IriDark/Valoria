package com.idark.valoria.core.proxy;

import net.minecraft.sounds.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;

import java.util.*;

public interface ISidedProxy {
    Player getPlayer();

    Level getLevel();

    void removeBossBarRender(UUID bossBar);

    void setBossBarRender(UUID bossBar, String id, SoundEvent bossMusic);
}