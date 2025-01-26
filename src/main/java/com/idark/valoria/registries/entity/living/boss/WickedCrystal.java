package com.idark.valoria.registries.entity.living.boss;

import com.idark.valoria.client.ui.bossbars.ServerBossBarEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class WickedCrystal extends AbstractBoss{
    public WickedCrystal(EntityType<? extends PathfinderMob> pEntityType, Level pLevel){
        super(pEntityType, pLevel, (ServerBossBarEvent)(new ServerBossBarEvent(pEntityType.getDescription(), "Wicked Crystal")).setDarkenScreen(true));
    }
}