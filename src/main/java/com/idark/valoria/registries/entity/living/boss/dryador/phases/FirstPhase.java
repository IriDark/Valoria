package com.idark.valoria.registries.entity.living.boss.dryador.phases;

import com.idark.valoria.registries.entity.living.boss.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.*;

public class FirstPhase implements IBossPhase{
    public boolean shouldTransition;
    public SoundEvent event;
    public LivingEntity entity;
    public FirstPhase(LivingEntity entity, boolean reason) {
        this.entity = entity;
        this.shouldTransition = reason;
    }

    public FirstPhase setSound(SoundEvent e) {
        this.event = e;
        return this;
    }

    @Override
    public void onEnter(){
        if(event != null) {
            entity.playSound(event);
        }
    }

    @Override
    public boolean shouldTransition(){
        return shouldTransition;
    }

    @Override
    public @Nullable SoundEvent getTransitionSound(){
        return event;
    }
}
