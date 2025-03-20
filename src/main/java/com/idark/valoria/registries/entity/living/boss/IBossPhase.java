package com.idark.valoria.registries.entity.living.boss;

import net.minecraft.sounds.*;

import javax.annotation.*;

public interface IBossPhase {
    void onEnter();
    default void update() {}
    boolean shouldTransition();
    @Nullable SoundEvent getTransitionSound();
}