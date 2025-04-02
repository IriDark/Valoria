package com.idark.valoria.registries.entity.living.boss.dryador.phases;

import com.idark.valoria.registries.entity.living.boss.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.*;

import java.util.function.*;

public class BossPhase implements IBossPhase{
    public SoundEvent event;
    public LivingEntity entity;
    public boolean playedSound = false;
    public final BooleanSupplier transitionCondition;
    public BossPhase(LivingEntity entity, BooleanSupplier transitionCondition){
        this.entity = entity;
        this.transitionCondition = transitionCondition;
    }

    public BossPhase setSound(SoundEvent e){
        this.event = e;
        return this;
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
        return transitionCondition.getAsBoolean();
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
