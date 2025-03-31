package com.idark.valoria.registries.entity.living.boss.dryador.phases;

import com.idark.valoria.registries.entity.living.boss.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.*;

public class BossPhase implements IBossPhase{
    public SoundEvent event;
    public LivingEntity entity;
    public String name;
    public boolean playedSound = false;
    public BossPhase(LivingEntity entity, String name){
        this.entity = entity;
        this.name = name;
    }

    public BossPhase setSound(SoundEvent e){
        this.event = e;
        return this;
    }

    @Override
    public String toString(){
        return "BossPhase{" +
        ", event=" + event.getLocation().getPath() +
        ", name='" + name + '\'' +
        '}';
    }

    @Override
    public void onEnter(){
        if(event != null) {
            entity.playSound(event);
        }

        playedSound = true;
    }

    @Override
    public boolean shouldTransition(){
        return false;
    }

    @Override
    public boolean playedSound(){
        return playedSound;
    }

    @Override
    public @Nullable SoundEvent getTransitionSound(){
        return event;
    }
}
