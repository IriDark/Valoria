package com.idark.valoria.core.interfaces;

import net.minecraft.sounds.SoundEvent;

// todo move to lib
/**
 * Implement this Interface to play a SoundEvent after the Item cooldown ending
 */
public interface CooldownNotifyItem {
    SoundEvent getSoundEvent();
}