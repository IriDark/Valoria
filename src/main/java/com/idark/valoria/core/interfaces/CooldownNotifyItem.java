package com.idark.valoria.core.interfaces;

import net.minecraft.sounds.SoundEvent;

// todo delete

/**
 * Implement this Interface to play a SoundEvent after the Item cooldown ending
 */
@FunctionalInterface
public interface CooldownNotifyItem{
    SoundEvent getSoundEvent();
}