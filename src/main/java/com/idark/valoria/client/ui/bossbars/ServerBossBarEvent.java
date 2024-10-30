package com.idark.valoria.client.ui.bossbars;

import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;

public class ServerBossBarEvent extends ServerBossEvent {
    public LivingEntity boss;
    public ServerBossBarEvent(Component pName, LivingEntity boss) {
        super(pName, BossBarColor.PURPLE, BossBarOverlay.PROGRESS);
        this.boss = boss;
    }

    public LivingEntity getEntity(){
        return boss;
    }
}